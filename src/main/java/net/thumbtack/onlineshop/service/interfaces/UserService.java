package net.thumbtack.onlineshop.service.interfaces;

import net.thumbtack.onlineshop.dto.request.UserDTO;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    UserDTO login(UserDTO userDTO);
    UserDTO logout(String sessionId);
    UserDTO getUser(String sessionId);
  }