package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.usermanagement.dao.TvppUser;
import com.javaschool.microecare.usermanagement.dto.TVPPRoles;
import com.javaschool.microecare.usermanagement.dto.TvppUserDTO;
import com.javaschool.microecare.usermanagement.service.TVPPUsersService;
import com.javaschool.microecare.usermanagement.viewmodel.TVPPUserView;
import com.javaschool.microecare.utils.EntityCannotBeSavedException;
import com.javaschool.microecare.utils.PageModelUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


    final TVPPUsersService tvppUsersService;

    public UsersPageTVPPController(TVPPUsersService tvppUsersService) {
        this.tvppUsersService = tvppUsersService;
    }

    @ModelAttribute
    public void setPathsAttributes(Model model) {
       model.addAttribute("pathNew", controllerPath + newPath);
        model.addAttribute("pathEdit", controllerPath + editPath);
        model.addAttribute("pathDeleteUpdate", controllerPath + "/{id}");
        model.addAttribute("controllerPath", controllerPath);
        //TODO: утильный метод не сеттит значения, они null. разобраться, почему
        //PageModelUtils.setStandardPathsAttributes(model, controllerPath);
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

    @PostMapping
    public String createNewDevice(@Valid TvppUserDTO tvppUserDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            setModelForUserPage(model);
            //  BindingResultUtils.setNiceValidationMessages(model, result, List.of("username"), "java.lang.NumberFormatException", priceDigitsMessage);
            return templateFolder + "new_user";
        }
        try {
            tvppUsersService.registerUser(tvppUserDTO);
            return "redirect:" + controllerPath;
        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            setModelForUserPage(model);
            return templateFolder + "new_user";
        }
    }

    @GetMapping("${endpoints.tvpp.entity.path.edit}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        TvppUser user = tvppUsersService.getUser(id);

        TVPPUserView userView = new TVPPUserView(user);
        TvppUserDTO tvppUserDTO = new TvppUserDTO(user);
        setModelForUserPage(model);

        model.addAttribute("tvppUserDTO", tvppUserDTO);
        model.addAttribute("userView", userView);
        return templateFolder + "edit_user";
    }


    @PatchMapping("/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid TvppUserDTO tvppUserDTO,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            setModelForUserPage(model);
            TVPPUserView userView = tvppUsersService.getUserView(id);
            model.addAttribute("userView", userView);
            return templateFolder + "edit_user";
        }

        try {
            tvppUsersService.updateUser(id, tvppUserDTO);
            return "redirect:" + controllerPath;

        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            setModelForUserPage(model);
            TVPPUserView userView = tvppUsersService.getUserView(id);
            model.addAttribute("userView", userView);
            return templateFolder + "edit_user";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteDevice(@PathVariable("id") int id, Model model) {
        tvppUsersService.deleteUserByID(id);
        return "redirect:" + controllerPath;

    }


}
