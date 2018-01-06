package com.example.naveed.retro;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by Naveed on 11/15/2017.
 */

public interface WebServices {


    @GET("/api/user")
    Call<List<User>> userRetrieve();

    @POST("/api/user")
    Call<User> userLogin(@Body User user);

}
