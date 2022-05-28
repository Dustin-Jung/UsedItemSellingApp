package com.android.aop.part2.useditemsellingapp.ui.home

import android.app.Application
import com.android.aop.part2.useditemsellingapp.base.BaseViewModel
import com.android.aop.part2.useditemsellingapp.data.model.ArticleModel
import com.android.aop.part2.useditemsellingapp.data.repo.FirebaseRepository
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(application = application) {

    private val articleList = mutableListOf<ArticleModel>()
    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return

            articleList.add(articleModel)
            articleRecyclerViewAdapter.addAll(articleList)

        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onChildRemoved(snapshot: DataSnapshot) {

        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    init {
        articleList.clear()
        firebaseRepository.setChildEventListener(listener)
    }

    fun createChatRoom(articleModel: ArticleModel) {
        firebaseRepository.createChatRoom(articleModel) { message ->
            viewStateChange(HomeViewState.Message(message))
        }
    }

    fun addArticle() {
        if (firebaseRepository.isLoginUser()) {
            viewStateChange(HomeViewState.RouteAddArticle)
        } else {
            viewStateChange(HomeViewState.Message("로그인 후 사용해주세요"))
        }
    }
}

