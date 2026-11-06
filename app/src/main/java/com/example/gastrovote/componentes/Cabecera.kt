package com.example.gastrovote.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gastrovote.R

@Composable
fun Cabecera() {
    Image(
        painter = painterResource(R.drawable.banner),
        contentDescription = stringResource(R.string.banner_descripcion),
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        stringResource(R.string.gastrovote),
        fontSize = 36.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        stringResource(R.string.ruta_de_la_tapa),
        fontSize = 24.sp
    )
}