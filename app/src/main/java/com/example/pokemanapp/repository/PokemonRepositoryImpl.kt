package com.example.pokemanapp.repository

import com.example.pokemanapp.data.remote.responses.Pokemon
import com.example.pokemanapp.data.remote.responses.PokemonList
import com.example.pokemanapp.util.Resource
import javax.inject.Singleton


@Singleton
interface PokemonRepositoryImpl {
    suspend fun getPokemonList(limit: Int , offset: Int): Resource<PokemonList>
    suspend fun getPokemonInfo(pokemonId: Int): Resource<Pokemon>
}