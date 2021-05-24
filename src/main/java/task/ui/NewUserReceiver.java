package task.ui;


import task.logic.model.user.User;

public interface NewUserReceiver {
    void receiveNewUserModel(boolean isEditing, User user);
}
