package sample;

public class DuplicateUserException extends Exception {

    public DuplicateUserException(String name) {
        super("duplicate username: " + name);
    }

}
