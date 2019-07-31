package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dao.implementations.ClientDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.UserDaoImpl;
import net.thumbtack.onlineshop.dto.UserDTO;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.entities.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ClientServiceTest {
    private ClientDaoImpl mockClientDao;
    private UserDaoImpl mockUserDao;
    private ClientService service;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String login;
    private String password;
    private String email;
    private String address;
    private String phone;

    @Before
    public void init() {
        firstName = "Никита";
        lastName = "Патраков";
        patronymic = "Сергеевич";
        email = "asdg@asg.asg";
        address = "asdfg";
        phone = "98741234";
        password = "q1w2e3r4t5y6";
        login = "patnik";
        mockClientDao = mock(ClientDaoImpl.class);
        mockUserDao = mock(UserDaoImpl.class);
        service = new ClientServiceImpl(mockClientDao, mockUserDao);
    }

    @Test
    public void testRegisterClient()  {
        UserDTO dto = new UserDTO();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setPatronymic(patronymic);
        dto.setPassword(password);
        dto.setLogin(login);
        dto.setAddress(address);
        dto.setEmail(email);
        dto.setPhone(phone);
        when(mockUserDao.checkLogin(any())).thenReturn(true);
            service.registerClient(dto);
            fail();
    }

    @Test
    public void testRegisterClient2() {
        UserDTO dto = new UserDTO();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setPatronymic(patronymic);
        dto.setPassword(password);
        dto.setLogin(login);
        dto.setAddress(address);
        dto.setEmail(email);
        dto.setPhone(phone);

        when(mockUserDao.checkLogin(any())).thenReturn(false);
        User expected = new User();
        expected.setFirstName(firstName);
        expected.setLastName(lastName);
        expected.setPassword(password);
        expected.setPatronymic(patronymic);
        expected.setLogin(login);
        User result = service.registerClient(dto);
        User actual = new User(result.getFirstName(), result.getLastName(), result.getPatronymic(), result.getLogin(), result.getPassword());
        verify(mockClientDao, times(1)).add(any());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEditClient()  {
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPatronymic(patronymic);
        client.setPassword(password);
        client.setLogin(login);
        client.setDeposit(0);
        client.setPhone(phone);
        client.setAddress(address);
        client.setEmail(email);

        UserDTO dto = new UserDTO();
        String newFirstName = "НовоеИмяЭЭ";
        String newLastName = "НоваяФамилия";
        String newEmail = "newemail@gmail.com";
        String newAddress = "new address";
        String oldPassword = password;
        String newPhone = "89179013909";
        String newPassword = "q1w2e3r4t5y6";
        dto.setFirstName(newFirstName);
        dto.setLastName(newLastName);
        dto.setPatronymic(null);
        dto.setPhone(newPhone);
        dto.setEmail(newEmail);
        dto.setAddress(newAddress);
        dto.setOldPassword(oldPassword);
        dto.setNewPassword(newPassword);

        UserDTO expected = new UserDTO();{
            {
                setFirstName(dto.getFirstName());
                setLastName(dto.getLastName());
                setPatronymic(dto.getPatronymic());
                setEmail(dto.getEmail());
                setAddress(dto.getAddress());
                setDeposit(0);
                setPhone(dto.getPhone());
            }};
        UserDTO actual = service.editClientProfile(client, dto);

        verify(mockClientDao, times(1)).update(any());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEditClient2() {
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPatronymic(patronymic);
        client.setPassword(password);
        client.setLogin(login);
        client.setDeposit(0);
        client.setPhone(phone);
        client.setAddress(address);
        client.setEmail(email);

        UserDTO dto = new UserDTO();
        String newFirstName = "НовоеИмяЭЭ";
        String newLastName = "НоваяФамилия";
        String newEmail = "newemail@gmail.com";
        String newAddress = "new address";
        String oldPassword = "WRONG_OLD_PASSWORD";
        String newPhone = "89179013909";
        String newPassword = "q1w2e3r4t5y6";
        dto.setFirstName(newFirstName);
        dto.setLastName(newLastName);
        dto.setPatronymic(null);
        dto.setPhone(newPhone);
        dto.setEmail(newEmail);
        dto.setAddress(newAddress);
        dto.setOldPassword(oldPassword);
        dto.setNewPassword(newPassword);
            service.editClientProfile(client, dto);
            fail();

    }
}
