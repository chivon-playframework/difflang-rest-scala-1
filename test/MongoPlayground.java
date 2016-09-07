import static play.test.Helpers.*;
import static org.junit.Assert.*;

import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import org.bson.Document;
import play.test.*;
import org.junit.*;

/**
 * https://github.com/mongodb/mongo-java-driver/blob/3.0.x/driver-async/src/examples/tour/QuickTour.java
 * http://mongodb.github.io/mongo-java-driver/3.0/driver-async/getting-started/quick-tour/
 */
public class MongoPlayground {
    private final MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost"));
    private MongoDatabase database = mongoClient.getDatabase("difflang_dev");
    MongoCollection<Document> collection;

    SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
        @Override
        public void onResult(Void result, Throwable t) {
            System.out.println("Operation finished!");
        }
    };

    @Before
    public void setup() {
        if (collection == null) {
            database.createCollection("translators",
                    new CreateCollectionOptions().capped(false),
                    callbackWhenFinished);
            collection = database.getCollection("translators");
        }
    }

    @Test
    public void testClientAndDatabase() {
        assertNotNull(mongoClient);
        assertNotNull(database);
    }

    @Test
    public void testInsertDocument() {
        // Construct nested JSON documents
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));
        // We can anonymous object
//        collection.insertOne(doc, new SingleResultCallback<Void>() {
//            @Override
//            public void onResult(Void result, Throwable t) {
//                System.out.println("Inserted!");
//            }
//        });
        // Or we can use lambda expression
        assertNotNull(collection);
        collection.insertOne(doc, (Void result, final Throwable t) ->
            System.out.println("Inserted!"));

        assertNotNull(doc);
    }

    @Test
    public void testGetCollections() {
        database.listCollectionNames().forEach(new Block<String>() {
            @Override
            public void apply(final String name) {
                System.out.println(name);
            }
        }, callbackWhenFinished);
        assertTrue(1 == 1);
    }

    @Test
    public void findDocument() {
        collection.find().first(new SingleResultCallback<Document>() {
            @Override
            public void onResult(Document result, Throwable t) {
                System.out.println(result.toJson());
            }
        });
        assertTrue(1 == 1);
    }
}
