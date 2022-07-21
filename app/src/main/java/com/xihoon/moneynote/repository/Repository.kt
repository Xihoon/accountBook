package com.xihoon.moneynote.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xihoon.moneynote.Logger
import com.xihoon.moneynote.ui.source.Category
import com.xihoon.moneynote.ui.source.Use
import com.xihoon.moneynote.ui.source.UseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class Repository {
    private val database = Firebase.database
    private val myRef = database.reference

    private val logger = Logger()
    private val scope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }

    val useItems by lazy { MutableSharedFlow<List<UseItem>?>(1, 0) }

    val assetsTypes by lazy { MutableSharedFlow<List<String>>(1, 0) }

    private val categoryList by lazy { MutableSharedFlow<List<Category>>(1, 0) }
    val incomeCategory by lazy {
        categoryList.map {
            it.filter { category -> category.type == TYPE_INCOME }
                .map { category -> category.category }
        }
    }
    val expenseCategory by lazy {
        categoryList.map {
            it.filter { category -> category.type == TYPE_EXPENSES }
                .map { category -> category.category }
        }
    }

    init {

        myRef.child(PATH_ASSETS_TYPE).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                scope.launch {
                    val list: List<String> = snapshot.children.mapNotNull {
                        it.getValue(object : GenericTypeIndicator<String>() {})
                    }
                    logger.info { "assets type list:${list.size}" }
                    assetsTypes.emit(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                logger.info { "onCancelled: $error" }
            }

        })

        myRef.child(PATH_CATEGORY).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                scope.launch {
                    val list: List<Category> = snapshot.children.mapNotNull {
                        it.getValue(object : GenericTypeIndicator<Category>() {})
                    }
                    logger.info { "category list:${list.size}" }
                    categoryList.emit(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                logger.info { "onCancelled: $error" }
            }

        })

        myRef.child(PATH_USE).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                scope.launch {
                    val list: List<UseItem> = snapshot.children.mapNotNull {
                        val key = it.key
                        val use = it.getValue(object : GenericTypeIndicator<Use>() {})
                        key ?: return@mapNotNull null
                        use ?: return@mapNotNull null
                        UseItem(key, use)

                    }
                    logger.info { "use list:${list.size}" }
                    useItems.emit(list)
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

    suspend fun addAssetsType(type: String) {
        myRef.child(PATH_ASSETS_TYPE).push().setValue(type)
    }

    suspend fun addIncomeCategory(category: String) {
        myRef.child(PATH_CATEGORY).push().setValue(Category(TYPE_INCOME, category))
    }

    suspend fun addExpenseCategory(category: String) {
        myRef.child(PATH_CATEGORY).push().setValue(Category(TYPE_EXPENSES, category))
    }

    suspend fun use(use: Use) {
        myRef.child(PATH_USE).push().setValue(use)
    }

    suspend fun update(useItem: UseItem) {
        myRef.child(PATH_USE).child(useItem.key).setValue(useItem.use)
    }

    suspend fun remove(useKey: String) {
        myRef.child(PATH_USE).child(useKey).removeValue()
    }

    companion object {
        private const val PATH_USE = "use"
        private const val PATH_ASSETS_TYPE = "assets_type"
        private const val PATH_CATEGORY = "category"
        private const val TYPE_INCOME = "income"
        private const val TYPE_EXPENSES = "expenses"

    }
}