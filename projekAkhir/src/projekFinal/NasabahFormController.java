package projekFinal;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class NasabahFormController implements Initializable {
    
    //individu
    @FXML
    private TextField tfIdNasabah;

    @FXML
    private TextField tfNama;

    @FXML
    private TextField tfAlamat;

    @FXML
    private TextField tfNik;

    @FXML
    private TextField tfNpwp;

    @FXML
    private TextField tfNoRekening;

    @FXML
    private TextField tfSaldo;

    @FXML
    private Button btnReload;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<Individu> tblNasabah;

    @FXML
    private TableColumn<Individu, Integer> colIdNasabah;

    @FXML
    private TableColumn<Individu, String> colNama;

    @FXML
    private TableColumn<Individu, String> colAlamat;

    @FXML
    private TableColumn<Individu, Long> colNik;

    @FXML
    private TableColumn<Individu, Long> colNpwp;

    @FXML
    private TableColumn<Individu, Integer> colNumRekening;

    @FXML
    private TableView<Rekening> tblRekening;

    @FXML
    private TableColumn<Rekening, Integer> colNoRekening;

    @FXML
    private TableColumn<Rekening, Double> colSaldo;

    @FXML
    private TextField tfIdNasabahBaru;

    @FXML
    private TextField tfNoRekeningBaru;

    @FXML
    private TextField tfSaldoRekeningBaru;
    
    @FXML
    private TextField tfJumlah;

    @FXML
    private Button btnTambahRekening;
    
    @FXML
    private Label lblDBStatus;
    
    @FXML
    private Label lblSaveStatus;
    
    
    //perusahaan
    @FXML
    private TextField tfIdNasabahP;

    @FXML
    private TextField tfNamaP;

    @FXML
    private TextField tfAlamatP;

    @FXML
    private TextField tfNib;

    @FXML
    private TextField tfNoRekeningP;

    @FXML
    private TextField tfSaldoP;

    @FXML
    private Button btnReloadP;

    @FXML
    private Button btnClearP;

    @FXML
    private TableView<Perusahaan> tblNasabahP;

    @FXML
    private TableColumn<Perusahaan, Integer> colIdNasabahP;

    @FXML
    private TableColumn<Perusahaan, String> colNamaP;

    @FXML
    private TableColumn<Perusahaan, String> colAlamatP;

    @FXML
    private TableColumn<Perusahaan, String> colNib;

    @FXML
    private TableColumn<Perusahaan, Integer> colNumRekeningP;

    @FXML
    private TableView<Rekening> tblRekeningP;

    @FXML
    private TableColumn<Rekening, Integer> colNoRekeningP;

    @FXML
    private TableColumn<Rekening, Double> colSaldoP;

    @FXML
    private TextField tfIdNasabahBaruP;

    @FXML
    private TextField tfNoRekeningBaruP;

    @FXML
    private TextField tfSaldoRekeningBaruP;

    @FXML
    private TextField tfJumlahP;

    @FXML
    private Button btnTambahRekeningP;
    
    @FXML
    private Label lblSaveStatusP;
    
  
    private NasabahDataModel ndm;
  
    
    //individu
    @FXML
    void handleAddAccountButton(ActionEvent event) {
        try {
            Rekening rek =  new Rekening(Integer.parseInt(tfNoRekeningBaru.getText()),
                            Double.parseDouble(tfSaldoRekeningBaru.getText()));
            
            ndm.tambahRekening(Integer.parseInt(tfIdNasabahBaru.getText()), rek);          
            viewDataRekening(Integer.parseInt(tfIdNasabahBaru.getText()));
            btnReload.fire();
            tfSaldoRekeningBaru.setText("");
            
        } catch (SQLException ex) {
            Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleAddHolderButton(ActionEvent event) {
        Individu i = new Individu(Integer.parseInt(tfIdNasabah.getText()),
                tfNama.getText(),
                tfAlamat.getText(),
                new Rekening(Integer.parseInt(tfNoRekening.getText()), Double.parseDouble(tfSaldo.getText())),
                Long.parseLong(tfNik.getText()),
                Long.parseLong(tfNpwp.getText()));
        try {
            ndm.tambahNasabah(i);
            lblSaveStatus.setText("Rekening berhasil dibuat");
            btnReload.fire();
            btnClear.fire();
        } catch (SQLException ex) {
            lblSaveStatus.setText("Rekening gagal dibuat");
            Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    void handleClearButton(ActionEvent event) {
        try {
            tfIdNasabah.setText("" + ndm.nextIdNasabah());
            tfNoRekening.setText(tfIdNasabah.getText() + "01");
            tfNama.setText("");
            tfAlamat.setText("");
            tfNik.setText("");
            tfNpwp.setText("");
            tfSaldo.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @FXML
    void handleReloadButton(ActionEvent event) {
        ObservableList<Individu> data = ndm.getIndividu();
        colIdNasabah.setCellValueFactory(new PropertyValueFactory<>("IdNasabah"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colNik.setCellValueFactory(new PropertyValueFactory<>("nik"));
        colNpwp.setCellValueFactory(new PropertyValueFactory<>("npwp"));
        colNumRekening.setCellValueFactory(new PropertyValueFactory<>("numRekening"));
        tblNasabah.setItems(null);
        tblNasabah.setItems(data);
        btnTambahRekening.setDisable(true);
    }
  
    @FXML
    void handleDepositButton(ActionEvent event) {
        Rekening rek = tblRekening.getSelectionModel().getSelectedItem();
         try {
             ndm.tambahSaldo(rek, Double.parseDouble(tfJumlah.getText()));
             btnReload.fire();
             viewDataRekening(Integer.parseInt(tfIdNasabahBaru.getText()));
             tfJumlah.setText("");
         } catch (SQLException ex) {
             Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @FXML
    void handleWithdrawButton(ActionEvent event) {
        Rekening rek = tblRekening.getSelectionModel().getSelectedItem();
        
        if (rek.getSaldo() >= Double.parseDouble(tfJumlah.getText())){
            try {
                ndm.tarikSaldo(rek, Double.parseDouble(tfJumlah.getText()));
                btnReload.fire();
                viewDataRekening(Integer.parseInt(tfIdNasabahBaru.getText()));
                tfJumlah.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //perusahaan
    @FXML
    void handleAddCorpAccountButton(ActionEvent event) {
        try {
            Rekening rek =  new Rekening(Integer.parseInt(tfNoRekeningBaruP.getText()),
                            Double.parseDouble(tfSaldoRekeningBaruP.getText()));
            
            ndm.tambahRekening(Integer.parseInt(tfIdNasabahBaruP.getText()), rek);          
            viewDataRekeningP(Integer.parseInt(tfIdNasabahBaruP.getText()));
            btnReloadP.fire();
            tfSaldoRekeningBaruP.setText("");
            
        } catch (SQLException ex) {
            Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleAddCorpHolderButton(ActionEvent event) {
        Perusahaan p = new Perusahaan(Integer.parseInt(tfIdNasabahP.getText()),
                tfNamaP.getText(),
                tfAlamatP.getText(),
                new Rekening(Integer.parseInt(tfNoRekeningP.getText()), Double.parseDouble(tfSaldoP.getText())),
                tfNib.getText());
        try {
            ndm.tambahNasabah(p);
            lblSaveStatusP.setText("Rekening berhasil dibuat");
            btnReloadP.fire();
            btnClearP.fire();
        } catch (SQLException ex) {
            lblSaveStatusP.setText("Rekening gagal dibuat");
            Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleCorpClearButton(ActionEvent event) {
        try {
            tfIdNasabahP.setText("" + ndm.nextIdNasabah());
            tfNoRekeningP.setText(tfIdNasabahP.getText() + "01");
            tfNamaP.setText("");
            tfAlamatP.setText("");
            tfNib.setText("");
            tfSaldoP.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @FXML
    void handleCorpReloadButton(ActionEvent event) {
        ObservableList<Perusahaan> data = ndm.getPerusahaan();
        colIdNasabahP.setCellValueFactory(new PropertyValueFactory<>("IdNasabah"));
        colNamaP.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colAlamatP.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colNib.setCellValueFactory(new PropertyValueFactory<>("nib"));
        colNumRekeningP.setCellValueFactory(new PropertyValueFactory<>("numRekening"));
        tblNasabahP.setItems(null);
        tblNasabahP.setItems(data);
        btnTambahRekeningP.setDisable(true);
    }

    @FXML
    void handleDepositButtonP(ActionEvent event) {
        Rekening rek = tblRekeningP.getSelectionModel().getSelectedItem();
         try {
             ndm.tambahSaldo(rek, Double.parseDouble(tfJumlahP.getText()));
             btnReloadP.fire();
             viewDataRekeningP(Integer.parseInt(tfIdNasabahBaruP.getText()));
             tfJumlahP.setText("");
         } catch (SQLException ex) {
             Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    void handleWithdrawButtonP(ActionEvent event) {
        Rekening rek = tblRekeningP.getSelectionModel().getSelectedItem();
        
        if (rek.getSaldo() >= Double.parseDouble(tfJumlahP.getText())){
            try {
                ndm.tarikSaldo(rek, Double.parseDouble(tfJumlahP.getText()));
                btnReloadP.fire();
                viewDataRekeningP(Integer.parseInt(tfIdNasabahBaruP.getText()));
                tfJumlahP.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ndm = new NasabahDataModel("SQLite");
            lblDBStatus.setText(ndm.conn == null ? "Not Connected" : "Connected");
            btnClear.fire();
            btnReload.fire();
            btnClearP.fire();
            btnReloadP.fire();
        } catch (SQLException ex) {
            Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //individu
        tblNasabah.getSelectionModel().selectedIndexProperty().addListener(listener->{
            if (tblNasabah.getSelectionModel().getSelectedItem() != null){
                Individu i =  tblNasabah.getSelectionModel().getSelectedItem();
                viewDataRekening(i.getIdNasabah());
                
                btnTambahRekening.setDisable(false);
                tfIdNasabahBaru.setText("" + i.getIdNasabah());
                try {
                    tfNoRekeningBaru.setText("" + ndm.nextNoRekening(i.getIdNasabah()));
                } catch (SQLException ex) {
                    Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //perusahaan
        tblNasabahP.getSelectionModel().selectedIndexProperty().addListener(listener->{
            if (tblNasabahP.getSelectionModel().getSelectedItem() != null){
                Perusahaan p =  tblNasabahP.getSelectionModel().getSelectedItem();
                viewDataRekeningP(p.getIdNasabah());
                
                btnTambahRekeningP.setDisable(false);
                tfIdNasabahBaruP.setText("" + p.getIdNasabah());
                try {
                    tfNoRekeningBaruP.setText("" + ndm.nextNoRekening(p.getIdNasabah()));
                } catch (SQLException ex) {
                    Logger.getLogger(NasabahFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    //individu
    public void viewDataRekening(int idNasabah){
        ObservableList<Rekening> data = ndm.getRekening(idNasabah);
        colNoRekening.setCellValueFactory(new PropertyValueFactory<>("noRekening"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        tblRekening.setItems(null);
        tblRekening.setItems(data);
    }

    //perusahaan
    public void viewDataRekeningP(int idNasabah){
        ObservableList<Rekening> data = ndm.getRekening(idNasabah);
        colNoRekeningP.setCellValueFactory(new PropertyValueFactory<>("noRekening"));
        colSaldoP.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        tblRekeningP.setItems(null);
        tblRekeningP.setItems(data);
    }
}