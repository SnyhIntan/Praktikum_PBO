import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class SistemPeminjaman {
    
    static ArrayList<User> daftarUser = new ArrayList<>();
    static ArrayList<Admin> daftarAdmin = new ArrayList<>();
    static ArrayList<Peminjaman> daftarPeminjaman = new ArrayList<>();
    static ArrayList<Ruangan> daftarRuangan = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    private static int totalLoginAttempts = 0;
    private static final int MAX_LOGIN_ATTEMPTS = 3;


public static void loadRuanganFromCSV(String fileName) {
    List<String[]> data = CSVHelper.readCSV(fileName);

    for (String[] row : data) {
        if (row.length >= 5) {
            String kodeRuangan = row[0];
            int kapasitas;
            try {
                kapasitas = Integer.parseInt(row[1]);
            } catch (NumberFormatException e) {
                System.out.println("Format kapasitas salah di baris: " + Arrays.toString(row));
                continue;
            }
            String fasilitas = row[2];
            String lokasi = row[3];
            String jenis = row[4].toLowerCase();
            String jenisLab = row.length > 5 ? row[5] : "";

            Ruangan ruanganBaru;

            switch (jenis) {
                case "kelas":
                    ruanganBaru = new RuangKelas(kodeRuangan, kapasitas, fasilitas, lokasi);
                    break;
                case "laboratorium":
                    ruanganBaru = new RuangLaboratorium(kodeRuangan, kapasitas, fasilitas, lokasi, jenisLab);
                    break;
                case "seminar":
                    ruanganBaru = new RuangSeminar(kodeRuangan, kapasitas, fasilitas, lokasi);
                    break;
                default:
                    ruanganBaru = new Ruangan(kodeRuangan, kapasitas, fasilitas, lokasi);
                    break;
            }

            daftarRuangan.add(ruanganBaru);
        } else {
            System.out.println("Data tidak lengkap: " + Arrays.toString(row));
        }
    }
}


    // public static void clearScreen() {
    //     System.out.print("\033[H\033[2J");
    //     System.out.flush();
    // }    
    public static void clearScreen() {
    try {
        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    } catch (Exception e) {
        System.out.println("Gagal membersihkan layar.");
    }
}

    
    public static void judul() {
        System.out.println("------------------------------------------------------");
        System.out.println("          === SISTEM PEMINJAMAN RUANGAN ===           ");
        System.out.println("------------------------------------------------------");
        System.out.print("\n");
    } 

    public static void main(String[] args) {
    daftarAdmin.add(new Admin("admin12", "123", "StaffTU", "stafftu@email.com"));
    daftarUser.add(new Mahasiswa("2309106004", "121212", "Saniyyah", "saniyyah@email.com", "Informatika"));
    daftarUser.add(new Dosen("1223334444", "121212", "Melly", "melly@email.com", "Dosen Teknik Sipil"));

    int totalLoginAttempts = 0;
    final int MAX_LOGIN_ATTEMPTS = 3;

    while (true) {
        clearScreen();
        judul();
        System.out.print("                        MENU ");
        System.out.println("\n\n1. Login");
        System.out.println("2. Keluar dari Program");
        System.out.print("\nPilih menu: ");

        String input = scanner.nextLine().trim();

        if (!input.equals("1") && !input.equals("2")) {
            System.out.println("\nPilihan tidak valid!");
            System.out.print("\nTekan Enter untuk kembali...");
            scanner.nextLine();
            continue;
        }

        int pilihan = Integer.parseInt(input);
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

        clearScreen();
        judul();
        System.out.print("                        LOGIN ");

        System.out.print("\n\nMasukkan NIM/NIP  : ");
        String nimNip = scanner.nextLine().trim();
        System.out.print("Masukkan Password : ");
        String password = scanner.nextLine().trim();

        if (nimNip.isEmpty() || password.isEmpty()) {
            System.out.println("\nNIM/NIP dan Password tidak boleh kosong atau hanya spasi!");
            totalLoginAttempts++;
            System.out.println("Percobaan login: " + totalLoginAttempts + "/" + MAX_LOGIN_ATTEMPTS);
            System.out.print("\nTekan Enter untuk mencoba lagi...");
            scanner.nextLine();

            if (totalLoginAttempts >= MAX_LOGIN_ATTEMPTS) {
                System.out.println("\nAnda telah melebihi jumlah percobaan login yang diizinkan.");
                System.out.println("Silakan coba lagi nanti.");
                return;
            }
            continue;
        }

        boolean loginBerhasil = false;

        for (Admin admin : daftarAdmin) {
            if (admin.login(nimNip, password)) {
                System.out.println("\nLogin berhasil sebagai Admin!");
                loginBerhasil = true;
                menuAdmin();
                break;
            }
        }

        if (!loginBerhasil) {
            for (User user : daftarUser) {
                if (user.login(nimNip, password)) {
                    System.out.println("\nLogin berhasil sebagai User!");
                    user.tampilkanInfo();
                    System.out.print("\nTekan Enter untuk melanjutkan...");
                    scanner.nextLine();
                    menuUser(user);
                    loginBerhasil = true;
                    break;
                }
            }
        }

        if (!loginBerhasil) {
            totalLoginAttempts++;
            System.out.println("\nLogin gagal! NIM/NIP atau password salah.");
            System.out.println("Percobaan login: " + totalLoginAttempts + "/" + MAX_LOGIN_ATTEMPTS);                
            System.out.print("\nTekan Enter untuk mencoba lagi...");
            scanner.nextLine();

            if (totalLoginAttempts >= MAX_LOGIN_ATTEMPTS) {
                System.out.println("\nAnda telah melebihi jumlah percobaan login yang diizinkan.");
                System.out.println("Silakan coba lagi nanti.");
                return;
            }
        }
    }
}
    
    // MENU USER
    public static void menuUser(User currentUser) {
        int pilihan;
        do {
            clearScreen();
            judul();
            System.out.print("                        MENU USER ");
            System.out.println("\n\n1. Lihat Daftar Ruangan");
            System.out.println("2. Tambah Peminjaman");
            System.out.println("3. Lihat Peminjaman Saya");
            System.out.println("4. Batalkan Peminjaman");
            System.out.println("5. Lihat Reservasi Ruangan");
            System.out.println("6. Logout");
            System.out.print("\nPilih menu : ");

            while (!scanner.hasNextInt()) {
                System.out.println("\nInput tidak valid! Masukkan angka.");
                System.out.print("\nPilih menu: ");
                scanner.next();
            }
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1 : {lihatRuangan();
                    System.out.print("\nTekan Enter untuk kembali...");
                    scanner.nextLine();
                    break;}
                case 2 : {lihatRuangan();
                    tambahPeminjaman(currentUser);
                    break;}
                case 3 : lihatPeminjamanUser(currentUser.getNimNip());
                    break;
                case 4 : batalkanPeminjaman(currentUser);
                    break;
                case 5 : lihatRuanganTerpakai(); 
                    break;
                case 6 : {
                    System.out.println("\nLogout berhasil!");
                    return;
                }
                default : {System.out.println("\nPilihan tidak valid!");
                System.out.print("\nTekan Enter untuk kembali...");
                scanner.nextLine();
                break;}
            }
        } while (pilihan != 6);
    }

    public static void tambahPeminjaman(User currentUser) {
        clearScreen();
        judul();
        lihatRuangan();

        if (daftarRuangan.isEmpty()) {
            System.out.println("\nTidak ada ruangan yang tersedia untuk dipinjam.");
            System.out.print("\nTekan Enter untuk kembali...");
            scanner.nextLine();
            return;
        }

        String nama = currentUser.getNama();
        String nimNip = currentUser.getNimNip();

        if (nama == null) {
            System.out.println("\nNIM/NIP tidak ditemukan! Peminjaman gagal.");
            System.out.print("\nTekan Enter untuk kembali...");
            scanner.nextLine();
            return;
        }

        // Load ruangan dan peminjaman dari file
        List<String[]> ruanganCSV = CSVHelper.readCSV("ruangan.csv");
        List<String[]> peminjamanCSV = CSVHelper.readCSV("peminjaman.csv");

        String kodeRuangan;
        while (true) {
            System.out.print("\nMasukkan Kode Ruangan (atau ketik 0 untuk batal): ");
            kodeRuangan = scanner.nextLine().trim();

            if (kodeRuangan.equalsIgnoreCase("0")) {
                System.out.println("\nPeminjaman dibatalkan.");
                System.out.print("\nTekan Enter untuk kembali...");
                scanner.nextLine();
                return;
            }

            if (kodeRuangan.isEmpty()) {
                System.out.println("Kode Ruangan wajib diisi!");
                continue;
            }

            boolean ruanganValid = false;
            for (String[] r : ruanganCSV) {
                if (r[0].equalsIgnoreCase(kodeRuangan)) {
                    ruanganValid = true;
                    break;
                }
            }

            if (!ruanganValid) {
                System.out.println("Kode Ruangan tidak ditemukan di file!");
            } else {
                break;
            }
        }

        String tanggal;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate batasMinimal = LocalDate.now().plusDays(2);

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

            try {
                LocalDate parsedDate = LocalDate.parse(tanggal, formatter);
                if (parsedDate.isBefore(batasMinimal)) {
                    System.out.println("Tanggal minimal peminjaman adalah: " + batasMinimal);
                    continue;
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Tanggal tidak valid!");
            }
        }

        String waktuMulai, waktuSelesai;
        int totalMenitMulai = 0, totalMenitSelesai = 0;

        while (true) {
            System.out.print("Masukkan Waktu Mulai (HH.mm)  : ");
            waktuMulai = scanner.nextLine().trim();
            System.out.print("Masukkan Waktu Selesai (HH.mm): ");
            waktuSelesai = scanner.nextLine().trim();

            if (waktuMulai.isEmpty() || waktuSelesai.isEmpty()) {
                System.out.println("Waktu mulai dan selesai wajib diisi!");
                continue;
            }

            if (!waktuMulai.matches("\\d{1,2}\\.\\d{1,2}") || !waktuSelesai.matches("\\d{1,2}\\.\\d{1,2}")) {
                System.out.println("Gunakan format HH.mm");
                continue;
            }

            try {
                String[] bagianMulai = waktuMulai.split("\\.");
                String[] bagianSelesai = waktuSelesai.split("\\.");

                int jamMulai = Integer.parseInt(bagianMulai[0]);
                int menitMulai = Integer.parseInt(bagianMulai[1]);
                int jamSelesai = Integer.parseInt(bagianSelesai[0]);
                int menitSelesai = Integer.parseInt(bagianSelesai[1]);

                if (jamMulai < 0 || jamMulai > 24 || jamSelesai < 0 || jamSelesai > 24 ||
                    menitMulai < 0 || menitMulai >= 60 || menitSelesai < 0 || menitSelesai >= 60) {
                    System.out.println("Jam atau menit tidak valid!");
                    continue;
                }

                totalMenitMulai = jamMulai * 60 + menitMulai;
                totalMenitSelesai = jamSelesai * 60 + menitSelesai;

                if (totalMenitSelesai <= totalMenitMulai) {
                    System.out.println("Waktu selesai harus lebih besar dari mulai.");
                    continue;
                }

                if (totalMenitSelesai - totalMenitMulai < 60) {
                    System.out.println("Durasi peminjaman minimal adalah 1 jam!");
                    continue;
                }

                boolean konflik = false;
                for (String[] pinjam : peminjamanCSV) {
                    String kode = pinjam[2];
                    String tgl = pinjam[3];
                    String mulai = pinjam[4];
                    String selesai = pinjam[5];
                    String peminjam = pinjam[0];

                    if (kode.equalsIgnoreCase(kodeRuangan) && tgl.equals(tanggal)) {
                        menitMulai = Integer.parseInt(mulai.split("\\.")[0]) * 60 + Integer.parseInt(mulai.split("\\.")[1]);
                        menitSelesai = Integer.parseInt(selesai.split("\\.")[0]) * 60 + Integer.parseInt(selesai.split("\\.")[1]);

                        boolean bentrok = !(totalMenitSelesai <= menitMulai || totalMenitMulai >= menitSelesai);

                        if (bentrok) {
                            if (peminjam.equalsIgnoreCase(nimNip)) {
                                System.out.println("Anda sudah meminjam ruangan ini di jam yang bertabrakan!");
                            } else {
                                System.out.println("Ruangan ini sudah dipinjam orang lain di jam yang bertabrakan!");
                            }
                            konflik = true;
                            break;
                        }
                    }
                }

                if (konflik) continue;

                break;
            } catch (Exception e) {
                System.out.println("Format waktu tidak valid.");
            }
        }

        // Tambahkan peminjaman
        String keterangan = "";
        if (currentUser instanceof Mahasiswa) {
            Mahasiswa mhs = (Mahasiswa) currentUser;
            mhs.ajukanPeminjaman(kodeRuangan, tanggal, waktuMulai, waktuSelesai);
            keterangan = mhs.getProdi();
        } else if (currentUser instanceof Dosen) {
            Dosen dos = (Dosen) currentUser;
            dos.ajukanPeminjaman(kodeRuangan, tanggal, waktuMulai, waktuSelesai);
            keterangan = dos.getDepartemen();
        }

        daftarPeminjaman.add(new Peminjaman(nimNip, nama, kodeRuangan, tanggal, waktuMulai, waktuSelesai, keterangan));
        CSVHelper.appendToCSV("peminjaman.csv", new String[]{
            nimNip, nama, kodeRuangan, tanggal, waktuMulai, waktuSelesai, keterangan
        });

        // System.out.println("\nPeminjaman berhasil ditambahkan.");
        // System.out.print("\nTekan Enter untuk kembali...");
        // scanner.nextLine();
    }

    public static void lihatPeminjamanUser(String nimNip) {
        clearScreen();
        judul();
        daftarPeminjaman = new ArrayList<>(CSVHelper.readPeminjamanFromCSV("peminjaman.csv"));
        boolean adaPeminjaman = false;

        for (Peminjaman p : daftarPeminjaman) {
        if (p.getNimNip().equals(nimNip)) {
            adaPeminjaman = true;
            break;
        }
    }

    System.out.println("Riwayat Peminjaman untuk NIM/NIP: " + nimNip);

        if (!adaPeminjaman) {
            System.out.println("\nTidak ada riwayat peminjaman.");
        } else {
            // System.out.println("Riwayat Peminjaman untuk NIM/NIP: " + nimNip);
            System.out.println("-------------------------------------------------------------------------------------------");
            System.out.printf("%-10s | %-20s | %-8s | %-10s | %-7s | %-7s | %-15s%n", 
                            "NIM/NIP", "Nama", "Kode", "Tanggal", "Mulai", "Selesai", "Keterangan");
            System.out.println("-------------------------------------------------------------------------------------------");

            for (Peminjaman p : daftarPeminjaman) {
                if (p.getNimNip().equals(nimNip)) {
                    System.out.printf("%-10s | %-20s | %-8s | %-10s | %-7s | %-7s | %-15s%n",
                                    p.getNimNip(),
                                    p.getNama(),
                                    p.getKodeRuangan(),
                                    p.getTanggal(),
                                    p.getWaktuMulai(),
                                    p.getWaktuSelesai(),
                                    p.getKeterangan());
                }
            }
        }
        System.out.print("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }

    public static void batalkanPeminjaman(User currentUser) {
    try {
            clearScreen();
            judul();

            if (daftarPeminjaman.isEmpty()) {
                throw new Exception("Belum ada peminjaman ruangan!");
            }

            System.out.println("                DAFTAR PEMINJAMAN ANDA          \n");
            int nomor = 1;
            List<Peminjaman> peminjamanUser = new ArrayList<>();

            for (Peminjaman p : daftarPeminjaman) {
                if (p.getNimNip().equals(currentUser.getNimNip())) {
                    System.out.println(nomor + ". Ruang: " + p.getKodeRuangan() + 
                                    " | Tanggal: " + p.getTanggal() + 
                                    " | Waktu: " + p.getWaktuMulai() + "-" + p.getWaktuSelesai());
                    peminjamanUser.add(p);
                    nomor++;
                }
            }

            if (peminjamanUser.isEmpty()) {
                throw new Exception("Anda belum meminjam ruangan apapun!");
            }

            System.out.print("\nMasukkan nomor yang akan dibatalkan: ");
            int pilihan = Integer.parseInt(scanner.nextLine());

            if (pilihan < 1 || pilihan > peminjamanUser.size()) {
                throw new Exception("Nomor peminjaman tidak valid!");
            }

            Peminjaman pinjam = peminjamanUser.get(pilihan - 1);

            PeminjamanInterface peminjam = (PeminjamanInterface) currentUser;
            peminjam.batalkanPeminjaman(pinjam.getKodeRuangan());

            daftarPeminjaman.remove(pinjam);

            boolean deleted = CSVHelper.deletePeminjamanFromCSV("peminjaman.csv", pinjam);
            if (!deleted) {
                System.out.println("Gagal menghapus peminjaman dari file CSV!");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nHarap masukkan angka!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.print("\nTekan Enter untuk kembali...");
            scanner.nextLine();
        }
    }

    public static void lihatRuanganTerpakai() {
        clearScreen();
        judul();
        daftarPeminjaman = new ArrayList<>(CSVHelper.readPeminjamanFromCSV("peminjaman.csv"));
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
    
    public static void menuAdmin() {
        int pilihan;
        daftarPeminjaman = new ArrayList<>(CSVHelper.readPeminjamanFromCSV("peminjaman.csv"));
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
                case 1 : kelolaRuangan();
                    break;
                case 2 : {lihatPeminjaman();
                    System.out.print("\nTekan Enter untuk melanjutkan...");
                    scanner.nextLine();
                    break;}
                case 3 : hapusPeminjaman(daftarPeminjaman);
                    break;
                case 4 : {
                    System.out.println("\nLogout berhasil!");
                    return;
                }
                default : {System.out.println("\nPilihan tidak valid!");
                System.out.print("\nTekan Enter untuk kembali...");
                scanner.nextLine();
                break;}
            }
        } while (pilihan != 4);
    }

    public static void kelolaRuangan() {
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
                case 1 : tambahRuangan();
                    break;
                case 2 : {lihatRuangan();
                    System.out.print("\nTekan Enter untuk kembali...");
                    scanner.nextLine();
                    break;}
                case 3 : perbaruiRuangan();
                    break;
                case 4 : hapusRuangan();
                    break;
                case 5 : System.out.println("Kembali ke menu admin.");
                    break;
                default : {System.out.println("\nPilihan tidak valid!");
                System.out.print("\nTekan Enter untuk kembali...");
                scanner.nextLine();
                break;}
            }
        } while (pilihan != 5);
    }

    public static void tambahRuangan() {
        clearScreen();
        judul();

        List<String[]> dataCSV = CSVHelper.readCSV("ruangan.csv");

        String kodeRuangan;
        while (true) {
            System.out.print("Masukkan Kode Ruangan : ");
            kodeRuangan = scanner.nextLine().trim();

            if (kodeRuangan.isEmpty()) {
                System.out.println("Kode Ruangan wajib diisi!");
                continue;
            }

            boolean kodeSudahAda = false;
            for (String[] row : dataCSV) {
                if (row.length > 0 && row[0].equalsIgnoreCase(kodeRuangan)) {
                    kodeSudahAda = true;
                    break;
                }
            }

            if (kodeSudahAda) {
                System.out.println("Kode Ruangan sudah ada!");
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

        String fasilitas, lokasi;
        while (true) {
            System.out.print("Masukkan Fasilitas    : ");
            fasilitas = scanner.nextLine().trim();
            if (fasilitas.isEmpty()) {
                System.out.println("Fasilitas tidak boleh kosong. Silakan masukkan kembali.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Masukkan Lokasi       : ");
            lokasi = scanner.nextLine().trim();
            if (lokasi.isEmpty()) {
                System.out.println("Lokasi tidak boleh kosong. Silakan masukkan kembali.");
            } else {
                break;
            }
        }

        System.out.println("Pilih Jenis Ruangan   :");
        System.out.println("1. Ruang Kelas");
        System.out.println("2. Ruang Laboratorium");
        System.out.println("3. Ruang Seminar");
        System.out.print("Pilihan Anda (1/2/3): ");
        String pilihan = scanner.nextLine();

        Ruangan ruanganBaru;

        switch (pilihan) {
            case "1" -> ruanganBaru = new RuangKelas(kodeRuangan, kapasitas, fasilitas, lokasi);
            case "2" -> {
                System.out.print("\nMasukkan Jenis Laboratorium: ");
                String jenisLab = scanner.nextLine();
                ruanganBaru = new RuangLaboratorium(kodeRuangan, kapasitas, fasilitas, lokasi, jenisLab);
            }
            case "3" -> ruanganBaru = new RuangSeminar(kodeRuangan, kapasitas, fasilitas, lokasi);
            default -> {
                System.out.println("Tidak memilih jenis ruangan, default ke 'Ruangan Umum'.");
                ruanganBaru = new Ruangan(kodeRuangan, kapasitas, fasilitas, lokasi);
            }
        }

        daftarRuangan.add(ruanganBaru);
        System.out.println("\nRuangan berhasil ditambahkan!");


        CSVHelper.appendToCSV("ruangan.csv", new String[] {
            ruanganBaru.getKodeRuangan(),
            String.valueOf(ruanganBaru.getKapasitas()),
            "\"" + ruanganBaru.getFasilitas() + "\"",
            ruanganBaru.getLokasi(),
            ruanganBaru.getJenis()
        });

        System.out.print("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }


    public static void lihatRuangan() {
        clearScreen();
        judul();

        daftarRuangan.clear();
        List<String[]> data = CSVHelper.readCSV("ruangan.csv");

        for (String[] row : data) {
            if (row.length < 5) continue;

            String kode = row[0].trim();
            int kapasitas = Integer.parseInt(row[1].trim());
            String fasilitas = row[2].trim();
            String lokasi = row[3].trim();
            String jenis = row[4].trim().toLowerCase();

            if (jenis.contains("laboratorium")) {
                String jenisLab = row[4].replaceFirst("(?i)laboratorium\\s*", "").trim();
                if (jenisLab.isEmpty()) {
                    jenisLab = "Umum";
                }
                daftarRuangan.add(new RuangLaboratorium(kode, kapasitas, fasilitas, lokasi, jenisLab));
            } else if (jenis.contains("kelas")) {
                daftarRuangan.add(new RuangKelas(kode, kapasitas, fasilitas, lokasi));
            } else if (jenis.contains("seminar")) {
                daftarRuangan.add(new RuangSeminar(kode, kapasitas, fasilitas, lokasi));
            } else {
                daftarRuangan.add(new Ruangan(kode, kapasitas, fasilitas, lokasi));
            }
        }

        if (daftarRuangan.isEmpty()) {
            System.out.println("\nTidak ada data ruangan.");
        } else {
            System.out.println("Daftar Ruangan:");
            int no = 1;
            for (Ruangan r : daftarRuangan) {
                System.out.println("\nRuangan ke-" + no++);
                System.out.println("Kode Ruangan : " + r.getKodeRuangan());
                System.out.println("Kapasitas    : " + r.getKapasitas());
                System.out.println("Fasilitas    : " + r.getFasilitas());
                System.out.println("Lokasi       : " + r.getLokasi());

                if (r instanceof RuangLaboratorium) {
                    System.out.println("Jenis        : Laboratorium " + ((RuangLaboratorium) r).getJenisLaboratorium());
                } else if (r instanceof RuangKelas) {
                    System.out.println("Jenis        : Ruang Kelas");
                } else if (r instanceof RuangSeminar) {
                    System.out.println("Jenis        : Ruang Seminar");
                } else {
                    System.out.println("Jenis        : Ruangan Umum");
                }
            }
        }
    }

    public static void perbaruiRuangan() {
            clearScreen();
            judul();
            lihatRuangan();

            if (daftarRuangan.isEmpty()) {
                System.out.println("\nTidak ada ruangan yang tersedia untuk diperbarui.");
                System.out.print("\nTekan Enter untuk kembali...");
                scanner.nextLine();
                return;
            }

            System.out.print("\nMasukkan Kode Ruangan yang ingin diperbarui: ");
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
                        case "1":

                            ruanganBaru = new RuangKelas(kodeRuangan, kapasitas, fasilitas, lokasi);
                            break;

                        case "2":
                            System.out.print("Masukkan Jenis Laboratorium baru: ");
                            String jenisLab = scanner.nextLine();
                            ruanganBaru = new RuangLaboratorium(kodeRuangan, kapasitas, fasilitas, lokasi, jenisLab);
                            break;

                        case "3":

                            ruanganBaru = new RuangSeminar(kodeRuangan, kapasitas, fasilitas, lokasi);
                            break;

                        default:
                            ruanganBaru.setKapasitas(kapasitas);
                            ruanganBaru.setFasilitas(fasilitas);
                            ruanganBaru.setLokasi(lokasi);

                            if (ruanganBaru instanceof RuangLaboratorium) {
                                RuangLaboratorium lab = (RuangLaboratorium) ruanganBaru;
                                System.out.print("Masukkan Jenis Laboratorium baru: ");
                                String jenisLabBaru = scanner.nextLine();
                                if (!jenisLabBaru.isEmpty()) {
                                    lab.setJenisLaboratorium(jenisLabBaru);
                                }
                            }

                            Collections.replaceAll(daftarRuangan, ruanganLama, ruanganBaru);
                            CSVHelper.writeAllToCSV("ruangan.csv", daftarRuangan);
                            System.out.println("\nRuangan berhasil diperbarui!");
                            System.out.print("\nTekan Enter untuk kembali...");
                            scanner.nextLine();
                            return;
                        }

                        Collections.replaceAll(daftarRuangan, ruanganLama, ruanganBaru);
                        CSVHelper.writeAllToCSV("ruangan.csv", daftarRuangan);
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

    public static void hapusRuangan() {
        clearScreen();
        judul();

        List<String[]> data = CSVHelper.readCSV("ruangan.csv");

        if (data.isEmpty()) {
            System.out.println("\nTidak ada ruangan yang tersedia untuk dihapus.");
            System.out.print("\nTekan Enter untuk kembali...");
            scanner.nextLine();
            return;
        }

        System.out.println("Daftar Ruangan:");
        int no = 1;
        for (String[] row : data) {
            if (row.length < 5) continue;
            System.out.println("\nRuangan ke-" + no++);
            System.out.println("Kode Ruangan : " + row[0]);
            System.out.println("Kapasitas    : " + row[1]);
            System.out.println("Fasilitas    : " + row[2]);
            System.out.println("Lokasi       : " + row[3]);
            System.out.println("Jenis        : " + row[4]);
        }

        System.out.print("\nMasukkan Kode Ruangan untuk dihapus: ");
        String kodeRuangan = scanner.nextLine().trim();

        List<String[]> daftarPeminjaman = CSVHelper.readCSV("peminjaman.csv");
        boolean sedangDipinjam = false;

        for (String[] row : daftarPeminjaman) {
            if (row.length >= 3 && row[2].equalsIgnoreCase(kodeRuangan)) {
                sedangDipinjam = true;
                break;
            }
        }

        if (sedangDipinjam) {
            System.out.println("\nRuangan tidak bisa dihapus karena masih dalam status peminjaman.");
        } else {
            System.out.print("Apakah Anda yakin ingin menghapus ruangan ini? (y/n): ");
            String konfirmasi = scanner.nextLine().trim().toLowerCase();

            if (konfirmasi.equals("y")) {
                if (CSVHelper.deleteFromCSV("ruangan.csv", kodeRuangan)) {
                    System.out.println("\nRuangan berhasil dihapus!");
                } else {
                    System.out.println("\nRuangan tidak ditemukan.");
                }
            } else {
                System.out.println("\nPenghapusan dibatalkan.");
            }
        }

        System.out.print("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }

    public static void lihatPeminjaman() {
        clearScreen();
        judul();
        
        if (daftarPeminjaman.isEmpty()) {
            System.out.println("Tidak ada data peminjaman.");
        } else {
            System.out.println("\n     ======== Daftar Peminjaman Ruangan ========      ");
            for (int i = 0; i < daftarPeminjaman.size(); i++) {
                Peminjaman p = daftarPeminjaman.get(i);
                System.out.println("\n----------------------------------------");
                System.out.println("ID Peminjaman   : " + (i + 1));
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
    
   
    public static void hapusPeminjaman(List<Peminjaman> daftarPeminjaman) {
    clearScreen();
    // judul();
    Scanner scanner = new Scanner(System.in);

    if (daftarPeminjaman.isEmpty()) {
        System.out.println("Belum ada data peminjaman.");
        System.out.print("\nTekan Enter untuk kembali...");
        scanner.nextLine();
        return;
    }

    System.out.println("-------------------------------------------------------------------");
    System.out.println("                 === SISTEM PEMINJAMAN RUANGAN ===                  ");
    System.out.println("-------------------------------------------------------------------");
    System.out.print("\n");

    System.out.println("                         Daftar Peminjaman                        \n");
    System.out.println("-------------------------------------------------------------------");
    System.out.printf("   %-5s %-15s %-15s %-12s %-17s\n", 
                    "No", "Nama", "Kode Ruangan", "Tanggal", "Waktu");
    System.out.println("-------------------------------------------------------------------");

    for (int i = 0; i < daftarPeminjaman.size(); i++) {
        Peminjaman p = daftarPeminjaman.get(i);
        String waktu = p.getWaktuMulai() + " - " + p.getWaktuSelesai();
        System.out.printf("   %-5d %-15s %-15s %-12s %-17s\n", 
                        i + 1, p.getNama(), p.getKodeRuangan(), p.getTanggal(), waktu);
    }

    System.out.print("\nMasukkan nomor peminjaman yang ingin dihapus (tekan Enter untuk batal): ");
    String input = scanner.nextLine();

    if (input.isEmpty()) {
        System.out.println("\nPenghapusan dibatalkan.");
        return;
    }

    int idPeminjaman;
    try {
        idPeminjaman = Integer.parseInt(input);
    } catch (NumberFormatException e) {
        System.out.println("Input bukan angka yang valid.");
        return;
    }

    if (idPeminjaman < 1 || idPeminjaman > daftarPeminjaman.size()) {
        System.out.println("Nomor peminjaman tidak valid.");
        return;
    }

    Peminjaman peminjaman = daftarPeminjaman.get(idPeminjaman - 1);

    System.out.printf("\nApakah Anda yakin ingin menghapus data ini? (y/n): ");
    String konfirmasi = scanner.nextLine().trim().toLowerCase();
    if (!konfirmasi.equals("y")) {
        System.out.println("\nPenghapusan dibatalkan.");
        return;
    }

    boolean success = CSVHelper.deletePeminjamanFromCSV("peminjaman.csv", peminjaman);

    if (success) {
        daftarPeminjaman.remove(peminjaman);
        System.out.println("\nData peminjaman berhasil dihapus.");
    } else {
        System.out.println("\nData peminjaman tidak ditemukan di file.");
    }

    System.out.print("\nTekan Enter untuk kembali...");
    scanner.nextLine();
}

}