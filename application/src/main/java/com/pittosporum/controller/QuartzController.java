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
import com.pittosporum.utils.CommonUtil;
import com.pittosporum.utils.ValidateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @ResponseBody
    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProcessResponse<Void> deleteById(@RequestBody Map<String,Object> params){
        if (CommonUtil.isEmpty(params)){
            return ProcessResponse.failure(AppErrorCode.PARAMS_IS_EMPTY.getStatusCode(), AppErrorCode.PARAMS_IS_EMPTY.getMessage());
        }

        int jobId = (int) params.get("jobId");

        return quartzService.deleteQuartz(jobId);
    }
}


