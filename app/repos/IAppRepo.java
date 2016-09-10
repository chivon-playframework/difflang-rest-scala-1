package repos;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.rx.client.Success;
import models.App;
import org.bson.Document;
import rx.Observable;
import java.util.List;
import java.util.Optional;

public interface IAppRepo {
    Observable<List<App>> find();
    Observable<Optional<App>> findById(final String id);
    Observable<Optional<App>> findByName(final String name);
    Observable<Success> insertOne(App app);
    Observable<Success> insertMany(List<App> apps);
    Observable<UpdateResult> updateOne(String id, Document doc);
    Observable<DeleteResult> deleteOne(String id);
}
