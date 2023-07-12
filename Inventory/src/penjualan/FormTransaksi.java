/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package penjualan;

/**
 *
 * @author ACER
 */
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.sql.*;
import java.text.*;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class FormTransaksi extends javax.swing.JFrame {
Connection Con;
ResultSet RsBrg;
ResultSet RsKons;
Statement stm;
double total=0;
String tanggal;
Boolean edit=false;
PreparedStatement pstat;
String sKd_Kons;
String sKd_Brg;
String xnojual;

DefaultTableModel tableModel = new DefaultTableModel(new Object [][]{}, new String [] {"Kd Barang", "Nama Barang","Harga Barang","Jumlah","Total"});

public FormTransaksi() {
initComponents();
open_db();
inisialisasi_tabel();
aktif(false);
setTombol(true);
btCetak.setEnabled(false);
txtTgl.setEditor(new JSpinner.DateEditor(txtTgl, "yyyy/MM/dd"));
}
private void setField()
{
int row=tblJual.getSelectedRow();
cmbKd_Brg.setSelectedItem((String)tblJual.getValueAt(row,0));
txtNm_Brg.setText((String)tblJual.getValueAt(row,1));
String hargaJual = Double.toString((Double)tblJual.getValueAt(row,2));
txtHarga.setText(hargaJual);
String jumlah=Integer.toString((Integer)tblJual.getValueAt(row,3));
txtJml.setText(jumlah);
String total=Double.toString((Double)tblJual.getValueAt(row,4));
txtTot.setText(total);
}

private void hitung_jual()
{
double xtot,xhrg;
int xjml;
xhrg=Double.parseDouble(txtHarga.getText());
xjml=Integer.parseInt(txtJml.getText());
xtot=xhrg*xjml;
String xtotal=Double.toString(xtot);
txtTot.setText(xtotal);
total=total+xtot;
txtTotal.setText(Double.toString(total));
}
private void open_db()
{
try{
KoneksiMysql kon = new KoneksiMysql ("localhost","root","","penjualan");
Con = kon.getConnection();
//System.out.println("Berhasil ");
}catch (Exception e) {
System.out.println("Error : "+e);
}
}
private void baca_konsumen()
{
try{
stm=Con.createStatement();
pstat = Con.prepareStatement("select kd_kons,nm_kons from konsumen",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
ResultSet rs = pstat.executeQuery();
rs.beforeFirst();
while(rs.next())
{
cmbKd_Kons.addItem(rs.getString(1).trim());
}
rs.close();
}catch(SQLException e){
System.out.println("Error : "+e);
}
}
public void inisialisasi_tabel()
{
tblJual.setModel(tableModel);
}

private void baca_barang()
{

try{
stm=Con.createStatement();
pstat = Con.prepareStatement("select * from barang",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
ResultSet rs = pstat.executeQuery();
rs.beforeFirst();
while(rs.next())
{
cmbKd_Brg.addItem(rs.getString(1).trim());
}
rs.close();
}catch(SQLException e){
System.out.println("Error : "+e);
}
}
private void detail_barang(String xkode)
{
try{
stm=Con.createStatement();
pstat = Con.prepareStatement("select * from barang where kd_brg='"+xkode+"'",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
ResultSet rs = pstat.executeQuery();
rs.beforeFirst();
while(rs.next())
{
txtNm_Brg.setText(rs.getString(2).trim());
txtHarga.setText(Double.toString((Double)rs.getDouble(7)));
}
rs.close();
}catch(SQLException e){
System.out.println("Error : "+e);
}
}
private void setTombol(boolean t)
{
btTambah.setEnabled(t);
btSimpan.setEnabled(!t);
btBatal.setEnabled(!t);
btKeluar.setEnabled(t);
btHapusItem.setEnabled(!t);
}
private void detail_konsumen(String xkode)
{
try{
stm=Con.createStatement();

pstat = Con.prepareStatement("select * from konsumen where kd_kons='"+xkode+"'",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
ResultSet rs = pstat.executeQuery();
rs.beforeFirst();
while(rs.next()){
txtNama.setText(rs.getString(2).trim());
}
rs.close();
}catch(SQLException e){
System.out.println("Error : "+e);
}
}
private void kosong()
{
txtNoJual.setText("");
txtNama.setText("");
txtHarga.setText("");
txtTotal.setText("");
text.setText("");
}
private void kosong_detail()
{
txtNm_Brg.setText("");
txtHarga.setText("");
txtJml.setText("");
txtTot.setText("");
}
private void aktif(boolean x)
{
cmbKd_Kons.setEnabled(x);
cmbKd_Brg.setEnabled(x);
cmbKd_Kons.removeAllItems();
cmbKd_Brg.removeAllItems();
txtTgl.setEnabled(x);
txtJml.setEditable(x);
}
private void nomor_jual()
{
try{
stm=Con.createStatement();
pstat = Con.prepareStatement("SELECT no_jual FROM jual ORDER BY no_jual DESC LIMIT1",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
ResultSet rs = pstat.executeQuery();
int brs=0;
while(rs.next()){
brs=rs.getRow();
}

if(brs==0)
txtNoJual.setText("1");
else{
rs.beforeFirst();
while(rs.next()) {
int no=rs.getInt("no_jual")+1;
txtNoJual.setText(Integer.toString(no));
}
}
rs.close();
}catch(SQLException e){
System.out.println("Error : "+e);
}
}
private void format_tanggal()
{
String DATE_FORMAT = "yyyy-MM-dd";
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
Calendar c1 = Calendar.getInstance();
int year=c1.get(Calendar.YEAR);
int month=c1.get(Calendar.MONTH)+1;
int day=c1.get(Calendar.DAY_OF_MONTH);
tanggal=Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
}
private void simpan_ditabel()
{
try{
String tKode=cmbKd_Brg.getSelectedItem().toString();
String tNama=txtNm_Brg.getText();
double hrgJ=Double.parseDouble(txtHarga.getText());
int jml=Integer.parseInt(txtJml.getText());
double tot=Double.parseDouble(txtTot.getText());
tableModel.addRow(new Object[]{tKode,tNama,hrgJ,jml,tot});
inisialisasi_tabel();
}catch(Exception e){
System.out.println("Error : "+e);
}
}
private void simpan_transaksi()
{
try{
xnojual=txtNoJual.getText();
format_tanggal();
String xkode=cmbKd_Kons.getSelectedItem().toString();
String msql="insert into jual values('"+xnojual+"','"+xkode+"','"+tanggal+"')";
stm.executeUpdate(msql);
for(int i=0;i<tblJual.getRowCount();i++)
{
String xkd=(String)tblJual.getValueAt(i,0);
double xhrg=(Double)tblJual.getValueAt(i,2);
int xjml=(Integer)tblJual.getValueAt(i,3);

String zsql="insert into djual values('"+xnojual+"','"+xkd+"',"+xhrg+","+xjml+")";
stm.executeUpdate(zsql);
//update stok
String ysql="update barang set stok=stok-"+xjml+" where kd_brg='"+xkd+"'";
stm.executeUpdate(ysql);
}
}catch(SQLException e){
System.out.println("Error : "+e);
}
}
private class PrintingTask extends SwingWorker<Object, Object> {
private final MessageFormat headerFormat;
private final MessageFormat footerFormat;
private final boolean interactive;
private volatile boolean complete = false;
private volatile String message;
public PrintingTask(MessageFormat header, MessageFormat footer, boolean interactive) {
this.headerFormat = header;
this.footerFormat = footer;
this.interactive = interactive;
}
@Override
protected Object doInBackground() {
try {
complete = text.print(headerFormat, footerFormat, true, null, null, interactive);
message = "Printing " + (complete ? "complete" : "canceled");
} catch (PrinterException ex) {
message = "Sorry, a printer error occurred";
} catch (SecurityException ex) {
message = "Sorry, cannot access the printer due to security reasons";
}
return null;
}
@Override
protected void done() {
//message(!complete, message);
}
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNoJual = new javax.swing.JTextField();
        cmbKd_Kons = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        cmbKd_Brg = new javax.swing.JComboBox<>();
        txtNm_Brg = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtJml = new javax.swing.JTextField();
        txtTot = new javax.swing.JTextField();
        btHapusItem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblJual = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btTambah = new javax.swing.JButton();
        btSimpan = new javax.swing.JButton();
        btBatal = new javax.swing.JButton();
        btCetak = new javax.swing.JButton();
        btKeluar = new javax.swing.JButton();
        txtTgl = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("No Jual");

        jLabel2.setText("Tgl Jual");

        txtNoJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoJualActionPerformed(evt);
            }
        });

        cmbKd_Kons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKd_KonsActionPerformed(evt);
            }
        });

        jLabel3.setText("Kode Konsumen");

        jLabel4.setText("Nama Konsumen");

        cmbKd_Brg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKd_BrgActionPerformed(evt);
            }
        });

        txtJml.setText("Jumlah Jual");
        txtJml.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtJmlKeyPressed(evt);
            }
        });

        btHapusItem.setText("Hapus Item");
        btHapusItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHapusItemActionPerformed(evt);
            }
        });

        tblJual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kd Brg", "Nama Barang", "Harga Barang", "Jumlah", "Total"
            }
        ));
        tblJual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblJualMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblJual);

        text.setColumns(20);
        text.setRows(5);
        jScrollPane2.setViewportView(text);

        jLabel5.setText("Total");

        btTambah.setText("Tambah");
        btTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTambahActionPerformed(evt);
            }
        });

        btSimpan.setText("Simpan");
        btSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSimpanActionPerformed(evt);
            }
        });

        btBatal.setText("Batal");
        btBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBatalActionPerformed(evt);
            }
        });

        btCetak.setText("Cetak");
        btCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCetakActionPerformed(evt);
            }
        });

        btKeluar.setText("Keluar");
        btKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKeluarActionPerformed(evt);
            }
        });

        txtTgl.setModel(new javax.swing.SpinnerDateModel());

        jLabel6.setText("TRANSAKSI PENJUALAN");

        jLabel7.setText("HASIL CETAK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(182, 182, 182)
                                .addComponent(jLabel6))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btHapusItem)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(btCetak)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5)
                                    .addGap(28, 28, 28)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbKd_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNm_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNoJual, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbKd_Kons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtJml, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btSimpan)
                            .addComponent(btTambah)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btKeluar)
                                .addComponent(btBatal)))
                        .addGap(27, 27, 27))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNoJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbKd_Kons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btTambah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSimpan))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbKd_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNm_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJml, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btBatal))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btHapusItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btCetak))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btKeluar))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNoJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoJualActionPerformed

    private void cmbKd_KonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKd_KonsActionPerformed
