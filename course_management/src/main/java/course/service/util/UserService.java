package course.service.util;

import course.model.User;
import course.repository.impl.UserRepository;
import course.session.LoginSession;

import java.util.List;

public class UserService {
    private UserRepository repository;

    public UserService() {
        repository = new UserRepository();
    }

    public List<User> getAllUsers() {
        return repository.getAll();
    }

    public User saveUser(User user) {
        int id = repository.getLatestUserId();
        user.setId(id + 1);
        addUserToSession(user.getUsername(), user.getPassword());
        return repository.add(user);
    }

    public User updateUser(User user) {
        addUserToSession(user.getUsername(), user.getPassword());
        return repository.update(user);
    }

    public User loginUser(String username, String password) {
        User user = repository.getUserByUsernameAndPassword(username, password);
        if (user == null)
            return null;
        addUserToSession(username, password);
        return user;

    }

    public User getUserInSession() {
        return repository.getUserByUsernameAndPassword(LoginSession.getUsername(), LoginSession.getPassword());
    }

    public void addUserToSession(String username, String password) {
        LoginSession.setUsername(username);
        LoginSession.setPassword(password);
    }
}
