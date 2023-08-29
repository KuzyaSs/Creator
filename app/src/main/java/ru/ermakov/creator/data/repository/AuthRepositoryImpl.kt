package ru.ermakov.creator.data.repository

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.ermakov.creator.data.repository.mapper.AuthUserToDomainUserMapper
import ru.ermakov.creator.data.repository.mapper.AuthUserToUserRemoteEntityMapper
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.util.Constant.Companion.EMAIL_COLLISION_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.EMAIL_FORMAT_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.INVALID_USER_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.UNKNOWN_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.USERS_COLLECTION
import ru.ermakov.creator.util.Constant.Companion.WEAK_PASSWORD_EXCEPTION
import ru.ermakov.creator.util.Resource

class AuthRepositoryImpl(
    private val authService: FirebaseAuth,
    private val databaseService: FirebaseFirestore,
    private val authUserToUserRemoteEntityMapper: AuthUserToUserRemoteEntityMapper,
    private val authUserToDomainUserMapper: AuthUserToDomainUserMapper
) : AuthRepository {
    override suspend fun signUp(signUpData: SignUpData): Resource<User> {
        signUpData.apply {
            try {
                val task = authService.createUserWithEmailAndPassword(email, password).await()
                task.user?.let { authUser ->
                    authUser.sendEmailVerification().await()
                    addNewUserToRemoteDatabase(authUser = authUser)
                    return Resource.Success(
                        data = authUserToDomainUserMapper.map(data = authUser)
                    )
                }
                return Resource.Error(data = null, message = INVALID_USER_EXCEPTION)
            } catch (exception: Exception) {
                when (exception) {
                    is FirebaseNetworkException -> {
                        return Resource.Error(data = null, message = NETWORK_EXCEPTION)
                    }

                    is FirebaseAuthWeakPasswordException -> {
                        return Resource.Error(data = null, message = WEAK_PASSWORD_EXCEPTION)
                    }

                    is FirebaseAuthInvalidCredentialsException -> {
                        return Resource.Error(data = null, message = EMAIL_FORMAT_EXCEPTION)
                    }

                    is FirebaseAuthUserCollisionException -> {
                        return Resource.Error(data = null, message = EMAIL_COLLISION_EXCEPTION)
                    }

                    else -> {
                        Log.d("MY EXCEPTION", exception.toString())
                        return Resource.Error(data = null, message = UNKNOWN_EXCEPTION)
                    }
                }
            }
        }
    }

    private suspend fun addNewUserToRemoteDatabase(authUser: FirebaseUser) {
        val userRemoteEntity = authUserToUserRemoteEntityMapper.map(data = authUser)
        databaseService.collection(USERS_COLLECTION)
            .document(authUser.uid)
            .set(userRemoteEntity)
            .await()
    }
}