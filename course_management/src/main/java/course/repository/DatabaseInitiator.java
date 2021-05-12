package course.repository;

import course.helper.JsonFileHandler;
import course.model.Course;
import course.model.User;
import course.model.UserCourse;

public class DatabaseInitiator {

    public User[] initiateUsers() {
        return JsonFileHandler.convertJsonToPojo("default_users.json", User[].class);
    }

    public Course[] initiateCourses() {
        return JsonFileHandler.convertJsonToPojo("default_courses.json", Course[].class);
    }

    public UserCourse[] initiateUserCourses() {
        return JsonFileHandler.convertJsonToPojo("default_user_courses.json", UserCourse[].class);
    }
}
