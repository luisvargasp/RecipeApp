package com.codechallenge.recipeapp.presentation.di

import com.codechallenge.recipeapp.BuildConfig.BUILD_TYPE
import com.codechallenge.recipeapp.data.RecipeAPIService
import com.codechallenge.recipeapp.data.util.RECIPE_BASE_URL
import com.codechallenge.recipeapp.domain.repository.remote.RecipeRepository
import com.codechallenge.recipeapp.domain.repositoryimpl.remote.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideRecipeApiService(client:OkHttpClient) : RecipeAPIService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(RECIPE_BASE_URL).build().create(RecipeAPIService::class.java)

    }

    @Singleton
    @Provides
    fun provideRemoteRecipeRepository(api: RecipeAPIService): RecipeRepository {
        return RecipeRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideClient():OkHttpClient{
        val builder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if(BUILD_TYPE =="debug") HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        return builder.build()
    }

}