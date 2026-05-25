package model;

public class barangElektronik extends barang {
    
    protected String merk;
    protected String warna;

    // constructor
    public barangElektronik(String namaAlat, String kodeBarang, int jumlahBarang,  String kondisiBarang, String kategoriBarang, String merk, String warna) {
        super(namaAlat, kodeBarang, jumlahBarang, kondisiBarang, kategoriBarang);
        this.merk = merk;
        this.warna = warna;
    }
    
}
