package com.study.base.exception;

import java.io.Serializable;

/**
 * @Description: 和前端约定的返回异常信息模型 
 * @Author: kiligsmile
 * @Date: 2023/11/22 11:44 AM
 * @Return:
*/


public class RestErrorResponse implements Serializable {
    private String errMessage;

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
