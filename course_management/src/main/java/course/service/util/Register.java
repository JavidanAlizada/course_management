package course.service.util;

import course.model.User;

public class Register {
    private UserService service;

    public Register() {
        service = new UserService();
    }

    public User signUp(User user) {
        return service.saveUser(user);
    }

    public User signIn(String username, String password) {
        return service.loginUser(username, password);
    }
}
