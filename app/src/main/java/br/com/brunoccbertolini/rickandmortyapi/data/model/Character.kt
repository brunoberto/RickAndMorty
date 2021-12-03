package br.com.brunoccbertolini.rickandmortyapi.data.model

import java.io.Serializable

data class Character(
    val id: Int,
    val created: String?,
    val gender: String?,
    val image: String?,
    val name: String,
    val species: String?,
    val status: String?,
    val type: String?
): Serializable