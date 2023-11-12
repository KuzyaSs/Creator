package ru.ermakov.creator.data.remote.dataSource

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File

class FileRemoteDataSourceImpl(
    private val firebaseStorage: FirebaseStorage
) : FileRemoteDataSource {
    override suspend fun uploadProfileAvatar(uri: String, path: String) {
        val remotePath = firebaseStorage.reference.child(path)
        remotePath.putFile(Uri.parse(uri)).await()
    }

    override suspend fun download(file: File, path: String) {
        val remotePath = firebaseStorage.reference.child(path)
        remotePath.getFile(file).await()
    }
}