package com.florence.consulting.model.db;

import com.florence.consulting.model.so.UserSO;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MongoEntity(database = "florence-consulting")
public @Data class UserPME extends PanacheMongoEntity {

    private String idUser;
    private String firstName;
    private String lastName;
    private @Email String email;
    private String address;

    public static List<UserPME> findUsers() {
        return UserPME.findAll().list();
    }

    public static List<UserPME> findByIdUser(String idUser) {
        return UserPME.find("idUser = ?1", idUser).list();
    }

    public static List<UserPME> findByUserSO(UserSO userSO) {
        Map<String, Object> params = new HashMap<>();
        if (userSO.getFirstName() != null && !userSO.getFirstName().isBlank())
            params.put(UserField.firstName.name(),userSO.getFirstName());
        if (userSO.getLastName() != null && !userSO.getLastName().isBlank())
            params.put(UserField.lastName.name(), userSO.getLastName());

        if (params.get(UserField.firstName.name()) != null && params.get(UserField.lastName.name())!=null)
            return UserPME.find(UserField.firstName+" = ?1 and "+UserField.lastName+" = ?2", userSO.getFirstName(), userSO.getLastName()).list();
        else if (params.get(UserField.firstName.name()) != null)
            return UserPME.find(UserField.firstName+" = ?1", userSO.getFirstName()).list();
        else if (params.get(UserField.lastName.name())!=null)
            return UserPME.find(UserField.lastName+" = ?1", userSO.getLastName()).list();
        else
            return new ArrayList<>();

    }

    enum UserField{
        firstName,
        lastName
    }
}
