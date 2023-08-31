package ru.ermakov.creator.data.repository.mapper

interface Mapper<Source, Destination> {
    fun map(source: Source): Destination
}