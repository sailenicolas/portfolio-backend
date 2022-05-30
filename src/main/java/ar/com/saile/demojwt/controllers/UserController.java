package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.domain.AppRole;
import ar.com.saile.demojwt.domain.AppUser;
import ar.com.saile.demojwt.exceptions.RecordNotFoundException;
import ar.com.saile.demojwt.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public AppUser addUser(@RequestBody AppUser appUser) {

        return userService.saveUser(appUser);
    }

    @GetMapping("/me")
    @Transactional
    public AppUser getAppUser(HttpServletRequest request) {

        AppUser appUser = userService.fetchAuthenticatedUserFromRequest(request);
        Optional<AppUser> appUserUs = userService.findByUsername(appUser);
        return appUserUs.orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
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

}

@Data
class RoleToUser {

    private String roleNane;

    private String userName;
}