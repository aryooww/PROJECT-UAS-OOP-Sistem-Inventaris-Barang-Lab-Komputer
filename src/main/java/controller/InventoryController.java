package controller;

import java.util.ArrayList;
import java.util.List;

import exception.ValidationException;
import model.*;

public class InventoryController implements OperasiBarang {
    private List<Barang> daftarBarang = new ArrayList<>();

    // CREATE
    @Override
    public void create() {
        // Method ini dipanggil dari view dengan input yang sudah divalidasi
        // Overload dibuat untuk menerima objek Barang langsung
    }

    // Overload untuk menambah barang langsung
    public void tambahBarang(Barang barang) throws ValidationException {
        if (barang == null) throw new ValidationException("Barang tidak boleh null");
        if (cariById(barang.getId()) != null)
            throw new ValidationException("ID sudah ada: " + barang.getId());
        daftarBarang.add(barang);
    }

    // READ
    @Override
    public void read() {
        // Output ke view sebaiknya di view, tapi untuk sederhana kita return list
    }

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
    @Override
    public void update() {
        // Dilakukan via view + method ini
    }

    public void updateBarang(String id, String namaBaru, int jumlahBaru, String lokasiBaru) throws ValidationException {
        Barang barang = cariById(id);
        if (barang == null) throw new ValidationException("Barang dengan ID " + id + " tidak ditemukan");
        if (namaBaru != null && !namaBaru.trim().isEmpty()) barang.setNama(namaBaru);
        if (jumlahBaru >= 0) barang.setJumlah(jumlahBaru);
        if (lokasiBaru != null && !lokasiBaru.trim().isEmpty()) barang.setLokasi(lokasiBaru);
    }

    // DELETE
    @Override
    public void delete() {
        // via view
    }

    public boolean hapusBarang(String id) throws ValidationException {
        Barang barang = cariById(id);
        if (barang == null) throw new ValidationException("ID tidak ditemukan: " + id);
        return daftarBarang.remove(barang);
    }

    // Tambahkan method ini di dalam class InventoryController Anda
    public List<Barang> getFilterBarangPremium() {
        List<Barang> hasilFilter = new ArrayList<>();
        for (Barang b : daftarBarang) {
            // instanceof membedakan mana barang premium dan barang biasa
            if (b instanceof model.BarangPremium) {
                hasilFilter.add(b);
            }
        }
        return hasilFilter;
    }

}