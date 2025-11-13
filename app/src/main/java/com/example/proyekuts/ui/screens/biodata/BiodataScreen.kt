package com.example.proyekuts.ui.screens.biodata

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyekuts.R
import com.example.proyekuts.ui.theme.ProyekUTSTheme
import java.util.Calendar

// Helper untuk menyimpan state saat terjadi perubahan konfigurasi (misal: rotasi layar)
private val BiodataStateSaver = Saver<BiodataState, BiodataUiState>(
    save = { it.uiState },
    restore = { BiodataState().apply { uiState = it } }
)

// Warna kustom yang digunakan di seluruh layar
private val darkBlueBase = Color(0xFF013674)
private val darkBlueBg = Color(0xFF013773)
private val darkBlueGradient = Brush.verticalGradient(
    colors = listOf(darkBlueBg, Color(0xFF002145))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiodataScreen(modifier: Modifier = Modifier) {
    val stateHolder = rememberSaveable(saver = BiodataStateSaver) { BiodataState() }
    val state = stateHolder.uiState

    // Logika untuk Date Picker Dialog
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val (year, month, day) = try {
        val parts = state.tanggalLahir.split("/")
        Triple(parts[2].toInt(), parts[1].toInt() - 1, parts[0].toInt())
    } catch (e: Exception) {
        Triple(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            stateHolder.onTanggalLahirChange("$d/${m + 1}/$y")
        },
        year, month, day
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        BiodataHeader(namaLengkap = state.namaLengkap)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            if (state.isEditMode) {
                BiodataEditContent(
                    stateHolder = stateHolder,
                    onDatePickerClick = { datePickerDialog.show() }
                )
            } else {
                BiodataViewContent(
                    state = state,
                    onEditClick = stateHolder::onEditModeToggle
                )
            }
        }
    }
}

@Composable
private fun BiodataHeader(namaLengkap: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                darkBlueGradient,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Biodata Diri", color = Color.White, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(16.dp))

        Box(modifier = Modifier.size(120.dp)) {
            Image(
                painter = painterResource(id = R.drawable.foto_saya),
                contentDescription = "Foto Profil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.White, CircleShape)
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Ganti Foto",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(darkBlueBase)
                    .border(2.dp, Color.White, CircleShape)
                    .padding(6.dp)
                    .clickable { /* TODO: Aksi ganti foto */ }
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(namaLengkap, color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("Software Engineer", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun BiodataViewContent(state: BiodataUiState, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(bottom = 8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 4.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Informasi Pribadi", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                TextButton(onClick = onEditClick) {
                    Text("Edit", color = darkBlueBase)
                }
            }
            Spacer(Modifier.height(8.dp))

            ProfileInfoItem(icon = Icons.Default.Badge, label = "Nama Lengkap", value = state.namaLengkap)
            ProfileInfoItem(icon = Icons.Default.Call, label = "Nomor Telepon", value = state.telepon)
            ProfileInfoItem(icon = Icons.Default.Email, label = "Email", value = state.email)
            ProfileInfoItem(icon = Icons.Default.Home, label = "Alamat", value = state.alamat)
            ProfileInfoItem(icon = Icons.Default.Link, label = "Profil LinkedIn", value = state.linkedin)
            ProfileInfoItem(icon = Icons.Default.Link, label = "Profil GitHub", value = state.github)
            ProfileInfoItem(icon = Icons.Default.CalendarMonth, label = "Tanggal Lahir", value = state.tanggalLahir)
            ProfileInfoItem(icon = Icons.Default.Wc, label = "Jenis Kelamin", value = state.jenisKelamin)
            ProfileInfoItem(icon = Icons.Default.BusinessCenter, label = "Pekerjaan", value = state.pekerjaan, isLastItem = true)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BiodataEditContent(stateHolder: BiodataState, onDatePickerClick: () -> Unit) {
    val state = stateHolder.uiState
    val customTextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = darkBlueBase,
        unfocusedIndicatorColor = Color.Gray,
        cursorColor = Color.Black,
        focusedLabelColor = darkBlueBase,
        unfocusedLabelColor = Color.Gray
    )

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Informasi Pribadi", style = MaterialTheme.typography.titleMedium, color = Color.Black)
            TextButton(onClick = stateHolder::onEditModeToggle) {
                Text("Batal", color = darkBlueBase)
            }
        }

        OutlinedTextField(value = state.namaLengkap, colors = customTextFieldColors, onValueChange = stateHolder::onNamaLengkapChange, label = { Text("Nama Lengkap") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = state.telepon, colors = customTextFieldColors, onValueChange = stateHolder::onTeleponChange, label = { Text("Nomor Telepon") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = state.email, colors = customTextFieldColors, onValueChange = stateHolder::onEmailChange, label = { Text("Email") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = state.alamat, colors = customTextFieldColors, onValueChange = stateHolder::onAlamatChange, label = { Text("Alamat") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
        OutlinedTextField(value = state.linkedin, colors = customTextFieldColors, onValueChange = stateHolder::onLinkedInChange, label = { Text("Profil LinkedIn") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = state.github, colors = customTextFieldColors, onValueChange = stateHolder::onGithubChange, label = { Text("Profil GitHub") }, modifier = Modifier.fillMaxWidth())

        OutlinedTextField(
            value = state.tanggalLahir, colors = customTextFieldColors, onValueChange = {},
            label = { Text("Tanggal Lahir") }, readOnly = true,
            trailingIcon = { Icon(Icons.Default.CalendarToday, "Pilih Tanggal", Modifier.clickable(onClick = onDatePickerClick)) },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Jenis Kelamin", style = MaterialTheme.typography.titleSmall, color = Color.Black)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            listOf("Laki-laki", "Perempuan").forEach { gender ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { stateHolder.onJenisKelaminSelect(gender) }) {
                    RadioButton(
                        selected = state.jenisKelamin == gender,
                        onClick = { stateHolder.onJenisKelaminSelect(gender) },
                        colors = RadioButtonDefaults.colors(selectedColor = darkBlueBase)
                    )
                    Text(text = gender, color = Color.Black)
                }
            }
        }

        ExposedDropdownMenuBox(expanded = state.pekerjaanDropdownExpanded, onExpandedChange = { stateHolder.onPekerjaanDropdownToggle() }) {
            OutlinedTextField(
                value = state.pekerjaan, colors = customTextFieldColors, onValueChange = {}, label = { Text("Pekerjaan") }, readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.pekerjaanDropdownExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = state.pekerjaanDropdownExpanded,
                onDismissRequest = stateHolder::onPekerjaanDropdownDismiss,
                modifier = Modifier.background(Color.White),
            ) {
                stateHolder.pekerjaanOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, color = Color.Black) },
                        onClick = { stateHolder.onPekerjaanSelect(option) }
                    )
                }
            }
        }

        Button(
            onClick = stateHolder::onEditModeToggle,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = darkBlueBase)
        ) {
            Text("Simpan Perubahan", modifier = Modifier.padding(vertical = 8.dp), style = MaterialTheme.typography.titleMedium, color = Color.White)
        }
    }
}


@Composable
private fun ProfileInfoItem(icon: ImageVector, label: String, value: String, isLastItem: Boolean = false) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(24.dp), tint = darkBlueBase)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(label, color = Color.Gray, style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(2.dp))
                Text(value.ifEmpty { "-" }, color = Color.Black, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            }
        }
        if (!isLastItem) {
            Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(start = 56.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BiodataScreenPreview() {
    ProyekUTSTheme {
        BiodataScreen()
    }
}
