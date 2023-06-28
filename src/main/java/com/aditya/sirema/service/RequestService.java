package com.aditya.sirema.service;

import com.aditya.sirema.dto.RequestDto;
import com.aditya.sirema.entity.Request.Status;
import com.aditya.sirema.entity.Request;

import java.util.List;

public interface RequestService {
    public List<RequestDto> getRequests();

    public List<RequestDto> searchRequests(String namaPengaju, String bentukRequest, String judulRequest);

    public List<RequestDto> searchRequestsUser(String namaPengaju, String bentukRequest, String judulRequest, Long userId);

    public List<RequestDto> findRequestsByUserId(Long userId);

    public List<RequestDto> findTop5RequestsByUser(Long userId);

    public  List<RequestDto> findTop8RequestsOrderByCreatedAtDesc();

    public RequestDto getRequest(Long requestId);

    public void updateRequest(RequestDto requestDto);

    public void deleteRequest(Long requestId);

    public void saveRequest(RequestDto requestDto);

    public int countRequests();

    public int countRequestsByStatus(Status status);

    public int countTotalRequestByUser(Long userId);

    public int countTotalRequestByUserAndStatus(Long userId, Status status);
}
