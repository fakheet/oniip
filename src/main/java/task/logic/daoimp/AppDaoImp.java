package task.logic.daoimp;

import task.logic.dao.AppDao;
import task.logic.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppDaoImp implements AppDao {

    private AppDaoImp() {
    }

    private static AppDaoImp instance;


    public static synchronized AppDaoImp getInstance() {
        if (instance == null) {
            instance = new AppDaoImp();
        }
        return instance;
    }


    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        Connection con = JdbcUtils.getConnection();
        try {
            try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user")) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        users.add(new User(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4),
                                rs.getTimestamp(5).toLocalDateTime(),
                                rs.getTimestamp(6).toLocalDateTime()
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get users");
            System.out.println(e);
        }
        return users;
    }

    @Override
    public boolean insertUser(User user) {
        Connection con = JdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO user" +
                "(login, password, accesslvl, dateofcreation, dateofmodification)" +
                "values(?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getAccessLevel());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(user.getCreationDate()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(user.getLastModificationDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to insert user");
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        Connection con = JdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE user " +
                "SET login = ?, password = ?, accesslvl = ?, dateofcreation = ?, dateofmodification = ? " +
                "WHERE id = ?")) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getAccessLevel());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(user.getCreationDate()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(user.getLastModificationDate()));
            preparedStatement.setLong(6, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to update user");
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean removeUser(long id) {
        Connection con = JdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM user WHERE id = " + id)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to remove user");
            System.out.println(e);
            return false;
        }
        return true;
    }
}
