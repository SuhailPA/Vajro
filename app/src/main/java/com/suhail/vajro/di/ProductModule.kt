package com.suhail.vajro.di

import android.app.Application
import androidx.room.Room
import com.suhail.vajro.api.ProductAPI
import com.suhail.vajro.room.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProductModule {



    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(ProductAPI.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesProductAPI(retrofit: Retrofit) : ProductAPI =
        retrofit.create(ProductAPI::class.java)

    @Provides
    @Singleton
    fun providesDatabase(app: Application): ProductDatabase =
        Room.databaseBuilder(app, ProductDatabase :: class.java, "product_database")
            .build()
}