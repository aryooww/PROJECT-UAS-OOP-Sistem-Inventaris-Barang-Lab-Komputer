package model;
import java.time.LocalDate;

// class riwayatpeminjaman
public class riwayatpeminjaman {
    private String IdRiwayat;
    private String peminjaman;
    private String pengembalian;
    private LocalDate tanggalPeminjaman;
    private LocalDate tanggalPengembalian;
    private String kondisiTerakhir;
    private int jumlahDikembalikan;
    private String status;

    // constructor
    public riwayatpeminjaman(String IdRiwayat, String peminjaman, String pengembalian, LocalDate tanggalPeminjaman, LocalDate tanggalPengembalian, String kondisiTerakhir, int jumlahDikembalikan, String status) {
        this.IdRiwayat = IdRiwayat;
        this.peminjaman = peminjaman;
        this.pengembalian = null;
        this.tanggalPeminjaman = tanggalPeminjaman;
        this.tanggalPengembalian = null;
        this.kondisiTerakhir = "";
        this.jumlahDikembalikan = 0;
        this.status = status;
    }