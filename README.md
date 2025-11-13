# Proyek UTS Android — Jetpack Compose (Material 3)

Aplikasi Android berbasis Kotlin dan Jetpack Compose (Material 3) yang menampilkan lima fitur utama: Biodata, Kontak, Kalkulator, Cuaca, dan Berita. Navigasi utama menggunakan Bottom Navigation. Tiap menu diimplementasikan dengan konsep Fragment yang memuat UI Compose (ComposeView/FragmentContainer).

## About

Aplikasi ini disusun sesuai ketentuan UTS:

- a) Splash Screen 5 detik menampilkan judul aplikasi, foto, NIM, dan nama.
- b) Dashboard dengan Bottom Navigation menuju: Biodata, Kontak, Kalkulator, Cuaca, Berita. Tiap menu adalah Fragment, dan layout sudah lengkap.
- c) Biodata: teks + foto profil; input dropdown, radio button, text field, dan date picker (calendar). Data tidak disimpan.
- d) Kontak: daftar 15 kontak statis (teks dan circle image/avatars).
- e) Kalkulator: operasi tambah, kurang, kali, bagi, kuadrat, akar kuadrat, serta tombol hapus/clear.
- f) Cuaca: data statis (suhu, kelembapan, kondisi, dsb.) dengan animasi/gambar cuaca.
- g) Berita: daftar berita statis dengan pola tampilan berulang (card/list).

## Fitur Halaman

- Splash Screen

  - Durasi 5 detik.
  - Menampilkan: Judul aplikasi, Foto, NIM, Nama.

- Dashboard

  - Bottom Navigation: Biodata | Kontak | Kalkulator | Cuaca | Berita.
  - Setiap tab adalah Fragment yang meng-host UI Compose.

- Biodata

  - Informasi profil (teks + foto profil).
  - Input: dropdown (mis. jurusan), radio button (mis. gender), text field (mis. alamat), date picker (tanggal lahir).
  - Validasi ringan, tanpa penyimpanan ke database.

- Kontak

  - Minimal 15 item kontak statis (nama, nomor).
  - Avatar berbentuk lingkaran; tata letak list modern.

- Kalkulator

  - Operasi: +, −, ×, ÷, x², √x.
  - Input numerik, riwayat/hasil singkat, tombol clear.

- Cuaca

  - Data statis: suhu, kelembapan, angin, kondisi.
  - Ilustrasi/animasi kondisi cuaca (cerah, hujan, mendung).

- Berita
  - Daftar kartu berita statis berulang (gambar kecil, judul, ringkasan, waktu).

## Screenshots

Letakkan file gambar di folder `public/screenshots/`. Tabel di bawah membatasi lebar gambar agar tidak terlalu besar (maks 320 px).

| Halaman    | Deskripsi singkat                                                            | Screenshot                                                                 |
| ---------- | ---------------------------------------------------------------------------- | -------------------------------------------------------------------------- |
| Splash     | Judul aplikasi, foto, NIM, nama; tampil 5 detik sebelum ke Dashboard.        | <img src="public/screenshots/splash_screen.jpg" alt="Splash" width="320">  |
| Dashboard  | Navigasi bawah ke Biodata, Kontak, Kalkulator, Cuaca, Berita.                | <img src="public/screenshots/dashboard.jpg" alt="Dashboard" width="320">   |
| Biodata    | Profil + input dropdown, radio, text field, date picker (tanpa penyimpanan). | <img src="public/screenshots/view_mode.jpg" alt="Biodata" width="320">     |
| Kontak     | Daftar minimal 15 kontak statis dengan avatar lingkaran.                     | <img src="public/screenshots/contact_list.jpg" alt="Kontak" width="320">   |
| Kalkulator | Operasi +, −, ×, ÷, x², √x dan tombol clear.                                 | <img src="public/screenshots/calculator.jpg" alt="Kalkulator" width="320"> |
| Cuaca      | Data statis suhu, kelembapan, angin; ilustrasi/animasi kondisi cuaca.        | <img src="public/screenshots/weather.jpg" alt="Cuaca" width="320">         |
| Berita     | List/card berita statis berulang: gambar kecil, judul, ringkasan, waktu.     | <img src="public/screenshots/news_list.jpg" alt="Berita" width="320">      |

Catatan:

- Jika nama file berbeda, ganti path pada kolom Screenshot.
- Disarankan kompresi gambar (< 500 KB) agar repo ringan.

## Struktur Proyek (ringkas)

- app/ — Kode sumber utama, konfigurasi modul, dan AndroidManifest.
  - app/build.gradle.kts
  - app/src/main/AndroidManifest.xml
- public/screenshots/ — Gambar untuk dokumentasi README.

## Build & Run (Windows)

- Build APK Debug:
  - .\gradlew.bat :app:assembleDebug
- Install ke emulator/perangkat:
  - .\gradlew.bat :app:installDebug
- Unit test (JVM):
  - .\gradlew.bat :app:testDebugUnitTest
- Instrumentation test (Android):
  - .\gradlew.bat :app:connectedDebugAndroidTest

## Lisensi

Proyek ini dilisensikan di bawah MIT License. Lihat file [LICENSE](LICENSE) untuk detail.
