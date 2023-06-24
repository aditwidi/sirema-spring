package com.aditya.sirema.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "nama_pengaju", nullable = false)
    private String namaPengaju;

    @Enumerated(EnumType.STRING)
    @Column(name = "asal_pengaju", nullable = false)
    private AsalPengaju asalPengaju;

    @Column(name = "nomor_handphone", nullable = false)
    private String nomorHandphone;

    @Column(name = "judul_request", nullable = false)
    private String judulRequest;

    @Enumerated(EnumType.STRING)
    @Column(name = "bentuk_request", nullable = false)
    private BentukRequest bentukRequest;

    @Column(nullable = false)
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate updatedAt;

    public enum AsalPengaju {
        Dosen, Mahasiswa, Lainnya
    }

    public enum BentukRequest {
        Liputan, Design, Lainnya
    }

    public enum Status {
        Pending, Disetujui, Ditolak
    }
}
