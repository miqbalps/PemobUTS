package com.example.proyekuts.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.proyekuts.ui.theme.ProyekUTSTheme
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// --- Data & Routes ---
private object ContactNav {
    const val LIST_ROUTE = "contact_list"
    const val DETAIL_ROUTE = "contact_detail/{name}/{phone}"

    fun detailRouteFor(contact: Contact): String {
        val encodedName = URLEncoder.encode(contact.name, StandardCharsets.UTF_8.toString())
        val encodedPhone = URLEncoder.encode(contact.phoneNumber, StandardCharsets.UTF_8.toString())
        return "contact_detail/$encodedName/$encodedPhone"
    }
}

private data class Contact(val name: String, val phoneNumber: String)

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
    Contact("Joko Susilo", "+62 858-3333-4444")
)

private val darkBlueBase = Color(0xFF013674)

/**
 * Entry point for the Contact feature, handles navigation between list and detail.
 */
@Composable
fun ContactNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ContactNav.LIST_ROUTE,
        modifier = modifier
    ) {
        composable(ContactNav.LIST_ROUTE) {
            ContactListScreen(navController = navController)
        }
        composable(ContactNav.DETAIL_ROUTE) { backStackEntry ->
            val encodedName = backStackEntry.arguments?.getString("name") ?: ""
            val encodedPhone = backStackEntry.arguments?.getString("phone") ?: ""

            val name = URLDecoder.decode(encodedName, StandardCharsets.UTF_8.toString())
            val phone = URLDecoder.decode(encodedPhone, StandardCharsets.UTF_8.toString())

            ContactDetailScreen(
                name = name,
                phoneNumber = phone,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

/**
 * Displays the list of contacts and handles search functionality.
 */
@Composable
private fun ContactListScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredContacts by remember(searchQuery) {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                staticContacts
            } else {
                staticContacts.filter {
                    it.name.contains(searchQuery, ignoreCase = true) ||
                            it.phoneNumber.replace("-", "").contains(searchQuery)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        ContactHeader(
            searchQuery = searchQuery,
            onQueryChange = { searchQuery = it }
        )

        if (filteredContacts.isEmpty()) {
            EmptyState(message = "Kontak \"$searchQuery\" tidak ditemukan.")
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(filteredContacts, key = { it.phoneNumber }) { contact ->
                    ContactItem(
                        contact = contact,
                        onContactClick = {
                            navController.navigate(ContactNav.detailRouteFor(contact))
                        }
                    )
                }
            }
        }
    }
}

/**
 * Displays the details of a single selected contact.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactDetailScreen(
    name: String,
    phoneNumber: String,
    onNavigateBack: () -> Unit
) {
    val imageUrl = "https://ui-avatars.com/api/?name=${name.replace(" ", "+")}&background=random&size=256&bold=true&color=FFFFFF"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        TopAppBar(
            title = { Text("Detail Kontak", fontWeight = FontWeight.Bold, color = Color.Black) },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali", tint = Color.Black)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true).build(),
                contentDescription = "Avatar $name",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(Modifier.height(24.dp))
            Text(name, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(Modifier.height(8.dp))
            Text(phoneNumber, style = MaterialTheme.typography.titleLarge, color = Color.Gray)
            Spacer(Modifier.height(32.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                ActionButton(icon = Icons.Default.Call, text = "Telepon") { /* TODO: Intent Panggilan */ }
                ActionButton(icon = Icons.AutoMirrored.Filled.Message, text = "Pesan") { /* TODO: Intent SMS */ }
            }
            Spacer(Modifier.height(32.dp))
            Divider()
        }
    }
}

/**
 * Header section for the contact list, including title and search bar.
 */
@Composable
private fun ContactHeader(searchQuery: String, onQueryChange: (String) -> Unit) {
    val darkBlueGradient = Brush.verticalGradient(listOf(Color(0xFF013773), Color(0xFF002145)))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(darkBlueGradient)
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("List Kontak", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.PersonAdd, "Tambah Kontak", tint = Color.White, modifier = Modifier.size(28.dp))
        }
        SearchBar(
            searchQuery = searchQuery,
            onQueryChange = onQueryChange
        )
    }
}

/**
 * A styled search input field.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(searchQuery: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        placeholder = { Text("Cari nama atau nomor...", color = Color.Gray) },
        leadingIcon = { Icon(Icons.Default.Search, "Cari", tint = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}

/**
 * A card representing a single contact in the list.
 */
@Composable
private fun ContactItem(contact: Contact, onContactClick: () -> Unit, modifier: Modifier = Modifier) {
    val imageUrl = "https://ui-avatars.com/api/?name=${contact.name.replace(" ", "+")}&background=random&size=128"
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onContactClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true).build(),
                contentDescription = "Avatar ${contact.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(contact.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(Modifier.height(4.dp))
                Text(contact.phoneNumber, style = MaterialTheme.typography.bodyMedium, color = Color.Gray, fontSize = 14.sp)
            }
            Icon(Icons.Default.ChevronRight, "Lihat Detail", tint = Color.Gray, modifier = Modifier.size(24.dp))
        }
    }
}

/**
 * A circular button with an icon and text below it, for detail screen actions.
 */
@Composable
private fun ActionButton(icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            // Menggunakan warna primary dari tema untuk konsistensi
            Icon(icon, contentDescription = text, tint = darkBlueBase)
        }
        Spacer(Modifier.height(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
    }
}

/**
 * A message displayed when a search returns no results.
 */
@Composable
private fun EmptyState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Preview for the entire Contact Navigation flow.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ContactScreenPreview() {
    ProyekUTSTheme {
        ContactNavigation()
    }
}
