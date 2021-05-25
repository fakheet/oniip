package task.ui;

import task.logic.model.user.User;
import task.logic.model.user.UserAccessLevel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class EditScreen extends JFrame {
    private final EditScreenMode screenMode;
    private User userBeingEdited;

    private JPanel mainPanel;

    private JTextField idTextField;
    private JTextField loginTextField;
    private JTextField passwordTextField;
    private JComboBox accessLevelDropdown;

    private JLabel headerLabel;
    private JLabel idLabel;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JLabel accessLevelLabel;

    private JButton acceptButton;
    private JButton cancelButton;

    private NewUserReceiver parent;

    public EditScreen(NewUserReceiver parent) throws HeadlessException {
        super(getScreenTitle(EditScreenMode.CREATE));
        this.screenMode = EditScreenMode.CREATE;
        initScreen(parent);
    }

    public EditScreen(NewUserReceiver parent, User userBeingEdited) throws HeadlessException {
        super(getScreenTitle(EditScreenMode.EDIT));
        this.screenMode = EditScreenMode.EDIT;
        this.userBeingEdited = userBeingEdited;
        setUserFields();
        initScreen(parent);
    }

    private static String getScreenTitle(EditScreenMode screenMode) {
        return screenTitleSelector(screenMode);
    }

    private static String screenTitleSelector(EditScreenMode mode) {
        return mode == EditScreenMode.EDIT ? "Редактировать" : "Создать";
    }

    private void initScreen(NewUserReceiver parent) {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        attachActionListeners();
        this.parent = parent;
        headerLabel.setText(getScreenTitle());
        idTextField.setEnabled(false);
    }

    private void setUserFields() {
        idTextField.setText(String.valueOf(userBeingEdited.getId()));
        loginTextField.setText(userBeingEdited.getLogin());
        passwordTextField.setText(userBeingEdited.getPassword());
        accessLevelDropdown.setSelectedItem(UserAccessLevel.longToString(userBeingEdited.getAccessLevel()));
    }

    public void attachActionListeners() {

        acceptButton.addActionListener(actionEvent -> {
            LocalDateTime creationDate = LocalDateTime.now();
            LocalDateTime lastModifiedDate = creationDate;

            long id = 0;

            if (screenMode == EditScreenMode.EDIT) {
                assert userBeingEdited != null;
                creationDate = userBeingEdited.getCreationDate();
                lastModifiedDate = LocalDateTime.now();
                id = userBeingEdited.getId();
            }

            parent.receiveNewUserModel(
                    screenMode == EditScreenMode.EDIT,
                    new User(
                            id,
                            loginTextField.getText(),
                            passwordTextField.getText(),
                            getDropdownValue(),
                            creationDate,
                            lastModifiedDate
                    ));

            this.dispose();
        });

        cancelButton.addActionListener(actionEvent -> {
            this.dispose();
        });
    }

    private long getDropdownValue() {
        String selectedItem = (String) accessLevelDropdown.getSelectedItem();
        return UserAccessLevel.createFromString(selectedItem != null ? selectedItem : "User").getAccessLevelValue();
    }

    private String getScreenTitle() {
        return screenTitleSelector(screenMode);
    }
}
