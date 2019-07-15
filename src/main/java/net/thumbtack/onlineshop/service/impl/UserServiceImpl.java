package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.UserDaoImpl;
import net.thumbtack.onlineshop.dao.ProductDao;
import net.thumbtack.onlineshop.dto.request.UserDTO;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.entities.User;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static net.thumbtack.onlineshop.common.Validator.clearField;


@Service
public class UserServiceImpl implements UserService {
    private ProductDao productDao;
    private UserDaoImpl userDao;

    @Autowired
    public UserServiceImpl(ProductDaoImpl productDao, UserDaoImpl userDao) {
        this.productDao = productDao;
        this.userDao = userDao;
    }

    @Override
    public UserDTO login(UserDTO userDTO) {
        User user = userDao.getUserByLoginAndPassword(userDTO.getLogin(), userDTO.getPassword());
        if (user == null) {
            userDTO.addError(new UserServiceError(UserErrorCode.WRONG_LOGIN_OR_PASSWORD, "Пользователь с введеными Вами логином и паролем не существует", "login/password"));
            clearField(userDTO);
            return userDTO;
        }
        return getDTO(user);
    }

    @Override
    public UserDTO logout(String sessionId) {
        if (sessionId == null || sessionId.isEmpty() || Sessions.getUser(sessionId) == null) {
            UserDTO dto = new UserDTO();
            dto.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Невалидная сессия", "cookie"));
            return dto;
        }
        Sessions.removeSession(sessionId);
        return new UserDTO();
    }

    @Override
    public UserDTO getUser(String sessionId) {
        User user = Sessions.getUser(sessionId);
        UserDTO userDTO = new UserDTO();
        if (user == null) {
            userDTO.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Невалидная сессия", "cookie"));
            return userDTO;
        }
        return getDTO(user);
    }

    public UserDTO getDTO(User user) {
        UserDTO userDTO = new UserDTO();
        if (user instanceof Client) {
            Client client = (Client) user;
            userDTO.setFieldOfClientForJson(client);
        }
        if (user instanceof Administrator) {
            Administrator admin = (Administrator) user;
            userDTO.setFieldOfAdminForJson(admin);
        }
        return userDTO;
    }
}
