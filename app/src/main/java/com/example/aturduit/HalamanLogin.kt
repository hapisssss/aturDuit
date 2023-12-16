package com.example.aturduit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import java.util.StringTokenizer
import androidx.compose.ui.tooling.preview.Preview


class HalamanLogin(val navController: NavController) {



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun showLogin() {
//        var nim by remember { mutableStateOf("") }
//        var password by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }
        var dialogMessage by remember { mutableStateOf("") }

        BackHandler {
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xB21F293D)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .height(400.dp),
                shape = RoundedCornerShape(20.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Login",
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color(0xFF1F293D),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    var nim by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = nim,
                        onValueChange = { nim = it },
                        label = { Text("NIM") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                validateLogin(nim, password) { success, message ->
                                    if (success) {
                                        navController.navigate("HalamanUtama")
                                    } else {
                                        dialogMessage = message
                                        showDialog = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7F36))
                        ) {
                            Text(
                                "Login",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            // Dialog
                            if (showDialog) {
                                ErrorDialog(message = dialogMessage, onDismiss = { showDialog = false })
                            }
                        }
                        Button(
                            onClick = { navController.navigate("HalamanRegister") },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7F36))
                        ) {
                            Text(
                                "Register",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    private fun validateLogin(nim: String, password: String, onResult: (Boolean, String) -> Unit) {
        // Periksa apakah NIM atau Password kosong
        if (nim.isEmpty() || password.isEmpty()) {
            onResult(false, "Nim dan Password tidak boleh kosong!")
            return
        }
        val database = FirebaseDatabase.getInstance("https://aturduit-f3099-default-rtdb.firebaseio.com/")
        val myRef = database.getReference("users")

        myRef.child(nim).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val userPassword = dataSnapshot.child("password").value.toString()
                if (password == userPassword) {
                    onResult(true, "")
                } else {
                    onResult(false, "Nim atau Password anda salah!")
                }
            } else {
                onResult(false, "Nim atau Password anda salah!")
            }
        }.addOnFailureListener {
            onResult(false, "Error: ${it.message}")
        }
    }



    @Composable
    fun ErrorDialog(message: String, onDismiss: () -> Unit) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Error") },
            text = { Text(message) },
            confirmButton = {
                Button(onClick = { onDismiss() }) {
                    Text("OK")
                }
            }
        )


    }



}