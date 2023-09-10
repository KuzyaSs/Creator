package ru.ermakov.creator.di.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.service.AuthService
import ru.ermakov.creator.data.storage.remote.UserRemoteDataSource

@Module
class RemoteModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    fun provideAuthService(firebaseAuth: FirebaseAuth): AuthService {
        return AuthService(firebaseAuth = firebaseAuth)
    }

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    fun provideUserRemoteDataSource(remoteDataSource: FirebaseFirestore): UserRemoteDataSource {
        return UserRemoteDataSource(remoteDataSource = remoteDataSource)
    }
}