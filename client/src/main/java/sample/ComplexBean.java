package sample;

import java.util.HashMap;
import java.util.Map;

public class ComplexBean {

    private Map<String, User> users = new HashMap<>();

    public User create(String name, String password) throws DuplicateUserException {
        if (!users.containsKey(name)) {
            User user = new User(name, password);
            users.put(name, user);
            return user;
        } else {
            throw new DuplicateUserException(name);
        }
    }

    public Boolean containsUser(String user) {
        return users.containsKey(user);
    }

    public class User {
        String username;
        String password;

        public User(String username, String password) {
            if (username == null) throw new IllegalArgumentException("username cannot be null");
            if (password == null) throw new IllegalArgumentException("password cannot be null");
            this.username = username;
            this.password = password;
        }


    }


}
