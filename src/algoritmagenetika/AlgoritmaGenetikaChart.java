/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmagenetika;

import static algoritmagenetika.AlgoritmaGenetika.dataRow;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author YAFIE IMAM A
 */
public class AlgoritmaGenetikaChart extends JFrame {
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
     * Creates new form AlgoritmaGenetikaChart
     */
    public AlgoritmaGenetikaChart() {
//        initComponents();
        loadData();
        buat_populasi_awal();
        int count = 0;
        do{
            hitung_jarak();
            selection();
            hitung_jarak();
            crossOver();
            mutasi();
            elitism();
            
            XYSeries dataRuspini = new XYSeries( "Data" );
            for(int i=0; i<dataRow.size(); i++){
                dataRuspini.add(Double.parseDouble(dataRow.get(i).get(label.get(0))), Double.parseDouble(dataRow.get(i).get(label.get(1))));
            }
            XYSeries cluster = new XYSeries( "Cluster" );
            for(int l=0; l<4; l++){
                cluster.add(populasi_awal[0][l][0], populasi_awal[0][l][1]);
            }
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(dataRuspini);
            dataset.addSeries(cluster);

            JFreeChart xylineChart = ChartFactory.createXYLineChart(
                    "Tes",
                    "Category",
                    "Score",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false
            );

            final XYPlot plot = (XYPlot) xylineChart.getPlot();

            XYDotRenderer renderer = new XYDotRenderer();
            plot.setRenderer(renderer);
            renderer.setDotHeight(5);
            renderer.setDotWidth(5);
            renderer.setSeriesPaint( 0 , Color.BLACK );
            renderer.setSeriesPaint(1, Color.GREEN);

            JPanel jPanel1 = new JPanel();
            jPanel1.setLayout(new java.awt.BorderLayout());
            jPanel1.setVisible(true);
            jPanel1.setSize(640,480);

            ChartPanel cp = new ChartPanel(xylineChart){
              @Override  
              public Dimension getPreferredSize(){
                  return new Dimension(640,480);
              }
            };
            cp.setMouseWheelEnabled(true);
            add(cp);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            pack();
            setVisible(true);
            
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            count++;
            if(count<100){
                remove(cp);
                setVisible(false);
            }
        }while(count<100);
    }
    
//    public XYDataset createDatasetChart(){
//        XYSeries dataRuspini = new XYSeries( "Data" );
//        XYSeries cluster = new XYSeries( "Cluster" );
//        for(int i=0; i<dataRow.size(); i++){
//            dataRuspini.add(Double.parseDouble(dataRow.get(i).get(label.get(0))), Double.parseDouble(dataRow.get(i).get(label.get(1))));
//        }
//        for(int l=0; l<4; l++){
//            cluster.add(populasi_awal[0][l][0], populasi_awal[0][l][1]);
//        }
//        XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(dataRuspini);
//        dataset.addSeries(cluster);
//        return dataset;
//    }
    
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
    
    public void loadData(){
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
        
        for(int i=0; i<dataRow.size(); i++){
            for(int j=0; j<label.size(); j++){
                System.out.println("Data " + (i+1) + ", Fitur ke " + j + " = " + dataRow.get(i).get(label.get(j)));
            }
        }
    }
    
