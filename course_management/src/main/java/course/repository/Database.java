package course.repository;

import course.model.Course;
import course.model.User;
import course.model.UserCourse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {

    private static final List<User> users = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<UserCourse> userCourses = new ArrayList<>();

    static {
        DatabaseInitiator databaseInitiator = new DatabaseInitiator();
        Arrays.stream(databaseInitiator.initiateUsers()).forEach(Database::setUser);
        Arrays.stream(databaseInitiator.initiateCourses()).forEach(Database::setCourse);
        Arrays.stream(databaseInitiator.initiateUserCourses()).forEach(Database::setUserCourses);
    }

    private Database() {
    }

    public static void setUser(User user) {
        users.add(user);
    }

    public static void setCourse(Course course) {
        courses.add(course);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Course> getCourses() {
        int idx = 0;
        for (Course course : courses) {
            if (course.getCreatedOn() == null) {
                course.setCreatedOn(LocalDateTime.now());
                courses.set(idx, course);
            }
            idx++;
        }
        return courses;
    }

    public static List<UserCourse> getUserCourses() {
        return userCourses;
    }

    public static void setUserCourses(UserCourse userCourse) {
        userCourses.add(userCourse);
    }

}