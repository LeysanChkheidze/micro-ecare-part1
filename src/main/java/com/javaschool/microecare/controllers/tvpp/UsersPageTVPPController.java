package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.usermanagement.service.TVPPUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${endpoints.tvpp.users.controller_path}")
public class UsersPageTVPPController {

    @Value("${directory.templates.tvpp.users}")
    private String templateFolder;

    final
    TVPPUsersService tvppUsersService;

    public UsersPageTVPPController(TVPPUsersService tvppUsersService) {
        this.tvppUsersService = tvppUsersService;
    }

    @GetMapping
    public String getUsersPage(Model model) {
        model.addAttribute("users", tvppUsersService.getAllUserViews());
        return templateFolder + "users";
    }


}
