package model;
import java.time.LocalDate;

// class barangMasukKeluar
public class barangMasukKeluar {
    private String idTransaksi;
    private String namaBarang;
    private String kodeBarang;
    private String tipe; // "Masuk" atau "Keluar"
    private int jumlahBarang;
    private LocalDate tanggalTransaksi;
    private String keterangan;
    private String petugas;

    // constructor
    public barangMasukKeluar(String idTransaksi, String namaBarang, String kodeBarang, String tipe, int jumlahBarang, LocalDate tanggalTransaksi, String keterangan, String petugas) {
        this.idTransaksi = idTransaksi;
        this.namaBarang = namaBarang;
        this.kodeBarang = kodeBarang;
        setTipe(tipe); // Validasi tipe 
        this.jumlahBarang = jumlahBarang;
        this.tanggalTransaksi = tanggalTransaksi;
        this.keterangan = keterangan;
        this.petugas = petugas;
    }
}
