package com.example.LAB_05.controller;

import com.example.LAB_05.entity.Courses;
import com.example.LAB_05.entity.Operators;
import com.example.LAB_05.models.ApplicationRequest;
import com.example.LAB_05.repository.ApplicationRequestRepository;
import com.example.LAB_05.repository.CourseRepository;
import com.example.LAB_05.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class HomeController {

    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("applicationRequests", applicationRequestRepository.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String addRequestPage(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "addRequest";
    }

    @PostMapping("/requests/add")
    public String addRequest(
            @RequestParam String userName,
            @RequestParam String commentary,
            @RequestParam String phone,
            @RequestParam Long courseId) {

        Courses course = courseRepository.findById(courseId).orElseThrow();
        ApplicationRequest request = new ApplicationRequest();
        request.setUserName(userName);
        request.setCommentary(commentary);
        request.setPhone(phone);
        request.setCourse(course);
        request.setHandled(false);

        applicationRequestRepository.save(request);
        return "redirect:/";
    }

    @GetMapping("/process/{id}")
    public String processRequestPage(Model model, @PathVariable Long id) {
        ApplicationRequest request = applicationRequestRepository.findById(id).orElse(null);
        model.addAttribute("applicationRequest", request);
        model.addAttribute("operators", operatorRepository.findAll());
        return "assignOperators";
    }

    @PostMapping("/requests/{requestId}/assignOperators")
    public String assignOperatorsToRequest(
            @PathVariable Long requestId,
            @RequestParam List<Long> operatorIds) {

        ApplicationRequest request = applicationRequestRepository.findById(requestId).orElseThrow();
        List<Operators> selectedOperators = operatorRepository.findAllById(operatorIds);
        request.setOperators(selectedOperators);

        applicationRequestRepository.save(request);
        return "redirect:/";
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteRequest(@PathVariable(name = "id") Long id) {
        applicationRequestRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/pending")
    public String pendingRequests(Model model) {
        List<ApplicationRequest> pending = applicationRequestRepository.findByHandled(false);
        model.addAttribute("applicationRequests", pending);
        return "pending";
    }

    @GetMapping("/processed")
    public String processedRequests(Model model) {
        List<ApplicationRequest> processed = applicationRequestRepository.findByHandled(true);
        model.addAttribute("applicationRequests", processed);
        return "processed";
    }
}
