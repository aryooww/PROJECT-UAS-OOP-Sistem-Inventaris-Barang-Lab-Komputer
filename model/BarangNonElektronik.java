package model;

//  Class BarangNonElektronik extends Barang
public class BarangNonElektronik extends Barang {
    private String bahan;

    // Constructor
    public BarangNonElektronik(String id, String nama, int jumlah, String lokasi, String bahan) {
        super(id, nama, jumlah, lokasi);
        this.bahan = bahan;
    }

    // Getter 
    public String getBahan() { return bahan; }

    // Setter
    public void setBahan(String bahan) { this.bahan = bahan; }


    // Menampilkan kategori khusus untuk barang non-elektronik
    @Override
    public String getKategori() {
        return "Non-Elektronik (" + bahan + ")";
    }
}