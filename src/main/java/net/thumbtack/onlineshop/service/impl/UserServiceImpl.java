package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.common.Validator;
import net.thumbtack.onlineshop.dao.implementations.ProductDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.UserDaoImpl;
import net.thumbtack.onlineshop.dao.ProductDao;
import net.thumbtack.onlineshop.dto.UserDTO;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.entities.User;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    private ProductDao productDao;
    private UserDaoImpl userDao;
    private Validator validator;
    private Sessions sessions;

    @Autowired
    public UserServiceImpl(ProductDaoImpl productDao, UserDaoImpl userDao,Validator validator, Sessions sessions) {
        this.productDao = productDao;
        this.userDao = userDao;
        this.validator = validator;
        this.sessions = sessions;
    }


    public UserDTO login(UserDTO userDTO) {
        User user = userDao.getUserByLoginAndPassword(userDTO.getLogin(), userDTO.getPassword());
        if (user == null) {
            userDTO.addError(new UserServiceError(UserErrorCode.WRONG_LOGIN_OR_PASSWORD, "Пользователь с введеными Вами логином и паролем не существует", "login/password"));
            validator.clearField(userDTO);
            return userDTO;
        }
        Cookie cookie = new Cookie("JAVASESSIONID", UUID.randomUUID().toString());
        sessions.addSession(cookie.getValue(), user);
        sessions.addTokens(user.getId(),cookie);

        return getDTO(user);
    }

    @Override
    public UserDTO logout(String sessionId) {
        if (sessionId == null || sessionId.isEmpty() || sessions.getUser(sessionId) == null) {
            UserDTO dto = new UserDTO();
            dto.addError(new UserServiceError(UserErrorCode.INVALID_SESSION, "Невалидная сессия", "cookie"));
            return dto;
        }
        sessions.removeSession(sessionId);
        sessions.removeTokens(sessions.getUser(sessionId).getId());
        return new UserDTO();
    }

    @Override
    public UserDTO getUser(String sessionId) {
        User user = sessions.getUser(sessionId);
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
            validator.setFieldOfClientForJson(client, userDTO);
        }
        if (user instanceof Administrator) {
            Administrator admin = (Administrator) user;
            validator.setFieldOfAdminForJson(admin, userDTO);
        }
        return userDTO;
    }
}
