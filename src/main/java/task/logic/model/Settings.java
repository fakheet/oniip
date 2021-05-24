package task.logic.model;

import java.util.Objects;

public class Settings {

    private String location;
    private String user;
    private String password;

    public Settings(String location, String user, String password) {
        this.location = location;
        this.user = user;
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settings settings = (Settings) o;
        return Objects.equals(location, settings.location) && Objects.equals(user, settings.user) && Objects.equals(password, settings.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, user, password);
    }

    @Override
    public String toString() {
        return "Settings{" +
                "location='" + location + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
