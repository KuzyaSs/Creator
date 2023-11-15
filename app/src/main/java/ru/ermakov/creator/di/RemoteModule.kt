package ru.ermakov.creator.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ermakov.creator.data.remote.api.UserApi
import ru.ermakov.creator.data.remote.dataSource.AuthRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.AuthRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.UserRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.UserRemoteDataSourceImpl

private const val BASE_URL = "*"

@Module
class RemoteModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    fun provideAuthRemoteDataSource(firebaseAuth: FirebaseAuth): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(firebaseAuth = firebaseAuth)
    }

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    fun provideFileRemoteDataSource(firebaseStorage: FirebaseStorage): FileRemoteDataSource {
        return FileRemoteDataSourceImpl(firebaseStorage = firebaseStorage)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideUserRemoteDataSource(userApi: UserApi): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userApi = userApi)
    }
}