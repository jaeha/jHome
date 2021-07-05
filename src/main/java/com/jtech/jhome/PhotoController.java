package com.jtech.jhome;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/photo")
public class PhotoController {

    private static final Logger log = LoggerFactory.getLogger(PhotoController.class);

    @Autowired
    private PhotoRepository reposiotory;

    @Value("${jhome.photo.path}")
    private String photoPath;

    @GetMapping("")
    public String getPhoto(Model model) {
        return "photo";
    }


    //// API
    @GetMapping("/api/list")
    public @ResponseBody
    List<Photo> getList() {
        return reposiotory.findAll();
    }
}
