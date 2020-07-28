package com.pittosporum.service;

import pittosporum.constant.ProcessResponse;
import pittosporum.dto.QuartzDto;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface QuartzService {
    ProcessResponse<Void> createQuartz(QuartzDto quartzDto);

    ProcessResponse<Void> startJob(Integer jobId);

    ProcessResponse<Void> deleteQuartz(Integer jobId);
}
