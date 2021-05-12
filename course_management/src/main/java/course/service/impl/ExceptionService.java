package course.service.impl;

import course.constant.ExceptionMessage;
import course.service.Service;

import java.util.Scanner;

public class ExceptionService implements Service {

    private Scanner scanner;

    public ExceptionService(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void service() {
        System.err.println(ExceptionMessage.SERVICE_NOT_FOUND_ERROR);
    }

    @Override
    public void service(Exception exception) {
        System.err.println(exception.getMessage());
    }
}
