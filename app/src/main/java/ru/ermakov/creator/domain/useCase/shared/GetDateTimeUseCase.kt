package ru.ermakov.creator.domain.useCase.shared

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GetDateTimeUseCase {
    operator fun invoke(date: LocalDateTime): String {
        val currentDate = LocalDateTime.now()

        return if (currentDate.year == date.year && currentDate.dayOfYear == date.dayOfYear) {
            date.format(DateTimeFormatter.ofPattern("HH:mm"))
        } else if (currentDate.year == date.year) {
            date.format(DateTimeFormatter.ofPattern("d MMM HH:mm"))
        } else {
            date.format(DateTimeFormatter.ofPattern("d MMM yyyy HH:mm"))
        }
    }
}