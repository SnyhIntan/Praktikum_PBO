public class RuangKelas extends Ruangan {
    public RuangKelas(String kodeRuangan, int kapasitas, String fasilitas, String lokasi) {
        super(kodeRuangan, kapasitas, fasilitas, lokasi);
    }

    @Override
    public String getJenis() {
        return "Ruang Kelas";
    }
}
