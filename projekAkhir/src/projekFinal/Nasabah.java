package projekFinal;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

abstract class Nasabah {

    protected IntegerProperty idNasabah;
    protected StringProperty nama;
    protected StringProperty alamat;
    protected IntegerProperty numRekening;
    protected ArrayList<Rekening> rekening = new ArrayList<Rekening>();

    public Nasabah(int idNasabah, String nama, String alamat, ArrayList<Rekening> rekening) {
        this.idNasabah = new SimpleIntegerProperty(idNasabah);
        this.nama = new SimpleStringProperty(nama);
        this.alamat = new SimpleStringProperty(alamat);
        this.rekening = rekening;
        this.numRekening = new SimpleIntegerProperty(rekening.size());
    }
    
    public Nasabah(int idNasabah, String nama, String alamat, Rekening rek) {
        this.nama = new SimpleStringProperty(nama);
        this.alamat = new SimpleStringProperty(alamat);
        this.idNasabah = new SimpleIntegerProperty(idNasabah);
        this.rekening.add(rek);
        this.numRekening = new SimpleIntegerProperty(rekening.size());
    }
    
    public int getIdNasabah() {
        return idNasabah.get();
    }

    public void setIdNasabah(int idNasabah) {
        this.idNasabah.set(idNasabah);
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    public String getAlamat() {
        return alamat.get();
    }

    public void setAlamat(String alamat) {
        this.alamat.set(alamat);
    }

    public int getNumRekening() {
        return numRekening.get();
    }

    public void setNumRekening(int numRekening) {
        this.numRekening.set(numRekening);
    }

    public ArrayList<Rekening> getRekening() {
        return rekening;
    }

    public void setRekening(ArrayList<Rekening> rekening) {
        this.rekening = rekening;
    }
    
    public void tambahRekening(Rekening rekening){
        this.rekening.add(rekening);
    }
    
    public IntegerProperty IdNasabahProperty(){
        return idNasabah;
    }
    
    public StringProperty namaProperty(){
        return nama;
    }
    
    public StringProperty alamatProperty(){
        return alamat;
    }
    
    public IntegerProperty numRekeningProperty(){
        return numRekening;
    }
}
