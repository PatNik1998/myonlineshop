package net.thumbtack.onlineshop;

import net.thumbtack.onlineshop.dao.implementations.UserDaoImpl;
import net.thumbtack.onlineshop.dto.UserDTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OnlineShopServer{

    public static void main(String[] args) {
       ApplicationContext context = SpringApplication.run(OnlineShopServer.class);
    }

}
