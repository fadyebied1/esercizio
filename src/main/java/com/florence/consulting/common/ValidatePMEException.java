package com.florence.consulting.common;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public @Data class ValidatePMEException extends Exception{
    private List<String> messageList;

    public ValidatePMEException(){
        this.messageList = new ArrayList<>();
    }



    public enum PMEValidateMsg{
        MORE_THEN_ONE("Più di una"),
        NOT_EXIST("Non esiste");


        public final String msg;

        PMEValidateMsg(String msg){
            this.msg = msg;
        }
    }


}
