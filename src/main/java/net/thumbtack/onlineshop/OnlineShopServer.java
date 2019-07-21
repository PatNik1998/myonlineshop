package net.thumbtack.onlineshop;

import net.thumbtack.onlineshop.dto.UserDTO;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
