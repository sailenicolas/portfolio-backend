package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.*;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {

    AppUser saveUser(AppUser appUser);

    AppRole addRole(AppRole appRole);

    void addRoleToUser(String username, String roleName);

    Page<AppUser> getUsers();

    AppRole saveRole(AppRole appRole);

    void addSoftSkillsToUser(String username, AppSoftSkill appSoftSkill);

    void addProjectsToUser(String username, AppProject appProject);

    void addEducationToUser(String username, AppEducation appEducation);

    void addExperienceToUser(String username, AppExperience appExperience);

    Optional<AppUser> findByUsername(AppUser appUser);

    AppUser findByUsername(String username);

    void addUserToAboutMe(AppAboutMe appAboutMe);

    AppUser fetchAuthenticatedUserFromRequest(HttpServletRequest request);

}
