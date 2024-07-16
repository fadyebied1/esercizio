package com.florence.consulting.service;

import com.florence.consulting.common.CommonService;
import com.florence.consulting.common.ValidatePMEException;
import com.florence.consulting.model.db.UserPME;
import com.florence.consulting.model.so.UserSO;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class MongoUserService {
    private static final Logger logger = Logger.getLogger(MongoUserService.class);


    public List<UserPME> readAll() {
        return UserPME.findUsers();
    }

    public UserPME readUser(String idUser) throws Exception {
        List<UserPME> userPMEList = UserPME.findByIdUser(idUser);
        return getOneUserFromUserList(userPMEList);
    }

    public boolean updateUser(UserSO userSO) throws Exception {
        List<UserPME> userPMEList = UserPME.findByIdUser(userSO.getIdUser());
        UserPME userPME = getOneUserFromUserList(userPMEList);

        updateUserPME(userPME,userSO);

        userPME.update();
        return true;
    }

    public boolean createUser(UserSO userSO) throws Exception {
        List<UserPME> userPMEList = UserPME.findByIdUser(userSO.getIdUser());
        if (userPMEList.size() == 0) {
            UserPME userPME = CommonService.getObjectMapperGeneralConfig().convertValue(userSO, UserPME.class);
            userPME.persistOrUpdate();
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUser(String idUser) throws Exception {
        List<UserPME> userPMEList = UserPME.findByIdUser(idUser);
        UserPME userPME = getOneUserFromUserList(userPMEList);
        userPME.delete();
        return true;
    }

    private UserPME getOneUserFromUserList(List<UserPME> userPMEList) throws Exception {
        if (userPMEList.size() == 0) {
            ValidatePMEException validatePMEException = new ValidatePMEException();
            validatePMEException.getMessageList().add(ValidatePMEException.PMEValidateMsg.NOT_EXIST.msg);
            throw validatePMEException;
        } else if (userPMEList.size() > 1) {
            ValidatePMEException validatePMEException = new ValidatePMEException();
            validatePMEException.getMessageList().add(ValidatePMEException.PMEValidateMsg.MORE_THEN_ONE.msg);
            throw validatePMEException;
        } else
            return userPMEList.get(0);

    }

    private void updateUserPME (UserPME userPME, UserSO userSO){
        userPME.setFirstName(userSO.getFirstName());
        userPME.setLastName(userSO.getLastName());
        userPME.setEmail(userSO.getEmail());
        userPME.setAddress(userSO.getAddress());
    }
}
