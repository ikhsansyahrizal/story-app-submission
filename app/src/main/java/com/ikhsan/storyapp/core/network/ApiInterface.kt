package com.ikhsan.storyapp.core.network

import com.ikhsan.storyapp.core.EndPoint
import com.ikhsan.storyapp.core.data.request.LoginReq
import com.ikhsan.storyapp.core.data.request.RegisterUserReq
import com.ikhsan.storyapp.core.data.response.GetAllStoriesRes
import com.ikhsan.storyapp.core.data.response.LoginRes
import com.ikhsan.storyapp.core.data.response.RegisterUserRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST(EndPoint.REGISTER)
    suspend fun registerUser(@Body body: RegisterUserReq): Response<RegisterUserRes>

    @POST(EndPoint.LOGIN)
    suspend fun doLogin(@Body body: LoginReq): Response<LoginRes>

    @GET(EndPoint.STORIES)
    suspend fun getAllStories(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null,
    ): Response<GetAllStoriesRes>

}