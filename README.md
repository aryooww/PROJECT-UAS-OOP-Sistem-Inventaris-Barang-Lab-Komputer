## Update Versi 2.0 - Fitur Barang Rentan 

Fitur baru yang ditambahkan pada versi ini adalah dukungan untuk **Barang Rentan (Fragile)**.

### Penambahan:
1. **Subclass BarangRentan** - Mewarisi dari class `Barang` dengan atribut tambahan `tingkatKerapuhan` (1-10)
2. **Polimorfisme** - Override method `getKategori()` dan `toString()` untuk menampilkan informasi kerapuhan
3. **Sorting** - Barang rentan dapat diurutkan berdasarkan tingkat kerapuhan tertinggi (prioritas penanganan)
4. **Custom Exception** - `KerapuhanTidakValidException` untuk memvalidasi input tingkat kerapuhan (range 1-10)
5. **Menu Baru** - Opsi nomor 6 pada menu utama untuk melihat daftar barang rentan terurut

### Cara Menggunakan:
- Pilih menu 2 → pilih kategori 3 (Barang Rentan) → masukkan tingkat kerapuhan 1-10
- Pilih menu 6 untuk melihat daftar barang rentan yang sudah diurutkan dari yang paling rapuh

---
