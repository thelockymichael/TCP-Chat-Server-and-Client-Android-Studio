package com.milo.chatclientproject0315022020

import kotlinx.serialization.Serializable

/**
 * Contains ChatMessage object with important properties like message, user and date.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

@Serializable
class ChatMessage(
    val message: String?,
    val user: String,
    val command: String?,
    val createdDateTime: String
)