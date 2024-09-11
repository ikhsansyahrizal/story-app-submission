package com.ikhsan.storyapp.core.network.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.GsonBuilder
import com.ikhsan.storyapp.BuildConfig
import com.ikhsan.storyapp.R
import com.ikhsan.storyapp.core.network.ApiInterface
import com.ikhsan.storyapp.core.network.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideNetwork {

    private fun getBaseUrl() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideApi(
        @NetworkConnectionInterceptor noNetworkInterceptor: Interceptor,
        ): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(setupClient(noNetworkInterceptor))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().disableHtmlEscaping().setLenient().create()))
            .build()
            .create(ApiInterface::class.java)
    }

    private fun setupClient(noNetworkInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(50L, TimeUnit.SECONDS)
            .connectTimeout(50L, TimeUnit.SECONDS)
            .writeTimeout(50L, TimeUnit.SECONDS)
            .addNetworkInterceptor {
                val headers = it.request().addHeaders()
                return@addNetworkInterceptor it.proceed(headers.build())
            }
            .addInterceptor(noNetworkInterceptor)
            .also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }
            .build()
    }

    private fun Request.addHeaders(): Request.Builder {
        return newBuilder().apply {
            header("Content-Type", "application/json")
        }
    }

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    @Singleton
    @Provides
    @NetworkConnectionInterceptor
    fun provideInterceptor(
        @ApplicationContext context: Context,
        connectivityManager: ConnectivityManager
    ): Interceptor = NetworkConnectionInterceptor(context.getString(R.string.no_internet), connectivityManager)

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class NetworkConnectionInterceptor


}