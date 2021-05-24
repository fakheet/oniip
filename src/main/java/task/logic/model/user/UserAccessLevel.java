package task.logic.model.user;

public enum UserAccessLevel {
    Admin(1),
    Tester(2),
    User(3);

    private final long accessLevelValue;

    UserAccessLevel(long accessLevelValue) {
        this.accessLevelValue = accessLevelValue;
    }

    public long getAccessLevelValue() {
        return accessLevelValue;
    }

    public static String valueToString(long accessLevel) {
        switch ((int) accessLevel) {
            case 1:
                return "Admin";
            case 2:
                return "Tester";
            case 3:
                return "User";
        }
        return "";
    }

    public static UserAccessLevel createFromString(String string) {
        switch (string) {
            case "Admin":
                return UserAccessLevel.Admin;
            case "Tester":
                return UserAccessLevel.Tester;
            default:
                return UserAccessLevel.User;
        }
    }

}
