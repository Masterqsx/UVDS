package com.parabird.uvds.dataLake.onboarding.db.api;

import com.parabird.uvds.dataLake.onboarding.db.dao.MediaDAO;
import com.parabird.uvds.dataLake.onboarding.db.dao.SourceDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
@Path("/db")
public class OnboardingDBRest {

    @Autowired
    MediaDAO mediaDao;

    @Autowired
    SourceDAO sourceDao;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response mainPage() {
        return Response.status(200).entity("Hello World").build();
    }

    @GET
    @Path("/media/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllMedia() {
        return Response.status(200).entity(StringUtils.join(mediaDao.findAll())).build();
    }
}
