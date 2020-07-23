package com.pittosporum.controller;

import com.pittosporum.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pittosporum.constant.ProcessResponse;
import pittosporum.constant.ValidateResult;
import pittosporum.constant.app.AppErrorCode;
import pittosporum.dto.DataBaseProfileDto;
import pittosporum.dto.SQLStoreDto;
import pittosporum.utils.ValidateHelper;

import java.util.HashMap;
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
        ValidateResult validateResult = ValidateHelper.validate(store, "create");
        if (validateResult.isHasError()){
            HashMap<String, String> errorMap =  validateResult.getErrorMap();
            return ProcessResponse.failure(AppErrorCode.PARAM_ERROR.getStatusCode(), ValidateHelper.buildHTMLErrorStr(errorMap));
        }

        return storeService.createStore(store);
    }

    @ResponseBody
    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SQLStoreDto> receiveStoreData(){
        return storeService.receiveStoreData();
    }

    @ResponseBody
    @GetMapping(value = "profile-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DataBaseProfileDto> receiveDataBaseProfile(){
        return storeService.receiveDataBaseProfile();
    }
}
