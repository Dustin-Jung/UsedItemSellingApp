package com.android.aop.part2.useditemsellingapp.ui.home


import com.android.aop.part2.useditemsellingapp.base.ViewState
import com.android.aop.part2.useditemsellingapp.data.model.ArticleModel

sealed class HomeViewState : ViewState {
    object RouteAddArticle : HomeViewState()
    data class Message(val message: String) : HomeViewState()
    data class GetArticleList(val list: List<ArticleModel>) : HomeViewState()
}