package com.pittosporum.controller;

import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.service.ExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@RestController
@RequestMapping(value = "/execute")
public class ExecuteController {

    @Autowired
    private ExecuteService executeService;

    @ResponseBody
    @PostMapping(value = "/run-sql-list-by-store-ids", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE  )
    public ProcessResponse<Void> executeSqlList(@RequestBody List<Integer> storeIds){
        return executeService.executeSqlList(storeIds);
    }

    @ResponseBody
    @GetMapping(value = "/run-sql-by-store-id/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE  )
    public ProcessResponse<Void> executeSqlByStoreId(@PathVariable("storeId") Integer storeId){
          return executeService.executeSqlByStoreId(storeId);
    }
}
