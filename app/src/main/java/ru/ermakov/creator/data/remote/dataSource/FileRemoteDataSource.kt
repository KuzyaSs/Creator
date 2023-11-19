package ru.ermakov.creator.data.remote.dataSource

import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.flow.Flow
import ru.ermakov.creator.domain.model.UploadedFile
import java.io.File

interface FileRemoteDataSource {
    suspend fun uploadFile(uri: String, path: String): Flow<UploadedFile>
    suspend fun downloadFile(file: File, path: String)
    fun cancelUploadTask()
}