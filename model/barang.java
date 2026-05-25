package model;

// abstract class barang
public abstract class barang {
    private String namaAlat;
    private String kodeBarang;
    private int jumlahBarang;
    private String kondisiBarang;
    private String kategoriBarang;

    // constructor
    public barang(String namaAlat, String kodeBarang, int jumlahBarang, String kondisiBarang, String kategoriBarang) {
        this.namaAlat = namaAlat;
        this.kodeBarang = kodeBarang;
        this.jumlahBarang = jumlahBarang;
        this.kondisiBarang = kondisiBarang;
        this.kategoriBarang = kategoriBarang;
    }

    // getter
    public String getNamaAlat() {
        return namaAlat;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public String getKondisiBarang() {
        return kondisiBarang;
    }

    public String getKategoriBarang() {
        return kategoriBarang;
    }

    // setter
    public void setNamaAlat(String namaAlat) {
        this.namaAlat = namaAlat;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public void setKondisiBarang(String kondisiBarang) {
        this.kondisiBarang = kondisiBarang;
    }

    public void setKategoriBarang(String kategoriBarang) {
        this.kategoriBarang = kategoriBarang;
    }

    // abstract method
    public abstract String getJenisBarang();

    // polymorphism (method overloading)
    // method untuk mengurangi jumlah barang
    public void kurangiJumlahBarang(int jumlah) {
        if (jumlahBarang <= this.jumlahBarang) {
            this.jumlahBarang -= jumlah;
        } else {
            throw new IllegalArgumentException("Stok tidak cukup.");
        }        
    }

    // overloading method untuk mengurangi jumlah barang 
    public void kurangiJumlahBarang (int jumlah, String alasan) {
        kurangiJumlahBarang(jumlah);
        System.out.println("Jumlah barang dikurangi karena: " + alasan);
    }

    // method untuk menambah jumlah stok barang
    public void tambahJumlahBarang(int jumlah) {
        if (jumlah > 0) {
            this.jumlahBarang += jumlah;
        } else {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0.");
        }   
    }

     // override method toString untuk menampilkan informasi barang
    @Override
    public String toString() {
        return "Nama Alat: " + namaAlat + ", Kode Barang: " + kodeBarang + ", Jumlah Barang: " + jumlahBarang + ", Kondisi Barang: " + kondisiBarang + ", Kategori Barang: " + kategoriBarang;
    }
}
