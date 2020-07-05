package com.pittosporum.controller;

import com.pittosporum.constant.AppErrorCode;
import com.pittosporum.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pittosporum.core.ProcessResponse;
import pittosporum.dto.SQLStoreDto;
import pittosporum.utils.CommonUtil;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@RestController
@RequestMapping(value = "/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * receiveSqlStore
     * @param profileId
     * @param status
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/profile/{profileId}/store/{status}", produces = MediaType.APPLICATION_JSON_VALUE  )
    public List<SQLStoreDto> receiveSqlStore(@PathVariable("profileId") String profileId, @PathVariable("status") String status){
        return storeService.receiveSqlStore(profileId, status);
    }

    /**
     *
     * @param store
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        public ProcessResponse<Void> createStore(@RequestBody SQLStoreDto store){
        if (store == null){
            ProcessResponse.failure(AppErrorCode.PARAMS_IS_EMPTY.getStatusCode(), AppErrorCode.PARAMS_IS_EMPTY.getMessage());
        }
        return storeService.createStore(store);
    }

    /**
     *
     * @param stores
     * @return
     */
    @PostMapping(value = "list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProcessResponse<Void> createStoreList(@RequestBody List<SQLStoreDto> stores){
        if (CommonUtil.isEmpty(stores)){
            ProcessResponse.failure(AppErrorCode.PARAMS_IS_EMPTY.getStatusCode(), AppErrorCode.PARAMS_IS_EMPTY.getMessage());
        }
        return storeService.createStoreList(stores);
    }
}
