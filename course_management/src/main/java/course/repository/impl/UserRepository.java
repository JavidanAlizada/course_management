package course.repository.impl;

import course.model.User;
import course.repository.Database;
import course.repository.Repository;

import java.util.Comparator;
import java.util.List;

public class UserRepository implements Repository<User, Integer> {

    @Override
    public User add(User user) {
        Database.setUser(user);
        return user;
    }

    @Override
    public User getById(Integer id) {
        for (User user : Database.getUsers()) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        for (User user : Database.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return Database.getUsers();
    }

    @Override
    public User update(User user) {
        int idx = 0;
        for (User eachUser : Database.getUsers()) {
            if (user.getId() == eachUser.getId()) {
                Database.getUsers().set(idx, user);
                return user;
            }
            idx++;
        }
        return null;
    }

    public int getLatestUserId() {
        return Database.getUsers()
                .stream()
                .max(Comparator.comparingInt(User::getId))
                .get()
                .getId();
    }

    @Override
    public boolean delete(User user) {
        return false;
    }
}
