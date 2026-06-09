package controller;

import model.*;
import exception.ValidationException;
import java.util.ArrayList;
import java.util.List;

public class InventoryController implements OperasiBarang {
    private List<Barang> daftarBarang = new ArrayList<>();

    @Override
    public void tambahBarang(Barang barang) throws ValidationException {
        if(barang == null) {
            throw new ValidationException("Barang tidak boleh null");
        }
        if (cariById(barang.getId()) != null) {
            throw new ValidationException("Barang dengan ID tersebut sudah ada");
        }
        daftarBarang.add(barang);
    }

    @Override
    public List<Barang> getAllBarang() {
        return new ArrayList<>(daftarBarang);
    }

    public Barang cariById(String id) {
        for (Barang barang : daftarBarang) {
            if (barang.getId().equals(id)) {
                return barang;
            }
        }
        return null;
    }

    public List<Barang> CariByNama(String nama) {
        List<Barang> hasil = new ArrayList<>();
        for (Barang barang : daftarBarang) {
            if (barang.getNama().equalsIgnoreCase(nama)) {
                hasil.add(barang);
            }
        }
        return hasil;
    }

    @Override
    public void updateBarang(String id, String nama, int jumlah, String lokasi) throws ValidationException {
        Barang barang = cariById(id);
        if (barang == null) {
            throw new ValidationException("Barang dengan ID tersebut tidak ditemukan");
        }
        if (nama != null && !nama.isEmpty()) {
            barang.setNama(nama);
        }
        if (jumlah >= 0) {
            barang.setJumlah(jumlah);
        }
        if (lokasi != null && !lokasi.isEmpty()) {
            barang.setLokasi(lokasi);
        }
    }

    @Override
    public void hapusBarang(String id) throws ValidationException {
        Barang barang = cariById(id);
        if (barang == null) {
            throw new ValidationException("Barang dengan ID tersebut tidak ditemukan");
        }
        daftarBarang.remove(barang);
    }
}
    

