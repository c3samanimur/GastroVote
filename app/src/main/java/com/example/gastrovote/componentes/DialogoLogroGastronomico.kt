package com.example.gastrovote.componentes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.gastrovote.R

@Composable
fun DialogoLogroGastronomico(cerrarDialogo: () -> Unit){
    AlertDialog(
        onDismissRequest = cerrarDialogo,
        title = { Text(stringResource(R.string.logro_titulo)) },
        text = { Text(stringResource(R.string.logro_texto)) },
        confirmButton = {
            TextButton(onClick = cerrarDialogo) {
                Text(stringResource(R.string.logro_aceptar))
            }
        }
    )
}