package com.library.userModel;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserReader {
    Long id;
    String fullName;
    String email;
    Boolean deleted;
    LocalDateTime deletedAt;
    LocalDateTime updatedAt;
}