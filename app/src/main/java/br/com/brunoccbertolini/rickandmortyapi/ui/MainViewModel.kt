package br.com.brunoccbertolini.rickandmortyapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.brunoccbertolini.rickandmortyapi.data.model.CharacterResponse
import br.com.brunoccbertolini.rickandmortyapi.repository.CharactersRepository
import br.com.brunoccbertolini.rickandmortyapi.util.DispatcherProvider
import br.com.brunoccbertolini.rickandmortyapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CharactersRepository,
    private val dispacher: DispatcherProvider
    ) : ViewModel() {

    private var _characterLivedata: MutableLiveData<Resource<CharacterResponse>> = MutableLiveData()
    val characterLiveData: LiveData<Resource<CharacterResponse>> get() = _characterLivedata

    init {
        getAllCharacter()
    }

    fun getAllCharacter() = viewModelScope.launch {
        _characterLivedata.postValue(Resource.Loading())
        val response = repository.getCharactersAll()
        _characterLivedata.postValue(response)
    }



    private fun handleResponse(response: Response<CharacterResponse>): Resource<CharacterResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}