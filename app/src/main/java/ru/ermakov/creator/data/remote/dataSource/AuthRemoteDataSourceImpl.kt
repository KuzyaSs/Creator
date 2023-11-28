package ru.ermakov.creator.data.remote.dataSource

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.data.exception.EmailVerificationException
import ru.ermakov.creator.data.exception.InvalidUserException
import ru.ermakov.creator.data.mapper.toAuthUser
import ru.ermakov.creator.domain.model.AuthUser

class AuthRemoteDataSourceImpl(private val firebaseAuth: FirebaseAuth) : AuthRemoteDataSource {
    override suspend fun signIn(signInData: SignInData) {
        val task = firebaseAuth.signInWithEmailAndPassword(
            signInData.email, signInData.password
        ).await()
        val authUser = task.user ?: throw InvalidUserException()
        if (!authUser.isEmailVerified) {
            signOut()
            authUser.sendEmailVerification().await()
            throw EmailVerificationException()
        }
    }

    override suspend fun signedIn(signInData: SignInData) {
        val authUser = getCurrentUser()
        val credential = EmailAuthProvider.getCredential(signInData.email, signInData.password)
        authUser.reauthenticate(credential).await()
    }

    override suspend fun signUp(signUpData: SignUpData): AuthUser {
        val task = firebaseAuth.createUserWithEmailAndPassword(
            signUpData.email, signUpData.password
        ).await()
        val authUser = task.user ?: throw InvalidUserException()
        authUser.sendEmailVerification().await()
        return authUser.toAuthUser()
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser {
        return firebaseAuth.currentUser ?: throw InvalidUserException()
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override suspend fun changePassword(currentPassword: String, newPassword: String) {
        val authUser = getCurrentUser()
        val credential = EmailAuthProvider.getCredential(
            authUser.email.toString(),
            currentPassword
        )
        authUser.reauthenticate(credential).await()
        authUser.updatePassword(newPassword).await()
    }
}