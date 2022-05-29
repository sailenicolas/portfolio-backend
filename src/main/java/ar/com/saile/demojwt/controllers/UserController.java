package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.domain.*;
import ar.com.saile.demojwt.exceptions.BindingResultException;
import ar.com.saile.demojwt.exceptions.NotAuthorizedException;
import ar.com.saile.demojwt.exceptions.RecordNotFoundException;
import ar.com.saile.demojwt.service.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final EducationService educationService;

    private final ProjectService projectService;

    private final ExperienceService experienceService;

    private final SoftSkillService softSkillService;

    private final AboutMeService aboutMeService;

    @PostMapping("/register")
    public AppUser addUser(@RequestBody AppUser appUser) {

        return userService.saveUser(appUser);
    }

    @GetMapping("/me")
    @Transactional
    public Object getAppUser(HttpServletRequest request) {

        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        Optional<AppUser> appUserUs = userService.findByUsername(appUser);
        return appUserUs.orElse(null);
    }

    @PostMapping("/role")
    public ResponseEntity<AppRole> addRole(@RequestBody AppRole appRole) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(appRole));
    }

    @GetMapping("/users")
    public ResponseEntity<Page<AppUser>> getUsers() {

        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/roleToUser")
    public ResponseEntity<?> RoleToUser(@RequestBody RoleToUser form) {

        userService.addRoleToUser(form.getRoleNane(), form.getUserName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken() {

    }

    @PutMapping("/experience/{id}")
    public AppExperience editExperience(@PathVariable Long id, @Valid @RequestBody final AppExperience appExperience, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        appExperience.setId(id);
        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        if (!appUser.getExperiences().contains(appExperience)) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        appExperience.setUserApp(appUser);
        return experienceService.saveExperience(appExperience);

    }

    @PutMapping("/education/{id}")
    public AppEducation editEducation(@PathVariable Long id, @Valid @RequestBody final AppEducation appEducation, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        appEducation.setId(id);
        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        if (!appUser.getEducations().contains(appEducation)) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        appEducation.setUserApp(appUser);
        return educationService.saveEducation(appEducation);

    }

    @PutMapping("/softskills/{id}")
    public AppSoftSkill editSoftSkills(@PathVariable Long id, @Valid @RequestBody final AppSoftSkill appSoftSkill, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        appSoftSkill.setId(id);
        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        if (!appUser.getSoftSkills().contains(appSoftSkill)) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        appSoftSkill.setUserApp(appUser);
        return softSkillService.saveSoftSkills(appSoftSkill);
    }

    @PutMapping("/projects/{id}")
    public AppProject editProjects(@PathVariable Long id, @Valid @RequestBody AppProject appProject, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        appProject.setId(id);
        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        if (!appUser.getProjects().contains(appProject)) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        appProject.setUserApp(appUser);
        return projectService.saveProject(appProject);
    }

    @PutMapping("/aboutMe/{id}")
    public AppAboutMe editAboutMe(@PathVariable Long id, @Valid @RequestBody AppAboutMe appAboutMe, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        appAboutMe.setId(id);
        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        AppAboutMe aboutMe = appUser.getAboutMe();
        if (!(Objects.equals(aboutMe.getId(), appAboutMe.getId()))) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        appAboutMe.setAppUser(appUser);
        appUser.setAboutMe(appAboutMe);
        return userService.saveUser(appUser).getAboutMe();


    }


    @GetMapping("/experience/{id}")
    public AppExperience viewExperience(@PathVariable Long id) {

        return experienceService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
    }

    @GetMapping("/education/{id}")
    public AppEducation viewEducation(@PathVariable Long id) {

        return educationService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
    }

    @GetMapping("/softskills/{id}")
    public AppSoftSkill viewSoftSkills(@PathVariable Long id) {

        return softSkillService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
    }

    @GetMapping("/projects/{id}")
    public AppProject viewProjects(@PathVariable Long id) {

        return projectService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
    }

    @GetMapping("/aboutMe")
    public Optional<AppAboutMe> viewAboutMe(HttpServletRequest request) {

        AppUser details = userService.fetchAuthenticatedUserFromRequest(request);
        return aboutMeService.findById(details.getId());
    }

}

@Data
class RoleToUser {

    private String roleNane;

    private String userName;
}