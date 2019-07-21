package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.common.Validator;
import net.thumbtack.onlineshop.dao.implementations.AdministratorDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ClientDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.dao.interfaces.AdministratorDao;
import net.thumbtack.onlineshop.dao.interfaces.ClientDao;
import net.thumbtack.onlineshop.dao.interfaces.ProductDao;
import net.thumbtack.onlineshop.dto.response.BuyProductsDTOResponse;
import net.thumbtack.onlineshop.dto.request.ProductDTOWithNameCategories;
import net.thumbtack.onlineshop.dto.request.UserDTO;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ClientServiceImpl implements ClientService {
    private AdministratorDao adminDao;
    private ClientDao clientDao;
    private ProductDao productDao;
    private Validator validator;
    private Sessions sessions;

@Autowired
    public ClientServiceImpl(ClientDaoImpl clientDao, ProductDaoImpl productDao, AdministratorDaoImpl adminDao,Validator validator, Sessions sessions) {
        this.clientDao = clientDao;
        this.productDao = productDao;
        this.adminDao = adminDao;
        this.validator = validator;
        this.sessions = sessions;
    }

    public UserDTO registerClient(UserDTO dto) {
       // dto.registerClientValidate();
        if (dto.getErrors().isEmpty()) {
            Client client = new Client();
            client.setPhone(dto.getPhone());
            client.setAddress(dto.getAddress());
            client.setEmail(dto.getEmail());
            client.setFirstName(dto.getFirstName());
            client.setLastName(dto.getLastName());
            client.setPatronymic(dto.getPatronymic());
            client.setLogin(dto.getLogin());
            client.setPassword(dto.getPassword());
            try {
                clientDao.add(client);
            }
            catch (Exception ex) {
                dto.addError(new UserServiceError(UserErrorCode.USER_ALREADY_EXISTS, "Пользователь с логином " + dto.getLogin() + " уже существует", "login"));
             //   dto.clearField();
                return dto;
            }
            dto.setPassword(null);
            dto.setLogin(null);
            return dto;
        }
        return dto;
    }
    public List<UserDTO> getClients(String sessionId) {
        List<UserDTO> result = new ArrayList<>();
        List<Client> clients = clientDao.getAll();
        for (Client client : clients) {
            UserDTO userDTO = new UserDTO();
            validator.setFieldOfClientForJson(client, userDTO);
            userDTO.setUserType(client.getClass().getSimpleName());
            result.add(userDTO);
        }
        return result;
    }

    public UserDTO editClientProfile(String sessionId, UserDTO userDTO) {
        Client client = (Client) sessions.getUser(sessionId);
    }



    public UserDTO addDeposit(String sessionId, UserDTO userDTO) {
        return null;
    }

    public UserDTO getDeposit(String sessionId) {
        return null;
    }

    public ProductDTOWithNameCategories buyProduct(String sessionId, ProductDTOWithNameCategories productDTO) {
        return null;
    }

    public List<ProductDTOWithNameCategories> addProductToCart(String sessionId, ProductDTOWithNameCategories ProductDTO) {
        return null;
    }

    public String deleteProductFromCart(String sessionId, int productId) {
        return null;
    }

    public List<ProductDTOWithNameCategories> editProductOfCart(String sessionId, ProductDTOWithNameCategories ProductDTO) {
        return null;
    }

    public List<ProductDTOWithNameCategories> getCart(String sessionId) {
        return null;
    }

    public BuyProductsDTOResponse buyProducts(String sessionId, List<ProductDTOWithNameCategories> products) {
        return null;
    }
}
