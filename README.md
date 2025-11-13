# Proyek UTS Android — Jetpack Compose (Material 3)

Aplikasi Android berbasis Kotlin dan Jetpack Compose (Material 3) yang menampilkan lima fitur utama: Biodata, Kontak, Kalkulator, Cuaca, dan Berita. Navigasi utama menggunakan Bottom Navigation. Tiap menu diimplementasikan dengan konsep Fragment yang memuat UI Compose (ComposeView/FragmentContainer).

## Tampilan Aplikasi

| Halaman    | Deskripsi (detail)                                                                                                                                                                                                                                            | Screenshot                                                                 |
| ---------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------- |
| Splash     | Menampilkan: judul aplikasi, foto Anda, NIM, nama.<br>Timer 5 detik dengan auto-navigasi ke Dashboard.<br>Tata letak center-align dengan warna merek dan tipografi konsisten.                                                                                 | <img src="public/screenshots/splash_screen.jpg" alt="Splash" width="300">  |
| Dashboard  | Bottom Navigation dengan 5 menu: Biodata, Kontak, Kalkulator, Cuaca, Berita.<br>AppBar menampilkan judul tab aktif.<br>Setiap menu adalah Fragment yang meng-host UI Compose.<br>State tiap tab dipertahankan saat berpindah.                                 | <img src="public/screenshots/dashboard.jpg" alt="Dashboard" width="300">   |
| Biodata    | Header profil: avatar lingkaran, nama lengkap, NIM.<br>Form input: Dropdown (contoh: Prodi/Jurusan), Radio Button (contoh: Jenis Kelamin), TextField (contoh: Alamat, Email, No. HP), DatePicker/Calendar (Tanggal Lahir).<br>Validasi ringan, non-persisten. | <img src="public/screenshots/view_mode.jpg" alt="Biodata" width="300">     |
| Kontak     | Daftar minimal 15 kontak statis (LazyColumn).<br>Item: avatar lingkaran, nama, nomor telepon.<br>Aksi ketuk: toast/placeholder; tombol panggil/pesan opsional.                                                                                                | <img src="public/screenshots/contact_list.jpg" alt="Kontak" width="300">   |
| Kalkulator | Display: ekspresi berjalan dan hasil terakhir.<br>Keypad: angka 0–9, titik; operator +, −, ×, ÷; fungsi x², √x; tombol =, C (clear), ⌫ (backspace).<br>Penanganan bagi 0 dan input tidak valid.                                                               | <img src="public/screenshots/calculator.jpg" alt="Kalkulator" width="300"> |
| Cuaca      | Kartu kondisi saat ini: suhu (°C), kelembapan (%), kecepatan angin, deskripsi kondisi.<br>Ikon/animasi cuaca (cerah/hujan/mendung).<br>Perkiraan statis singkat per jam/hari (opsional).                                                                      | <img src="public/screenshots/weather.jpg" alt="Cuaca" width="300">         |
| Berita     | List card berulang: thumbnail, kategori/tag, judul, ringkasan, waktu rilis.<br>Tap item menuju detail/placeholder (data statis).<br>Layout responsif dengan pemotongan teks multi-baris.                                                                      | <img src="public/screenshots/news_list.jpg" alt="Berita" width="300">      |

## Pengembang

**Nama:** Muhammad Iqbal Pasha Al Farabi  
**NIM**: 152023174

## Lisensi

Proyek ini dilisensikan di bawah MIT License. Lihat file [LICENSE](LICENSE) untuk detail.
