package controller;

import java.util.ArrayList;
import java.util.List;

import exception.ValidationException;
import model.*;

public class PeminjamanController implements OperasiPeminjaman {
    private List<Peminjaman> daftarPeminjaman = new ArrayList<>();

    private InventoryController inventoryController;

    public PeminjamanController(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
    }
    
    //** Metode peminjaman barang */
    @Override
    public void pinjamBarang(String idBarang, String namaPeminjam, String nim, int jumlah) throws ValidationException {
        if (jumlah <= 0) throw new ValidationException("Jumlah pinjam harus > 0");
        Barang barang = inventoryController.cariById(idBarang);
        if (barang == null) throw new ValidationException("Barang tidak ditemukan");
        if (barang.getJumlah() < jumlah) 
            throw new ValidationException("Stok tidak cukup (tersedia: " + barang.getJumlah() + ")");

        barang.setJumlah(barang.getJumlah() - jumlah);
        Peminjaman peminjaman = new Peminjaman(idBarang, namaPeminjam, nim, jumlah);
        daftarPeminjaman.add(peminjaman);
    }

    //** Metode pengembalian barang */
    @Override
    public void kembalikanBarang(String idPeminjaman, int jumlahKembali) throws ValidationException {
        if (jumlahKembali <= 0) throw new ValidationException("Jumlah kembali harus > 0");
        Peminjaman p = cariPeminjamanById(idPeminjaman);
        if (p == null) throw new ValidationException("ID peminjaman tidak ditemukan");
        if (p.getStatus().equals("SELESAI")) throw new ValidationException("Barang sudah lunas dikembalikan");

        int sisa = p.getSisaBelumKembali();
        if (jumlahKembali > sisa) throw new ValidationException("Melebihi sisa pinjam (sisa: " + sisa + ")");

        Barang barang = inventoryController.cariById(p.getIdBarang());
        if (barang != null) {
            barang.setJumlah(barang.getJumlah() + jumlahKembali);
        }

        p.setJumlahKembali(p.getJumlahKembali() + jumlahKembali);
    }

    @Override
    public Peminjaman cariPeminjamanById(String id) {
        for (Peminjaman p : daftarPeminjaman) {
            if (p.getIdPeminjaman().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    @Override
    public List<Peminjaman> getAllPeminjaman() {
        return new ArrayList<>(daftarPeminjaman);
    }

}