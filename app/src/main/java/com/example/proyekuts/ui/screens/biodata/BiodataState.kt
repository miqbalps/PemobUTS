package com.example.proyekuts.ui.screens.biodata

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BiodataUiState(
    val namaLengkap: String = "Muhammad Iqbal Pasha",
    val telepon: String = "",
    val email: String = "",
    val alamat: String = "",
    val linkedin: String = "linkedin.com/in/iqbalpasha",
    val github: String = "github.com/miqbalps",
    val tanggalLahir: String = "",
    val jenisKelamin: String = "",
    val pekerjaan: String = "",
    val pekerjaanDropdownExpanded: Boolean = false,
    var isEditMode: Boolean = false
) : Parcelable

class BiodataState {
    var uiState by mutableStateOf(BiodataUiState())

    val pekerjaanOptions = listOf("Pelajar", "Karyawan Swasta", "Wirausaha", "Lainnya")

    // Event handlers
    fun onEditModeToggle() {
        uiState = uiState.copy(isEditMode = !uiState.isEditMode)
    }

    fun onNamaLengkapChange(newValue: String) {
        uiState = uiState.copy(namaLengkap = newValue)
    }

    fun onTeleponChange(newValue: String) {
        uiState = uiState.copy(telepon = newValue)
    }

    fun onEmailChange(newValue: String) {
        uiState = uiState.copy(email = newValue)
    }

    fun onAlamatChange(newValue: String) {
        uiState = uiState.copy(alamat = newValue)
    }

    fun onLinkedInChange(newValue: String) {
        uiState = uiState.copy(linkedin = newValue)
    }

    fun onGithubChange(newValue: String) {
        uiState = uiState.copy(github = newValue)
    }

    fun onTanggalLahirChange(newValue: String) {
        uiState = uiState.copy(tanggalLahir = newValue)
    }

    fun onJenisKelaminSelect(jenisKelamin: String) {
        uiState = uiState.copy(jenisKelamin = jenisKelamin)
    }

    fun onPekerjaanSelect(pekerjaan: String) {
        uiState = uiState.copy(pekerjaan = pekerjaan, pekerjaanDropdownExpanded = false)
    }

    fun onPekerjaanDropdownDismiss() {
        uiState = uiState.copy(pekerjaanDropdownExpanded = false)
    }

    fun onPekerjaanDropdownToggle() {
        uiState = uiState.copy(pekerjaanDropdownExpanded = !uiState.pekerjaanDropdownExpanded)
    }
}