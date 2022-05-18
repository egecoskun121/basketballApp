package com.ege.basketballapp.di

import android.content.Context
import android.provider.Settings
import com.ege.basketballapp.data.api.DataApi
import com.ege.basketballapp.data.api.UserApi
import com.ege.basketballapp.data.dataSource.DataRemoteDataSource
import com.ege.basketballapp.data.dataSource.UserRemoteDataSource
import com.ege.basketballapp.data.repository.DataRepository
import com.ege.basketballapp.data.repository.UserRepository
import com.ege.basketballapp.utils.GlobalPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    @Named("device_id")
    fun provideDeviceId(@ApplicationContext context: Context): String =
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )


    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)


    @Provides
    @Singleton
    fun provideDataApi(retrofit: Retrofit): DataApi = retrofit.create(DataApi::class.java)


    @Provides
    @Singleton
    fun provideUserRemoteDataSource(userApi: UserApi) = UserRemoteDataSource(userApi)

    @Provides
    @Singleton
    fun provideDataRemoteDataSource(dataApi: DataApi, globalPreferences: GlobalPreferences) =
        DataRemoteDataSource(dataApi, globalPreferences)


    @Provides
    @Singleton
    fun provideUserRepository(remoteDataSource: UserRemoteDataSource) =
        UserRepository(remoteDataSource)


    @Provides
    @Singleton
    fun provideDataRepository(
        remoteDataSource: DataRemoteDataSource,
    ) =
        DataRepository(remoteDataSource)

}