package com.android.aop.part2.useditemsellingapp.ui.home

import com.android.aop.part2.useditemsellingapp.data.model.ArticleModel

sealed class HomeViewState : com.android.aop.part2.useditemsellingapp.base.ViewState {
    object RouteAddArticle : HomeViewState()
    data class Message(val message: String) : HomeViewState()
    data class GetArticleList(val list: List<ArticleModel>) : HomeViewState()
}