JComboBox cKd_Kons = (javax.swing.JComboBox)evt.getSource();
//Membaca Item Yang Terpilih — > String
sKd_Kons = (String)cKd_Kons.getSelectedItem();
detail_konsumen(sKd_Kons);
    }//GEN-LAST:event_cmbKd_KonsActionPerformed

    private void cmbKd_BrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKd_BrgActionPerformed
JComboBox cKd_Brg = (javax.swing.JComboBox)evt.getSource();
//Membaca Item Yang Terpilih — > String
sKd_Brg = (String)cKd_Brg.getSelectedItem();
detail_barang(sKd_Brg);
txtJml.setText("");
txtTot.setText("");
    }//GEN-LAST:event_cmbKd_BrgActionPerformed

    private void txtJmlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJmlKeyPressed
if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
hitung_jual();
simpan_ditabel();
}
    }//GEN-LAST:event_txtJmlKeyPressed

    private void btHapusItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHapusItemActionPerformed
DefaultTableModel dataModel = (DefaultTableModel) tblJual.getModel();
int row=tblJual.getSelectedRow();
if (tblJual.getRowCount() > 0) {
dataModel.removeRow(row);
}
double xtot,xhrg;
int xjml;
xhrg=Double.parseDouble(txtHarga.getText());
xjml=Integer.parseInt(txtJml.getText());
xtot=xhrg*xjml;
total=total-xtot;
txtTotal.setText(Double.toString(total));
    }//GEN-LAST:event_btHapusItemActionPerformed

    private void tblJualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblJualMouseClicked
        setField();
    }//GEN-LAST:event_tblJualMouseClicked

    private void btTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTambahActionPerformed
 aktif(true);
