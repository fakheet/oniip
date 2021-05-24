package task;

import task.logic.dao.AppDao;
import task.logic.daoimp.AppDaoImp;
import task.logic.model.user.User;
import task.ui.MainScreen;

import javax.swing.*;
import java.time.LocalDateTime;

class Main {
    private final MainScreen mainScreen = new MainScreen("CRUD");

    public static void main(String[] args) {
//        addMore(5);
        JFrame frame = new MainScreen("База пользователей");
        frame.setVisible(true);
    }

    private static void addMore(int howMany) {
        AppDao dao = AppDaoImp.getInstance();
        for (int i = 0; i < howMany; i++) {
            dao.insertUser(new User("test", "test", 1, LocalDateTime.now(), LocalDateTime.now()));
        }
    }
}
