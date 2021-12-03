package br.com.brunoccbertolini.rickandmortyapi.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunoccbertolini.rickandmortyapi.adapter.CharactersAdapter
import br.com.brunoccbertolini.rickandmortyapi.data.api.RickAndMortyAPI
import br.com.brunoccbertolini.rickandmortyapi.databinding.ActivityMainBinding
import br.com.brunoccbertolini.rickandmortyapi.repository.CharactersRepository
import br.com.brunoccbertolini.rickandmortyapi.repository.DefaultCharactersRepository
import br.com.brunoccbertolini.rickandmortyapi.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }
    private val characterAdapter = CharactersAdapter()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.rvCharacters.adapter = characterAdapter
        viewBinding.rvCharacters.layoutManager = LinearLayoutManager(this)


        viewModel.characterLiveData.observe(this, { response ->

            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        characterAdapter.differ.submitList(it.results)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                else -> {response.message?.let { message ->
                    Log.e(TAG, "An error occured: $message")
                }}
            }

        })

        characterAdapter.setOnItemClickListener {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("charId", it.id)
            }
            startActivity(intent)
        }
    }

}