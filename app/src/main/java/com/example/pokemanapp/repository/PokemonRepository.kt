package com.example.pokemanapp.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.pokemanapp.data.remote.PokemonApi
import com.example.pokemanapp.data.remote.responses.Pokemon
import com.example.pokemanapp.data.remote.responses.PokemonList
import com.example.pokemanapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val api: PokemonApi
) : PokemonRepositoryImpl{

    @SuppressLint("LogNotTimber")
    override suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit,offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown Error")
        }
        Log.d("aviveravin", "getPokemonList: $response")

        return Resource.Success(response)
    }

    override suspend fun getPokemonInfo(pokemonId: Int): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonId)
        } catch (e: Exception) {
            return Resource.Error("An unknown Error")
        }
        return Resource.Success(response)
    }
}