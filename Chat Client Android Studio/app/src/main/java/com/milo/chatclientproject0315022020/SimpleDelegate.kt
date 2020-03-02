package com.milo.chatclientproject0315022020

import kotlin.reflect.KProperty

class SimpleDelegate {

    operator fun getValue(thisRef: Any, property: KProperty<*>): String {
        return thisRef::class.java.name
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("you pass me $value")
    }
}