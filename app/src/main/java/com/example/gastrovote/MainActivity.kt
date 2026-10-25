package com.example.gastrovote

import android.R.attr.contentDescription
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
fun Pantalla (modifier: Modifier){
    Column(
        modifier = Modifier.fillMaxSize(),
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
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button (
                onClick = {
                    if(indice > 0 && indice <= 8){
                        indice--
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
                    if(indice < totalBares - 1){
                        indice++
                    } else{
                        0
                    }
                }
            ) {
                Text(">",
                    fontSize = 36.sp)
            }
        }
    }
}