package course.service.util;

import course.constant.Role;

import java.util.Arrays;

public class RoleService {

    public Role[] getRoles() {
        return Role.values();
    }

    public String getRolesAsString() {
        StringBuilder roles = new StringBuilder();
        Arrays.stream(getRoles()).forEach(role -> {
            roles.append("\n");
            roles.append(role.toString());
        });
        return roles.toString();
    }
}
