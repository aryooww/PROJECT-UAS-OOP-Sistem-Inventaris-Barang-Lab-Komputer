package controller;

import model.barangMasukKeluar;
import model.CrudOperasi;

import java.util.ArrayList;
import java.util.List;

/**
 * Service CRUD untuk transaksi barang masuk dan keluar (penyimpanan dalam memori).
 */
public class BarangMasukKeluarService implements CrudOperasi<barangMasukKeluar> {
    private List<barangMasukKeluar> transaksiList = new ArrayList<>();

    @Override
    public void create(barangMasukKeluar obj) {
        transaksiList.add(obj);
    }

    @Override
    public List<barangMasukKeluar> readAll() {
        return new ArrayList<>(transaksiList);
    }

    @Override
    public barangMasukKeluar readById(String id) {
        for (barangMasukKeluar t : transaksiList) {
            if (t.getIdTransaksi().equals(id)) return t;
        }
        return null;
    }

    @Override
    public void update(barangMasukKeluar obj) {
        barangMasukKeluar existing = readById(obj.getIdTransaksi());
        if (existing == null) throw new IllegalArgumentException("Transaksi tidak ditemukan.");
        delete(obj.getIdTransaksi());
        transaksiList.add(obj);
    }

    @Override
    public void delete(String id) {
        barangMasukKeluar target = readById(id);
        if (target != null) transaksiList.remove(target);
    }
}