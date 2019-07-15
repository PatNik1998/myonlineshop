package net.thumbtack.onlineshop.common;



import net.thumbtack.onlineshop.dto.request.CategoryDto;
import net.thumbtack.onlineshop.dto.request.ProductDto;
import net.thumbtack.onlineshop.dto.request.UserDTO;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static int length = 20;

    public static int minPasswordLength = 8;

    public static void registerAdminValidate(UserDTO userDto){
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

    public static void editAdminValidate(UserDTO userDto) {
        validateFirstName(userDto);
        validateLastName(userDto);
        validatePatronymic(userDto);
        validateNewPassword(userDto);
        validatePosition(userDto);

    }


    public static boolean validateName(String name) {
        String regexForFIO = "^([А-Яа-я]+(-[а-я]+)* ?)+$";
        Pattern pattern = Pattern.compile(regexForFIO);
        Matcher matcher = pattern.matcher(name);
        return !matcher.matches();
    }

    public static void clearField(UserDTO userDto) {
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

    public static void validateAddress(UserDTO userDto){
        if (userDto.getAddress() == null || userDto.getAddress().isEmpty()) {
            userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_ADDRESS, "Поле адрес не может быть пустым", "address"));
        }
    }

    public static void validateFirstName(UserDTO userDto){
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

    public static void validateLastName(UserDTO userDto){
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

    public static void validatePatronymic(UserDTO userDto){
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

    public static void validatePosition(UserDTO userDto){
        if (userDto.getPosition() == null || userDto.getPosition().isEmpty()) {
            userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_POSITION, "Поле должность не должно быть пыстым", "position"));
            return;
        }
    }

    public static void validateLogin(UserDTO userDto){
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

    public static void validatePassword(UserDTO userDto){
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_PASSWORD, "Поле пароль не может быть пустым", "password"));
            return;
        }
        if (userDto.getPassword().length() < minPasswordLength) {
            userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_PASSWORD, "Минимальная длина пароля " + minPasswordLength + " символов", "password"));
            return;
        }
    }

    public static void validateNewPassword(UserDTO userDto){
        if (userDto.getNewPassword() == null || userDto.getNewPassword().isEmpty()) {
            userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_PASSWORD, "Поле пароль не может быть пустым", "password"));
            return;
        }
        if (userDto.getNewPassword().length() < minPasswordLength) {
            userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_PASSWORD, "Минимальная длина пароля " + minPasswordLength + " символов", "password"));
            return;
        }
    }

    public static void checkEmail(UserDTO userDto) {
        String regexForEmail = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
        Pattern pattern = Pattern.compile(regexForEmail);
        Matcher matcher = pattern.matcher(userDto.getEmail());
        if (!matcher.matches()) {
            userDto.getErrors().add(new UserServiceError(UserErrorCode.INVALID_EMAIL, "Почта должна быть типа XXXX@XXX.XXX, где X-любое количество символов", "email"));
        }
    }

    public static void setNullForFieldsCategory(CategoryDto categoryDto) {
        categoryDto.setId(null);
        categoryDto.setParentId(null);
        categoryDto.setName(null);
        categoryDto.setParentName(null);
    }

    public static boolean validateCategory(CategoryDto categoryDto) {
        if (categoryDto.getName() == null || categoryDto.getName().isEmpty()) {
            categoryDto.getErrors().add(new UserServiceError(UserErrorCode.WRONG_CATEGORY_NAME, "Имя категории не может быть пустым", "name"));
            setNullForFieldsCategory(categoryDto);
            return false;
        }
        return true;
    }

    public static boolean validateProduct(ProductDto productDto) {
        if (productDto.getName() == null || productDto.getName().isEmpty()) {
            productDto.getErrors().add(new UserServiceError(UserErrorCode.WRONG_CATEGORY_NAME, "Имя категории не может быть пустым", "name"));
            setNullForFieldsProduct(productDto);
            return false;
        }
        return true;
    }

    public static void setNullForFieldsProduct(ProductDto productDto){
        productDto.setId(null);
        productDto.setName(null);
        productDto.setCategories(null);
        productDto.setCount(null);
        productDto.setPrice(null);
    }




}
