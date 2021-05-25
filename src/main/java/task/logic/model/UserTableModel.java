package task.logic.model;

import task.logic.model.user.User;
import task.logic.model.user.UserAccessLevel;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class UserTableModel extends AbstractTableModel {

    private String[] columnNames = {"id", "Логин", "Пароль", "Уровень доступа", "Дата создания", "Дата модификации"};
    private ArrayList<User> users;

    public UserTableModel(ArrayList<User> users) {
        this.users = users;
    }


    public ArrayList<User> getUsers() {
        return this.users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public int getRowCount() {
        return users.size();
    }

    public int getColumnCount() {
        return 6;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class<?> getColumnClass(int col) {
        if (users.size() != 0) {
            return users.get(0).getFieldAtIndex(col).getClass();
        } else {
            return Object.class;
        }
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Object getValueAt(int row, int col) {
        if (col == 3) {
            return UserAccessLevel.longToString(users.get(row).getAccessLevel());
        }
        return users.get(row).getFieldAtIndex(col);
    }

    public void setValueAt(Object o, int row, int col) {
        assert false;
    }

    public void addTableModelListener(TableModelListener tableModelListener) {

    }

    public void removeTableModelListener(TableModelListener tableModelListener) {

    }
}
