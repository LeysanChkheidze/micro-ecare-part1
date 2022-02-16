package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.usermanagement.dto.TVPPRoles;
import com.javaschool.microecare.usermanagement.dto.TvppUserDTO;
import com.javaschool.microecare.usermanagement.service.TVPPUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${endpoints.tvpp.users.controller_path}")
public class UsersPageTVPPController {

    @Value("${directory.templates.tvpp.users}")
    private String templateFolder;
    @Value("${endpoints.tvpp.entity.path.new}")
    private String newPath;
    @Value("${endpoints.tvpp.entity.path.edit}")
    private String editPath;
    @Value("${endpoints.tvpp.users.controller_path}")
    private String controllerPath;


    final
    TVPPUsersService tvppUsersService;

    public UsersPageTVPPController(TVPPUsersService tvppUsersService) {
        this.tvppUsersService = tvppUsersService;
    }

    @ModelAttribute
    public void setPathsAttributes(Model model) {
        model.addAttribute("pathNew", controllerPath + newPath);
        model.addAttribute("pathEdit", controllerPath + editPath);
        model.addAttribute("pathDeleteUpdate", controllerPath + "/{id}");
        model.addAttribute("controllerPath", controllerPath);
    }

    @GetMapping
    public String getUsersPage(Model model) {
        model.addAttribute("users", tvppUsersService.getAllUserViews());
        return templateFolder + "users";
    }

    private void setModelForUserPage(Model model) {
        TVPPRoles[] availableRoles = TVPPRoles.values();
        model.addAttribute("roles", availableRoles);
    }

    @GetMapping("${endpoints.tvpp.entity.path.new}")
    public String showNewDevicePage(TvppUserDTO tvppUserDTO, Model model) {
        setModelForUserPage(model);
        return templateFolder + "new_user";
    }


}
