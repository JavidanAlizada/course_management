package course.repository.impl;

import course.model.Course;
import course.repository.Database;
import course.repository.Repository;

import java.util.List;

public class CourseRepository implements Repository<Course, Integer> {
    @Override
    public Course add(Course course) {
        Database.setCourse(course);
        return course;
    }

    @Override
    public Course getById(Integer integer) {
        return null;
    }

    @Override
    public List<Course> getAll() {
        return Database.getCourses();
    }

    @Override
    public Course update(Course course) {
        int idx = 0;
        for (Course eachCourse : Database.getCourses()) {
            if (course.getId() == eachCourse.getId()) {
                Database.getCourses().set(idx, course);
                return course;
            }
            idx++;
        }
        return null;

    }

    @Override
    public boolean delete(Course course) {
        return false;
    }
}
