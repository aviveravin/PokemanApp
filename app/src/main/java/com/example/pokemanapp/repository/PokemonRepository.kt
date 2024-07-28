package com.example.pokemanapp.repository

import androidx.compose.ui.geometry.Offset
import com.example.pokemanapp.data.remote.PokemonApi
import com.example.pokemanapp.data.remote.responses.Pokemon
import com.example.pokemanapp.data.remote.responses.PokemonList
import com.example.pokemanapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokemonApi
) {

    suspend fun getPokemonList(limit: Int , offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit,offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown Error")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An unknown Error")
        }
        return Resource.Success(response)
    }
}