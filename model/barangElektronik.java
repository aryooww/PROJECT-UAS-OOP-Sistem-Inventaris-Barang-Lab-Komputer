package model;

public class barangElektronik extends barang {

    protected String merk;
    protected String warna;

    // constructor
    public barangElektronik(String namaAlat, String kodeBarang, int jumlahBarang,  String kondisiBarang, String kategoriBarang, String merk, String warna) {
        super(namaAlat, kodeBarang, jumlahBarang, kondisiBarang, kategoriBarang);
        this.merk = merk;
        this.warna = warna;
    }

    // getter
    public String getMerk() {
        return merk;
    }

    public String getWarna() {
        return warna;
    }

    // setter
    public void setMerk(String merk) {
        this.merk = merk;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    // implementasi abstract method
    @Override
    public String getJenisBarang() {
        return "Elektronik - Merk : " + merk + " ( " + warna + " )";
    }

    @Override
    public String toString() {
        return super.toString() + " | " + getJenisBarang();
    }
    
}
