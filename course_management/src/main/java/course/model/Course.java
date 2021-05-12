package course.model;

import course.validator.Unique;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    private int id;
    @Unique
    private String name;
    private String description;
    private String content;
    private int price;
    private LocalDateTime createdOn;
    private User createdBy;

}
