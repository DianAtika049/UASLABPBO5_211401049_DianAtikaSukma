/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.menuperpustakaan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Class buku
class Buku {
    private String namaBuku;
    private String namaPenulis;

    public Buku(String namaBuku, String namaPenulis) {
        this.namaBuku = namaBuku;
        this.namaPenulis = namaPenulis;
    }

    public String getNamaBuku() {
        return namaBuku;
    }

    public String getNamaPenulis() {
        return namaPenulis;
    }
}

// Class buku yang dipinjam
class BukuDipinjam extends Buku {
    private String namaPeminjam;
    private String tanggalPeminjaman;
    private String batasPengembalian;

    public BukuDipinjam(String namaBuku, String namaPenulis, String namaPeminjam, String tanggalPeminjaman, String batasPengembalian) {
        super(namaBuku, namaPenulis);
        this.namaPeminjam = namaPeminjam;
        this.tanggalPeminjaman = tanggalPeminjaman;
        this.batasPengembalian = batasPengembalian;
    }

    public String getNamaPeminjam() {
        return namaPeminjam;
    }

    public String getTanggalPeminjaman() {
        return tanggalPeminjaman;
    }

    public String getBatasPengembalian() {
        return batasPengembalian;
    }
}

// Class perpustakaan
class Perpustakaan {
    private ArrayList<Buku> daftarBuku;
    private ArrayList<BukuDipinjam> daftarBukuDipinjam;

    public Perpustakaan() {
        daftarBuku = new ArrayList<>();
        daftarBukuDipinjam = new ArrayList<>();
    }

    public void tambahBuku(String namaBuku, String namaPenulis) {
        Buku buku = new Buku(namaBuku, namaPenulis);
        daftarBuku.add(buku);
        System.out.println("NOTICE : Buku berhasil ditambahkan");
    }

    public boolean cekPersediaanBuku(String namaBuku, String namaPenulis) {
        for (Buku buku : daftarBuku) {
            if (buku.getNamaBuku().equals(namaBuku) && buku.getNamaPenulis().equals(namaPenulis)) {
                return true;
            }
        }
        return false;
    }

    public void pinjamBuku(String namaBuku, String namaPenulis, String namaPeminjam, String tanggalPeminjaman, String batasPengembalian) {
        boolean bukuTersedia = cekPersediaanBuku(namaBuku, namaPenulis);
        if (bukuTersedia) {
            // Validasi format tanggal
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(tanggalPeminjaman);
                dateFormat.parse(batasPengembalian);
                BukuDipinjam bukuDipinjam = new BukuDipinjam(namaBuku, namaPenulis, namaPeminjam, tanggalPeminjaman, batasPengembalian);
                daftarBukuDipinjam.add(bukuDipinjam);
                daftarBuku.removeIf(buku -> buku.getNamaBuku().equals(namaBuku) && buku.getNamaPenulis().equals(namaPenulis));
                System.out.println("\nNOTICE : Buku berhasil dipinjam");
            } catch (ParseException e) {
                System.out.println("\nALERT : Harap masukkan tanggal dengan format dd/MM/yyyy");
            }
        } else {
            System.out.println("\nALERT : Buku tidak tersedia di perpustakaan");
        }
    }

    public void kembalikanBuku(String namaBuku, String namaPenulis) {
        boolean bukuTersedia = cekPersediaanBuku(namaBuku, namaPenulis);
        if (!bukuTersedia) {
            for (int i = 0; i < daftarBukuDipinjam.size(); i++) {
                BukuDipinjam bukuDipinjam = daftarBukuDipinjam.get(i);
                if (bukuDipinjam.getNamaBuku().equals(namaBuku) && bukuDipinjam.getNamaPenulis().equals(namaPenulis)) {
                    daftarBukuDipinjam.remove(i);
                    daftarBuku.add(new Buku(namaBuku, namaPenulis));
                    System.out.println("\nNOTICE : Buku berhasil dikembalikan");
                    return;
                }
            }
            System.out.println("\nALERT : Buku yang dikembalikan bukan buku dari perpustakaan ini");
        } else {
            System.out.println("\nNOTICE : Buku tersebut masih tersedia di perpustakaan");
        }
    }

    public void tampilkanBukuDipinjam() {
        if (daftarBukuDipinjam.isEmpty()) {
            System.out.println("NOTICE : Tidak ada buku yang dipinjam");
        } else {
            System.out.println("Daftar Buku yang Sedang Dipinjam: \n");
            for (int i = 0; i < daftarBukuDipinjam.size(); i++) {
                BukuDipinjam bukuDipinjam = daftarBukuDipinjam.get(i);
                System.out.println("Buku : " + (i + 1));
                System.out.println("Nama Buku: " + bukuDipinjam.getNamaBuku());
                System.out.println("Nama Penulis: " + bukuDipinjam.getNamaPenulis());
                System.out.println("Nama Peminjam: " + bukuDipinjam.getNamaPeminjam());
                System.out.println("Tanggal Peminjaman: " + bukuDipinjam.getTanggalPeminjaman());
                System.out.println("Batas Pengembalian: " + bukuDipinjam.getBatasPengembalian());
                System.out.println();
            }
        }
    }

    public void tampilkanBukuTersedia() {
        if (daftarBuku.isEmpty()) {
            System.out.println("NOTICE : Tidak ada buku yang tersedia di perpustakaan");
        } else {
            System.out.println("Daftar Buku yang Tersedia: \n");
            for (int i = 0; i < daftarBuku.size(); i++) {
                Buku buku = daftarBuku.get(i);
                System.out.println("Buku : " + (i + 1));
                System.out.println("Nama Buku: " + buku.getNamaBuku());
                System.out.println("Nama Penulis: " + buku.getNamaPenulis());
                System.out.println();
            }
        }
    }
}

