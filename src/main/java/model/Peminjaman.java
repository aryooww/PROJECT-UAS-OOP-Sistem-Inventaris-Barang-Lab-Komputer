package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Peminjaman {
    private static int counter = 1;
    private String idPeminjaman;
    private String idBarang;
    private String namaPeminjam;
    private String nim;
    private int jumlahPinjam;
    private int jumlahKembali;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private String status; // "DIPINJAM", "SEBAGIAN", "SELESAI"

    public Peminjaman(String idBarang, String namaPeminjam, String nim, int jumlahPinjam) {
        this.idPeminjaman = "PJM" + String.format("%03d", counter++);
        this.idBarang = idBarang;
        this.namaPeminjam = namaPeminjam;
        this.nim = nim;
        this.jumlahPinjam = jumlahPinjam;
        this.jumlahKembali = 0;
        this.tanggalPinjam = LocalDate.now();
        this.status = "DIPINJAM";
    }

    // Getter & Setter
    public String getIdPeminjaman() { return idPeminjaman; }
    public String getIdBarang() { return idBarang; }
    public String getNamaPeminjam() { return namaPeminjam; }
    public String getNim() { return nim; }
    public int getJumlahPinjam() { return jumlahPinjam; }
    public int getJumlahKembali() { return jumlahKembali; }
    public LocalDate getTanggalPinjam() { return tanggalPinjam; }
    public LocalDate getTanggalKembali() { return tanggalKembali; }
    public String getStatus() { return status; }

    public int getSisaBelumKembali() {
        return jumlahPinjam - jumlahKembali;
    }

    public void setJumlahKembali(int jumlahKembali) {
        this.jumlahKembali = jumlahKembali;
        if (this.jumlahKembali >= this.jumlahPinjam) {
            this.status = "SELESAI";
            this.tanggalKembali = LocalDate.now();
        } else if (this.jumlahKembali > 0) {
            this.status = "SEBAGIAN";
        } else {
            this.status = "DIPINJAM";
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String tglKembaliStr = (tanggalKembali == null) ? "-" : tanggalKembali.format(formatter);
        return String.format("ID: %s | Barang: %s | Peminjam: %s (%s) | Pinjam: %d | Kembali: %d | Sisa: %d | Status: %s | Tgl Pinjam: %s | Tgl Kembali: %s",
                idPeminjaman, idBarang, namaPeminjam, nim, jumlahPinjam, jumlahKembali, getSisaBelumKembali(),
                status, tanggalPinjam.format(formatter), tglKembaliStr);
    }
}