package course.service.impl;

import course.constant.ExceptionMessage;
import course.constant.Role;
import course.model.User;
import course.service.Service;
import course.service.factory.ErrorMessageFactory;
import course.service.util.RoleService;
import course.service.util.UserService;

import java.util.Scanner;

public class AccountService implements Service {

    private final UserService service;
    private final RoleService roleService = new RoleService();
    private Scanner scanner;

    public AccountService(Scanner scanner) {
        this.scanner = scanner;
        this.service = new UserService();
    }

    @Override
    public void service() throws Exception {
        serviceAccount(this.scanner);
    }

    private void showAccountMenu() {
        System.out.println("You are in Account Service. Please select number: ");
        System.out.println("1. Show Account Credentials");
        System.out.println("2. Edit Account");
        System.out.println("3. Show All Accounts in System (only for ADMIN)");
    }

    private void serviceAccount(Scanner scanner) throws Exception {
        showAccountMenu();
        int accountService = scanner.nextInt();
        switch (accountService) {
            case 1:
                showAccountCredentials();
                break;
            case 2: {
                System.out.println("Update username: ");
                String username = scanner.next();
                System.out.println("Update password: ");
                String password = scanner.next();
                String roles = roleService.getRolesAsString();
                System.out.println("Update role: " + roles);
                String role = scanner.next();
                User user = new User();
                for (Role r : roleService.getRoles()) {
                    if (role.trim().toUpperCase().equals(r.toString())) {
                        user.setRole(r);
                        break;
                    }
                }
                user.setId(getCurrentUser().getId());
                user.setUsername(username);
                user.setPassword(password);
                editAccount(user);
                break;
            }
            case 3:
                showAllAccountCredentials();
                break;

            default:
                throw ErrorMessageFactory.message(ExceptionMessage.SERVICE_NOT_FOUND_ERROR);
        }

    }

    private User getCurrentUser() {
        return service.getUserInSession();
    }

    private void showAccountCredentials() {
        User user = getCurrentUser();
        System.out.println(user);
    }

    private void editAccount(User user) {
        User userUpdated = service.updateUser(user);
        System.out.println(userUpdated);
    }

    private void showAllAccountCredentials() throws Exception {
        if (getCurrentUser().getRole() != Role.ADMIN)
            throw ErrorMessageFactory.message(ExceptionMessage.ACCOUNT_VIEW_ROLE_RESTRICTION);
        for (User user : service.getAllUsers()) {
            System.out.println(user + "\n");
        }
    }

}
