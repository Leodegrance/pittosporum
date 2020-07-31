package com.pittosporum.controller;

import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.constant.ValidateResult;
import com.pittosporum.constant.app.AppErrorCode;
import com.pittosporum.dto.DataBaseProfileDto;
import com.pittosporum.dto.SQLStoreDto;
import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;
import com.pittosporum.dto.view.SQLStoreQueryDto;
import com.pittosporum.service.StoreService;
import com.pittosporum.utils.ValidateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public List<SQLStoreDto> receiveSqlStore(@PathVariable("profileId") Integer profileId, @PathVariable("status") String status){
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

    /**
     *
     * @param queryParam
     * @return
     */
    @ResponseBody
    @PostMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueryResult<SQLStoreQueryDto> receiveStoreData(@RequestBody QueryParam queryParam){
        return storeService.receiveStoreData(queryParam);
    }

    @ResponseBody
    @GetMapping(value = "profile-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DataBaseProfileDto> receiveDataBaseProfile(){
        return storeService.receiveDataBaseProfile();
    }
}
