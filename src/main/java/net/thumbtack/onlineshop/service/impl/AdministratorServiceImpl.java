package net.thumbtack.onlineshop.service.impl;

import net.thumbtack.onlineshop.common.Validator;
import net.thumbtack.onlineshop.dao.AdministratorDao;
import net.thumbtack.onlineshop.dao.implementations.AdministratorDaoImpl;
import net.thumbtack.onlineshop.dto.request.UserDTO;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.service.Sessions;
import net.thumbtack.onlineshop.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    private AdministratorDao adminDao;

    @Autowired
    public AdministratorServiceImpl(AdministratorDaoImpl adminDao) {
        this.adminDao = adminDao;
    }

    public UserDTO registerAdmin(UserDTO dto) {
        Validator.registerAdminValidate(dto);
        if (dto.getErrors().isEmpty()) {
            Administrator admin = new Administrator();
            admin.setFirstName(dto.getFirstName());
            admin.setLastName(dto.getLastName());
            admin.setPatronymic(dto.getPatronymic());
            admin.setLogin(dto.getLogin());
            admin.setPassword(dto.getPassword());
            admin.setPosition(dto.getPosition());
            adminDao.add(admin);

            dto.setId(admin.getId());
            dto.setPassword(null);
            dto.setLogin(null);
            return dto;
        }
        return  dto;
    }

    public UserDTO editAdminProfile(String sessionId, UserDTO adminDTO) {
        Administrator admin = (Administrator) Sessions.getUser(sessionId);

        Validator.editAdminValidate(adminDTO);
        if (adminDTO.getErrors().isEmpty()) {
            admin.setPassword(adminDTO.getNewPassword());
            admin.setFirstName(adminDTO.getFirstName());
            admin.setLastName(adminDTO.getLastName());
            admin.setPatronymic(adminDTO.getPatronymic());
            admin.setPosition(adminDTO.getPosition());
            adminDao.update(admin);

            adminDTO.setId(admin.getId());
            adminDTO.setOldPassword(null);
            adminDTO.setNewPassword(null);
            return adminDTO;
        }
        return adminDTO;
    }
}
