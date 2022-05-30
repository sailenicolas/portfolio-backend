package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.domain.AppSoftSkill;
import ar.com.saile.demojwt.domain.AppUser;
import ar.com.saile.demojwt.exceptions.BindingResultException;
import ar.com.saile.demojwt.exceptions.NotAuthorizedException;
import ar.com.saile.demojwt.exceptions.RecordNotFoundException;
import ar.com.saile.demojwt.service.SoftSkillService;
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
@RequestMapping("/api/v1/user/softskills")
@RequiredArgsConstructor
@Slf4j
public class SoftSkillController {

    private final SoftSkillService softSkillService;

    private final UserService userService;

    @PutMapping("{id}")
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

    @PostMapping("/")
    public AppSoftSkill createSoftSkills(@Valid @RequestBody final AppSoftSkill appSoftSkill, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        if (!appUser.getSoftSkills().contains(appSoftSkill)) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        appSoftSkill.setUserApp(appUser);
        return softSkillService.saveSoftSkills(appSoftSkill);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> deleteSoftSkills(@PathVariable Long id, HttpServletRequest request) {

        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        AppSoftSkill appSoftSkill = softSkillService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT_FOUND"));
        if (!appUser.getSoftSkills().contains(appSoftSkill)) {
            throw new NotAuthorizedException("NOT_AUTHORIZED");
        }
        Map<String, Object> softSkillResponse = new HashMap<>();
        softSkillResponse.put("status", id);
        softSkillResponse.put("result", softSkillService.deleteById(id));
        return new ResponseEntity<>(softSkillResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public AppSoftSkill viewSoftSkills(@PathVariable Long id) {

        return softSkillService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
    }
}
