package view;

import model.*;
import controller.*;
import java.util.Scanner;
import java.time.LocalDate;

public class MainMenuConsole {
    private BarangMasukKeluarService transaksiService = new BarangMasukKeluarService();
    private BarangService barangService = new BarangService(transaksiService);
    private RiwayatPeminjamanService riwayatService = new RiwayatPeminjamanService();
    private PeminjamanService peminjamanService = new PeminjamanService(barangService, riwayatService);
    private LaporanService laporanService = new LaporanService();
    
    private Scanner scanner = new Scanner(System.in);

    public void jalankan() {
        barangService.create(new barangElektronik("Laptop ASUS", "LP01", 10, "BAIK", "KOMPUTER", "ASUS", "Hitam"));

        while (true) {
            // Menampilkan antarmuka menu ke layar Terminal
            System.out.println("\n=== SISTEM INVENTARIS LAB KOMPUTER ===");
            System.out.println("1. Tambah Barang Baru");
            System.out.println("2. Transaksi Stok Manual (Masuk/Keluar)");
            System.out.println("3. Pinjam Barang");
            System.out.println("4. Kembalikan Barang");
            System.out.println("5. Cetak Laporan (Barang & Riwayat)");
            System.out.println("6. Keluar");
            System.out.print("Pilih Menu (1-6): ");

            // Mengamankan input navigasi
            int pilihan = 0;
            try {
                pilihan = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ROBUSTNESS] Error: Input wajib berupa angka!");
                continue; // Mencegah crash dan mengembalikan user ke menu awal
            }

            // Eksekusi Pilihan
            if (pilihan == 1) menuTambahBarang();
            else if (pilihan == 2) menuTransaksiStok();
            else if (pilihan == 3) menuPinjamBarang();
            else if (pilihan == 4) menuKembalikanBarang();
            else if (pilihan == 5) {
                laporanService.generateLaporanBarang(barangService.readAll());
                laporanService.generateLaporanPeminjaman(riwayatService.readAll());
            } 
            else if (pilihan == 6) {
                System.out.println("Keluar dari sistem...");
                break;
            } else {
                System.out.println("[VALIDASI] Pilihan tidak valid.");
            }
        }
    }

    private void menuTambahBarang() {
        // Mengambil input teks
        System.out.print("Nama Alat: "); String nama = scanner.nextLine();
        System.out.print("Kode Barang: "); String kode = scanner.nextLine();
        
        // Validasi string kosong
        if (nama.trim().isEmpty() || kode.trim().isEmpty()) {
            System.out.println("[VALIDASI] Nama dan Kode wajib diisi!"); return;
        }

        // Blok pengamanan utama 
        try {
            System.out.print("Jumlah Stok Awal: ");
            int jumlah = Integer.parseInt(scanner.nextLine());
            
            barang barangBaru = new barangElektronik(nama, kode, jumlah, "BAIK", "KOMPUTER", "-", "-");
            barangService.create(barangBaru);
            System.out.println("Sukses menambah barang!");
            
        } catch (NumberFormatException e) {
            // Tangkapan jika user mengetik huruf di kolom jumlah
            System.out.println("[ROBUSTNESS] Error: Stok harus angka!");
        } catch (IllegalArgumentException e) {
            // Tangkapan jika kode barang sudah ada (duplikat) dari logic Role 2
            System.out.println("[ROBUSTNESS] " + e.getMessage());
        }
    }

    private void menuTransaksiStok() {
        System.out.print("Kode Barang: "); String kode = scanner.nextLine();
        System.out.print("Jenis Transaksi (1. Masuk, 2. Keluar): ");
        
        try {
            int jenis = Integer.parseInt(scanner.nextLine());
            System.out.print("Jumlah Barang: ");
            int jumlah = Integer.parseInt(scanner.nextLine());
            
            if (jenis == 1) {
                barangService.tambahStok(kode, jumlah, "Restok Manual", "Admin");
                System.out.println("Sukses menambah stok!");
            } else if (jenis == 2) {
                barangService.kurangiStok(kode, jumlah, "Penggunaan Lab", "Admin");
                System.out.println("Sukses mengurangi stok!");
            } else {
                System.out.println("[VALIDASI] Pilihan transaksi salah.");
            }
        } catch (NumberFormatException e) {
            System.out.println("[ROBUSTNESS] Input harus angka!");
        } catch (IllegalArgumentException e) {
            System.out.println("[ROBUSTNESS] " + e.getMessage());
        }
    }

    private void menuPinjamBarang() {
        System.out.print("Nama Peminjam: "); String nama = scanner.nextLine();
        System.out.print("NIM (Angka): "); String nimStr = scanner.nextLine();
        System.out.print("Kode Barang: "); String kode = scanner.nextLine();
        
        try {
            int nim = Integer.parseInt(nimStr);
            System.out.print("Jumlah Pinjam: ");
            int jumlah = Integer.parseInt(scanner.nextLine());

            peminjamanService.pinjamBarang(nama, nim, kode, jumlah, LocalDate.now(), LocalDate.now().plusDays(7), "BAIK");
            System.out.println("Sukses meminjam barang!");
        } catch (NumberFormatException e) {
            System.out.println("[ROBUSTNESS] NIM dan Jumlah harus angka!");
        } catch (IllegalArgumentException e) {
            System.out.println("[ROBUSTNESS] Ditolak Sistem: " + e.getMessage());
        }
    }

    private void menuKembalikanBarang() {
        System.out.print("Masukkan ID Riwayat Peminjaman: "); 
        String idRiwayat = scanner.nextLine();
        
        try {
            System.out.print("Jumlah Dikembalikan: ");
            int jumlah = Integer.parseInt(scanner.nextLine());
            
            peminjamanService.kembalikanBarang(idRiwayat, jumlah, LocalDate.now(), "BAIK");
            System.out.println("Sukses! Barang telah dikembalikan dan stok diperbarui.");
        } catch (NumberFormatException e) {
            System.out.println("[ROBUSTNESS] Jumlah harus angka!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("[ROBUSTNESS] Gagal: " + e.getMessage());
        }
    }
}