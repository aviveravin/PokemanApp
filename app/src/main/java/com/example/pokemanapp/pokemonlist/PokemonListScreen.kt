package com.example.pokemanapp.pokemonlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemanapp.R
import com.example.pokemanapp.data.models.PokemonListEntry
import com.example.pokemanapp.viewmodel.PokemonListViewModel

@Composable
fun PokemonListScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.pok_mon_logo),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController)
        }
    }
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(pokemonList.size) { index ->
            if (index >= pokemonList.size - 1 && !endReached) {
                viewModel.loadPokemonPaginated()
            }
            PokedexEntry(
                entry = pokemonList[index],
                navController = navController,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun PokedexEntry(
    entry: PokemonListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.number}"
                )
            }
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = entry.pokemonName,
                contentScale = ContentScale.Crop,
                onSuccess = { painterResult ->
                    painterResult.result.drawable?.let { drawable ->
                        viewModel.calcDominantColor(drawable) { color ->
                            dominantColor = color
                        }
                    }
                },
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally)
            )
            Text(
                text = entry.pokemonName,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

