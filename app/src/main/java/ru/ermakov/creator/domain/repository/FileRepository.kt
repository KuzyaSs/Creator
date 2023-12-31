package ru.ermakov.creator.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.ermakov.creator.domain.model.UploadedFile
import java.io.File

interface FileRepository {
    suspend fun uploadFile(uri: String, path: String): Flow<UploadedFile>

    suspend fun downloadFile(file: File, path: String)

    fun cancelUploadTask()
}