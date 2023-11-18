package ru.ermakov.creator.data.remote.dataSource

import java.io.File

interface FileRemoteDataSource {
    suspend fun uploadFile(uri: String, path: String): String
    suspend fun downloadFile(file: File, path: String)
}