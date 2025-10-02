package com.jmadrigal.hackernews.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jmadrigal.hackernews.core.database.HackerNewsDatabase
import com.jmadrigal.hackernews.core.database.HitDao
import com.jmadrigal.hackernews.core.database.HitDeletedDao
import com.jmadrigal.hackernews.core.network.HackerNewsService
import com.jmadrigal.hackernews.utils.Constants.BASE_URL
import com.jmadrigal.hackernews.utils.Constants.DEFAULT_TIME_OUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson =
        GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    @Singleton
    @Provides
    fun providesInterceptor() =
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY
                /*if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE*/
            )
        }


    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor : HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .readTimeout(DEFAULT_TIME_OUT, TimeUnit.MINUTES)
            .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.MINUTES)
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
        return client.build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))

    @Singleton
    @Provides
    fun provideHackerNewsService(retrofit: Retrofit.Builder): HackerNewsService =
        retrofit
            .build()
            .create(HackerNewsService::class.java)


    @Singleton
    @Provides
    fun provideHackerNewsDatabase(@ApplicationContext context: Context): HackerNewsDatabase =
        Room.databaseBuilder(context, HackerNewsDatabase::class.java, "hacker_news_db")
            .build()

    @Singleton
    @Provides
    fun provideHitDao(db: HackerNewsDatabase): HitDao =
        db.hitDao()

    @Singleton
    @Provides
    fun provideHitDeletedDao(db: HackerNewsDatabase): HitDeletedDao =
        db.hitDeletedDao()

}