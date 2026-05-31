package model;

import java.util.List;

import exception.ValidationException;

public interface OperasiPeminjaman {
    void pinjamBarang(String idBarang, String namaPeminjam, String nim, int jumlah) throws ValidationException;
    void kembalikanBarang(String idPeminjaman, int jumlahKembali) throws ValidationException;
    Peminjaman cariPeminjamanById(String idPeminjaman);
    List<Peminjaman> getAllPeminjaman();
}