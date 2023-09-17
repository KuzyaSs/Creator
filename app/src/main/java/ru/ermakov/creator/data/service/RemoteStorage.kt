package ru.ermakov.creator.data.service

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File

class RemoteStorage(private val remoteStorage: FirebaseStorage) {
    suspend fun uploadProfileAvatar(uri: String, path: String) {
        val remotePath = remoteStorage.reference.child(path)
        remotePath.putFile(Uri.parse(uri)).await()
    }

    suspend fun download(file: File, path: String) {
        val remotePath = remoteStorage.reference.child(path)
        remotePath.getFile(file).await()
    }
}