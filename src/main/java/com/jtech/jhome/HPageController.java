package com.jtech.jhome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="/pages")
public class HPageController {

    private static final Logger log = LoggerFactory.getLogger(HPageController.class);

    @Autowired
    private HPageRepository pageRepository;

    @Autowired
    private HPageContentRepository contentRepository;

    @GetMapping("")
    public String getPages(Model model, @PageableDefault(size = 15) Pageable pageable) {
        return "pages";
    }

   @GetMapping("/listpage")
    public @ResponseBody
    Page<HPage> getListPage(Model model, @PageableDefault(size = 15) Pageable pageable) {
        return pageRepository.findAll(pageable);
    }

    @GetMapping("/list")
    public @ResponseBody
    List<HPage> getList() {
        return pageRepository.findAll();
    }

    @GetMapping("/view")
    public String getView(Model model, @RequestParam(value = "id", defaultValue = "0") long id) {
        String content="";

        HPageContent hContent = contentRepository.findById(id);

        if (hContent != null) {
            //log.info("[pages/view] hContent" + hContent.toString());
            model.addAttribute("content", hContent.getContent().toString());
        } else {
            model.addAttribute("content", "Content ID=" + id + " does not exist!");
        }

        return "view";
    }

    @RequestMapping("/edit")
    public String getEdit(Model model, @RequestParam(value = "id", defaultValue = "0") long id) {
        HPage hPage = null;
        String content = "";

        log.info("[pages/edit] id:" + id);

        if (id > 0) {
            hPage = pageRepository.findById(id);
            if (hPage != null) {
                log.info("[pages/edit] hPage.contentId:" + hPage.getContentId());
                content = contentRepository.findById(hPage.getContentId()).get().getContent();
                log.info("[pages/edit] content:" + content);
            }
        } else if (id == 0) {
            hPage = new HPage("", 0, "");
            hPage.setId(0);
        }

        if (hPage == null) {
            model.addAttribute("errorMessage", "Page ID=" + id + " does not exist!");
            return "error"; //go to error page
        }

        model.addAttribute("hPage", hPage);
        model.addAttribute("content", content);
        return "edit";
    }

    @PostMapping("/save")
    public String postSave(Model model, @Param("id") long id, @Param("title") String title,
                           @Param("content") String content,  @PageableDefault(size = 15) Pageable pageable) {

        log.info("[pages] id:" + id);
        log.info("[pages] title:" + title);
        log.info("[pages] content:" + content);

        if (id > 0) {
            log.info("[pages] trying to find page id=" + id);
            HPage hPage = pageRepository.findById(id);
            if (hPage != null) {
                // save page
                hPage.setData(title, hPage.getContentId(), hPage.getAuthor());
                HPageContent hContent = contentRepository.findById(hPage.getContentId().longValue());
                hContent.setData(content);
                pageRepository.save(hPage);
                contentRepository.save(hContent);
                return "pages";
            }
            else {
                model.addAttribute("errorMessage", "Page ID=" + id + " or content does not exist!");
                return "error";
            }
        }

        // save as a new page
        if (id == 0) {
            long contentId = contentRepository.save(new HPageContent(content)).getId();
            pageRepository.save(new HPage(title, contentId, "unknown"));
        }

        // return all pages
      //  model.addAttribute("page", repository.findAll(pageable));
        return "pages";
    }
}
