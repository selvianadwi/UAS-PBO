/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package penjualan;

import java.io.File;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author ACER
 */
public class FormKonsumen extends javax.swing.JFrame {

    /**
     * Creates new form frmKonsumen
     */
    Connection Con;
    ResultSet RsKons;
    Statement stm;
    PreparedStatement pstat;
    
    Boolean ada = false;
    Boolean edit = false;
    
    private Object[][] dataTable = null;
    private String[] header = {"Kode", "Nama Konsumen", "Alamat", "Kota", "Kode Pos", "Phone", "Email"};
    
    public FormKonsumen() {
        initComponents();
        open_db();
        baca_data();
        aktif(false);
        setTombol(true);
    }
    
    private void setField() {
        int row = tblKons.getSelectedRow();
        txtKd_Kons.setText((String)tblKons.getValueAt(row, 0));
        txtNm_Kons.setText((String)tblKons.getValueAt(row, 1));
        txtAlamat.setText((String)tblKons.getValueAt(row, 2));
        txtKota.setText((String)tblKons.getValueAt(row, 3));
        txtKd_Pos.setText((String)tblKons.getValueAt(row, 4));
        txtTlpn.setText((String)tblKons.getValueAt(row, 5));
        txtEmail.setText((String)tblKons.getValueAt(row, 6));
    }
    private void open_db() {
        try {
            KoneksiMysql kon = new KoneksiMysql("localhost:3307", "root", "", "penjualan");
            Con = kon.getConnection();
            System.out.println("Berhasil");
        } catch (Exception e) {
            System.out.println("Error : "+e);
        }
    }
    
