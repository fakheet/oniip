package task.logic.dao;

import task.logic.model.user.User;

import java.util.List;

public interface AppDao {

    List<User> getUsers();

    boolean insertUser(User user);

    boolean updateUser(User user);

    boolean removeUser(long id);
}
