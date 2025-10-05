package task2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import task2.dao.UserDAOImpl;
import task2.models.Detail;
import task2.models.User;
import task2.utils.TestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDAOImpl userDAOImpl;

    @Mock
    private BufferedReader reader;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // подмена reader-а в классе
        UserServiceImpl.reader = reader;
        // подмена DAO через reflection
        TestUtils.setField(userService, "userDAOImpl", userDAOImpl);
    }

    @Test
    void saveUserWithDetailsSuccess() throws IOException {
        // Эмулирование пользовательского ввода
        when(reader.readLine())
                .thenReturn("John")
                .thenReturn("john@example.com")
                .thenReturn("25")
                .thenReturn("Y")                // согласие на ввод детальной информации
                .thenReturn("7-999-888-77-66")
                .thenReturn("Sezam street");
        userService.saveUser();

        // Проверка вызова DAO
        verify(userDAOImpl, times(1)).saveUser(any(User.class));
    }

    @Test
    void saveUserWithoutDetailsSuccess() throws IOException {
        when(reader.readLine())
                .thenReturn("John")
                .thenReturn("john@example.com")
                .thenReturn("25")
                .thenReturn("N"); // отказ от указания деталей
        userService.saveUser();

        verify(userDAOImpl).saveUser(any(User.class));
    }

    @Test
    void getUserSuccess() throws IOException {
        when(reader.readLine()).thenReturn("1");
        User user = new User("John", "john@example.com", 25);
        when(userDAOImpl.getUser(1)).thenReturn(user);

        userService.getUser();

        verify(userDAOImpl, times(1)).getUser(1);
    }

    @Test
    void getUserFail() throws IOException {
        when(reader.readLine()).thenReturn("5");
        when(userDAOImpl.getUser(5)).thenReturn(null);

        userService.getUser();

        verify(userDAOImpl, times(1)).getUser(5);
    }

    @Test
    void updateUserWithDetailsSuccess() throws IOException {
        User user = new User("John", "john@example.com", 25);
        user.setUserDetail(new Detail("1-222-333-44-55", "Sezam street"));
        when(reader.readLine())
                .thenReturn("1")                    // ввод id пользователя
                .thenReturn("Y")                    // согласие на обновление основной информации
                .thenReturn("Fred")                 // новое имя
                .thenReturn("fred@example.com")     // новый email
                .thenReturn("35")                   // новый возраст
                .thenReturn("Y")                    // согласие на обновление детальной информации
                .thenReturn("7-999-888-77-66")      // новый телефон
                .thenReturn("Vyazov street");       // новый адрес
        when(userDAOImpl.getUser(1)).thenReturn(user);

        userService.updateUser();

        verify(userDAOImpl, times(1)).updateUser(any(User.class));
    }

    @Test
    void updateUserWithoutDetailsSuccess() throws IOException {
        User user = new User("John", "john@example.com", 25);
        when(reader.readLine())
                .thenReturn("1")                    // ввод id пользователя
                .thenReturn("Y")                    // согласие на обновление основной информации
                .thenReturn("Fred")                 // новое имя
                .thenReturn("fred@example.com")     // новый email
                .thenReturn("35")                   // новый возраст
                .thenReturn("Y")                    // согласие на обновление детальной информации
                .thenReturn("7-999-888-77-66")      // новый телефон
                .thenReturn("Vyazov street");       // новый адрес
        when(userDAOImpl.getUser(1)).thenReturn(user);

        userService.updateUser();

        verify(userDAOImpl, times(1)).updateUser(any(User.class));
    }

    @Test
    void updateUserWithoutChanges() throws IOException {
        User user = new User("John", "john@example.com", 25);
        when(reader.readLine())
                .thenReturn("1")                    // ввод id пользователя
                .thenReturn("N")                    // отказ от обновления основной информации
                .thenReturn("N");                   // отказ от обновления детальной информации
        when(userDAOImpl.getUser(1)).thenReturn(user);

        userService.updateUser();

        verify(userDAOImpl, never()).updateUser(any(User.class));
    }

    @Test
    void updateUserNotFound() throws IOException {
        when(reader.readLine()).thenReturn("10");
        when(userDAOImpl.getUser(10)).thenReturn(null);

        userService.updateUser();

        verify(userDAOImpl, never()).updateUser(any(User.class));
    }

    @Test
    void deleteUserSuccess() throws IOException {
        User user = new User("John", "john@example.com", 25);
        user.setId(1);
        when(reader.readLine()).thenReturn("1");
        when(userDAOImpl.getUser(1)).thenReturn(user);

        userService.deleteUser();

        verify(userDAOImpl, times(1)).deleteUser(1);
    }

    @Test
    void deleteUserNotFound() throws IOException {
        when(reader.readLine()).thenReturn("42");
        when(userDAOImpl.getUser(42)).thenReturn(null);

        userService.deleteUser();

        verify(userDAOImpl, never()).deleteUser(anyInt());
    }

    @Test
    void getAllUsersSuccess() {
        List<User> users = new ArrayList<>();
        users.add(new User("John", "john@example.com", 25));
        users.add(new User("Fred", "fred@example.com", 36));
        when(userDAOImpl.getAllUsers()).thenReturn(users);

        userService.getAllUsers();

        verify(userDAOImpl, times(1)).getAllUsers();
    }

    @Test
    void getAllUsersEmptyList() {
        when(userDAOImpl.getAllUsers()).thenReturn(new ArrayList<>());

        userService.getAllUsers();

        verify(userDAOImpl, times(1)).getAllUsers();
    }
}