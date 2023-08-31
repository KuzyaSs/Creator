package ru.ermakov.creator.data.storage.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.ermakov.creator.data.storage.remote.model.RemoteUser
import ru.ermakov.creator.util.Constant

class UserRemoteDataSource(private val remoteDataSource: FirebaseFirestore) {
    suspend fun insert(remoteUser: RemoteUser, id: String) {
        remoteDataSource.collection(Constant.USERS_COLLECTION)
            .document(id)
            .set(remoteUser)
            .await()
    }
}