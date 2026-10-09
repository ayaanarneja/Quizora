package com.example.studyjams

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyjams.ui.theme.BrandPrimary
import com.example.studyjams.ui.theme.BrandPrimaryDark
import com.example.studyjams.ui.theme.BrandSoft
import com.example.studyjams.ui.theme.GradientEnd
import com.example.studyjams.ui.theme.GradientStart
import com.example.studyjams.ui.theme.TextPrimary

/** Orange gradient screen background that keeps content clear of system bars and keyboard. */
@Composable
fun GradientScreen(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(GradientStart, GradientEnd)))
            .safeDrawingPadding()
            .padding(20.dp),
        content = content
    )
}

@Composable
fun AppLogo(size: Dp, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.app_logo),
        contentDescription = stringResource(R.string.logo_description),
        modifier = modifier.size(size)
    )
}

@Composable
fun WhiteCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        shadowElevation = 6.dp
    ) {
        Column(modifier = Modifier.padding(22.dp), content = content)
    }
}

@Composable
fun Badge(text: String) {
    Box(
        modifier = Modifier
            .background(BrandSoft, RoundedCornerShape(50))
            .padding(horizontal = 14.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            color = BrandPrimaryDark,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 54.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BrandPrimary,
            contentColor = Color.White
        )
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun OutlinedAppButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 54.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, BrandPrimary),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = BrandPrimaryDark
        )
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun CreditsText(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.credits),
        modifier = modifier,
        color = TextPrimary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}
