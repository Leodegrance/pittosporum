package com.pittosporum.controller;

import com.pittosporum.service.ExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pittosporum.core.ProcessResponse;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@RestController
@RequestMapping(value = "/execute")
public class ExecuteController {

    @Autowired
    private ExecuteService executeService;

    @ResponseBody
    @GetMapping(value = "/run-sql-by-store-id/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE  )
    public ProcessResponse<Void> executeSqlByStoreId(@PathVariable("storeId") String storeId){
        return executeService.executeSqlByStoreId(storeId);
    }
}
