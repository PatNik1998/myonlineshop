

import net.thumbtack.onlineshop.dao.implementations.AdministratorDaoImpl;
import net.thumbtack.onlineshop.dao.implementations.UserDaoImpl;

import net.thumbtack.onlineshop.dto.UserDTO;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.entities.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminServiceTest {
    private AdministratorDaoImpl mockAdminDao;
    private UserDaoImpl mockUserDao;
    private AdministratorService service;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private String login;
    private String password;

    @Before
    public void init() {
        firstName = "Патраков";
        lastName = "Никита";
        patronymic = "Сергеевич";
        position = "разработчик";
        password = "q1w2e3r4t5y6";
        login = "patnik";
        mockAdminDao = mock(AdministratorDaoImpl.class);
        mockUserDao = mock(UserDaoImpl.class);
        service = new AdministratorServiceImpl(mockAdminDao, mockUserDao);
    }

    @Test
    public void testInvalidRegisterAdmin() {
        UserDTO admin = new UserDTO();
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPatronymic(patronymic);
        admin.setPosition(position);
        admin.setPassword(password);
        admin.setLogin(login);
        when(mockUserDao.checkLogin(any())).thenReturn(true);
            service.registerAdmin(admin);

    }
    @Test
    public void testValidRegisterAdmin()  {
        UserDTO admin = new UserDTO();
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPatronymic(patronymic);
        admin.setPosition(position);
        admin.setPassword(password);
        admin.setLogin(login);

        when(mockUserDao.checkLogin(any())).thenReturn(false);
        User expected = new User();
        expected.setFirstName(firstName);
        expected.setLastName(lastName);
        expected.setPassword(password);
        expected.setPatronymic(patronymic);
        expected.setLogin(login);
        User result = service.registerAdmin(admin);
        User actual = new User(result.getFirstName(), result.getLastName(), result.getPatronymic(), result.getLogin(), result.getPassword());
        verify(mockAdminDao, times(1)).add(any());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testValidEditAdmin() {
        Administrator admin = new Administrator();
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPatronymic(patronymic);
        admin.setPosition(position);
        admin.setPassword(password);
        admin.setLogin(login);

        UserDTO dto = new UserDTO();
        dto.setFirstName("Имя");
        dto.setLastName("Фамилия");
        dto.setPatronymic("");
        dto.setPosition("Позиция");
        dto.setOldPassword(password);
        dto.setNewPassword("NewPassword");

        UserDTO expected = new UserDTO(){{setPosition(dto.getPosition());setPatronymic(dto.getPatronymic());setFirstName(dto.getFirstName());setLastName(dto.getLastName());}};
        UserDTO actual = service.editAdminProfile(admin, dto);

        verify(mockAdminDao, times(1)).update(any());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testInvalidEditAdmin() {
        Administrator admin = new Administrator();
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPatronymic(patronymic);
        admin.setPosition(position);
        admin.setPassword(password);
        admin.setLogin(login);

        UserDTO dto = new UserDTO();
        dto.setFirstName("НовоеИмя");
        dto.setLastName("НоваяФамилия");
        dto.setPatronymic("");
        dto.setPosition("НоваяПозиция");
        dto.setOldPassword("wrongOldPassword");
        dto.setNewPassword("correctNewPassword");
            service.editAdminProfile(admin, dto);
    }
}
