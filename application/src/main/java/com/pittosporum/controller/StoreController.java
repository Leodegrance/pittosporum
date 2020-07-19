package com.pittosporum.controller;

import com.pittosporum.entity.SQLStore;
import com.pittosporum.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pittosporum.constant.ProcessResponse;
import pittosporum.constant.ValidateResult;
import pittosporum.constant.app.AppErrorCode;
import pittosporum.dto.SQLStoreDto;
import pittosporum.utils.BeanUtil;
import pittosporum.utils.CommonUtil;
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
            return ProcessResponse.failure(AppErrorCode.PARAM_ERROR.getStatusCode(), ValidateHelper.generateErrorStr(errorMap));
        }

        return storeService.createStore(store);
    }

    public static void main(String[] args) {
        SQLStoreDto sqlStoreDto = new SQLStoreDto();
        sqlStoreDto.setProfileId(111);
        sqlStoreDto.setId(222);
        sqlStoreDto.setCreateBy("test");
        sqlStoreDto.setExecuteResult("aaaaa");
        sqlStoreDto.setExecuteSql("aaaaa");

        SQLStore sqlStore = BeanUtil.copyProperties(sqlStoreDto, SQLStore.class);

        System.out.println(sqlStore);
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
