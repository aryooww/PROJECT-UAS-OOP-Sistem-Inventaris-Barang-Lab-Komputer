package controller;

import model.*;
import exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LaporanControllerTest {

    private LaporanController laporanController;
    private List<Barang> daftarBarang;
    private List<Peminjaman> daftarPeminjaman;

    @BeforeEach
    void setUp() {
        laporanController = new LaporanController();
        daftarBarang = new ArrayList<>();
        daftarPeminjaman = new ArrayList<>();
    }

    @Test
    void testLaporanInventaris_WithEmptyList_ShouldNotThrow() {
        assertDoesNotThrow(() -> laporanController.laporanInventaris(daftarBarang, daftarPeminjaman));
    }

    @Test
    void testLaporanInventaris_WithNullList_ShouldNotThrow() {
        assertDoesNotThrow(() -> laporanController.laporanInventaris(null, daftarPeminjaman));
    }

    @Test
    void testLaporanInventaris_WithData_ShouldNotThrow() {
        Barang barang = new BarangElektronik("T01", "TV", 2, "Gudang TV", 220);
        daftarBarang.add(barang);
        assertDoesNotThrow(() -> laporanController.laporanInventaris(daftarBarang, daftarPeminjaman));
    }

    @Test
    void testLaporanPeminjaman_AktifOnly_WithEmptyList_ShouldNotThrow() {
        assertDoesNotThrow(() -> laporanController.laporanPeminjaman(daftarPeminjaman, true));
        assertDoesNotThrow(() -> laporanController.laporanPeminjaman(daftarPeminjaman, false));
    }

    @Test
    void testLaporanPeminjaman_WithData_ShouldNotThrow() {
        // Membuat peminjaman dummy tanpa perlu InventoryController
        Peminjaman p = new Peminjaman("B01", "John", "101", 2);
        daftarPeminjaman.add(p);
        assertDoesNotThrow(() -> laporanController.laporanPeminjaman(daftarPeminjaman, true));
        assertDoesNotThrow(() -> laporanController.laporanPeminjaman(daftarPeminjaman, false));
    }

    @Test
    void testLaporanInventaris_OverloadWithInventoryController_WhenNull() {
        // Overload method menerima InventoryController, jika null harus aman
        assertDoesNotThrow(() -> laporanController.laporanInventaris((InventoryController) null));
    }

    @Test
    void testLaporanInventaris_OverloadWithValidController() throws ValidationException {
        InventoryController inv = new InventoryController();
        Barang b = new BarangElektronik("X01", "Projector", 1, "Ruang Meeting", 110);
        inv.tambahBarang(b);
        // Method ini tidak akan throw exception meskipun param peminjaman null di dalam implementasinya
        assertDoesNotThrow(() -> laporanController.laporanInventaris(inv));
    }
}