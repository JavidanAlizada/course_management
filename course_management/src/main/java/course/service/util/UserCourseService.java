package course.service.util;

import course.model.UserCourse;
import course.repository.impl.UserCourseRepository;

import java.util.List;

public class UserCourseService {

    private final UserCourseRepository repository;

    public UserCourseService() {
        this.repository = new UserCourseRepository();
    }

    public UserCourse addUserCourse(UserCourse userCourse) {
        int id = repository.getLatestUserCourseId();
        userCourse.setId(id + 1);
        return repository.add(userCourse);
    }

    public List<UserCourse> getAllUserCourses() {
        return repository.getAll();
    }
}
