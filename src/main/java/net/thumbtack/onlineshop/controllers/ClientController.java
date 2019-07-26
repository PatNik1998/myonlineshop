package net.thumbtack.onlineshop.controllers;


import net.thumbtack.onlineshop.dto.ProductDto;
import net.thumbtack.onlineshop.dto.UserDTO;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.entities.User;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.impl.CartServiceImpl;
import net.thumbtack.onlineshop.service.impl.ClientServiceImpl;
import net.thumbtack.onlineshop.service.ClientService;
import net.thumbtack.onlineshop.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.nio.cs.US_ASCII;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ClientController {
    private ClientService clientService;
    private Sessions sessions;
    private CartService cartService;
@Autowired
    public ClientController(ClientServiceImpl clientService, Sessions sessions, CartServiceImpl cartService) {
        this.clientService = clientService;
        this.sessions = sessions;
        this.cartService = cartService;
    }

//  @CookieValue(value = "JAVASESSIONID", required = false) Cookie cookieName, HttpServletResponse response
    @PostMapping(value = "/clients")
    public UserDTO registerClient(@RequestBody(required = false) UserDTO dto, HttpServletResponse response) {
        UserDTO response1 = clientService.registerClient(dto);
        Cookie cookie = new Cookie("JAVASESSIONID",UUID.randomUUID().toString());
        response.addCookie(cookie);
        System.out.println(" asd "+response1.getId());
        sessions.addSession(cookie.getValue(),response1.getId());
        return response1;
    }

    @PutMapping(value = "/clients")
    public UserDTO editClient(@RequestBody UserDTO dto,@RequestHeader @CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie) {
        UserDTO response = clientService.editClientProfile(dto.getCookie().getValue(), dto);
        return response;
        }

    @PutMapping(value = "/deposits")
    public UserDTO deposit(@RequestBody UserDTO dto, @CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie){
      //  UserDTO response = clientService.addDeposit(cookie.getValue(),dto);
      //  return response;
        return null;
    }

//    @GetMapping(value = "/deposits")
//    public UserDTO getDeposit(@CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie){
//        User user = sessions.getUser(cookie.getValue());
//        return clientService.getDeposit((Client) user);
//    }

    @PostMapping(value = "/purchases")
    public ProductDto buyProduct(@CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie, @RequestBody ProductDto productDto){
      User user = sessions.getUser(cookie.getValue());
      return cartService.buyProduct((Client) user, productDto);
    }

    @PostMapping(value = "/baskets")
    public List<ProductDto> addProductToCart(@CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie, @RequestBody ProductDto productDto){
     User user = sessions.getUser(cookie.getValue());
     return cartService.addProductToCart((Client) user, productDto);
    }

    @PutMapping(value = "/baskets")
    public List<ProductDto> editProductFromCart(@CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie, @RequestBody ProductDto productDto){
    User user = sessions.getUser(cookie.getValue());
    return cartService.editProductFromCart((Client) user,productDto);
    }

    @GetMapping(value = "/baskets")
    public List<ProductDto> getCart(@CookieValue(value = "JAVASESSIONID", required = false) Cookie cookie){
       User user = sessions.getUser(cookie.getValue());
        return cartService.getCart((Client) user);
    }





}
