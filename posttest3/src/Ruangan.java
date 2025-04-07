public class Ruangan {
    private final String kodeRuangan;
    private int kapasitas; 
    protected String fasilitas;
    public String lokasi;

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
        if (kapasitas > 0) {
            this.kapasitas = kapasitas;
        } else {
            System.out.println("Kapasitas harus lebih dari 0!");
        }
    }

    protected String getFasilitas() {
        return fasilitas;
    }

    protected void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJenis() {
        return "Ruangan Umum";
    }

    @Override
    public String toString() {
        return getJenis() +
               "\nKode Ruangan: " + kodeRuangan +
               "\nKapasitas   : " + kapasitas +
               "\nFasilitas   : " + fasilitas +
               "\nLokasi      : " + lokasi +
               "\n";
    }
}
