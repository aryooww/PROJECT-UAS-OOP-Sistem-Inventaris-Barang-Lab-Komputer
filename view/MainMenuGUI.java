package view;

import model.*;
import controller.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;

public class MainMenuGUI extends JFrame {
    private BarangMasukKeluarService transaksiService = new BarangMasukKeluarService();
    private BarangService barangService = new BarangService(transaksiService);
    private RiwayatPeminjamanService riwayatService = new RiwayatPeminjamanService();
    private PeminjamanService peminjamanService = new PeminjamanService(barangService, riwayatService);

    // Definisi Warna & Font
    private final Color COLOR_PRIMARY = new Color(44, 62, 80);  
    private final Color COLOR_ACCENT = new Color(52, 152, 219);   
    private final Color COLOR_SUCCESS = new Color(46, 204, 113);   
    private final Color COLOR_BG = new Color(236, 240, 241);     
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);

    public MainMenuGUI() {
        // Inisialisasi data awal
        barangService.create(new barangElektronik("Monitor Lab LED", "MN01", 8, "BAIK", "KOMPUTER", "LG", "Hitam"));

        // Konfigurasi jendela utama
        setTitle("Inventory Lab Manager v1.0");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);

        // Header Aplikasi
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(COLOR_PRIMARY);
        JLabel lblAppTitle = new JLabel("SISTEM INVENTARIS BARANG LAB KOMPUTER");
        lblAppTitle.setForeground(Color.WHITE);
        lblAppTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblAppTitle.setBorder(new EmptyBorder(20, 0, 20, 0));
        headerPanel.add(lblAppTitle);
        add(headerPanel, BorderLayout.NORTH);

        // Konfigurasi TabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabbedPane.setBackground(Color.WHITE);
        
        tabbedPane.addTab(" Kelola Barang  ", buatPanelBarang());
        tabbedPane.addTab(" Peminjaman  ", buatPanelPinjam());
        tabbedPane.addTab(" Pengembalian  ", buatPanelKembali());
        tabbedPane.addTab(" Laporan  ", buatPanelLaporan());

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Helper untuk Styling Button
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    // Helper untuk Container Form agar rapi di tengah
    private JPanel createFormContainer(String title) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(COLOR_BG);
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setBorder(new TitledBorder(new LineBorder(COLOR_PRIMARY, 1), title, 
                       TitledBorder.LEFT, TitledBorder.TOP, FONT_TITLE, COLOR_PRIMARY));
        form.setPreferredSize(new Dimension(500, 350));
        wrapper.add(form);
        return form;
    }

    private JPanel buatPanelBarang() {
        JPanel container = createFormContainer("Input Data Barang Baru");
        container.setLayout(new GridLayout(5, 2, 15, 15));
        container.setBorder(new EmptyBorder(30, 40, 30, 40));

        JTextField txtNama = new JTextField();
        JTextField txtKode = new JTextField();
        JTextField txtStok = new JTextField();
        JButton btnSimpan = new JButton("Simpan ke Database");
        styleButton(btnSimpan, COLOR_SUCCESS);

        container.add(new JLabel("Nama Barang:")); container.add(txtNama);
        container.add(new JLabel("Kode Barang:")); container.add(txtKode);
        container.add(new JLabel("Jumlah Stok:")); container.add(txtStok);
        container.add(new JLabel("")); 
        container.add(btnSimpan);

        btnSimpan.addActionListener(e -> {
            try {
                if (txtNama.getText().isEmpty() || txtKode.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Data tidak boleh kosong!");
                    return;
                }
                int stok = Integer.parseInt(txtStok.getText());
                barangService.create(new barangElektronik(txtNama.getText(), txtKode.getText(), stok, "BAIK", "KOMPUTER", "-", "-"));
                JOptionPane.showMessageDialog(this, "Barang Berhasil Disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                txtNama.setText(""); txtKode.setText(""); txtStok.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Mengembalikan JPanel utama yang membungkus form di tengah
        JPanel outer = new JPanel(new BorderLayout());
        outer.add(container.getParent(), BorderLayout.CENTER);
        return outer;
    }

    private JPanel buatPanelPinjam() {
        JPanel container = createFormContainer("Form Peminjaman Barang");
        container.setLayout(new GridLayout(6, 2, 10, 10));
        container.setBorder(new EmptyBorder(25, 40, 25, 40));

        JTextField txtNama = new JTextField();
        JTextField txtNim = new JTextField();
        JTextField txtKode = new JTextField();
        JTextField txtJumlah = new JTextField();
        JButton btnPinjam = new JButton("Proses Pinjam");
        styleButton(btnPinjam, COLOR_ACCENT);

        container.add(new JLabel("Nama Peminjam:")); container.add(txtNama);
        container.add(new JLabel("NIM:")); container.add(txtNim);
        container.add(new JLabel("Kode Barang:")); container.add(txtKode);
        container.add(new JLabel("Jumlah Pinjam:")); container.add(txtJumlah);
        container.add(new JLabel("")); container.add(btnPinjam);

        btnPinjam.addActionListener(e -> {
            try {
                int nim = Integer.parseInt(txtNim.getText());
                int jumlah = Integer.parseInt(txtJumlah.getText());
                riwayatpeminjaman r = peminjamanService.pinjamBarang(txtNama.getText(), nim, txtKode.getText(), jumlah, LocalDate.now(), LocalDate.now().plusDays(7), "BAIK");
                JOptionPane.showMessageDialog(this, "Peminjaman Berhasil!\nID: " + r.getIdRiwayat());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel outer = new JPanel(new BorderLayout());
        outer.add(container.getParent(), BorderLayout.CENTER);
        return outer;
    }

    private JPanel buatPanelKembali() {
        JPanel container = createFormContainer("Form Pengembalian Barang");
        container.setLayout(new GridLayout(4, 2, 15, 15));
        container.setBorder(new EmptyBorder(40, 40, 40, 40));

        JTextField txtIdRiwayat = new JTextField();
        JTextField txtJumlah = new JTextField();
        JButton btnKembali = new JButton("Kembalikan Barang");
        styleButton(btnKembali, COLOR_PRIMARY);

        container.add(new JLabel("ID Riwayat:")); container.add(txtIdRiwayat);
        container.add(new JLabel("Jumlah Kembali:")); container.add(txtJumlah);
        container.add(new JLabel("")); container.add(btnKembali);

        btnKembali.addActionListener(e -> {
            try {
                int jumlah = Integer.parseInt(txtJumlah.getText());
                peminjamanService.kembalikanBarang(txtIdRiwayat.getText(), jumlah, LocalDate.now(), "BAIK");
                JOptionPane.showMessageDialog(this, "Data Berhasil Diupdate!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel outer = new JPanel(new BorderLayout());
        outer.add(container.getParent(), BorderLayout.CENTER);
        return outer;
    }

    private JPanel buatPanelLaporan() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextArea txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        txtArea.setBackground(new Color(253, 253, 253));
        
        JButton btnRefresh = new JButton("Perbarui Data Laporan");
        styleButton(btnRefresh, COLOR_PRIMARY);

        Runnable loadData = () -> {
            txtArea.setText("====== LAPORAN INVENTARIS LAB ======\n\n");
            txtArea.append("[DATA BARANG]\n");
            for(barang b : barangService.readAll()) txtArea.append(" > " + b.toString() + "\n");
            
            txtArea.append("\n[DATA PEMINJAMAN AKTIF]\n");
            for(riwayatpeminjaman r : riwayatService.readAll()) txtArea.append(" > " + r.tampilkanRiwayat() + "\n");
        };

        btnRefresh.addActionListener(e -> loadData.run());
        
        // [ROBUSTNESS] Auto update saat tab diklik
        panel.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent event) { loadData.run(); }
            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {}
            public void ancestorMoved(javax.swing.event.AncestorEvent event) {}
        });

        panel.add(btnRefresh, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtArea), BorderLayout.CENTER);
        return panel;
    }
}