package com.florence.consulting.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.florence.consulting.model.so.UserSO;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CommonService {

    private static final Logger logger = Logger.getLogger(CommonService.class);

    public static ValidateSOException validateUser(UserSO userSO) {
        ValidateSOException validateSOException = new ValidateSOException();

        if (userSO != null) {
            if (userSO.getFirstName() == null || userSO.getFirstName().isBlank())
                validateSOException.getMessageList().add(ValidateSOException.UsreValidateMsg.INVALID_FIRST_NAME.msg);
            if (userSO.getLastName() == null || userSO.getLastName().isBlank())
                validateSOException.getMessageList().add(ValidateSOException.UsreValidateMsg.INVALID_LAST_NAME.msg);
            if (userSO.getEmail() == null || userSO.getEmail().isBlank())
                validateSOException.getMessageList().add(ValidateSOException.UsreValidateMsg.INVALID_EMAIL.msg);
        } else {
            validateSOException.getMessageList().add(ValidateSOException.UsreValidateMsg.EMPTY.msg);
        }

        return validateSOException;
    }

    public static ObjectMapper getObjectMapperGeneralConfig() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
