package ru.ermakov.creator.data.remote.dataSource

import java.io.File

interface FileRemoteDataSource {
    suspend fun uploadProfileAvatar(uri: String, path: String)
    suspend fun download(file: File, path: String)
}