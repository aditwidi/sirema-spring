package com.aditya.sirema.dto;

import com.aditya.sirema.entity.Request;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private Long id;

    @NotNull(message = "User is required.")
    private UserDto user;

    @NotEmpty(message = "Nama Pengaju is required")
    private String namaPengaju;

    @NotNull(message = "Asal Pengaju is required")
    private Request.AsalPengaju asalPengaju;

    @NotEmpty(message = "Nomor Handphone is required")
    private String nomorHandphone;

    @NotEmpty(message = "Judul Request is required")
    private String judulRequest;

    @NotNull(message = "Bentuk Request is required")
    private Request.BentukRequest bentukRequest;

    @NotNull(message = "Deadline is required")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate deadline;

    @NotNull(message = "Status is required")
    private Request.Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate updateAt;
}
