package com.example.tazakhabar.NetworkInstanceViaHilt

import com.example.tazakhabar.APIsInterface.GetNewsApi
import com.example.tazakhabar.Utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module //class is a module, which provides dependencies for other parts of your code.(Just Like Juice ingredients.)
@InstallIn(SingletonComponent::class) //Module will be installed in a certain component,
// which is a container for dependencies.(Container having different types of foods.)

class networkObject {

    @Singleton
    @Provides
    fun retrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

// function is responsible for creating an object that allows
// the app to communicate with a specific web service using the retrofit object that is passed in.
    @Singleton
    @Provides
    fun apiCallCreator(retrofit: Retrofit.Builder): GetNewsApi {
        return retrofit.build().create(GetNewsApi::class.java)
    }
}