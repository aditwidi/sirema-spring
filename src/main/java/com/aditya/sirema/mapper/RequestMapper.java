package com.aditya.sirema.mapper;

import com.aditya.sirema.dto.RequestDto;
import com.aditya.sirema.entity.Request;

import java.util.List;
import java.util.stream.Collectors;

public class RequestMapper {
    public static RequestDto mapToRequestDto(Request request) {
        RequestDto requestDto = RequestDto.builder()
                .id(request.getId())
                .user(UserMapper.mapToUserDto(request.getUser()))
                .namaPengaju(request.getNamaPengaju())
                .asalPengaju(request.getAsalPengaju())
                .nomorHandphone(request.getNomorHandphone())
                .judulRequest(request.getJudulRequest())
                .bentukRequest(request.getBentukRequest())
                .deadline(request.getDeadline())
                .status(request.getStatus())
                .createdAt(request.getCreatedAt())
                .updateAt(request.getUpdatedAt())
                .build();
        return requestDto;
    }

    public static List<RequestDto> mapToRequestDtoList(List<Request> requests) {
        return requests.stream()
                .map(RequestMapper::mapToRequestDto)
                .collect(Collectors.toList());
    }

    public static Request mapToRequest(RequestDto requestDto) {
        Request request = Request.builder()
                .id(requestDto.getId())
                .user(UserMapper.mapToUser(requestDto.getUser()))
                .namaPengaju(requestDto.getNamaPengaju())
                .asalPengaju(requestDto.getAsalPengaju())
                .nomorHandphone(requestDto.getNomorHandphone())
                .judulRequest(requestDto.getJudulRequest())
                .bentukRequest(requestDto.getBentukRequest())
                .deadline(requestDto.getDeadline())
                .status(requestDto.getStatus())
                .createdAt(requestDto.getCreatedAt())
                .updatedAt(requestDto.getUpdateAt())
                .build();
        return request;
    }
}
