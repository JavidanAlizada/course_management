package course.service.impl;

import course.constant.ExceptionMessage;
import course.constant.ModelType;
import course.constant.Role;
import course.model.User;
import course.service.Service;
import course.service.factory.ErrorMessageFactory;
import course.service.util.Register;
import course.service.util.RoleService;
import course.validator.impl.UniqueValidator;

import java.util.Scanner;

public class Form implements Service {
    private final RoleService roleService = new RoleService();
    private final Scanner scanner;

    public Form(Scanner scanner) {
        this.scanner = scanner;
    }


    @Override
    public void service() throws Exception {
        serviceForm(this.scanner);
    }

    private void showFormMenu() {
        System.out.println("You are in Form Service. Please select number: ");
        System.out.println("1. Login");
        System.out.println("2. Register");
    }

    private void serviceForm(Scanner sc) throws Exception {
        Register register = new Register();
        showFormMenu();
        int choose = sc.nextInt();
        switch (choose) {
            case 1: {
                System.out.println("Enter your username: ");
                String username = sc.next();
                System.out.println("Enter your password: ");
                String password = sc.next();
                User signedUser = register.signIn(username, password);
                if (signedUser != null)
                    System.out.println("Login success");
                else
                    throw ErrorMessageFactory.message(ExceptionMessage.WRONG_CREDENTIALS);
                break;
            }
            case 2: {
                System.out.println("Enter your username: ");
                String username = sc.next();
                if (!UniqueValidator.isNameFieldUnique(username, ModelType.USER))
                    throw ErrorMessageFactory.message(ExceptionMessage.UNIQUE_NAME);
                System.out.println("Enter your password: ");
                String password = sc.next();
                String roles = roleService.getRolesAsString();
                System.out.println("Enter your role: " + roles);
                String role = sc.next();
                User user = new User();
                for (Role r : roleService.getRoles()) {
                    if (role.trim().toUpperCase().equals(r.toString())) {
                        user.setRole(r);
                        break;
                    }
                }
                if (user.getRole() == null)
                    throw ErrorMessageFactory.message(ExceptionMessage.ROLE_NOT_FOUND);
                user.setId(1);
                user.setUsername(username);
                user.setPassword(password);
                User createdUser = register.signUp(user);
                if (createdUser != null)
                    System.out.println("User created successfully");
                else
                    throw ErrorMessageFactory.message(ExceptionMessage.REGISTER_ERROR);
                break;
            }
            default:
                throw ErrorMessageFactory.message(ExceptionMessage.SERVICE_NOT_FOUND_ERROR);
        }
    }


}
