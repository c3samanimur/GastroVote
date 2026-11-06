package com.example.gastrovote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FilaEstrellas(
                  puntuacion: Int,
                  tamañoIcono: Dp = 24.dp,
                  onPuntuar: ((Int) -> Unit)? = null
                  ) {
    Row(
    ) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= puntuacion) Icons.Filled.Star else Icons.Outlined.StarBorder,
                contentDescription = null,
                tint = Color(0xFFE8A317),
                modifier = Modifier.size(tamañoIcono).then(if(onPuntuar != null)  Modifier.clickable{onPuntuar(i)} else Modifier )
            )
        }
    }
}