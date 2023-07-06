package services;

import model.Decor;
import model.Flower;
import model.Product;
import model.Tree;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class txtManagerImpl implements IDDBBManager{

	static private final String PATH_TXT_maxID = "src/main/java/resources/maxID.txt";


	public void overrideNextIDinTXT(int id){
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

		//Get the maxId from the DDBB
		ResultSet rs = null;
		try{
			rs = query("SELECT MAX(stock_id) FROM stock");
			rs.next();
			allTimeMaxId = rs.getInt(1);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch ( SQLException e){
			e.printStackTrace();
		}

		//Write the nextID in the txt
		overrideNextIDinTXT(++allTimeMaxId);

		//Read the number just written in the txt
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
			} catch (IOException e) {
			}
		}

		return allTimeMaxId;
	}

	@Override
	public ArrayList<Product> getAllBBDD() {
		ArrayList<Product> productes = new ArrayList<Product>();
		ResultSet rs = null;
		int id, preu;
		Product product = null;


		try {
			rs = query("SELECT * FROM stock;");

			while(rs.next()) {
				id = rs.getInt(1);
				preu = rs.getInt(3);
				switch(rs.getString(2)) {
				case "Tree":
					product = new Tree(id, preu, rs.getInt(4));
					break;
				case "Flower":
					product = new Flower (id, preu, rs.getString(4));
					break;
				case "Decoration":
					product = new Decor (id, preu, rs.getString(4));
					break;
				default:
					System.out.println("si veus aixo el programador es gilipolles");
					break;
				}

				productes.add(product);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}



		return productes;
	}
	@Override
	public void updateDDBB(List<Product> products){
		System.out.println("aixo no s'hauria de fer servir");
	}

	@Override
	public void addProduct(Product product) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection conn;

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/floristeria", "root", "rootroot");


			String[] tipus = product.getClass().toString().split(".", 2);

			String insert = "INSERT INTO `floristeria`.`stock` (`stock_id`, `type`, `price`, `other`) VALUES ('" + product.getIdProduct() + "', '" + tipus[1] 
					+ "', '" + product.getPrice() + "', '" + product.variableExtra() + "');" ;

			PreparedStatement preparao = conn.prepareStatement(insert);

			preparao.execute();

		} catch (SQLException e) {//aquesta excepcio salta pero es nomes un "data truncated" es a dir que en teoria a la columna "type" es perden dades, el tipus surt be ossigui que no tinc ni idea que s'esta perdent, jo crec que el podem ignorar pefrectament
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	//TODO Truncate
	@Override
	public void removeProduct(Product product) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection conn;

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/floristeria", "root", "rootroot");


			String delete = "DELETE FROM `floristeria`.`stock` WHERE (`stock_id` = '" + product.getIdProduct() + "') and (`type` = '" + product.getType() + "') and (`price` = '" + product.getPrice() 
			+ "') and (`other` = '" + product.variableExtra() + "');";

			PreparedStatement preparao = conn.prepareStatement(delete);

			preparao.execute();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	public ResultSet query(String query) throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/floristeria", "root", "rootroot");
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

		ResultSet rs = stmt.executeQuery(query);

		return rs;
	}



}
