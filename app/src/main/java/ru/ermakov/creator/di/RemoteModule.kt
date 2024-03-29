package ru.ermakov.creator.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.remote.api.CategoryApi
import ru.ermakov.creator.data.remote.api.CreatorApi
import ru.ermakov.creator.data.remote.api.CreditGoalApi
import ru.ermakov.creator.data.remote.api.FollowApi
import ru.ermakov.creator.data.remote.api.PostApi
import ru.ermakov.creator.data.remote.api.PostCommentApi
import ru.ermakov.creator.data.remote.api.SubscriptionApi
import ru.ermakov.creator.data.remote.api.TagApi
import ru.ermakov.creator.data.remote.api.TransactionApi
import ru.ermakov.creator.data.remote.api.UserApi
import ru.ermakov.creator.data.remote.api.UserSubscriptionApi
import ru.ermakov.creator.data.remote.dataSource.AuthRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.AuthRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.CategoryRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.CategoryRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.CreatorRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.CreatorRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.CreditGoalRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.CreditGoalRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.FollowRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.FollowRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.PostCommentRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.PostCommentRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.PostRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.PostRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.SubscriptionRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.SubscriptionRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.TagRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.TagRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.TransactionRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.TransactionRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.UserRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.UserRemoteDataSourceImpl
import ru.ermakov.creator.data.remote.dataSource.UserSubscriptionRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.UserSubscriptionRemoteDataSourceImpl
import javax.inject.Singleton

private const val BASE_URL = "http://81.200.147.70:8089/api/"

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

    @Singleton
    @Provides
    fun provideFileRemoteDataSource(firebaseStorage: FirebaseStorage): FileRemoteDataSource {
        return FileRemoteDataSourceImpl(firebaseStorage = firebaseStorage)
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
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
    fun provideCreatorApi(retrofit: Retrofit): CreatorApi {
        return retrofit.create(CreatorApi::class.java)
    }

    @Provides
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }

    @Provides
    fun provideFollowApi(retrofit: Retrofit): FollowApi {
        return retrofit.create(FollowApi::class.java)
    }

    @Provides
    fun provideTransactionApi(retrofit: Retrofit): TransactionApi {
        return retrofit.create(TransactionApi::class.java)
    }

    @Provides
    fun provideSubscriptionApi(retrofit: Retrofit): SubscriptionApi {
        return retrofit.create(SubscriptionApi::class.java)
    }

    @Provides
    fun provideUserSubscriptionApi(retrofit: Retrofit): UserSubscriptionApi {
        return retrofit.create(UserSubscriptionApi::class.java)
    }

    @Provides
    fun provideCreditGoalApi(retrofit: Retrofit): CreditGoalApi {
        return retrofit.create(CreditGoalApi::class.java)
    }

    @Provides
    fun providePostApi(retrofit: Retrofit): PostApi {
        return retrofit.create(PostApi::class.java)
    }

    @Provides
    fun provideTagApi(retrofit: Retrofit): TagApi {
        return retrofit.create(TagApi::class.java)
    }

    @Provides
    fun providePostCommentApi(retrofit: Retrofit): PostCommentApi {
        return retrofit.create(PostCommentApi::class.java)
    }

    @Provides
    fun provideUserRemoteDataSource(
        userApi: UserApi,
        apiExceptionLocalizer: ApiExceptionLocalizer
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(
            userApi = userApi,
            apiExceptionLocalizer = apiExceptionLocalizer
        )
    }

    @Provides
    fun provideCreatorRemoteDataSource(
        creatorApi: CreatorApi,
        apiExceptionLocalizer: ApiExceptionLocalizer
    ): CreatorRemoteDataSource {
        return CreatorRemoteDataSourceImpl(
            creatorApi = creatorApi,
            apiExceptionLocalizer = apiExceptionLocalizer
        )
    }

    @Provides
    fun provideCategoryRemoteDataSource(
        categoryApi: CategoryApi,
        apiExceptionLocalizer: ApiExceptionLocalizer
    ): CategoryRemoteDataSource {
        return CategoryRemoteDataSourceImpl(
            categoryApi = categoryApi,
            apiExceptionLocalizer = apiExceptionLocalizer
        )
    }

    @Provides
    fun provideFollowRemoteDataSource(
        followApi: FollowApi,
        apiExceptionLocalizer: ApiExceptionLocalizer
    ): FollowRemoteDataSource {
        return FollowRemoteDataSourceImpl(
            followApi = followApi,
            apiExceptionLocalizer = apiExceptionLocalizer
        )
    }

    @Provides
    fun provideTransactionRemoteDataSource(
        transactionApi: TransactionApi,
        apiExceptionLocalizer: ApiExceptionLocalizer
    ): TransactionRemoteDataSource {
        return TransactionRemoteDataSourceImpl(
            transactionApi = transactionApi,
            apiExceptionLocalizer = apiExceptionLocalizer
        )
    }

    @Provides
    fun provideSubscriptionRemoteDataSource(
        subscriptionApi: SubscriptionApi,
        apiExceptionLocalizer: ApiExceptionLocalizer
    ): SubscriptionRemoteDataSource {
        return SubscriptionRemoteDataSourceImpl(
            subscriptionApi = subscriptionApi,
            apiExceptionLocalizer = apiExceptionLocalizer
        )
    }

    @Provides
    fun provideUserSubscriptionRemoteDataSource(
        userSubscriptionApi: UserSubscriptionApi,
        apiExceptionLocalizer: ApiExceptionLocalizer
    ): UserSubscriptionRemoteDataSource {
        return UserSubscriptionRemoteDataSourceImpl(
            userSubscriptionApi = userSubscriptionApi,
            apiExceptionLocalizer = apiExceptionLocalizer
        )
    }

    @Provides
    fun provideCreditGoalRemoteDataSource(
        creditGoalApi: CreditGoalApi,
        apiExceptionLocalizer: ApiExceptionLocalizer
    ): CreditGoalRemoteDataSource {
        return CreditGoalRemoteDataSourceImpl(
            creditGoalApi = creditGoalApi,
            apiExceptionLocalizer = apiExceptionLocalizer
        )
    }

    @Provides
    fun providePostRemoteDataSource(
        postApi: PostApi,
        apiExceptionLocalizer: ApiExceptionLocalizer
    ): PostRemoteDataSource {
        return PostRemoteDataSourceImpl(
            postApi = postApi,
            apiExceptionLocalizer = apiExceptionLocalizer
        )
    }

    @Provides
    fun provideTagRemoteDataSource(
        tagApi: TagApi,
        apiExceptionLocalizer: ApiExceptionLocalizer
    ): TagRemoteDataSource {
        return TagRemoteDataSourceImpl(
            tagApi = tagApi,
            apiExceptionLocalizer = apiExceptionLocalizer
        )
    }

    @Provides
    fun providePostCommentRemoteDataSource(
        postCommentApi: PostCommentApi,
        apiExceptionLocalizer: ApiExceptionLocalizer
    ): PostCommentRemoteDataSource {
        return PostCommentRemoteDataSourceImpl(
            postCommentApi = postCommentApi,
            apiExceptionLocalizer = apiExceptionLocalizer
        )
    }
}