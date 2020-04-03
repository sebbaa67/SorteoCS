package com.example.sorteocs.Names

class Name (name: String, categoria: Int = 1) {

    var name: String = name
        get() = field
    var categoria: Int = categoria
        get() = field
        set(value) {
            field = value
        }


}