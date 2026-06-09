package model;

public class BarangRentan extends Barang {
    private int tingkatMudahPecah; // 1 = sangat kuat, 10 = sangat rapuh

    public BarangRentan(String id, String nama, int jumlah, String lokasi, int tingkatMudahPecah) {
        super(id, nama, jumlah, lokasi);
        this.tingkatMudahPecah = tingkatMudahPecah;
    }

    public int getTingkatKerapuhan() {
        return tingkatMudahPecah;
    }

    public void setTingkatKerapuhan(int tingkatMudahPecah) {
        this.tingkatMudahPecah = tingkatMudahPecah;
    }

    @Override
    public String getKategori() {
        return "Barang Rentan [MudahPecah: " + tingkatMudahPecah + "]";
    }

    @Override
    public String toString() {
        return super.toString() + " | Kerapuhan: " + tingkatMudahPecah + "/10";
    }
}