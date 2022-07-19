package com.xihoon.moneynote.repository

import androidx.annotation.Keep
import com.google.firebase.database.*
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
    private val myRef = database.reference

    private val logger = Logger()
    private val scope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    val message by lazy { MutableSharedFlow<String>(0, 1) }
    val uses by lazy { MutableSharedFlow<List<Use>?>(1, 1) }

    init {
        myRef.child(PATH_USE).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                scope.launch {
                    logger.info { "launch snapshot:$snapshot" }
                    val list: List<Use> = snapshot.children.mapNotNull {
                        logger.info { "child:$it" }
                        it.getValue(object : GenericTypeIndicator<Use>() {})
                    }
                    logger.info { "list:${list.size}" }
                    uses.emit(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                logger.info { "onCancelled: $error" }
            }

        })
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                scope.launch {
                    logger.info { "data change : $snapshot" }
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

    suspend fun use(useType: String, category: String, account: Int, comment: String, time: Long) {
        myRef.child(PATH_USE).push().setValue(Use(useType, category, account, comment, time))
    }

    @Keep
    @IgnoreExtraProperties
    data class Use(
        val useType: String = "",
        val category: String = "",
        val account: Int = 0,
        val comment: String = "",
        val time: Long = 0
    )

    data class AccountField(
        val key: String,
        val use: Use
    )

    companion object {
        private const val PATH_USE = "use"
    }
}