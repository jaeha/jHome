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
@RequestMapping(value="/music")
public class MusicController {

    private static final Logger log = LoggerFactory.getLogger(MusicController.class);

    @Autowired
    private MusicRepository reposiotory;

    @Value("${jhome.music.path}")
    private String musicPath;

    @GetMapping("")
    public String getMusic(Model model) {
        return "music";
    }


    //// API
    @GetMapping("/api/list")
    public @ResponseBody
    List<Music> getList() {
        return reposiotory.findAll();
    }
}
