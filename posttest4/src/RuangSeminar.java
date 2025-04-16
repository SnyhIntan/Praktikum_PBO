public class RuangSeminar extends Ruangan {
    public RuangSeminar(String kodeRuangan, int kapasitas, String fasilitas, String lokasi) {
        super(kodeRuangan, kapasitas, fasilitas, lokasi);
    }

    @Override
    public String getJenis() {
        return "Ruang Seminar";
    }
}
