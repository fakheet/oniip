package task.ui;

import task.logic.dao.AppDao;
import task.logic.daoimp.AppDaoImp;
import task.logic.model.UserTableModel;
import task.logic.model.user.User;
import task.logic.model.user.UserAccessLevel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class MainScreen extends JFrame implements NewUserReceiver {
    private final JMenuBar appMenuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("Файл");
    private final JMenu helpMenu = new JMenu("Помощь");
    private final JMenuItem exportToXlsMenuItem = new JMenuItem("Экспорт в xls файл");
    private final JMenuItem exitMenuItem = new JMenuItem("Выход");
    private final JMenuItem aboutMenuItem = new JMenuItem("О программе");
    private final AppDao usersDao = AppDaoImp.getInstance();
    private UserTableModel userTableModel;
    private TableRowSorter<UserTableModel> tableRowSorter;

    private JPanel mainPanel;
    private JButton exitButton;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField idTextField;
    private JComboBox accessLevelDropdown;
    private JTextField loginTextField;
    private JTable usersTable;
    private JLabel totalRecordsLabel;
    private JButton resetFiltersButton;


    public MainScreen(String title) throws HeadlessException {
        super(title);
        reloadTableData();
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        configureElements();
        this.setSize(800, 600);
    }

    private void configureElements() {
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        usersTable.setModel(userTableModel);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        attachActionListeners();
        createMenuBar();
    }

    private void createMenuBar() {
        fileMenu.add(exportToXlsMenuItem);
        fileMenu.add(exitMenuItem);
        helpMenu.add(aboutMenuItem);

        appMenuBar.add(fileMenu);
        appMenuBar.add(helpMenu);

        setJMenuBar(appMenuBar);
    }

    private void attachActionListeners() {
        createButton.addActionListener(actionEvent -> {
            JFrame frame = new EditScreen(this);
            frame.setVisible(true);
        });

        editButton.addActionListener(actionEvent -> {
            int selectedRow = usersTable.convertRowIndexToModel(usersTable.getSelectedRow());
            JFrame frame = new EditScreen(this, userTableModel.getUsers().get(selectedRow));
            frame.setVisible(true);
        });

        deleteButton.addActionListener(actionEvent -> {
            deleteButton.setEnabled(false);
            try {
                int deletedRow = usersTable.convertRowIndexToModel(usersTable.getSelectedRow());
                if (deletedRow <= userTableModel.getUsers().size()) {
                    long userId = userTableModel.getUsers().get(deletedRow).getId();
                    usersDao.removeUser(userId);
                    reloadTableData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteButton.setEnabled(true);
        });

        exitMenuItem.addActionListener(actionEvent -> exitApplication());
        exitButton.addActionListener(actionEvent -> exitApplication());

        usersTable.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
        });

        usersTable.getSelectionModel().addListSelectionListener(
                event -> {
                    if (usersTable.getSelectedRow() >= 0) {
                        int modelRow = usersTable.convertRowIndexToModel(usersTable.getSelectedRow());
                        System.out.println(modelRow);
                    }
                }
        );

        idTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                updateFilters();
            }
            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                updateFilters();
            }
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                updateFilters();
            }
        });

        loginTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                updateFilters();
            }
            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                updateFilters();
            }
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                updateFilters();
            }
        });

        accessLevelDropdown.addActionListener(actionEvent -> updateFilters());

        resetFiltersButton.addActionListener(actionEvent -> tableRowSorter.setRowFilter(null));
    }

    private void reloadTableData() {
        userTableModel = new UserTableModel((ArrayList<User>) usersDao.getUsers());
        usersTable.setModel(userTableModel);
        tableRowSorter = new TableRowSorter<>(userTableModel);
        usersTable.setRowSorter(tableRowSorter);
        usersTable.repaint();
    }

    private void updateFilters() {
        RowFilter<UserTableModel, Object> rf = null;
        try {
            var filters = new ArrayList<RowFilter<UserTableModel, Object>>();
            filters.add(RowFilter.regexFilter(idTextField.getText(), 0));
            filters.add(RowFilter.regexFilter(loginTextField.getText(), 1));
            filters.add(RowFilter.regexFilter((String) Objects.requireNonNull(accessLevelDropdown.getSelectedItem()),3));

            rf = RowFilter.andFilter(filters);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        tableRowSorter.setRowFilter(rf);
    }

    private void exitApplication() {
        Container frame = exitButton.getParent();
        do
            frame = frame.getParent();
        while (!(frame instanceof JFrame));
        ((JFrame) frame).dispose();
    }

    @Override
    public void receiveNewUserModel(boolean isEditing, User user) {
        if (isEditing) {
            usersDao.updateUser(user);
        } else {
            usersDao.insertUser(user);
        }
        reloadTableData();
    }
}
