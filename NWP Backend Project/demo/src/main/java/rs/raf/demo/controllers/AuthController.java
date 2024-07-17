package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.Permission;
import rs.raf.demo.model.User;
import rs.raf.demo.requests.LoginRequest;
import rs.raf.demo.responses.LoginResponse;
import rs.raf.demo.services.UserService;
import rs.raf.demo.utils.JwtUtil;

import java.util.*;
//import rs.edu.raf.spring_project.model.AuthReq;
//import rs.edu.raf.spring_project.model.AuthRes;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path="/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        System.out.println(loginRequest.getEmail() +" "+loginRequest.getPassword()+" JA SAM LOGIN");
        try {


            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (Exception   e){
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }

        Optional<User> usr = userService.findUserByEmail(loginRequest.getEmail());
        User user = usr.get();
        Set<String> permissions = new HashSet<>();



        if(user.isCan_delete_users()) {
            permissions.add("deleteuser");
        }
        if(user.isCan_read_users()){
            permissions.add("readuser");}
        if(user.isCan_create_users()){
            permissions.add("createuser");}
        if(user.isCan_update_users()){
            permissions.add("updateuser");}
        if(user.isCan_add_vacuum()){
            permissions.add("createvacuum");}
        if(user.isCan_discharge_vacuum()){
            permissions.add("dischargevacuum");}
        if(user.isCan_remove_vacuums()){
            permissions.add("removevacuum");}
        if(user.isCan_search_vacuum()){
            permissions.add("searchvacuum");}
        if(user.isCan_stop_vacuum()){
            permissions.add("stopvacuum");}
        if(user.isCan_start_vacuum()){
            permissions.add("startvacuum");}



        System.out.println(user.getEmail()+permissions+" mage je op");




        return ResponseEntity.ok(new LoginResponse(jwtUtil.generateToken(loginRequest.getEmail()),permissions));
    }


}
