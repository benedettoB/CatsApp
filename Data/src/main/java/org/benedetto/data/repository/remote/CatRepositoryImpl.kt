package org.benedetto.data.repository.remote

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import org.benedetto.data.model.Cat
import org.benedetto.data.util.log
import java.io.IOException
import javax.inject.Inject

internal class CatRepositoryImpl @Inject constructor() : CatRepository {

    private val client = OkHttpClient()
    private val gson = Gson()

    override fun fetchCats(): Flow<List<Cat>> = flow {
        val cats = fetchCatData()
        emit(cats.toList())
    }.flowOn(Dispatchers.IO)// now all code that runs in between flow{...} runs in background thread

    private fun fetchCatData(): Array<Cat> {
        log("inside coroutine context Dispatchers.IO ")
        val request = Request.Builder()
            .url("https://api.thecatapi.com/v1/images/search?limit=5")
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            if (responseBody != null) {
                return gson.fromJson(responseBody, Array<Cat>::class.java)
            } else {
                throw IOException("Response body is null")
            }
        } else {
            throw IOException("Unexpected code $response")
        }
    }
}

/*
        Suspending functions suspend the execution of the coroutine until they have the result
        *suspending functions can run on the same or a different thread.
        *Suspending functions can only run inside a coroutine or inside another suspending function
            withContext() is a suspending function

                    //coroutine context determines which thread the coroutine will be executed in
        //coroutine context is a set of rules and configurations that define how the coroutine will be executed
        //Under the hood, it's a kind of map, with a set of possible keys and values
            //One of the possible configurations is the dispatcher that is used to identify the thread where the coroutine will be executed
            //Dispatcher can be provided in two ways (Dispatchers implement CoroutineContext):
                //1 Explicitly: we manually set the dispatcher that will be used (like flowOn(Dispatchers.IO) above)
                //2 By the coroutine scope:
     */