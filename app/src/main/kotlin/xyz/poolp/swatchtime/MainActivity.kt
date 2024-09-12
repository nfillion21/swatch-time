package xyz.poolp.swatchtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import xyz.poolp.feature.time.TimeScreen
import xyz.poolp.swatchtime.ui.theme.SwatchTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwatchTimeTheme {
                TimeScreen()
            }
        }
    }
}