package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.*;
import ar.com.saile.demojwt.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EducationRepository educationRepository;
    private final ProjectRepository projectRepository;
    private final SoftSkillRepository softSkillRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ExperienceRepository experienceRepository;

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
        log.info("Going back");
        AppUser appUser = userRepository.findByUsername(username);
        AppRole appRole = roleRepository.findByName(roleName);
        appUser.getRoleCollection().add(appRole);
    }

    @Override
    public List<AppUser> getUsers() {

        return userRepository.findAll();
    }

    @Override
    public AppRole saveRole(AppRole appRole) {
        return roleRepository.save(appRole);
    }

    @Override
    public void addSoftSkillsToUser(String username, AppSoftSkill appSoftSkill) {
        AppUser appUser = userRepository.findByUsername(username);
        appUser.getSoftSkills().add(appSoftSkill);
    }
    @Override
    public AppSoftSkill saveSoftSkills(AppSoftSkill appSoftSkill) {

        return softSkillRepository.save(appSoftSkill);
    }

    @Override
    public void addProjectsToUser(String username, AppProject appProject) {
        AppUser appUser = userRepository.findByUsername(username);
        appUser.getProjects().add(appProject);
    }
    @Override
    public AppProject saveProject(AppProject appProject) {

        return projectRepository.save(appProject);
    }

    @Override
    public void addEducationToUser(String username, AppEducation appEducation) {
        AppUser appUser = userRepository.findByUsername(username);
        appUser.getEducations().add(appEducation);
    }
    @Override
    public AppEducation saveEducation(AppEducation appEducation) {

        return educationRepository.save(appEducation);
    }

    @Override
    public AppExperience saveExperience(AppExperience appExperience) {
        return experienceRepository.save(appExperience);
    }

    @Override
    public void addExperienceToUser(String username, AppExperience appExperience) {
        experienceRepository.save(appExperience);
        AppUser appUser = userRepository.findByUsername(username);
        appUser.getExperiences().add(appExperience);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
        user.getRoleCollection().forEach(appRole -> roles.add(new SimpleGrantedAuthority(appRole.getName())));
        return new User(user.getUsername(), user.getPassword(), roles);
    }
}
