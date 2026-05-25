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

     // getter 
    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getTipe() {
        return tipe;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public LocalDate getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getPetugas() {
        return petugas;
    }

    // setter
    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public void setTipe(String tipe) {
    if (tipe.equalsIgnoreCase("Masuk")) {
            this.tipe = "Masuk";
        } else if (tipe.equalsIgnoreCase("Keluar")) {
            this.tipe = "Keluar";
        } else {
            throw new IllegalArgumentException("Tipe harus 'Masuk' atau 'Keluar'");
        } 
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public void setTanggalTransaksi(LocalDate tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setPetugas(String petugas) {
        this.petugas = petugas;
    }

    // Override method toString untuk menampilkan informasi barang masuk/keluar
    @Override
    public String toString() {
        return "ID Transaksi: " + idTransaksi + ", Nama Barang: " + namaBarang + ", Kode Barang: " + kodeBarang + ", Tipe: " + tipe + ", Jumlah Barang: " + jumlahBarang + ", Tanggal Transaksi: " + tanggalTransaksi + ", Keterangan: " + keterangan    + ", Petugas: " + petugas;
    }
}
