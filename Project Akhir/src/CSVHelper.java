import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVHelper {
public static List<String[]> readCSV(String fileName) {
    List<String[]> data = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = br.readLine()) != null) {
            List<String> row = new ArrayList<>();
            boolean inQuotes = false;
            StringBuilder sb = new StringBuilder();
            for (char c : line.toCharArray()) {
                if (c == '"') {
                    inQuotes = !inQuotes;
                } else if (c == ',' && !inQuotes) {
                    row.add(sb.toString().trim());
                    sb = new StringBuilder();
                } else {
                    sb.append(c);
                }
            }
            row.add(sb.toString().trim());
            data.add(row.toArray(new String[0]));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return data;
}

public static List<Peminjaman> readPeminjamanFromCSV(String fileName) {
    List<Peminjaman> peminjamanList = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            List<String> row = new ArrayList<>();
            boolean inQuotes = false;
            StringBuilder sb = new StringBuilder();

            for (char c : line.toCharArray()) {
                if (c == '"') {
                    inQuotes = !inQuotes;
                } else if (c == ',' && !inQuotes) {
                    row.add(sb.toString().trim());
                    sb.setLength(0);
                } else {
                    sb.append(c);
                }
            }
            row.add(sb.toString().trim());

            if (row.size() >= 7) {
                String nimNip = row.get(0);
                String nama = row.get(1);
                String kodeRuangan = row.get(2);
                String tanggal = row.get(3);
                String waktuMulai = row.get(4);
                String waktuSelesai = row.get(5);
                String keterangan = row.get(6);

                Peminjaman p = new Peminjaman(nimNip, nama, kodeRuangan, tanggal, waktuMulai, waktuSelesai, keterangan);
                peminjamanList.add(p);
            }
        }
    } catch (IOException e) {
        System.out.println("Gagal membaca file peminjaman: " + e.getMessage());
    }
    return peminjamanList;
}


     public static void appendToCSV(String fileName, String[] data) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            for (int i = 0; i < data.length; i++) {
                writer.append(data[i]);
                if (i != data.length - 1) writer.append(",");
            }
            writer.append("\n");
        } catch (IOException e) {
            System.out.println("Gagal menulis ke file: " + fileName);
            e.printStackTrace();
        }
    }

    public static void writeAllToCSV(String filename, List<Ruangan> ruanganList) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
        for (Ruangan r : ruanganList) {
            String fasilitasEscaped = "\"" + r.getFasilitas().replace("\"", "\"\"") + "\"";
            String[] line = new String[] {
                r.getKodeRuangan(),
                String.valueOf(r.getKapasitas()),
                fasilitasEscaped,
                r.getLokasi(),
                r.getJenis()
            };
            writer.println(String.join(",", line));
        }
    } catch (IOException e) {
        System.out.println("Gagal menyimpan data ke CSV: " + e.getMessage());
    }
}


public static boolean deleteFromCSV(String fileName, String idToDelete) {
    List<String[]> data = readCSV(fileName);
    boolean found = false;

    // Hapus baris yang ID-nya cocok (kolom pertama = ID)
    Iterator<String[]> iterator = data.iterator();
    while (iterator.hasNext()) {
        String[] row = iterator.next();
        if (row.length > 0 && row[0].equalsIgnoreCase(idToDelete)) {
            iterator.remove();
            found = true;
            break;
        }
    }

    if (found) {
        // Tulis ulang file CSV tanpa baris yang dihapus
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (String[] row : data) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    // Tangani koma dan kutip ganda di dalam data
                    String cell = row[i];
                    if (cell.contains(",") || cell.contains("\"")) {
                        cell = "\"" + cell.replace("\"", "\"\"") + "\"";
                    }
                    line.append(cell);
                    if (i < row.length - 1) {
                        line.append(",");
                    }
                }
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    return found;
}

public static boolean deletePeminjamanFromCSV(String fileName, Peminjaman target) {
    List<String[]> data = readCSV(fileName);
    boolean found = false;

    Iterator<String[]> iterator = data.iterator();
    while (iterator.hasNext()) {
        String[] row = iterator.next();
        if (row.length >= 6 &&
            row[0].equalsIgnoreCase(target.getNimNip()) &&
            row[2].equalsIgnoreCase(target.getKodeRuangan()) &&
            row[3].equalsIgnoreCase(target.getTanggal()) &&
            row[4].equalsIgnoreCase(target.getWaktuMulai()) &&
            row[5].equalsIgnoreCase(target.getWaktuSelesai())) {
            iterator.remove();
            found = true;
            break;
        }
    }

    if (found) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (String[] row : data) {
                writer.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    return found;
}

}
