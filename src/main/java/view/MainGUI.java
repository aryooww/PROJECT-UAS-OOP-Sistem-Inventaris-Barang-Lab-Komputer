package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.InventoryController;
import controller.LaporanController;
import controller.PeminjamanController;
import exception.InputKosongException;
import exception.ValidationException;
import model.*;
import java.awt.*;
import java.util.List;

public class MainGUI extends JFrame {
    private InventoryController inventoryController;
    private PeminjamanController peminjamanController;
    private LaporanController laporanController;

    private JTabbedPane tabbedPane;
    private JTable barangTable, peminjamanTable;
    private DefaultTableModel barangTableModel, peminjamanTableModel;

    public MainGUI() {
        inventoryController = new InventoryController();
        peminjamanController = new PeminjamanController(inventoryController);
        laporanController = new LaporanController();
        seedData();

        setTitle("Sistem Inventaris Barang Lab - Update V2.0 Premium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Kelola Barang", createBarangPanel());
        tabbedPane.addTab("Kelola Peminjaman", createPeminjamanPanel());
        tabbedPane.addTab("Laporan", createLaporanPanel());

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createBarangPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        barangTableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Jumlah", "Lokasi", "Kategori"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        barangTable = new JTable(barangTableModel);
        refreshBarangTable(inventoryController.getAllBarang());

        JScrollPane scrollPane = new JScrollPane(barangTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTambah = new JButton("Tambah Barang");
        JButton btnUpdate = new JButton("Update Barang");
        JButton btnHapus = new JButton("Hapus Barang");
        JButton btnShowAll = new JButton("Semua Barang");
        JButton btnFilterPremium = new JButton("Filter: Premium Saja"); // Tombol Filter Baru

        btnTambah.addActionListener(e -> showTambahBarangDialog());
        btnUpdate.addActionListener(e -> showUpdateBarangDialog());
        btnHapus.addActionListener(e -> hapusBarang());
        btnShowAll.addActionListener(e -> refreshBarangTable(inventoryController.getAllBarang()));
        btnFilterPremium.addActionListener(e -> refreshBarangTable(inventoryController.getFilterBarangPremium()));

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnShowAll);
        buttonPanel.add(btnFilterPremium);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshBarangTable(List<Barang> listBarang) {
        barangTableModel.setRowCount(0);
        for (Barang b : listBarang) {
            barangTableModel.addRow(new Object[]{
                    b.getId(), b.getNama(), b.getJumlah(), b.getLokasi(), b.getKategori()
            });
        }
    }

    private void showTambahBarangDialog() {
        JDialog dialog = new JDialog(this, "Tambah Barang", true);
        dialog.setSize(450, 400);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> cbJenis = new JComboBox<>(new String[]{"Elektronik", "Non-Elektronik", "Premium (Fragile/VIP)"});
        JTextField tfId = new JTextField(15);
        JTextField tfNama = new JTextField(15);
        JTextField tfJumlah = new JTextField(15);
        JTextField tfLokasi = new JTextField(15);
        
        // Komponen Dinamis
        JTextField tfTegangan = new JTextField(15);
        JTextField tfBahan = new JTextField(15);
        JTextField tfPrioritas = new JTextField(15);
        JTextField tfAsuransi = new JTextField(15);

        JPanel dynamicPanel = new JPanel(new GridBagLayout());
        GridBagConstraints dynamicGbc = new GridBagConstraints();
        dynamicGbc.insets = new Insets(5, 5, 5, 5);
        dynamicGbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblTegangan = new JLabel("Tegangan (V):");
        JLabel lblBahan = new JLabel("Bahan:");
        JLabel lblPrioritas = new JLabel("Tingkat Prioritas:");
        JLabel lblAsuransi = new JLabel("Nilai Asuransi (Rp):");
        
        dynamicGbc.gridx = 0; dynamicGbc.gridy = 0; dynamicPanel.add(lblTegangan, dynamicGbc);
        dynamicGbc.gridx = 1; dynamicPanel.add(tfTegangan, dynamicGbc);

        cbJenis.addActionListener(e -> {
            dynamicPanel.removeAll();
            dynamicGbc.gridx = 0; dynamicGbc.gridy = 0;
            
            if (cbJenis.getSelectedIndex() == 0) {
                dynamicPanel.add(lblTegangan, dynamicGbc);
                dynamicGbc.gridx = 1; dynamicPanel.add(tfTegangan, dynamicGbc);
            } else if (cbJenis.getSelectedIndex() == 1) {
                dynamicPanel.add(lblBahan, dynamicGbc);
                dynamicGbc.gridx = 1; dynamicPanel.add(tfBahan, dynamicGbc);
            } else {
                dynamicPanel.add(lblPrioritas, dynamicGbc);
                dynamicGbc.gridx = 1; dynamicPanel.add(tfPrioritas, dynamicGbc);
                dynamicGbc.gridx = 0; dynamicGbc.gridy = 1; dynamicPanel.add(lblAsuransi, dynamicGbc);
                dynamicGbc.gridx = 1; dynamicPanel.add(tfAsuransi, dynamicGbc);
            }
            dynamicPanel.revalidate();
            dynamicPanel.repaint();
            dialog.pack();
        });

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Jenis:"), gbc); gbc.gridx = 1; dialog.add(cbJenis, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("ID Barang:"), gbc); gbc.gridx = 1; dialog.add(tfId, gbc);
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Nama:"), gbc); gbc.gridx = 1; dialog.add(tfNama, gbc);
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Jumlah:"), gbc); gbc.gridx = 1; dialog.add(tfJumlah, gbc);
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Lokasi:"), gbc); gbc.gridx = 1; dialog.add(tfLokasi, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; dialog.add(dynamicPanel, gbc);

        JButton btnSave = new JButton("Simpan");
        btnSave.addActionListener(e -> {
            try {
                int jenis = cbJenis.getSelectedIndex();
                String id = tfId.getText().trim();
                String nama = tfNama.getText().trim();
                int jumlah = Integer.parseInt(tfJumlah.getText().trim());
                String lokasi = tfLokasi.getText().trim();

                if (id.isEmpty() || nama.isEmpty() || lokasi.isEmpty())
                    throw new ValidationException("Semua field dasar harus diisi!");

                if (jenis == 0) { 
                    if (tfTegangan.getText().trim().isEmpty()) throw new ValidationException("Tegangan harus diisi");
                    inventoryController.tambahBarang(new BarangElektronik(id, nama, jumlah, lokasi, Integer.parseInt(tfTegangan.getText().trim())));
                } else if (jenis == 1) { 
                    if (tfBahan.getText().trim().isEmpty()) throw new ValidationException("Bahan harus diisi");
                    inventoryController.tambahBarang(new BarangNonElektronik(id, nama, jumlah, lokasi, tfBahan.getText().trim()));
                } else {
                    String prioritas = tfPrioritas.getText().trim();
                    if (prioritas.isEmpty()) throw new InputKosongException("Tingkat Prioritas wajib diisi untuk barang premium!");
                    double asuransi = Double.parseDouble(tfAsuransi.getText().trim());
                    inventoryController.tambahBarang(new BarangPremium(id, nama, jumlah, lokasi, prioritas, asuransi));
                }
                
                refreshBarangTable(inventoryController.getAllBarang());
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Barang berhasil ditambahkan", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (ValidationException | InputKosongException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error Validasi", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Pastikan input jumlah/angka valid!", "Error Tipe Data", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; dialog.add(btnSave, gbc);
        dialog.setVisible(true);
    }

    // -- (Method updateBarang, hapusBarang, dll di panel lain tetap sama seperti sebelumnya) --
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

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Nama:"), gbc); gbc.gridx = 1; dialog.add(tfNama, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Jumlah:"), gbc); gbc.gridx = 1; dialog.add(tfJumlah, gbc);
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Lokasi:"), gbc); gbc.gridx = 1; dialog.add(tfLokasi, gbc);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> {
            try {
                inventoryController.updateBarang(id, tfNama.getText().trim(), Integer.parseInt(tfJumlah.getText().trim()), tfLokasi.getText().trim());
                refreshBarangTable(inventoryController.getAllBarang());
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Update berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (ValidationException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; dialog.add(btnUpdate, gbc); dialog.setVisible(true);
    }

    private void hapusBarang() {
        int selectedRow = barangTable.getSelectedRow();
        if (selectedRow == -1) return;
        String id = (String) barangTableModel.getValueAt(selectedRow, 0);
        if (JOptionPane.showConfirmDialog(this, "Yakin hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                inventoryController.hapusBarang(id);
                refreshBarangTable(inventoryController.getAllBarang());
            } catch (ValidationException ex) {}
        }
    }

    private JPanel createPeminjamanPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        peminjamanTableModel = new DefaultTableModel(new String[]{"ID Pinjam", "ID Barang", "Peminjam", "NIM", "Jml Pinjam", "Jml Kembali", "Sisa", "Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        peminjamanTable = new JTable(peminjamanTableModel);
        refreshPeminjamanTable();
        panel.add(new JScrollPane(peminjamanTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnPinjam = new JButton("Pinjam Barang");
        JButton btnKembali = new JButton("Kembalikan Barang");

        btnPinjam.addActionListener(e -> showPinjamDialog());
        btnKembali.addActionListener(e -> showKembaliDialog());

        buttonPanel.add(btnPinjam); buttonPanel.add(btnKembali);
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
        dialog.setSize(350, 250); dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(5, 5, 5, 5); gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfIdBarang = new JTextField(15); JTextField tfNama = new JTextField(15);
        JTextField tfNim = new JTextField(15); JTextField tfJumlah = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("ID Barang:"), gbc); gbc.gridx = 1; dialog.add(tfIdBarang, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Nama Peminjam:"), gbc); gbc.gridx = 1; dialog.add(tfNama, gbc);
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("NIM:"), gbc); gbc.gridx = 1; dialog.add(tfNim, gbc);
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Jumlah Pinjam:"), gbc); gbc.gridx = 1; dialog.add(tfJumlah, gbc);

        JButton btnPinjam = new JButton("Pinjam");
        btnPinjam.addActionListener(e -> {
            try {
                peminjamanController.pinjamBarang(tfIdBarang.getText().trim(), tfNama.getText().trim(), tfNim.getText().trim(), Integer.parseInt(tfJumlah.getText().trim()));
                refreshPeminjamanTable(); refreshBarangTable(inventoryController.getAllBarang());
                dialog.dispose();
            } catch (Exception ex) { JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; dialog.add(btnPinjam, gbc); dialog.setVisible(true);
    }

    private void showKembaliDialog() {
        String idPinjam = JOptionPane.showInputDialog(this, "Masukkan ID Peminjaman:");
        if (idPinjam == null || idPinjam.trim().isEmpty()) return;
        Peminjaman p = peminjamanController.cariPeminjamanById(idPinjam);
        if (p == null) return;
        String input = JOptionPane.showInputDialog(this, "Jumlah yang dikembalikan (maks " + p.getSisaBelumKembali() + "):");
        if (input == null) return;
        try {
            peminjamanController.kembalikanBarang(idPinjam, Integer.parseInt(input));
            refreshPeminjamanTable(); refreshBarangTable(inventoryController.getAllBarang());
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private JPanel createLaporanPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea textArea = new JTextArea(); textArea.setEditable(false); textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton btnInventaris = new JButton("Laporan Inventaris");
        JButton btnPeminjaman = new JButton("Laporan Semua Peminjaman");

        btnInventaris.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("=== LAPORAN INVENTARIS BARANG ===\n");
            sb.append(String.format("%-10s %-20s %-10s %-10s %-15s\n", "ID", "Nama", "Jumlah", "Lokasi", "Kategori"));
            for (Barang b : inventoryController.getAllBarang()) {
                sb.append(String.format("%-10s %-20s %-10d %-10s %-15s\n", b.getId(), b.getNama(), b.getJumlah(), b.getLokasi(), b.getKategori()));
            }
            textArea.setText(sb.toString());
        });

        btnPeminjaman.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("=== LAPORAN PEMINJAMAN BARANG ===\n");
            for (Peminjaman p : peminjamanController.getAllPeminjaman()) sb.append(p).append("\n");
            textArea.setText(sb.toString());
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(btnInventaris); btnPanel.add(btnPeminjaman);
        panel.add(btnPanel, BorderLayout.NORTH); panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void seedData() {
        try {
            inventoryController.tambahBarang(new BarangElektronik("EL001", "Multimeter", 5, "Rak A1", 220));
            inventoryController.tambahBarang(new BarangNonElektronik("LAB002", "Tabung Reaksi", 20, "Lemari B2", "Kaca"));
            inventoryController.tambahBarang(new BarangPremium("PRM001", "Lensa Mikroskop", 2, "Lemari VIP", "Fragile", 1500000));
        } catch (ValidationException e) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}