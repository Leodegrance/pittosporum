package com.pittosporum.service;

import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.dto.QuartzDto;
import com.pittosporum.dto.view.QuartzQueryDto;
import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface QuartzService {
    ProcessResponse<Void> createQuartz(QuartzDto quartzDto);

    ProcessResponse<Void> startJob(Integer jobId);

    ProcessResponse<Void> deleteQuartz(Integer jobId);

    QueryResult<QuartzQueryDto> receiveJobList(QueryParam queryParam);

    ProcessResponse<QuartzDto> getQuartzById(Integer id);
}
