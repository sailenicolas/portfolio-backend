package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.domain.AppExperience;
import ar.com.saile.demojwt.domain.AppUser;
import ar.com.saile.demojwt.exceptions.BindingResultException;
import ar.com.saile.demojwt.exceptions.NotAuthorizedException;
import ar.com.saile.demojwt.exceptions.RecordNotFoundException;
import ar.com.saile.demojwt.service.ExperienceService;
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
@RequestMapping("/api/v1/user/experiences")
@RequiredArgsConstructor
@Slf4j
public class ExperienceController {

    private final ExperienceService experienceService;

    private final UserService userService;

    @PutMapping("{id}")
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

    @PostMapping("/")
    public AppExperience createExperience(@Valid @RequestBody final AppExperience appExperience, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        if (!appUser.getExperiences().contains(appExperience)) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        appExperience.setUserApp(appUser);
        return experienceService.saveExperience(appExperience);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> deleteExperience(@PathVariable Long id, HttpServletRequest request) {

        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        AppExperience appExperience = experienceService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT_FOUND"));
        if (!appUser.getExperiences().contains(appExperience)) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        Map<String, Object> experienceResponse = new HashMap<>();
        experienceResponse.put("id", id);
        experienceResponse.put("result", experienceService.deleteById(id));
        return new ResponseEntity<>(experienceResponse, HttpStatus.OK);

    }


    @GetMapping("{id}")
    public AppExperience viewExperience(@PathVariable Long id) {

        return experienceService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
    }


}
