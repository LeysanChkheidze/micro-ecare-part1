package com.javaschool.microecare.controllers.tvpp;

import com.javaschool.microecare.catalogmanagement.service.TariffsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${endpoints.tvpp.tariffs.controller_path}")
public class TariffsPageTVPPController {
    @Value("${directory.templates.tvpp.tariffs}")
    private String templateFolder;
    @Value("${endpoints.tvpp.entity.path.new}")
    private String newPath;
    @Value("${endpoints.tvpp.entity.path.edit}")
    private String editPath;
    @Value("${endpoints.tvpp.tariffs.controller_path}")
    private String controllerPath;

    final TariffsService tariffsService;


    public TariffsPageTVPPController(TariffsService tariffsService) {
        this.tariffsService = tariffsService;
    }
}
