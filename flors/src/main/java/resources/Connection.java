package resources;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.*;
import org.bson.Document;
import org.bson.io.BsonOutput;

import java.util.ArrayList;
import java.util.List;

public class Connection {

    private static final String DATABASE_URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "TiendaDB";
    private static final String COLLECTION_NAME = "MisTickets";

    public void guardarTicketsEnDB(ArrayList<Ticket> tickets) {
        System.err.println("MongoDB connection started");

        MongoClientURI uri = new MongoClientURI(DATABASE_URI);

        try (MongoClient mongoClient = new MongoClient(uri)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> ticketsCollection = database.getCollection(COLLECTION_NAME);

            List<Document> documentosTickets = new ArrayList<>();
            for (Ticket ticket : tickets) {
                Document documentoTicket = new Document("productos", new ArrayList<>());
                for (Product producto : ticket.getProducts()) {
                    if (producto instanceof Tree tree) {
                        documentoTicket.getList("productos", Document.class).add(new Document("nombre", "Arbol")
                                .append("altura", tree.getHeight()).append("precio", tree.getPrice()));

                    } else if (producto instanceof Flower flower) {
                        documentoTicket.getList("productos", Document.class).add(new Document("nombre", "Flor")
                                .append("color", flower.getColor()).append("precio", flower.getPrice()));

                    } else if (producto instanceof Decor decor) {
                        documentoTicket.getList("productos", Document.class).add(new Document("nombre", "Decoración")
                                .append("material", decor.getMaterial()).append("precio", decor.getPrice()));
                    }
                }
                documentosTickets.add(documentoTicket);
            }

            ticketsCollection.insertMany(documentosTickets);

            System.out.println("Los tickets se han guardado en la base de datos correctamente.");
        } catch (Exception e) {
            System.err.println("Error al interactuar con la base de datos: " + e.getMessage());
        }
    }

    public static void guardarTicketEnDB(Ticket ticket) {
        System.err.println("MongoDB connection started");

        MongoClientURI uri = new MongoClientURI(DATABASE_URI);

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
