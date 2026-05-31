package controller;

import model.Barang;
import model.BarangElektronik;
import model.Peminjaman;
import exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeminjamanControllerTest {

    private InventoryController inventoryController;
    private PeminjamanController peminjamanController;

    @BeforeEach
    void setUp() throws Exception {
        // Reset static counter di Peminjaman agar ID konsisten
        Field counterField = Peminjaman.class.getDeclaredField("counter");
        counterField.setAccessible(true);
        counterField.setInt(null, 1);

        inventoryController = new InventoryController();
        peminjamanController = new PeminjamanController(inventoryController);

        // Siapkan stok barang
        Barang laptop = new BarangElektronik("L01", "Laptop", 10, "Gudang A", 220);
        Barang mouse = new BarangElektronik("M01", "Mouse", 5, "Gudang B", 5);
        inventoryController.tambahBarang(laptop);
        inventoryController.tambahBarang(mouse);
    }

    @Test
    void testPinjamBarang_Success() throws ValidationException {
        peminjamanController.pinjamBarang("L01", "Budi", "12345", 2);
        Barang barang = inventoryController.cariById("L01");
        assertEquals(8, barang.getJumlah()); // stok berkurang

        List<Peminjaman> all = peminjamanController.getAllPeminjaman();
        assertEquals(1, all.size());
        Peminjaman p = all.get(0);
        assertEquals("L01", p.getIdBarang());
        assertEquals("Budi", p.getNamaPeminjam());
        assertEquals(2, p.getJumlahPinjam());
        assertEquals(0, p.getJumlahKembali());
        assertEquals("DIPINJAM", p.getStatus());
    }

    @Test
    void testPinjamBarang_JumlahNegatif_ThrowsException() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> peminjamanController.pinjamBarang("L01", "Budi", "12345", -1));
        assertEquals("Jumlah pinjam harus > 0", exception.getMessage());
    }

    @Test
    void testPinjamBarang_StokTidakCukup_ThrowsException() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> peminjamanController.pinjamBarang("L01", "Budi", "12345", 20));
        assertEquals("Stok tidak cukup (tersedia: 10)", exception.getMessage());
    }

    @Test
    void testPinjamBarang_BarangNotFound_ThrowsException() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> peminjamanController.pinjamBarang("XXX", "Budi", "12345", 1));
        assertEquals("Barang tidak ditemukan", exception.getMessage());
    }

    @Test
    void testKembalikanBarang_Success() throws ValidationException {
        // Pinjam 3 mouse
        peminjamanController.pinjamBarang("M01", "Ani", "67890", 3);
        Peminjaman p = peminjamanController.getAllPeminjaman().get(0);
        String idPeminjaman = p.getIdPeminjaman();

        // Kembalikan 2
        peminjamanController.kembalikanBarang(idPeminjaman, 2);

        Barang mouse = inventoryController.cariById("M01");
        assertEquals(4, mouse.getJumlah()); // stok awal 5, dipinjam 3 -> sisa 2, +2 kembali = 4? Wait, hitung: awal 5, pinjam 3 => stok 2. Kembali 2 => stok 4. Ya benar.
        Peminjaman updated = peminjamanController.cariPeminjamanById(idPeminjaman);
        assertEquals(2, updated.getJumlahKembali());
        assertEquals(1, updated.getSisaBelumKembali());
        assertEquals("SEBAGIAN", updated.getStatus());
    }

    @Test
    void testKembalikanBarang_Lunas() throws ValidationException {
        peminjamanController.pinjamBarang("L01", "Citra", "11223", 1);
        Peminjaman p = peminjamanController.getAllPeminjaman().get(0);
        String id = p.getIdPeminjaman();

        peminjamanController.kembalikanBarang(id, 1);

        Barang laptop = inventoryController.cariById("L01");
        assertEquals(10, laptop.getJumlah()); // awal 10, pinjam 1 jadi 9, kembali 1 jadi 10
        Peminjaman updated = peminjamanController.cariPeminjamanById(id);
        assertEquals("SELESAI", updated.getStatus());
        assertNotNull(updated.getTanggalKembali());
    }

    @Test
    void testKembalikanBarang_MelebihiSisa_ThrowsException() throws ValidationException {
        peminjamanController.pinjamBarang("M01", "Doni", "44556", 2);
        Peminjaman p = peminjamanController.getAllPeminjaman().get(0);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> peminjamanController.kembalikanBarang(p.getIdPeminjaman(), 3));
        assertEquals("Melebihi sisa pinjam (sisa: 2)", exception.getMessage());
    }

    @Test
    void testKembalikanBarang_IdPeminjamanNotFound_ThrowsException() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> peminjamanController.kembalikanBarang("PJM999", 1));
        assertEquals("ID peminjaman tidak ditemukan", exception.getMessage());
    }

    @Test
    void testCariPeminjamanById() throws ValidationException {
        peminjamanController.pinjamBarang("L01", "Eka", "33445", 1);
        Peminjaman p = peminjamanController.getAllPeminjaman().get(0);
        Peminjaman found = peminjamanController.cariPeminjamanById(p.getIdPeminjaman());
        assertNotNull(found);
        assertEquals("Eka", found.getNamaPeminjam());
    }
}