    private void baca_data() {
        try {
            stm = Con.createStatement();
            
            pstat = Con.prepareStatement("select * from konsumen",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            RsKons = pstat.executeQuery();
            
            ResultSetMetaData meta = RsKons.getMetaData();
            int col = meta.getColumnCount();
            int baris = 0;
            
            while(RsKons.next()){
                baris = RsKons.getRow();
            }
            
            dataTable = new Object[baris][col];
            int x = 0;
            RsKons.beforeFirst();
            
            while(RsKons.next()) {
                dataTable[x][0] = RsKons.getString("kd_kons");
                dataTable[x][1] = RsKons.getString("nm_kons");
                dataTable[x][2] = RsKons.getString("alm_kons");
                dataTable[x][3] = RsKons.getString("kota_kons");
                dataTable[x][4] = RsKons.getString("kd_pos");
                dataTable[x][5] = RsKons.getString("phone");
                dataTable[x][6] = RsKons.getString("email");
                x++;
            }
            tblKons.setModel(new DefaultTableModel(dataTable, header));
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void kosong() {
        txtKd_Kons.setText("");
        txtNm_Kons.setText("");
        txtAlamat.setText("");
        txtKota.setText("");
        txtKd_Pos.setText("");
        txtTlpn.setText("");
        txtEmail.setText("");
    }
    
    //mengset aktif tidak isian data
    private void aktif(boolean x) {
        txtKd_Kons.setEditable(x);
        txtNm_Kons.setEditable(x);
        txtAlamat.setEditable(x);
        txtKota.setEditable(x);
        txtKd_Pos.setEditable(x);
        txtTlpn.setEditable(x);
        txtEmail.setEditable(x);
    }
        
    //Mengset tombol on/off
    private void setTombol(boolean t) {
        btTambah.setEnabled(t);
        btKoreksi.setEnabled(t);
        btHapus.setEnabled(t);
        btSimpan.setEnabled(!t);
        btBatal.setEnabled(!t);
        btKeluar.setEnabled(t);
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
        txtKd_Kons = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNm_Kons = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtAlamat = new javax.swing.JTextField();
        txtKota = new javax.swing.JTextField();
        txtKd_Pos = new javax.swing.JTextField();
        txtTlpn = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        btTambah = new javax.swing.JButton();
        btSimpan = new javax.swing.JButton();
        btKoreksi = new javax.swing.JButton();
        btHapus = new javax.swing.JButton();
        btBatal = new javax.swing.JButton();
        btKeluar = new javax.swing.JButton();
        btExport = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKons = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("DATA KONSUMEN");

        jLabel2.setText("Kode Konsumen");

        txtKd_Kons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKd_KonsActionPerformed(evt);
            }
        });

        jLabel3.setText("Nama Konsumen");

        jLabel4.setText("Alamat");

        jLabel5.setText("Kota");

        jLabel6.setText("Kode Pos");

        jLabel7.setText("Telepon");

        jLabel8.setText("Email");

        txtKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKotaActionPerformed(evt);
            }
        });

        txtTlpn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTlpnActionPerformed(evt);
            }
        });

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        btTambah.setText("Tambah");
        btTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTambahActionPerformed(evt);
            }
        });

        btSimpan.setText("Simpan");
        btSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btSimpanMouseClicked(evt);
            }
        });

        btKoreksi.setText("Update");
        btKoreksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKoreksiActionPerformed(evt);
            }
        });

        btHapus.setText("Hapus");
        btHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHapusActionPerformed(evt);
            }
        });

        btBatal.setText("Batal");
        btBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBatalActionPerformed(evt);
            }
        });

        btKeluar.setText("Keluar");
        btKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKeluarActionPerformed(evt);
            }
        });

        btExport.setText("Export");
        btExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExportActionPerformed(evt);
            }
        });

        tblKons.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode", "Nama Konsumen", "Alamat", "Kota", "Kode Pos", "Telepon", "Email"
            }
        ));
        tblKons.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKonsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKons);

        jScrollPane2.setViewportView(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btSimpan)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtKd_Kons)
                            .addComponent(txtNm_Kons)
                            .addComponent(txtAlamat)
                            .addComponent(txtKota)
                            .addComponent(txtKd_Pos)
                            .addComponent(txtTlpn)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(139, 139, 139)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btExport))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btKoreksi)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btBatal)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btKeluar)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(325, 325, 325))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtKd_Kons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNm_Kons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtKd_Pos, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtTlpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btExport))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(btBatal)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btTambah)
                    .addComponent(btSimpan)
                    .addComponent(btKoreksi)
                    .addComponent(btHapus)
                    .addComponent(btKeluar))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtKd_KonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKd_KonsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKd_KonsActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKotaActionPerformed

    private void txtTlpnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTlpnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTlpnActionPerformed

    private void btKoreksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKoreksiActionPerformed
        edit = true;
        aktif(true);
        setTombol(false);
    }//GEN-LAST:event_btKoreksiActionPerformed

    private void btHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHapusActionPerformed
        try {
            String sql = "delete from konsumen where kd_kons='" +txtKd_Kons.getText()+ "'";
            stm.executeUpdate(sql);
            baca_data();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btHapusActionPerformed

    private void btTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTambahActionPerformed
        aktif(true);
        setTombol(false);
        kosong();
    }//GEN-LAST:event_btTambahActionPerformed

    private void btBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBatalActionPerformed
        aktif(false);
        setTombol(true);
        kosong();
    }//GEN-LAST:event_btBatalActionPerformed

    private void btKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKeluarActionPerformed
         dispose();
    }//GEN-LAST:event_btKeluarActionPerformed

    private void tblKonsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKonsMouseClicked
        setField();
    }//GEN-LAST:event_tblKonsMouseClicked

    private void btSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btSimpanMouseClicked
        String tKode = txtKd_Kons.getText();
        String tNama = txtNm_Kons.getText();
        String tAlamat = txtAlamat.getText();
        String tKota = txtKota.getText();
        String tKdPos = txtKd_Pos.getText();
        String tTlp = txtTlpn.getText();
        String tEmail = txtEmail.getText();
        
        try {
            if(edit==true){
                stm.executeUpdate("update konsumen set "
                        + "nm_kons='"+tNama+"',alm_kons='"+tAlamat+"',kota_kons='"+tKota+"',kd_pos='"+tKdPos+"',phone='"+tTlp+"', email='"+tEmail+"' where kd_kons='" + tKode + "'");
            } else {
                stm.executeUpdate("INSERT into konsumen VALUES"
                        + "('"+tKode+"','"+tNama+"','"+tAlamat+"','"+tKota+"', '"+tKdPos+"', '"+tTlp+"', '"+tEmail+"')");
            }
            tblKons.setModel(new DefaultTableModel(dataTable, header));
            baca_data();
            aktif(false);
            setTombol(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btSimpanMouseClicked

    private void btExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExportActionPerformed
        try {
            ExportToExcel ex = new ExportToExcel(tblKons, new File("DataKonsumen.xls"));
            JOptionPane.showMessageDialog(null, "Sukses Export data.....");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btExportActionPerformed

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
            java.util.logging.Logger.getLogger(FormKonsumen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormKonsumen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormKonsumen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormKonsumen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormKonsumen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBatal;
    private javax.swing.JButton btExport;
    private javax.swing.JButton btHapus;
    private javax.swing.JButton btKeluar;
    private javax.swing.JButton btKoreksi;
    private javax.swing.JButton btSimpan;
    private javax.swing.JButton btTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblKons;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtKd_Kons;
    private javax.swing.JTextField txtKd_Pos;
    private javax.swing.JTextField txtKota;
    private javax.swing.JTextField txtNm_Kons;
    private javax.swing.JTextField txtTlpn;
    // End of variables declaration//GEN-END:variables
}
