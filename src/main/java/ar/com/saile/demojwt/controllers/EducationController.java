package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.domain.AppEducation;
import ar.com.saile.demojwt.domain.AppUser;
import ar.com.saile.demojwt.exceptions.BindingResultException;
import ar.com.saile.demojwt.exceptions.NotAuthorizedException;
import ar.com.saile.demojwt.exceptions.RecordNotFoundException;
import ar.com.saile.demojwt.service.EducationService;
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
@RequestMapping("/api/v1/user/educations")
@RequiredArgsConstructor
@Slf4j
public class EducationController {

    private final EducationService educationService;

    private final UserService userService;

    @PostMapping("/")
    public AppEducation createEducation(@Valid @RequestBody final AppEducation appEducation, BindingResult bindingResult, HttpServletRequest request) {

        AppUser appUser = this.userService.fetchAuthenticatedUserFromRequest(request);
        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        appEducation.setUserApp(appUser);
        return educationService.saveEducation(appEducation);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> deleteEducation(@PathVariable Long id, HttpServletRequest request) {

        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        AppEducation appEducation = educationService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
        if (!appUser.getEducations().contains(appEducation)) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        Map<String, Object> educationResponse = new HashMap<>();
        educationResponse.put("id", id);
        educationResponse.put("result", educationService.deleteById(id));
        return new ResponseEntity<>(educationResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public AppEducation viewEducation(@PathVariable Long id) {

        return educationService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
    }

    @PutMapping("{id}")
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
}
