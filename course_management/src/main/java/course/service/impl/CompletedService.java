package course.service.impl;

import course.service.Service;

public class CompletedService implements Service {

    @Override
    public void service() {
        serviceCompleted();
    }

    private void showCompletedMenu() {
        System.out.println("Process finished successfully.");
    }

    private void serviceCompleted() {
        showCompletedMenu();
    }
}
