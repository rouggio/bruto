package sample.basic;

public class DuplicateUserException extends Exception {

    public DuplicateUserException(String name) {
        super("duplicate username: " + name);
    }

}
