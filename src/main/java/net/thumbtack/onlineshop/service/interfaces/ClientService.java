package net.thumbtack.onlineshop.service.interfaces;

import net.thumbtack.onlineshop.dto.response.BuyProductsDTOResponse;
import net.thumbtack.onlineshop.dto.request.ProductDTOWithNameCategories;
import net.thumbtack.onlineshop.dto.request.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    UserDTO registerClient(UserDTO client);
    List<UserDTO> getClients(String sessionId);
}
