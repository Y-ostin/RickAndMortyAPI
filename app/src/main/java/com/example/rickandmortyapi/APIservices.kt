package com.example.rickandmortyapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIservices {

    @GET("character")
    suspend fun getCharacter(): Response<CharacterResponse>


    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<Character>
}