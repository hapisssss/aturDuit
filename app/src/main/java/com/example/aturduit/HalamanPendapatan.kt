package com.example.aturduit.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.aturduit.R
import com.example.aturduit.User
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.util.Date

class HalamanPendapatan (val navController: NavController){


    //FireBase
    data class Pendapatan(
        val nominal: String,
        val catatan: String,
        val tanggal: String,
        val toString: String
    )
    val database = Firebase.database("https://aturduit-f3099-default-rtdb.firebaseio.com/")
    val myRef = database.getReference("Pendapatan")


    @Composable
    fun CalculatorButton(
        text: String,
        onClick: () -> Unit,
    ) {
        val buttonWidth = if (text in listOf("+", "-", "%", "X")) {
            115.dp // Set the width for specific buttons
        } else {
            85.dp // Default width for other buttons
        }
        val colour = 0xFF818FB4

        Button(
            onClick = onClick,
            modifier = Modifier
                .width(buttonWidth)
                .height(79.dp) // Set the height as needed
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp), // Adjust the corner radius as needed
            colors = ButtonDefaults.buttonColors(containerColor = Color(colour))

        )
        {
            Text(text)
        }

    }

    fun hitungOperasiMatematika(input: String): String {
        try {
            // Menghilangkan spasi dari input
            val cleanInput = input.replace(" ", "")

            // Menyimpan nilai awal
            var result = 0.0
            // Menyimpan operator terakhir
            var operator = '+'

            // Menggunakan regex untuk memisahkan angka dan operator
            val regex = "([+-]?\\d*\\.?\\d+)([+\\-*/])?".toRegex()
            val matches = regex.findAll(cleanInput)

            for (match in matches) {
                val (operand, nextOperator) = match.destructured
                val value = operand.toFloat()

                when (operator) {
                    '+' -> result += value
                    '-' -> result -= value
                    '*' -> result *= value
                    '/' -> result /= value
                }

                // Memperbarui operator untuk iterasi berikutnya
                operator = if (nextOperator.isNotEmpty()) nextOperator[0] else '+'
            }

            // Mengubah hasil ke dalam format String
            return result.toString()
        } catch (e: Exception) {
            // Jika terjadi kesalahan, kembalikan input asli
            return input
        }
    }


    // KATEGORI

    @Composable
    fun CategoryDialog(onCategorySelected: (String) -> Unit) {
        Dialog(
            onDismissRequest = {
                // Dismiss the dialog when requested
                onCategorySelected("Cancelled")
            }
        ) {

            // Dialog content
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                    .fillMaxWidth()
            ) {
                CategoryButton("Makanan") { category ->
                    onCategorySelected(category)
                }
                CategoryButton("Kediaman") { category ->
                    onCategorySelected(category)
                }
                CategoryButton("Transport") { category ->
                    onCategorySelected(category)
                }
                CategoryButton("Other") { category ->
                    onCategorySelected(category)
                }
            }
        }
    }

    @Composable
    fun CategoryButton(category: String, onClick: (String) -> Unit) {
        Button(
            onClick = {
                onClick(category) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8036))
        ) {
            Text(category, color = Color.White)
        }
    }








    @OptIn(ExperimentalMaterial3Api::class)

    @Composable
    public fun ShowHalamanPendapatan() {
        var input by remember { mutableStateOf("") }
        val operation = ArrayList<String>()
        val colourBase = 0xFF090D15
        var catatanText by remember { mutableStateOf("") }
        var selectedCategory by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }
        var dialogMessage by remember { mutableStateOf("") }
        var tanggal = Date()





        Column(
            modifier = Modifier
                .fillMaxSize(),

            ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth() // Mengisi lebar maksimal layar
                    .height(60.dp)  // Menentukan tinggi persegi panjang
                    .background(Color(colourBase))  // Warna latar belakang persegi panjang
                    .padding(16.dp), // Padding untuk memberikan ruang di sekitar teks
                // Mengatur contentAlignment agar teks berada di tengah
            ) {

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable {


                        },


                    ) {
                    Text(
                        text = "Kembali",
                        color = Color.White, // Warna teks di dalam persegi panjang
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start, // Posisi teks di tengah persegi panjang
                        modifier = Modifier.clickable {
                            navController.navigate("HalamanUtama")

                        }

                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 310.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Simpan",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            val pendapatann = Pendapatan(
                                input,
                                catatanText,
                                tanggal.toString(),
                                tanggal.toString()
                            )

                            myRef.child(input).setValue(pendapatann).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Tampilkan pop-up berhasil
                                    showDialog = true
                                    dialogMessage = "Pencatatan berhasil!"
                                    input  = ""
                                    catatanText = ""
                                } else {
                                    // Tampilkan pop-up gagal
                                    showDialog = true
                                    dialogMessage = "Pencatatan gagal!"
                                }
                            }
                        }
                    )
                }

                // ... (unchanged code)

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Informasi") },
                        text = { Text(dialogMessage) },
                        confirmButton = {
                            Button(onClick = { showDialog = false }) {
                                Text("OK")
                            }
                        }
                    )
                }

            }

            //ColorChangingBox
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(Color(colourBase))// Ganti dengan warna latar belakang yang diinginkan
                    .padding(16.dp)
                    .clickable { /* Perform action when the entire box is clicked */ },
            ) {

            }


            OutlinedTextField(
                value = catatanText,
                onValueChange = { newCatatanText ->
                    catatanText = newCatatanText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .padding(5.dp)
                    .padding(start = 13.dp),
                textStyle = TextStyle.Default.copy(color = Color.White), // Ganti warna teks sesuai keinginan
                maxLines = 10, // Atur jumlah maksimum baris
                shape = RoundedCornerShape(8.dp), // Mengatur sudut elemen input
                label = {
                    Text(
                        "Note",
                        color = Color.White // Ganti warna label sesuai keinginan, dalam contoh ini putih
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF818FB4), // Ganti dengan nilai hex RGB warna yang diinginkan
                    textColor = Color.White // Ganti dengan warna teks yang diinginkan
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // TextField untuk input
                OutlinedTextField(
                    value = input,
                    onValueChange = { newInput ->
                        input = newInput
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFF818FB4), // Ganti dengan nilai hex RGB warna yang diinginkan
                        textColor = Color.White // Ganti dengan warna teks yang diinginkan
                    )
                )

                // Tombol backspace
                IconButton(
                    onClick = {
                        if (input.isNotEmpty()) {
                            input = input.dropLast(1)
                            // Hapus satu operator dari list operation jika ada
                            if (operation.isNotEmpty()) {
                                operation.removeAt(operation.size - 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(55.dp)
                        .background(Color(colourBase)),

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_backspace_24),
                        contentDescription = "Backspace"
                    )
                }
            }

            // ... (unchanged code for numeric buttons)

            // Row for the first set of buttons (+, 7, 8, 9)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CalculatorButton("+", onClick = {
                    input += "+"
                    operation.add("+")
                })
                CalculatorButton("7", onClick = { input += "7" })
                CalculatorButton("8", onClick = { input += "8" })
                CalculatorButton("9", onClick = { input += "9" })
            }

            // Row for the second set of buttons (-, 4, 5, 6)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CalculatorButton("-", onClick = {
                    input += "-"
                    operation.add("-")
                })
                CalculatorButton("4", onClick = { input += "4" })
                CalculatorButton("5", onClick = { input += "5" })
                CalculatorButton("6", onClick = { input += "6" })
            }

            // Row for the third set of buttons (X, 1, 2, 3)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CalculatorButton("X", onClick = {
                    input += "X"
                    operation.add("*")
                })
                CalculatorButton("1", onClick = { input += "1" })
                CalculatorButton("2", onClick = { input += "2" })
                CalculatorButton("3", onClick = { input += "3" })
            }

            // Row for the fourth set of buttons (%, 0, C, =)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CalculatorButton("%", onClick = {
                    input += "/"
                    operation.add("/")
                })
                CalculatorButton("0", onClick = { input += "0" })
                CalculatorButton("C", onClick = { input = input.dropLast(input.length) })
                CalculatorButton("=", onClick = {
                    // Evaluate the expression and update the input
                    input = hitungOperasiMatematika(input)

                })

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(Color(colourBase))// Ganti dengan warna latar belakang yang diinginkan
                    .padding(16.dp)
                    .clickable { /* Perform action when the entire box is clicked */ },
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "10 november 2025",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier

                    )

                }

            }

        }


    }


}