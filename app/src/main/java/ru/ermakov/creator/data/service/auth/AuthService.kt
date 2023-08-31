package ru.ermakov.creator.data.service.auth

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.util.exception.EmailVerificationException
import ru.ermakov.creator.util.exception.InvalidUserException

class AuthService(private val firebaseAuth: FirebaseAuth) {
    suspend fun signIn(signInData: SignInData) {
        val task = firebaseAuth.signInWithEmailAndPassword(
            signInData.email, signInData.password
        ).await()
        val authUser = task.user ?: throw InvalidUserException()
        if (!authUser.isEmailVerified) {
            signOut()
            sendEmailVerification(authUser = authUser)
            throw EmailVerificationException()
        }
    }

    suspend fun signedIn(signInData: SignInData) {
        val authUser = getCurrentUser()
        val credential = EmailAuthProvider.getCredential(signInData.email, signInData.password)
        authUser.reauthenticate(credential).await()
    }

    suspend fun signUp(signUpData: SignUpData): FirebaseUser {
        val task = firebaseAuth.createUserWithEmailAndPassword(
            signUpData.email, signUpData.password
        ).await()
        task.user?.let { authUser ->
            sendEmailVerification(authUser = authUser)
            return authUser
        }
        throw InvalidUserException()
    }

    private fun getCurrentUser(): FirebaseUser {
        return firebaseAuth.currentUser ?: throw InvalidUserException()
    }

    private fun signOut() {
        firebaseAuth.signOut()
    }

    private suspend fun sendEmailVerification(authUser: FirebaseUser) {
        authUser.sendEmailVerification().await()
    }
}