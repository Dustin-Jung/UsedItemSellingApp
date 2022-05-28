package com.android.aop.part2.useditemsellingapp.ui.chatlist

data class ChatListItem (
    val buyerId: String,
    val sellerId :String,
    val itemTitle: String,
    val key: Long
) {

    constructor(): this("", "", "", 0)
}
