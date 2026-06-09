# Sistem Inventaris Barang Laboratorium

Aplikasi desktop berbasis Java Swing untuk mengelola inventaris barang laboratorium, peminjaman barang, serta pelaporan. Proyek ini dikembangkan sebagai tugas perkuliahan dengan fokus pada penerapan **konsep OOP**, **Collections (ArrayList)**, **Exception Handling**, serta **Filter dan Sorting** pada data.

## Fitur Utama

### 1. Kelola Barang
- **Tambah Barang** dengan 3 jenis:
  - `BarangElektronik` (memiliki tegangan)
  - `BarangNonElektronik` (memiliki bahan)
  - `BarangFragile` (subclass baru, memiliki bahan)
- **Update** data barang (nama, jumlah, lokasi)
- **Hapus** barang
- **Cari** barang berdasarkan ID atau nama
- **Filter** data berdasarkan kategori: *Semua, Elektronik, Non-Elektronik, Fragile*
- **Sorting** data berdasarkan:
  - Nama (A-Z)
  - Jumlah (dari terkecil ke terbesar)

### 2. Kelola Peminjaman
- **Pinjam barang** (mengurangi stok otomatis)
- **Kembalikan barang** (menambah stok otomatis)
- Menampilkan riwayat peminjaman dengan status (Belum Selesai / Selesai)

### 3. Laporan
- **Laporan Inventaris** (seluruh barang yang terdaftar)
- **Laporan Semua Peminjaman** (detail peminjaman)

### 4. Validasi Input & Exception Handling
- Custom exception **`InputTidakValidException`** untuk validasi input pengguna (field kosong, angka negatif/positif, format salah).
- Exception `ValidationException` untuk logika bisnis (ID duplikat, stok habis, dll).
- Semua exception ditangani dengan `try-catch` sehingga aplikasi **tidak crash** saat terjadi kesalahan input.

## Teknologi yang Digunakan
- Java SE (Swing untuk GUI)
- Java Collections Framework (ArrayList)
- OOP: Pewarisan, Polimorfisme, Enkapsulasi
- Custom Exception

## Struktur Proyek