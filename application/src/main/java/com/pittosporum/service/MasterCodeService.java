package com.pittosporum.service;

import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.dto.MasterCodeDto;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface MasterCodeService {
    ProcessResponse<MasterCodeDto> receiveAllMasterCode();
}
