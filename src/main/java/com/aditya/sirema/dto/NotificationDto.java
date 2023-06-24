package com.aditya.sirema.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;

    @NotNull(message = "User is required.")
    private UserDto user;

    @NotNull(message = "Username is required")
    private String username;

    @NotEmpty(message = "Message is required")
    private String message;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;
}
