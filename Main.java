import view.MainMenuConsole;
import view.MainMenuGUI;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner penyeleksiMode = new Scanner(System.in);

        System.out.println("=================================================");
        System.out.println("[SISTEM] Menyalakan Sistem Inventaris Lab...");
        System.out.println("=================================================");
        System.out.println("PILIH MODE DEMO APLIKASI:");
        System.out.println("1. Jalankan Mode GUI (Jendela Visual Swing)");
        System.out.println("2. Jalankan Mode Terminal (Console Teks / Scanner)");
        System.out.print("Masukkan pilihan Anda (1 / 2): ");

        // Mengamankan menu pemilihan mode
        int modePilihan = 0;
        try {
            modePilihan = Integer.parseInt(penyeleksiMode.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\n[ROBUSTNESS] Input tidak valid! masuk ke Mode Terminal\n");
            modePilihan = 2; 
        } finally {
            penyeleksiMode.close();
        }

        // Percabangan untuk menentukan mode runtime aplikasi
        if (modePilihan == 1) {
            //Mengeksekusi mode GUI
            System.out.println("[SISTEM] Mengaktifkan Thread Jendela GUI Visual...");
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                System.err.println("Gagal memuat tema visual Nimbus.");
            }

            javax.swing.SwingUtilities.invokeLater(() -> {
                MainMenuGUI menuUtama = new MainMenuGUI();
                menuUtama.setVisible(true);
            });

        } else if (modePilihan == 2) {
            // Mengeksekusi mode terminal
            System.out.println("[SISTEM] Mengaktifkan Mode Interaksi Terminal...\n");
            MainMenuConsole menuTerminal = new MainMenuConsole();
            menuTerminal.jalankan();

        } else {
            System.out.println("\n[VALIDASI] Angka pilihan salah! masuk ke Mode Terminal...\n");
            MainMenuConsole menuTerminal = new MainMenuConsole();
            menuTerminal.jalankan();
        }
    }
}