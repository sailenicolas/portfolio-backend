package ar.com.saile.demojwt;

import ar.com.saile.demojwt.domain.*;
import ar.com.saile.demojwt.enums.TipoDeEmpleo;
import ar.com.saile.demojwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class DemojwtApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemojwtApplication.class, args);

    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            AppRole role_user = new AppRole(null, "ROLE_USER");
            userService.saveRole(role_user);
            AppRole role_manager = new AppRole(null, "ROLE_MANAGER");
            userService.saveRole(role_manager);
            AppRole role_admin = new AppRole(null, "ROLE_ADMIN");
            userService.saveRole(role_admin);
            AppRole role_super_admin = new AppRole(null, "ROLE_SUPER_ADMIN");
            userService.saveRole(role_super_admin);
            AppSoftSkill appSoftSkill = new AppSoftSkill(25L, "Algo, aqui", "Me gusta el dulce de leche");
            AppSoftSkill appSoftSkill1 = new AppSoftSkill(10L, "Mas alla", "Me gusta la mermelada");
            AppProject appProject = new AppProject("Algo, aqui", "Me gusta el dulce de leche", "");
            AppProject appProject1 = new AppProject("Mas alla", "Me gusta la mermelada", "Lorem Ipsum");
            AppEducation appEducation = new AppEducation("institucion", "titulo", "imagen", "Ingenieria algo", 0, new Date(), new Date());
            AppEducation appEducation1 = new AppEducation("institucion 1", "titulo 2", "imagen", "Ingenieria algo 2", 0, new Date(), new Date());
            AppExperience appExperience = new AppExperience("cargo", "empresa", TipoDeEmpleo.formacion, "imagen", new Date(), "fin", "ubicacion");
            AppExperience appExperience1 = new AppExperience("cargo1", "empresa 2", TipoDeEmpleo.formacion, "imagen", new Date(), "fin", "ubicacion 2");
            userService.saveProject(appProject);
            userService.saveProject(appProject1);
            
            userService.saveEducation(appEducation);
            userService.saveEducation(appEducation1);
            
            userService.saveExperience(appExperience);
            userService.saveExperience(appExperience1);
            
            userService.saveSoftSkills(appSoftSkill);
            userService.saveSoftSkills(appSoftSkill1);

            ArrayList<AppRole> roles = new ArrayList<>();
            ArrayList<AppSoftSkill> softSkills = new ArrayList<>();
            ArrayList<AppEducation> educations = new ArrayList<>();
            ArrayList<AppExperience> experiences = new ArrayList<>();
            ArrayList<AppProject> projects = new ArrayList<>();
            AppUser appUser = new AppUser(1L, "John Travolta", "John Travolta", "john", "1234", roles, softSkills, educations, experiences, projects);
            AppUser appUser1 = new AppUser(null, "John Travolta", "Will Smith", "will", "1234", roles, softSkills, educations, experiences, projects);
            AppUser appUser2 = new AppUser(null, "John Travolta", "Jim Carry", "jim", "1234", roles, softSkills, educations, experiences, projects);
            AppUser appUser3 = new AppUser(null, "John Travolta", "Arnold Schwarzenegger", "arnold", "1234", roles, softSkills, educations, experiences, projects);
            userService.saveUser(appUser);
            userService.saveUser(appUser1);
            userService.saveUser(appUser2);
            userService.saveUser(appUser3);
            userService.addRoleToUser("john", "ROLE_USER");
            userService.addRoleToUser("will", "ROLE_MANAGER");
            userService.addRoleToUser("jim", "ROLE_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_USER");
            userService.addSoftSkillsToUser("john", appSoftSkill);
            userService.addSoftSkillsToUser("john", appSoftSkill1);
            userService.addProjectsToUser("john", appProject);
            userService.addProjectsToUser("john", appProject1);
            userService.addEducationToUser("john", appEducation);
            userService.addEducationToUser("john", appEducation1);
            userService.addExperienceToUser("john", appExperience);
            userService.addExperienceToUser("john", appExperience1);
        };
    }
}
