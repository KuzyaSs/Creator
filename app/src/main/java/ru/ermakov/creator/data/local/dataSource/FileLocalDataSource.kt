package ru.ermakov.creator.data.local.dataSource

import java.io.File

interface FileLocalDataSource {
    fun createFileByPath(path: String): File

    fun getRootPath(): String
}