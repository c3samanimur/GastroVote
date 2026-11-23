package com.example.gastrovote

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gastrovote.componentes.BarraSuperior
import com.example.gastrovote.componentes.FilaBares
import com.example.gastrovote.ui.theme.GastroVoteTheme

class RutaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GastroVoteTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        BarraSuperior(
                            titulo = stringResource(R.string.titulo_ruta),
                            onAtras = { finish() }
                        )
                    }
                ) { innerPadding ->
                    val nombreBar = stringArrayResource(R.array.nombres_bares)
                    val tapa = stringArrayResource(R.array.tapas_bares)
                    val resenados = List(nombreBar.size) { false }
                    val favoritos = List(nombreBar.size) { false }

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        nombreBar.forEachIndexed { indice, nombre ->
                            FilaBares(
                                nombreBar = nombre,
                                tapa = tapa[indice],
                                resenado = resenados[indice],
                                favorito = favoritos[indice],
                                onClick = { abrirFicha(indice) }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun abrirFicha(indice: Int) {
        val intent = Intent(this, DetalleBarActivity::class.java)
        intent.putExtra(Claves.EXTRA_INDICE_BAR, indice)
        startActivity(intent)
    }
}