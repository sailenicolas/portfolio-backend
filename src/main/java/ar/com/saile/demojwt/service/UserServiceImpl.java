package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.*;
import ar.com.saile.demojwt.repository.RoleRepository;
import ar.com.saile.demojwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    private final ProjectService projectService;


    private final ExperienceService experienceService;

    private final EducationService educationService;

    private final SoftSkillService softSkillService;

    private final AboutMeService aboutMeService;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser saveUser(AppUser appUser) {

        appUser.setPassword(bCryptPasswordEncoder
                .encode(appUser.getPassword()));
        userRepository.save(appUser);
        return null;
    }

    @Override
    public AppRole addRole(AppRole appRole) {

        return null;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        Optional<AppUser> appUser = userRepository.findByUsername(username);
        AppRole appRole = roleRepository.findByName(roleName);
        appUser.ifPresent(user -> user.getRoleCollection().add(appRole));
    }

    @Override
    public Page<AppUser> getUsers() {

        return userRepository.findAll(Pageable.ofSize(15).withPage(0));
    }

    @Override
    public AppRole saveRole(AppRole appRole) {

        return roleRepository.save(appRole);
    }

    @Override
    public void addSoftSkillsToUser(String username, AppSoftSkill appSoftSkill) {

        Optional<AppUser> appUser = userRepository.findByUsername(username);
        if (appUser.isPresent()) {
            appSoftSkill.setUserApp(appUser.get());
            this.softSkillService.saveSoftSkills(appSoftSkill);
        }
    }

    @Override
    public void addProjectsToUser(String username, AppProject appProject) {

        Optional<AppUser> appUser = userRepository.findByUsername(username);
        if (appUser.isPresent()) {
            appProject.setUserApp(appUser.get());
            this.projectService.saveProject(appProject);

        }
    }


    @Override
    public void addEducationToUser(String username, AppEducation appEducation) {

        Optional<AppUser> appUser = userRepository.findByUsername(username);
        if (appUser.isPresent()) {
            appEducation.setUserApp(appUser.get());
            this.educationService.saveEducation(appEducation);

        }
    }


    @Override
    public void addExperienceToUser(String username, AppExperience appExperience) {

        Optional<AppUser> appUser = userRepository.findByUsername(username);
        if (appUser.isPresent()) {
            appExperience.setUserApp(appUser.get());
            this.experienceService.saveExperience(appExperience);
        }
    }

    @Override
    public Optional<AppUser> findByUsername(AppUser appUser) {

        return userRepository.findByUsername(appUser.getUsername());
    }

    @Override
    public AppUser findByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow();
    }


    @Override
    public void addUserToAboutMe(AppAboutMe appAboutMe) {

        aboutMeService.saveAboutMe(appAboutMe);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AppUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("username not found");
        }
        Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
        user.get().getRoleCollection().forEach(appRole -> roles.add(new SimpleGrantedAuthority(appRole.getName())));
        return new User(user.get().getUsername(), user.get().getPassword(), roles);
    }

    @Override
    public AppUser fetchCurrentUser(HttpServletRequest request) {

        UsernamePasswordAuthenticationToken userPrincipal = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        AppUser details = (AppUser) userPrincipal.getDetails();
        return details;
    }

}
