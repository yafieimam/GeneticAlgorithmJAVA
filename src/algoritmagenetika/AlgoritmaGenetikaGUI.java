/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmagenetika;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
/**
 *
 * @author YAFIE IMAM A
 */
public class AlgoritmaGenetikaGUI extends javax.swing.JFrame {
    int pilIndividu = 0;
    static List<String> label = new ArrayList<>();
    static List<Map<String,String>> dataRow = new ArrayList<>();
    double nilai_tertinggi, nilai_terendah;
    double[][][] populasi_awal;
    double[] nilai_fitness;
    double[] nilai_fitness_baru;
    double[] jarak;
    double[][][] populasi_baru;
    
    /**
     * Creates new form AlgoritmaGenetikaGUI
     */
    public AlgoritmaGenetikaGUI() {
        initComponents();
    }
    
    public double cari_nilai_tertinggi(){
        double nilai_tertinggi, nilai;
        nilai_tertinggi = Double.parseDouble(dataRow.get(0).get(label.get(0)));
        for(int i=0; i<dataRow.size(); i++){
            for(int j=0; j<(dataRow.get(i).size()); j++){
                nilai = Double.parseDouble(dataRow.get(i).get(label.get(j)));
                if(nilai > nilai_tertinggi){
                    nilai_tertinggi = nilai;
                }
            }
        }
        //System.out.println("Tertinggi = " + nilai_tertinggi);
        return nilai_tertinggi;
    }
    
    public double cari_nilai_terendah(){
        double nilai_terendah, nilai;
        nilai_terendah = Double.parseDouble(dataRow.get(0).get(label.get(0)));
        for(int i=0; i<dataRow.size(); i++){
            for(int j=0; j<(dataRow.get(i).size()); j++){
                nilai = Double.parseDouble(dataRow.get(i).get(label.get(j)));
                if(nilai < nilai_terendah){
                    nilai_terendah = nilai;
                }
            }
        }
        //System.out.println("Terendah = " + nilai_terendah);
        return nilai_terendah;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        btnLoadData = new javax.swing.JButton();
        btnPopulasiAwal = new javax.swing.JButton();
        label1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnPilIndividu = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(640, 480));

        btnLoadData.setText("Load Data");
        btnLoadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadDataActionPerformed(evt);
            }
        });

        btnPopulasiAwal.setText("Tentukan Populasi Awal");
        btnPopulasiAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPopulasiAwalActionPerformed(evt);
            }
        });

        jLabel1.setText("Path File = ");

        btnPilIndividu.setText("Tentukan Individu");
        btnPilIndividu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilIndividuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPopulasiAwal)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnLoadData)
                            .addComponent(btnPilIndividu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(695, 695, 695))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLoadData)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPilIndividu))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPopulasiAwal)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadDataActionPerformed
        // TODO add your handling code here:
        BufferedReader br = null;
        String csvfile = "D:/My Files/PENS/Machine Learning/ruspini_dataset.csv";
        String line = "";
        String cvsSplitBy = ",";
        dataRow.clear();
        try {
            br = new BufferedReader(new FileReader(csvfile));
            int index = 0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                if(index==0){
                    for(String c : country){
                        label.add(c);
                    }
                }
                else{
                    int a = 0;
                    Map<String,String> dataColumn = new HashMap<>();
                    for(String key : label){
                        dataColumn.put(key,country[a]);
                        a++;
                    }
                    dataRow.add(dataColumn);
                }
                index++;
            }
                //                if(dataRow.isEmpty()){
                    //                    System.out.println("Data kosong");
                    //                }else{
                    //                    for(Map<String,String> data : dataRow){
                        //                        System.out.println(data.toString());
                        //                    }
                    //                }
                //                if(label.isEmpty()){
                    //                    System.out.println("Label kosong");
                    //
                    //                }else{
                    //                    for(String c : label){
                        //                            System.out.println(c);
                        //                        }
                    //                }
                //                System.out.println("Country [code= " + dataRow.get(4).get(label.get(0)) + " , name=" + dataRow.get(0).get(label.get(1)) + "]");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
