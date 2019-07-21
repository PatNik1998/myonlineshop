package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserDTO {

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String firstName;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String lastName;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String patronymic;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String position;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String email;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String address;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String phone;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer deposit;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String userType;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String login;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String password;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String oldPassword;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String newPassword;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<UserServiceError> errors = new ArrayList<>();
    @JsonInclude(value = JsonInclude.Include.NON_NULL )
    private Cookie cookie;

    public UserDTO() {

    }


    public void addError(UserServiceError error) {
        errors.add(error);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<UserServiceError> getErrors() {
        return errors;
    }

    public void setErrors(List<UserServiceError> errors) {
        this.errors = errors;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", deposit=" + deposit +
                ", userType='" + userType + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", errors=" + errors +
                '}';
    }
}

