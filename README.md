#  Dokumentasi Sistem Inventaris Barang Laboratorium Komputer - Update Versi 2.0

##  Ringkasan Update Versi 2.0
Aplikasi Sistem Inventaris Barang Laboratorium Komputer kini telah merilis **Update Versi 2.0** yang membawa pembaruan arsitektur sistem berbasis *Object-Oriented Programming* (OOP) secara utuh, interaktif, dan aman. Pembaruan utama pada versi ini berfokus pada fleksibilitas operasional dengan menyediakan dua pilihan antarmuka pengguna (*Dual-Interface*), yaitu **Mode Console (CLI)** untuk manajemen cepat melalui terminal dan **Mode Visual (GUI)** berbasis *Java Swing* yang modern untuk kenyamanan operasional[cite: 1]. Seluruh komponen aplikasi diintegrasikan menggunakan pola arsitektur *Model-View-Controller* (MVC) guna memisahkan logika bisnis dari antarmuka visual[cite: 1].

Selain perombakan visual, Versi 2.0 ini memperkenalkan fitur **"Kategori Premium/Prioritas"** untuk memisahkan manajemen barang biasa dengan barang-barang lab khusus yang bernilai tinggi atau rentan[cite: 1]. Pembaruan ini didukung penuh oleh mekanisme ketahanan sistem (*robustness*) yang ketat menggunakan kombinasi blok *try-catch* dan *Custom Exception* baru guna memvalidasi data masukan secara *real-time*[cite: 1]. Aplikasi kini juga memanfaatkan *Java Collections* untuk melakukan penyaringan (*filtering*) data khusus barang premium secara instan tanpa mengganggu stabilitas program[cite: 1].

---

##  Rincian Fitur Baru (Update Versi 2.0)

1. **Entitas Kategori Premium / Prioritas (OOP Inheritance & Polymorphism)**
   * Mendukung pencatatan objek inventaris khusus seperti barang pecah belah (*Fragile*), perangkat bernilai tinggi (*High Value*), atau kebutuhan darurat (*Urgent*)[cite: 1].
   * Objek ini memiliki atribut unik berupa tingkat prioritas dan nilai biaya asuransi kerusakan[cite: 1].
   * Memanfaatkan *Polymorphism Override* pada method `getKategori()` dan `toString()` untuk membedakan output informasi dari barang reguler[cite: 1].

2. **Sistem Penyaringan Koleksi Data (Collections Filtering)**
   * Menambahkan fitur penyaringan data berbasis *Collections* (`ArrayList`) untuk memisahkan data[cite: 1].
   * Pengguna dapat memanggil fungsi ini melalui CLI maupun GUI untuk menampilkan daftar barang berkategori premium saja dari memori penyimpanan secara instan[cite: 1].

3. **Validasi Input & Ketahanan Sistem (Robustness / Exception Handling)**
   * Proteksi penuh dari risiko program *crash* atau *force close* akibat kelalaian input data kosong oleh pengguna melalui *Custom Exception* buatan sendiri[cite: 1].

---

##  Rincian Perubahan File & Folder Kode Sumber

Berikut adalah rincian penambahan file baru dan modifikasi baris kode yang terjadi pada rilis Versi 2.0 ini:

### 1. File Baru yang Ditambahkan
* **`src/main/java/model/BarangPremium.java`**
  * Subclass baru yang mewarisi class `Barang`[cite: 1]. Berisi konstruktor khusus, enkapsulasi atribut unik (tingkat prioritas dan asuransi), serta overriding fungsi cetak informasi[cite: 1].
* **`src/main/java/exception/InputKosongException.java`**
  * Class penampung *Custom Exception* yang mengekstensi class induk `Exception`[cite: 1]. Digunakan khusus untuk menjebak (*catch*) input data krusial yang dikosongkan oleh pengguna[cite: 1].

### 2. File yang Dimodifikasi / Diperbarui
* **`src/main/java/controller/InventoryController.java`**
  * Penambahan method logika baru `getFilterBarangPremium()` yang menerapkan operasi perulangan dan seleksi objek (`instanceof`) untuk menyaring data dari `ArrayList`[cite: 1].
* **`src/main/java/view/MainConsole.java`**
  * Penambahan opsi menu ke-6 ("Filter: Tampilkan Barang Premium Saja") pada antarmuka terminal[cite: 1].
  * Penerapan penanganan eror (*exception handling*) berbasis struktur *try-catch-catch* berganda untuk memvalidasi form pembuatan barang premium baru[cite: 1].
* **`src/main/java/view/MainGUI.java`**
  * Penambahan tombol "Filter: Premium Saja" pada tabel utama kelola barang[cite: 1].
  * Pembaruan komponen *combobox* kategori dan pembuatan panel input dinamis (jika opsi Premium dipilih, form otomatis memunculkan kolom tingkat prioritas dan biaya asuransi secara visual)[cite: 1].
  * Integrasi dialog pesan peringatan `JOptionPane` untuk menangkap galat `InputKosongException`[cite: 1].
* **`src/main/java/view/Main.java`**
  * Penyesuaian instruksi *entry point* program utama untuk memproses pemilihan peluncuran dual-mode interface (CLI atau GUI)
---

##  Cara Menjalankan Aplikasi
1. Buka folder project ini menggunakan **VS Code**.
2. Pastikan ekstensi *Java Extension Pack* sudah aktif.
3. Jalankan file `Main.java` yang berada di dalam package `view`.
4. Pilih mode aplikasi yang diinginkan pada terminal (`1` untuk Console, `2` untuk GUI).

## Teknologi yang Digunakan

- Java 8 atau lebih tinggi
- Java Swing (GUI)
- Model-View-Controller (MVC) pattern
- Penyimpanan data sementara dalam memori (ArrayList)

