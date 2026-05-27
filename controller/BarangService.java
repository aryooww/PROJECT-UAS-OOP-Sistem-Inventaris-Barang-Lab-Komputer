package controller;

import model.barang;
import model.barangMasukKeluar;
import model.CrudOperasi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service untuk mengelola data barang dan stok.
 * Implementasi CRUD + pencatatan transaksi otomatis.
 */
public class BarangService implements CrudOperasi<barang> {
    private List<barang> daftarBarang = new ArrayList<>();
    private BarangMasukKeluarService transaksiService;

    public BarangService(BarangMasukKeluarService transaksiService) {
        this.transaksiService = transaksiService;
    }

    public BarangService() { }

    public void setTransaksiService(BarangMasukKeluarService transaksiService) {
        this.transaksiService = transaksiService;
    }

    @Override
    public void create(barang obj) {
        if (readById(obj.getKodeBarang()) != null) {
            throw new IllegalArgumentException("Barang dengan kode " + obj.getKodeBarang() + " sudah ada.");
        }
        daftarBarang.add(obj);
        if (transaksiService != null) {
            String idTrans = "TRX" + UUID.randomUUID().toString().substring(0, 8);
            barangMasukKeluar trans = new barangMasukKeluar(
                idTrans, obj.getNamaAlat(), obj.getKodeBarang(), "Masuk",
                obj.getJumlahBarang(), LocalDate.now(), "Penambahan barang baru", "System"
            );
            transaksiService.create(trans);
        }
    }

    @Override
    public List<barang> readAll() {
        return new ArrayList<>(daftarBarang);
    }

    @Override
    public barang readById(String id) {
        for (barang b : daftarBarang) {
            if (b.getKodeBarang().equals(id)) return b;
        }
        return null;
    }

    @Override
    public void update(barang obj) {
        barang existing = readById(obj.getKodeBarang());
        if (existing == null) throw new IllegalArgumentException("Barang tidak ditemukan.");
        delete(obj.getKodeBarang());
        daftarBarang.add(obj);
    }

    @Override
    public void delete(String id) {
        barang target = readById(id);
        if (target != null) {
            if (transaksiService != null && target.getJumlahBarang() > 0) {
                String idTrans = "TRX" + UUID.randomUUID().toString().substring(0, 8);
                barangMasukKeluar trans = new barangMasukKeluar(
                    idTrans, target.getNamaAlat(), id, "Keluar",
                    target.getJumlahBarang(), LocalDate.now(), "Penghapusan barang", "System"
                );
                transaksiService.create(trans);
            }
            daftarBarang.remove(target);
        }
    }

    /**
     * Menambah stok barang dan mencatat transaksi masuk.
     * @param kodeBarang kode barang
     * @param jumlah jumlah tambahan (positif)
     * @param alasan alasan penambahan (contoh: "Restok")
     * @param petugas nama petugas (contoh: "Admin")
     */
    public void tambahStok(String kodeBarang, int jumlah, String alasan, String petugas) {
        barang b = readById(kodeBarang);
        if (b == null) throw new IllegalArgumentException("Barang tidak ditemukan.");
        b.tambahJumlahBarang(jumlah);
        if (transaksiService != null) {
            String idTrans = "TRX" + UUID.randomUUID().toString().substring(0, 8);
            barangMasukKeluar trans = new barangMasukKeluar(
                idTrans, b.getNamaAlat(), kodeBarang, "Masuk",
                jumlah, LocalDate.now(), alasan, petugas
            );
            transaksiService.create(trans);
        }
    }

    /**
     * Mengurangi stok barang dan mencatat transaksi keluar.
     * @param kodeBarang kode barang
     * @param jumlah jumlah pengurangan (positif)
     * @param alasan alasan pengurangan (contoh: "Peminjaman")
     * @param petugas nama petugas (contoh: "Teknisi")
     */
    public void kurangiStok(String kodeBarang, int jumlah, String alasan, String petugas) {
        barang b = readById(kodeBarang);
        if (b == null) throw new IllegalArgumentException("Barang tidak ditemukan.");
        b.kurangiJumlahBarang(jumlah);
        if (transaksiService != null) {
            String idTrans = "TRX" + UUID.randomUUID().toString().substring(0, 8);
            barangMasukKeluar trans = new barangMasukKeluar(
                idTrans, b.getNamaAlat(), kodeBarang, "Keluar",
                jumlah, LocalDate.now(), alasan, petugas
            );
            transaksiService.create(trans);
        }
    }
}