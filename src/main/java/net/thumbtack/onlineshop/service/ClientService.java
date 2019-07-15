package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dto.response.BuyProductsDTOResponse;
import net.thumbtack.onlineshop.dto.request.ProductDTOWithNameCategories;
import net.thumbtack.onlineshop.dto.request.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
   // UserDTO registerClient(UserDTO client);
    UserDTO editClientProfile(String sessionId, UserDTO userDTO);
    List<UserDTO> getClients(String sessionId);
    UserDTO addDeposit(String sessionId, UserDTO userDTO);
    UserDTO getDeposit(String sessionId);

    ProductDTOWithNameCategories buyProduct(String sessionId, ProductDTOWithNameCategories productDTO);
    List<ProductDTOWithNameCategories> addProductToCart(String sessionId, ProductDTOWithNameCategories ProductDTO);
    String deleteProductFromCart(String sessionId, int productId);
    List<ProductDTOWithNameCategories> editProductOfCart(String sessionId, ProductDTOWithNameCategories ProductDTO);
    List<ProductDTOWithNameCategories> getCart(String sessionId);
    BuyProductsDTOResponse buyProducts(String sessionId, List<ProductDTOWithNameCategories> products);
}
