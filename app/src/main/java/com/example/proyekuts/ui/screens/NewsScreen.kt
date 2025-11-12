package com.example.proyekuts.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.proyekuts.ui.theme.ProyekUTSTheme // Ganti theme sesuai proyek Anda

// --- 1. Model Data ---

private data class NewsArticle(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val date: String,
    val imageUrl: String
)

private data class Category(
    val name: String,
    val isSelected: Boolean = false
)

// --- 2. Data Statis (Dummy) ---

private val staticArticles = listOf(
    NewsArticle(
        1,
        "Terobosan Baru dalam Kecerdasan Buatan",
        "Model AI baru yang dapat memahami konteks dengan lebih baik.",
        "Teknologi",
        "12 Okt 2023",
        "https://picsum.photos/seed/ai/200"
    ),
    NewsArticle(
        2,
        "Tim Nasional Maju ke Babak Final",
        "Kemenangan dramatis 2-1 memastikan tempat di puncak.",
        "Olahraga",
        "12 Okt 2023",
        "https://picsum.photos/seed/bola/200"
    ),
    NewsArticle(
        3,
        "Pasar Saham Global Mengalami Koreksi",
        "Analis memperkirakan volatilitas akan terus berlanjut.",
        "Ekonomi",
        "11 Okt 2023",
        "https://picsum.photos/seed/saham/200"
    ),
    NewsArticle(
        4,
        "Pentingnya Sarapan Sehat Setiap Pagi",
        "Ahli gizi menyarankan kombinasi serat dan protein.",
        "Kesehatan",
        "11 Okt 2023",
        "https://picsum.photos/seed/sarapan/200"
    ),
    NewsArticle(
        5,
        "Kebijakan Baru Diumumkan Pemerintah",
        "Regulasi perbankan akan dapat...", // Deskripsi terpotong
        "Ekonomi",
        "11 Okt 2023",
        "https://picsum.photos/seed/pemerintah/200"
    )
)

private val staticCategories = listOf(
    Category("Semua", isSelected = true),
    Category("Teknologi"),
    Category("Olahraga"),
    Category("Ekonomi"),
    Category("Kesehatan")
)

// --- 3. Composable Utama ---

@Composable
fun NewsScreen(modifier: Modifier = Modifier) {
    // State untuk search dan kategori
    var searchQuery by remember { mutableStateOf("") }
    var categories by remember { mutableStateOf(staticCategories) }

    // State turunan untuk memfilter artikel
    val filteredArticles by remember(categories, searchQuery) {
        derivedStateOf {
            val selectedCategory = categories.first { it.isSelected }.name
            val articles = if (selectedCategory == "Semua") {
                staticArticles
            } else {
                staticArticles.filter { it.category == selectedCategory }
            }

            if (searchQuery.isBlank()) {
                articles
            } else {
                articles.filter {
                    it.title.contains(searchQuery, ignoreCase = true) ||
                            it.description.contains(searchQuery, ignoreCase = true)
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8)) // Background abu-abu muda
    ) {
        // --- Header, Search Bar, dan Kategori Chips (Biru) ---
        HeaderSection(
            searchQuery = searchQuery,
            onQueryChange = { searchQuery = it },
            categories = categories,
            onCategoryClick = { clickedCategoryName ->
                categories = categories.map {
                    it.copy(isSelected = it.name == clickedCategoryName)
                }
            }
        )

        // --- Daftar Artikel ---
        ArticleListSection(articles = filteredArticles)
    }
}

// --- 4. Komponen Bagian-Bagian UI ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeaderSection(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    categories: List<Category>, // Tambahkan parameter kategori
    onCategoryClick: (String) -> Unit // Tambahkan parameter onClick
) {
    val darkBlueBg = Color(0xFF013773)
    val darkBlueGradient = Brush.verticalGradient(
        colors = listOf(darkBlueBg, Color(0xFF002145))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(darkBlueGradient)
            .padding(bottom = 16.dp), // Padding bawah agar ada jarak dengan list artikel
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Baris Judul dan Lonceng (Padding horizontal sendiri)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp), // Atur padding di sini
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Berita Terkini",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifikasi",
                tint = Color.White
            )
        }

        // Search Bar (Padding horizontal sendiri)
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            placeholder = { Text("Cari berita...", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Cari",
                    tint = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Atur padding di sini
            shape = RoundedCornerShape(32.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )

        // --- Pindahkan CategorySection ke sini ---
        CategorySection(
            categories = categories,
            onCategoryClick = onCategoryClick
        )
    }
}

@Composable
private fun CategorySection(
    categories: List<Category>,
    onCategoryClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp), // Hanya padding horizontal
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryChip(
                category = category,
                onClick = { onCategoryClick(category.name) }
            )
        }
    }
}

@Composable
private fun CategoryChip(
    category: Category,
    onClick: () -> Unit
) {
    val darkBlueBg = Color(0xFF013674)
    val lightGrayBg = Color(0xFFB3C8DF)

    // --- PERUBAHAN DI SINI: Warna chip aktif menjadi putih ---
    val backgroundColor = if (category.isSelected) Color.White else lightGrayBg
    val textColor = if (category.isSelected) darkBlueBg else Color.White
    // --------------------------------------------------------

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = category.name,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ArticleListSection(
    articles: List<NewsArticle>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp) // Tambahkan padding atas
    ) {
        items(articles, key = { it.id }) { article ->
            ArticleItem(article = article)
        }
    }
}

@Composable
private fun ArticleItem(article: NewsArticle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Kembalikan sedikit elevasi
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Gambar
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
            )

            // Kolom Teks
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2
                )
                Text(
                    text = article.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    maxLines = 2
                )
                Text(
                    text = "${article.category} • ${article.date}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray.copy(alpha = 0.8f)
                )
            }
        }
    }
}

// --- 5. Preview ---

@Preview(showBackground = true)
@Composable
private fun NewsScreenPreview() {
    ProyekUTSTheme {
        NewsScreen()
    }
}