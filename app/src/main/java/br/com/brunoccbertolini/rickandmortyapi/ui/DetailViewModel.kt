package br.com.brunoccbertolini.rickandmortyapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.brunoccbertolini.rickandmortyapi.data.model.CharacterResponse
import br.com.brunoccbertolini.rickandmortyapi.repository.CharactersRepository
import br.com.brunoccbertolini.rickandmortyapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import br.com.brunoccbertolini.rickandmortyapi.data.model.Character

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: CharactersRepository
):ViewModel(){

    private var _characterLivedata: MutableLiveData<Resource<Character>> = MutableLiveData()
    val characterLiveData: LiveData<Resource<Character>> get() = _characterLivedata


    fun getCharacter(id: Int) = viewModelScope.launch{
        _characterLivedata.postValue(Resource.Loading())
        val response = repository.getCharacter(id)
        _characterLivedata.postValue(response)
    }
}