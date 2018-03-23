package com.rrahul.uacf.rest.web.resource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrahul.uacf.rest.api.model.PostRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChatResourceTest {

    @Test
    void postTest() {
        PostRequest postRequest = getPostData1();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/chat");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonInString = objectMapper.writeValueAsString(postRequest);
            StringEntity entity = new StringEntity(jsonInString);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response.toString());

            assertEquals(response.getStatusLine().getStatusCode(), 201);
            assertTrue(jsonObject.containsValue("id"));

            client.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("rahul here");
    }

    PostRequest getPostData1() {
        PostRequest postRequest = new PostRequest();
        postRequest.setUserName("Rahul");
        postRequest.setMsgText("Hello, how are you?");
        postRequest.setTimeout(5);
        return postRequest;
    }

    PostRequest getPostData2() {
        PostRequest postRequest = new PostRequest();
        postRequest.setUserName("Austin");
        postRequest.setMsgText("TX, USA");
        return postRequest;
    }

    PostRequest getPostData3() {
        PostRequest postRequest = new PostRequest();
        postRequest.setUserName("UnderArmor");
        postRequest.setMsgText("Connected Fitness");
        return postRequest;
    }
}
