package com.example.gastrovote

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ResumenReseña(reseña: ReseñaPublicada) {
    var multiplicador = when (reseña.tamaño) {
        "Tapa" -> 1
        "Media" -> 2
        "Ración" -> 3
        else -> 1
    }
    var etiquetas = mutableListOf<String>()
    if(reseña.vegetariana) etiquetas.add(stringResource(R.string.vegetariana))
    if(reseña.sinGluten) etiquetas.add(stringResource(R.string.sin_gluten))
    if(reseña.picante) etiquetas.add(stringResource(R.string.picante))

    var etiquetaTexto =
        if(etiquetas.isEmpty()){
            stringResource(R.string.etiquetas_ninguna)
        } else{
            etiquetas.joinToString(", ")
        }

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
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

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
                    when (reseña.puntuacion) {
                        1, 2 -> R.string.mejorable
                        3 -> R.string.correcta
                        4 -> R.string.muy_buena
                        else -> R.string.espectacular
                    }
                )
                Text(
                    stringResource(
                        R.string.resumen_tamano_puntuacion,
                        tamañoTexto,
                        reseña.puntuacion,
                        opinionTexto
                    )
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
                    Text(
                        stringResource(
                            R.string.resumen_precio_final,
                            precioSinDescuento - descuentoNum.toDouble()
                        )
                    )
                } else {
                    Text(stringResource(R.string.resumen_precio_final, precioSinDescuento))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
               Text(stringResource(R.string.resumen_etiquetas, etiquetaTexto))
            }
            if (reseña.comentario.isNotBlank()){
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.resumen_comentario, reseña.comentario))
                }
            }

        }
    }
}