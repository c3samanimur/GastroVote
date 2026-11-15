package com.example.gastrovote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.gastrovote.componentes.BarraSuperior
import com.example.gastrovote.ui.theme.GastroVoteTheme

class DetalleBarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val indice = intent.getIntExtra(Claves.EXTRA_INDICE_BAR, 0)
        setContent {
            GastroVoteTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {BarraSuperior(titulo = stringResource(R.string.titulo_ficha), onAtras = {finish()})}
                ) { innerPadding ->
                    Text(
                        text = "Ficha del bar $indice",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

