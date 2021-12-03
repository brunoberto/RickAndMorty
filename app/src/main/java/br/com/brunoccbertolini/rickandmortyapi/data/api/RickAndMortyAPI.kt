package br.com.brunoccbertolini.rickandmortyapi.data.api

import br.com.brunoccbertolini.rickandmortyapi.data.model.Character
import br.com.brunoccbertolini.rickandmortyapi.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character")
    suspend fun getAllCharacter(
    ): Response<CharacterResponse>

    @GET("character/")
    suspend fun getCharacterByName(
        @Query("name")
        name: String
    ): Response<CharacterResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id")
        id :String
    ): Response<Character>
}