package ru.ermakov.creator.domain.useCase.shared

import ru.ermakov.creator.domain.repository.TransactionRepository

class GetBalanceByUserIdUseCase(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(userId: String): Long {
        return transactionRepository.getBalanceByUserId(userId = userId)
    }
}