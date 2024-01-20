package ru.ermakov.creator.domain.useCase.topUp

class ValidateCreditCardDetailsUseCase(
    private val validateCreditCardNumberUseCase: ValidateCreditCardNumberUseCase,
    private val validateValidityUseCase: ValidateValidityUseCase,
    private val validateCvvUseCase: ValidateCvvUseCase
) {
    operator fun invoke(cardNumber: String, validity: String, cvv: String) {
        validateCreditCardNumberUseCase(cardNumber = cardNumber)
        validateValidityUseCase(validity = validity)
        validateCvvUseCase(cvv = cvv)
    }
}