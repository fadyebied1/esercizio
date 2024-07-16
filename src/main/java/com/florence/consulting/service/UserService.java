package com.florence.consulting.service;

import com.florence.consulting.common.CommonService;
import com.florence.consulting.common.ValidatePMEException;
import com.florence.consulting.model.db.UserPME;
import com.florence.consulting.model.so.CSVUpload;
import com.florence.consulting.model.so.UserSO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jboss.logging.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);

    @Inject
    MongoUserService mongoUserService;

    public List<UserSO> readAll() {
        return convertSOListFromPMEList(UserPME.findUsers());
    }

    public UserSO readUser(String idUser) throws Exception {

        return CommonService.getObjectMapperGeneralConfig().convertValue(mongoUserService.readUser(idUser),UserSO.class);
    }

    public boolean updateUser(UserSO userSO) throws Exception {
        return mongoUserService.updateUser(userSO);
    }

    public boolean createUser(UserSO userSO) throws Exception {
        return mongoUserService.createUser(userSO);
    }

    public boolean deleteUser(String idUser) throws Exception {
       return mongoUserService.deleteUser(idUser);
    }

    private List<UserSO> convertSOListFromPMEList(List<UserPME> userPMEList)  {
        List<UserSO> userSOList = new ArrayList<>();

        userPMEList.stream().forEach(userPME->
                userSOList.add(CommonService.getObjectMapperGeneralConfig().convertValue(userPME, UserSO.class))
        );

        return userSOList;
    }

    public List<UserSO> searchUserSO(UserSO userSO) {
        return convertSOListFromPMEList(UserPME.findByUserSO(userSO));
    }

    public void uploadCSV(CSVUpload csvUpload) throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().build();
        File csv = csvUpload.csvUpload.filePath().toFile();
        try (Reader reader = new FileReader(csv)){
            Iterable<CSVRecord> records = csvFormat.parse(reader);
            int i = 1;
            for (CSVRecord record : records) {
                UserPME userPME = new UserPME();
                userPME.setIdUser(record.get(0));
                userPME.setFirstName(record.get(1));
                userPME.setLastName(record.get(2));
                userPME.setEmail(record.get(3));
                userPME.setAddress(record.get(4));
                userPME.persistOrUpdate();
            }

        }


    }
}
