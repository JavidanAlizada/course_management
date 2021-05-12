package course.service;

import course.constant.ServiceType;
import course.service.impl.*;

import java.util.Scanner;

public abstract class ServiceFactory {

    private static Scanner scanner;

    public static void setScanner(Scanner scanner) {
        ServiceFactory.scanner = scanner;
    }

    public static Service runService(ServiceType type) {
        switch (type) {
            case FORM:
                return new Form(scanner);
            case COURSE:
                return new CourseService(scanner);
            case ACCOUNT:
                return new AccountService(scanner);
            case COMPLETED:
                return new CompletedService();
            case EXCEPTION:
                return new ExceptionService(scanner);
            default:
                return null;
        }
    }
}
