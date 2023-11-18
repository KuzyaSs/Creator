package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.local.dataSource.FileLocalDataSource
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSource
import ru.ermakov.creator.domain.repository.FileRepository

class FileRepositoryImpl(
    private val fileLocalDataSource: FileLocalDataSource,
    private val fileRemoteDataSource: FileRemoteDataSource
) : FileRepository {
    override suspend fun uploadFile(uri: String, path: String) {
        fileRemoteDataSource.uploadFile(uri = uri, path = path)
    }
}