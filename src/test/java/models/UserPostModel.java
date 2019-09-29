package models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@FieldDefaults(level = AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
public class UserPostModel {
        Integer userId;
        Integer id;
        String title;
        String body;
}
