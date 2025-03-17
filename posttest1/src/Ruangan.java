public class Ruangan {
    private final String kodeRuangan; // ID unik
    private int kapasitas;
    private String fasilitas;
    private String lokasi;

    public Ruangan(String kodeRuangan, int kapasitas, String fasilitas, String lokasi) {
        this.kodeRuangan = kodeRuangan;
        this.kapasitas = kapasitas;
        this.fasilitas = fasilitas;
        this.lokasi = lokasi;
    }

    public String getKodeRuangan() {
        return kodeRuangan;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    @Override
    public String toString() {
        return "Kode Ruangan: " + kodeRuangan + ", Kapasitas: " + kapasitas + 
               ", Fasilitas: " + fasilitas + ", Lokasi: " + lokasi;
    }
}
