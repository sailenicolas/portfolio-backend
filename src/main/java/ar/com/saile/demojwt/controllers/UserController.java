package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.domain.AppRole;
import ar.com.saile.demojwt.domain.AppUser;
import ar.com.saile.demojwt.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public AppUser addUser(@RequestBody AppUser appUser){
       return  userService.saveUser(appUser);
    }
    @PostMapping("/role")
    public ResponseEntity<AppRole> addRole(@RequestBody AppRole appRole){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("").toUriString());

        return ResponseEntity.created(uri).body(userService.saveRole(appRole)) ;
    }
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers(){
       return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/roleToUser")
    public ResponseEntity<? >RoleToUser(@RequestBody RoleToUser form){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("").toUriString());
        userService.addRoleToUser(form.getRoleNane(), form.getUserName());
        return ResponseEntity.ok().build();
    }
}
@Data
class RoleToUser{
    private String roleNane;
    private String userName;
}