package ru.ermakov.creator.data.remote.dataSource

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import java.io.File

private const val UPLOAD_TIMEOUT = 15000L

class FileRemoteDataSourceImpl(
    private val firebaseStorage: FirebaseStorage
) : FileRemoteDataSource {
    override suspend fun uploadFile(uri: String, path: String): String {
        val remotePath = firebaseStorage.reference.child(path)
        return withTimeout(UPLOAD_TIMEOUT) {
            remotePath.putFile(Uri.parse(uri)).addOnProgressListener { task ->
                Log.d("MY_TAG", "${task.bytesTransferred} / ${task.totalByteCount}")
            }.await()
            /*            remotePath.downloadUrl.addOnSuccessListener { urlTask ->

                        }.await()*/
            ""
        }
    }

    override suspend fun downloadFile(file: File, path: String) {
        val remotePath = firebaseStorage.reference.child(path)
        remotePath.getFile(file).await()
    }
}