package course.repository.impl;

import course.model.UserCourse;
import course.repository.Database;
import course.repository.Repository;

import java.util.Comparator;
import java.util.List;

public class UserCourseRepository implements Repository<UserCourse, Integer> {
    @Override
    public UserCourse add(UserCourse userCourse) {
        Database.setUserCourses(userCourse);
        return userCourse;
    }

    @Override
    public UserCourse getById(Integer integer) {
        return null;
    }

    @Override
    public List<UserCourse> getAll() {
        return Database.getUserCourses();
    }

    @Override
    public UserCourse update(UserCourse userCourse) {
        return null;
    }

    @Override
    public boolean delete(UserCourse userCourse) {
        return false;
    }

    public int getLatestUserCourseId() {
        return Database.getUserCourses()
                .stream()
                .max(Comparator.comparingInt(UserCourse::getId))
                .get()
                .getId();
    }
}
