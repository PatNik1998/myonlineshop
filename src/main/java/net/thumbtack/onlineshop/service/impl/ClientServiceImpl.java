package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.common.Validator;
import net.thumbtack.onlineshop.dao.implementations.AdministratorDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ClientDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.dao.AdministratorDao;
import net.thumbtack.onlineshop.dao.ClientDao;
import net.thumbtack.onlineshop.dao.ProductDao;

import net.thumbtack.onlineshop.dto.ProductDTOWithNameCategories;
import net.thumbtack.onlineshop.dto.UserDTO;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.ClientService;
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

            clientDao.add(client);

            dto.setId(client.getId());
            dto.setPassword(null);
            dto.setLogin(null);
            dto.setDeposit(0);
            return dto;
        }
        return dto;
    }
    public List<UserDTO> getClients(String sessionId) {
        Administrator admin = (Administrator) sessions.getUser(sessionId);
        if(admin == null){
            //userDTO.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "client"));
            return null;//TODO: сделать бы тут дто чтоб ошибку вставить
        }
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
        if(client == null){
            userDTO.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "client"));
            return userDTO;
        }
        validator.editClientValidate(userDTO);
        if(userDTO.getErrors().isEmpty()){
            client.setPassword(userDTO.getNewPassword());
            client.setFirstName(userDTO.getFirstName());
            client.setLastName(userDTO.getLastName());
            client.setPatronymic(userDTO.getPatronymic());
            client.setEmail(userDTO.getEmail());
            client.setAddress(userDTO.getAddress());
            client.setPhone(userDTO.getPhone());
            clientDao.update(client);

            userDTO.setNewPassword(null);
            userDTO.setOldPassword(null);

            return  userDTO;
        }
        return userDTO;
    }



    public UserDTO addDeposit(String sessionId, UserDTO userDTO) {
        Client client = (Client) sessions.getUser(sessionId);
        if(client == null){
            userDTO.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "client"));
            return userDTO;
        }
        int numberDeposit  = userDTO.getDeposit();
        client.setDeposit(client.getDeposit() + numberDeposit);
        clientDao.update(client);
        userDTO.setDeposit(client.getDeposit());
        userDTO.setFirstName(client.getFirstName());
        userDTO.setLastName(client.getLastName());
        userDTO.setPatronymic(client.getPatronymic());
        userDTO.setEmail(client.getEmail());
        userDTO.setAddress(client.getAddress());
        userDTO.setPhone(client.getPhone());
        return  userDTO;
}

    public UserDTO getDeposit(String sessionId) {
        Client client = (Client) sessions.getUser(sessionId);
        UserDTO userDTO = new UserDTO();
        if(client == null){
            userDTO.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Wrong login or session!", "client"));
            return userDTO;
        }
        validator.setFieldOfClientForJson(client,userDTO);
        return userDTO;
    }




}
