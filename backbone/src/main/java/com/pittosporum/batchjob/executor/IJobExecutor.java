package com.pittosporum.batchjob.executor;

import com.pittosporum.batchjob.model.TriggerStrategy;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface IJobExecutor {

    void execute(TriggerStrategy triggerStrategy);
}
