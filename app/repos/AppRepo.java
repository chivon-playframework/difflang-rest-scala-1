package repos;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.rx.client.*;
import models.App;
import org.bson.Document;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// import java.util.concurrent.CountDownLatch;
import static com.mongodb.client.model.Filters.eq;
// import static java.lang.Thread.sleep;

// http://mongodb.github.io/mongo-java-driver-rx/1.2/getting-started/quick-tour/
// At this layer, we only deal with data access operation
// All business logic, validation, etc. will be done at service layer
public class AppRepo implements IAppRepo {
    private final MongoCollection<Document> appsCollection;
    private final String DB_NAME = "difflang_dev";
    private final String APPS_COLLECTION_NAME = "apps";

    public AppRepo() {
        final MongoClient client = MongoClients.create();
        final MongoDatabase db = client.getDatabase(DB_NAME);
        this.appsCollection = db.getCollection(APPS_COLLECTION_NAME);
    }

    public Observable<List<App>> find() {
        return appsCollection.find().toObservable()
                .map(doc -> new App(doc)).toList();
    }

    public Observable<Optional<App>> findById(final String id) {
        return appsCollection
                .find(eq("_id", id))
                .first()
                .map(doc -> new App(doc))
                .toList()
                .map(apps -> apps.size() == 0 ? Optional.empty()
                : Optional.of(apps.get(0)));
    }

    public Observable<Optional<App>> findByName(final String name) {
        return appsCollection
                .find(eq("name", name))
                .first()
                .map(doc -> new App(doc))
                .toList()
                .map(apps -> apps.size() == 0 ? Optional.empty()
                        : Optional.of(apps.get(0)));
    }

    public Observable<Success> insertOne(App app) {
        Document doc = app.toDocument();
        return appsCollection.insertOne(doc);
    }

    public Observable<Success> insertMany(List<App> apps) {
        List<Document> docs = new ArrayList<>();
        for(App app : apps) {
            docs.add(app.toDocument());
        }
        return appsCollection.insertMany(docs);
    }

    public Observable<UpdateResult> updateOne(String id, Document doc) {
        return appsCollection.updateOne(eq("_id", id), new Document("$set", doc));
    }

    public Observable<DeleteResult> deleteOne(String id) {
        return appsCollection.deleteOne(eq("_id", id));
    }
}
