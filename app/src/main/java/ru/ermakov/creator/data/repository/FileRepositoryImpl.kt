package ru.ermakov.creator.data.repository

import kotlinx.coroutines.flow.Flow
import ru.ermakov.creator.data.local.dataSource.FileLocalDataSource
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSource
import ru.ermakov.creator.domain.model.UploadedFile
import ru.ermakov.creator.domain.repository.FileRepository
import java.io.File

class FileRepositoryImpl(
    private val fileLocalDataSource: FileLocalDataSource,
    private val fileRemoteDataSource: FileRemoteDataSource
) : FileRepository {
    override suspend fun uploadFile(uri: String, path: String): Flow<UploadedFile> {
        return fileRemoteDataSource.uploadFile(uri = uri, path = path)
    }

    override suspend fun downloadFile(file: File, path: String) {
        TODO("Not yet implemented")
    }

    override fun cancelUploadTask() {
        fileRemoteDataSource.cancelUploadTask()
    }
}