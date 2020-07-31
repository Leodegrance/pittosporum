package com.pittosporum.constant;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class Status {
    private Status(){}

    public final static String PENDING_EXECUTE = "PE0001";
    public final static String EXECUTE_OVER = "PE0002";
    public final static String EXECUTE_FAILURE  = "PE0003";

    public final static String ACTIVE_RECORD  = "AT0001";
    public final static String INACTIVE_RECORD  = "AT0002";
    public final static String DELETE_RECORD  = "AT0003";
}
