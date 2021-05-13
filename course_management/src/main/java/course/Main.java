package course;

import course.constant.ServiceType;
import course.service.factory.ServiceFactory;

import java.util.Scanner;

public class Main {
    private static boolean isProcessing = true;

    private static void showMenu() {
        System.out.println("Enter service number which you want to use : ");
        System.out.println("1. Account Details Service");
        System.out.println("2. Course Details Service");
        System.out.println("3. Register-Login Service");
        System.out.println("4. Exit From System");
    }

    private static void exceptionHandler(Exception exception) {
        ServiceFactory.runService(ServiceType.EXCEPTION).service(exception);
    }

    private static void process(int chosenServiceNumber) throws Exception {
        ServiceType service = ServiceType.EXCEPTION;
        switch (chosenServiceNumber) {
            case 1:
                service = ServiceType.ACCOUNT;
                break;
            case 2:
                service = ServiceType.COURSE;
                break;
            case 3:
                service = ServiceType.FORM;
                break;
            case 4:
                service = ServiceType.COMPLETED;
                isProcessing = false;
                break;
        }
        ServiceFactory.runService(service).service();
    }

    private static void execute(Scanner scanner) {
        while (isProcessing) {
            try {
                showMenu();
                process(scanner.nextInt());
            } catch (Exception exception) {
                exceptionHandler(exception);
            }
        }
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        ServiceFactory.setScanner(scanner);
        System.out.println("Welcome Course System");
        try {
            ServiceFactory.runService(ServiceType.FORM).service();
            execute(scanner);
        } catch (Exception exception) {
            exceptionHandler(exception);
        }
    }
}

