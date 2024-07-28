package com.example.pokemanapp.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokemanapp.repository.PokemonRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PokemonViewModelFactory @Inject constructor(private val instance: PokemonRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PokemonRepository::class.java).newInstance(instance)
    }
}