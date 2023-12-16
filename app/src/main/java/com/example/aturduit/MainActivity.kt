package com.example.aturduit

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.aturduit.ui.theme.AturduitTheme
import com.example.aturduit.ui.theme.HalamanPendapatan

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AturduitTheme {
                // A surface container using the 'background' color from the theme
                val colourBase = 0xFF090D15
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(colourBase)
                ) {

                    //NavigationHost()
                    NavigationHost()




                }
            }
        }
    }
}