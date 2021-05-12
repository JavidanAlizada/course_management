package course;

import course.constant.ServiceType;
import course.service.ServiceFactory;

import java.util.Scanner;

public class Main {

    private static Exception exception;

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

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        ServiceFactory.setScanner(scanner);
        System.out.println("Welcome Course System");
        boolean isProcessing = true;
        try {
            ServiceFactory.runService(ServiceType.FORM).service();
            while (isProcessing) {
                try {
                    showMenu();
                    int chosenServiceNumber = scanner.nextInt();
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
                } catch (Exception exception) {
                    setException(exception);
                    ServiceFactory.runService(ServiceType.EXCEPTION).service(exception);
                    setException(null);
                }
            }
        } catch (Exception exception) {
            setException(exception);
            ServiceFactory.runService(ServiceType.EXCEPTION).service(exception);
            setException(null);
        }
    }
}
