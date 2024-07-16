package com.florence.consulting;

import com.florence.consulting.common.CommonService;
import com.florence.consulting.common.ValidatePMEException;
import com.florence.consulting.common.ValidateSOException;
import com.florence.consulting.model.so.CSVUpload;
import com.florence.consulting.model.so.UserSO;
import com.florence.consulting.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.List;


@Path("/user")
public class UserResource {

    private static final Logger logger = Logger.getLogger(UserResource.class);


    @Inject
    UserService userService;

    @ServerExceptionMapper
    public Response mapException(ValidateSOException x) {
        logger.info("Invalid User", x);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(x.getMessageList())
                .build();
    }

    @ServerExceptionMapper
    public Response mapException(ValidatePMEException x) {
        logger.info("Invalid User in DB", x);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(x.getMessageList())
                .build();
    }


    @ServerExceptionMapper
    public Response mapException(Exception x) {

        logger.info("Generic Error", x);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Generic Error: " + x.getStackTrace())
                .build();

    }


    @GET
    @Path("all")
    public List<UserSO> readUsers() throws Exception {
        try {
            return userService.readAll();
        } catch (Exception x) {
            throw x;
        }

    }

    @GET
    @Path("/{idUser}")
    public UserSO readUser(@PathParam("idUser") String idUser) throws Exception {
        try {
            return userService.readUser(idUser);
        } catch (ValidatePMEException x) {
            throw x;
        } catch (ValidateSOException x) {
            throw x;
        } catch (Exception x) {
            throw x;
        }

    }


    @POST
    public String updateUser(UserSO userSO) throws Exception {

        try {
            ValidateSOException validateSOException = CommonService.validateUser(userSO);
            if (validateSOException.getMessageList().size() > 0)
                throw validateSOException;
            userService.updateUser(userSO);
            return "updated";
        } catch (ValidatePMEException x) {
            throw x;
        } catch (ValidateSOException x) {
            throw x;
        } catch (Exception x) {
            throw x;
        }
    }

    @PUT
    public String createUser(UserSO userSO) throws Exception {
        try {


            ValidateSOException validateSOException = CommonService.validateUser(userSO);
            if (validateSOException.getMessageList().size() > 0)
                throw validateSOException;

            userService.createUser(userSO);
            return "created";
        } catch (ValidatePMEException x) {
            throw x;
        } catch (ValidateSOException x) {
            throw x;
        } catch (Exception x) {
            throw x;
        }
    }

    @DELETE
    @Path("/{idUer}")
    public String deleteUser(@PathParam("idUser") String idUser) throws Exception {
        try {
            userService.deleteUser(idUser);
            return "deleted";
        } catch (ValidatePMEException x) {
            throw x;
        } catch (Exception x) {
            throw x;
        }
    }

    @GET
    @Path("/search")
    public List<UserSO> searchUser(UserSO userSO) throws Exception {
        try {
            return userService.searchUserSO(userSO);
        } catch (Exception x) {
            throw x;
        }
    }

    @PUT
    @Path("/csv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String importCSV(CSVUpload csvUpload) throws Exception {
        try {

            userService.uploadCSV(csvUpload);
            return "OK";
        } catch (Exception x) {
            throw x;
        }
    }



}
