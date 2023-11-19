package ru.ermakov.creator.domain.useCase.common

import ru.ermakov.creator.domain.repository.FileRepository

class CancelUploadTaskUseCase(private val fileRepository: FileRepository) {
    operator fun invoke() {
        fileRepository.cancelUploadTask()
    }
}