//        } else {
//            System.out.println("File access cancelled by user.");
//        }
        
        for(int i=0; i<dataRow.size(); i++){
            for(int j=0; j<label.size(); j++){
                System.out.println("Data " + (i+1) + ", Fitur ke " + j + " = " + dataRow.get(i).get(label.get(j)));
            }
        }
        AlgoritmaGenetikaGUI algoritmagenetika = new AlgoritmaGenetikaGUI();
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Tes",
                "Category",
                "Score",
                algoritmagenetika.createDatasetChart(),
                PlotOrientation.VERTICAL,
                true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new Dimension(640, 480));
        final XYPlot plot = xylineChart.getXYPlot();
        
        XYDotRenderer renderer = new XYDotRenderer( );
        renderer.setDotHeight(5);
        renderer.setDotWidth(5);
        renderer.setSeriesPaint( 0 , Color.BLACK );
        renderer.setSeriesPaint(1, Color.GREEN);
        plot.setRenderer(renderer); 
        setContentPane(chartPanel); 
        AlgoritmaGenetikaGUI chart = new AlgoritmaGenetikaGUI();
        chart.pack();          
        RefineryUtilities.centerFrameOnScreen( chart );          
        chart.setVisible( true ); 
    }//GEN-LAST:event_btnLoadDataActionPerformed
   
    public void buat_populasi_awal(int individu, double nilai_tertinggi, double nilai_terendah){
        if(individu == 0){
            JOptionPane.showMessageDialog(null, "Masukkan Jumlah Individu Terlebih Dahulu!!");
            btnPilIndividuActionPerformed(null);
        }
        populasi_awal = new double[individu][4][label.size()];
        
        for(int i = 0; i<individu; i++){
            for(int j = 0; j<4; j++){
                for(int k = 0; k<label.size(); k++){
                    Random r = new Random();
                    populasi_awal[i][j][k] = nilai_terendah + (nilai_tertinggi - nilai_terendah) * r.nextDouble();
                }
            }
        }
        for(int i=0; i<individu; i++){
            for(int j=0; j<4; j++){
                for(int k=0; k<label.size(); k++){
                    System.out.println("Populasi " + (i+1) + ", Kromosom ke " + (j+1) + ", Gen ke " + (k+1) + " = " + populasi_awal[i][j][k]);
                }
            }
        }
    }
    
    public void hitung_jarak(){
        double hasil = 0, jumlah_fitness = 0;        
        jarak = new double[dataRow.size()];
        nilai_fitness = new double[populasi_awal.length];
        
        for(int i=0; i<populasi_awal.length; i++){
            for(int j=0; j<4; j++){
                for(int k=0; k<dataRow.size(); k++){
                    for(int l=0; l<label.size(); l++){
                        hasil = sqrt((pow(hasil,2)) + (pow(Double.parseDouble(dataRow.get(k).get(label.get(l))) - populasi_awal[i][j][l], 2)));
                    }
                    jarak[k]=hasil;
                    hasil = 0;
                }
                Arrays.sort(jarak);
                jumlah_fitness = jumlah_fitness + jarak[0];
            }
            nilai_fitness[i] = 1/jumlah_fitness * 100;
            jumlah_fitness = 0;
        }
        for(int k=0; k<nilai_fitness.length; k++){
            System.out.println("Nilai Fitness " + (k+1) + " = " + nilai_fitness[k]);
        }
    }
    
    public void selection() {
        populasi_baru = new double[populasi_awal.length][4][label.size()];
        double hasil_random = 0, jumlah_fitness;
        boolean tepat;
        int j;
        for(int i=0; i<populasi_awal.length; i++){
            Random r = new Random();
            hasil_random = 0 + (populasi_awal.length - 0) * r.nextDouble();
            tepat = false;
            j=0;
            jumlah_fitness = 0;
            while(!tepat){
                jumlah_fitness = jumlah_fitness + nilai_fitness[j];
                if(hasil_random <= jumlah_fitness){
                    tepat = true;
                }else{
                    j++;
                }
            }
            populasi_baru[i] = populasi_awal[j];
        }
        for(int i=0; i<populasi_baru.length; i++){
            for(int l=0; l<4; l++){
                for(int k=0; k<label.size(); k++){
                    System.out.println("Populasi " + (i+1) + ", Kromosom ke " + (l+1) + ", Gen ke " + (k+1) + " = " + populasi_baru[i][l][k]);
                }
            }
        }
        
        for(int i=0; i<populasi_baru.length; i++){
            for(int l=0; l<4; l++){
                for(int k=0; k<label.size(); k++){
                    populasi_awal[i][l][k] = populasi_baru[i][l][k];
                }
            }
        }
    }
    
    public void crossOver(){
        double p, probCo = 0.9, temp;
        int batas_min, batas_max;
        
        for(int i=0; i<(populasi_baru.length/2); i++){
            Random r = new Random();
            p = 0 + (1 - 0) * r.nextDouble();
            if(p < probCo){
                batas_min = r.nextInt((7 - 1) + 1) + 1;
                batas_max = r.nextInt((8 - batas_min) + 1) + batas_min;
                for(int k=batas_min; k<=batas_max; k++){
                    if(k == 1){
                        temp = populasi_baru[i*2][0][0];
                        populasi_baru[i*2][0][0] = populasi_baru[i*2+1][0][0];
                        populasi_baru[i*2+1][0][0] = temp;
                    }else if(k == 2){
                        temp = populasi_baru[i*2][0][1];
                        populasi_baru[i*2][0][1] = populasi_baru[i*2+1][0][1];
                        populasi_baru[i*2+1][0][1] = temp;
                    }else if(k == 3){
                        temp = populasi_baru[i*2][1][0];
                        populasi_baru[i*2][1][0] = populasi_baru[i*2+1][1][0];
                        populasi_baru[i*2+1][1][0] = temp;
                    }else if(k == 4){
                        temp = populasi_baru[i*2][1][1];
                        populasi_baru[i*2][1][1] = populasi_baru[i*2+1][1][1];
                        populasi_baru[i*2+1][1][1] = temp;
                    }else if(k == 5){
                        temp = populasi_baru[i*2][2][0];
                        populasi_baru[i*2][2][0] = populasi_baru[i*2+1][2][0];
                        populasi_baru[i*2+1][2][0] = temp;
                    }else if(k == 6){
                        temp = populasi_baru[i*2][2][1];
                        populasi_baru[i*2][2][1] = populasi_baru[i*2+1][2][1];
                        populasi_baru[i*2+1][2][1] = temp;
                    }else if(k == 7){
                        temp = populasi_baru[i*2][3][0];
                        populasi_baru[i*2][3][0] = populasi_baru[i*2+1][3][0];
                        populasi_baru[i*2+1][3][0] = temp;
                    }else if(k == 8){
                        temp = populasi_baru[i*2][3][1];
                        populasi_baru[i*2][3][1] = populasi_baru[i*2+1][3][1];
                        populasi_baru[i*2+1][3][1] = temp;
                    }
                }
                for(int j=0; j<populasi_baru.length; j++){
                    for(int l=0; l<4; l++){
                        for(int k=0; k<label.size(); k++){
                            System.out.println("Populasi " + (j+1) + ", Kromosom ke " + (l+1) + ", Gen ke " + (k+1) + " = " + populasi_baru[j][l][k]);
                        }
                    }
                }
            }
        }
    }
    
    public void mutasi(){
        double p, probMut = 0.1;
        int gen_mut, nilai_geser = 2;
        
        for(int i=0; i<populasi_baru.length; i++){
            Random r = new Random();
            p = 0 + (1 - 0) * r.nextDouble();
            System.out.println("p = " + p);
            if(p < probMut){
                gen_mut = r.nextInt((8 - 1) + 1) + 1;
                System.out.println("Gen ke = " + gen_mut);
                if(gen_mut == 1){
                    populasi_baru[i][0][0] = populasi_baru[i][0][0] + nilai_geser;
                }else if(gen_mut == 2){
                    populasi_baru[i][0][1] = populasi_baru[i][0][1] + nilai_geser;
                }else if(gen_mut == 3){
                    populasi_baru[i][1][0] = populasi_baru[i][1][0] + nilai_geser;
                }else if(gen_mut == 4){
                    populasi_baru[i][1][1] = populasi_baru[i][1][1] + nilai_geser;
                }else if(gen_mut == 5){
                    populasi_baru[i][2][0] = populasi_baru[i][2][0] + nilai_geser;
                }else if(gen_mut == 6){
                    populasi_baru[i][2][1] = populasi_baru[i][2][1] + nilai_geser;
                }else if(gen_mut == 7){
                    populasi_baru[i][3][0] = populasi_baru[i][3][0] + nilai_geser;
                }else if(gen_mut == 8){
                    populasi_baru[i][3][1] = populasi_baru[i][3][1] + nilai_geser;
                }
            }
            for(int j=0; j<populasi_baru.length; j++){
                for(int l=0; l<4; l++){
                    for(int k=0; k<label.size(); k++){
                        System.out.println("Populasi " + (j+1) + ", Kromosom ke " + (l+1) + ", Gen ke " + (k+1) + " = " + populasi_baru[j][l][k]);
                    }
                }
            }
        }
    }
    
    public void elitism(){
        double hasil = 0, jumlah_fitness = 0, nilai;        
        nilai_fitness_baru = new double[populasi_baru.length];
        int tertinggi;
        
        for(int i=0; i<populasi_baru.length; i++){
            for(int j=0; j<4; j++){
                for(int k=0; k<dataRow.size(); k++){
                    for(int l=0; l<label.size(); l++){
                        hasil = sqrt((pow(hasil,2)) + (pow(Double.parseDouble(dataRow.get(k).get(label.get(l))) - populasi_baru[i][j][l], 2)));
                    }
                    jarak[k]=hasil;
                    hasil = 0;
                }
                Arrays.sort(jarak);
                jumlah_fitness = jumlah_fitness + jarak[0];
            }
            nilai_fitness_baru[i] = 1/jumlah_fitness * 100;
            jumlah_fitness = 0;
        }
        for(int k=0; k<nilai_fitness_baru.length; k++){
            System.out.println("Nilai Fitness " + (k+1) + " = " + nilai_fitness_baru[k]);
        }
        
        double[] nilai_fitness_semua = new double[nilai_fitness.length + nilai_fitness_baru.length];
        System.arraycopy(nilai_fitness, 0, nilai_fitness_semua, 0, nilai_fitness.length);
        System.arraycopy(nilai_fitness_baru, 0, nilai_fitness_semua, nilai_fitness.length, nilai_fitness_baru.length);
        
        for(int k=0; k<nilai_fitness_semua.length; k++){
            System.out.println("Nilai Fitness " + (k+1) + " = " + nilai_fitness_semua[k]);
        }
        
        double[][] populasi_terbesar = new double[nilai_fitness_semua.length][3];
        int k=0;
        for(int i=0; i<2; i++){
            for(int j=0; j<(nilai_fitness_semua.length/2); j++){
                populasi_terbesar[k][0] = (double) i;
                populasi_terbesar[k][1] = (double) j;
                populasi_terbesar[k][2] = nilai_fitness_semua[i+j];
                k++;
            }
        }
        
        Arrays.sort(populasi_terbesar, new Comparator<double[]>() {
            @Override
            public int compare(double[] t, double[] t1) {
                if (t[2] < t1[2])
                    return 1;
                else if (t[2] > t1[2])
                    return -1;
                else
                    return 0;
            }
        });

        for(int i=0; i<populasi_terbesar.length; i++){
            System.out.println(populasi_terbesar[i][0] + " dan " + populasi_terbesar[i][1] + " dan " + populasi_terbesar[i][2]);
        }
        
        for(int i=0; i<(populasi_terbesar.length/2); i++){
            if(populasi_terbesar[i][0] == 0){
                populasi_awal[i] = populasi_awal[i];
            }else if(populasi_terbesar[i][0] == 1){
                populasi_awal[i] = populasi_baru[i];
            }
        }
        
        for(int j=0; j<populasi_awal.length; j++){
            for(int l=0; l<4; l++){
                for(int i=0; i<label.size(); i++){
                    System.out.println("Populasi " + (j+1) + ", Kromosom ke " + (l+1) + ", Gen ke " + (i+1) + " = " + populasi_awal[j][l][i]);
                }
            }
        }
    }
    
    public XYDataset createDatasetChart(){
        XYSeries dataRuspini = new XYSeries( "Data" );
        XYSeries cluster = new XYSeries( "Cluster" );
        for(int i=0; i<dataRow.size(); i++){
            dataRuspini.add(Double.parseDouble(dataRow.get(i).get(label.get(0))), Double.parseDouble(dataRow.get(i).get(label.get(1))));
        }
        for(int l=0; l<4; l++){
            cluster.add(populasi_awal[0][l][0], populasi_awal[0][l][1]);
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(dataRuspini);
        dataset.addSeries(cluster);
        return dataset;
    }
    
    public void tampilChart(){
        AlgoritmaGenetikaGUI algoritmagenetika = new AlgoritmaGenetikaGUI();
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Tes",
                "Category",
                "Score",
                algoritmagenetika.createDatasetChart(),
                PlotOrientation.VERTICAL,
                true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new Dimension(640, 480));
        final XYPlot plot = xylineChart.getXYPlot();
        
        XYDotRenderer renderer = new XYDotRenderer( );
        renderer.setDotHeight(5);
        renderer.setDotWidth(5);
        renderer.setSeriesPaint( 0 , Color.BLACK );
        renderer.setSeriesPaint(1, Color.GREEN);
        plot.setRenderer(renderer); 
        setContentPane(chartPanel); 
        AlgoritmaGenetikaGUI chart = new AlgoritmaGenetikaGUI();
        chart.pack();          
        RefineryUtilities.centerFrameOnScreen( chart );          
        chart.setVisible(true);
    }
    
    private void btnPopulasiAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPopulasiAwalActionPerformed
        // TODO add your handling code here:
        AlgoritmaGenetikaGUI algoritmagenetika = new AlgoritmaGenetikaGUI();
        nilai_tertinggi = algoritmagenetika.cari_nilai_tertinggi();
        nilai_terendah = algoritmagenetika.cari_nilai_terendah();
        algoritmagenetika.buat_populasi_awal(pilIndividu, nilai_tertinggi, nilai_terendah);
        for(int i=0; i<100; i++){
            algoritmagenetika.hitung_jarak();
            algoritmagenetika.selection();
            algoritmagenetika.hitung_jarak();
            algoritmagenetika.crossOver();
            algoritmagenetika.mutasi();
            algoritmagenetika.elitism();
        } 
    }//GEN-LAST:event_btnPopulasiAwalActionPerformed

    private void btnPilIndividuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilIndividuActionPerformed
        // TODO add your handling code here:
        do{
            if((pilIndividu%2) != 0){
                JOptionPane.showMessageDialog(null, "Masukkan Bilangan Genap Saja!!");
            }
            pilIndividu = Integer.parseInt(JOptionPane.showInputDialog("Tentukan Jumlah Individu yang ingin dibentuk!\n(Harus Genap)\nJawaban : "));
        }while((pilIndividu%2) != 0);
    }//GEN-LAST:event_btnPilIndividuActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        AlgoritmaGenetikaGUI algoritmagenetika = new AlgoritmaGenetikaGUI();
        algoritmagenetika.tampilChart();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AlgoritmaGenetikaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlgoritmaGenetikaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlgoritmaGenetikaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlgoritmaGenetikaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlgoritmaGenetikaGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadData;
    private javax.swing.JButton btnPilIndividu;
    private javax.swing.JButton btnPopulasiAwal;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label1;
    // End of variables declaration//GEN-END:variables
}