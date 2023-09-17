package ru.ermakov.creator.data.service

import java.io.File

class LocalStorage(val externalStorageFilesDir: File) {
    fun save(path: String): File {
        val fileName = path.takeLastWhile { it != '/' }
        val filePath = createFolder(path = path)
        return File(filePath, fileName)
    }

    fun getRootPath(): String {
        return externalStorageFilesDir.path + File.separator
    }

    private fun createFolder(path: String): String {
        val folderPath = path.dropLastWhile { it != '/' }.dropLast(1)
        var currentFolderPath = externalStorageFilesDir.absolutePath
        for (folder in folderPath.split('/')) {
            val newFolder = File(currentFolderPath + File.separator + folder)
            if (!newFolder.exists()) {
                newFolder.mkdir()
            }
            currentFolderPath = newFolder.absolutePath
        }
        return currentFolderPath
    }
}