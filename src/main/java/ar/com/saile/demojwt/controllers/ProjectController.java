package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.domain.AppProject;
import ar.com.saile.demojwt.domain.AppUser;
import ar.com.saile.demojwt.exceptions.BindingResultException;
import ar.com.saile.demojwt.exceptions.NotAuthorizedException;
import ar.com.saile.demojwt.exceptions.RecordNotFoundException;
import ar.com.saile.demojwt.service.ProjectService;
import ar.com.saile.demojwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    private final UserService userService;

    @PutMapping("{id}")
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

    @PostMapping("/")
    public AppProject createProjects(@Valid @RequestBody AppProject appProject, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        if (!appUser.getProjects().contains(appProject)) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        appProject.setUserApp(appUser);
        return projectService.saveProject(appProject);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> deleteProjects(@PathVariable Long id, HttpServletRequest request) {

        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        AppProject appEducation = projectService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
        if (!appUser.getProjects().contains(appEducation)) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        Map<String, Object> projectResponse = new HashMap<>();
        projectResponse.put("status", id);
        projectResponse.put("result", projectService.deleteById(id));
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public AppProject viewProjects(@PathVariable Long id) {

        return projectService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
    }
}
