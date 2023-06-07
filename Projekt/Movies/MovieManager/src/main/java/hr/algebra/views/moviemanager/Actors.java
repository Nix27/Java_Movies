/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hr.algebra.views.moviemanager;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.models.Actor;
import hr.algebra.models.MovieActor;
import hr.algebra.models.enums.RepoType;
import hr.algebra.utilities.MessageUtils;
import hr.algebra.view.models.ActorTableModel;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Nix
 */
public class Actors extends javax.swing.JPanel {

    private List<JTextComponent> validationFields;
    private List<JLabel> errorLabels;

    private Repository<Actor> actorRepo;
    private Repository<MovieActor> movieActorRepo;

    private ActorTableModel actorTableModel;

    private Actor selectedActor;

    /**
     * Creates new form Actors
     */
    public Actors() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblActors = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        tfFirst = new javax.swing.JTextField();
        lbFirstError = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfLast = new javax.swing.JTextField();
        lbLastError = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        tblActors.setModel(new javax.swing.table.DefaultTableModel(
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
        tblActors.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblActorsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblActors);

        jLabel1.setText("First name");

        lbFirstError.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbFirstError.setForeground(new java.awt.Color(255, 0, 0));
        lbFirstError.setText("X");

        jLabel3.setText("Last name");

        lbLastError.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbLastError.setForeground(new java.awt.Color(255, 0, 0));
        lbLastError.setText("X");

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 0, 0));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Actors");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(396, 396, 396)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tfFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbFirstError, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tfLast, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbLastError, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(410, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbFirstError, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfLast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbLastError, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        init();
    }//GEN-LAST:event_formComponentShown

    private void tblActorsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblActorsMouseClicked
        showActor();
    }//GEN-LAST:event_tblActorsMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!formValid()) return;

        try {
            Actor actor = new Actor(
                    tfFirst.getText().trim(),
                    tfLast.getText().trim()
            );

            actorRepo.createSingle(actor);
            actorTableModel.setActors(actorRepo.selectAll());
            clearForm();
        } catch (Exception ex) {
            Logger.getLogger(Actors.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedActor == null) {
            MessageUtils.showInformationMessage("Not selected actor", "Please select actor to update");
            return;
        }

        if (!formValid()) {
            return;
        }

        try {
            updateSelectedActor(selectedActor);

            actorTableModel.setActors(actorRepo.selectAll());
            clearForm();
        } catch (Exception ex) {
            Logger.getLogger(Actors.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedActor == null) {
            MessageUtils.showInformationMessage("Not selected actor", "Please select actor to update");
            return;
        }

        if (MessageUtils.showConfirmDialog("Delete actor", "Are you sure you want to delete actor?")) {
            
            
            try {
                List<MovieActor> movieActors = movieActorRepo.selectAll();
                deleteMovieActors(movieActors);
                
                actorRepo.delete(selectedActor.getId());
                
                actorTableModel.setActors(actorRepo.selectAll());
                clearForm();
            } catch (Exception ex) {
                Logger.getLogger(Directors.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbFirstError;
    private javax.swing.JLabel lbLastError;
    private javax.swing.JTable tblActors;
    private javax.swing.JTextField tfFirst;
    private javax.swing.JTextField tfLast;
    // End of variables declaration//GEN-END:variables

    private void init() {
        try {
            initValidation();
            hideErrors();
            initRepository();
            initTable();
        } catch (Exception ex) {
            Logger.getLogger(Actors.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initValidation() {
        validationFields = Arrays.asList(
                tfFirst, tfLast
        );

        errorLabels = Arrays.asList(
                lbFirstError, lbLastError
        );
    }

    private void hideErrors() {
        errorLabels.forEach(e -> e.setVisible(false));
    }

    private void initRepository() throws Exception {
        actorRepo = RepositoryFactory.getRepository(RepoType.ACTOR);
        movieActorRepo = RepositoryFactory.getRepository(RepoType.MOVIE_ACTOR);
    }

    private void initTable() throws Exception {
        tblActors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblActors.setAutoCreateRowSorter(true);
        tblActors.setRowHeight(25);
        
        actorTableModel = new ActorTableModel(actorRepo.selectAll());
        tblActors.setModel(actorTableModel);
    }
    
    private boolean formValid() {
        hideErrors();

        boolean ok = true;

        for (int i = 0; i < validationFields.size(); i++) {
            ok &= !validationFields.get(i).getText().trim().isEmpty();
            errorLabels.get(i).setVisible(validationFields.get(i).getText().trim().isEmpty());
        }

        return ok;
    }
    
    private void clearForm() {
        hideErrors();
        validationFields.forEach(f -> f.setText(""));
        selectedActor = null;
    }
    
    private void showActor() {
        clearForm();

        int selectedRow = tblActors.getSelectedRow();
        int rowIndex = tblActors.convertRowIndexToModel(selectedRow);
        int selectedActorId = (int) actorTableModel.getValueAt(rowIndex, 0);

        try {
            Optional<Actor> optActor = actorRepo.selectSingle(selectedActorId);

            if (optActor.isPresent()) {
                selectedActor = optActor.get();
                fillForm(selectedActor);
            }
        } catch (Exception ex) {
            Logger.getLogger(Directors.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillForm(Actor selectedActor) {
        tfFirst.setText(selectedActor.getFirstName());
        tfLast.setText(selectedActor.getLastName());
    }
    
    private void updateSelectedActor(Actor selectedActor) throws Exception {
        selectedActor.setFirstName(tfFirst.getText().trim());
        selectedActor.setLastName(tfLast.getText().trim());

        actorRepo.update(selectedActor.getId(), selectedActor);
    }

    private void deleteMovieActors(List<MovieActor> movieActors) throws Exception {
        for (MovieActor movieActor : movieActors) {
            if(movieActor.getActorId() == selectedActor.getId()){
                movieActorRepo.delete(movieActor.getId());
            }
        }
    }
}
