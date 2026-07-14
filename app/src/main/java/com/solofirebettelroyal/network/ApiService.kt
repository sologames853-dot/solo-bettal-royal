package com.solofirebettelroyal.network

import com.solofirebettelroyal.model.GameCharacter
import com.solofirebettelroyal.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("characters")
    suspend fun getCharacters(): List<GameCharacter>

    @POST("characters")
    suspend fun addCharacter(@Body character: GameCharacter)

    @POST("user/login")
    suspend fun loginUser(@Body user: User): User

    @GET("events")
    suspend fun getEvents(): List<GameEvent>

    @POST("events")
    suspend fun addEvent(@Body event: GameEvent)
}

data class GameEvent(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val expiryDate: Long
)
