package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.Creator

interface CreatorRepository {
    suspend fun getCreatorsByPage(searchQuery: String, page: Int): List<Creator>

    suspend fun getCreatorByUserId(userId: String): Creator
}