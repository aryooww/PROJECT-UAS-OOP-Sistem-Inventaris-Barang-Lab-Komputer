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

    public List<Barang> cariByNama(String keyword) {
        List<Barang> hasil = new ArrayList<>();
        for (Barang b : daftarBarang) {
            if (b.getNama().toLowerCase().contains(keyword.toLowerCase()))
                hasil.add(b);
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

    public List<Barang> getFilteredBarang(String kategoriFilter) {
        List<Barang> hasil = new ArrayList<>();
        for (Barang b : daftarBarang) {
            String kat = b.getKategori();
            if (kategoriFilter.equals("Elektronik") && kat.contains("Elektronik"))
                hasil.add(b);
            else if (kategoriFilter.equals("Non-Elektronik") && kat.contains("Non-Elektronik"))
                hasil.add(b);
            else if (kategoriFilter.equals("Fragile") && kat.contains("Fragile"))
                hasil.add(b);
        }
        return hasil;
    }
} 

