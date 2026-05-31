package view;

import controller.InventoryController;
import controller.PeminjamanController;
import controller.LaporanController;
import model.*;
import exception.ValidationException;

import java.util.Scanner;

public class MainConsole {
    private InventoryController inventoryController;
    private PeminjamanController peminjamanController;
    private LaporanController laporanController;
    private Scanner scanner;

    public MainConsole() {
        inventoryController = new InventoryController();
        peminjamanController = new PeminjamanController(inventoryController);
        laporanController = new LaporanController();
        scanner = new Scanner(System.in); // Diperbaiki agar tidak dobel deklarasi
        seedData();
    }

    public void start() {
        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n========================================");
            System.out.println("          SISTEM INVENTARIS LAB         ");
            System.out.println("========================================");
            System.out.println("1. Lihat Daftar Barang");
            System.out.println("2. Tambah Barang Baru");
            System.out.println("3. Lakukan Peminjaman Barang");
            System.out.println("4. Pengembalian Barang");
            System.out.println("5. Cetak Laporan Lengkap (Inventaris & Transaksi)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1":
                    tampilkanBarang();
                    break;
                case "2":
                    inputBarangBaru();
                    break;
                case "3":
                    inputPeminjaman();
                    break;
                case "4":
                    inputPengembalian();
                    break;
                case "5":
                    cetakLaporanLengkap(); // Memanggil method baru
                    break;
                case "0":
                    berjalan = false;
                    System.out.println("Terima kasih! Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private void tampilkanBarang() {
        System.out.println("\n--- DAFTAR BARANG ---");
        if (inventoryController.getAllBarang().isEmpty()) {
            System.out.println("Belum ada barang di inventaris.");
        } else {
            for (Barang b : inventoryController.getAllBarang()) {
                System.out.println(b);
            }
        }
    }

    private void inputBarangBaru() {
        System.out.println("\n--- TAMBAH BARANG BARU ---");
        System.out.print("ID Barang: ");
        String id = scanner.nextLine();
        System.out.print("Nama Barang: ");
        String nama = scanner.nextLine();
        System.out.print("Jumlah: ");
        int jumlah = Integer.parseInt(scanner.nextLine());
        System.out.print("Lokasi (misal: Rak A1): ");
        String lokasi = scanner.nextLine();
        
        System.out.println("Kategori Barang:");
        System.out.println("1. Elektronik");
        System.out.println("2. Non-Elektronik");
        System.out.print("Pilih kategori (1/2): ");
        String kategori = scanner.nextLine();

        try {
            if (kategori.equals("1")) {
                System.out.print("Tegangan (Volt): ");
                int tegangan = Integer.parseInt(scanner.nextLine());
                inventoryController.tambahBarang(new BarangElektronik(id, nama, jumlah, lokasi, tegangan));
                System.out.println("Barang Elektronik berhasil ditambahkan!");
            } else if (kategori.equals("2")) {
                System.out.print("Bahan (misal: Kaca, Plastik): ");
                String bahan = scanner.nextLine();
                inventoryController.tambahBarang(new BarangNonElektronik(id, nama, jumlah, lokasi, bahan));
                System.out.println("Barang Non-Elektronik berhasil ditambahkan!");
            } else {
                System.out.println("Kategori tidak valid. Gagal menambah barang.");
            }
        } catch (ValidationException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void inputPeminjaman() {
        System.out.println("\n--- PEMINJAMAN BARANG ---");
        System.out.print("ID Barang yang ingin dipinjam: ");
        String idBarang = scanner.nextLine();
        System.out.print("Nama Peminjam: ");
        String namaPeminjam = scanner.nextLine();
        System.out.print("NIM: ");
        String nim = scanner.nextLine();
        System.out.print("Jumlah Pinjam: ");
        
        try {
            int jumlah = Integer.parseInt(scanner.nextLine());
            peminjamanController.pinjamBarang(idBarang, namaPeminjam, nim, jumlah);
            System.out.println("Peminjaman berhasil dicatat!");
        } catch (ValidationException | NumberFormatException e) {
            System.out.println("Gagal meminjam: " + e.getMessage());
        }
    }

    private void inputPengembalian() {
        System.out.println("\n--- PENGEMBALIAN BARANG ---");
        System.out.print("ID Peminjaman (misal: PJM001): ");
        String idPeminjaman = scanner.nextLine();
        System.out.print("Jumlah yang dikembalikan: ");
        
        try {
            int jumlahKembali = Integer.parseInt(scanner.nextLine());
            peminjamanController.kembalikanBarang(idPeminjaman, jumlahKembali);
            System.out.println("Pengembalian berhasil dicatat!");
        } catch (ValidationException | NumberFormatException e) {
            System.out.println("Gagal mengembalikan: " + e.getMessage());
        }
    }

    private void cetakLaporanLengkap() {
        System.out.println("\n========================================");
        System.out.println("          LAPORAN INVENTARIS            ");
        System.out.println("========================================");
        // Memanggil fungsi cetak inventaris dari controller bawaan
        try {
            laporanController.laporanInventaris(inventoryController);
        } catch (Exception e) {
            // Backup jika metode LaporanController tidak sesuai
            tampilkanBarang(); 
        }

        System.out.println("\n========================================");
        System.out.println("    LAPORAN PEMINJAMAN & PENGEMBALIAN   ");
        System.out.println("========================================");
        
        if (peminjamanController.getAllPeminjaman().isEmpty()) {
            System.out.println("Belum ada riwayat transaksi peminjaman.");
        } else {
            for (Peminjaman p : peminjamanController.getAllPeminjaman()) {
                System.out.println("ID Pinjam    : " + p.getIdPeminjaman());
                System.out.println("ID Barang    : " + p.getIdBarang());
                System.out.println("Peminjam     : " + p.getNamaPeminjam() + " (NIM: " + p.getNim() + ")");
                System.out.println("Jml Pinjam   : " + p.getJumlahPinjam());
                System.out.println("Jml Kembali  : " + p.getJumlahKembali());
                System.out.println("Sisa Pinjam  : " + p.getSisaBelumKembali());
                
                // Logika status otomatis berdasarkan sisa barang yang belum kembali
                if (p.getSisaBelumKembali() == 0) {
                    System.out.println("Status       : [TELAH DIKEMBALIKAN SEPENUHNYA]");
                } else {
                    System.out.println("Status       : [MASIH DIPINJAM] - " + p.getStatus());
                }
                System.out.println("----------------------------------------");
            }
        }
    }

    private void seedData() {
        try {
            inventoryController.tambahBarang(new BarangElektronik("EL001", "Multimeter", 5, "Rak A1", 220));
            inventoryController.tambahBarang(new BarangNonElektronik("LAB002", "Tabung Reaksi", 20, "Lemari B2", "Kaca"));
        } catch (ValidationException e) {
            // Abaikan error seed data awal
        }
    }

    public static void main(String[] args) {
        MainConsole app = new MainConsole();
        app.start();
    }
}