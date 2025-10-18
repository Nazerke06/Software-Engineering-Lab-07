package com.example.LAB_05.controller;

import com.example.LAB_05.models.ApplicationRequest;
import com.example.LAB_05.repository.ApplicationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;

    @GetMapping(value = "/")
    public String index(Model model) {
        List<ApplicationRequest> applicationRequestList = applicationRequestRepository.findAll();
        model.addAttribute("applicationRequests", applicationRequestList);
        return "index";
    }

    @PostMapping(value = "/addApplicationRequest")
    public String addApplicationRequest(@RequestParam(name = "userName") String userName,
                                        @RequestParam(name = "courseName") String courseName,
                                        @RequestParam(name = "commentary") String commentary,
                                        @RequestParam(name = "phone") String phone) {

        ApplicationRequest applicationRequest = new ApplicationRequest();
        applicationRequest.setUserName(userName);
        applicationRequest.setCourseName(courseName);
        applicationRequest.setCommentary(commentary);
        applicationRequest.setPhone(phone);
        applicationRequest.setHandled(false);

        applicationRequestRepository.save(applicationRequest);
        return "redirect:/";
    }

    @GetMapping(value = "/details/{id}")
    public String details(Model model, @PathVariable(name = "id") Long id) {
        ApplicationRequest applicationRequest = applicationRequestRepository.findById(id).orElse(null);
        model.addAttribute("applicationRequest", applicationRequest);
        return "details";
    }

    @PostMapping(value = "/process/{id}")
    public String processRequest(@PathVariable(name = "id") Long id) {
        ApplicationRequest applicationRequest = applicationRequestRepository.findById(id).orElse(null);
        if (applicationRequest != null) {
            applicationRequest.setHandled(true);
            applicationRequestRepository.save(applicationRequest);
        }
        return "redirect:/details/" + id;
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

    // Фильтр: только обработанные заявки
    @GetMapping("/processed")
    public String processedRequests(Model model) {
        List<ApplicationRequest> processed = applicationRequestRepository.findByHandled(true);
        model.addAttribute("applicationRequests", processed);
        return "processed";
    }
}
