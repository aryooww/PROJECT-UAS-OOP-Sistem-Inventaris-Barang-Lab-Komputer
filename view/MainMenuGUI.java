package view;

import model.*;
import controller.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MainMenuGUI extends JFrame {
    private BarangMasukKeluarService transaksiService = new BarangMasukKeluarService();
    private BarangService barangService = new BarangService(transaksiService);
    private RiwayatPeminjamanService riwayatService = new RiwayatPeminjamanService();
    private PeminjamanService peminjamanService = new PeminjamanService(barangService, riwayatService);

    private final String[] PILIHAN_KONDISI = {"BAIK", "KURANG BAIK", "RUSAK"};

    public MainMenuGUI() {
        // Data Dummy Awal
        barangService.create(new barangElektronik("Monitor Lab LED", "MN01", 8, "BAIK", "KOMPUTER", "LG", "Hitam"));

        // Konfigurasi Frame Utama
        setTitle("Sistem Inventaris & Peminjaman Lab Komputer");
        setSize(900, 650); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        tabbedPane.addTab("Kelola Barang", buatPanelKelolaBarang());
        tabbedPane.addTab("Peminjaman & Pengembalian", buatPanelPeminjaman());
        tabbedPane.addTab("Laporan Transaksi", buatPanelLaporan());

        add(tabbedPane);
    }

    // KELOLA BARANG
    private JPanel buatPanelKelolaBarang() {
        JPanel panelUtama = new JPanel(new BorderLayout(15, 15));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel Kiri: Form Tambah & Hapus Barang
        JPanel panelKiri = new JPanel(new GridLayout(2, 1, 10, 10));
        panelKiri.setPreferredSize(new Dimension(380, 0));

        // Form Tambah
        JPanel formTambah = new JPanel(new GridLayout(5, 2, 5, 10));
        formTambah.setBorder(BorderFactory.createTitledBorder("Tambah Barang Baru"));
        JTextField txtNama = new JTextField();
        JTextField txtKode = new JTextField();
        JTextField txtStok = new JTextField();
        JComboBox<String> cbKondisi = new JComboBox<>(PILIHAN_KONDISI); // Dropdown Kondisi
        JButton btnSimpan = new JButton("Simpan Barang"); // Sudah diperbaiki (Tidak Merah)

        formTambah.add(new JLabel("Nama Barang:")); formTambah.add(txtNama);
        formTambah.add(new JLabel("Kode Barang:")); formTambah.add(txtKode);
        formTambah.add(new JLabel("Jumlah Stok:")); formTambah.add(txtStok);
        formTambah.add(new JLabel("Kondisi Awal:")); formTambah.add(cbKondisi);
        formTambah.add(new JLabel("")); formTambah.add(btnSimpan);

        // Form Hapus
        JPanel formHapus = new JPanel(new GridLayout(3, 2, 5, 10));
        formHapus.setBorder(BorderFactory.createTitledBorder("Hapus Barang"));
        JTextField txtKodeHapus = new JTextField();
        JButton btnHapus = new JButton("Hapus Barang");
        
        formHapus.add(new JLabel("Masukkan Kode:")); formHapus.add(txtKodeHapus);
        formHapus.add(new JLabel("")); formHapus.add(btnHapus);

        panelKiri.add(formTambah);
        panelKiri.add(formHapus);

        // Panel Kanan: Daftar Semua Barang
        JPanel panelKanan = new JPanel(new BorderLayout());
        panelKanan.setBorder(BorderFactory.createTitledBorder("Daftar Semua Barang"));
        JTextArea txtAreaDaftar = new JTextArea();
        txtAreaDaftar.setEditable(false);
        txtAreaDaftar.setFont(new Font("Consolas", Font.PLAIN, 12));
        panelKanan.add(new JScrollPane(txtAreaDaftar), BorderLayout.CENTER);

        // Fungsi Auto-refresh Daftar
        Runnable muatDaftar = () -> {
            txtAreaDaftar.setText("");
            for(barang b : barangService.readAll()) {
                txtAreaDaftar.append(b.toString() + "\n");
            }
        };

        // Aksi Simpan Barang 
        btnSimpan.addActionListener(e -> {
            try {
                // Robustness: Validasi Data Kosong
                if (txtNama.getText().isEmpty() || txtKode.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Data tidak boleh kosong!"); return;
                }
                
                // Try-Catch Handle: Format Angka
                int stok = Integer.parseInt(txtStok.getText());
                String kondisiDipilih = cbKondisi.getSelectedItem().toString();
                
                barangService.create(new barangElektronik(txtNama.getText(), txtKode.getText(), stok, kondisiDipilih, "KOMPUTER", "-", "-"));
                JOptionPane.showMessageDialog(this, "Barang Tersimpan dengan Kondisi: " + kondisiDipilih);
                
                // Reset Form
                txtNama.setText(""); txtKode.setText(""); txtStok.setText("");
                cbKondisi.setSelectedIndex(0); 
                muatDaftar.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Aksi Hapus Barang
        btnHapus.addActionListener(e -> {
            try {
                String kode = txtKodeHapus.getText();
                if (kode.isEmpty()) { JOptionPane.showMessageDialog(this, "Kode tidak boleh kosong!"); return; }
                
                barangService.delete(kode);
                JOptionPane.showMessageDialog(this, "Barang Dihapus!");
                txtKodeHapus.setText("");
                muatDaftar.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal Hapus: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelUtama.add(panelKiri, BorderLayout.WEST);
        panelUtama.add(panelKanan, BorderLayout.CENTER);
        
        muatDaftar.run(); 
        return panelUtama;
    }

    // PEMINJAMAN & PENGEMBALIAN 
    private JPanel buatPanelPeminjaman() {
        JPanel panelUtama = new JPanel(new BorderLayout(15, 15));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel Kiri: Form Peminjaman & Pengembalian
        JPanel panelKiri = new JPanel(new GridLayout(2, 1, 10, 10));
        panelKiri.setPreferredSize(new Dimension(380, 0));

        // Form Pinjam
        JPanel formPinjam = new JPanel(new GridLayout(6, 2, 5, 5));
        formPinjam.setBorder(BorderFactory.createTitledBorder("Form Peminjaman"));
        JTextField txtNama = new JTextField();
        JTextField txtNim = new JTextField();
        JTextField txtKode = new JTextField();
        JTextField txtJumlah = new JTextField();
        JComboBox<String> cbKondisiPinjam = new JComboBox<>(PILIHAN_KONDISI);
        JButton btnPinjam = new JButton("Proses Pinjam");

        formPinjam.add(new JLabel("Nama:")); formPinjam.add(txtNama);
        formPinjam.add(new JLabel("NIM:")); formPinjam.add(txtNim);
        formPinjam.add(new JLabel("Kode Barang:")); formPinjam.add(txtKode);
        formPinjam.add(new JLabel("Jumlah:")); formPinjam.add(txtJumlah);
        formPinjam.add(new JLabel("Kondisi Pinjam:")); formPinjam.add(cbKondisiPinjam);
        formPinjam.add(new JLabel("")); formPinjam.add(btnPinjam);

        // Form Kembali
        JPanel formKembali = new JPanel(new GridLayout(4, 2, 5, 5));
        formKembali.setBorder(BorderFactory.createTitledBorder("Form Pengembalian"));
        JTextField txtIdRiwayat = new JTextField();
        JTextField txtJumlahKembali = new JTextField();
        JComboBox<String> cbKondisiKembali = new JComboBox<>(PILIHAN_KONDISI);
        JButton btnKembali = new JButton("Kembalikan");

        formKembali.add(new JLabel("ID Riwayat:")); formKembali.add(txtIdRiwayat);
        formKembali.add(new JLabel("Jml Kembali:")); formKembali.add(txtJumlahKembali);
        formKembali.add(new JLabel("Kondisi Saat Kembali:")); formKembali.add(cbKondisiKembali);
        formKembali.add(new JLabel("")); formKembali.add(btnKembali);

        panelKiri.add(formPinjam);
        panelKiri.add(formKembali);

        // Panel Kanan: Riwayat Peminjaman
        JPanel panelKanan = new JPanel(new BorderLayout());
        panelKanan.setBorder(BorderFactory.createTitledBorder("Riwayat Peminjaman"));
        JTextArea txtAreaRiwayat = new JTextArea();
        txtAreaRiwayat.setEditable(false);
        txtAreaRiwayat.setFont(new Font("Consolas", Font.PLAIN, 12));
        panelKanan.add(new JScrollPane(txtAreaRiwayat), BorderLayout.CENTER);

        // Fungsi Refresh Riwayat
        Runnable muatRiwayat = () -> {
            txtAreaRiwayat.setText("");
            for(riwayatpeminjaman r : riwayatService.readAll()) {
                txtAreaRiwayat.append(r.tampilkanRiwayat() + "\n");
            }
        };

        // Aksi Proses Pinjam
        btnPinjam.addActionListener(e -> {
            try {
                int nim = Integer.parseInt(txtNim.getText());
                int jumlah = Integer.parseInt(txtJumlah.getText());
                String kondisiPinjam = cbKondisiPinjam.getSelectedItem().toString();

                riwayatpeminjaman riwayat = peminjamanService.pinjamBarang(
                    txtNama.getText(), nim, txtKode.getText(), jumlah, LocalDate.now(), LocalDate.now().plusDays(7), kondisiPinjam
                );
                
                JOptionPane.showMessageDialog(this, "Berhasil Pinjam! ID: " + riwayat.getIdRiwayat());
                txtNama.setText(""); txtNim.setText(""); txtKode.setText(""); txtJumlah.setText("");
                cbKondisiPinjam.setSelectedIndex(0);
                muatRiwayat.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Aksi Proses Kembali
        btnKembali.addActionListener(e -> {
            try {
                int jumlah = Integer.parseInt(txtJumlahKembali.getText());
                String kondisiKembali = cbKondisiKembali.getSelectedItem().toString();

                peminjamanService.kembalikanBarang(txtIdRiwayat.getText(), jumlah, LocalDate.now(), kondisiKembali);
                
                JOptionPane.showMessageDialog(this, "Barang dikembalikan dengan kondisi: " + kondisiKembali);
                txtIdRiwayat.setText(""); txtJumlahKembali.setText("");
                cbKondisiKembali.setSelectedIndex(0);
                muatRiwayat.run();
            } catch (Exception ex) {
               JOptionPane.showMessageDialog(this, ex.getMessage(), "Error Pengembalian", JOptionPane.ERROR_MESSAGE); 
            }
        });

        panelUtama.add(panelKiri, BorderLayout.WEST);
        panelUtama.add(panelKanan, BorderLayout.CENTER);
        
        muatRiwayat.run(); 
        return panelUtama;
    }

    // LAPORAN
    private JPanel buatPanelLaporan() {
        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextArea txtLaporan = new JTextArea();
        txtLaporan.setEditable(false); 
        txtLaporan.setFont(new Font("Consolas", Font.PLAIN, 13));
        
        JButton btnRefresh = new JButton("Perbarui Log Laporan Transaksi");

        btnRefresh.addActionListener(e -> {
            txtLaporan.setText("=== LAPORAN TRANSAKSI (MASUK / KELUAR) ===\n\n");
            try {
                for(Object log : transaksiService.readAll()) { 
                    txtLaporan.append(log.toString() + "\n");
                }
            } catch (Exception ex) {
                txtLaporan.append("Menunggu implementasi data log transaksi dari Role 2...\n");
            }
        });

        panelUtama.add(btnRefresh, BorderLayout.NORTH);
        panelUtama.add(new JScrollPane(txtLaporan), BorderLayout.CENTER);
        
        return panelUtama;
    }
}