package com.example.proyekuts.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactPage // Diubah dari People
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Calculate // Diubah dari Calculate
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(val route: String, val icon: ImageVector, val title: String) {
    // Label diubah agar sesuai gambar
    object Biodata : NavItem("biodata", Icons.Default.Person, "Biodata")
    object Contact : NavItem("contact", Icons.Default.ContactPage, "Kontak")
    // Ikon ini untuk FAB tengah
    object Calculator : NavItem("calculator", Icons.Default.Calculate, "")
    object Weather : NavItem("weather", Icons.Default.Cloud, "Cuaca")
    object News : NavItem("news", Icons.Default.Newspaper, "Berita")
}