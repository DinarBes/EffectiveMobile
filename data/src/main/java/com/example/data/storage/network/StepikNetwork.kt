package com.example.data.storage.network

import com.example.data.storage.model.CourseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class StepikNetwork {

    companion object {

        private val serializer = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
        private val client = HttpClient(CIO) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(serializer)
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    val responseException = exception as? ResponseException ?: return@handleResponseExceptionWithRequest
                    val response = responseException.response
                    val error = serializer.decodeFromString<Exception>(response.bodyAsText())
                    throw error
                }
            }
        }

        suspend fun getCourses(
            page: Int = 2
        ): CourseModel {

            val response = client.get("https://stepik.org/api/courses") {
                url {
                    parameters.append("page", page.toString())
                }
            }

            return response.body()
        }
    }
}