    public void buat_populasi_awal(){
        nilai_tertinggi = cari_nilai_tertinggi();
        nilai_terendah = cari_nilai_terendah();
        do{
            if((pilIndividu%2) != 0){
                JOptionPane.showMessageDialog(null, "Masukkan Bilangan Genap Saja!!");
            }
            pilIndividu = Integer.parseInt(JOptionPane.showInputDialog("Tentukan Jumlah Individu yang ingin dibentuk!\n(Harus Genap)\nJawaban : "));
        }while((pilIndividu%2) != 0);
        
        populasi_awal = new double[pilIndividu][4][label.size()];
        
        for(int i = 0; i<pilIndividu; i++){
            for(int j = 0; j<4; j++){
                for(int k = 0; k<label.size(); k++){
                    Random r = new Random();
                    populasi_awal[i][j][k] = nilai_terendah + (nilai_tertinggi - nilai_terendah) * r.nextDouble();
                }
            }
        }
        for(int i=0; i<pilIndividu; i++){
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
            hasil_random = 0 + ((populasi_awal.length-1) - 0) * r.nextDouble();
            tepat = false;
            j=0;
            jumlah_fitness = 0;
            while(!tepat && j<(populasi_awal.length-1)){
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
            System.out.println("p = " + p);
            if(p < probCo){
                batas_min = r.nextInt((7 - 1) + 1) + 1;
                batas_max = r.nextInt((8 - batas_min) + 1) + batas_min;
                System.out.println(batas_min + " - " + batas_max);
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
        double p, probMut = 0.2, random_mutasi = 2, kondisi_mutasi;
        int gen_mut;
        
        for(int i=0; i<populasi_baru.length; i++){
            Random r = new Random();
            p = 0 + (1 - 0) * r.nextDouble();
            System.out.println("p = " + p);
            if(p < probMut){
                gen_mut = r.nextInt((8 - 1) + 1) + 1;
                System.out.println("Gen ke = " + gen_mut);
                    if(gen_mut == 1){
                        if(populasi_baru[i][0][0] > random_mutasi)
                            populasi_baru[i][0][0] = populasi_baru[i][0][0] + random_mutasi;
                        else
                            populasi_baru[i][0][0] = populasi_baru[i][0][0] - random_mutasi;
                    }else if(gen_mut == 2){
                        if(populasi_baru[i][0][1] > random_mutasi)
                            populasi_baru[i][0][1] = populasi_baru[i][0][1] + random_mutasi;
                        else
                            populasi_baru[i][0][1] = populasi_baru[i][0][1] - random_mutasi;
                    }else if(gen_mut == 3){
                        if(populasi_baru[i][1][0] > random_mutasi)
                            populasi_baru[i][1][0] = populasi_baru[i][1][0] + random_mutasi;
                        else
                            populasi_baru[i][1][0] = populasi_baru[i][1][0] - random_mutasi;
                    }else if(gen_mut == 4){
                        if(populasi_baru[i][1][1] > random_mutasi)
                            populasi_baru[i][1][1] = populasi_baru[i][1][1] + random_mutasi;
                        else
                            populasi_baru[i][1][1] = populasi_baru[i][1][1] - random_mutasi;
                    }else if(gen_mut == 5){
                        if(populasi_baru[i][2][0] > random_mutasi)
                            populasi_baru[i][2][0] = populasi_baru[i][2][0] + random_mutasi;
                        else
                            populasi_baru[i][2][0] = populasi_baru[i][2][0] - random_mutasi;
                    }else if(gen_mut == 6){
                        if(populasi_baru[i][2][1] > random_mutasi)
                            populasi_baru[i][2][1] = populasi_baru[i][2][1] + random_mutasi;
                        else
                            populasi_baru[i][2][1] = populasi_baru[i][2][1] - random_mutasi;
                    }else if(gen_mut == 7){
                        if(populasi_baru[i][3][0] > random_mutasi)
                            populasi_baru[i][3][0] = populasi_baru[i][3][0] + random_mutasi;
                        else
                            populasi_baru[i][3][0] = populasi_baru[i][3][0] - random_mutasi;
                    }else if(gen_mut == 8){
                        if(populasi_baru[i][3][1] > random_mutasi)
                            populasi_baru[i][3][1] = populasi_baru[i][3][1] + random_mutasi;
                        else
                            populasi_baru[i][3][1] = populasi_baru[i][3][1] - random_mutasi;
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(AlgoritmaGenetikaChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlgoritmaGenetikaChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlgoritmaGenetikaChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlgoritmaGenetikaChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlgoritmaGenetikaChart();
//                algoritmagenetika.loadData();
//                algoritmagenetika.buat_populasi_awal();
//                algoritmagenetika.loadChart();  
//                algoritmagenetika.pack();          
//                RefineryUtilities.centerFrameOnScreen(algoritmagenetika); 
//                algoritmagenetika.setVisible(true); 
//                for(int i=0; i<100; i++){
//                    algoritmagenetika.hitung_jarak();
//                    algoritmagenetika.selection();
//                    algoritmagenetika.hitung_jarak();
//                    algoritmagenetika.crossOver();
//                    algoritmagenetika.mutasi();
//                    algoritmagenetika.elitism();
//                    algoritmagenetika.loadChart();
//                }  
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
