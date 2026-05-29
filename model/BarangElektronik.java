package model;

// class BarangElektronik extends Barang
public class BarangElektronik extends Barang {
    private int tegangan; // voltase

    // Constructor
    public BarangElektronik(String id, String nama, int jumlah, String lokasi, int tegangan) {
        super(id, nama, jumlah, lokasi);
        this.tegangan = tegangan;
    }

    // Getter
    public int getTegangan() { return tegangan; }

    // Setter
    public void setTegangan(int tegangan) { this.tegangan = tegangan; }

    // Menampilkan kategori khusus BarangElektronik
    @Override
    public String getKategori() {
        return "Elektronik (" + tegangan + "V)";
    }
}