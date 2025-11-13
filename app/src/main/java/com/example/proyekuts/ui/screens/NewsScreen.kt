package com.example.proyekuts.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.proyekuts.ui.theme.ProyekUTSTheme

// --- 1. Model Data (Tidak Berubah) ---
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

// --- 2. Data Statis (Dummy, Tidak Berubah) ---
private val staticArticles = listOf(
    NewsArticle(
        1,
        "Terobosan Baru dalam Kecerdasan Buatan",
        "Model AI baru yang dapat memahami konteks dengan lebih baik telah diluncurkan, menjanjikan revolusi di berbagai sektor industri.",
        "Teknologi",
        "12 Okt 2023",
        "https://picsum.photos/seed/ai/400/200"
    ),
    NewsArticle(
        2,
        "Tim Nasional Maju ke Babak Final",
        "Kemenangan dramatis 2-1 atas lawan bebuyutan memastikan tempat di puncak turnamen. Gol penentu dicetak di menit akhir.",
        "Olahraga",
        "12 Okt 2023",
        "https://picsum.photos/seed/bola/400/200"
    ),
    NewsArticle(
        3,
        "Pasar Saham Global Mengalami Koreksi",
        "Analis memperkirakan volatilitas akan terus berlanjut seiring dengan ketidakpastian ekonomi global. Investor disarankan untuk berhati-hati.",
        "Ekonomi",
        "11 Okt 2023",
        "https://picsum.photos/seed/saham/400/200"
    ),
    NewsArticle(
        4,
        "Pentingnya Sarapan Sehat Setiap Pagi",
        "Ahli gizi dari universitas terkemuka menyarankan kombinasi serat dan protein untuk memulai hari dengan energi maksimal.",
        "Kesehatan",
        "11 Okt 2023",
        "https://picsum.photos/seed/sarapan/400/200"
    ),
    NewsArticle(
        5,
        "Kebijakan Baru Diumumkan Pemerintah",
        "Regulasi perbankan akan diperketat untuk menjaga stabilitas keuangan nasional. Aturan baru ini akan berlaku mulai kuartal depan.",
        "Ekonomi",
        "11 Okt 2023",
        "https://picsum.photos/seed/pemerintah/400/200"
    )
)

private val staticCategories = listOf(
    Category("Semua", isSelected = true),
    Category("Teknologi"),
    Category("Olahraga"),
    Category("Ekonomi"),
    Category("Kesehatan")
)

// --- 3. Entry Point & Navigation ---

private object NewsNav {
    const val LIST_ROUTE = "news_list"
    const val DETAIL_ROUTE = "news_detail/{articleId}"
    fun detailRouteFor(articleId: Int) = "news_detail/$articleId"
}

@Composable
fun NewsNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NewsNav.LIST_ROUTE,
        modifier = modifier
    ) {
        composable(NewsNav.LIST_ROUTE) {
            NewsListScreen(
                onArticleClick = { articleId ->
                    navController.navigate(NewsNav.detailRouteFor(articleId))
                }
            )
        }
        composable(NewsNav.DETAIL_ROUTE) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getString("articleId")?.toIntOrNull()
            val article = staticArticles.find { it.id == articleId }
            if (article != null) {
                NewsDetailScreen(
                    article = article,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            // Opsional: Tampilkan layar error jika artikel tidak ditemukan
        }
    }
}

// --- 4. Composable untuk Setiap Layar ---

@Composable
private fun NewsListScreen(onArticleClick: (Int) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var categories by remember { mutableStateOf(staticCategories) }

    val filteredArticles by remember(categories, searchQuery) {
        derivedStateOf {
            val selectedCategory = categories.first { it.isSelected }.name
            val articles = if (selectedCategory == "Semua") staticArticles else staticArticles.filter { it.category == selectedCategory }
            if (searchQuery.isBlank()) articles else articles.filter { it.title.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        HeaderSection(
            searchQuery = searchQuery,
            onQueryChange = { searchQuery = it },
            categories = categories,
            onCategoryClick = { clickedCategoryName ->
                categories = categories.map { it.copy(isSelected = it.name == clickedCategoryName) }
            }
        )
        ArticleListSection(
            articles = filteredArticles,
            onArticleClick = onArticleClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsDetailScreen(article: NewsArticle, onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(modifier = Modifier.height(250.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(article.imageUrl).crossfade(true).build(),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Tombol kembali di atas gambar
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.4f))
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = Color.White)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "${article.category} • ${article.date}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Divider()
            Text(
                text = article.description, // Tampilkan deskripsi lengkap
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 24.sp,
                color = Color.DarkGray
            )
        }
    }
}

// --- 5. Komponen UI Bagian Kecil (Header, List, Item, dll.) ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeaderSection(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    categories: List<Category>,
    onCategoryClick: (String) -> Unit
) {
    val darkBlueGradient = Brush.verticalGradient(listOf(Color(0xFF013773), Color(0xFF002145)))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(darkBlueGradient)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Berita Terkini", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.Notifications, "Notifikasi", tint = Color.White)
        }
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            placeholder = { Text("Cari berita...", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, "Cari", tint = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(32.dp),
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
        CategorySection(categories = categories, onCategoryClick = onCategoryClick)
    }
}

@Composable
private fun CategorySection(categories: List<Category>, onCategoryClick: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryChip(category = category, onClick = { onCategoryClick(category.name) })
        }
    }
}

@Composable
private fun CategoryChip(category: Category, onClick: () -> Unit) {
    val darkBlueBg = Color(0xFF013674)
    val lightGrayBg = Color(0xFFB3C8DF)
    val backgroundColor = if (category.isSelected) Color.White else lightGrayBg
    val textColor = if (category.isSelected) darkBlueBg else Color.White
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = category.name, color = textColor, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ArticleListSection(
    articles: List<NewsArticle>,
    onArticleClick: (Int) -> Unit, // Tambahkan parameter onClick
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        items(articles, key = { it.id }) { article ->
            ArticleItem(
                article = article,
                onClick = { onArticleClick(article.id) } // Panggil lambda dengan id
            )
        }
    }
}

@Composable
private fun ArticleItem(article: NewsArticle, onClick: () -> Unit) { // Tambahkan onClick
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick), // Buat Card bisa diklik
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(article.imageUrl).crossfade(true).build(),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(article.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black, maxLines = 2)
                Text(article.description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray, maxLines = 2)
                Text("${article.category} • ${article.date}", style = MaterialTheme.typography.bodySmall, color = Color.Gray.copy(alpha = 0.8f))
            }
        }
    }
}

// --- 6. Preview ---

@Preview(showBackground = true, name = "News List")
@Composable
private fun NewsListScreenPreview() {
    ProyekUTSTheme {
        NewsListScreen(onArticleClick = {})
    }
}

@Preview(showBackground = true, name = "News Detail")
@Composable
private fun NewsDetailScreenPreview() {
    ProyekUTSTheme {
        NewsDetailScreen(article = staticArticles.first(), onNavigateBack = {})
    }
}
