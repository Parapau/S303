package resources;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.*;
import org.bson.Document;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Connection {

    private static final String DATABASE_URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "TiendaDB";
    private static final String COLLECTION_NAME = "MisTickets";

    public static ArrayList<Ticket> obtenerTicketsDesdeDB() {

        ArrayList<Ticket>Tickets = new ArrayList<>();

        System.out.print("Empezando exportación de Tickets antiguos :) ");
        for (int i = 3; i > 0; i--) {
            System.out.print(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print("\b");
        }

        System.out.println("READY!! XD ");

        Ticket ticket;

        /*Esta parte elimina la información que arroja mongo cuando interactua con la base de datos
        * -----------------------------------------------------------------------------------------*/
        // Configuración del nivel de registro de Logback para MongoDB
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger mongoLogger = loggerContext.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.ERROR); // Configura el nivel de registro según tus necesidades
        //-------------------------------------------------------------------------------------------

        try (MongoClient mongoClient = new MongoClient(new MongoClientURI(DATABASE_URI))) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> ticketsCollection = database.getCollection(COLLECTION_NAME);

            for (Document documentoTicket : ticketsCollection.find()) {
                ticket = new Ticket();

                List<Document> documentosProductos = documentoTicket.getList("productos", Document.class);

                for (Document documentoProducto : documentosProductos) {
                    String nombre = documentoProducto.getString("nombre");
                    double precio = documentoProducto.getDouble("precio");

                    switch (nombre) {
                        case "Arbol" -> {
                            int altura = documentoProducto.getInteger("altura");
                            ticket.getProducts().add(new Tree(1, precio, altura));
                        }
                        case "Flor" -> {
                            String color = documentoProducto.getString("color");
                            ticket.getProducts().add(new Flower(2, precio, color));
                        }
                        case "Decoración" -> {
                            String material = documentoProducto.getString("material");
                            ticket.getProducts().add(new Decor(3, precio, material));
                        }
                    }
                }

                Tickets.add(ticket);
            }

            System.out.println("Exportación de Tickets exitosa :D");

            if (Tickets.size() == 0) {
                System.out.println("La base de datos no tiene tickets registrados hasta el momento :)");
            } else {
                System.out.println("Se han importado " + Tickets.size() + " " + "Tickets: ");
            }

        } catch (Exception e) {
            System.err.println("Error al interactuar con la base de datos: " + e.getMessage());
        }

        return Tickets;
    }


    public static void guardarTicketEnDB(Ticket ticket) {
        System.err.println("MongoDB connection started");

        MongoClientURI uri = new MongoClientURI(DATABASE_URI);

        /*Esta parte elimina la información que arroja mongo cuando interactua con la base de datos
         * -----------------------------------------------------------------------------------------*/
        // Configuración del nivel de registro de Logback para MongoDB
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger mongoLogger = loggerContext.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.ERROR); // Configura el nivel de registro según tus necesidades
        //-------------------------------------------------------------------------------------------

        try (MongoClient mongoClient = new MongoClient(uri)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> ticketsCollection = database.getCollection(COLLECTION_NAME);

            Document documentoTicket = new Document("productos", new ArrayList<>());
            for (Product producto : ticket.getProducts()) {
                if (producto instanceof Tree tree) {
                    documentoTicket.getList("productos", Document.class).add(new Document("nombre", "Árbol")
                            .append("altura", tree.getHeight()).append("precio", tree.getHeight()));

                } else if (producto instanceof Flower flower) {
                    documentoTicket.getList("productos", Document.class).add(new Document("nombre", "Flor")
                            .append("color", flower.getColor()).append("precio", flower.getPrice()));

                } else if (producto instanceof Decor decor) {
                    documentoTicket.getList("productos", Document.class).add(new Document("nombre", "Decoración")
                            .append("material", decor.getMaterial()).append("precio", decor.getPrice()));
                }
            }

            ticketsCollection.insertOne(documentoTicket);

            System.out.println("El ticket se ha guardado en la base de datos correctamente.");
        } catch (Exception e) {
            System.err.println("Error al interactuar con la base de datos: " + e.getMessage());
        }
    }
}
