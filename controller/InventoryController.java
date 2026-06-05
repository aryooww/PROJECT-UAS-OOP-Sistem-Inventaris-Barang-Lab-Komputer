package controller;

import java.util.ArrayList;
import java.util.List;

import exception.ValidationException;
import model.*;

public class InventoryController {
    private List<Barang> daftarBarang = new ArrayList<>();

    // CREATE
    public void tambahBarang(Barang barang) throws ValidationException {
        if (barang == null) throw new ValidationException("Barang tidak boleh null");
        if (cariById(barang.getId()) != null)
            throw new ValidationException("ID sudah ada: " + barang.getId());
        daftarBarang.add(barang);
    }

    // READ
    public List<Barang> getAllBarang() {
        return new ArrayList<>(daftarBarang);
    }

    public Barang cariById(String id) {
        for (Barang b : daftarBarang) {
            if (b.getId().equalsIgnoreCase(id)) return b;
        }
        return null;
    }

    public List<Barang> cariByNama(String keyword) {
        List<Barang> hasil = new ArrayList<>();
        for (Barang b : daftarBarang) {
            if (b.getNama().toLowerCase().contains(keyword.toLowerCase()))
                hasil.add(b);
        }
        return hasil;
    }

    // UPDATE
    public void updateBarang(String id, String namaBaru, int jumlahBaru, String lokasiBaru) throws ValidationException {
        Barang barang = cariById(id);
        if (barang == null) throw new ValidationException("Barang dengan ID " + id + " tidak ditemukan");
        if (namaBaru != null && !namaBaru.trim().isEmpty()) barang.setNama(namaBaru);
        if (jumlahBaru >= 0) barang.setJumlah(jumlahBaru);
        if (lokasiBaru != null && !lokasiBaru.trim().isEmpty()) barang.setLokasi(lokasiBaru);
    }

    // DELETE
    public boolean hapusBarang(String id) throws ValidationException {
        Barang barang = cariById(id);
        if (barang == null) throw new ValidationException("ID tidak ditemukan: " + id);
        return daftarBarang.remove(barang);
    }

}