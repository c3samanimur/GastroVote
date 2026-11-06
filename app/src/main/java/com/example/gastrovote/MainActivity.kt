package com.example.gastrovote


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun BarraSelectora(){
    val nombreBares = stringArrayResource(id = R.array.nombres_bares)
    val tapasBares = stringArrayResource(id = R.array.tapas_bares)
    val descripcionTapas = stringArrayResource(id = R.array.descripciones_tapas)
    val precioTapas = stringArrayResource(id = R.array.precios_tapas)
    val baresSeleccionados = remember { mutableStateListOf(
        false, false, false, false, false, false, false, false
    ) }

    var ultimaReseña by remember {mutableStateOf<ReseñaPublicada?>(null)}
    var indice by remember { mutableStateOf(0) }
    val totalBares = nombreBares.size
    val nombreActual = nombreBares[indice]
    val posicion = indice+1

    val contexto = LocalContext.current
    val favoritos = remember { mutableStateListOf(false, false, false, false, false, false, false, false) }
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

    BarraSelectora(irAnterior, nombreActual, posicion, totalBares, irSiguiente)

    FichaBar(
        nombreBar = nombreBares[indice],
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


    TuReseña(indiceActual = indice,
        onPublicar = { reseña ->
        baresSeleccionados[indice] = true
        ultimaReseña = reseña
    })

    ultimaReseña?.let { reseña ->
        ResumenReseña(reseña)
    }

    var baresReseñados = baresSeleccionados.count() {it}

    Text(
        stringResource(R.string.ruta_progreso, baresReseñados, totalBares),
        fontWeight = FontWeight.ExtraBold
    )
    LinearProgressIndicator(
        progress = {baresReseñados.toFloat() / totalBares},
        modifier = Modifier.fillMaxWidth()
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TuReseña(indiceActual: Int,
             onPublicar: (ReseñaPublicada) -> Unit){
    var tamaño by remember { mutableStateOf("Tapa") }
    var puntuacion by remember {mutableStateOf(0)}
    var barraDeslizadora by remember { mutableStateOf(5f) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            stringResource(R.string.tu_rese_a),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.ExtraBold
        )

        SelectorTamaño(tamaño = tamaño, seleccionarTamaño = {tamaño = it})

        PuntuacionReseña(puntuacion, {puntuacion = it})

        Spacer(modifier = Modifier.height(16.dp))

        BarraCalidadPrecio(barraDeslizadora, {barraDeslizadora = it})

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.sigue_al_desplazar),
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

    }
        TuReseñaAbajo(
            indiceActual = indiceActual,
            tamaño = tamaño,
            puntuacion = puntuacion,
            barraDeslizadora = barraDeslizadora,
            onLimpiar = {
                tamaño = "Tapa"
                puntuacion = 0
                barraDeslizadora = 5f
            },
            onPublicar = onPublicar

        )}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BarraCalidadPrecio(
    barraDeslizadora: Float,
    onBarraDeslizador: (Float) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.calidad_precio),
            modifier = Modifier.weight(0.3f)
        )
        Box(
            modifier = Modifier.weight(0.55f)
        ) {
            Slider(
                value = barraDeslizadora,
                onValueChange = onBarraDeslizador,
                valueRange = 0f..10f,
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color.Blue, CircleShape)
                    )
                }
            )
        }
        Text(
            stringResource(R.string.valor_sobre_10, barraDeslizadora.toInt()),
            modifier = Modifier.weight(0.2f)
        )

    }
}

@Composable
private fun PuntuacionReseña(puntuacion: Int, puntuar: (Int) -> Unit) {
    Column() {
        Text(
            stringResource(R.string.puntuacion_)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            FilaEstrellas(puntuacion = puntuacion, tamañoIcono = 36.dp, onPuntuar = puntuar)
            Text(
                if (puntuacion == 0) {
                    stringResource(R.string.sin_puntuar)
                } else if (puntuacion == 1 || puntuacion == 2) {
                    stringResource(R.string.mejorable)
                } else if (puntuacion == 3) {
                    stringResource(R.string.correcta)
                } else if (puntuacion == 4) {
                    stringResource(R.string.muy_buena)
                } else if (puntuacion == 5) {
                    stringResource(R.string.espectacular)
                } else {
                    ""
                }
            )
        }
    }
}

