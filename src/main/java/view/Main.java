package view;

import javax.swing.SwingUtilities;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==================================================");
        System.out.println("      SISTEM INVENTARIS BARANG LAB        ");
        System.out.println("==================================================");
        System.out.println("Pilih mode tampilan aplikasi:");
        System.out.println("1. Mode Terminal / Console (CLI)");
        System.out.println("2. Mode Visual / Jendela (GUI)");
        System.out.print("Masukkan pilihan Anda (1/2): ");
        
        String pilihan = scanner.nextLine();
        
        if (pilihan.equals("1")) {
            System.out.println("\n[!] Memulai Aplikasi Mode Console...\n");
            // Menjalankan MainConsole.java
            MainConsole consoleApp = new MainConsole();
            consoleApp.start();
            
        } else if (pilihan.equals("2")) {
            System.out.println("\n[!] Memulai Aplikasi Mode GUI...");
            // Menjalankan MainGUI.java menggunakan thread SwingUtilities agar aman
            SwingUtilities.invokeLater(() -> {
                new MainGUI();
            });
            
        } else {
            System.out.println("\n[X] Pilihan tidak valid!");
            System.out.println("Menjalankan Mode GUI secara otomatis sebagai default...");
            SwingUtilities.invokeLater(() -> {
                new MainGUI();
            });
        }
        
        // Menutup scanner (Mencegah Resource Leak)
        scanner.close();
    }
}