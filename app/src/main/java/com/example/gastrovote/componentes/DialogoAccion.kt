package com.example.gastrovote.componentes

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.gastrovote.R

@Composable
fun DialogoAccion(cerrarDialogo: () -> Unit, abrirDialogo: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            cerrarDialogo()
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
                    abrirDialogo()
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
                    cerrarDialogo()
                }
            ) {
                Text(
                    stringResource(R.string.cancelar)
                )
            }
        }

    )
}