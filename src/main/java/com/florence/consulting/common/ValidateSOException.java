package com.florence.consulting.common;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public @Data class ValidateSOException extends Exception{
    private List<String> messageList;

    public ValidateSOException(){
        this.messageList = new ArrayList<>();
    }



    public enum UsreValidateMsg{
        INVALID_FIRST_NAME("Nome è richiesto"),
        INVALID_LAST_NAME("Cognome è richiesto"),
        INVALID_EMAIL("E-Mail è richiesto"),
        EMPTY("user vuoto");

        public final String msg;

        UsreValidateMsg(String msg){
            this.msg = msg;
        }
    }


}
