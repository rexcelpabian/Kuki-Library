/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarytubes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rexcel
 */
public class FDataAnggota extends javax.swing.JFrame {

    /**
     * Creates new form FDataAnggota
     */
    public FDataAnggota() {
        initComponents();
        load_data(); //Memanggil menampilkan data
        IDOtomatis(); //Menampilkan id otomatis
        LoadTingkat(); //load combo tingkat
        LoadJurusan(); //load combo jurusan 
        LoadKelas(); //load combo kelas
        
        BEdit.setEnabled(false);
        BDelete.setEnabled(false);
    }
    
    //Load data dari database tbl_anggota
    private void load_data() {
        Connection kon=Koneksi.koneksiDb();
        Object header[]={"ID ANGGOTA", "NIM", "NAMA ANGGOTA", "JK", "TINGKAT", "JURUSAN", "KELAS", "NO HP", "STATUS"};
        DefaultTableModel data=new DefaultTableModel(null, header);
        TabelAnggota.setModel(data);
        String sql_data="SELECT * FROM tbl_anggota";
        
        try {
            Statement st=kon.createStatement();
            ResultSet rs=st.executeQuery(sql_data);
            while(rs.next()) {
                String d1=rs.getString(1);
                String d2=rs.getString(2);
                String d3=rs.getString(3);
                String d4=rs.getString(4);
                String d5=rs.getString(5);
                String d6=rs.getString(6);
                String d7=rs.getString(7);
                String d8=rs.getString(8);
                String d9=rs.getString(9);
                
                
                String d[]={d1,d2,d3,d4,d5,d6,d7,d8,d9};
                data.addRow(d);
            }
        }
        
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // ID Anggota Otomatis
    private void IDOtomatis(){
        try {
            Connection kon=Koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_id="SELECT * FROM tbl_anggota order by id_anggota desc";
            ResultSet rs=st.executeQuery(sql_id);
            if(rs.next()) {
                String id_anggota=rs.getString("id_anggota").substring(1);
                String AN=""+(Integer.parseInt(id_anggota)+1);
                String Nol="";
                if(AN.length()==1) { //jika id_anggota A00001
                    Nol = "0000";
                } else if(AN.length()==2) { //jika id_anggota A00010
                    Nol = "000";
                } else if(AN.length()==3) { //jika id_anggota A00100
                    Nol = "00";
                } 
                ID.setText("A"+Nol+AN);
            } else {
                ID.setText("A00001");
            }
        }
        
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Load Combo Tingkat--------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void LoadTingkat() {
        try {
            Connection kon=Koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_tingkat="SELECT * FROM tbl_tingkat";
            ResultSet rs=st.executeQuery(sql_tingkat);
            while(rs.next()){
                KTINGKAT.addItem(rs.getString("id_tingkat"));
            }
            rs.close();
        }
        catch(Exception e) {
            
        }
    }
    
    //Load nama tingkat--------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void NamaTingkat() {
        try {
            Connection kon=Koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_tingkat="SELECT jurusan FROM tbl_tingkat WHERE id_tingkat='"+KTINGKAT.getSelectedItem();
            ResultSet rs=st.executeQuery(sql_tingkat);
            while(rs.next()){
                NTINGKAT.setText(rs.getString("tingkat"));
            }
        }
        catch(Exception e) {
            
        }
    }
    
    //Load Combo Jurusan--------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void LoadJurusan() {
        try {
            Connection kon=Koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_tingkat="SELECT id_jurusan FROM tbl_jurusan";
            ResultSet rs=st.executeQuery(sql_tingkat);
            while(rs.next()){
                KJURUSAN.addItem(rs.getString("id_jurusan"));
            }
            rs.close();
        }
        catch(Exception e) {
            
        }
    }
    
