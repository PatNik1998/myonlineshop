package net.thumbtack.onlineshop.common;

import net.thumbtack.onlineshop.dto.CategoryDto;
import net.thumbtack.onlineshop.dto.ProductDto;
import net.thumbtack.onlineshop.dto.UserDTO;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class Validator {


        public int length = 20;

        public int minPasswordLength = 8;

        public void registerAdminValidate(UserDTO userDto){
            validateFirstName(userDto);
            validateLastName(userDto);
            validatePatronymic(userDto);
            validateLogin(userDto);
            validatePassword(userDto);
            validatePosition(userDto);
            if(!userDto.getErrors().isEmpty()){
                clearField(userDto);
            }
        }

        public void registerClientValidate(UserDTO userDto){
            validateFirstName(userDto);
            validateLastName(userDto);
            validatePatronymic(userDto);
            validateEmail(userDto);
            validateAddress(userDto);
            validateLogin(userDto);
            validatePassword(userDto);
            if(!userDto.getErrors().isEmpty()){
                clearField(userDto);
            }

        }

        public void editAdminValidate(UserDTO userDto) {
            validateFirstName(userDto);
            validateLastName(userDto);
            validatePatronymic(userDto);
            validateNewPassword(userDto);
            validatePosition(userDto);
            if(!userDto.getErrors().isEmpty()){
                clearField(userDto);
            }

        }

        public void editClientValidate(UserDTO userDto){
            validateFirstName(userDto);
            validateLastName(userDto);
            validatePatronymic(userDto);
            validateNewPassword(userDto);
            validateEmail(userDto);
            validateAddress(userDto);
            if(!userDto.getErrors().isEmpty()){
                clearField(userDto);
            }
        }


        public boolean validateName(String name) {
            String regexForFIO = "^([А-Яа-я]+(-[а-я]+)* ?)+$";
            Pattern pattern = Pattern.compile(regexForFIO);
            Matcher matcher = pattern.matcher(name);
            return !matcher.matches();
        }

        public  void clearField(UserDTO userDto) {
            userDto.setFirstName(null);
            userDto.setLastName(null);
            userDto.setPatronymic(null);
            userDto.setLogin(null);
            userDto.setPassword(null);
            userDto.setPosition(null);
            userDto.setAddress(null);
            userDto.setPhone(null);
            userDto.setEmail(null);
        }

        public void validateAddress(UserDTO userDto){
            if (userDto.getAddress() == null || userDto.getAddress().isEmpty()) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_ADDRESS, "Поле адрес не может быть пустым", "address"));
            }
        }

        public void validateFirstName(UserDTO userDto){
            if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty()) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_FIRST_NAME, "Поле имя не может быть пустым", "firstName"));
                return;
            }
            if (userDto.getFirstName().length() > length) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_FIRST_NAME, "Максимальная длина имени " + length + " символов", "firstName"));
                return;
            }
            if (validateName(userDto.getFirstName())) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_FIRST_NAME, "Имя может содержать только русские буквы", "firstName"));
                return;
            }
        }

        public void validateLastName(UserDTO userDto){
            if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_LAST_NAME, "Поле фамилия не может быть пустым", "lastName"));
                return;
            }
            if (userDto.getLastName().length() > length) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_LAST_NAME, "Максимальная длина фамилии " + length + " символов", "lastName"));
                return;
            }
            if (validateName(userDto.getLastName())) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_LAST_NAME, "Фамилия может содержать только русские буквы", "lastName"));
            }
        }

        public void validatePatronymic(UserDTO userDto){
            if (userDto.getPatronymic() == null) {
                return;
            }
            if (userDto.getPatronymic().length() > length) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_PATRONYMIC_NAME, "Максимальная длина отчества " + length + " символов", "patronymic"));
                return;
            }
            if (validateName(userDto.getPatronymic())) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_PATRONYMIC_NAME, "Отчество может содержать только русские буквы", "patronymic"));
                return;
            }
        }

        public void validatePosition(UserDTO userDto){
            if (userDto.getPosition() == null || userDto.getPosition().isEmpty()) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_POSITION, "Поле должность не должно быть пустым", "position"));
                return;
            }
        }

        public void validateLogin(UserDTO userDto){
            if (userDto.getLogin() == null || userDto.getLogin().isEmpty()) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_LOGIN, "Поле логин не может быть пустым", "login"));
                return;
            }
            if (userDto.getLogin().length() > length) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_LOGIN, "Максимальная длина логина " + length + " символов", "login"));
                return;
            }
            String regexForLogin = "^([А-Яа-яa-zA-Z0-9]+)$";
            Pattern pattern = Pattern.compile(regexForLogin);
            Matcher matcher = pattern.matcher(userDto.getLogin());
            if (!matcher.matches()) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_LOGIN, "Логин может содержать только латинские и русские буквы и цифры", "login"));
                return;
            }

        }

        public void validatePassword(UserDTO userDto){
            if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_PASSWORD, "Поле пароль не может быть пустым", "password"));
                return;
            }
            if (userDto.getPassword().length() < minPasswordLength) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_PASSWORD, "Минимальная длина пароля " + minPasswordLength + " символов", "password"));
                return;
            }
        }

        public void validateNewPassword(UserDTO userDto){
            if (userDto.getNewPassword() == null || userDto.getNewPassword().isEmpty()) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_PASSWORD, "Поле пароль не может быть пустым", "password"));
                return;
            }
            if (userDto.getNewPassword().length() < minPasswordLength) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_PASSWORD, "Минимальная длина пароля " + minPasswordLength + " символов", "password"));
                return;
            }
        }

        public void validateEmail(UserDTO userDto) {
            String regexForEmail = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
            Pattern pattern = Pattern.compile(regexForEmail);
            Matcher matcher = pattern.matcher(userDto.getEmail());
            if (!matcher.matches()) {
                userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_EMAIL, "Почта должна быть типа XXXX@XXX.XXX, где X-любое количество символов", "email"));
            }
        }







        public void setFieldOfClientForJson(Client client, UserDTO userDto) {
            userDto.setId(client.getId());
            userDto.setFirstName(client.getFirstName());
            userDto.setLastName(client.getLastName());
            userDto.setPatronymic(client.getPatronymic());
            userDto.setEmail(client.getEmail());
            userDto.setAddress(client.getAddress());
            userDto.setPhone(client.getPhone());
            userDto.setPassword(null);
            userDto.setLogin(null);
            userDto.setDeposit(0);
        }

        public void setFieldOfAdminForJson(Administrator admin, UserDTO userDto) {
            userDto.setId(admin.getId());
            userDto.setFirstName(admin.getFirstName());
            userDto.setLastName(admin.getLastName());
            userDto.setPatronymic(admin.getPatronymic());
            userDto.setPosition(admin.getPosition());
            userDto.setPassword(null);
            userDto.setLogin(null);
        }
    public void setNullForFieldsCategory(CategoryDto categoryDto) {
        categoryDto.setParentId(null);
        categoryDto.setName(null);
        categoryDto.setParentName(null);
    }

    public boolean validateCategory(CategoryDto categoryDto) {
        if (categoryDto.getName() == null || categoryDto.getName().isEmpty()) {
            categoryDto.getErrors().add(new UserServiceError(UserErrorCode.WRONG_CATEGORY_NAME, "Имя категории не может быть пустым", "name"));
            setNullForFieldsCategory(categoryDto);
            return false;
        }
        return true;
    }

    public boolean validateProduct(ProductDto productDto) {
        if (productDto.getName() == null || productDto.getName().isEmpty()) {
            productDto.getErrors().add(new UserServiceError(UserErrorCode.WRONG_CATEGORY_NAME, "Имя категории не может быть пустым", "name"));
            setNullForFieldsProduct(productDto);
            return false;
        }
        return true;
    }

    public void setNullForFieldsProduct(ProductDto productDto){
        productDto.setId(null);
        productDto.setName(null);
        productDto.setCategories(null);
        productDto.setCount(null);
        productDto.setPrice(null);
    }


    }
