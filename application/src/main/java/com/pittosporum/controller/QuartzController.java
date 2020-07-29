package com.pittosporum.controller;

import com.pittosporum.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pittosporum.constant.ProcessResponse;
import pittosporum.constant.ValidateResult;
import pittosporum.constant.app.AppErrorCode;
import pittosporum.dto.QuartzDto;
import pittosporum.dto.view.QuartzQueryDto;
import pittosporum.dto.view.QueryParam;
import pittosporum.dto.view.QueryResult;
import pittosporum.utils.ValidateHelper;

import java.util.HashMap;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@RestController
@RequestMapping(value = "/quartz-mgr")
public class QuartzController {
    @Autowired
    private QuartzService quartzService;

    /**
     * create quartz
     * @param quartzDto
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProcessResponse<Void> createQuartz(@RequestBody QuartzDto quartzDto){
        ValidateResult validateResult = ValidateHelper.validate(quartzDto, "create");
        if (validateResult.isHasError()){
            HashMap<String, String> errorMap =  validateResult.getErrorMap();
            return ProcessResponse.failure(AppErrorCode.PARAM_ERROR.getStatusCode(), ValidateHelper.buildHTMLErrorStr(errorMap));
        }

        return quartzService.createQuartz(quartzDto);
    }

    @ResponseBody
    @PostMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueryResult<QuartzQueryDto> receiveJobList(@RequestBody QueryParam queryParam){
        return quartzService.receiveJobList(queryParam);
    }
}


