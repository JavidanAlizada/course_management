package course;

import course.constant.ServiceType;
import course.service.factory.ServiceFactory;

import java.util.Scanner;

public class Main {
    private static Exception exception;
    private static boolean isProcessing = true;

    public static void setException(Exception exception) {
        Main.exception = exception;
    }

    private static void showMenu() {
        System.out.println("Enter service number which you want to use : ");
        System.out.println("1. Account Details Service");
        System.out.println("2. Course Details Service");
        System.out.println("3. Register-Login Service");
        System.out.println("4. Exit From System");
    }

    private static void exceptionHandler(Exception exception) {
        setException(exception);
        ServiceFactory.runService(ServiceType.EXCEPTION).service(exception);
        setException(null);
    }

    private static void process(int chosenServiceNumber) throws Exception {
        switch (chosenServiceNumber) {
            case 1:
                ServiceFactory.runService(ServiceType.ACCOUNT).service();
                break;
            case 2:
                ServiceFactory.runService(ServiceType.COURSE).service();
                break;
            case 3:
                ServiceFactory.runService(ServiceType.FORM).service();
                break;
            case 4:
                ServiceFactory.runService(ServiceType.COMPLETED).service();
                isProcessing = false;
                break;
            default:
                ServiceFactory.runService(ServiceType.EXCEPTION).service();
                break;
        }
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

