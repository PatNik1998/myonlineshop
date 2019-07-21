package net.thumbtack.onlineshop;

import net.thumbtack.onlineshop.common.Validator;
import net.thumbtack.onlineshop.controllers.AdministratorController;
import net.thumbtack.onlineshop.dao.implementations.AdministratorDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.UserDaoImpl;
import net.thumbtack.onlineshop.dao.interfaces.AdministratorDao;
import net.thumbtack.onlineshop.dto.request.UserDTO;
import net.thumbtack.onlineshop.entities.User;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.impl.AdministratorServiceImpl;
import net.thumbtack.onlineshop.service.impl.UserServiceImpl;
import net.thumbtack.onlineshop.service.interfaces.AdministratorService;
import net.thumbtack.onlineshop.service.interfaces.UserService;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.Cookie;

@SpringBootApplication
public class OnlineShopServer{

    public static void main(String[] args) {
     //  ApplicationContext context = SpringApplication.run(OnlineShopServer.class);

    }

    public static UserDTO createUserDto(String firstName, String lastName, String patronymic, String position){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setPatronymic(patronymic);
        userDTO.setPosition(position);


        return userDTO;

    }
}
