package ru.ermakov.creator.domain.useCase.topUp

import ru.ermakov.creator.domain.exception.InvalidCvvLengthException

private const val CVV_LENGTH = 3

class ValidateCvvUseCase {
    operator fun invoke(cvv: String) {
        if (cvv.length != CVV_LENGTH) {
            throw InvalidCvvLengthException()
        }
    }
}