package br.com.brunoccbertolini.rickandmortyapi.repository

import br.com.brunoccbertolini.rickandmortyapi.data.api.RickAndMortyAPI
import br.com.brunoccbertolini.rickandmortyapi.data.model.Character
import br.com.brunoccbertolini.rickandmortyapi.data.model.CharacterResponse
import br.com.brunoccbertolini.rickandmortyapi.util.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultCharactersRepository @Inject constructor(
    private  val api: RickAndMortyAPI
): CharactersRepository {
    override suspend fun getCharactersAll(): Resource<CharacterResponse> {
         return try {
            val response = api.getAllCharacter()
            val result = response.body()
            if (response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        } catch (e: Exception){
            Resource.Error(e.message ?: "An error occured")
        }
    }

    override suspend fun getCharacter(id: Int): Resource<Character> {
        return try {
            val response = api.getCharacterById(id.toString())
            val result = response.body()
            if (response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        } catch (e: Exception){
            Resource.Error(e.message ?: "An error occured")
        }
    }


}