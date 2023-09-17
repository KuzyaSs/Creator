package ru.ermakov.creator.data.dataSource.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import ru.ermakov.creator.data.dataSource.remote.model.RemoteUser
import ru.ermakov.creator.data.repository.user.UserRemoteDataSource
import ru.ermakov.creator.data.toRemoteUser
import ru.ermakov.creator.data.toUser
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Constant.Companion.USERS_COLLECTION

class UserRemoteDataSourceImpl(
    private val remoteDataSource: FirebaseFirestore
) : UserRemoteDataSource {
    private var registration: ListenerRegistration? = null

    override suspend fun insert(user: User) {
        remoteDataSource.collection(USERS_COLLECTION)
            .document(user.id)
            .set(user.toRemoteUser())
            .await()
    }

    override suspend fun update(user: User) {
        remoteDataSource.collection(USERS_COLLECTION)
            .document(user.id)
            .set(user.toRemoteUser())
            .await()
    }

    /*    fun attachListener(field: String, id: String) {
            detachUsersListener()
            usersRegistration = remoteDataSource.collection(USERS_COLLECTION)
                .orderBy(FieldPath.documentId())
                .startAfter(id)
                .limit(10)
                .addSnapshotListener { remoteUsersQuerySnapshot, exception ->
                    if (exception != null) {
                        throw exception
                    }
                    if (remoteUsersQuerySnapshot != null) {
                        for (remoteUserDocumentSnapshot in remoteUsersQuerySnapshot) {
                            val remoteUser = remoteUserDocumentSnapshot.toObject<RemoteUser>()

                        }
                    }
                }
        }*/

    override fun attachListenerToUserById(id: String, update: (User) -> Unit) {
        detachListener()
        registration = remoteDataSource.collection(USERS_COLLECTION)
            .document(id)
            .addSnapshotListener { userDocumentSnapshot, _ ->
                if (userDocumentSnapshot != null) {
                    val remoteUser = userDocumentSnapshot
                        .toObject<RemoteUser>()
                    if (remoteUser != null) {
                        update(remoteUser.toUser(id = userDocumentSnapshot.id))
                    }
                }
            }
    }

    override fun detachListener() {
        registration?.remove()
    }
}