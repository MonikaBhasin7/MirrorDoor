package com.krsna.mirrordoor.Common

import android.text.format.DateUtils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

object FireStore {

    private lateinit var fireStoreDb : FirebaseFirestore

    fun getInstance(): FirebaseFirestore {
        if(!::fireStoreDb.isInitialized) {
            fireStoreDb = FirebaseFirestore.getInstance()
            return fireStoreDb
        }
        return fireStoreDb
    }
}