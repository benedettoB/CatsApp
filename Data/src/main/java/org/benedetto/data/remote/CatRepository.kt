package org.benedetto.data.remote

import kotlinx.coroutines.flow.Flow
import org.benedetto.data.model.Cat

interface CatRepository {
    fun fetchCats(): Flow<List<Cat>>
}

/*
package com.example.hellokotlin.repository
import com.example.hellokotlin.data.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun fetchCats(): Flow<List<Cat>>
}

 */