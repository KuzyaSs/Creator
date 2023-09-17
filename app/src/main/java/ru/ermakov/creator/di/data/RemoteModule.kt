package ru.ermakov.creator.di.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.firestore.ktx.memoryCacheSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.dataSource.remote.UserRemoteDataSourceImpl
import ru.ermakov.creator.data.repository.user.UserRemoteDataSource
import ru.ermakov.creator.data.service.AuthService
import ru.ermakov.creator.data.service.RemoteStorage

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
    fun provideFirebaseStorage(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    fun provideRemoteStorage(remoteStorage: FirebaseStorage): RemoteStorage {
        return RemoteStorage(remoteStorage = remoteStorage)
    }

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {})
        }
        val db = Firebase.firestore
        db.firestoreSettings = settings
        return db
    }

    @Provides
    fun provideUserRemoteDataSource(remoteDataSource: FirebaseFirestore): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(remoteDataSource = remoteDataSource)
    }
}