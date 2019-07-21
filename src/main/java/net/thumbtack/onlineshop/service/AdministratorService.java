package net.thumbtack.onlineshop.service;


import net.thumbtack.onlineshop.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdministratorService {
    UserDTO registerAdmin(UserDTO administrator) ;
    UserDTO editAdminProfile(String sessionId, UserDTO adminDTO);
  //  Administrator getBySession(String sessionId);
}
