package com.jtech.jhome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/calendar")
public class CalendarController {

    private static final Logger log = LoggerFactory.getLogger(CalendarController.class);

//    @Autowired
//    private DocRepository repository;

    @GetMapping("")
    public String getCalendar(Model model, @PageableDefault(size = 15) Pageable pageable) {
        //log.info("hit /doc");

       // model.addAttribute("page", repository.findAll(pageable));

        return "test";
    }

}