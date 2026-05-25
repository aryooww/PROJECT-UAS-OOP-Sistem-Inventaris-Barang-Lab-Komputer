package model;

import java.util.List;

// interface LaporanOperasi
public interface LaporanOperasi {

    // method untuk menghasilkan laporan data barang
    void generateLaporanBarang(List<barang> daftarBarang);

    // method untuk menghasilkan laporan peminjaman dan pengembalian
    void generateLaporanPeminjaman(List<riwayatpeminjaman> riwayat);
}