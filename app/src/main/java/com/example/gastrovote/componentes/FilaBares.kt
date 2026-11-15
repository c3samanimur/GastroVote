package com.example.gastrovote.componentes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun FilaBares(
    nombreBar: String,
    tapa: String,
    resenado: Boolean,
    favorito: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.5.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    nombreBar,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    tapa
                )
            }
            if (favorito) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Has marcado esta tapa como favorita",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            if (resenado) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Te gusta esta tapa",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}