package com.example.aturduit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aturduit.ui.theme.AturduitTheme

class HalamanUtama(val navController: NavController) {
    @Composable
    fun ShowHalamanUtama (riwayat: List<RiwayatPendapatan>){
        val total = "120000000"//riwayat.sumBy { it.nominal }
        Column(
            modifier =  Modifier
                .padding(10.dp)
        ) {
            Header()
            // Grafik
            Text(
                text = "Jumlah uang anda : $total",
            )
            Row (
                modifier = Modifier
                    .padding(3.dp)
            ){
                Button(onClick = { navController.navigate("HalamanPendapatan")}) {
                    Text(text = "Tambah Pemasukan")
                }
                Button(onClick = { navController.navigate("HalamanPengeluaran")}) {
                    Text(text = "Tambah Pengeluaran")
                }
            }

            RiwayatList(riwayat)
        }
    }

    @Composable
    fun Header(
    ) {
        Column(
            modifier = Modifier.padding(2.dp)
        ) {
            Text(
                text = "Login sebagai Nama",
                fontSize = 24.sp
            )
        }
    }

    @Composable
    fun RiwayatList(riwayat: List<RiwayatPendapatan>) {
        if (riwayat.isNotEmpty()) {
            LazyColumn {
                items(riwayat) { riwayatItem ->
                    RiwayatCard(riwayatItem)
                }
            }
        } else {
            Box(contentAlignment = Alignment.Center) {
                Text("No people to greet :(")
            }
        }
    }

    @Composable
    fun RiwayatCard(
        riwayatItem: RiwayatPendapatan
    ){

        Card (
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column (modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${riwayatItem.nominal}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tanggal ${riwayatItem.tanggal}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24) ,
                        contentDescription = "Delete")
                }
            }
        }
    }

}