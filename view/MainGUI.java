package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.InventoryController;
import controller.LaporanController;
import controller.PeminjamanController;
import exception.ValidationException;
import model.*;

import java.awt.*;
import java.util.List;

public class MainGUI extends JFrame {
    private InventoryController inventoryController;
    private PeminjamanController peminjamanController;
    private LaporanController laporanController;

    // Komponen UI
    private JTabbedPane tabbedPane;
    private JTable barangTable, peminjamanTable;
    private DefaultTableModel barangTableModel, peminjamanTableModel;

    public MainGUI() {
        // Inisialisasi controller
        inventoryController = new InventoryController();
        peminjamanController = new PeminjamanController(inventoryController);
        laporanController = new LaporanController();
        seedData();

        setTitle("Sistem Inventaris Barang Laboratorium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Membuat tab utama
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Kelola Barang", createBarangPanel());
        tabbedPane.addTab("Kelola Peminjaman", createPeminjamanPanel());
        tabbedPane.addTab("Laporan", createLaporanPanel());

        add(tabbedPane);
        setVisible(true);
    }

    // ================= PANEL KELOLA BARANG =================
    private JPanel createBarangPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabel barang
        barangTableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Jumlah", "Lokasi", "Kategori"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        barangTable = new JTable(barangTableModel);
        refreshBarangTable();

        JScrollPane scrollPane = new JScrollPane(barangTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTambah = new JButton("Tambah Barang");
        JButton btnUpdate = new JButton("Update Barang");
        JButton btnHapus = new JButton("Hapus Barang");
        JButton btnCari = new JButton("Cari Barang");

        btnTambah.addActionListener(e -> showTambahBarangDialog());
        btnUpdate.addActionListener(e -> showUpdateBarangDialog());
        btnHapus.addActionListener(e -> hapusBarang());
        btnCari.addActionListener(e -> cariBarang());

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnCari);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshBarangTable() {
        barangTableModel.setRowCount(0);
        for (Barang b : inventoryController.getAllBarang()) {
            barangTableModel.addRow(new Object[]{
                    b.getId(), b.getNama(), b.getJumlah(), b.getLokasi(), b.getKategori()
            });
        }
    }

    private void showTambahBarangDialog() {
        JDialog dialog = new JDialog(this, "Tambah Barang", true);
        dialog.setSize(400, 350);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> cbJenis = new JComboBox<>(new String[]{"Elektronik", "Non-Elektronik"});
        JTextField tfId = new JTextField(15);
        JTextField tfNama = new JTextField(15);
        JTextField tfJumlah = new JTextField(15);
        JTextField tfLokasi = new JTextField(15);
        JTextField tfTegangan = new JTextField(15);
        JTextField tfBahan = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Jenis:"), gbc);
        gbc.gridx = 1;
        dialog.add(cbJenis, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("ID Barang:"), gbc);
        gbc.gridx = 1;
        dialog.add(tfId, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1;
        dialog.add(tfNama, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Jumlah:"), gbc);
        gbc.gridx = 1;
        dialog.add(tfJumlah, gbc);
        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(new JLabel("Lokasi:"), gbc);
        gbc.gridx = 1;
        dialog.add(tfLokasi, gbc);
        gbc.gridx = 0; gbc.gridy = 5;
        dialog.add(new JLabel("Tegangan (Elektronik):"), gbc);
        gbc.gridx = 1;
        dialog.add(tfTegangan, gbc);
        gbc.gridx = 0; gbc.gridy = 6;
        dialog.add(new JLabel("Bahan (Non-Elektronik):"), gbc);
        gbc.gridx = 1;
        dialog.add(tfBahan, gbc);

        JButton btnSave = new JButton("Simpan");
        btnSave.addActionListener(e -> {
            try {
                int jenis = cbJenis.getSelectedIndex() + 1; // 1=Elektronik, 2=Non
                String id = tfId.getText().trim();
                String nama = tfNama.getText().trim();
                int jumlah = Integer.parseInt(tfJumlah.getText().trim());
                String lokasi = tfLokasi.getText().trim();

                if (id.isEmpty() || nama.isEmpty() || lokasi.isEmpty())
                    throw new ValidationException("Semua field harus diisi");

                if (jenis == 1) {
                    int tegangan = Integer.parseInt(tfTegangan.getText().trim());
                    BarangElektronik b = new BarangElektronik(id, nama, jumlah, lokasi, tegangan);
                    inventoryController.tambahBarang(b);
                } else {
                    String bahan = tfBahan.getText().trim();
                    BarangNonElektronik b = new BarangNonElektronik(id, nama, jumlah, lokasi, bahan);
                    inventoryController.tambahBarang(b);
                }
                refreshBarangTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Barang berhasil ditambahkan", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Jumlah, tegangan harus angka", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        dialog.add(btnSave, gbc);
        dialog.setVisible(true);
    }

    private void showUpdateBarangDialog() {
        int selectedRow = barangTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih barang yang akan diupdate", "Info", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = (String) barangTableModel.getValueAt(selectedRow, 0);
        Barang b = inventoryController.cariById(id);
        if (b == null) return;

        JDialog dialog = new JDialog(this, "Update Barang", true);
        dialog.setSize(350, 250);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfNama = new JTextField(b.getNama(), 15);
        JTextField tfJumlah = new JTextField(String.valueOf(b.getJumlah()), 15);
        JTextField tfLokasi = new JTextField(b.getLokasi(), 15);

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1;
        dialog.add(tfNama, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Jumlah:"), gbc);
        gbc.gridx = 1;
        dialog.add(tfJumlah, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Lokasi:"), gbc);
        gbc.gridx = 1;
        dialog.add(tfLokasi, gbc);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> {
            try {
                String namaBaru = tfNama.getText().trim();
                int jumlahBaru = Integer.parseInt(tfJumlah.getText().trim());
                String lokasiBaru = tfLokasi.getText().trim();
                inventoryController.updateBarang(id, namaBaru, jumlahBaru, lokasiBaru);
                refreshBarangTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Update berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (ValidationException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(btnUpdate, gbc);
        dialog.setVisible(true);
    }

    private void hapusBarang() {
        int selectedRow = barangTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih barang yang akan dihapus", "Info", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = (String) barangTableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus barang " + id + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                inventoryController.hapusBarang(id);
                refreshBarangTable();
                JOptionPane.showMessageDialog(this, "Barang dihapus", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cariBarang() {
        String keyword = JOptionPane.showInputDialog(this, "Masukkan ID atau Nama barang:");
        if (keyword == null || keyword.trim().isEmpty()) return;
        Barang byId = inventoryController.cariById(keyword);
        if (byId != null) {
            JOptionPane.showMessageDialog(this, byId.toString(), "Hasil Cari", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        List<Barang> byNama = inventoryController.cariByNama(keyword);
        if (byNama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ditemukan", "Hasil Cari", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder sb = new StringBuilder("Hasil pencarian:\n");
            for (Barang b : byNama) sb.append(b).append("\n");
            JOptionPane.showMessageDialog(this, sb.toString(), "Hasil Cari", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ================= PANEL KELOLA PEMINJAMAN =================
    private JPanel createPeminjamanPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        peminjamanTableModel = new DefaultTableModel(new String[]{"ID Pinjam", "ID Barang", "Peminjam", "NIM", "Jml Pinjam", "Jml Kembali", "Sisa", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        peminjamanTable = new JTable(peminjamanTableModel);
        refreshPeminjamanTable();
        JScrollPane scrollPane = new JScrollPane(peminjamanTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnPinjam = new JButton("Pinjam Barang");
        JButton btnKembali = new JButton("Kembalikan Barang");

        btnPinjam.addActionListener(e -> showPinjamDialog());
        btnKembali.addActionListener(e -> showKembaliDialog());

        buttonPanel.add(btnPinjam);
        buttonPanel.add(btnKembali);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshPeminjamanTable() {
        peminjamanTableModel.setRowCount(0);
        for (Peminjaman p : peminjamanController.getAllPeminjaman()) {
            peminjamanTableModel.addRow(new Object[]{
                    p.getIdPeminjaman(), p.getIdBarang(), p.getNamaPeminjam(), p.getNim(),
                    p.getJumlahPinjam(), p.getJumlahKembali(), p.getSisaBelumKembali(), p.getStatus()
            });
        }
    }

    private void showPinjamDialog() {
        JDialog dialog = new JDialog(this, "Pinjam Barang", true);
        dialog.setSize(350, 250);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfIdBarang = new JTextField(15);
        JTextField tfNama = new JTextField(15);
        JTextField tfNim = new JTextField(15);
        JTextField tfJumlah = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("ID Barang:"), gbc);
        gbc.gridx = 1; dialog.add(tfIdBarang, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Nama Peminjam:"), gbc);
        gbc.gridx = 1; dialog.add(tfNama, gbc);
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("NIM:"), gbc);
        gbc.gridx = 1; dialog.add(tfNim, gbc);
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Jumlah Pinjam:"), gbc);
        gbc.gridx = 1; dialog.add(tfJumlah, gbc);

        JButton btnPinjam = new JButton("Pinjam");
        btnPinjam.addActionListener(e -> {
            try {
                String idBarang = tfIdBarang.getText().trim();
                String nama = tfNama.getText().trim();
                String nim = tfNim.getText().trim();
                int jumlah = Integer.parseInt(tfJumlah.getText().trim());
                if (idBarang.isEmpty() || nama.isEmpty() || nim.isEmpty())
                    throw new ValidationException("Semua field harus diisi");
                peminjamanController.pinjamBarang(idBarang, nama, nim, jumlah);
                refreshPeminjamanTable();
                refreshBarangTable(); // update stok
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Peminjaman berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (ValidationException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        dialog.add(btnPinjam, gbc);
        dialog.setVisible(true);
    }

    private void showKembaliDialog() {
        String idPinjam = JOptionPane.showInputDialog(this, "Masukkan ID Peminjaman:");
        if (idPinjam == null || idPinjam.trim().isEmpty()) return;
        Peminjaman p = peminjamanController.cariPeminjamanById(idPinjam);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "ID peminjaman tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String input = JOptionPane.showInputDialog(this, "Data: " + p + "\nJumlah yang dikembalikan (maks " + p.getSisaBelumKembali() + "):");
        if (input == null) return;
        try {
            int jml = Integer.parseInt(input);
            peminjamanController.kembalikanBarang(idPinjam, jml);
            refreshPeminjamanTable();
            refreshBarangTable(); // update stok
            JOptionPane.showMessageDialog(this, "Pengembalian berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (ValidationException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ================= PANEL LAPORAN =================
    private JPanel createLaporanPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton btnInventaris = new JButton("Laporan Inventaris");
        JButton btnPeminjaman = new JButton("Laporan Semua Peminjaman");

        btnInventaris.addActionListener(e -> {
            // Tampilkan laporan inventaris
            StringBuilder sb = new StringBuilder();
            laporanController.laporanInventaris(inventoryController.getAllBarang(), peminjamanController.getAllPeminjaman());
            // Karena laporanController mencetak ke console, kita redirect ke textArea. Untuk praktis, kita buat ulang.
            sb.append("=== LAPORAN INVENTARIS BARANG ===\n");
            sb.append(String.format("%-10s %-20s %-10s %-10s %-15s\n", "ID", "Nama", "Jumlah", "Lokasi", "Kategori"));
            for (Barang b : inventoryController.getAllBarang()) {
                sb.append(String.format("%-10s %-20s %-10d %-10s %-15s\n",
                        b.getId(), b.getNama(), b.getJumlah(), b.getLokasi(), b.getKategori()));
            }
            textArea.setText(sb.toString());
        });

        btnPeminjaman.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("=== LAPORAN PEMINJAMAN BARANG ===\n");
            for (Peminjaman p : peminjamanController.getAllPeminjaman()) {
                sb.append(p).append("\n");
            }
            textArea.setText(sb.toString());
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(btnInventaris);
        btnPanel.add(btnPeminjaman);

        panel.add(btnPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void seedData() {
        try {
            inventoryController.tambahBarang(new BarangElektronik("EL001", "Multimeter", 5, "Rak A1", 220));
            inventoryController.tambahBarang(new BarangNonElektronik("LAB002", "Tabung Reaksi", 20, "Lemari B2", "Kaca"));
        } catch (ValidationException e) {
            // ignore
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}