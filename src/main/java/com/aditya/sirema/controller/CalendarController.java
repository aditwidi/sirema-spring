package com.aditya.sirema.controller;

import com.aditya.sirema.dto.RequestDto;
import com.aditya.sirema.entity.Request;
import com.aditya.sirema.entity.User;
import com.aditya.sirema.service.RequestService;
import com.aditya.sirema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("name")
public class CalendarController {
    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    private String getLogedinUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public static class CalendarEvent {
        private String title;
        private LocalDate date;
        private String color;

        public CalendarEvent(String title, LocalDate date, String color) {
            this.title = title;
            this.date = date;
            this.color = color;
        }

        public String getTitle() {
            return title;
        }

        public LocalDate getDate() {
            return date;
        }

        public String getColor() {
            return color;
        }
    }
//    MAPPING FOR ROLE_USER
@GetMapping("/user/calendar-requests")
public String viewCalendarRequestUser(Model model) {
    User user = userService.findUserByEmail(getLogedinUsername());

    model.addAttribute("user", user);
    return "user/calendar-request";
}

    @GetMapping("/user/calendar-data")
    @ResponseBody
    public ResponseEntity<List<CalendarEvent>> getCalendarDataUser() {
        List<RequestDto> requests = requestService.getRequests();

        List<CalendarEvent> events = requests.stream()
                .filter(request -> Request.Status.Disetujui.equals(request.getStatus()))
                .map(request -> {
                    String color;
                    switch(request.getStatus()) {
                        case Pending:
                            color = "#FEB019";
                            break;
                        case Disetujui:
                            color = "#00E396";
                            break;
                        case Ditolak:
                            color = "#FF4560";
                            break;
                        default:
                            color = "gray";
                    }
                    return new CalendarEvent(request.getJudulRequest(), request.getDeadline(), color);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(events);
    }


//    MAPPING FOR ROLE_ADMIN
    @GetMapping("/staff/calendar-requests")
    public String viewCalendarRequestStaff(Model model) {
        User user = userService.findUserByEmail(getLogedinUsername());

        model.addAttribute("user", user);
        return "staff/calendar-request";
    }

    @GetMapping("/staff/calendar-data")
    @ResponseBody
    public ResponseEntity<List<CalendarEvent>> getCalendarData() {
        List<RequestDto> requests = requestService.getRequests();

        List<CalendarEvent> events = requests.stream()
                .map(request -> {
                    Request.Status status = request.getStatus(); // This is an Enum
                    String color;
                    switch(status) {
                        case Pending:
                            color = "#FEB019";
                            break;
                        case Disetujui:
                            color = "#00E396";
                            break;
                        case Ditolak:
                            color = "#FF4560";
                            break;
                        default:
                            color = "gray";
                    }
                    return new CalendarEvent(request.getJudulRequest(), request.getDeadline(), color);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(events);
    }

}
