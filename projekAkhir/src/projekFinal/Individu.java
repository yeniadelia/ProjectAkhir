package projekFinal;

import java.util.ArrayList;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.LongProperty;

public class Individu extends Nasabah{
    private LongProperty nik;
    private LongProperty npwp;

    public Individu(int idNasabah, String nama, String alamat, ArrayList<Rekening> rekening, long nik, long npwp) {
        super(idNasabah, nama, alamat,  rekening);
        this.nik = new SimpleLongProperty(nik);
        this.npwp = new SimpleLongProperty(npwp);
    }
    
    public Individu(int idNasabah, String nama, String alamat, Rekening rek, Long nik, Long npwp) {
        super(idNasabah, nama, alamat, rek);
        this.nik = new SimpleLongProperty(nik);
        this.npwp = new SimpleLongProperty(npwp);
    }

    public long getNik() {
        return nik.get();
    }

    public void setNik(long nik) {
        this.nik.set(nik);
    }

    public long getNpwp() {
        return npwp.get();
    }

    public void setNpwp(long npwp) {
        this.npwp.set(npwp);
    }
    
    public LongProperty nikProperty() {
        return nik;
    }

    public LongProperty npwpProperty() {
        return npwp;
    }
}
