package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        User user1 = new User("Danya", "Ternovskii", (byte) 25);
        User user2 = new User("Vanya", "Pupkin", (byte) 27);
        User user3 = new User("Marat", "Sakharov", (byte) 17);
        User user4 = new User("Alex", "Crown", (byte) 21);

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        List<User> users = userService.getAllUsers();

        for (User user:users){
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();

//        userService.removeUserById(5);

    }
}
