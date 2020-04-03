package com.example.sorteocs.Names

import android.content.Context

object NamesArchive {

    private var namesArchive = ArrayList<Name>(0)

    fun getNamesArchive(): ArrayList<Name> {
        return namesArchive
    }

}