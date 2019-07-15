package net.thumbtack.onlineshop.service;


import net.thumbtack.onlineshop.dto.request.UserDTO;
import net.thumbtack.onlineshop.entities.Administrator;
import org.springframework.stereotype.Service;

@Service
public interface AdministratorService {
    UserDTO registerAdmin(UserDTO administrator) ;
    UserDTO editAdminProfile(String sessionId, UserDTO adminDTO);

}
