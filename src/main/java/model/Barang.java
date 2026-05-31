package model;

public abstract class Barang {
    private String id;
    private String nama;
    private int jumlah;
    private String lokasi;

    public Barang(String id, String nama, int jumlah, String lokasi) {
        this.id = id;
        this.nama = nama;
        this.jumlah = jumlah;
        this.lokasi = lokasi;
    }

    // Encapsulation: Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }

    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    // Method abstract yang wajib diimplement subclass
    public abstract String getKategori();

    @Override
    public String toString() {
        return String.format("ID: %s | Nama: %s | Jumlah: %d | Lokasi: %s | Kategori: %s",
                id, nama, jumlah, lokasi, getKategori());
    }
}