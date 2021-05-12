package course.service.impl;

import course.constant.ExceptionMessage;
import course.constant.ModelType;
import course.constant.Role;
import course.model.Course;
import course.model.User;
import course.model.UserCourse;
import course.repository.impl.CourseRepository;
import course.service.Service;
import course.service.factory.ErrorMessageFactory;
import course.service.util.UserCourseService;
import course.service.util.UserService;
import course.validator.impl.UniqueValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CourseService implements Service {

    private final UserService userService;
    private final UserCourseService service;
    private Scanner scanner;

    public CourseService(Scanner scanner) {
        this.scanner = scanner;
        this.userService = new UserService();
        this.service = new UserCourseService();
    }


    @Override
    public void service() throws Exception {
        serviceCourse(this.scanner);
    }

    private void showCourseMenu() {
        System.out.println("You are in Course Service. Please select number: ");
        System.out.println("1. Show Course Details by Name");
        System.out.println("2. Show All Courses");
        System.out.println("3. Take a New Course (only for ATTENDER)");
        System.out.println("4. Register a New Course (only for TUTOR)");
        System.out.println("5. Edit Course (for TUTOR and ADMIN)");
        System.out.println("6. Show all Registered Courses (only for TUTOR)");
        System.out.println("7. Show all Taken Courses (only for ATTENDER)");
    }

    private void serviceCourse(Scanner scanner) throws Exception {
        showCourseMenu();
        int courseService = scanner.nextInt();
        switch (courseService) {
            case 1:
                System.out.println("Enter course name to search: ");
                String courseName = scanner.next();
                showCourseDetails(courseName);
                break;
            case 2:
                showAllCourses();
                break;
            case 3:
                System.out.println("Enter course name to search: ");
                String course = scanner.next();
                if (userService.getUserInSession().getRole() != Role.ATTENDER)
                    throw ErrorMessageFactory.message(ExceptionMessage.COURSE_ENROLL_ROLE_RESTRICTION);
                showTakenCourseDetailsWithAccount(course);
                break;
            case 4:
                System.out.println("Enter course name");
                String registeredCourseName = scanner.next();
                if (!UniqueValidator.isNameFieldUnique(registeredCourseName, ModelType.COURSE))
                    throw ErrorMessageFactory.message(ExceptionMessage.UNIQUE_NAME);
                System.out.println("Enter course description");
                String description = scanner.next();
                System.out.println("Enter course content");
                String content = scanner.next();
                System.out.println("Enter course price");
                int price = Integer.parseInt(scanner.next());
                Course newCourse = Course.builder().name(registeredCourseName)
                        .description(description).content(content).price(price).build();
                showRegisteredCourseInfo(newCourse);
                break;
            case 5:
                if (userService.getUserInSession().getRole() == Role.ATTENDER)
                    throw ErrorMessageFactory.message(ExceptionMessage.COURSE_EDIT_ROLE_RESTRICTION);
                System.out.println("Enter course id: ");
                String courseId = scanner.next();
                System.out.println("Enter course name");
                String registCourseName = scanner.next();
                if (!UniqueValidator.isNameFieldUnique(registCourseName, ModelType.COURSE))
                    throw ErrorMessageFactory.message(ExceptionMessage.UNIQUE_NAME);
                System.out.println("Enter course description");
                String editDescription = scanner.next();
                System.out.println("Enter course content");
                String editContent = scanner.next();
                System.out.println("Enter course price");
                int editPrice = Integer.parseInt(scanner.next());
                Course editedCourse = Course.builder().name(registCourseName)
                        .description(editDescription).content(editContent)
                        .price(editPrice).id(Integer.parseInt(courseId)).build();
                showEditedCourseInfo(editedCourse);
                break;
            case 6:
                if (userService.getUserInSession().getRole() != Role.TUTOR)
                    throw ErrorMessageFactory.message(ExceptionMessage.COURSE_OWNER_ERROR);
                showAllRegisteredCourse();
                break;
            case 7:
                if (userService.getUserInSession().getRole() != Role.ATTENDER)
                    throw ErrorMessageFactory.message(ExceptionMessage.COURSE_VIEW_ROLE_RESTRICTION);
                showAllTakenCourses();
                break;
            default:
                throw ErrorMessageFactory.message(ExceptionMessage.SERVICE_NOT_FOUND_ERROR);
        }
    }

    private void showCourseDetails(String name) {
        Course course = getCourseByName(name);
        System.out.println(course);
    }

    private Course getCourseByName(String courseName) {
        return CourseServiceDetailsImplementation.getCourseDetailsByName(courseName);
    }

    private void showAllCourses() {
        getAllCourses().forEach(System.out::println);
    }

    private List<Course> getAllCourses() {
        return CourseServiceDetailsImplementation.getAllCourses();
    }

    private void showTakenCourseDetailsWithAccount(String courseName) throws Exception {
        UserCourse userCourse = getTakenUserCourseDetails(courseName);
        if (userCourse == null)
            throw ErrorMessageFactory.message(ExceptionMessage.ROLE_NOT_FOUND);
        User user = userCourse.getUser();
        Course course = userCourse.getCourse();
        System.out.println(user + "\n" + course);
    }

    private UserCourse getTakenUserCourseDetails(String courseName) {
        Course course = getCourseByName(courseName);
        User user = userService.getUserInSession();
        if (user.getRole() != Role.ATTENDER)
            return null;
        UserCourse userCourse = new UserCourse();
        userCourse.setUser(user);
        userCourse.setCourse(course);
        return service.addUserCourse(userCourse);
    }

    private void showRegisteredCourseInfo(Course course) {
        Course registeredCourse = registerCourse(course);
        System.out.println(registeredCourse);
    }

    private Course registerCourse(Course course) {
        course.setCreatedOn(LocalDateTime.now());
        course.setCreatedBy(userService.getUserInSession());
        course.setId(CourseServiceDetailsImplementation.getLatestCourseId() + 1);
        return CourseServiceDetailsImplementation.addCourse(course);
    }

    private void showEditedCourseInfo(Course course) throws Exception {
        System.out.println(editCourse(course));
    }

    private Course editCourse(Course course) throws Exception {
        if (!CourseServiceDetailsImplementation.existsCourse(course.getId()))
            throw ErrorMessageFactory.message(ExceptionMessage.COURSE_NOT_EXIST);
        course.setCreatedOn(LocalDateTime.now());
        course.setCreatedBy(userService.getUserInSession());
        return CourseServiceDetailsImplementation.editCourse(course);
    }

    private void showAllRegisteredCourse() {
        getAllCoursesForTutor().forEach(System.out::println);
    }

    private List<Course> getAllCoursesForTutor() {
        return CourseServiceDetailsImplementation
                .getAllCourses()
                .stream()
                .filter(c -> c.getCreatedBy().equals(userService.getUserInSession()))
                .collect(Collectors.toList());
    }

    private void showAllTakenCourses() {
        getAllCoursesForCurrentUser().forEach(System.out::println);
    }

    private List<Course> getAllCoursesForCurrentUser() {
        return service
                .getAllUserCourses()
                .stream()
                .filter(userCourse -> userCourse.getUser().equals(userService.getUserInSession()))
                .map(UserCourse::getCourse)
                .collect(Collectors.toList());
    }

    static final class CourseServiceDetailsImplementation {
        private static final CourseRepository repository;

        static {
            repository = new CourseRepository();
        }

        private static Course getCourseDetailsByName(String courseName) {
            for (Course course : repository.getAll()) {
                if (course.getName().equals(courseName))
                    return course;
            }
            return null;
        }

        private static List<Course> getAllCourses() {
            return repository.getAll();
        }

        private static Course addCourse(Course course) {
            return repository.add(course);
        }

        private static int getLatestCourseId() {
            return repository.getAll().get(repository.getAll().size() - 1).getId();
        }

        private static boolean existsCourse(int courseID) {
            for (Course course : repository.getAll()) {
                if (course.getId() == courseID)
                    return true;
            }
            return false;
        }

        private static Course editCourse(Course course) throws Exception {
            final UserService userService = new UserService();
            if (course.getCreatedBy().equals(userService.getUserInSession()))
                return repository.update(course);
            throw ErrorMessageFactory.message(ExceptionMessage.COURSE_EDIT_RESTRICTION);
        }


    }
}
