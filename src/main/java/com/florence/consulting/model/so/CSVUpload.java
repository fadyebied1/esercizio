package com.florence.consulting.model.so;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.InputStream;

public class CSVUpload {
    @FormParam("csvUpload")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public FileUpload csvUpload;

}
