package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.InventoryController;
import controller.LaporanController;
import controller.PeminjamanController;
import exception.KerapuhanTidakValidException;
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

        setTitle("Sistem Inventaris Barang Laboratorium - Versi 2.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        // Membuat tab utama
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Kelola Barang", createBarangPanel());
        tabbedPane.addTab("Kelola Peminjaman", createPeminjamanPanel());
        tabbedPane.addTab("Laporan", createLaporanPanel());
        
        // === C(a). TAB KHUSUS untuk fitur Barang Rentan (Sorting) ===
        tabbedPane.addTab("Barang Rentan (Prioritas)", createBarangRentanPanel());

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

    // === A(a), A(b), A(c). DIALOG TAMBAH BARANG (termasuk opsi BarangRentan) ===
    private void showTambahBarangDialog() {
        JDialog dialog = new JDialog(this, "Tambah Barang", true);
        dialog.setSize(450, 450);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // === C(a). OPSI KATEGORI termasuk Barang Rentan ===
        JComboBox<String> cbJenis = new JComboBox<>(new String[]{"Elektronik", "Non-Elektronik", "Barang Rentan (Fragile)"});
        JTextField tfId = new JTextField(15);
        JTextField tfNama = new JTextField(15);
        JTextField tfJumlah = new JTextField(15);
        JTextField tfLokasi = new JTextField(15);
        JTextField tfTegangan = new JTextField(15);
        JTextField tfBahan = new JTextField(15);
        JTextField tfKerapuhan = new JTextField(15); // === A(b). Field untuk atribut unik

        // Panel untuk field dinamis
        JPanel dynamicPanel = new JPanel(new GridBagLayout());
        GridBagConstraints dynamicGbc = new GridBagConstraints();
        dynamicGbc.insets = new Insets(5, 5, 5, 5);
        dynamicGbc.fill = GridBagConstraints.HORIZONTAL;
        dynamicGbc.gridx = 0;
        dynamicGbc.gridy = 0;
        
        JLabel lblTegangan = new JLabel("Tegangan (Volt) - Elektronik:");
        JLabel lblBahan = new JLabel("Bahan - Non-Elektronik:");
        JLabel lblKerapuhan = new JLabel("Tingkat Kerapuhan (1-10, 10=sangat rapuh):");
        
        // Default tampilkan tegangan
        dynamicPanel.add(lblTegangan, dynamicGbc);
        dynamicGbc.gridx = 1;
        dynamicPanel.add(tfTegangan, dynamicGbc);
        
        // Sembunyikan field lainnya terlebih dahulu
        lblBahan.setVisible(false);
        tfBahan.setVisible(false);
        lblKerapuhan.setVisible(false);
        tfKerapuhan.setVisible(false);

        // Listener untuk combo box
        cbJenis.addActionListener(e -> {
            dynamicPanel.removeAll();
            dynamicGbc.gridx = 0;
            dynamicGbc.gridy = 0;
            
            if (cbJenis.getSelectedIndex() == 0) { // Elektronik
                lblTegangan.setVisible(true);
                tfTegangan.setVisible(true);
                lblBahan.setVisible(false);
                tfBahan.setVisible(false);
                lblKerapuhan.setVisible(false);
                tfKerapuhan.setVisible(false);
                
                dynamicPanel.add(lblTegangan, dynamicGbc);
                dynamicGbc.gridx = 1;
                dynamicPanel.add(tfTegangan, dynamicGbc);
            } else if (cbJenis.getSelectedIndex() == 1) { // Non-Elektronik
                lblTegangan.setVisible(false);
                tfTegangan.setVisible(false);
                lblBahan.setVisible(true);
                tfBahan.setVisible(true);
                lblKerapuhan.setVisible(false);
                tfKerapuhan.setVisible(false);
                
                dynamicPanel.add(lblBahan, dynamicGbc);
                dynamicGbc.gridx = 1;
                dynamicPanel.add(tfBahan, dynamicGbc);
            } else { // === A(a). Barang Rentan (Fragile) ===
                lblTegangan.setVisible(false);
                tfTegangan.setVisible(false);
                lblBahan.setVisible(false);
                tfBahan.setVisible(false);
                lblKerapuhan.setVisible(true);
                tfKerapuhan.setVisible(true);
                
                dynamicPanel.add(lblKerapuhan, dynamicGbc);
                dynamicGbc.gridx = 1;
                dynamicPanel.add(tfKerapuhan, dynamicGbc);
            }
            
            dynamicPanel.revalidate();
            dynamicPanel.repaint();
            dialog.pack();
        });

        // Menambahkan komponen ke dialog utama
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Jenis Barang:"), gbc);
        gbc.gridx = 1;
        dialog.add(cbJenis, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("ID Barang:"), gbc);
        gbc.gridx = 1;
        dialog.add(tfId, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Nama Barang:"), gbc);
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
        
        // Menambahkan panel dinamis
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        dialog.add(dynamicPanel, gbc);

        JButton btnSave = new JButton("Simpan");
        
        // === C(c). TRY-CATCH untuk validasi input ===
        btnSave.addActionListener(e -> {
            try {
                int jenis = cbJenis.getSelectedIndex();
                String id = tfId.getText().trim();
                String nama = tfNama.getText().trim();
                int jumlah = Integer.parseInt(tfJumlah.getText().trim());
                String lokasi = tfLokasi.getText().trim();

                if (id.isEmpty() || nama.isEmpty() || lokasi.isEmpty())
                    throw new ValidationException("Semua field harus diisi");

                if (jenis == 0) { // Elektronik
                    if (tfTegangan.getText().trim().isEmpty())
                        throw new ValidationException("Tegangan harus diisi");
                    int tegangan = Integer.parseInt(tfTegangan.getText().trim());
                    BarangElektronik b = new BarangElektronik(id, nama, jumlah, lokasi, tegangan);
                    inventoryController.tambahBarang(b);
                } else if (jenis == 1) { // Non-Elektronik
                    if (tfBahan.getText().trim().isEmpty())
                        throw new ValidationException("Bahan harus diisi");
                    String bahan = tfBahan.getText().trim();
                    BarangNonElektronik b = new BarangNonElektronik(id, nama, jumlah, lokasi, bahan);
                    inventoryController.tambahBarang(b);
                } else { // === A(a). Barang Rentan dengan validasi custom exception ===
                    if (tfKerapuhan.getText().trim().isEmpty())
                        throw new ValidationException("Tingkat kerapuhan harus diisi");
                    int kerapuhan = Integer.parseInt(tfKerapuhan.getText().trim());
                    
                    // === C(c). Validasi dengan CUSTOM EXCEPTION ===
                    if (kerapuhan < 1 || kerapuhan > 10) {
                        throw new KerapuhanTidakValidException("Tingkat kerapuhan harus antara 1 dan 10!");
                    }
                    
                    // === A(a). Membuat objek subclass BarangRentan ===
                    BarangRentan b = new BarangRentan(id, nama, jumlah, lokasi, kerapuhan);
                    inventoryController.tambahBarang(b);
                }
                refreshBarangTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Barang berhasil ditambahkan", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (KerapuhanTidakValidException ex) {
                // === C(c). Menangkap custom exception ===
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Validasi Gagal", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Jumlah, tegangan, dan kerapuhan harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0; gbc.gridy = 6;
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
                refreshBarangTable();
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
            refreshBarangTable();
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
            StringBuilder sb = new StringBuilder();
            sb.append("=== LAPORAN INVENTARIS BARANG ===\n");
            sb.append(String.format("%-10s %-20s %-10s %-10s %-20s\n", "ID", "Nama", "Jumlah", "Lokasi", "Kategori"));
            sb.append("--------------------------------------------------------------------\n");
            for (Barang b : inventoryController.getAllBarang()) {
                sb.append(String.format("%-10s %-20s %-10d %-10s %-20s\n",
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

    // ================= PANEL BARANG RENTAN (FITUR SORTING VERSI 2.0) =================
    // === C(a). PANEL KHUSUS untuk menampilkan fitur sorting barang rentan ===
    private JPanel createBarangRentanPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Label judul
        JLabel titleLabel = new JLabel("DAFTAR BARANG RENTAN (TERURUT BERDASARKAN TINGKAT KERAPUHAN TERTINGGI)");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Tabel untuk menampilkan barang rentan yang sudah di-sort
        DefaultTableModel fragileTableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Jumlah", "Lokasi", "Kategori", "Tingkat Kerapuhan"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable fragileTable = new JTable(fragileTableModel);
        JScrollPane scrollPane = new JScrollPane(fragileTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // === B(b). Tombol untuk refresh dan menampilkan hasil sorting ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnRefresh = new JButton("Refresh / Urutkan Barang Rentan");
        
        btnRefresh.addActionListener(e -> {
            // === B(b). Memanggil method sorting dari controller ===
            List<Barang> sortedFragile = inventoryController.getBarangRentanSortedByKerapuhan();
            fragileTableModel.setRowCount(0);
            
            if (sortedFragile.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Belum ada barang rentan. Silakan tambahkan melalui tab 'Kelola Barang'", 
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Barang b : sortedFragile) {
                    BarangRentan fr = (BarangRentan) b;
                    fragileTableModel.addRow(new Object[]{
                            fr.getId(), 
                            fr.getNama(), 
                            fr.getJumlah(), 
                            fr.getLokasi(), 
                            fr.getKategori(),
                            fr.getTingkatKerapuhan() + "/10"
                    });
                }
                // Memberi warna pada baris dengan kerapuhan tinggi (prioritas)
                fragileTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, 
                            boolean isSelected, boolean hasFocus, int row, int column) {
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        int kerapuhan = 0;
                        try {
                            String kerapuhanStr = (String) table.getValueAt(row, 5);
                            kerapuhan = Integer.parseInt(kerapuhanStr.replace("/10", ""));
                        } catch (Exception ex) {}
                        
                        if (!isSelected) {
                            if (kerapuhan >= 8) {
                                c.setBackground(new Color(255, 200, 200)); // Merah muda (sangat rapuh)
                            } else if (kerapuhan >= 5) {
                                c.setBackground(new Color(255, 255, 200)); // Kuning muda (cukup rapuh)
                            } else {
                                c.setBackground(Color.WHITE);
                            }
                        }
                        return c;
                    }
                });
                fragileTable.repaint();
                
                JOptionPane.showMessageDialog(panel, "Menampilkan " + sortedFragile.size() + " barang rentan yang diurutkan dari yang paling rapuh", 
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        buttonPanel.add(btnRefresh);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private void seedData() {
        try {
            inventoryController.tambahBarang(new BarangElektronik("EL001", "Multimeter", 5, "Rak A1", 220));
            inventoryController.tambahBarang(new BarangNonElektronik("LAB002", "Tabung Reaksi", 20, "Lemari B2", "Kaca"));
            // === SEED DATA untuk demo BarangRentan ===
            inventoryController.tambahBarang(new BarangRentan("FRG001", "Gelas Ukur", 10, "Rak Kaca", 8));
            inventoryController.tambahBarang(new BarangRentan("FRG002", "Termometer", 5, "Lemari A", 6));
            inventoryController.tambahBarang(new BarangRentan("FRG003", "Beaker Glass", 7, "Rak Kaca", 9));
        } catch (ValidationException e) {
            // ignore
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}