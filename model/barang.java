package model;

// abstract class barang
public abstract class barang {
    private String namaAlat;
    private String kodeBarang;
    private int jumlahBarang;
    private String kondisiBarang;
    private String kategoriBarang;

    // constructor
    public barang(String namaAlat, String kodeBarang, int jumlahBarang, String kondisiBarang, String kategoriBarang) {
        this.namaAlat = namaAlat;
        this.kodeBarang = kodeBarang;
        this.jumlahBarang = jumlahBarang;
        this.kategoriBarang = kategoriBarang;
    }
}
