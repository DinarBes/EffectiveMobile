package com.example.data.storage.network

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.tasks.await

class FirebaseNetwork {

    companion object {

        private val auth = FirebaseAuth.getInstance()

        suspend fun registration(
            email: String,
            password: String,
        ) {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
            } catch (error: Exception) {
                Log.e("Reg error", error.toString())
            }
        }

        suspend fun auth(
            email: String,
            password: String
        ) {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
            } catch (error: Exception) {
                Log.e("Reg error", error.toString())
            }
        }

        suspend fun subscribeToTopics(vararg topics: String) {

            for (topic in topics) {
                try {
                    Firebase.messaging.subscribeToTopic(topic).await()
                } catch (error: Exception) {
                    Log.e("Subcribe to topic error", "$topic: $error")
                }
            }
        }

        suspend fun unsubscribeToTopics(vararg topics: String) {

            for (topic in topics) {
                try {
                    Firebase.messaging.unsubscribeFromTopic(topic).await()
                } catch (error: Exception) {
                    Log.e("Unsubcribe to topic error", "$topic: $error")
                }
            }
        }
    }
}