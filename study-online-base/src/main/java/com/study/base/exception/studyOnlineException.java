package com.study.base.exception;

/**
 * @Description: 本项目自定义异常类型
 * @Author: kiligsmile
 * @Date: 2023/11/22 11:06 PM
*/


public class studyOnlineException extends RuntimeException{
    private String errMessage;

    public studyOnlineException() {
        super();
    }

    public studyOnlineException(String Message) {
        super(Message);
        this.errMessage = Message;
    }

    public String getErrMessage() {
        return errMessage;
    }



    public static void cast(CommonError commonError){
        throw new studyOnlineException(commonError.getErrMessage());
    }
    public static void cast(String errMessage){
        throw new studyOnlineException(errMessage);
    }
}
