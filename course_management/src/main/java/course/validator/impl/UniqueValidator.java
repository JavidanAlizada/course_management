package course.validator.impl;

import course.constant.ModelType;
import course.repository.Database;

public class UniqueValidator {

    public static boolean isNameFieldUnique(String name, ModelType type) {
        if (type == ModelType.USER)
            return Database.getUsers()
                    .stream().noneMatch(user -> user.getUsername().equals(name));
        return Database.getCourses()
                .stream().noneMatch(course -> course.getName().equals(name));
    }
}
