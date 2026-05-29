package src.model;

// Abstract class untuk barang
public abstract class barang {
    private String id;
    private String nama;
    private int jumlah;
    private String lokasi;

    // Constructor
    public barang(String id, String nama, int jumlah, String lokasi) {
        this.id = id;
        this.nama = nama;
        this.jumlah = jumlah;
        this.lokasi = lokasi;
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getLokasi() {
        return lokasi;
    }

    // Setter
    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

}