setTombol(false);
kosong();
baca_konsumen();
baca_barang();
nomor_jual();
    }//GEN-LAST:event_btTambahActionPerformed

    private void btSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSimpanActionPerformed
simpan_transaksi();
aktif(false);
setTombol(true);
kosong();
kosong_detail();
btCetak.setEnabled(true);
    }//GEN-LAST:event_btSimpanActionPerformed

    private void btBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBatalActionPerformed
aktif(false);
setTombol(true);
kosong();
    }//GEN-LAST:event_btBatalActionPerformed

    private void btCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCetakActionPerformed
        format_tanggal();
String ctk="Nota Penjualan\nNo : "+xnojual+"\nTanggal : "+tanggal;
ctk=ctk+"\n"+"-------------------------------------------------------------------------------------------------";
ctk=ctk+"\n"+"Kode\tNama Barang\tHarga\tJml\tTotal";
ctk=ctk+"\n"+"-------------------------------------------------------------------------------------------------";
for(int i=0;i<tblJual.getRowCount();i++)
{
String xkd=(String)tblJual.getValueAt(i,0);
String xnama=(String)tblJual.getValueAt(i,1);
double xhrg=(Double)tblJual.getValueAt(i,2);
int xjml=(Integer)tblJual.getValueAt(i,3);
double xtot=(Double)tblJual.getValueAt(i,4);
ctk=ctk+"\n"+xkd+"\t"+xnama+"\t"+xhrg+"\t"+xjml+"\t"+xtot;
}

ctk=ctk+"\n"+"-------------------------------------------------------------------------------------------------";
text.setText(ctk);
String headerField="";
String footerField="";
MessageFormat header = new MessageFormat(headerField);
MessageFormat footer = new MessageFormat(footerField);;
boolean interactive = true;//interactiveCheck.isSelected();
boolean background = true;//backgroundCheck.isSelected();
PrintingTask task = new PrintingTask(header, footer, interactive);
if (background) {
task.execute();
} else {
task.run();
}
    }//GEN-LAST:event_btCetakActionPerformed

    private void btKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btKeluarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBatal;
    private javax.swing.JButton btCetak;
    private javax.swing.JButton btHapusItem;
    private javax.swing.JButton btKeluar;
    private javax.swing.JButton btSimpan;
    private javax.swing.JButton btTambah;
    private javax.swing.JComboBox<String> cmbKd_Brg;
    private javax.swing.JComboBox<String> cmbKd_Kons;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblJual;
    private javax.swing.JTextArea text;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJml;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNm_Brg;
    private javax.swing.JTextField txtNoJual;
    private javax.swing.JSpinner txtTgl;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
