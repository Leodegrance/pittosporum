package com.pittosporum.dao;

import com.pittosporum.dto.view.QuartzQueryDto;
import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;
import com.pittosporum.entity.Quartz;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface QuartzDao {
    void createQuartz(Quartz quartz);

    Quartz getQuartzById(Integer id);

    QueryResult<QuartzQueryDto> receiveJobList(QueryParam queryParam);
}
