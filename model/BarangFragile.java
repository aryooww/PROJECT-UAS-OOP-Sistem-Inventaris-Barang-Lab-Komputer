package model;

public class BarangFragile extends Barang {
    private String bahan;
    
    public BarangFragile(String id, String nama, int jumlah, String lokasi, String bahan) {
        super(id, nama, jumlah, lokasi);
        this.bahan = bahan;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    @Override
    public String getKategori() {
        return "Fragile (" + bahan + ")";
    }

}
