import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SistemPeminjaman {
    static ArrayList<User> daftarUser = new ArrayList<>();
    static ArrayList<Admin> daftarAdmin = new ArrayList<>();
    static ArrayList<Peminjaman> daftarPeminjaman = new ArrayList<>();
    static ArrayList<Ruangan> daftarRuangan = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    // private static User currentUser;

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }    
    
    public static void judul() {
        System.out.println("------------------------------------------------------");
        System.out.println("  === Sistem Perpinjaman Ruangan Fakultas Teknik ===  ");
        System.out.println("------------------------------------------------------");
        System.out.print("\n");
    } 

    public static void main(String[] args) {
        daftarAdmin.add(new Admin("admin12", "123", "StaffTU", "stafftu@email.com"));
        daftarUser.add(new Mahasiswa("004", "12", "Saniyyah", "saniyyah@email.com", "Informatika"));
        daftarUser.add(new Dosen("001", "12", "Melly", "melly@email.com", "Dosen Teknik Sipil"));

    
        while (true) {
            clearScreen();
            judul();
            System.out.print("                        MENU ");
            System.out.println("\n\n1. Login");
            System.out.println("2. Keluar dari Program");
            System.out.print("\nPilih menu: ");
    
            while (!scanner.hasNextInt()) {
                System.out.println("\nInput tidak valid! Masukkan angka.");
                System.out.print("\nPilih menu: ");
                scanner.next();
            }
            int pilihan = scanner.nextInt();
            scanner.nextLine();
    
            if (pilihan == 2) { 
                clearScreen();
                System.out.println("\nProgram berhenti. Bye Bye....");
                System.exit(0);
            } else if (pilihan != 1) {
                System.out.println("\nPilihan tidak valid!");
                System.out.print("\nTekan Enter untuk kembali...");
                scanner.nextLine();
                continue;
            }
    
            // Proses login
            clearScreen();
            judul();
            System.out.print("                        LOGIN ");
            System.out.print("\n\nMasukkan NIM/NIP  : ");
            String nimNip = scanner.nextLine();
            System.out.print("Masukkan Password : ");
            String password = scanner.nextLine();
    
            boolean loginBerhasil = false;
    
            for (Admin admin : daftarAdmin) {
                if (admin.login(nimNip, password)) {
                    System.out.println("\nLogin berhasil sebagai Admin!");
                    menuAdmin();
                    loginBerhasil = true;
                    break;
                }
            }            
    
            for (User user : daftarUser) {
                if (user.login(nimNip, password)) {
                    System.out.println("\nLogin berhasil sebagai User!");
                    menuUser(user);
                    loginBerhasil = true;
                    break;
                }
            }            
    
            if (!loginBerhasil) {
                System.out.println("\nLogin gagal! NIM/NIP atau password salah.");
                System.out.print("\nTekan Enter untuk mencoba lagi...");
                scanner.nextLine();
            }
        }
    }
    
    
    // MENU USER
    static void menuUser(User currentUser) {
        int pilihan;
        do {
            clearScreen();
            judul();
            System.out.print("                        MENU USER ");
            System.out.println("\n\n1. Lihat Daftar Ruangan");
            System.out.println("2. Tambah Peminjaman");
            System.out.println("3. Lihat Peminjaman Saya");
            System.out.println("4. Lihat Reservasi Ruangan");
            System.out.println("5. Logout");
            System.out.print("\nPilih menu : ");

            while (!scanner.hasNextInt()) {
                System.out.println("\nInput tidak valid! Masukkan angka.");
                System.out.print("\nPilih menu: ");
                scanner.next();
            }
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1 -> {lihatRuangan();
                    System.out.print("\nTekan Enter untuk kembali...");
                    scanner.nextLine();}
                case 2 -> {lihatRuangan();
                tambahPeminjaman(currentUser);}
                case 3 -> lihatPeminjamanUser(currentUser.getNimNip());
                case 4 -> lihatRuanganTerpakai();
                case 5 -> {
                    System.out.println("\nLogout berhasil!");
                    return;
                }
                default -> {System.out.println("\nPilihan tidak valid!");
                System.out.print("\nTekan Enter untuk kembali...");
                scanner.nextLine();}
            }
        } while (pilihan != 5);
    }

    static void tambahPeminjaman(User currentUser) {
        clearScreen();
        judul();
    
        if (daftarRuangan.isEmpty()) {
            System.out.println("\nTidak ada ruangan yang tersedia untuk dipinjam.");
            System.out.print("\nTekan Enter untuk kembali...");
            scanner.nextLine();
            return;
        }

        String nama = currentUser.getNama();
        String nimNip = currentUser.getNimNip();

    
        // // Cari nama berdasarkan NIM/NIP
        // String nama = null;
        // for (User u : daftarUser) {
        //     if (u.getNimNip().equals(nimNip)) {
        //         nama = u.getNama();
        //         break;
        //     }
        // }
    
        if (nama == null) {
            System.out.println("\nNIM/NIP tidak ditemukan! Peminjaman gagal.");
            System.out.print("\nTekan Enter untuk kembali...");
            scanner.nextLine();
            return;
        }
    
        String kodeRuangan;
        boolean ruanganDitemukan = false;
        while (true) {
            System.out.print("Masukkan Kode Ruangan: ");
            kodeRuangan = scanner.nextLine().trim();
    
            if (kodeRuangan.isEmpty()) {
                System.out.println("Kode Ruangan wajib diisi!");
                continue;
            }
    
            for (Ruangan r : daftarRuangan) {
                if (r.getKodeRuangan().equalsIgnoreCase(kodeRuangan)) {
                    ruanganDitemukan = true;
                    break;
                }
            }
    
            if (!ruanganDitemukan) {
                System.out.println("Kode Ruangan tidak ditemukan! Masukkan kode yang valid.");
            } else {
                break;
            }
        }
    
        String tanggal;
        while (true) {
            System.out.print("Masukkan Tanggal Peminjaman (YYYY-MM-DD): ");
            tanggal = scanner.nextLine().trim();

            if (tanggal.isEmpty()) {
                System.out.println("Tanggal tidak boleh kosong!");
                continue;
            }

            if (!tanggal.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("Gunakan format YYYY-MM-DD.");
                continue;
            }

            break;
        }
    
        String waktuMulai, waktuSelesai;
        while (true) {
            System.out.print("Masukkan Waktu Mulai (HH.mm)  : ");
            waktuMulai = scanner.nextLine().trim();
            System.out.print("Masukkan Waktu Selesai (HH.mm): ");
            waktuSelesai = scanner.nextLine().trim();

            if (waktuMulai.isEmpty() || waktuSelesai.isEmpty()) {
                System.out.println("Waktu mulai dan waktu selesai wajib diisi!");
                continue;
            }

            // Cek format jam dengan regex (hanya angka dan 1 titik)
            if (!waktuMulai.matches("\\d{1,2}\\.\\d{1,2}") || !waktuSelesai.matches("\\d{1,2}\\.\\d{1,2}")) {
                System.out.println("Format waktu tidak valid! Gunakan format HH.mm, contoh: 08.30");
                continue;
            }

            try {
                double mulai = Double.parseDouble(waktuMulai);
                double selesai = Double.parseDouble(waktuSelesai);

                if (mulai == selesai) {
                    System.out.println("Waktu selesai tidak boleh sama dengan waktu mulai!");
                    continue;
                }

                if (mulai > 24.00 || selesai > 24.00 || mulai < 0 || selesai < 0) {
                    System.out.println("Jam tidak boleh di luar rentang 00.00 - 24.00!");
                    continue;
                }

                break;
            } catch (NumberFormatException e) {
                System.out.println("Input waktu tidak valid! Pastikan hanya menggunakan angka dan titik (HH.mm)");
            }
        }

        for (Peminjaman p : daftarPeminjaman) {
            if (p.getKodeRuangan().equalsIgnoreCase(kodeRuangan) && p.getTanggal().equals(tanggal)) {
                String pMulai = p.getWaktuMulai();
                String pSelesai = p.getWaktuSelesai();
        
                // Cek apakah ada tabrakan waktu
                if (!(waktuSelesai.compareTo(pMulai) <= 0 || waktuMulai.compareTo(pSelesai) >= 0)) {
                    System.out.println("Ruangan sudah dipinjam pada waktu tersebut!");
                    System.out.print("\nTekan Enter untuk kembali...");
                    scanner.nextLine();
                    return;
                }
            }
        } 
    
        String keterangan = "";

        if (currentUser instanceof Mahasiswa mhs) {
            mhs.ajukanPeminjaman(kodeRuangan, tanggal, waktuMulai, waktuSelesai);
            keterangan = mhs.getProdi();
        } if (currentUser instanceof Dosen dos) {
            dos.ajukanPeminjaman(kodeRuangan, tanggal, waktuMulai, waktuSelesai);
            keterangan = dos.getDepartemen();
        }
        

        daftarPeminjaman.add(new Peminjaman(nimNip, nama, kodeRuangan, tanggal, waktuMulai, waktuSelesai, keterangan));

    }
    
    static void lihatPeminjamanUser(String nimNip) {
        clearScreen();
        judul();
        boolean adaPeminjaman = false;
        for (Peminjaman p : daftarPeminjaman) {
            if (p.getNimNip().equals(nimNip)) {
                System.out.println(p);
                adaPeminjaman = true;
            }
        }
        if (!adaPeminjaman) {
            System.out.println("\nTidak ada riwayat peminjaman.");
        }

        System.out.print("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }

    static void lihatRuanganTerpakai() {
        clearScreen();
        judul();
        
        if (daftarPeminjaman.isEmpty()) {
            System.out.println("\nBelum ada ruangan yang direservasi.");
        } else {
            System.out.println("\nDaftar Ruangan yang Sudah Dipesan:");
            System.out.println("\n");
            System.out.printf("%-15s %-12s %-10s\n", "Kode Ruangan", "Tanggal", "Waktu");
            System.out.println("------------------------------------------------------");
    
            for (Peminjaman p : daftarPeminjaman) {
                String waktu = p.getWaktuMulai() + "-" + p.getWaktuSelesai();
                System.out.printf("%-15s %-12s %-15s\n", p.getKodeRuangan(), p.getTanggal(), waktu);
            }
        }
    
        System.out.print("\nTekan Enter untuk kembali...");
        scanner.nextLine();
    }
    

    // MENU ADMIN
    static void menuAdmin() {
        int pilihan;
        do {
            clearScreen();
            judul();
            System.out.print("                      MENU ADMIN ");
            System.out.println("\n\n1. Kelola Ruangan");
            System.out.println("2. Lihat Semua Peminjaman");
            System.out.println("3. Hapus Peminjaman");
            System.out.println("4. Logout");
            System.out.print("\nPilih menu : ");
            while (!scanner.hasNextInt()) {
                System.out.println("\nInput tidak valid! Masukkan angka.");
                System.out.print("\nPilih menu: ");
                scanner.next();
            }
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1 -> kelolaRuangan();
                case 2 -> {lihatPeminjaman();
                    System.out.print("\nTekan Enter untuk melanjutkan...");
                    scanner.nextLine();}
                case 3 -> { lihatPeminjaman();
                    hapusPeminjaman();}
                case 4 -> {
                    System.out.println("\nLogout berhasil!");
                    return;
                }
                default -> {System.out.println("\nPilihan tidak valid!");
                System.out.print("\nTekan Enter untuk kembali...");
                scanner.nextLine();}
            }
        } while (pilihan != 4);
    }

    static void kelolaRuangan() {
        int pilihan;
        do {
            clearScreen();
            judul();
            System.out.print("                     KELOLA RUANGAN ");
            System.out.println("\n\n1. Tambah Ruangan");
            System.out.println("2. Lihat Semua Ruangan");
            System.out.println("3. Perbarui Ruangan");
            System.out.println("4. Hapus Ruangan");
            System.out.println("5. Kembali");
            System.out.print("\nPilih menu : ");
            while (!scanner.hasNextInt()) {
                System.out.println("\nInput tidak valid! Masukkan angka.");
                System.out.print("\nPilih menu: ");
                scanner.next();
            }
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1 -> tambahRuangan();
                case 2 -> {lihatRuangan();
                    System.out.print("\nTekan Enter untuk kembali...");
                    scanner.nextLine();}
                case 3 -> perbaruiRuangan();
                case 4 -> hapusRuangan();
                case 5 -> System.out.println("Kembali ke menu admin.");
                default -> {System.out.println("\nPilihan tidak valid!");
                System.out.print("\nTekan Enter untuk kembali...");
                scanner.nextLine();}
            }
        } while (pilihan != 5);
    }

    static void tambahRuangan() {
        clearScreen();
        judul();
        String kodeRuangan;
        while (true) {
            System.out.print("Masukkan Kode Ruangan : ");
            kodeRuangan = scanner.nextLine().trim();

            if (kodeRuangan.isEmpty()) {
                System.out.println("Kode Ruangan wajib diisi!");
                continue;
            }

            boolean kodeSudahAda = false;
            for (Ruangan r : daftarRuangan) {
                if (r.getKodeRuangan().equalsIgnoreCase(kodeRuangan)) {
                    kodeSudahAda = true;
                    break;
                }
            }

            if (kodeSudahAda) {
                System.out.println("Kode Ruangan sudah digunakan!");
            } else {
                break;
            }
        }

        int kapasitas;
        while (true) {
            System.out.print("Masukkan Kapasitas    : ");
            String input = scanner.nextLine().trim();    
            try {
                kapasitas = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid! Masukkan angka saja.");
            }
        }  

        System.out.print("Masukkan Fasilitas    : ");
        String fasilitas = scanner.nextLine();
        System.out.print("Masukkan Lokasi       : ");
        String lokasi = scanner.nextLine();

        System.out.println("Pilih Jenis Ruangan   :");
        System.out.println("1. Ruang Kelas");
        System.out.println("2. Ruang Laboratorium");
        System.out.println("3. Ruang Seminar");
        System.out.print("Pilihan Anda (1/2/3): ");
        String pilihan = scanner.nextLine();

        Ruangan ruanganBaru;

        switch (pilihan) {
            case "1" -> {
                ruanganBaru = new RuangKelas(kodeRuangan, kapasitas, fasilitas, lokasi);
            }
            case "2" -> {
                System.out.print("\nMasukkan Jenis Laboratorium: ");
                String jenisLab = scanner.nextLine();
                ruanganBaru = new RuangLaboratorium(kodeRuangan, kapasitas, fasilitas, lokasi, jenisLab);
            }
            case "3" -> {
                ruanganBaru = new RuangSeminar(kodeRuangan, kapasitas, fasilitas, lokasi);
            }
            default -> {
                System.out.println("Tidak memilih jenis ruangan, default ke 'Ruangan Umum'.");
                ruanganBaru = new Ruangan(kodeRuangan, kapasitas, fasilitas, lokasi);
            }
        }

        daftarRuangan.add(ruanganBaru);
        System.out.println("\nRuangan berhasil ditambahkan!");

        System.out.print("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }

    static void lihatRuangan() {
        clearScreen();
        judul();
    
        if (daftarRuangan.isEmpty()) {
            System.out.println("\nTidak ada data ruangan.");
        } else {
            System.out.println("Daftar Ruangan");
            int no = 1;
            for (Ruangan r : daftarRuangan) {
                System.out.println("\nRuangan ke-" + no++);
                System.out.println("Kode Ruangan : " + r.getKodeRuangan());
                System.out.println("Kapasitas    : " + r.getKapasitas());
                System.out.println("Fasilitas    : " + r.getFasilitas());
                System.out.println("Lokasi       : " + r.getLokasi());
    
                if (r instanceof RuangLaboratorium lab) {
                    System.out.println("Jenis        : Laboratorium " + lab.getJenisLaboratorium());
                } else if (r instanceof RuangKelas) {
                    System.out.println("Jenis        : Ruang Kelas");
                } else if (r instanceof RuangSeminar) {
                    System.out.println("Jenis        : Ruang Seminar");
                } else {
                    System.out.println("Jenis        : Ruangan Umum");
                }
            }
        }
    
        // System.out.print("\nTekan Enter untuk kembali...");
        // scanner.nextLine();
    }
    
    
    static void perbaruiRuangan() {
        clearScreen();
        judul();

        if (daftarRuangan.isEmpty()) {
            System.out.println("\nTidak ada ruangan yang tersedia untuk diperbarui.");
            System.out.print("\nTekan Enter untuk kembali...");
            scanner.nextLine();
            return;
        }

        System.out.print("Masukkan Kode Ruangan yang ingin diperbarui: ");
        String kodeRuangan = scanner.nextLine();

        for (Ruangan r : daftarRuangan) {
            if (r.getKodeRuangan().equals(kodeRuangan)) {
                System.out.println("\nInput data baru (Enter jika tidak ingin mengubah data)");

                int kapasitas = r.getKapasitas();
                while (true) {
                    System.out.print("\nMasukkan Kapasitas baru: ");
                    String kapasitasInput = scanner.nextLine().trim();
                    if (kapasitasInput.isEmpty()) break;
                    try {
                        kapasitas = Integer.parseInt(kapasitasInput);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Input tidak valid! Masukkan angka saja.");
                    }
                }
                
                System.out.print("Masukkan Fasilitas baru: ");
                String fasilitas = scanner.nextLine();
                fasilitas = fasilitas.isEmpty() ? r.getFasilitas() : fasilitas;

                System.out.print("Masukkan Lokasi baru   : ");
                String lokasi = scanner.nextLine();
                lokasi = lokasi.isEmpty() ? r.getLokasi() : lokasi;

                r.setKapasitas(kapasitas);
                r.setFasilitas(fasilitas);
                r.setLokasi(lokasi);

                System.out.println("\nPilih Jenis Ruangan  :");
                System.out.println("1. Kelas");
                System.out.println("2. Laboratorium");
                System.out.println("3. Seminar");
                System.out.print("Tekan Enter jika tetap sebagai " + r.getJenis() + ": ");
                String pilihan = scanner.nextLine();

                Ruangan ruanganBaru = r;
                Ruangan ruanganLama = r;

                switch (pilihan) {
                    case "1" -> ruanganBaru = new RuangKelas(kodeRuangan, kapasitas, fasilitas, lokasi);
                    case "2" -> {
                        System.out.print("Masukkan Jenis Laboratorium baru: ");
                        String jenisLab = scanner.nextLine();
                        ruanganBaru = new RuangLaboratorium(kodeRuangan, kapasitas, fasilitas, lokasi, jenisLab);
                    }
                    case "3" -> ruanganBaru = new RuangSeminar(kodeRuangan, kapasitas, fasilitas, lokasi);
                    default -> {
                        // Jika enter atau pilihan salah, tetap gunakan jenis lama dan update isinya
                        ruanganBaru.setKapasitas(kapasitas);
                        ruanganBaru.setFasilitas(fasilitas);
                        ruanganBaru.setLokasi(lokasi);
                        
                        if (ruanganBaru instanceof RuangLaboratorium lab) {
                            System.out.print("Masukkan Jenis Laboratorium baru: ");
                            String jenisLabBaru = scanner.nextLine();
                            if (!jenisLabBaru.isEmpty()) lab.setJenisLaboratorium(jenisLabBaru);
                        }

                        Collections.replaceAll(daftarRuangan, ruanganLama, ruanganBaru);
                        System.out.println("\nRuangan berhasil diperbarui!");
                        System.out.print("\nTekan Enter untuk kembali...");
                        scanner.nextLine();
                        return;
                    }
                }

                Collections.replaceAll(daftarRuangan, ruanganLama, ruanganBaru);

                System.out.println("\nRuangan berhasil diperbarui!");
                System.out.print("\nTekan Enter untuk kembali...");
                scanner.nextLine();
                return;
            }
        }
        System.out.println("\nRuangan tidak ditemukan.");
        System.out.print("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }

    static void hapusRuangan() {
        clearScreen();
        judul();

        if (daftarRuangan.isEmpty()) {
            System.out.println("\nTidak ada ruangan yang tersedia untuk dihapus.");
            System.out.print("\nTekan Enter untuk kembali...");
            scanner.nextLine();
            return;
        }

        System.out.print("Masukkan Kode Ruangan untuk dihapus: ");
        String kodeRuangan = scanner.nextLine();

        for (Ruangan r : daftarRuangan) {
            if (r.getKodeRuangan().equals(kodeRuangan)) {
                daftarRuangan.remove(r);
                System.out.println("\nRuangan berhasil dihapus!");
                System.out.print("\nTekan Enter untuk melanjutkan...");
                scanner.nextLine();
                return;
            }
        }
        System.out.println("\nRuangan tidak ditemukan.");

        System.out.print("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }

    static void lihatPeminjaman() {
        clearScreen();
        judul();
        
        if (daftarPeminjaman.isEmpty()) {
            System.out.println("Tidak ada data peminjaman.");
        } else {
            System.out.println("===== Daftar Peminjaman Ruangan =====");
            for (Peminjaman p : daftarPeminjaman) {
                System.out.println("\n----------------------------------------");
                System.out.println("Nama            : " + p.getNama());
                System.out.println("NIM/NIP         : " + p.getNimNip());
                System.out.println("Kode Ruangan    : " + p.getKodeRuangan());
                System.out.println("Tanggal         : " + p.getTanggal());
                System.out.println("Waktu           : " + p.getWaktuMulai() + " - " + p.getWaktuSelesai());
                System.out.println("Keterangan      : " + p.getKeterangan());
            }
            System.out.println("----------------------------------------");
            System.out.println("\n");
        }
    }
    

    static void hapusPeminjaman() {
        clearScreen();
        judul();

        if (daftarPeminjaman.isEmpty()) {
            System.out.println("Tidak ada peminjaman yang bisa dihapus.");
            System.out.print("\nTekan Enter untuk kembali...");
            scanner.nextLine();
            return;
        }
    
        System.out.print("Masukkan NIM/NIP untuk menghapus data peminjaman: ");
        String nimNip = scanner.nextLine();
    
        boolean ditemukan = false;
        for (Peminjaman p : daftarPeminjaman) {
            if (p.getNimNip().equals(nimNip)) {
                daftarPeminjaman.remove(p);
                System.out.println("\nData peminjaman berhasil dihapus!");
                ditemukan = true;
                break;
            }
        }
    
        if (!ditemukan) {
            System.out.println("\nData tidak ditemukan.");
        }
    
        System.out.print("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }
    
}
