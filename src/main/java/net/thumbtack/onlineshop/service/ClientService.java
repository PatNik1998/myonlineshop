package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    UserDTO registerClient(UserDTO client);
    List<UserDTO> getClients(String sessionId);
    UserDTO editClientProfile(String sessionId, UserDTO userDTO);
   // UserDTO addDeposit(String sessionId, UserDTO userDTO);
}
