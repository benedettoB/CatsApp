package org.benedetto.data.repository.remote
import org.benedetto.data.model.Cat
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

internal class CatRepositoryImpl @Inject constructor() : CatRepository {

    private val client = OkHttpClient()
    private val gson = Gson()

    override fun fetchCats(): Flow<List<Cat>> = flow {
        val cats = fetchCatData() // Fetch cat data
        emit(cats.toList()) // Emit the result
    }

    private suspend fun fetchCatData(): Array<Cat> {
        val request = Request.Builder()
            .url("https://api.thecatapi.com/v1/images/search?limit=5")
            .build()

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                    gson.fromJson(responseBody, Array<Cat>::class.java)
                } else {
                    throw IOException("Response body is null")
                }
            } else {
                throw IOException("Unexpected code $response")
            }
        }
    }
}