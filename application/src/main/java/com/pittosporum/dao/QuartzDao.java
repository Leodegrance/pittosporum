package com.pittosporum.dao;

import com.pittosporum.entity.Quartz;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface QuartzDao {
    void createQuartz(Quartz quartz);

    Quartz getQuartzById(Integer id);
}
