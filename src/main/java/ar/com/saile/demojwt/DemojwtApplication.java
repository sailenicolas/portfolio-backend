package ar.com.saile.demojwt;

import ar.com.saile.demojwt.domain.*;
import ar.com.saile.demojwt.enums.TipoDeEmpleo;
import ar.com.saile.demojwt.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;

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
    CommandLineRunner run(UserService userService, ProjectService projectService, EducationService educationService, ExperienceService experienceService, SoftSkillService softSkillService) {

        return args -> {
            AppRole role_user = new AppRole(null, "ROLE_USER");
            AppRole role_manager = new AppRole(null, "ROLE_MANAGER");
            AppRole role_admin = new AppRole(null, "ROLE_ADMIN");
            AppRole role_super_admin = new AppRole(null, "ROLE_SUPER_ADMIN");
            AppSoftSkill appSoftSkill = new AppSoftSkill(25L, "Algo, aqui", "Me gusta el dulce de leche");
            AppSoftSkill appSoftSkill1 = new AppSoftSkill(10L, "Mas alla", "Me gusta la mermelada");
            AppProject appProject = new AppProject("Algo, aqui", "Me gusta el dulce de leche", "algo, largo aca");
            AppProject appProject1 = new AppProject("Mas alla", "Me gusta la mermelada", "Lorem Ipsum");
            AppEducation appEducation = new AppEducation("institucion", "titulo", "imagen", "Ingenieria algo", 0, LocalDate.of(2010, 2, 1), LocalDate.of(2012, 2, 15));
            AppEducation appEducation1 = new AppEducation("institucion 1", "titulo 2", "imagen", "Ingenieria algo 2", 0, LocalDate.of(2010, 2, 1), LocalDate.of(2012, 2, 15));
            AppExperience appExperience = new AppExperience("cargo", "empresa", TipoDeEmpleo.AUTONOMO, "imagen", LocalDate.of(2020, 2, 13), LocalDate.of(2021, 2, 27), "ubicacion");
            AppExperience appExperience1 = new AppExperience("cargo1", "empresa 2", TipoDeEmpleo.FORMACION, "imagen", LocalDate.of(2019, 2, 4), LocalDate.of(2013, 1, 20), "ubicacion 2");
            userService.saveRole(role_user);
            userService.saveRole(role_manager);
            userService.saveRole(role_admin);
            userService.saveRole(role_super_admin);


            projectService.saveProject(appProject);
            projectService.saveProject(appProject1);
            educationService.saveEducation(appEducation);
            educationService.saveEducation(appEducation1);
            experienceService.saveExperience(appExperience);
            experienceService.saveExperience(appExperience1);

            softSkillService.saveSoftSkills(appSoftSkill);
            softSkillService.saveSoftSkills(appSoftSkill1);

            ArrayList<AppRole> roles = new ArrayList<>();
            ArrayList<AppSoftSkill> softSkills = new ArrayList<>();
            ArrayList<AppEducation> educations = new ArrayList<>();
            ArrayList<AppExperience> experiences = new ArrayList<>();
            ArrayList<AppProject> projects = new ArrayList<>();

            AppAboutMe appAboutMe = new AppAboutMe("assets/images/128.png", "default", "default", "aaaaa", "argentina", "mail1@saile.com.ar");
            AppAboutMe appAboutMe1 = new AppAboutMe("assets/images/128.png", "default", "default", "aaaaa", "argentina", "mail2@saile.com.ar");
            AppAboutMe appAboutMe2 = new AppAboutMe("assets/images/128.png", "default", "default", "aaaaa", "argentina", "mail3@saile.com.ar");
            AppAboutMe appAboutMe3 = new AppAboutMe("assets/images/128.png", "default", "default", "aaaaa", "argentina", "mail4@saile.com.ar");
            AppUser appUser = new AppUser(1L, "john", "1234", roles, softSkills, educations, experiences, projects, appAboutMe);
            AppUser appUser1 = new AppUser(null, "will", "1234", roles, softSkills, educations, experiences, projects, appAboutMe1);
            AppUser appUser2 = new AppUser(null, "jim", "1234", roles, softSkills, educations, experiences, projects, appAboutMe2);
            AppUser appUser3 = new AppUser(null, "arnold", "1234", roles, softSkills, educations, experiences, projects, appAboutMe3);
            appAboutMe.setAppUser(appUser);
            appAboutMe1.setAppUser(appUser1);
            appAboutMe2.setAppUser(appUser2);
            appAboutMe3.setAppUser(appUser3);

            userService.saveUser(appUser);
            userService.saveUser(appUser1);
            userService.saveUser(appUser2);
            userService.saveUser(appUser3);

            userService.addRoleToUser("john", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("john", "ROLE_ADMIN");
            userService.addRoleToUser("john", "ROLE_USER");

            
            userService.addRoleToUser("will", "ROLE_MANAGER");
            userService.addRoleToUser("jim", "ROLE_ADMIN");
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
