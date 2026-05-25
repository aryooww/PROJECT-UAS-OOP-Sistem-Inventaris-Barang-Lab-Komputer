package model;

import java.time.LocalDate;

public class riwayatpeminjaman {
    private String idRiwayat;
    private peminjaman peminjaman;  
    private pengembalian pengembalian;  
    private LocalDate tanggalPengembalian;
    private String kondisiTerakhir;
    private int jumlahDikembalikan;
    private String status;

    // constructor
    public riwayatpeminjaman(String idRiwayat, peminjaman peminjaman) {
        this.idRiwayat = idRiwayat;
        this.peminjaman = peminjaman;
        this.pengembalian = null;
        this.tanggalPengembalian = null;
        this.kondisiTerakhir = "";
        this.jumlahDikembalikan = 0;
        this.status = "BELUM DIKEMBALIKAN";
    }

    // method saat barang dikembalikan
    public void selesaikanPeminjaman(pengembalian pengembalian) {  
        this.pengembalian = pengembalian;
        this.tanggalPengembalian = pengembalian.getTanggalKembali();  
        this.kondisiTerakhir = pengembalian.getKondisiBarang();  
        this.jumlahDikembalikan = pengembalian.getJumlahDikembalikan(); 
        this.status = "SELESAI";
    }

     // getter
    public String getIdRiwayat() {
        return idRiwayat;
    }

    public peminjaman getPeminjaman() {  
        return peminjaman;
    }

    public pengembalian getPengembalian() { 
        return pengembalian;
    }

    public LocalDate getTanggalPengembalian() {
        return tanggalPengembalian;
    }

    public String getKondisiTerakhir() {
        return kondisiTerakhir;
    }

    public int getJumlahDikembalikan() {
        return jumlahDikembalikan;
    }

    public String getStatus() {
        return status;
    }

    // setter
    public void setIdRiwayat(String idRiwayat) {
        this.idRiwayat = idRiwayat;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // method untuk menampilkan riwayat pinjaman
    public String tampilkanRiwayat() {
        return "ID: " + idRiwayat + ", Barang: " + peminjaman.getNamaBarang() +  
               ", Peminjam: " + peminjaman.getNamaPeminjam() +  
               ", NIM: " + peminjaman.getNIMPeminjam() +
               ", Tanggal Pinjam: " + peminjaman.getTanggalPinjam() +
               ", Tanggal Kembali: " + (tanggalPengembalian != null ? tanggalPengembalian : "Belum kembali") +
               ", Status: " + status;
    }
}

