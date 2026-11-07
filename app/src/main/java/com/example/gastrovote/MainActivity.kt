package com.example.gastrovote


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gastrovote.componentes.SelectorBar
import com.example.gastrovote.componentes.Cabecera
import com.example.gastrovote.componentes.FichaBar
import com.example.gastrovote.componentes.TuReseña
import com.example.gastrovote.ui.theme.GastroVoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GastroVoteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Pantalla(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Pantalla(modifier: Modifier) {
    var scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        Cabecera()
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                stringResource(R.string.elige_el_bar),
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            BarraSelectora()
            Spacer(modifier = Modifier.height(16.dp))

        }

    }
}


@Composable
fun BarraSelectora() {
    val nombreBares = stringArrayResource(id = R.array.nombres_bares)
    val tapasBares = stringArrayResource(id = R.array.tapas_bares)
    val descripcionTapas = stringArrayResource(id = R.array.descripciones_tapas)
    val precioTapas = stringArrayResource(id = R.array.precios_tapas)
    val baresSeleccionados = remember {
        mutableStateListOf(
            false, false, false, false, false, false, false, false
        )
    }

    var ultimaReseña by remember { mutableStateOf<ReseñaPublicada?>(null) }
    var indice by remember { mutableStateOf(0) }
    val totalBares = nombreBares.size
    val nombreActual = nombreBares[indice]
    val posicion = indice + 1

    val contexto = LocalContext.current
    val favoritos =
        remember { mutableStateListOf(false, false, false, false, false, false, false, false) }
    val puntuacionesEstrella = stringArrayResource(id = R.array.puntuacionesEstrella)

    val irAnterior: () -> Unit = {
        if (indice > 0) {
            indice--
        } else {
            indice = totalBares - 1
        }
    }

    val irSiguiente: () -> Unit = {
        if (indice < totalBares - 1) {
            indice++
        } else {
            indice = 0
        }
    }

    SelectorBar(irAnterior, nombreActual, posicion, totalBares, irSiguiente)

    FichaBar(
        tapaBar = tapasBares[indice],
        descripcionTapa = descripcionTapas[indice],
        precioTapa = precioTapas[indice].toDoubleOrNull() ?: 0.0,
        puntuacion = puntuacionesEstrella[indice].toIntOrNull() ?: 0,
        esFav = favoritos[indice],
        onAlternarFav = {
            val nuevoEstado = !favoritos[indice]
            favoritos[indice] = nuevoEstado
            Toast.makeText(
                contexto,
                if (nuevoEstado) R.string.a_adido_a_favoritos else R.string.fav_quitado,
                Toast.LENGTH_SHORT
            ).show()
        }
    )


    TuReseña(
        indiceActual = indice,
        onPublicar = { reseña ->
            baresSeleccionados[indice] = true
            ultimaReseña = reseña
        })

    ultimaReseña?.let { reseña ->
        ResumenReseña(reseña)
    }

    var baresReseñados = baresSeleccionados.count { it }

    Text(
        stringResource(R.string.ruta_progreso, baresReseñados, totalBares),
        fontWeight = FontWeight.ExtraBold
    )
    LinearProgressIndicator(
        progress = { baresReseñados.toFloat() / totalBares },
        modifier = Modifier.fillMaxWidth()
    )

}


data class ReseñaPublicada(
    val nombreBar: String,
    val nombreTapa: String,
    val precioBase: Double,
    val tamaño: String,
    val puntuacion: Int,
    val calidadPrecio: Int,
    val vegetariana: Boolean,
    val sinGluten: Boolean,
    val picante: Boolean,
    val meGusta: Boolean,
    val descuento: Boolean,
    val comentario: String,
    val precioFinal: Double
)



