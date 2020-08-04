package com.pittosporum.service;

import com.pittosporum.batchjob.model.ReturnT;
import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.dto.QuartzDto;
import com.pittosporum.dto.view.QuartzQueryDto;
import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface QuartzService {
    ProcessResponse<Void> createQuartz(QuartzDto quartzDto);

    ProcessResponse<ReturnT<String>> startJob(Integer jobId);

    ProcessResponse<Void> deleteQuartz(Integer jobId);

    QueryResult<QuartzQueryDto> receiveJobList(QueryParam queryParam);

    ProcessResponse<QuartzDto> getQuartzById(Integer id);

}
