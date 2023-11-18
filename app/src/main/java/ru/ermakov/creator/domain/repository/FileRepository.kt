package ru.ermakov.creator.domain.repository

interface FileRepository {
    suspend fun uploadFile(uri: String, path: String)
}