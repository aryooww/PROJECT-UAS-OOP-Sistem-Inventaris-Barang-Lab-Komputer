package model;

import exception.ValidationException; // untuk validasi data
import java.util.List;

// class untuk manajemen peminjaman barang dan interface ini menyediakan isi dari semua method yg ada
public interface OperasiPeminjaman {
    // ethod untuk melakukan peminjaman barang
    void pinjamBarang(String idBarang, String namaPeminjam, String nim, int jumlah) throws ValidationException;

    //  Method untuk melakukan pengembalian barang
    void kembalikanBarang(String idPeminjaman, int jumlahKembali) throws ValidationException;

    //  Method untuk mencari data peminjaman berdasarkan ID-nya
    Peminjaman cariPeminjamanById(String idPeminjaman);

    // Method untuk mengambil semua data peminjaman yang ada
    List<Peminjaman> getAllPeminjaman();
}