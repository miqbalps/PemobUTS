package com.example.proyekuts.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.proyekuts.R
import com.example.proyekuts.ui.theme.ProyekUTSTheme

// Data class
private data class Contact(
    val name: String,
    val phoneNumber: String
)

// Data statis
private val staticContacts = listOf(
    Contact("Aisha Putri", "+62 812-3456-7890"),
    Contact("Ujang Santoso", "+62 813-1122-3344"),
    Contact("Budiman Tarso", "+62 815-5566-7788"),
    Contact("Dewi Anggraini", "+62 817-9988-7766"),
    Contact("Eko Prasetyo", "+62 818-1234-5678"),
    Contact("Fitri Handayani", "+62 819-8765-4321"),
    Contact("Galih Pratama", "+62 821-2345-6789"),
    Contact("Hana Yuliana", "+62 822-8877-6655"),
    Contact("Indra Wijaya", "+62 857-1111-2222"),
    Contact("Joko Susilo", "+62 858-3333-4444"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        // --- Header Biru (Sekarang berisi Judul & SearchBar) ---
        ContactHeader()

        // --- Daftar Kontak ---
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            // Tambahkan padding di atas agar list tidak menempel header
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            items(staticContacts) { contact ->
                ContactItem(contact = contact)
            }
        }
    }
}

@Composable
private fun ContactHeader() {
    val darkBlueBg = Color(0xFF013773)
    val darkBlueGradient = Brush.verticalGradient(
        colors = listOf(darkBlueBg, Color(0xFF002145))
    )
    // --- PERUBAHAN 1: Ubah Row menjadi Column ---
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(darkBlueGradient)
            // Atur padding untuk seluruh kotak header
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Beri jarak antara judul dan search
    ) {
        // Row untuk Judul dan Ikon "Add"
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "List Kontak",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.PersonAdd,
                contentDescription = "Tambah Kontak",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        // --- PERUBAHAN 2: Panggil SearchBar di dalam header ---
        SearchBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar() {
    var searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Cari kontak...", color = Color.Gray) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Cari",
                tint = Color.Gray
            )
        },
        // --- PERUBAHAN 3: Hapus padding, biarkan parent (header) yg mengatur ---
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.LightGray
        ),
        singleLine = true
    )
}

@Composable
private fun ContactItem(contact: Contact, modifier: Modifier = Modifier) {
    val imageUrl = "https://ui-avatars.com/api/?name=${contact.name.replace(" ", "+")}&background=random&size=128"

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Avatar untuk ${contact.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contact.phoneNumber,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Lihat Detail",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactScreenPreview() {
    ProyekUTSTheme {
        ContactScreen()
    }
}