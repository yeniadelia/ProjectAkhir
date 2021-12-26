package projekFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projekFinal.db.DBHelper;

public class NasabahDataModel {
    public final Connection conn;

    public NasabahDataModel(String driver) throws SQLException {
        this.conn = DBHelper.getConnection(driver);
    }
    
    public void tambahNasabah(Individu n) throws SQLException{
        String insertNasabah = "INSERT INTO Nasabah (idNasabah, nama, alamat)"
                + " VALUES (?,?,?)";
        String insertIndividu = "INSERT INTO Individu (idNasabah, nik, npwp)"
                + " VALUES (?,?,?)";
        String insertRekening = "INSERT INTO Rekening (noRekening, saldo, idNasabah)"
                + " VALUES (?,?,?)";
        PreparedStatement stmtNasabah = conn.prepareStatement(insertNasabah);
        stmtNasabah.setInt(1, n.getIdNasabah());
        stmtNasabah.setString(2, n.getNama());
        stmtNasabah.setString(3, n.getAlamat());
        stmtNasabah.execute();
        
        PreparedStatement stmtIndividual = conn.prepareStatement(insertIndividu);
        stmtIndividual.setInt(1, n.getIdNasabah());
        stmtIndividual.setLong(2, n.getNik());
        stmtIndividual.setLong(3, n.getNpwp());
        stmtIndividual.execute();
        
        PreparedStatement stmtRekening = conn.prepareStatement(insertRekening);
        stmtRekening.setInt(1, n.getRekening().get(0).getNoRekening());
        stmtRekening.setDouble(2, n.getRekening().get(0).getSaldo());
        stmtRekening.setInt(3, n.getIdNasabah());
        stmtRekening.execute();
        
    }
    
    public void tambahNasabah(Perusahaan n) throws SQLException{
        String insertNasabah = "INSERT INTO Nasabah (idNasabah, nama, alamat)"
                + " VALUES (?,?,?)";
        String insertPerusahaan = "INSERT INTO Perusahaan (idNasabah, nib)"
                + " VALUES (?,?)";
        String insertRekening = "INSERT INTO Rekening (noRekening, saldo, idNasabah)"
                + " VALUES (?,?,?)";
        
        PreparedStatement stmtNasabah = conn.prepareStatement(insertNasabah);
        stmtNasabah.setInt(1, n.getIdNasabah());
        stmtNasabah.setString(2, n.getNama());
        stmtNasabah.setString(3, n.getAlamat());
        stmtNasabah.execute();
        
        PreparedStatement stmtPerusahaan = conn.prepareStatement(insertPerusahaan);
        stmtPerusahaan.setInt(1, n.getIdNasabah());
        stmtPerusahaan.setString(2, n.getNib());
        stmtPerusahaan.execute();
        
        PreparedStatement stmtRekening = conn.prepareStatement(insertRekening);
        stmtRekening.setInt(1, n.getRekening().get(0).getNoRekening());
        stmtRekening.setDouble(2, n.getRekening().get(0).getSaldo());
        stmtRekening.setInt(3, n.getIdNasabah());
        stmtRekening.execute();
    }
    
    public ObservableList<Individu> getIndividu(){
        ObservableList<Individu> data = FXCollections.observableArrayList();
        String sql="SELECT `idNasabah`, `nama`,`alamat`, `nik`, `npwp` "
                + "FROM `Nasabah` NATURAL JOIN `Individu` "
                + "ORDER BY nama";
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()){
                String sqlRekening = "SELECT noRekening, saldo "
                    + "FROM `Rekening` WHERE idNasabah=" + rs.getInt(1);
                ResultSet rsRekening = conn.createStatement().executeQuery(sqlRekening);
                
                ArrayList<Rekening> dataRekening = new ArrayList<>();
                while (rsRekening.next()){
                    dataRekening.add(new Rekening(rsRekening.getInt(1), rsRekening.getDouble(2)));
                }
                data.add(new Individu(rs.getInt(1),rs.getString(2),rs.getString(3), dataRekening, rs.getLong(4),rs.getLong(5)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }     
        return data;
    }
    
    public ObservableList<Perusahaan> getPerusahaan(){
        ObservableList<Perusahaan> data = FXCollections.observableArrayList();
        String sql="SELECT `idNasabah`, `nama`, `alamat`, `nib` "
                + "FROM `Nasabah` NATURAL JOIN `Perusahaan` "
                + "ORDER BY nama";
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()){
                String sqlRekening = "SELECT noRekening, saldo "
                    + "FROM `Rekening` WHERE idNasabah=" + rs.getInt(1);
                ResultSet rsRekening = conn.createStatement().executeQuery(sqlRekening);
                
                ArrayList<Rekening> dataRekening = new ArrayList<>();
                while (rsRekening.next()){
                    dataRekening.add(new Rekening(rsRekening.getInt(1),rsRekening.getDouble(2)));
                }
                data.add(new Perusahaan(rs.getInt(1), rs.getString(2), rs.getString(3), dataRekening, rs.getString(4)));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }     
        return data;
    }
    
    public ObservableList<Rekening> getRekening(int idNasabah){
        ObservableList<Rekening> data = FXCollections.observableArrayList();
        String sql="SELECT `noRekening`, `saldo` "
                + "FROM `Rekening` "
                + "WHERE idNasabah=" + idNasabah;
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()){
                data.add(new Rekening(rs.getInt(1), rs.getDouble(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public int nextIdNasabah() throws SQLException{
        String sql="SELECT MAX(idNasabah) from Nasabah";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()){
                return rs.getInt(1)==0?1000001:rs.getInt(1) + 1;
            }
        return 1000001;
    }
    
    public int nextNoRekening(int idNasabah) throws SQLException{
        String sql="SELECT MAX(noRekening) FROM Rekening WHERE idNasabah=" + idNasabah;
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()){
                return rs.getInt(1) + 1;
            }
        return 0;
    }
    
    public void tambahRekening(int idNasabah, Rekening rek) throws SQLException{
        String insertNasabah = "INSERT INTO Rekening (idNasabah, noRekening, saldo)"
                + " VALUES (?,?,?)";
  
        PreparedStatement stmtNasabah = conn.prepareStatement(insertNasabah);
        stmtNasabah.setInt(1, idNasabah);
        stmtNasabah.setInt(2, rek.getNoRekening());
        stmtNasabah.setDouble(3, rek.getSaldo());
        stmtNasabah.execute();
    }
    
    public void tambahSaldo(Rekening rek, double jumlah) throws SQLException{
        String insertNasabah = "UPDATE Rekening "
                + "SET saldo = ? "
                + "WHERE noRekening = ?";
        
        PreparedStatement stmtNasabah = conn.prepareStatement(insertNasabah);
        stmtNasabah.setDouble(1, (rek.getSaldo() + jumlah));
        stmtNasabah.setInt(2, rek.getNoRekening());
        stmtNasabah.execute();
    }
    
    public void tarikSaldo(Rekening rek, double jumlah) throws SQLException{
        String insertNasabah = "UPDATE Rekening "
                + "SET saldo = ? "
                + "WHERE noRekening = ?";
        
        PreparedStatement stmtNasabah = conn.prepareStatement(insertNasabah);
        stmtNasabah.setDouble(1, (rek.getSaldo() - jumlah));
        stmtNasabah.setInt(2, rek.getNoRekening());
        stmtNasabah.execute();
    }
}