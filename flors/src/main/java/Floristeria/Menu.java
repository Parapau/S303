package Floristeria;

import model.*;
import resources.Connection;
import services.IDDBBManager;
import services.txtManagerImpl;

//import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

	static Scanner scanner = new Scanner(System.in);
	static IDDBBManager DDBBManager = new txtManagerImpl();
	//pido como parámetro el array de productos de la clase rienda
	public static void Options(ArrayList<Product>stock, ArrayList<Ticket>sales){

		int option = 3;

		do {
			System.out.print(
					"""
							\s
							______________________________________________________________
							0-Salir.                        7-Retirar Flor.
							1-Crear Floristeria.            8-Retirar Decoración.
							2-Agregar un Árbol.             9-Stock de Cantidades.
							3-Agregar una Flor.             10-Valor total de Floristería.
							4-Agregar una Decoración.       11-Crear ticket de compra.
							5-Imprimir Stock.               12-Mostrar lista de compras.
							6-Retirar Árbol.                13-Mostrar Ganancias.
							______________________________________________________________
							✏️Elige una opción: """
					);
			String choose= scanner.next();
			try {
			option = Integer.parseInt(choose);
			RunMenu(option, stock, sales);
			} catch (NumberFormatException e) {
				System.out.println("por favor, introduce valores con sentido.");
			}
			

		}while (option != 0);
	}

	public static void RunMenu(int option, ArrayList<Product>stock, ArrayList<Ticket>sales){

		switch (option) {
		case 0 -> System.out.println(" ✅Hasta Pronto :)");
		case 1 -> NewFlorist();
		case 2 -> NewTree(stock);
		case 3 -> NewFlower(stock);
		case 4 -> NewDecor(stock);
		case 5 -> printStock(stock);
		case 6 -> RemoveTree(stock);
		case 7 -> RemoveFlower(stock);
		case 8 -> RemoveDecor(stock);
		case 9 -> PrintQuantity(stock);
		case 10 -> PrintValueFlorist(stock);
		case 11 -> NewTicket(stock, sales);
		case 12 -> PrintTickets(sales);
		case 13 -> Profits(sales);
		default -> System.out.println("Opcion no encontrada");
		}

	}

	//Métodos menu---------------------------------------->>

	//crear floristeria
	public static void NewFlorist(){
		System.out.print("Introduce el nombre de la nueva Floristería: ");
		String name = scanner.next();
		new Store(name);
		System.out.println("✅Tienda " + name + " Creada correctamente :)");
	}


	//Agregar arbol
	public static void NewTree(ArrayList<Product>stock){

		System.out.print("Introduce su altura: ");
		String height = scanner.next();
		double valueHeight = Double.parseDouble(height);

		System.out.print("Introduce su precio: ");
		String price = scanner.next();
		double valuePrice = Double.parseDouble(price);

		Tree tree = new Tree(valueHeight, DDBBManager,valuePrice);
		stock.add(tree);
		DDBBManager.addProduct(tree);

		System.out.println("✅Árbol agregado correctamente al Stock ;)");

	}


	//Agregar Flor
	public static void NewFlower(ArrayList<Product>stock){

		System.out.print("Introduce su Color: ");
		String color = scanner.next();

		System.out.print("Introduce su precio: ");
		String price = scanner.next();
		double valuePrice = Double.parseDouble(price);

		Flower flower = new Flower(valuePrice,DDBBManager, color );
		stock.add(flower);
		DDBBManager.addProduct(flower);

		System.out.println("✅Flor ingresada correctamente al Stock ;)\n");

	}



	//Agregar Decoracion
	public static void NewDecor(ArrayList<Product>stock){

		System.out.print("Introduce su Material: ");
		String material = scanner.next();

		System.out.print("Introduce su precio: " );
		String price = scanner.next();
		double valuePrice = Double.parseDouble(price);

		Decor decoration = new Decor(valuePrice, DDBBManager, material);
		stock.add(decoration);
		DDBBManager.addProduct(decoration);

		System.out.println("✅Decoración agregada correctamente al Stock ;)");

	}

	//imprimir Stock
	public static void printStock(ArrayList<Product>stock){
		//recorrer stock con metodo foreach y lambda
		stock.stream().map(Product::ToString).forEach(System.out::println);

	}

	//retirar Arbol
	public static void RemoveTree(ArrayList<Product>stock){

		//imprimo por consola la lista de árboles que tenemos en stock
		stock.stream().filter(product -> product instanceof Tree).map(Product::ToString).forEach(System.out::println);

		//pido al usuario que ingrese el índice del arbol que desee
		System.out.print("Introduce el índice del árbol que deseas remover: ");
		String Text = scanner.next();
		int number = Integer.parseInt(Text);

		//Verificamos el indice del árbol dentro del stock
		int index = SearchIndex(number,stock);

		//Lógica para remover árbol del arrayList
		if(index == -1){
			System.out.println("❌No hemos encontrado el índice de este árbol :(\n");
		}else{
			DDBBManager.removeProduct(stock.get(index));
			stock.remove(index);
			System.out.println("✅Árbol retirado correctamente :D\n");
		}

		//lógica para agregar árbol al stock sales y eliminar de archivo txt

	}

	//Retirar flor
	public static void RemoveFlower(ArrayList<Product>stock){

		//imprimo por consola la lista de flores que tenemos en stock
		stock.stream().filter(product -> product instanceof Flower).map(Product::ToString).forEach(System.out::println);

		//pido al usuario que ingrese el índice de la flor que desee
		System.out.print("Introduce el índice de la flor que deseas remover: ");
		String Text = scanner.next();
		int number = Integer.parseInt(Text);

		//Verificamos el índice de la flor dentro del stock
		int index = SearchIndex(number,stock);

		//Lógica para remover la flor del arrayList
		if(index == -1){
			System.out.println("❌No hemos encontrado el índice de esta flor :(\n");
		}else{
			DDBBManager.removeProduct(stock.get(index));
			stock.remove(index);
			System.out.println("✅Flor retirada correctamente :D\n");
		}

		//lógica para agregar flor al stock sales y eliminar de archivo txt

	}

	//Retirar Decoración
	public static void RemoveDecor(ArrayList<Product>stock){

		//imprimo por consola la lista de decoraciones que tenemos en stock
		stock.stream().filter(product -> product instanceof Decor).map(Product::ToString).forEach(System.out::println);

		//pido al usuario que ingrese el índice de la decoración que desee
		System.out.print("Introduce el índice de la decoración que deseas remover: ");
		String Text = scanner.next();
		int number = Integer.parseInt(Text);

		//Verificamos el índice de la decoración dentro del stock
		int index = SearchIndex(number,stock);

		//Lógica para remover la decoración del arrayList
		if(index == -1){
			System.out.println("❌No hemos encontrado el índice de esta decoración :(");
		}else{
			DDBBManager.removeProduct(stock.get(index));
			stock.remove(index);
			System.out.println("✅Decoración retirada correctamente :D");
		}

		//lógica para agregar flor al stock sales y eliminar de archivo txt

	}

	//imprimir cantidad de stock
	public static void PrintQuantity(ArrayList<Product> stock){

		int treeCounter = 0;
		int flowerCounter = 0;
		int decorCounter = 0;

		for (Product product : stock) {
			if (product instanceof Tree) {
				treeCounter++;
			} else if (product instanceof Flower) {
				flowerCounter++;
			} else if (product instanceof Decor) {
				decorCounter++;
			}
		}

		System.out.println("✅En el stock tenemos: \n\n" +
				"\tÁrboles " + treeCounter + " unidades\n" +
				"\tFlores " + flowerCounter + " unidades\n" +
				"\tDecoraciones " + decorCounter + " unidades.");

	}

	//Imprimir Valor total de floristeria
	public static void PrintValueFlorist(ArrayList<Product>stock){

		int ValueFlorist = 0;

		for (Product product : stock) {
			ValueFlorist += product.getPrice();
		}

		System.out.println("✅El valor total del stock en la floristeria es de: " + ValueFlorist + " Euros.");

	}

	//Crear Ticket con multiples productos

	public static void NewTicket(ArrayList<Product>stock ,ArrayList<Ticket>sales){
		if (stock .size() != 0) {
			//creamos un objeto ticket
			Ticket ticket = new Ticket();
			String msg, text, text1, indexStr;
			int number, intdex, index;
			//pedimos al usuario la cantidad de productos que desea agregar al ticket
			msg = ("Introduzca la cantidad de productos que desea agregar: ");
			do {

				System.out.println(msg);
				text = scanner.next();
				number = Integer.parseInt(text);

				msg = "Lo siento pero ese numero excede nuestra cantidad total de stock (" + stock.size() + ") por favor, introduce un numero que sea menor a.";
			} while (number > stock.size());
			//lógica para crear ticket productos
			for (int i = 0; i < number; i++) {
				msg = ("Introduzca el tipo del producto (arbol, flor, decoracion): ");

				do {
					System.out.print(msg);
					text1 = scanner.next();
					msg = "Eso no es una opcion. Introduzca el tipo del producto (arbol, flor, decoracion):";
				}while (!text1.equalsIgnoreCase("arbol") && !text1.equalsIgnoreCase("flor") && !text1.equalsIgnoreCase("decoracion"));


				switch (text1.toLowerCase()) {
				case "arbol" -> {
					//imprimo por consola la lista de árboles que tenemos en stock
					stock.stream().filter(product -> product instanceof Tree).map(Product::ToString).forEach(System.out::println);

					//pido al usuario que ingrese el índice del arbol que desee
					msg = ("Introduce el índice del árbol que deseas comprar: ");

					do {
						System.out.println(msg);
						indexStr = scanner.next();
						index = Integer.parseInt(indexStr);
						msg = "❌No hemos encontrado el índice de este árbol :(, por favor vuelvelo a introducir";
						index = SearchIndex(index, stock);
					} while (index == -1);

					//Agrego el producto a la lista de ventas del ticket creado
					ticket.getProducts().add(stock.get(index));
					//elimino el producto del stock
					DDBBManager.removeProduct(stock.get(index));

					stock.remove(index);
					//aviso que el proceso ocurrió con exito


					System.out.println("✅Árbol agregado al ticket de compra :D");

				}
				case "flor" -> {
					//imprimo por consola la lista de flores que tenemos en stock
					stock.stream().filter(product -> product instanceof Flower).map(Product::ToString).forEach(System.out::println);



					msg = ("Introduce el índice de la flor que deseas comprar: ");

					do {
						System.out.println(msg);
						indexStr = scanner.next();
						index = Integer.parseInt(indexStr);
						msg = "❌No hemos encontrado el índice de esta flor :(, por favor vuelvelo a introducir";
						index = SearchIndex(index, stock);
					} while (index == -1);

					//Agrego el producto a la lista de ventas del ticket creado
					ticket.getProducts().add(stock.get(index));
					//elimino el producto del stock
					DDBBManager.removeProduct(stock.get(index));

					stock.remove(index);
					//aviso que el proceso ocurrió con exito
					System.out.println("✅Flor agregada al ticket de compra :D");
				}

				case "decoracion" -> {
					//imprimo por consola la lista de decoraciones que tenemos en stock
					stock.stream().filter(product -> product instanceof Decor).map(Product::ToString).forEach(System.out::println);

					//pido al usuario que ingrese el índice de la decoración que desee
					System.out.print("Introduce el índice de la decoración que deseas comprar: ");
					String Text2 = scanner.next();
					int number3 = Integer.parseInt(Text2);

					//Verificamos el índice de la decoración dentro del stock
					int index2 = SearchIndex(number3, stock);
					msg = ("Introduce el índice de la decoracion que deseas comprar: ");

					do {
						System.out.println(msg);
						indexStr = scanner.next();
						index = Integer.parseInt(indexStr);
						msg = "❌No hemos encontrado el índice de esta decoracion :(, por favor vuelvelo a introducir";
						index = SearchIndex(index, stock);
					} while (index == -1);


					//Agrego el producto a la lista de ventas del ticket creado
					ticket.getProducts().add(stock.get(index));
					//elimino el producto del stock
					DDBBManager.removeProduct(stock.get(index));

					stock.remove(index);
					//aviso que el proceso ocurrió con exito
					System.out.println("✅Decoracion agregada al ticket de compra :D");
				}

				default -> System.out.println("Opcion no encontrada. si ves esto es que soy gilipollas.");
				}
			}
			Connection.guardarTicketEnDB(ticket);

			//agregamos el tiquete al inventario de ventas de la tienda
			sales.add(ticket);
			System.out.println("✅Ticket generado exitosamente :D");
		} else {
			System.out.println("Lo siento pero no nos queda nada de stock en la tienda, por favor espera a que tengamos nuevos productos antes de comprar nada.");
		}

	}

	//Mostar Lista de tiquetes antiguos
	public static void PrintTickets(ArrayList<Ticket>sales){

		for (Ticket sale : sales) {
			System.out.println("----------------------------------------------------------------\n");
			System.out.println("Ticket #" + sale.getIdTicket() + " :\n");

			for (int j = 0; j < sale.getProducts().size(); j++) {
				if (sale.getProducts().get(j) instanceof Tree) {
					System.out.println(sale.getProducts().get(j).ToString());
				} else if (sale.getProducts().get(j) instanceof Flower) {
					System.out.println(sale.getProducts().get(j).ToString());
				} else if (sale.getProducts().get(j) instanceof Decor) {
					System.out.println(sale.getProducts().get(j).ToString());
				}
			}
		}

	}

	//total de dinero ganado en ventas
	public static void Profits(ArrayList<Ticket>sales){

		int sumProfit = 0;

		for (Ticket sale : sales) {
			for (int j = 0; j < sale.getProducts().size(); j++) {
				sumProfit += sale.getProducts().get(j).getPrice();
			}
		}

		System.out.println("✅El total de ganancias obtenidos" +
				" por la suma de los tickets de venta es de " + sumProfit + " euros.");

	}

	//Método para encontrar índices
	public static int SearchIndex(int number, ArrayList<Product>stock){

		int index = -1;
		int counter = 0;

		while(counter < stock.size() && index == -1){

			if(stock.get(counter).getIdProduct() == number){
				index = counter;
			}

			counter++;

		}
		return index;

	}

}
