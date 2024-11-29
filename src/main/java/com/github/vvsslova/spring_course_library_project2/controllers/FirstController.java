package com.github.vvsslova.spring_course_library_project2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FirstController {

    @RequestMapping("/")
    public String showFirstView(){
        return "first-view";
    }
}
