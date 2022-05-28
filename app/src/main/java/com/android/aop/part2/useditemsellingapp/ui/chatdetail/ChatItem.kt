package com.android.aop.part2.useditemsellingapp.ui.chatdetail

data class ChatItem(
    val senderId: String,
    val message: String
) {

    constructor() : this("", "")
}