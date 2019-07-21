package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.common.Validator;
import net.thumbtack.onlineshop.dao.implementations.AdministratorDaoImpl;
import net.thumbtack.onlineshop.dao.interfaces.*;
import net.thumbtack.onlineshop.dto.request.UserDTO;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.interfaces.AdministratorService;
import net.thumbtack.onlineshop.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.UUID;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    private AdministratorDao adminDao;
    private Validator validator;
    private Sessions sessions;
    private UserService userService;

    @Autowired
    public AdministratorServiceImpl(AdministratorDaoImpl adminDao,Validator validator, Sessions sessions) {
        this.adminDao = adminDao;
        this.validator = validator;
        this.sessions = sessions;

    }


    public UserDTO registerAdmin(UserDTO dto) {
        validator.registerAdminValidate(dto);
        if (dto.getErrors().isEmpty()) {
            Administrator admin = new Administrator();
            admin.setFirstName(dto.getFirstName());
            admin.setLastName(dto.getLastName());
            admin.setPatronymic(dto.getPatronymic());
            admin.setLogin(dto.getLogin());
            admin.setPassword(dto.getPassword());
            admin.setPosition(dto.getPosition());
            adminDao.add(admin);
            Cookie cookie = new Cookie("JAVASESSIONID", UUID.randomUUID().toString());
            sessions.addSession(cookie.getValue(), admin);
            sessions.addTokens(admin.getId(),cookie);
            dto.setId(admin.getId());
            dto.setPassword(null);
            dto.setLogin(null);
            return dto;
        }
        return  dto;
    }

    public UserDTO editAdminProfile(String sessionId, UserDTO adminDTO) {
        Administrator admin = (Administrator) sessions.getUser(sessionId);

        validator.editAdminValidate(adminDTO);
        if (adminDTO.getErrors().isEmpty()) {
            admin.setPassword(adminDTO.getNewPassword());
            admin.setFirstName(adminDTO.getFirstName());
            admin.setLastName(adminDTO.getLastName());
            admin.setPatronymic(adminDTO.getPatronymic());
            admin.setPosition(adminDTO.getPosition());
            adminDTO.setCookie(null);
            adminDao.update(admin);

            adminDTO.setId(admin.getId());
            adminDTO.setOldPassword(null);
            adminDTO.setNewPassword(null);
            return adminDTO;
        }
        return adminDTO;
    }


}
