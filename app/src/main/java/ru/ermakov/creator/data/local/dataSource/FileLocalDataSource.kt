package ru.ermakov.creator.data.local.dataSource

import java.io.File

interface FileLocalDataSource {
    fun save(path: String): File
    fun getRootPath(): String
}