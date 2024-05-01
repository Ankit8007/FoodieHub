package com.app.foodiehub.appServices.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthService {
    companion object{
        private var auth: FirebaseAuth = FirebaseAuth.getInstance()

        suspend fun createAccount(email: String, password: String): FirebaseUser? {
            try {
                val data =  auth.createUserWithEmailAndPassword(email, password).await()
                return data.user
            } catch (e: FirebaseAuthException){
                throw e
            } catch (e: Exception){
                throw e
            }
        }

        suspend fun auth(email: String, password: String): FirebaseUser?  {
            try {
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                return authResult.user
            }catch (e: FirebaseAuthException){
                throw e
            }catch (e: Exception){
                throw e
            }
        }

        fun isAuth(): Boolean = auth.currentUser != null

        fun getUid(): String? = auth.currentUser?.uid

    }
}

