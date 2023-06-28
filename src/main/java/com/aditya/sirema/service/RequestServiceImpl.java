package com.aditya.sirema.service;

import com.aditya.sirema.dto.RequestDto;
import com.aditya.sirema.entity.Request;
import com.aditya.sirema.entity.Request.Status;
import com.aditya.sirema.entity.User;
import com.aditya.sirema.mapper.RequestMapper;
import com.aditya.sirema.repository.RequestRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<RequestDto> getRequests() {
        List<Request> requests = requestRepository.findAll();
        List<RequestDto> requestDtos = requests.stream()
                .map(RequestMapper::mapToRequestDto)
                .toList();
        return requestDtos;
    }

    @Override
    public List<RequestDto> searchRequests(String namaPengaju, String bentukRequest, String judulRequest) {
        List<Request> requests = requestRepository.findAll();
        return requests.stream()
                .filter(r -> (namaPengaju == null || r.getNamaPengaju().toLowerCase().contains(namaPengaju.toLowerCase()))
                        && (bentukRequest == null || r.getBentukRequest().toString().toLowerCase().contains(bentukRequest.toLowerCase()))
                        && (judulRequest == null || r.getJudulRequest().toLowerCase().contains(judulRequest.toLowerCase())))
                .map(RequestMapper::mapToRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> findRequestsByUserId(Long userId) {
        User user = userService.findUserById(userId);
        List<Request> requests = requestRepository.findByUser(user);
        return requests.stream()
                .map(RequestMapper::mapToRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> findTop5RequestsByUser(Long userId) {
        User user = userService.findUserById(userId);
        List<Request> requests = requestRepository.findTop5ByUserOrderByCreatedAtDesc(user);
        return requests.stream()
                .map(RequestMapper::mapToRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> findTop8RequestsOrderByCreatedAtDesc() {
        List<Request> requests = requestRepository.findTop8ByOrderByCreatedAtDesc();
        return requests.stream()
                .map(RequestMapper::mapToRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto getRequest(Long requestId) {
        Optional<Request> request = requestRepository.findById(requestId);
        return request.map(RequestMapper::mapToRequestDto).orElse(null);
    }

    @Override
    public void updateRequest(RequestDto requestDto) {
        if (requestDto.getId() != null) {
            // Fetch the existing record by ID
            Optional<Request> existingRequest = requestRepository.findById(requestDto.getId());

            // If the request is present, update its fields
            if (existingRequest.isPresent()) {
                Request request = existingRequest.get();

                // Use your RequestMapper to map the fields from requestDto to request object
                // But make sure the ID field is also mapped, or simply set it like this
                request.setId(requestDto.getId());

                // Now, map other fields...
                request.setNamaPengaju(requestDto.getNamaPengaju());
                request.setAsalPengaju(requestDto.getAsalPengaju());
                request.setNomorHandphone(requestDto.getNomorHandphone());
                request.setJudulRequest(requestDto.getJudulRequest());
                request.setBentukRequest(requestDto.getBentukRequest());
                request.setDeadline(requestDto.getDeadline());
                request.setStatus(requestDto.getStatus());
                // Save the updated request back to the database
                requestRepository.save(request);
            } else {
                // Handle the case where no request with the given ID was found
                System.out.println("Request with ID " + requestDto.getId() + " not found");
            }
        } else {
            // Handle the case where RequestDto does not have an ID
            System.out.println("RequestDto ID is null");
        }
    }

    @Override
    public void deleteRequest(Long requestId) {
        requestRepository.deleteById(requestId);
    }

    @Override
    public void saveRequest(RequestDto requestDto) {
        Request request = RequestMapper.mapToRequest(requestDto);
        requestRepository.save(request);
    }

    @Override
    public int countRequests() {
        return (int) requestRepository.count();
    }

    @Override
    public int countRequestsByStatus(Status status) {
        return requestRepository.countByStatus(status);
    }

    @Override
    public int countTotalRequestByUser(Long userId) {
        User user = userService.findUserById(userId);
        return requestRepository.countByUser(user);
    }

    @Override
    public int countTotalRequestByUserAndStatus(Long userId, Status status) {
        User user = userService.findUserById(userId);
        return requestRepository.countByUserAndStatus(user, status);
    }
}
