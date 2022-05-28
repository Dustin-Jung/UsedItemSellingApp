package com.android.aop.part2.useditemsellingapp.data.repo

import com.android.aop.part2.useditemsellingapp.data.model.ArticleModel
import com.google.firebase.database.ChildEventListener

interface FirebaseRepository {
    fun createChatRoom(
        articleModel: ArticleModel,
        callback: (String) -> Unit
    )

    fun setChildEventListener(listener : ChildEventListener)
    fun removeChildEventListener(listener: ChildEventListener)

    fun isLoginUser() : Boolean
}