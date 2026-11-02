package com.example.gastrovote

import android.R.attr.contentDescription
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gastrovote.ui.theme.GastroVoteTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.util.normalizedAngleCos


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
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(R.drawable.banner),
            contentDescription = "GastroVote"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.gastrovote),
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.ruta_de_la_tapa),
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.elige_el_bar),
            fontSize = 32.sp,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(16.dp))
        BarraSelectora()
        Spacer(modifier = Modifier.height(16.dp))
    }
}
@Composable
fun BarraSelectora(){
    val nombreBares = stringArrayResource(id = R.array.nombres_bares)
    val tapasBares = stringArrayResource(id = R.array.tapas_bares)
    val descripcionTapas = stringArrayResource(id = R.array.descripciones_tapas)
    val precioTapas = stringArrayResource(id = R.array.precios_tapas)

    var indice by remember { mutableStateOf(0) }
    val totalBares = nombreBares.size

    Column(
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button (
                onClick = {
                    if (indice > 0) {
                        indice--
                    } else {
                        indice = totalBares - 1
                    }
                }
            ) {
                Text(
                    "<",
                    fontSize = 36.sp
                )
            }
            Column() {
                Text(
                    text = nombreBares[indice],
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
                Text("Bar ${indice + 1} de $totalBares")
            }
            Button(
                onClick = {
                    if (indice < totalBares - 1) {
                        indice++
                    } else {
                        indice = 0
                    }
                }
            ) {
                Text(">",
                    fontSize = 36.sp)
            }
        }
    }
    FichaBar(indiceActual = indice)
    TuReseña(indiceActual = indice)
}

@Composable
fun FichaBar(indiceActual: Int){
    val nombreBares = stringArrayResource(id = R.array.nombres_bares)
    val tapasBares = stringArrayResource(id = R.array.tapas_bares)
    val descripcionTapas = stringArrayResource(id = R.array.descripciones_tapas)
    val precioTapas = stringArrayResource(id = R.array.precios_tapas)
    val puntuacionesEstrella = stringArrayResource(id = R.array.puntuacionesEstrella)
    val contexto = LocalContext.current

    val favoritos = remember { mutableStateListOf(false, false, false, false, false, false, false, false) }

    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp).border(
            width = 4.dp,
            color = Color(0xFF113152),
            shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Cyan)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    nombreBares[indiceActual],
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF113152)
                )
                IconButton(
                    onClick = {
                        val nuevoEstado = !favoritos[indiceActual]
                        favoritos[indiceActual] = nuevoEstado
                        Toast.makeText(contexto, "Añadido a favoritos!", Toast.LENGTH_LONG).show()
                    }
                ) {
                    Icon(
                        imageVector = if(favoritos[indiceActual]) Icons.Default.Favorite else Icons.Outlined.Favorite,
                        contentDescription= "¿Te ha gustado?",
                        tint = if(favoritos[indiceActual]) Color.Red else Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    descripcionTapas[indiceActual]
                )

            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    precioTapas[indiceActual] + "€"
                )
                Row(

                ) {
                    val puntuacionInt = puntuacionesEstrella[indiceActual].toIntOrNull() ?: 0

                    for (i in 1..5){
                        var estrella = ""
                        if (i <= puntuacionInt){
                            estrella = "★"
                        } else{
                            estrella = "☆"
                        }
                        Text(
                            text = estrella,
                            color = Color.Yellow,
                            fontSize = 24.sp
                        )

                    }
                }



            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TuReseña(indiceActual: Int){
    var tamaño by remember { mutableStateOf("Tapa") }
    var puntuacion by remember {mutableStateOf(0)}
    var barraDeslizadora by remember { mutableStateOf(5f) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Tu reseña",
            fontSize = 16.sp,
            color = Color.Blue,
            fontWeight = FontWeight.ExtraBold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Tamaño:"
            )
            RadioButton(
                selected = (tamaño == "Tapa"),
                onClick = {tamaño = "Tapa"}
            )
            Text(
                "Tapa"
            )
            RadioButton(
                selected = (tamaño == "Media"),
                onClick = {tamaño = "Media"}
            )
            Text(
                "Media"
            )
            RadioButton(
                selected = (tamaño == "Ración"),
                onClick = {tamaño = "Ración"}
            )
            Text(
                "Ración"
            )
        }
        Text(
            "Puntuación:"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {


            for (i in 1..5) {
                Text(
                    text = if (i <= puntuacion) "★" else "☆",
                    color = Color(0xFFFFB300),
                    fontSize = 36.sp,
                    modifier = Modifier.clickable {
                        puntuacion = i
                    }
                )
            }
            Text(
                if(puntuacion == 0){
                    "Sin puntuar"
                } else if(puntuacion == 1){
                    "Mejorable"
                } else if(puntuacion == 2){
                    "Correcta"
                } else if(puntuacion == 3){
                    "¡Muy buena!"
                } else if (puntuacion == 4){
                    "¡Espectacular!"
                } else if(puntuacion == 5){
                    "¡Perfecto!"
                } else{
                    ""
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Calidad-precio",
                modifier = Modifier.weight(0.3f)
            )
            Box(
                modifier = Modifier.weight(0.55f)
            ) {
                Slider(
                    value = barraDeslizadora,
                    onValueChange = { nuevoValor ->
                        barraDeslizadora = nuevoValor
                    },
                    valueRange = 0f..10f,
                    thumb = {
                        Box(
                            modifier = Modifier.size(20.dp).background(Color.Blue, CircleShape)
                        )
                    }
                )
            }
            Text(
                "${barraDeslizadora.toInt()}/10",
                modifier = Modifier.weight(0.2f)
            )

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "▼ sigue al desplazar ▼",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

    }
        TuReseñaAbajo(
            indiceActual = indiceActual,
            tamaño = tamaño,
            puntuacion = puntuacion,
            barraDeslizadora = barraDeslizadora
        )}

@Composable
fun TuReseñaAbajo(
    indiceActual: Int,
    tamaño: String,
    puntuacion: Int,
    barraDeslizadora: Float
){
    var vegetariana by remember { mutableStateOf(false) }
    var sinGluten by remember { mutableStateOf(false) }
    var picante by remember { mutableStateOf(false) }
    var meGusta by remember { mutableStateOf(false) }
    var descuento by remember {mutableStateOf(false)}
    var reseña by remember {mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = vegetariana,
                    onCheckedChange = { vegetariana = it }
                )
                Text("Vegetariana", fontSize = 13.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = sinGluten,
                    onCheckedChange = { sinGluten = it }
                )
                Text("Sin gluten", fontSize = 13.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = picante,
                    onCheckedChange = { picante = it }
                )
                Text("Picante", fontSize = 13.sp)
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(34.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Me gusta"
            )
            Switch(
                checked = meGusta,
                onCheckedChange = {meGusta = it}
            )
            Text(
                if(meGusta == true) {
                    "!Te gusta esta tapa¡"
                } else{
                    ""
                }
            )

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(34.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = descuento,
                onCheckedChange = {descuento = it}
            )
            Text(
                "Descuento estudiante (10%)"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = reseña,
                onValueChange = {reseña = it}
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {}
            ) {
                Text(
                    "LIMPIAR"
                )
            }
            Button(
                onClick = {}
            ) {
                Text(
                    "!PUBLICAR RESEÑA¡"
                )
            }
        }
    }
}