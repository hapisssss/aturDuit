package com.example.aturduit

data class RiwayatPendapatan(
    val nominal: Int,
    val tanggal: String
) {
    companion object {
        public val riwayatPendapatan = listOf(
            RiwayatPendapatan(nominal = 100000, tanggal = "2023-11-27"),
            RiwayatPendapatan(nominal = 200000, tanggal = "2023-11-28"),
            RiwayatPendapatan(nominal = 200000, tanggal = "2023-11-28"),
            RiwayatPendapatan(nominal = 200000, tanggal = "2023-11-28"),
            RiwayatPendapatan(nominal = 200000, tanggal = "2023-11-28"),
            RiwayatPendapatan(nominal = 300000, tanggal = "2023-11-29")
        )
    }
}