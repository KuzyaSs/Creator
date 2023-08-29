package ru.ermakov.creator.data.repository.mapper

interface Mapper<Source, Destination> {
    fun map(data: Source): Destination
}