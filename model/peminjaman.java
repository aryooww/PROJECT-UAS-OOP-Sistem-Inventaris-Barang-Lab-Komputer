package model;
import java.time.LocalDate;

// class peminjaman
public class peminjaman {
    private String namaPeminjam;
    private int NIMPeminjam;
    private String namaBarang;
    private String kodeBarang;
    private int jumlahPinjam;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private String kondisiBarang;

    // constructor
    public peminjaman(String namaPeminjam, int NIMPeminjam, String namaBarang, String kodeBarang, int jumlahPinjam, LocalDate tanggalPinjam, LocalDate tanggalKembali, String kondisiBarang) {
        this.namaPeminjam = namaPeminjam;
        this.NIMPeminjam = NIMPeminjam;
        this.namaBarang = namaBarang;
        this.kodeBarang = kodeBarang;
        this.jumlahPinjam = jumlahPinjam;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
        this.kondisiBarang = kondisiBarang;
    }
}