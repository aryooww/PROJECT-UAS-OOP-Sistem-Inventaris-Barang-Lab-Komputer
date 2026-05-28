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

    public MainMenuGUI() {
        barangService.create(new barangElektronik("Monitor Lab LED", "MN01", 8, "BAIK", "KOMPUTER", "LG", "Hitam"));

        // Konfigurasi jendela utama
        setTitle("Sistem Inventaris & Peminjaman Lab Komputer");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Kelola Barang", buatPanelBarang());
        tabbedPane.addTab("Peminjaman", buatPanelPinjam());
        tabbedPane.addTab("Pengembalian", buatPanelKembali());
        tabbedPane.addTab("Laporan", buatPanelLaporan());

        add(tabbedPane);
    }

    private JPanel buatPanelBarang() {
        // Desain form barang
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtNama = new JTextField();
        JTextField txtKode = new JTextField();
        JTextField txtStok = new JTextField();
        JButton btnSimpan = new JButton("Simpan Barang");

        panel.add(new JLabel("Nama Barang:")); panel.add(txtNama);
        panel.add(new JLabel("Kode Barang:")); panel.add(txtKode);
        panel.add(new JLabel("Jumlah Stok:")); panel.add(txtStok);
        panel.add(new JLabel("")); panel.add(btnSimpan);

        btnSimpan.addActionListener(e -> {
            // Validasi tombol simpan
            try {
                // Validasi data kosong
                if (txtNama.getText().isEmpty() || txtKode.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Data tidak boleh kosong!");
                }

                //Konversi teks ke angka
                int stok = Integer.parseInt(txtStok.getText());
                barangService.create(new barangElektronik(txtNama.getText(), txtKode.getText(), stok, "BAIK", "KOMPUTER", "-", "-"));
                JOptionPane.showMessageDialog(this, "Barang Tersimpan!");

                // Membersihkan form setelah sukses
                txtNama.setText("");
                txtKode.setText("");
                txtStok.setText("");;
            } catch (NumberFormatException ex) {
                // Jika user mengetik huruf di kolom angka (stok)
                JOptionPane.showMessageDialog(this, "Stok harus angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Konflik", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel buatPanelPinjam() {
        // Desain form peminjaman
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtNama = new JTextField();
        JTextField txtNim = new JTextField();
        JTextField txtKode = new JTextField();
        JTextField txtJumlah = new JTextField();
        JButton btnPinjam = new JButton("Proses Pinjam");

        panel.add(new JLabel("Nama Peminjam:")); panel.add(txtNama);
        panel.add(new JLabel("NIM:")); panel.add(txtNim);
        panel.add(new JLabel("Kode Barang:")); panel.add(txtKode);
        panel.add(new JLabel("Jumlah Pinjam:")); panel.add(txtJumlah);
        panel.add(new JLabel("")); panel.add(btnPinjam);

        btnPinjam.addActionListener(e -> {
            // Validasi tombol pinjam
            try {
                int nim = Integer.parseInt(txtNim.getText());
                int jumlah = Integer.parseInt(txtJumlah.getText());

                riwayatpeminjaman riwayat = peminjamanService.pinjamBarang(
                    txtNama.getText(), nim, txtKode.getText(), jumlah, LocalDate.now(), LocalDate.now().plusDays(7), "BAIK"
                );

                JOptionPane.showMessageDialog(this, "Berhasil Pinjam! ID Riwayat: " + riwayat.getIdRiwayat());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "NIM dan Jumlah harus angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel buatPanelKembali() {
        // Desain Form kembali
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtIdRiwayat = new JTextField();
        JTextField txtJumlah = new JTextField();
        JButton btnKembali = new JButton("Proses Pengembalian");

        panel.add(new JLabel("ID Riwayat Peminjaman:")); panel.add(txtIdRiwayat);
        panel.add(new JLabel("Jumlah Dikembalikan:")); panel.add(txtJumlah);
        panel.add(new JLabel("")); panel.add(btnKembali);

        btnKembali.addActionListener(e -> {
            // Validasi tombol kembali
            try {
                int jumlah = Integer.parseInt(txtJumlah.getText());
                peminjamanService.kembalikanBarang(txtIdRiwayat.getText(), jumlah, LocalDate.now(), "BAIK");
                JOptionPane.showMessageDialog(this, "Barang berhasil dikembalikan!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Jumlah harus angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException | IllegalStateException ex) {
               JOptionPane.showMessageDialog(this, ex.getMessage(), "Error Pengembalian", JOptionPane.ERROR_MESSAGE); 
            }
        });
        return panel;
    }

    private JPanel buatPanelLaporan() {
        // Desain panel laporan
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea txtArea = new JTextArea();
        txtArea.setEditable(false); 
        JButton btnRefresh = new JButton("Refresh Laporan");

        btnRefresh.addActionListener(e -> {
            txtArea.setText("--- LAPORAN BARANG ---\n");
            for(barang b : barangService.readAll()) txtArea.append(b.toString() + "\n");

            txtArea.append("\n--- LAPORAN PEMINJAMAN ---\n");
            for(riwayatpeminjaman r : riwayatService.readAll()) txtArea.append(r.tampilkanRiwayat() + "\n");
        });

        panel.add(btnRefresh, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtArea), BorderLayout.CENTER);
        return panel;
    }
}