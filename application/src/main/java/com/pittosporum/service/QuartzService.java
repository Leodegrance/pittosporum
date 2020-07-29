package com.pittosporum.service;

import pittosporum.constant.ProcessResponse;
import pittosporum.dto.QuartzDto;
import pittosporum.dto.view.QuartzQueryDto;
import pittosporum.dto.view.QueryParam;
import pittosporum.dto.view.QueryResult;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface QuartzService {
    ProcessResponse<Void> createQuartz(QuartzDto quartzDto);

    ProcessResponse<Void> startJob(Integer jobId);

    ProcessResponse<Void> deleteQuartz(Integer jobId);

    QueryResult<QuartzQueryDto> receiveJobList(QueryParam queryParam);
}
