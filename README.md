# Sistem Manajemen Inventaris dan Peminjaman

Aplikasi desktop berbasis Java Swing untuk mengelola data barang, transaksi stok (masuk/keluar), peminjaman barang, serta pembuatan laporan.

## Fitur Utama

- **Manajemen Barang**  
  - Tambah, edit, hapus barang  
  - Dua jenis barang: Elektronik (merk, warna) dan Non-Elektronik (bahan, warna)  
  - Setiap penambahan/penghapusan stok otomatis mencatat transaksi

- **Transaksi Stok Manual**  
  - Tambah stok (barang masuk)  
  - Kurangi stok (barang keluar)  
  - Riwayat transaksi lengkap dengan petugas, alasan, dan tanggal

- **Peminjaman & Pengembalian**  
  - Peminjaman barang dengan pencatatan kondisi awal  
  - Pengembalian sebagian/seluruh jumlah  
  - Status peminjaman ("BELUM DIKEMBALIKAN" / "SELESAI")  
  - Transaksi stok otomatis saat peminjaman (keluar) dan pengembalian (masuk)

- **Laporan**  
  - Cetak laporan data barang  
  - Cetak laporan riwayat peminjaman dan pengembalian

## Teknologi yang Digunakan

- Java 8 atau lebih tinggi
- Swing (GUI)
- Model-View-Controller (MVC) pattern
- Penyimpanan data sementara dalam memori (ArrayList)

