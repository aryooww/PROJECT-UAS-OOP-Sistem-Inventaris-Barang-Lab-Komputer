package model;

// Subclass baru mewarisi Barang
public class BarangPremium extends Barang {
    private String tingkatPrioritas; // Misal: Fragile, VIP
    private double biayaAsuransi;

    public BarangPremium(String id, String nama, int jumlah, String lokasi, String tingkatPrioritas, double biayaAsuransi) {
        super(id, nama, jumlah, lokasi);
        this.tingkatPrioritas = tingkatPrioritas;
        this.biayaAsuransi = biayaAsuransi;
    }

    public String getTingkatPrioritas() { return tingkatPrioritas; }
    public void setTingkatPrioritas(String tingkatPrioritas) { this.tingkatPrioritas = tingkatPrioritas; }

    public double getBiayaAsuransi() { return biayaAsuransi; }
    public void setBiayaAsuransi(double biayaAsuransi) { this.biayaAsuransi = biayaAsuransi; }

    @Override
    public String getKategori() {
        return "Premium (" + tingkatPrioritas + ")";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Asuransi: Rp.%,.0f", biayaAsuransi);
    }
}