package com.hyunjung.aiku.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.presentation.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AiKUTheme {
                SplashScreen(
                    onSplashFinished = {
//                        startActivity(Intent(this, MainActivity::class.java).apply {
//                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        })
//                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF6EE7C2)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("아이쿠! 또 지각이네?!", color = Color(0xFF1A237E), fontSize = 18.sp)
            Text(
                text = "AiKU",
                modifier = Modifier.padding(bottom = 32.dp),
                color = Color(0xFF1A237E),
                fontSize = 48.sp
            )
            SplashCharactersRow()


        }
    }
}

@Composable
fun SplashCharactersRow() {
    Row(
        modifier = Modifier
            .wrapContentWidth(align = Alignment.CenterHorizontally, unbounded = true),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.presentation_char_1),
            contentDescription = "Character 1",
        )
        Image(
            painter = painterResource(id = R.drawable.presentation_char_2),
            contentDescription = "Character 2",

            )
        Image(
            painter = painterResource(id = R.drawable.presentation_char_3),
            contentDescription = "Character 3"
        )
        Image(
            painter = painterResource(id = R.drawable.presentation_char_4),
            contentDescription = "Character 4",
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(onSplashFinished = {})
}