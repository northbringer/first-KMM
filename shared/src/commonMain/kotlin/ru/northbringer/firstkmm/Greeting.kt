package ru.northbringer.firstkmm

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform.reversed()}!"
    }
}