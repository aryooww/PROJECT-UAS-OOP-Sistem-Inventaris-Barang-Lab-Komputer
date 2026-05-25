package model;

// class barangNonElektronik  
public class barangNonElektronik extends barang {

    protected String bahan;
    protected String warna;

    public barangNonElektronik(String namaAlat, String kodeBarang, int jumlahBarang, String kondisiBarang, String kategoriBarang, String bahan, String warna) {
        super(namaAlat, kodeBarang, jumlahBarang, kondisiBarang, kategoriBarang);

        this.bahan = bahan;
        this.warna = warna;
    }

    // getter
    public String getBahan() {
        return bahan;
    }

    public String getWarna() {
        return warna;
    }

    // setter
    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    // implementasi abstract method
    @Override
    public String getJenisBarang() {
        return "Non Elektronik - Bahan : "
                + bahan + ", Warna : " + warna;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + getJenisBarang();
    }
}
