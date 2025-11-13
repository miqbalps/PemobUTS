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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.proyekuts.R
import java.util.Calendar

val BiodataStateSaver = Saver<BiodataState, BiodataUiState>(
    save = { it.uiState },
    restore = { BiodataState().apply { uiState = it } }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiodataScreen(modifier: Modifier = Modifier) {
    val stateHolder = rememberSaveable(saver = BiodataStateSaver) { BiodataState() }
    val state = stateHolder.uiState

    // --- Logika untuk Date Picker Dialog ---
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

    // --- Warna Kustom ---
    val darkBlueBase = Color(0xFF013674)
    val darkBlueBg = Color(0xFF013773)
    val darkBlueGradient = Brush.verticalGradient(
        colors = listOf(darkBlueBg, Color(0xFF002145))
    )

    // Salin tema saat ini dan ganti warna primernya
    val customColorScheme = MaterialTheme.colorScheme.copy(
        primary = darkBlueBase
    )

    val customTextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = darkBlueBase,
        unfocusedIndicatorColor = Color.Gray,
        cursorColor = Color.Black
    )

    // --- UI Layout ---
    Column(
        modifier = modifier.fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        // --- Bagian Header (Gradasi Dark Blue) ---
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
            Text(
                "Biodata Diri",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier.size(120.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.foto_saya), // Pastikan ini adalah drawable yang benar
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
                        .background(darkBlueBase) // Menggunakan warna biru dasar
                        .border(2.dp, Color.White, CircleShape)
                        .padding(6.dp)
                        .clickable { /* Aksi ganti foto */ }
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = state.namaLengkap,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Ganti Foto",
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))
        }

        // --- Bagian Form (Putih dengan Aksen Biru) ---
        // Bungkus dengan MaterialTheme baru untuk mengganti warna aksen
        MaterialTheme(colorScheme = customColorScheme) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(Modifier.height(4.dp))
                Text(
                    "Informasi Pribadi",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black
                )
                Spacer(Modifier.height(4.dp))

                OutlinedTextField(value = state.namaLengkap, colors = customTextFieldColors, onValueChange = stateHolder::onNamaLengkapChange, label = { Text("Nama Lengkap", color = Color.Black) }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = state.telepon, colors = customTextFieldColors, onValueChange = stateHolder::onTeleponChange, label = { Text("Nomor Telepon", color = Color.Black) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = state.email, colors = customTextFieldColors, onValueChange = stateHolder::onEmailChange, label = { Text("Email", color = Color.Black) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = state.alamat, colors = customTextFieldColors, onValueChange = stateHolder::onAlamatChange, label = { Text("Alamat", color = Color.Black) }, modifier = Modifier.fillMaxWidth(), minLines = 3)
                OutlinedTextField(value = state.linkedin, colors = customTextFieldColors, onValueChange = stateHolder::onLinkedInChange, label = { Text("Profil LinkedIn", color = Color.Black) }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = state.github, colors = customTextFieldColors, onValueChange = stateHolder::onGithubChange, label = { Text("Profil GitHub", color = Color.Black) }, modifier = Modifier.fillMaxWidth())

                OutlinedTextField(
                    value = state.tanggalLahir,
                    colors = customTextFieldColors,
                    onValueChange = {},
                    label = { Text("Tanggal Lahir", color = Color.Black) },
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Default.CalendarToday, "Pilih Tanggal", Modifier.clickable { datePickerDialog.show() })
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Jenis Kelamin",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    listOf("Laki-laki", "Perempuan").forEach { gender ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { stateHolder.onJenisKelaminSelect(gender) }
                        ) {
                            RadioButton(
                                selected = state.jenisKelamin == gender,
                                onClick = { stateHolder.onJenisKelaminSelect(gender) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = darkBlueBase,
                                    unselectedColor = Color.Gray
                                )
                            )
                            Text(text = gender, color = Color.Black)
                        }
                    }
                }


                ExposedDropdownMenuBox(
                    expanded = state.pekerjaanDropdownExpanded,
                    onExpandedChange = { stateHolder.onPekerjaanDropdownToggle() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = state.pekerjaan,
                        colors = customTextFieldColors,
                        onValueChange = {},
                        label = { Text("Pekerjaan", color = Color.Black) },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.pekerjaanDropdownExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = state.pekerjaanDropdownExpanded,
                        onDismissRequest = stateHolder::onPekerjaanDropdownDismiss
                    ) {
                        stateHolder.pekerjaanOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, color = Color.Black) },
                                onClick = { stateHolder.onPekerjaanSelect(option) }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Tombol ini akan otomatis menggunakan warna primer dari customColorScheme
                Button(
                    onClick = { /* Aksi simpan data */ },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Simpan",
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}