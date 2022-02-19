package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.usermanagement.dao.TvppUser;
import com.javaschool.microecare.usermanagement.dto.TVPPRoles;
import com.javaschool.microecare.usermanagement.dto.TvppUserDTO;
import com.javaschool.microecare.usermanagement.service.TVPPUsersService;
import com.javaschool.microecare.usermanagement.viewmodel.TVPPUserView;
import com.javaschool.microecare.utils.EntityCannotBeSavedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * Controller for TVPP Users page in TVPP.
 */
@Controller
@RequestMapping("${endpoints.tvpp.users.controller_path}")
public class UsersPageTVPPController {

    @Value("${directory.templates.tvpp.users}")
    private String templateFolder;
    @Value("${endpoints.tvpp.users.controller_path}")
    private String controllerPath;

    private boolean successfulAction = false;
    private String successActionName;
    private long successId;


    final TVPPUsersService tvppUsersService;
    final CommonEntityService commonEntityService;

    /**
     * Instantiates a new TVPP Users page tvpp controller.
     *
     * @param commonEntityService the CommonEntityService service with methods relevant to any entity
     * @param tvppUsersService    the TVPPUsersService
     */
    public UsersPageTVPPController(TVPPUsersService tvppUsersService, CommonEntityService commonEntityService) {
        this.tvppUsersService = tvppUsersService;
        this.commonEntityService = commonEntityService;
    }

    /**
     * Sets paths attributes for paths which are standard for CRUD operations for any entity.
     *
     * @param model the model of the page
     */
    @ModelAttribute
    public void setPathsAttributes(Model model) {
        commonEntityService.setPathsAttributes(model, controllerPath);
    }

    /**
     * Sets list of all found user views and attributes to display confirmation modal window into model
     *
     * @param model the model of the page
     */
    private void setAllUsersModel(Model model) {
        model.addAttribute("users", tvppUsersService.getAllUserViews());
        if (successfulAction) {
            model.addAllAttributes(Map.of("successfulAction", true,
                    "successEntityName", "TVPP user",
                    "successAction", successActionName,
                    "successId", successId));
        }
    }

    /**
     * Returns all users page template with required model attributes at get request.
     * Sets successfulAction to false after the actual value of the field was set into model in setAllUsersModel method
     *
     * @param model the model
     * @return all users page template
     */
    @GetMapping
    public String getUsersPage(Model model) {
        setAllUsersModel(model);
        successfulAction = false;
        return templateFolder + "users";
    }

    /**
     * Sets list of all availalbe roles into model
     *
     * @param model the model
     */
    private void setModelForUserPage(Model model) {
        TVPPRoles[] availableRoles = TVPPRoles.values();
        model.addAttribute("roles", availableRoles);
    }

    /**
     * Returns new user page at get request.
     *
     * @param tvppUserDTO the user dto which will be used to create a new tariff
     * @param model       the model of the page
     * @return new user page template
     */
    @GetMapping("${endpoints.tvpp.entity.path.new}")
    public String showNewUserPage(TvppUserDTO tvppUserDTO, Model model) {
        setModelForUserPage(model);
        return templateFolder + "new_user";
    }

    /**
     * Creates new user at post request using TvppUserDTO.
     * In case of validation errors in TvppUserDTO returns new user page with human-readable validation messages in model
     * In case if EntityCannotBeSavedException caught during saving new user returns new user page with
     * error field name and error message in model
     *
     * @param tvppUserDTO the user dto to create new user
     * @param result      binding result
     * @param model       page model
     * @return all users or new user page template depending on result of saving of the new user
     */
    @PostMapping
    public String createNewUser(@Valid TvppUserDTO tvppUserDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            setModelForUserPage(model);
            return templateFolder + "new_user";
        }
        try {
            TvppUser newUser = tvppUsersService.registerUser(tvppUserDTO);

            successfulAction = true;
            successActionName = "created";
            successId = newUser.getId();
            return "redirect:" + controllerPath;
        } catch (EntityCannotBeSavedException e) {
            model.addAttribute("errorEntity", e.getEntityName());
            model.addAttribute("errorMessage", e.getMessage());
            setModelForUserPage(model);
            return templateFolder + "new_user";
        }
    }

    /**
     * Returns update user page at get request.
     *
     * @param id    the id of the user to update
     * @param model the page model
     * @return update user page template
     */
    @GetMapping("${endpoints.tvpp.entity.path.edit}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        TvppUser user = tvppUsersService.getUser(id);

        TVPPUserView userView = new TVPPUserView(user);
        TvppUserDTO tvppUserDTO = new TvppUserDTO(user);
        setModelForUserPage(model);

        model.addAttribute("tvppUserDTO", tvppUserDTO);
        model.addAttribute("userView", userView);
        return templateFolder + "edit_user";
    }

    /**
     * Updates existing user at patch request using validated TvppUserDTO.
     * In case of validation errors in TvppUserDTO returns update user page with human-readable validation messages in model
     * In case if EntityCannotBeSavedException caught during saving updated user returns update user page with
     * error field name and error message in model
     *
     * @param id          the id of the user to update
     * @param tvppUserDTO the user dto to use to set new parameters of the tariff
     * @param result      the binding result
     * @param model       the page model
     * @return all users or update user template depending on result of saving of the new user
     */
    @PatchMapping("/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid TvppUserDTO tvppUserDTO,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            setModelForUserPage(model);
            TVPPUserView userView = tvppUsersService.getUserView(id);
            model.addAttribute("userView", userView);
            return templateFolder + "edit_user";
        }

        try {
            TvppUser updatedUser = tvppUsersService.updateUser(id, tvppUserDTO);

            successfulAction = true;
            successActionName = "updated";
            successId = updatedUser.getId();
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

    /**
     * Deletes existing user at delete request.
     *
     * @param id    the id of user to delete
     * @param model the model
     * @return all users page template
     */
    @DeleteMapping("/{id}")
    public String deleteDevice(@PathVariable("id") long id, Model model) {
        try {
            tvppUsersService.deleteUserByID(id);
            successfulAction = true;
            successActionName = "deleted";
            successId = id;

        } catch (RuntimeException e) {
            //todo: add modal for error
        }
        return "redirect:" + controllerPath;


    }


}
