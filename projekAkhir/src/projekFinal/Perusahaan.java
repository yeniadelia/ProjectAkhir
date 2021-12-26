package projekFinal;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Perusahaan extends Nasabah{
    private StringProperty nib;

    public Perusahaan(int idNasabah, String nama, String alamat, ArrayList<Rekening> rekening, String nib) {
        super(idNasabah, nama, alamat, rekening);
        this.nib = new SimpleStringProperty(nib);
    }
    
    public Perusahaan(int idNasabah, String nama, String alamat, Rekening rek, String nib) {
        super(idNasabah, nama, alamat, rek);
        this.nib = new SimpleStringProperty(nib);
    }

    public String getNib() {
        return nib.get();
    }

    public void setNib(String nib) {
        this.nib.set(nib);
    }
    
    public StringProperty nibProperty() {
        return nib;
    }
}
