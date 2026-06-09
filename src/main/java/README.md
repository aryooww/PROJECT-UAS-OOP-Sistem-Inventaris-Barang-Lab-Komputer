# Sistem Inventaris Barang Laboratorium Komputer

## 🚀 Update Versi 2.0 (Premium & Prioritas Update)
Aplikasi Sistem Inventaris Barang Laboratorium Komputer kini telah merilis **Update Versi 2.0** yang membawa transformasi arsitektur penuh menjadi aplikasi *Full-Stack* berbasis OOP yang andal, interaktif, dan aman. Pembaruan utama pada versi ini berfokus pada fleksibilitas operasional dengan menyediakan dua pilihan antarmuka pengguna, yaitu **Mode Console (CLI)** untuk manajemen cepat melalui terminal dan **Mode Visual (GUI)** berbasis *Java Swing* yang modern. Seluruh sistem ini dikelola secara mulus menggunakan pola arsitektur *Model-View-Controller* (MVC) untuk memisahkan logika data dari antarmuka visual.

Selain perombakan visual, Versi 2.0 ini memperkenalkan fitur **"Kategori Premium/Prioritas"** untuk mengakomodasi pengelolaan barang khusus seperti barang pecah belah (*Fragile*), perangkat bernilai tinggi, atau kebutuhan darurat (*Urgent*). Pembaruan ini didukung oleh mekanisme ketahanan sistem (*robustness*) yang ketat lewat implementasi blok *try-catch* menggunakan *Custom Exception* (`InputKosongException` dan `ValidationException`). Sistem kini secara cerdas mampu mendeteksi dan mengisolasi kesalahan input pengguna—seperti kolom prioritas yang kosong atau nilai asuransi yang tidak valid—serta memanfaatkan *Java Collections* untuk melakukan penyaringan (*filtering*) data khusus barang premium secara instan tanpa mengganggu stabilitas program.

---

## 🛠️ Fitur Utama Aplikasi
1. **Dual-Mode Interface:** Pengguna bebas memilih untuk menjalankan aplikasi dalam mode Terminal (CLI) atau Jendela Jframe (GUI) saat aplikasi pertama kali dijalankan.
2. **Kategori Inventaris Khusus (Premium/Prioritas):** Mendukung pengelolaan objek khusus dengan properti tingkat prioritas dan biaya asuransi kerusakan menggunakan prinsip *Inheritance* dan *Polymorphism*.
3. **Penyaringan Data Cepat (Collections Filter):** Fitur untuk menyaring dan menampilkan koleksi barang kategori premium saja dari memori penyimpanan secara *real-time*.
4. **Transaksi Peminjaman & Pengembalian:** Sistem pencatatan peminjaman barang yang otomatis memotong jumlah stok inventaris, serta fitur pengembalian berskala (bisa dikembalikan sebagian atau sekaligus lunas).
5. **Validasi Input Kuat (Exception Handling):** Perlindungan penuh terhadap data kosong atau salah ketik pada data krusial menggunakan objek pengecualian buatan sendiri agar aplikasi bebas dari *crash*.

---

## 📂 Struktur Package & Class (OOP)
Project ini dibangun dengan menerapkan prinsip *Clean Code* dan struktur berorientasi objek yang terbagi ke dalam package berikut:

* **`models`**
  * `Barang.java` (Abstract Class & Encapsulation)
  * `BarangElektronik.java` & `BarangNonElektronik.java` (Subclasses dasar)
  * `BarangPremium.java` (Subclass Baru untuk Fitur Prioritas)
  * `Peminjaman.java` (Model Transaksi)
* **`controller`**
  * `InventoryController.java` (Logika Pengelolaan Stok & Metode Filter Collections)
  * `PeminjamanController.java` (Logika Transaksi Peminjaman)
  * `LaporanController.java` (Logika Pemrosesan & Cetak Laporan)
* **`exceptions`**
  * `ValidationException.java` (Custom Exception untuk validasi umum)
  * `InputKosongException.java` (Custom Exception Baru untuk validasi form kosong)
* **`view`**
  * `Main.java` (Entry Point Utama Aplikasi)
  * `MainConsole.java` (Antarmuka CLI dengan Menu Filter Premium)
  * `MainGUI.java` (Antarmuka GUI dengan Tombol Filter dan Form Dinamis)

---

## 💻 Cara Menjalankan Aplikasi
1. Buka folder project ini menggunakan **VS Code**.
2. Pastikan ekstensi *Java Extension Pack* sudah aktif.
3. Jalankan file `Main.java` yang berada di dalam package `view`.
4. Pilih mode aplikasi yang diinginkan pada terminal (`1` untuk Console, `2` untuk GUI).