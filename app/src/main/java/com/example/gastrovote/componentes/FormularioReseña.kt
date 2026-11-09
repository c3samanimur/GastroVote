package com.example.gastrovote.componentes

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gastrovote.R
import com.example.gastrovote.ReseñaPublicada


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TuReseña(
    indiceActual: Int,
    onPublicar: (ReseñaPublicada) -> Unit
) {
    var tamaño by remember { mutableStateOf("Tapa") }
    var puntuacion by remember { mutableStateOf(0) }
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

        SelectorTamaño(tamaño = tamaño, seleccionarTamaño = { tamaño = it })

        PuntuacionReseña(puntuacion, { puntuacion = it })

        Spacer(modifier = Modifier.height(16.dp))

        BarraCalidadPrecio(barraDeslizadora, { barraDeslizadora = it })

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

    )
}


@Composable
private fun TuReseñaAbajo(
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

    val abrirDialogo: () -> Unit = {
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
    val enviar: () -> Unit = {
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
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            CasillaEtiqueta(
                marcado = vegetariana,
                onMarcar = { vegetariana = it },
                texto = stringResource(R.string.vegetariana)
            )

            CasillaEtiqueta(
                marcado = sinGluten,
                onMarcar = { sinGluten = it },
                texto = stringResource(R.string.sin_gluten)
            )

            CasillaEtiqueta(
                marcado = picante,
                onMarcar = { picante = it },
                texto = stringResource(R.string.picante)
            )

        }

        FilaMeGusta(meGusta, { meGusta = it })

        FilaDescuento(descuento, { descuento = it })

        CampoComentario(reseña, { reseña = it })

        Spacer(modifier = Modifier.height(24.dp))

        Botones({ dialogo = true }, enviar)



        if (dialogo) {
            DialogoAccion({ dialogo = false }, abrirDialogo)
        }
    }
}


@Composable
private fun Botones(limpiar: () -> Unit, enviar: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                limpiar()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.DeleteSweep,
                contentDescription = stringResource(R.string.limpiar),
                modifier = Modifier.size(30.dp)
            )
        }
        Button(
            onClick = {
                enviar()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = stringResource(R.string.limpiar),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun CampoComentario(reseña: String, comprobarLong: (String) -> Unit) {
    val maximo = 200
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = reseña,
            onValueChange = { nuevoTexto ->
                if (nuevoTexto.length <= maximo) {
                    comprobarLong(nuevoTexto)
                }
            },
            label = { Text(stringResource(R.string.comentario_opcional)) },
            supportingText = { Text(stringResource(R.string.contador_comentario, reseña.length, maximo))}
        )

    }
}

@Composable
private fun FilaDescuento(descuento: Boolean, onDescuento: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(34.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = descuento,
            onCheckedChange = onDescuento
        )
        Text(
            stringResource(R.string.descuento_estudiante_10)
        )
    }
}

@Composable
private fun FilaMeGusta(meGusta: Boolean, cambiarMeGusta: (Boolean) -> Unit) {
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
            onCheckedChange = cambiarMeGusta
        )
        Text(
            if (meGusta == true) {
                stringResource(R.string.te_gusta_esta_tapa)
            } else {
                ""
            }
        )

    }
}

@Composable
private fun CasillaEtiqueta(
    marcado: Boolean,
    onMarcar: (Boolean) -> Unit,
    texto: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = marcado,
            onCheckedChange = onMarcar
        )
        Text(texto, fontSize = 13.sp)
    }
}

@Composable
private fun SelectorTamaño(tamaño: String, seleccionarTamaño: (String) -> Unit) {
    Column(

    ) {
        Text(
            stringResource(R.string.tama_o)
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OpcionTamaño("Tapa", R.string.tamano_tapa, tamaño, seleccionarTamaño)
            OpcionTamaño("Media", R.string.media, tamaño, seleccionarTamaño)
            OpcionTamaño("Ración", R.string.raci_n, tamaño, seleccionarTamaño)
        }
    }

}
@Composable
private fun OpcionTamaño(
    valor: String,
    @StringRes texto: Int,
    seleccionado: String,
    onSeleccionar: (String) -> Unit
) {
    Row(
        modifier = Modifier.selectable(
            selected = seleccionado == valor,
            onClick = { onSeleccionar(valor) },
            role = Role.RadioButton
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = seleccionado == valor, onClick = null)
        Spacer(modifier = Modifier.width(6.dp))
        Text(stringResource(texto))
    }
}

@Composable
private fun PuntuacionReseña(puntuacion: Int, puntuar: (Int) -> Unit) {
    Column {
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
@OptIn(ExperimentalMaterial3Api::class)
private fun BarraCalidadPrecio(
    barraDeslizadora: Float,
    onBarraDeslizador: (Float) -> Unit
) {
    Column(

    ) {
        Text(stringResource(R.string.calidad_precio))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Slider(
                value = barraDeslizadora,
                onValueChange = onBarraDeslizador,
                valueRange = 0f..10f,
                modifier = Modifier.weight(1f),
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color.Blue, CircleShape)
                    )
                }
            )
            Text(stringResource(R.string.valor_sobre_10, barraDeslizadora.toInt()))
        }
    }

}

