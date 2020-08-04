package com.pittosporum.controller;

import com.pittosporum.dto.MasterCodeDto;
import com.pittosporum.service.MasterCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@RestController
@RequestMapping(value = "/master-code")
public class MasterCodeController {

    @Autowired
    private MasterCodeService masterCodeService;

    @ResponseBody
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE  )
    public List<MasterCodeDto> receiveAllMasterCode(){
        return masterCodeService.receiveAllMasterCode();
    }
}
