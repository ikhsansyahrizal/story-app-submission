package com.ikhsan.storyapp.core.network

import com.ikhsan.storyapp.core.EndPoint
import com.ikhsan.storyapp.core.data.request.LoginReq
import com.ikhsan.storyapp.core.data.request.RegisterUserReq
import com.ikhsan.storyapp.core.data.response.LoginRes
import com.ikhsan.storyapp.core.data.response.RegisterUserRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST(EndPoint.REGISTER)
    suspend fun registerUser(@Body body: RegisterUserReq): Response<RegisterUserRes>

    @POST(EndPoint.LOGIN)
    suspend fun doLogin(@Body body: LoginReq): Response<LoginRes>

}