package ru.ermakov.creator.data.storage.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.ermakov.creator.data.storage.local.database.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Update
    suspend fun update(userEntity: UserEntity)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM user WHERE id != :currentUserId")
    fun getUserList(currentUserId: Int): Flow<List<UserEntity>>
}