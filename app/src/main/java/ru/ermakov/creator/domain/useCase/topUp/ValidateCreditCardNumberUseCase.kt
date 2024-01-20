package ru.ermakov.creator.domain.useCase.topUp

import ru.ermakov.creator.domain.exception.InvalidCardNumberLengthException

private const val CARD_NUMBER_LENGTH = 16

class ValidateCreditCardNumberUseCase {
    operator fun invoke(cardNumber: String) {
        if (cardNumber.length != CARD_NUMBER_LENGTH) {
            throw InvalidCardNumberLengthException()
        }
    }
}