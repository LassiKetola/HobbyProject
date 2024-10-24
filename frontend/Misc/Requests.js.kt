package com.example.myapp

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// mock data purposes
suspend fun fetchRecipes(context: Context, fileName: String): String {
    val inputStream = context.assets.open(fileName)
    return inputStream.bufferedReader().use { it.readText() }
}

@Serializable
class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

val client = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun fetchRecipes(): List<Recipe> {
    val response: HttpResponse = client.get("${ApiURL}/get")
    return Json.decodeFromString<List<Recipe>>(response.bodyAsText())
}

suspend fun postRecipe(recipe: Recipe): HttpResponse {
    return client.post("${ApiURL}/recipes") {
        contentType(ContentType.Application.Json)
        setBody(recipe)
    }
}
