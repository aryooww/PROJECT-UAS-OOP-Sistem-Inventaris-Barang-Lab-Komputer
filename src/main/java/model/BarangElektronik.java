package model;

public class BarangElektronik extends Barang {
    private int tegangan; // voltase

    public BarangElektronik(String id, String nama, int jumlah, String lokasi, int tegangan) {
        super(id, nama, jumlah, lokasi);
        this.tegangan = tegangan;
    }

    public int getTegangan() { return tegangan; }
    public void setTegangan(int tegangan) { this.tegangan = tegangan; }

    @Override
    public String getKategori() {
        return "Elektronik (" + tegangan + "V)";
    }
}