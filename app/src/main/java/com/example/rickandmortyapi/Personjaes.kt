package com.example.rickandmortyapi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter

@Composable
fun ScreenCharacters(navController: NavHostController, servicio: APIservices) {
    var listaPersonajes by remember { mutableStateOf<List<Character>>(emptyList()) }

    LaunchedEffect(Unit) {
        val response = servicio.getCharacter()
        if (response.isSuccessful) {
            response.body()?.let {
                listaPersonajes = it.results // Extrae la lista de personajes
            }
        }
    }

    LazyColumn {
        items(listaPersonajes) { personaje ->
            CharacterItem(personaje, navController)
        }
    }
}

@Composable
fun CharacterItem(personaje: Character, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Cargar y mostrar la imagen en miniatura del personaje
            Image(
                painter = rememberImagePainter(personaje.image),
                contentDescription = "Imagen de ${personaje.name}",
                modifier = Modifier.size(64.dp) // Tamaño de la imagen en miniatura
            )
            Spacer(modifier = Modifier.width(16.dp)) // Espaciado entre la imagen y el texto
            Column {
                Text(text = personaje.name, style = MaterialTheme.typography.titleLarge)
                Text(text = "Status: ${personaje.status}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Species: ${personaje.species}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Gender: ${personaje.gender}", style = MaterialTheme.typography.bodyMedium)

                Button(
                    onClick = {
                        navController.navigate("personajesVer/${personaje.id}")
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Ver Detalles")
                }
            }
        }
    }
}

@Composable
fun CharacterDetailScreen(id: Int, servicio: APIservices) {
    var personaje by remember { mutableStateOf<Character?>(null) }

    LaunchedEffect(id) {
        val response = servicio.getCharacterById(id)
        if (response.isSuccessful) {
            personaje = response.body()
        }
    }

    if (personaje != null) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Nombre: ${personaje!!.name}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Status: ${personaje!!.status}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Species: ${personaje!!.species}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Gender: ${personaje!!.gender}", style = MaterialTheme.typography.bodyMedium)
            // Puedes añadir más detalles aquí.

            // Muestra la imagen del personaje.
            Image(
                painter = rememberImagePainter(personaje!!.image),
                contentDescription = "Imagen de ${personaje!!.name}",
                modifier = Modifier.size(300.dp)
            )
        }
    } else {
        // Puedes mostrar un indicador de carga aquí.
        CircularProgressIndicator()
    }
}