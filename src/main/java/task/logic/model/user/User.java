package task.logic.model.user;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {

    private long id;
    private String login;
    private String password;
    private long accessLevel;
    private LocalDateTime creationDate;
    private LocalDateTime lastModificationDate;

    public User(long id, String login, String password, long accessLevel, LocalDateTime creationDate, LocalDateTime lastModificationDate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.accessLevel = accessLevel;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
    }

    public User(String login, String password, long accessLevel, LocalDateTime creationDate, LocalDateTime lastModificationDate) {
        this.login = login;
        this.password = password;
        this.accessLevel = accessLevel;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
    }

    public Object getFieldAtIndex(int i) {
        switch (i) {
            case (0):
                return id;
            case (1):
                return login;
            case (2):
                return password;
            case (3):
                return accessLevel;
            case (4):
                return creationDate;
            case (5):
                return lastModificationDate;
            default:
                return new Object();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDateTime lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && accessLevel == user.accessLevel && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(creationDate, user.creationDate) && Objects.equals(lastModificationDate, user.lastModificationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, accessLevel, creationDate, lastModificationDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", accessLvl=" + accessLevel +
                ", dateofcreation=" + creationDate +
                ", dateofmodification=" + lastModificationDate +
                '}';
    }
}
