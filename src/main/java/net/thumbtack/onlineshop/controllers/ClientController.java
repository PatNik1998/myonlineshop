package net.thumbtack.onlineshop.controllers;


import net.thumbtack.onlineshop.dto.request.UserDTO;
import net.thumbtack.onlineshop.service.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

//  @CookieValue(value = "JAVASESSIONID", required = false) Cookie cookieName, HttpServletResponse response
    @PostMapping(value = "/clients")
    public UserDTO registerClient(@RequestBody(required = false) UserDTO dto) {
        return clientService.registerClient(dto);
    }



}
