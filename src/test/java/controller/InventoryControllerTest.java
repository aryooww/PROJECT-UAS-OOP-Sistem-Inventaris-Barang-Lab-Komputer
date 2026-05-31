package controller;

import model.Barang;
import model.BarangElektronik;
import exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryControllerTest {

    private InventoryController controller;

    @BeforeEach
    void setUp() {
        controller = new InventoryController();
    }

    @Test
    void testTambahBarang_Success() throws ValidationException {
        Barang barang = new BarangElektronik("E01", "Laptop", 10, "Gudang A", 220);
        controller.tambahBarang(barang);

        List<Barang> all = controller.getAllBarang();
        assertEquals(1, all.size());
        assertEquals("E01", all.get(0).getId());
        assertEquals("Laptop", all.get(0).getNama());
    }

    @Test
    void testTambahBarang_DuplicateId_ThrowsException() {
        Barang barang1 = new BarangElektronik("E01", "Laptop", 10, "Gudang A", 220);
        Barang barang2 = new BarangElektronik("E01", "Mouse", 5, "Gudang B", 5);
        assertDoesNotThrow(() -> controller.tambahBarang(barang1));
        ValidationException exception = assertThrows(ValidationException.class,
                () -> controller.tambahBarang(barang2));
        assertEquals("ID sudah ada: E01", exception.getMessage());
    }

    @Test
    void testTambahBarang_NullBarang_ThrowsException() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> controller.tambahBarang(null));
        assertEquals("Barang tidak boleh null", exception.getMessage());
    }

    @Test
    void testCariById_Found() throws ValidationException {
        Barang barang = new BarangElektronik("E02", "Printer", 5, "Ruang Lab", 110);
        controller.tambahBarang(barang);
        Barang found = controller.cariById("E02");
        assertNotNull(found);
        assertEquals("Printer", found.getNama());
    }

    @Test
    void testCariById_NotFound() {
        Barang found = controller.cariById("XXX");
        assertNull(found);
    }

    @Test
    void testCariByNama() throws ValidationException {
        controller.tambahBarang(new BarangElektronik("B01", "Monitor", 3, "Gudang", 220));
        controller.tambahBarang(new BarangElektronik("B02", "Mouse", 10, "Meja", 5));
        controller.tambahBarang(new BarangElektronik("B03", "Keyboard", 7, "Meja", 5));

        List<Barang> hasil = controller.cariByNama("mo");
        assertEquals(2, hasil.size());
        assertTrue(hasil.stream().allMatch(b -> b.getNama().toLowerCase().contains("mo")));
    }

    @Test
    void testUpdateBarang_Success() throws ValidationException {
        Barang barang = new BarangElektronik("U01", "Speaker", 2, "Ruang Rapat", 12);
        controller.tambahBarang(barang);

        controller.updateBarang("U01", "Speaker Bluetooth", 5, "Gudang Utama");

        Barang updated = controller.cariById("U01");
        assertEquals("Speaker Bluetooth", updated.getNama());
        assertEquals(5, updated.getJumlah());
        assertEquals("Gudang Utama", updated.getLokasi());
    }

    @Test
    void testUpdateBarang_IdNotFound_ThrowsException() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> controller.updateBarang("XXX", "Nama", 10, "Lokasi"));
        assertEquals("Barang dengan ID XXX tidak ditemukan", exception.getMessage());
    }

    @Test
    void testHapusBarang_Success() throws ValidationException {
        Barang barang = new BarangElektronik("D01", "Webcam", 4, "Lab Komputer", 5);
        controller.tambahBarang(barang);
        assertTrue(controller.hapusBarang("D01"));
        assertNull(controller.cariById("D01"));
    }

    @Test
    void testHapusBarang_IdNotFound_ThrowsException() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> controller.hapusBarang("ID_GAK_ADA"));
        assertEquals("ID tidak ditemukan: ID_GAK_ADA", exception.getMessage());
    }
}