@Composable
private fun SelectorTamaño(tamaño: String, seleccionarTamaño: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.tama_o)
        )
        RadioButton(
            selected = (tamaño == "Tapa"),
            onClick = { seleccionarTamaño("Tapa") }
        )
        Text(
            stringResource(R.string.tamano_tapa)
        )
        RadioButton(
            selected = (tamaño == "Media"),
            onClick = { seleccionarTamaño("Media") }
        )
        Text(
            stringResource(R.string.media)
        )
        RadioButton(
            selected = (tamaño == "Ración"),
            onClick = { seleccionarTamaño("Ración") }
        )
        Text(
            stringResource(R.string.raci_n)
        )
    }
}


@Composable
fun TuReseñaAbajo(
    indiceActual: Int,
    tamaño: String,
    puntuacion: Int,
    barraDeslizadora: Float,
    onLimpiar: () -> Unit,
    onPublicar: (ReseñaPublicada) -> Unit
) {
    var vegetariana by remember { mutableStateOf(false) }
    var sinGluten by remember { mutableStateOf(false) }
    var picante by remember { mutableStateOf(false) }
    var meGusta by remember { mutableStateOf(false) }
    var descuento by remember { mutableStateOf(false) }
    var reseña by remember { mutableStateOf("") }
    var contexto = LocalContext.current
    var mostrarReseña by remember { mutableStateOf(false) }
    var dialogo by remember { mutableStateOf(false) }

    val nombreBares = stringArrayResource(id = R.array.nombres_bares)
    val tapasBares = stringArrayResource(id = R.array.tapas_bares)
    val descripcionTapas = stringArrayResource(id = R.array.descripciones_tapas)
    val precioTapas = stringArrayResource(id = R.array.precios_tapas)

    var multiplicador = when (tamaño) {
        "Tapa" -> 1
        "Media" -> 2
        "Ración" -> 3
        else -> 1
    }

    var resultado = ((precioTapas[indiceActual].toDoubleOrNull() ?: 0.0) * multiplicador)
    var descuentoNum = (resultado * 0.1)

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
                Text(stringResource(R.string.vegetariana), fontSize = 13.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = sinGluten,
                    onCheckedChange = { sinGluten = it }
                )
                Text(stringResource(R.string.sin_gluten), fontSize = 13.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = picante,
                    onCheckedChange = { picante = it }
                )
                Text(stringResource(R.string.picante), fontSize = 13.sp)
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(34.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.me_gusta)
            )
            Switch(
                checked = meGusta,
                onCheckedChange = { meGusta = it }
            )
            Text(
                if (meGusta == true) {
                    stringResource(R.string.te_gusta_esta_tapa)
                } else {
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
                onCheckedChange = { descuento = it }
            )
            Text(
                stringResource(R.string.descuento_estudiante_10)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = reseña,
                onValueChange = { nuevoTexto ->
                    if (reseña.length <= 200) {
                        reseña = nuevoTexto
                    }
                },
                label = { Text(stringResource(R.string.comentario_opcional)) }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    dialogo = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.DeleteSweep, contentDescription = stringResource(R.string.limpiar),
                    modifier = Modifier.size(30.dp)
                )
            }
            Button(
                onClick = {
                    if (puntuacion == 0) {
                        Toast.makeText(
                            contexto,
                            R.string.puntua_la_tapa_antes_de_publicar,
                            Toast.LENGTH_SHORT
                        ).show()
                        mostrarReseña = false
                    } else {
                        mostrarReseña = true
                        var nuevaReseña = ReseñaPublicada(
                            nombreBar = nombreBares[indiceActual],
                            nombreTapa = tapasBares[indiceActual],
                            precioBase = precioTapas[indiceActual].toDoubleOrNull() ?: 0.0,
                            tamaño = tamaño,
                            puntuacion = puntuacion,
                            calidadPrecio = barraDeslizadora.toInt(),
                            vegetariana = vegetariana,
                            sinGluten = sinGluten,
                            picante = picante,
                            meGusta = meGusta,
                            descuento = descuento,
                            comentario = reseña,
                            precioFinal = if (descuento) {
                                resultado - descuentoNum
                            } else {
                                resultado
                            }
                        )
                        onPublicar(nuevaReseña)
                        Toast.makeText(contexto, R.string.resena_publicada, Toast.LENGTH_SHORT).show()
                    }

                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Send, contentDescription = stringResource(R.string.limpiar),
                    modifier = Modifier.size(30.dp)
                )
            }
        }


        if (dialogo) {
            AlertDialog(
                onDismissRequest = {
                    dialogo = false
                },
                title = {
                    Text(stringResource(R.string.desea_borrar_la_rese_a))
                },
                text = {
                    Text(stringResource(R.string.se_borraran_los_datos_del_formulario))
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            vegetariana = false
                            sinGluten = false
                            picante = false
                            meGusta = false
                            descuento = false
                            reseña = ""
                            mostrarReseña = false
                            onLimpiar()
                            dialogo = false
                            Toast.makeText(contexto, R.string.formulario_limpio, Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Text(
                            stringResource(R.string.si_borrar)
                        )
                    }

                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            dialogo = false
                        }
                    ) {
                        Text(
                            stringResource(R.string.cancelar)
                        )
                    }
                }

            )
        }
    }
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


    @Composable
    fun ResumenReseña(reseña: ReseñaPublicada) {
        var multiplicador = when (reseña.tamaño) {
            "Tapa" -> 1
            "Media" -> 2
            "Ración" -> 3
            else -> 1
        }
        var vegetariana = reseña.vegetariana
        var sinGluten = reseña.sinGluten
        var picante = reseña.picante

        var precioSinDescuento = reseña.precioBase * multiplicador

        val descuentoNum = if (reseña.descuento) {
            precioSinDescuento * 0.1
        } else {
            0
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    width = 1.5.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor =  MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.resumen_tapa_bar, reseña.nombreTapa, reseña.nombreBar)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                   val tamañoTexto = stringResource(
                       when (reseña.tamaño) {
                           "Media" -> R.string.media
                           "Ración" -> R.string.raci_n
                           else -> R.string.tamano_tapa
                       }
                   )

                    val opinionTexto = stringResource(
                        when (reseña.puntuacion){
                            1, 2 -> R.string.mejorable
                            3 -> R.string.correcta
                            4 -> R.string.muy_buena
                            else -> R.string.espectacular
                        }
                    )
                    Text(
                        stringResource(R.string.resumen_tamano_puntuacion, tamañoTexto, reseña.puntuacion, opinionTexto)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(
                            R.string.resumen_calidad_megusta,
                            reseña.calidadPrecio,
                            stringResource(if (reseña.meGusta) R.string.si else R.string.no)
                        )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        stringResource(
                            R.string.resumen_precio,
                            reseña.precioBase,
                            if (reseña.tamaño == "Tapa") 1 else if (reseña.tamaño == "Media") 2 else 3,
                            precioSinDescuento
                        )
                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (reseña.descuento) {
                        Text(stringResource(R.string.resumen_descuento, descuentoNum.toDouble()))
                    } else {
                        Text(stringResource(R.string.descuento_estudiante_no_aplicado))
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (reseña.descuento) {
                        Text(stringResource(R.string.resumen_precio_final, precioSinDescuento - descuentoNum.toDouble()))
                    } else {
                        Text(stringResource(R.string.resumen_precio_final, precioSinDescuento))
                    }
                }

            }
        }
    }




