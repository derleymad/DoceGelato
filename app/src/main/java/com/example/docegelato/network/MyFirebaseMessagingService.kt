package com.example.docegelato.network

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("firebasemessaging","The token refreshed $token")
    }
}