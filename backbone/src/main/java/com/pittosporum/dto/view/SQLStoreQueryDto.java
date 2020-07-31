package com.pittosporum.dto.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yichen(graffitidef @ gmail.com)
 */
@Getter
@Setter
public class SQLStoreQueryDto implements Serializable {
    private static final long serialVersionUID = 7379775484742794324L;

    private Integer id;

    private String executeSql;

    private String executeResult;

    private String profileName;

    private Integer profileId;

    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDt;

    private String status;

    private Integer runCount;
}
