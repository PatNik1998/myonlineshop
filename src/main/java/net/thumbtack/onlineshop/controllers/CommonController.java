package net.thumbtack.onlineshop.controllers;

import net.thumbtack.onlineshop.dto.request.UserDTO;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.impl.AdministratorServiceImpl;
import net.thumbtack.onlineshop.service.impl.ClientServiceImpl;
import net.thumbtack.onlineshop.service.impl.UserServiceImpl;
import net.thumbtack.onlineshop.service.AdministratorService;
import net.thumbtack.onlineshop.service.ClientService;
import net.thumbtack.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CommonController {
    private AdministratorService adminService;
    private ClientService clientService;
    private UserService userService;

    @Autowired
    public CommonController(UserServiceImpl userService, AdministratorServiceImpl adminService, ClientServiceImpl clientService) {
        this.userService = userService;
        this.adminService = adminService;
        this.clientService = clientService;
    }

    @PostMapping(value = "sessions")
    public UserDTO login(@RequestBody @Valid UserDTO dto, HttpServletResponse response) {
        UserDTO dtoResponse = userService.login(dto);
        if (dtoResponse.getErrors().isEmpty()) {
            Cookie cookie = new Cookie("JAVASESSIONID", UUID.randomUUID().toString());
            Sessions.addSession(cookie.getValue(), dtoResponse.getId());
            response.addCookie(cookie);
        }
        return dtoResponse;
    }

    @DeleteMapping(value = "sessions")
    public UserDTO logout(@CookieValue(value = "JAVASESSIONID") Cookie cookie, HttpServletResponse response) {
        response.addCookie(new Cookie("JAVASESSIONID", null));
        return userService.logout(cookie.getValue());
    }

    @GetMapping(value = "/accounts")
    public UserDTO getUser(@CookieValue(value = "JAVASESSIONID") Cookie cookie) {
        return userService.getUser(cookie.getValue());
    }


}
