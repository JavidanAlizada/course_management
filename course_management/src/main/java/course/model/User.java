package course.model;

import course.constant.Role;
import course.validator.Unique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int id;
    @Unique
    private String username;
    private String password;
    private Role role;

}
