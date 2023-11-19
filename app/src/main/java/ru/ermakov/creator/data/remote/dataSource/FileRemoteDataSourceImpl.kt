package ru.ermakov.creator.data.remote.dataSource

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ru.ermakov.creator.domain.model.UploadedFile
import java.io.File

class FileRemoteDataSourceImpl(
    private val firebaseStorage: FirebaseStorage
) : FileRemoteDataSource {
    private var uploadTask: UploadTask? = null
    override suspend fun uploadFile(uri: String, path: String): Flow<UploadedFile> {
        val remotePath = firebaseStorage.reference.child(path)
        uploadTask = remotePath.putFile(Uri.parse(uri))
        return callbackFlow {
            val progressListener = uploadTask?.addOnProgressListener { taskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                trySend(UploadedFile(progress = progress))
            }?.addOnCompleteListener { taskSnapshot ->
                if (taskSnapshot.isSuccessful) {
                    remotePath.downloadUrl.addOnCompleteListener { uriTaskSnapshot ->
                        val url = uriTaskSnapshot.result.toString()
                        trySend(UploadedFile(url = url, progress = 100.0))
                        close()
                    }
                } else {
                    close()
                }
            }
            awaitClose { progressListener?.removeOnProgressListener { } }
        }
    }

    override suspend fun downloadFile(file: File, path: String) {
        TODO("Not yet implemented")
        val remotePath = firebaseStorage.reference.child(path)
        remotePath.getFile(file).await()
    }

    override fun cancelUploadTask() {
        uploadTask?.cancel()
    }
}