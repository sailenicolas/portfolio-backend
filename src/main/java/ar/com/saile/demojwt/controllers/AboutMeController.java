package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.domain.AppAboutMe;
import ar.com.saile.demojwt.domain.AppUser;
import ar.com.saile.demojwt.exceptions.BindingResultException;
import ar.com.saile.demojwt.exceptions.NotAuthorizedException;
import ar.com.saile.demojwt.exceptions.RecordNotFoundException;
import ar.com.saile.demojwt.service.AboutMeService;
import ar.com.saile.demojwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user/aboutMe")
@RequiredArgsConstructor
@Slf4j
public class AboutMeController {

    private final UserService userService;

    private final AboutMeService aboutMeService;

    @PutMapping("{id}")
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

    @GetMapping("{id}")
    public AppAboutMe viewAboutMe(HttpServletRequest request) {

        AppUser details = userService.fetchAuthenticatedUserFromRequest(request);
        return aboutMeService.findById(details.getId()).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
    }
}
