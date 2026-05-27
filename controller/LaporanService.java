package controller;

import java.util.List;

import model.LaporanOperasi;
import model.barang;
import model.riwayatpeminjaman;

/**
 * Service untuk mencetak laporan barang dan riwayat peminjaman ke konsol.
 * Implementasi dari {@link LaporanOperasi}.
 */
public class LaporanService implements LaporanOperasi {

    /**
     * Mencetak laporan semua barang ke System.out.
     * @param daftarBarang daftar barang yang akan dilaporkan (bisa kosong)
     */
    @Override
    public void generateLaporanBarang(List<barang> daftarBarang) {
        System.out.println("\n========== LAPORAN DATA BARANG ==========");
        if (daftarBarang.isEmpty()) {
            System.out.println("Belum ada data barang.");
        } else {
            for (barang b : daftarBarang) {
                System.out.println(b);
            }
        }
        System.out.println("=========================================\n");
    }

    /**
     * Mencetak laporan riwayat peminjaman dan pengembalian ke System.out.
     * @param riwayat daftar riwayat peminjaman (bisa kosong)
     */
    @Override
    public void generateLaporanPeminjaman(List<riwayatpeminjaman> riwayat) {
        System.out.println("\n========== LAPORAN PEMINJAMAN & PENGEMBALIAN ==========");
        if (riwayat.isEmpty()) {
            System.out.println("Belum ada riwayat peminjaman.");
        } else {
            for (riwayatpeminjaman r : riwayat) {
                System.out.println(r.tampilkanRiwayat());
                if (r.getPengembalian() != null) {
                    System.out.println("   Detail Pengembalian: " + r.getPengembalian());
                }
            }
        }
        System.out.println("=======================================================\n");
    }
}