package br.com.brunoccbertolini.rickandmortyapi.repository

import br.com.brunoccbertolini.rickandmortyapi.data.model.Character
import br.com.brunoccbertolini.rickandmortyapi.data.model.CharacterResponse
import br.com.brunoccbertolini.rickandmortyapi.util.Resource
import retrofit2.Response

interface CharactersRepository {
    suspend fun getCharactersAll(): Resource<CharacterResponse>

    suspend fun getCharacter(id: Int): Resource<Character>

}