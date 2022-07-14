package com.xihoon.moneynote.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xihoon.moneynote.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class Repository {
    private val database = Firebase.database
    private val myRef = database.getReference("text")

    private val scope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    val message by lazy { MutableSharedFlow<String>(0, 1) }

    init {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                scope.launch {
                    Logger().info { "launch" }
                    val msg = snapshot.getValue(String::class.java) ?: "empty"
                    Logger().info { "data change : $msg" }
                    message.emit(msg)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Logger().info { "onCancelled: $error" }
            }

        })
    }

    fun setMessage(msg: String) {
        myRef.setValue(msg)
    }
}