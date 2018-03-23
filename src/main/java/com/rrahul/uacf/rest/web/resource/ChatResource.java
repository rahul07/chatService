package com.rrahul.uacf.rest.web.resource;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rrahul.uacf.rest.api.User;
import com.rrahul.uacf.rest.api.UserDetails;
import com.rrahul.uacf.rest.api.model.GetIdResponse;
import com.rrahul.uacf.rest.api.model.GetUserNameResponse;
import com.rrahul.uacf.rest.api.model.PostRequest;
import com.rrahul.uacf.rest.api.model.PostResponse;
import com.rrahul.uacf.rest.dao.UserDB;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class ChatResource {

    public ChatResource() {
    }
 
    @GET
    public Response getAllUsers() {
        return Response.ok(UserDB.getAllUsers()).build();
    }
 
    @GET
    @Path("/chats/{userName}")
    public List<GetUserNameResponse> getUser(@PathParam("userName") String userName) {
        User user = UserDB.getUser(userName);
        List<GetUserNameResponse> getUserNameResponseList = new ArrayList<>();
        GetUserNameResponse getUserNameResponse;
        if (user != null) {
            List<UserDetails> userDetailsList = user.getUserDetails();
            for (UserDetails details: userDetailsList) {
                getUserNameResponse = new GetUserNameResponse();
                Long currentTimestamp = Instant.now().getEpochSecond();
                if (!details.isExpired() && details.getMsgTimestamp() > currentTimestamp) { // only if text hasn't expired
                    getUserNameResponse.setId(details.getId());
                    getUserNameResponse.setText(details.getMsg());
                    details.setMsgTimestamp(Instant.now().getEpochSecond());
                }
                details.setExpired(true);
                getUserNameResponseList.add(getUserNameResponse);
            }
        }
        return getUserNameResponseList;
    }

    @GET
    @Path("/chat/{id}")
    public GetIdResponse getUserById(@PathParam("id") int id) {
        List<User> users = UserDB.getAllUsers();
        GetIdResponse getIdResponse = new GetIdResponse();
        for(User user: users) {
            List<UserDetails> userDetailsList = user.getUserDetails();
            for (UserDetails details: userDetailsList) {
                if (details.getId() == id) {
                    getIdResponse.setText(details.getMsg());

                    LocalDateTime dateTime = LocalDateTime.ofEpochSecond(details.getMsgTimestamp(), 0, OffsetDateTime.now().getOffset());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss", Locale.US);
                    String formattedDate = dateTime.format(formatter);
                    getIdResponse.setExpiration_date(formattedDate);
                    getIdResponse.setUserName(user.getUsername());
                    return getIdResponse;
                }
            }
        }
        return getIdResponse;
    }
 
    @POST
    @Path("/chat")
    public Response postChat(PostRequest request) throws URISyntaxException {
        int id = UserDB.postMsg(request.getUserName(), request.getMsgText(), request.getTimeout());
        PostResponse postResponse = new PostResponse();
        postResponse.setId(id);
        return Response.status(Response.Status.CREATED).entity(postResponse).build();
    }
}