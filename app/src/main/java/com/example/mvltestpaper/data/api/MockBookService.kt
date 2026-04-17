package com.example.mvltestpaper.data.api

import com.example.mvltestpaper.data.model.*
import kotlinx.coroutines.delay
import java.util.*

class MockBookService : BookService {
    private val books = mutableListOf<BookResponse>()

    override suspend fun createBook(request: BookRequest): BookResponse {
        delay(500) // Simulate network delay
        val response = BookResponse(
            a = request.a,
            b = request.b,
            price = 10000.0,
            id = UUID.randomUUID().toString()
        )
        books.add(response)
        return response
    }

    override suspend fun getBooks(year: Int, month: Int): List<BookResponse> {
        delay(500)
        // In a real mock, we might filter by year/month if we stored timestamps
        return books.toList()
    }
}
