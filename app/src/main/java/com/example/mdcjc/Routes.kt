package com.example.mdcjc

sealed class Routes(val id:String) {
    object LoginScreen:Routes("pantalla1")
    object PropertyScreen:Routes("pantalla2")
}