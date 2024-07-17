package rs.raf.demo.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.demo.model.ErrorMsg;
import rs.raf.demo.model.User;
import rs.raf.demo.services.ErrorMsgService;
import rs.raf.demo.services.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/errors")
public class ErrorMsgController {

    private final ErrorMsgService errorMsgService;
    private final UserService userService;

    public ErrorMsgController(ErrorMsgService errorMsgService, UserService userService) {
        this.errorMsgService = errorMsgService;
        this.userService = userService;
    }

    @GetMapping(value = "/get")
    public List<ErrorMsg> getAllMachines(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName()).get();


        System.out.println(user+" LMAO USER");

        return errorMsgService.findAll(user.getId());
    }

}
