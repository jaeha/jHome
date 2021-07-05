package com.jtech.jhome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/video")
public class VideoController {

    private static final Logger log = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoRepository reposiotory;


    @Value("${jhome.video.path}")
    private String videoPath;

    @GetMapping("")
    public String getVideo(Model model) {
        return "video";
    }


    //// API
    @GetMapping("/api/list")
    public @ResponseBody
    List<Video> getList() {
        return reposiotory.findAll();
    }
}
