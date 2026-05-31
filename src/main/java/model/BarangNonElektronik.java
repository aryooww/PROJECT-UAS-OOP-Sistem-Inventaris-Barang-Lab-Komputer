package model;

public class BarangNonElektronik extends Barang {
    private String bahan;

    public BarangNonElektronik(String id, String nama, int jumlah, String lokasi, String bahan) {
        super(id, nama, jumlah, lokasi);
        this.bahan = bahan;
    }

    public String getBahan() { return bahan; }
    public void setBahan(String bahan) { this.bahan = bahan; }

    @Override
    public String getKategori() {
        return "Non-Elektronik (" + bahan + ")";
    }
}