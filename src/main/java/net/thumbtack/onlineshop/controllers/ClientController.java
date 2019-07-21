package net.thumbtack.onlineshop.controllers;


import net.thumbtack.onlineshop.dto.UserDTO;
import net.thumbtack.onlineshop.service.impl.ClientServiceImpl;
import net.thumbtack.onlineshop.service.ClientService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

public class ClientController {
    private ClientService clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

//  @CookieValue(value = "JAVASESSIONID", required = false) Cookie cookieName, HttpServletResponse response
    @PostMapping(value = "/clients")
    public UserDTO registerClient(@RequestBody(required = false) UserDTO dto) {
        return clientService.registerClient(dto);
    }

    @PutMapping(value = "/clients")
    public UserDTO editClient(@RequestBody UserDTO dto,@RequestHeader @CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie) {
        UserDTO response = clientService.editClientProfile(dto.getCookie().getValue(), dto);
        return response;
        }

    @PutMapping(value = "/deposits")
    public UserDTO deposit(@RequestBody UserDTO dto, @CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie){
        UserDTO response = clientService.addDeposit(cookie.getValue(),dto);
        return response;
    }

}
