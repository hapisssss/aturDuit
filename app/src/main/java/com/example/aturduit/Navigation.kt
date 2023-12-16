package com.example.aturduit


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aturduit.ui.theme.HalamanPendapatan


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "HalamanLogin") {
        composable("HalamanLogin") {
            HalamanLogin(navController).showLogin()
        }
        composable("HalamanRegister") {
            HalamanRegister(navController).ShowRegister()
        }
        composable("HalamanPendapatan"){
            HalamanPendapatan(navController).ShowHalamanPendapatan()
        }
        composable("HalamanPengeluaran"){
            HalamanPengeluaran(navController).ShowHalamanPengeluaran()
        }
        composable("HalamanUtama"){
            HalamanUtama(navController).ShowHalamanUtama(RiwayatPendapatan.riwayatPendapatan)
        }
        // Tambahkan tujuan lainnya di sini
    }
}