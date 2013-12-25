package sample.composite;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

    private Map<String, Integer> accounts;

    public LoginService() {
        accounts = new HashMap<>();
        accounts.put("a", 0);
        accounts.put("b", 1);
        accounts.put("c", 2);
    }

    public Boolean authenticate(Credentials credentials) {
        Integer entry = accounts.get(credentials.getUsername());
        return entry.equals(credentials.getPassword());
    }

}

