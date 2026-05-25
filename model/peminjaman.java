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

    // getter
    public String getNamaPeminjam() {
        return namaPeminjam;
    }

    public int getNIMPeminjam() {
        return NIMPeminjam;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public int getJumlahPinjam() {
        return jumlahPinjam;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    public String getKondisiBarang() {
        return kondisiBarang;
    }

    // setter
    public void setNamaPeminjam(String namaPeminjam) {
        this.namaPeminjam = namaPeminjam;
    }

    public void setNIMPeminjam(int NIMPeminjam) {
        this.NIMPeminjam = NIMPeminjam;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public void setJumlahPinjam(int jumlahPinjam) {
        this.jumlahPinjam = jumlahPinjam;
    }

    public void setTanggalPinjam(LocalDate tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public void setKondisiBarang(String kondisiBarang) {
        this.kondisiBarang = kondisiBarang;
    }  

    // method untuk ngecek apakah barang yg dipinjam sudah melewati batas pengembalian atau blm
    public boolean isOverdue() {
        LocalDate today = LocalDate.now();
        return today.isAfter(tanggalKembali);
    }

    // override method toString untuk menampilkan informasi peminjaman
    @Override
    public String toString() {
        return "namaPeminjam: " + namaPeminjam + ", NIMPeminjam: " + NIMPeminjam + ", namaBarang: " + namaBarang + ", kodeBarang: " + kodeBarang + ", jumlahPinjam: " + jumlahPinjam + ", tanggalPinjam: " + tanggalPinjam + ", tanggalKembali: " + tanggalKembali + ", kondisiBarang: " + kondisiBarang;
    }
}