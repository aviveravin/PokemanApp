package com.example.pokemanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemanapp.pokemonlist.PokemonDetailScreen
import com.example.pokemanapp.pokemonlist.PokemonListScreen
import com.example.pokemanapp.ui.theme.PokemanAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemanAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemon_list_screen"){
                    composable("pokemon_list_screen"){
                        PokemonListScreen(navController = navController)
                    }
                    composable("pokemon_detail_screen/{dominantColor}/{pokemonId}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonId") {
                                type = NavType.IntType
                            }
                        )
                    ){
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }

                        val pokemonId  = remember {
                            it.arguments?.getInt("pokemonId")
                        }

                        if (pokemonId != null) {
                            PokemonDetailScreen(
                                dominantColor = dominantColor,
                                pokemonId = pokemonId,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
