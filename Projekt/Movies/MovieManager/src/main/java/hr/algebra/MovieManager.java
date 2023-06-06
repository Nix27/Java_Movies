/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hr.algebra;

import hr.algebra.views.moviemanager.Actors;
import hr.algebra.views.moviemanager.Directors;
import hr.algebra.views.moviemanager.Movies;

/**
 *
 * @author Nix
 */
public class MovieManager extends javax.swing.JFrame {

    private final static String MOVIES = "Movies";
    private static final String DIRECTORS = "Directors";
    private static final String ACTORS = "Actors";
    
    /**
     * Creates new form MovieManager
     */
    public MovieManager() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tpContent = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Movie manager");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpContent, javax.swing.GroupLayout.DEFAULT_SIZE, 1300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpContent, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tpContent;
    // End of variables declaration//GEN-END:variables

    private void init() {
        tpContent.add(MOVIES, new Movies());
        tpContent.add(DIRECTORS, new Directors());
        tpContent.add(ACTORS, new Actors());
    }
}
