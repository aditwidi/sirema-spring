package com.aditya.sirema.repository;

import com.aditya.sirema.entity.Request;
import com.aditya.sirema.entity.Request.Status;
import com.aditya.sirema.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByJudulRequestContaining(String searchTerm);
    List<Request> findByUser(User user);
    List<Request> findTop5ByUserOrderByCreatedAtDesc(User user);
    List<Request> findTop8ByOrderByCreatedAtDesc();
    int countByUser (User user);
    int countByUserAndStatus (User user, Status status);
    int countByStatus (Status status);
}
