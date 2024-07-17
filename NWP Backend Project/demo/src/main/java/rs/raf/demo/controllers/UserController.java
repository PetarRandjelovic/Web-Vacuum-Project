package rs.raf.demo.controllers;

import io.jsonwebtoken.Jws;
import org.apache.coyote.Request;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.User;
import rs.raf.demo.services.UserService;
import rs.raf.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import java.util.regex.Pattern;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public UserController(UserService userService,JwtUtil jwtUtil,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> can_read_users(@PathVariable Long id, @RequestHeader("Authorization") String headerValue){


        Optional<Optional<User>> optionalUser = Optional.ofNullable(userService.findById(id));
        if(optionalUser.isPresent()) {

            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping(path = "/del/{id}")
    public ResponseEntity<?> can_delete_users(@PathVariable Long id, @RequestHeader("Authorization") String headerValue){
        String tokenWithoutBearer = headerValue.replace("Bearer ", "");
        Claims s=jwtUtil.extractAllClaims(tokenWithoutBearer);


        if(checkPermission(s.get("permissions"),"deleteuser")){
            userService.deleteEntity(id);
            return ResponseEntity.noContent().build();
        }
        else{
            System.out.println("GRESKA");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to access this resource");
        }





    }


    @PostMapping(path="/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user, @RequestHeader("Authorization") String headerValue ){
        try {
            String tokenWithoutBearer = headerValue.replace("Bearer ", "");
            Claims s = jwtUtil.extractAllClaims(tokenWithoutBearer);

            if (!isValidEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address");
            }


            user.setPassword(passwordEncoder.encode(user.getPassword()));


            if (checkPermission(s.get("permissions"), "createuser")) {

                return ResponseEntity.ok(userService.createEntity(user));


            } else {
                System.out.println("GRESKA");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to access this resource");
            }
        }catch (DataIntegrityViolationException e) {
            System.out.println("hahahaha");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email already exists");
        }



    }


    @PutMapping(path="/editUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUser(@RequestBody User user, @RequestHeader("Authorization") String headerValue ){
        try {
        String tokenWithoutBearer = headerValue.replace("Bearer ", "");
        String secretKey = "headerValue";
        Claims s=jwtUtil.extractAllClaims(tokenWithoutBearer);

            if (!isValidEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address");
            }






        if(checkPermission(s.get("permissions"),"updateuser")){

         //   System.out.println(user.getPassword()+" PASSWORD HIHI");
           // user.setPassword(passwordEncoder.encode("ss"));

     //       user.setPassword(passwordEncoder.encode("ss"));
            return ResponseEntity.ok(userService.createEntity(user));
        }
        else{

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to access this resource");
        }}catch (DataIntegrityViolationException e) {


            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email already exists");
        }




    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers( @RequestHeader("Authorization") String headerValue){




        String tokenWithoutBearer = headerValue.replace("Bearer ", "");
        Claims s=jwtUtil.extractAllClaims(tokenWithoutBearer);


        System.out.println(s+" READ USERS metoda");
        if(checkPermission(s.get("permissions"),"readuser")){
            return userService.findAll();
        }
        else{
            System.out.println("GRESKA");
            throw new ForbiddenException("You do not have permission to access this resource");
        }



    };


    public boolean checkPermission(Object object,String permission){


        return (object.toString().contains(permission));


    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }


    class ForbiddenException extends RuntimeException {
        public ForbiddenException(String message) {
            super(message);
        }
    }
}
