package rs.raf.demo.controllers;

import io.jsonwebtoken.Claims;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.Status;
import rs.raf.demo.model.User;
import rs.raf.demo.model.Vacuum;
import rs.raf.demo.responses.VacuumSearchResponse;
import rs.raf.demo.services.UserService;
import rs.raf.demo.services.VacuumService;
import rs.raf.demo.utils.JwtUtil;
import java.time.ZoneId;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.ZoneId;
@CrossOrigin
@RestController
@RequestMapping("/api/vacuum")
public class VacuumController {


    private final UserService userService;
    private final VacuumService vacuumService;
    private final JwtUtil jwtUtil;
    public VacuumController(VacuumService vacuumService, UserService userService,JwtUtil jwtUtil) {
        this.vacuumService = vacuumService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vacuum> getAllVacuumes(  @RequestHeader("Authorization") String headerValue){

        ResponseEntity<?> responseEntity=fensiMetoda(headerValue,"createvacuum");

        String tokenWithoutBearer = headerValue.replace("Bearer ", "");
        Claims s = jwtUtil.extractAllClaims(tokenWithoutBearer);

        System.out.println(s.getSubject());

       return vacuumService.findAllwithEmail(s.getSubject());

/*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication);

        User user = userService.findUserByEmail(authentication.getName()).get();

        System.out.println(user+" xd");

        Long id = user.getId();*/
       // Long s = Long.valueOf(1);

  //      return vacuumService.findAll(s);
    }


    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getbyID(@PathVariable("id") Long id){
        Optional<Vacuum> vacuum = (vacuumService.findbyID(id));
        if(vacuum.isPresent()) {
            return ResponseEntity.ok(vacuum.get());
        }
        else return ResponseEntity.notFound().build();
    }

    @PostMapping(path="/addVacuum", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createVacuum(@RequestBody Vacuum vacuum, @RequestHeader("Authorization") String headerValue ){

            ResponseEntity<?> responseEntity=fensiMetoda(headerValue,"createvacuum");

    if(responseEntity==null) {
        String tokenWithoutBearer = headerValue.replace("Bearer ", "");
        Claims s = jwtUtil.extractAllClaims(tokenWithoutBearer);

        Optional<User> user=userService.findUserByEmail(s.getSubject());
        User h=user.get();

        vacuum.setUser(h);

        vacuum.setActive(false);

        vacuum.setStatus(Status.OFF);

        LocalDate today = LocalDate.now();

        // Convert LocalDate to ZonedDateTime
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = today.atStartOfDay(zoneId);

        // Convert ZonedDateTime to Date
        Date date = Date.from(zonedDateTime.toInstant());
        vacuum.setDate(today);


        return ResponseEntity.ok(vacuumService.createEntity(vacuum));
    }else {
        return responseEntity;
    }


    }
    @PutMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchVacuum(@RequestBody VacuumSearchResponse vacuumSearchResponse, @RequestHeader("Authorization") String headerValue){



        ResponseEntity<?> responseEntity=fensiMetoda(headerValue,"searchvacuum");

        if(responseEntity==null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(authentication.getName()).get();

            String email = user.getEmail();
            String vacuumName = vacuumSearchResponse.getVacuumName();
            List<Status> status = vacuumSearchResponse.getStatus();
            LocalDate dateFrom = vacuumSearchResponse.getDateFrom();
            LocalDate dateTo = vacuumSearchResponse.getDateTo();

            System.out.println(status + " search");


            return new ResponseEntity<>(vacuumService.search(email, status, vacuumName, dateFrom, dateTo), HttpStatus.OK);
        }else{
            return responseEntity;
        }
    }


    @PutMapping(value = "/start/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Start(@PathVariable("id") Long id, @RequestHeader("Authorization") String headerValue){
        return new ResponseEntity<>(vacuumService.start(id), HttpStatus.OK);
    }
    @PutMapping(value = "/remove/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Remove(@PathVariable("id") Long id, @RequestHeader("Authorization") String headerValue){
        System.out.println("REMOVE "+id);

        return new ResponseEntity<>(vacuumService.remove(id), HttpStatus.OK);
    }

    @PutMapping(value = "/stop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Stop(@PathVariable("id") Long id, @RequestHeader("Authorization") String headerValue){
        ResponseEntity<?> responseEntity=fensiMetoda(headerValue,"stopvacuum");

        if(responseEntity==null) {

        return new ResponseEntity<>(vacuumService.stop(id), HttpStatus.OK);

        }
        else {

            return responseEntity;
        }
    }

    @PutMapping(value = "/restart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Discharge(@PathVariable("id") Long id, @RequestHeader("Authorization") String headerValue){

        ResponseEntity<?> responseEntity=fensiMetoda(headerValue,"dischargevacuum");

        if(responseEntity==null) {

            return new ResponseEntity<>(vacuumService.discharge(id), HttpStatus.OK);   }
        else {

            return responseEntity;
        }
    }

    public boolean checkPermission(Object object,String permission){


        return (object.toString().contains(permission));


    }
    @GetMapping(value = "/scheduleStart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ScheduleStart(@PathVariable("id") Long id, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") Date date, @RequestHeader("Authorization") String headerValue){

        ResponseEntity<?> responseEntity=fensiMetoda(headerValue,"startvacuum");

        if(responseEntity==null) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName()).get();

        return new ResponseEntity<>(vacuumService.scheduleStart(id,user.getId(),date), HttpStatus.OK);}
        else {
            return responseEntity;
        }
    }

    @GetMapping(value = "/scheduleStop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ScheduleStop(@PathVariable("id") Long id, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") Date date, @RequestHeader("Authorization") String headerValue) {

        ResponseEntity<?> responseEntity=fensiMetoda(headerValue,"stopvacuum");

        if(responseEntity==null) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName()).get();

        return new ResponseEntity<>(vacuumService.scheduleStop(id,user.getId(),date), HttpStatus.OK);}
        else {
            return responseEntity;
        }

    }

    @GetMapping(value = "/scheduleRestart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ScheduleRestart(@PathVariable("id") Long id, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") Date date, @RequestHeader("Authorization") String headerValue){

        ResponseEntity<?> responseEntity=fensiMetoda(headerValue,"startvacuum");

        if(responseEntity==null) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName()).get();

        return new ResponseEntity<>(vacuumService.scheduleRestart(id,user.getId(),date), HttpStatus.OK);}
        else {
            return responseEntity;
        }
    }



    public  ResponseEntity<?> fensiMetoda(String headerValue,String permission){



        try {
            String tokenWithoutBearer = headerValue.replace("Bearer ", "");
            Claims s = jwtUtil.extractAllClaims(tokenWithoutBearer);

            System.out.println(s.get("permissions")+" EJALO");

            if (!checkPermission(s.get("permissions"), permission)) {

                return    ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to access this resource");

            }


        }catch (DataIntegrityViolationException e) {
           return   ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email already exists");
        }
return null;

    }
}
