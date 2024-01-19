package ru.ermakov.creator.domain.useCase.topUp

import ru.ermakov.creator.domain.exception.InvalidCardNumberLengthException
import ru.ermakov.creator.domain.exception.InvalidCvvLengthException
import ru.ermakov.creator.domain.exception.InvalidValidityLengthException

private const val CARD_NUMBER_LENGTH = 16
private const val VALIDITY_LENGTH = 4
private const val CVV_LENGTH = 3

class ValidateCreditCardDetailsUseCase {
    operator fun invoke(cardNumber: String, validity: String, cvv: String) {
        if (cardNumber.length != CARD_NUMBER_LENGTH) {
            throw InvalidCardNumberLengthException()
        }
        if (validity.length != VALIDITY_LENGTH) {
            throw InvalidValidityLengthException()
        }
        if (cvv.length != CVV_LENGTH) {
            throw InvalidCvvLengthException()
        }
    }
}