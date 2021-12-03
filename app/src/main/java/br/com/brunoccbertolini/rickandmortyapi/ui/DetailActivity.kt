package br.com.brunoccbertolini.rickandmortyapi.ui

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import br.com.brunoccbertolini.rickandmortyapi.data.model.Character
import br.com.brunoccbertolini.rickandmortyapi.databinding.ActivityDetailBinding
import br.com.brunoccbertolini.rickandmortyapi.util.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val viewBinding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater)}

    private val viewModel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val intent = intent
        val intentExtras = intent.extras
        val characterId = intentExtras?.getInt("charId")
        characterId?.let { viewModel.getCharacter(it) }

        viewModel.characterLiveData.observe(this, Observer { response ->
            response.data?.let {
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            val charList: Character = it
                            setDetailCharacter(charList)
                        }
                    }
                    is Resource.Error -> {
                        response.message?.let { message ->
                            Log.e(ContentValues.TAG, "An error occured: $message")
                        }
                    }
                    else -> {response.message?.let { message ->
                        Log.e(ContentValues.TAG, "An error occured: $message")
                    }}
                }
            }
        })

        Toast.makeText(this, characterId.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun setDetailCharacter(char: Character) {
        char.apply {
            viewBinding.tcTittleDetail.text = name
            viewBinding.tvCreatedDetail.text = created
            viewBinding.tvGenderDetail.text = gender
            viewBinding.tvStatusDetail.text = status
            viewBinding.tvSpeciesDetail.text = species
            viewBinding.tvTypeDetail.text = type
            Glide.with(this@DetailActivity).load(image).into(viewBinding.ivCharacterDetail)
        }

    }
}