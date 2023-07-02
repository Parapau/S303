package services;

import model.Decor;
import model.Flower;
import model.Product;
import model.Tree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class txtManagerImpl implements IDDBBManager{

    static private final String PATH_TXT_DDBB = "src/main/java/resources/txtDDBB.txt";
    static private final String PATH_TXT_maxID = "src/main/java/resources/maxID.txt";
    static private int currentMaxId;

    //static final String PATH_TXT_DDBB2 = "src/resources/txtDDBB2.txt";

    static private Scanner sc = new Scanner(System.in);
//    static private FileReader fileReaderDDBB = null;
//    static private FileReader fileReaderMaxNum = null;
//    static private BufferedReader bufferReaderDDBB = null;
//    static private BufferedReader bufferReaderID = null;
//    static private FileWriter fileWriterDDBB = null;
//    static private FileWriter fileWriterID = null;
    static private List<Product> stock = new ArrayList<Product>();



    public int calculateDDBBMaxID(){
        currentMaxId = 0;
        FileReader fileReaderDDBB = null;
        BufferedReader bufferReaderDDBB = null;

        try{
            fileReaderDDBB = new FileReader(PATH_TXT_DDBB);
            bufferReaderDDBB = new BufferedReader(fileReaderDDBB);
            String line = "";
            while((line = bufferReaderDDBB.readLine()) != null){
                String[] parts = line.split(" ");
                int id = Integer.parseInt(parts[0]);
            }
        }catch (FileNotFoundException fnfe){
            System.out.println("There is no such file");
        } catch (IOException e) {
            System.out.println("Somethig happend with the file");
        }catch (ArrayIndexOutOfBoundsException siobe){
            System.out.println("The are extra blank lines in the file");
        }catch (NumberFormatException nfe){
            System.out.println("The are extra blank lines in the file");
        }finally {
            try {
                fileReaderDDBB.close();
                bufferReaderDDBB.close();
            } catch (IOException e) {
            }
        }
        return currentMaxId;
    }

    public void overrideMaxIDD(int id){
        FileWriter fileWriterID = null;

        try{
            fileWriterID = new FileWriter(PATH_TXT_maxID);
            fileWriterID.write(id+"\n");

        }catch (FileNotFoundException fnfe){
            System.out.println("There is no such file");
        } catch (IOException e) {
            System.out.println("Somethig happend with the file");
        }catch (ArrayIndexOutOfBoundsException siobe){
            System.out.println("The are extra blank lines in the file");
        }catch (NumberFormatException nfe){
            System.out.println("The are extra blank lines in the file");
        }finally {
            try {
                fileWriterID.close();
            } catch (IOException e) {
            }
        }
    }

    //Este metodo solo se puede utilizar en el constructor de Producto
    public int nextIdBBDD(){
        int allTimeMaxId=0;
        FileReader fileReaderMaxNum = null;
        BufferedReader bufferReaderID = null;
        try{
            fileReaderMaxNum = new FileReader(PATH_TXT_maxID);
            bufferReaderID = new BufferedReader(fileReaderMaxNum);

            String line = "";
            while((line = bufferReaderID.readLine()) != null){
                String[] parts = line.split(" ");
                allTimeMaxId = Integer.parseInt(parts[0]);
            }
        }catch (FileNotFoundException fnfe){
            System.out.println("There is no such file");
        } catch (IOException e) {
            System.out.println("Somethig happend with the file");
        }catch (ArrayIndexOutOfBoundsException siobe){
            System.out.println("The are extra blank lines in the file");
        }catch (NumberFormatException nfe){
            System.out.println("The are extra blank lines in the file");
        }finally {
            try {
                fileReaderMaxNum.close();
                bufferReaderID.close();
                overrideMaxIDD(++allTimeMaxId);
            } catch (IOException e) {
            }
        }
        return allTimeMaxId;
    }

    @Override
    public ArrayList<Product> getAllBBDD() {
        ArrayList<Product> newStock = new ArrayList<>();
        FileReader fileReaderDDBB = null;
        BufferedReader bufferReaderDDBB = null;
        try{
            fileReaderDDBB = new FileReader(PATH_TXT_DDBB);
            bufferReaderDDBB = new BufferedReader(fileReaderDDBB);
            String line = "";
            while((line = bufferReaderDDBB.readLine()) != null){
                String[] parts = line.split(" ");
                int id = Integer.parseInt(parts[0]);
                String productType = parts[1];
                double price = Double.parseDouble(parts[2]);
                String attribute = parts[3];

                switch (productType) {
                    case "Tree" -> {
                        double height = Double.parseDouble(attribute);
                        Tree arbol = new Tree(id,price, height);
                        newStock.add(arbol);
                        break;
                    }
                    case "Flower" -> {
                        Flower flor = new Flower(id, price, attribute);
                        newStock.add(flor);
                        break;
                    }
                    case "Decoration" -> {
                        Decor adorno = new Decor(id, price, attribute);
                        newStock.add(adorno);
                        break;
                    }
                }
            }
        }catch (FileNotFoundException fnfe){
            System.out.println("There is no such file");
        } catch (IOException e) {
            System.out.println("Somethig happend with the file");
        }catch (ArrayIndexOutOfBoundsException siobe){
            System.out.println("The are extra blank lines in the file");
        }catch (NumberFormatException nfe){
            System.out.println("The are extra blank lines in the file");
        }finally {
            try {
                fileReaderDDBB.close();
                bufferReaderDDBB.close();
            } catch (IOException e) {
            	System.out.println("pugttawdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            }
        }
        return newStock;
    }

    @Override
    public void updateDDBB(List<Product> products){
        FileWriter fileWriterDDBB = null;
        try {
            fileWriterDDBB = new FileWriter(PATH_TXT_DDBB);
            for(int i=0; i<products.size(); i++){
                int id = products.get(i).getIdProduct();
                fileWriterDDBB.write(products.get(i).writeTXT()+"\n");
            }
        } catch (IOException e) {
            System.out.println("There is no such File add Products");
        }finally {
            try {
                fileWriterDDBB.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void addProduct(Product product) {
        FileWriter fileWriterDDBB = null;
        try {
            fileWriterDDBB = new FileWriter(PATH_TXT_DDBB,true);            
            fileWriterDDBB.write(product.writeTXT());
            
        } catch (IOException e) {
            System.out.println("There is no such File add Products");
        }finally {
            try {
                fileWriterDDBB.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void removeProduct(Product product) {
    	ArrayList<Product> productes = getAllBBDD();
    	int i = 0;
    	while (product.getIdProduct() >= productes.get(i).getIdProduct()) {
    		if (product.getIdProduct() == productes.get(i).getIdProduct()) {
    			productes.remove(i);
    			updateDDBB(productes);
    		}
    	}
    }
    
    
}
