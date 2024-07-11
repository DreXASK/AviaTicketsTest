package com.drexask.effectivemobiletickets.networkmodule

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun jsonMediaType(): MediaType {
        return MediaType.get("application/json")
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("drive.usercontent.google.com")
    fun provideRetrofitUserContent(
        httpClient: OkHttpClient,
        json: Json,
        contentType: MediaType
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/")
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    @Named("drive.google.com")
    fun provideRetrofit(
        httpClient: OkHttpClient,
        json: Json,
        contentType: MediaType
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://drive.google.com")
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

}