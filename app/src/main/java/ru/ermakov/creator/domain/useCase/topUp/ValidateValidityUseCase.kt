package ru.ermakov.creator.domain.useCase.topUp

import ru.ermakov.creator.domain.exception.InvalidValidityLengthException

private const val VALIDITY_LENGTH = 4

class ValidateValidityUseCase {
    operator fun invoke(validity: String) {
        if (validity.length != VALIDITY_LENGTH) {
            throw InvalidValidityLengthException()
        }
    }
}