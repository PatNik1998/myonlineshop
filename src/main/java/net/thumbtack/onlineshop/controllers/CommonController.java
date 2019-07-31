package net.thumbtack.onlineshop.controllers;

import net.thumbtack.onlineshop.dto.UserDTO;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.impl.AdministratorServiceImpl;
import net.thumbtack.onlineshop.service.impl.UserServiceImpl;
import net.thumbtack.onlineshop.service.AdministratorService;
import net.thumbtack.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CommonController {
    private AdministratorService adminService;
//    private ClientService clientService;
    private UserService userService;
    private Sessions sessions;

@Autowired
    public CommonController(UserServiceImpl userService, AdministratorServiceImpl adminService, Sessions sessions) {
        this.userService = userService;
        this.adminService = adminService;
        this.sessions=sessions;
      //  this.clientService = clientService;
    }

    @PostMapping(value = "sessions")
    public UserDTO login(@RequestBody UserDTO dto, HttpServletResponse response) {

        UserDTO response1 =  userService.login(dto);
        if(dto.getErrors().size() == 0){
            Cookie cookie = new Cookie("JAVASESSIONID", UUID.randomUUID().toString());
            sessions.addSession(cookie.getValue(), response1.getId());
            sessions.addTokens(response1.getId(),cookie);
            response.addCookie(cookie);
        }
        return response1;

    }

    @DeleteMapping(value = "sessions")
    public UserDTO logout(@CookieValue(value = "JAVASESSIONID") Cookie cookie, HttpServletResponse response) {
        return userService.logout(cookie.getValue());
    }

    @GetMapping(value = "/accounts")
    public UserDTO getUser(@CookieValue(value = "JAVASESSIONID") Cookie cookie) {
        return userService.getUser(cookie.getValue());
    }


}