    //Load nama jurusan--------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void NamaJurusan() {
        try {
            Connection kon=Koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_tingkat="SELECT jurusan FROM tbl_jurusan WHERE id_jurusan='"+KJURUSAN.getSelectedItem();
            ResultSet rs=st.executeQuery(sql_tingkat);
            while(rs.next()){
                NJURUSAN.setText(rs.getString("jurusan"));
            }
        }
        catch(Exception e) {
            
        }
    }
    
    //Load Combo Kelas--------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void LoadKelas() {
        try {
            Connection kon=Koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_tingkat="SELECT id_kelas FROM tbl_kelas";
            ResultSet rs=st.executeQuery(sql_tingkat);
            while(rs.next()){
                KKELAS.addItem(rs.getString("id_kelas"));
            }
            rs.close();
        }
        catch(Exception e) {
            
        }
    }
    
    //Input Data---------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void input_data() {
        try {
            Connection kon=Koneksi.koneksiDb();
            Statement st=kon.createStatement();
            
            //untuk jenis kelamin
            String jk="";
            if(JKP.isSelected()) {
                jk=JKP.getText();
            } else {
                jk = JKW.getText();
            }
            
            
            String sql="INSERT INTO tbl_anggota values ('"+ID.getText()
                    +"','"+NIM.getText()
                    +"','"+NAMA.getText()
                    +"','"+jk
                    +"','"+KTINGKAT.getSelectedItem()
                    +"','"+KJURUSAN.getSelectedItem()
                    +"','"+KKELAS.getSelectedItem()
                    +"','"+NOPE.getText()
                    +"','"+STATUS.getSelectedItem()
                    +"')";
            st.execute(sql);
            JOptionPane.showMessageDialog(null, "Data Anggota Berhasil Dimasukkan");
        } 
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Reset data setelah input/edit/delete
    public void clear() {
        NIM.setText("");
        NAMA.setText("");
        NOPE.setText("");
        JKP.setSelected(rootPaneCheckingEnabled);
        KTINGKAT.setSelectedItem(1);
        KJURUSAN.setSelectedItem("AB");
        KKELAS.setSelectedItem(1);
        STATUS.setSelectedItem("AKTIF");
    }
    
    //Edit data
    public void update() {
        try {
            Connection kon=Koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String jk="";
            if(JKP.isSelected()) {
                jk=JKP.getText();
            } else {
                jk = JKW.getText();
            }
            String sql_update="UPDATE tbl_anggota SET nim='"+NIM.getText()
                    +"',nama='"+NAMA.getText()
                    +"',jk='"+jk
                    +"',id_tingkat='"+KTINGKAT.getSelectedItem()
                    +"',id_jurusan='"+KJURUSAN.getSelectedItem()
                    +"',id_kelas='"+KKELAS.getSelectedItem()
                    +"',nope='"+NOPE.getText()
                    +"',status='"+STATUS.getSelectedItem()
                    +"'WHERE id_anggota='"+ID.getText()+"'";
            st.execute(sql_update);
            JOptionPane.showMessageDialog(null, "Data Berhasil di Update");
        } 
        
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Deelete data
    private void delete(){
        try {
            Connection kon=Koneksi.koneksiDb();
            Statement st=kon.createStatement();
            
            String sql_delete="DELETE from tbl_anggota WHERE "
                    + "id_anggota='"+ID.getText()+"'";
            st.executeUpdate(sql_delete);
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        }
        
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

        JK = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BKeluar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ID = new javax.swing.JTextField();
        NIM = new javax.swing.JTextField();
        NAMA = new javax.swing.JTextField();
        JKP = new javax.swing.JRadioButton();
        JKW = new javax.swing.JRadioButton();
        KTINGKAT = new javax.swing.JComboBox<>();
        KJURUSAN = new javax.swing.JComboBox<>();
        KKELAS = new javax.swing.JComboBox<>();
        NOPE = new javax.swing.JTextField();
        STATUS = new javax.swing.JComboBox<>();
        NTINGKAT = new javax.swing.JTextField();
        NJURUSAN = new javax.swing.JTextField();
        BInput = new javax.swing.JButton();
        BEdit = new javax.swing.JButton();
        BDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelAnggota = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("KELOLA DATA ANGGOTA PERPUSTAKAAN");

        BKeluar.setText("Keluar");
        BKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BKeluarActionPerformed(evt);
            }
        });

        jLabel2.setText("ID ANGGOTA");

        jLabel3.setText("NIM");

        jLabel4.setText("NAMA ANGGOTA");

        jLabel5.setText("JENIS KELAMIN");

        jLabel6.setText("TINGKAT");

        jLabel7.setText("JURUSAN");

        jLabel8.setText("KELAS");

        jLabel9.setText("NO HP");

        jLabel10.setText("STATUS");

        ID.setEnabled(false);

        NIM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NIMActionPerformed(evt);
            }
        });

        NAMA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NAMAActionPerformed(evt);
            }
        });

        JK.add(JKP);
        JKP.setSelected(true);
        JKP.setText("PRIA");

        JK.add(JKW);
        JKW.setText("WANITA");

        KTINGKAT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                KTINGKATMouseClicked(evt);
            }
        });
        KTINGKAT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KTINGKATActionPerformed(evt);
            }
        });

        KJURUSAN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KJURUSANActionPerformed(evt);
            }
        });

        STATUS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AKTIF", "TIDAK AKTIF" }));

        NTINGKAT.setEditable(false);

        NJURUSAN.setEditable(false);

        BInput.setText("INPUT");
        BInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BInputActionPerformed(evt);
            }
        });

        BEdit.setText("EDIT");
        BEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditActionPerformed(evt);
            }
        });

        BDelete.setText("DELETE");
        BDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDeleteActionPerformed(evt);
            }
        });

        TabelAnggota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TabelAnggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelAnggotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelAnggota);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(712, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(237, 237, 237))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(BKeluar))
                        .addGap(89, 89, 89)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(KTINGKAT, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(JKP))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(JKW))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(NTINGKAT, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(NIM, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NAMA, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(KJURUSAN, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(NJURUSAN, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(KKELAS, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NOPE, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(BInput)
                                .addGap(51, 51, 51)
                                .addComponent(BEdit)
                                .addGap(50, 50, 50)
                                .addComponent(BDelete))
                            .addComponent(STATUS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 846, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(NIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(NAMA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(JKP)
                            .addComponent(JKW))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(KTINGKAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NTINGKAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(KJURUSAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NJURUSAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(KKELAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(NOPE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(STATUS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(93, 93, 93)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BKeluar)
                            .addComponent(BInput)
                            .addComponent(BEdit)
                            .addComponent(BDelete)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BKeluarActionPerformed
        // TODO add your handling code here:
        int keluar;
        keluar = JOptionPane.showOptionDialog(this,
                "Keluar dari Kelola Data Anggota?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
          
        if(keluar==JOptionPane.YES_NO_OPTION) {
            new FUtamaPustakawan().show();
            this.dispose();
        }
    }//GEN-LAST:event_BKeluarActionPerformed

    private void NIMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NIMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NIMActionPerformed

    private void NAMAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NAMAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NAMAActionPerformed

    private void BDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDeleteActionPerformed
        // TODO add your handling code here:
        int delete=JOptionPane.showOptionDialog(this,
                "Apakah data akan dihapus? Hapus?",
                "Hapus data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        
        if(delete==JOptionPane.YES_OPTION) {
            delete();
            clear();
            load_data();
            IDOtomatis();

            //Set enable input
            BInput.setEnabled(true);
            BEdit.setEnabled(false);
            BDelete.setEnabled(false);
        }
    }//GEN-LAST:event_BDeleteActionPerformed

    private void KTINGKATMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KTINGKATMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_KTINGKATMouseClicked

    private void KTINGKATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KTINGKATActionPerformed
        // TODO add your handling code here:
        NamaTingkat();
    }//GEN-LAST:event_KTINGKATActionPerformed

    private void KJURUSANActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KJURUSANActionPerformed
        // TODO add your handling code here:
        NamaJurusan();
    }//GEN-LAST:event_KJURUSANActionPerformed

    private void BInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BInputActionPerformed
        // TODO add your handling code here:
        //Confirm Yes/No quest
        
        int simpan=JOptionPane.showOptionDialog(this,
                "Apakah data sudah benar? SIMPAN?",
                "Simpan data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        
        if(simpan==JOptionPane.YES_OPTION) {
            input_data();
            load_data();
            clear();
            IDOtomatis();
        }
    }//GEN-LAST:event_BInputActionPerformed

    private void TabelAnggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelAnggotaMouseClicked
        // TODO add your handling code here:
        int bar=TabelAnggota.getSelectedRow();
        String a=TabelAnggota.getValueAt(bar, 0).toString();
        String b=TabelAnggota.getValueAt(bar, 1).toString();
        String c=TabelAnggota.getValueAt(bar, 2).toString();
        String d=TabelAnggota.getValueAt(bar, 3).toString();
        String e=TabelAnggota.getValueAt(bar, 4).toString();
        String f=TabelAnggota.getValueAt(bar, 5).toString();
        String g=TabelAnggota.getValueAt(bar, 6).toString();
        String h=TabelAnggota.getValueAt(bar, 7).toString();
        String i=TabelAnggota.getValueAt(bar, 8).toString();
        
        ID.setText(a);
        NIM.setText(b);
        NAMA.setText(c);
        
        //Jenis Kelamin
        if("PRIA".equals(d)) {
            JKP.setSelected(true);
        } else {
            JKW.setSelected(true);
        }
        
        //Tingkat, kelas, jurusan
        KTINGKAT.setSelectedItem(e);
        KJURUSAN.setSelectedItem(f);
        KKELAS.setSelectedItem(g);
        NOPE.setText(h);
        
        //Status
        if("AKTIF".equals(i)) {
            STATUS.setSelectedItem(i);
        } else {
            STATUS.setSelectedItem(i);
        }
        
        //Set disable input
        BInput.setEnabled(false);
        BEdit.setEnabled(true);
        BDelete.setEnabled(true);
        
        
    }//GEN-LAST:event_TabelAnggotaMouseClicked

    private void BEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditActionPerformed
        // TODO add your handling code here:
        int update=JOptionPane.showOptionDialog(this,
                "Apakah data akan diupdate? Update?",
                "Update data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        
        if(update==JOptionPane.YES_OPTION) {
            update();
            clear();
            load_data();
            IDOtomatis();

            //Set enable input
            BInput.setEnabled(true);
            BEdit.setEnabled(false);
            BDelete.setEnabled(false);
        }
    }//GEN-LAST:event_BEditActionPerformed

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
            java.util.logging.Logger.getLogger(FDataAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FDataAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FDataAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FDataAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FDataAnggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BDelete;
    private javax.swing.JButton BEdit;
    private javax.swing.JButton BInput;
    private javax.swing.JButton BKeluar;
    private javax.swing.JTextField ID;
    private javax.swing.ButtonGroup JK;
    private javax.swing.JRadioButton JKP;
    private javax.swing.JRadioButton JKW;
    private javax.swing.JComboBox<String> KJURUSAN;
    private javax.swing.JComboBox<String> KKELAS;
    private javax.swing.JComboBox<String> KTINGKAT;
    private javax.swing.JTextField NAMA;
    private javax.swing.JTextField NIM;
    private javax.swing.JTextField NJURUSAN;
    private javax.swing.JTextField NOPE;
    private javax.swing.JTextField NTINGKAT;
    private javax.swing.JComboBox<String> STATUS;
    private javax.swing.JTable TabelAnggota;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
