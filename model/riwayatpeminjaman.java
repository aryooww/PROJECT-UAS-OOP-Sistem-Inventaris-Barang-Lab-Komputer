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

    

}