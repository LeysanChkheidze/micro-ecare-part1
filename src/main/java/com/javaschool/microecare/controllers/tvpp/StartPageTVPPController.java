package com.javaschool.microecare.controllers.tvpp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for start page in TVPP
 */
@Controller
@RequestMapping("${endpoints.tvpp.startpage.controller_path}")
public class StartPageTVPPController {

    @Value("${directory.templates.tvpp.startpage}")
    private String templateFolder;


    /**
     * Returns start page template at get request
     *
     * @param model the model
     * @return the start page template
     */
    @GetMapping
    public String getStartPage(Model model) {
        return templateFolder + "startpage";
    }
}
