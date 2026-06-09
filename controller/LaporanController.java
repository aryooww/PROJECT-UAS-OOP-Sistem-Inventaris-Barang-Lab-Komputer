package controller;

import java.util.List;

import model.Barang;
import model.Peminjaman;

public class LaporanController {
    public void laporanInventaris(List<Barang> daftarBarang, List<Peminjaman> daftarPeminjaman) {
        System.out.println("\n=== LAPORAN INVENTARIS BARANG ===");
        if (daftarBarang == null || daftarBarang.isEmpty()) {
            System.out.println("Belum ada data barang.");
            return;
        }

        System.out.printf("%-10s %-20s %-10s %-10s %-15s\n", "ID", "Nama", "Jumlah", "Lokasi", "Kategori");
        System.out.println("-------------------------------------------------------------------");
        for (Barang b : daftarBarang) {
            System.out.printf("%-10s %-20s %-10d %-10s %-15s\n",
                    b.getId(), b.getNama(), b.getJumlah(), b.getLokasi(), b.getKategori());
        }
    }

    public void laporanPeminjaman(List<Peminjaman> daftarPeminjaman, boolean hanyaAktif) {
        if (hanyaAktif) {
            System.out.println("\n=== LAPORAN PEMINJAMAN AKTIF ===");
            if (daftarPeminjaman == null || daftarPeminjaman.isEmpty()) {
                System.out.println("Tidak ada data peminjaman.");
                return;
            }
            List<Peminjaman> aktif = daftarPeminjaman.stream()
                    .filter(p -> "DIPINJAM".equals(p.getStatus()))
                    .toList();
            if (aktif.isEmpty()) {
                System.out.println("Tidak ada peminjaman aktif.");
            } else {
                aktif.forEach(System.out::println);
            }
        } else {
            System.out.println("\n=== LAPORAN SEMUA PEMINJAMAN ===");
            if (daftarPeminjaman == null || daftarPeminjaman.isEmpty()) {
                System.out.println("Belum ada riwayat peminjaman.");
            } else {
                daftarPeminjaman.forEach(System.out::println);
            }
        }
    }

    public void laporanInventaris(InventoryController invController) {
        if (invController == null) {
            System.out.println("Error: InventoryController tidak tersedia.");
            return;
        }
        List<Peminjaman> pinjamList = null;
        laporanInventaris(invController.getAllBarang(), pinjamList);
    }
}
