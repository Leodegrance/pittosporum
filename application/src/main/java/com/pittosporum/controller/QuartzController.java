package com.pittosporum.controller;

import com.pittosporum.batchjob.model.ReturnT;
import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.constant.ValidateResult;
import com.pittosporum.constant.app.AppErrorCode;
import com.pittosporum.dto.QuartzDto;
import com.pittosporum.dto.view.QuartzQueryDto;
import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;
import com.pittosporum.service.QuartzService;
import com.pittosporum.utils.ValidateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/start/{id}/quartz", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProcessResponse<ReturnT<String>> startJob(@PathVariable("id") Integer id){
        if (id == null){
            return ProcessResponse.failure(AppErrorCode.PARAM_ERROR.getStatusCode(), AppErrorCode.PARAM_ERROR.getMessage());
        }

        return quartzService.startJob(id);
    }

    @ResponseBody
    @PostMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueryResult<QuartzQueryDto> receiveJobList(@RequestBody QueryParam queryParam){
        return quartzService.receiveJobList(queryParam);
    }

    @ResponseBody
    @GetMapping(value = "/{id}/quartz", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProcessResponse<QuartzDto> getQuartzById(@PathVariable("id") Integer id){
        if (id == null){
            return ProcessResponse.failure(AppErrorCode.PARAM_ERROR.getStatusCode(), AppErrorCode.PARAM_ERROR.getMessage());
        }

        return quartzService.getQuartzById(id);
    }
}