public class MenuPerpustakaan{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Perpustakaan perpustakaan = new Perpustakaan();

        int menu = -1;

        while (menu != 0) {
            System.out.println("========== PERPUSTAKAAN KIYOWO ==========");
            System.out.println("||         1. Peminjaman Buku          ||");
            System.out.println("||        2. Pengembalian Buku         ||");
            System.out.println("|| 3. Daftar Buku yang Sedang Dipinjam ||");
            System.out.println("||     4. Daftar Buku yang Tersedia    ||");
            System.out.println("||      5. Tambah Persediaan Buku      ||");
            System.out.println("||              0. Keluar              ||");
            System.out.println("========================================");
            System.out.print("Pilih menu (0-5): ");
            
        try {
            menu = scanner.nextInt();
            scanner.nextLine();

            System.out.println();
            
                switch (menu) {
                    case 1:
                        System.out.print("Peminjaman Buku\n");
                        System.out.print("Masukkan nama buku: ");
                        String namaBuku = scanner.nextLine();
                        System.out.print("Masukkan nama penulis buku: ");
                        String namaPenulis = scanner.nextLine();
                        boolean bukuTersedia = perpustakaan.cekPersediaanBuku(namaBuku, namaPenulis);
                        if (bukuTersedia) {
                            System.out.print("Masukkan nama peminjam buku: ");
                            String namaPeminjam = scanner.nextLine();
                            System.out.print("Masukkan tanggal peminjaman buku (dd/MM/yyyy): ");
                            String tanggalPeminjaman = scanner.nextLine();
                            System.out.print("Masukkan batas waktu pengembalian buku (dd/MM/yyyy): ");
                            String batasPengembalian = scanner.nextLine();

                            // Validasi format tanggal
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            dateFormat.setLenient(false);
                            try {
                                dateFormat.parse(tanggalPeminjaman);
                                dateFormat.parse(batasPengembalian);
                                perpustakaan.pinjamBuku(namaBuku, namaPenulis, namaPeminjam, tanggalPeminjaman, batasPengembalian);
                            } catch (ParseException e) {
                                System.out.println("\nALERT : Harap masukkan tanggal dengan format dd/MM/yyyy");
                            }
                        } else {
                            System.out.println("\nNOTICE : Buku tidak tersedia di perpustakaan");
                        }
                        System.out.println();
                        break;
                    case 2:
                        System.out.print("Pengembalian Buku\n");
                        System.out.print("Masukkan nama buku: ");
                        namaBuku = scanner.nextLine();
                        System.out.print("Masukkan nama penulis buku: ");
                        namaPenulis = scanner.nextLine();
                        perpustakaan.kembalikanBuku(namaBuku, namaPenulis);
                        System.out.println();
                        break;
                    case 3:
                        perpustakaan.tampilkanBukuDipinjam();
                        System.out.println();
                        break;
                    case 4:
                        perpustakaan.tampilkanBukuTersedia();
                        System.out.println();
                        break;
                    case 5:
                        System.out.print("Tambah Persediaan Buku\n");
                        System.out.print("Masukkan jumlah buku yang ingin ditambahkan: ");
                        int jumlahBuku = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println();

                        for (int i = 0; i < jumlahBuku; i++) {
                            System.out.println("Buku ke-" + (i+1));
                            System.out.print("Masukkan nama buku: ");
                            namaBuku = scanner.nextLine();
                            System.out.print("Masukkan nama penulis buku: ");
                            namaPenulis = scanner.nextLine();
                            perpustakaan.tambahBuku(namaBuku, namaPenulis);
                            System.out.println();
                        }
                        break;
                    case 0:
                        System.out.println("NOTICE : Terima kasih dan Sampai Jumpa!");
                        break;
                    default:
                        System.out.println("ALERT : Harap masukkan pilihan menu dari 0 hingga 5");
                        System.out.println();
                }
            } 
            catch (InputMismatchException e) {
                System.out.println("\nALERT : Harap masukkan jumlah berupa angka");
                scanner.nextLine();
                System.out.println();
            }
        }
    }
}



