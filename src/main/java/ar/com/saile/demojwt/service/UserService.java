package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.domain.*;

import java.util.List;

public interface UserService {

    AppUser saveUser(AppUser appUser);
    AppProject saveProject(AppProject appProject);
    AppSoftSkill saveSoftSkills(AppSoftSkill appSoftSkill);
    AppEducation saveEducation(AppEducation appEducation);
    AppExperience saveExperience(AppExperience appExperience);

    AppRole addRole(AppRole appRole);

    void addRoleToUser(String username, String roleName);

    List<AppUser> getUsers();

    AppRole saveRole(AppRole appRole);

    void addSoftSkillsToUser(String username, AppSoftSkill appSoftSkill);

    void addProjectsToUser(String username, AppProject appProject);

    void addEducationToUser(String username, AppEducation appEducation);

    void addExperienceToUser(String username, AppExperience appExperience);
}
