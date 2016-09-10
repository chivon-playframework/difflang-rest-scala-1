package repos;

import enums.Env;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import play.api.libs.json.Json;
import play.api.mvc.Result;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MongoRepoTest {
    private IMongoRepo mongoRepo;

    @Before
    public void setup() {
        this.mongoRepo = mock(IMongoRepo.class);
        if(this.mongoRepo != null)
            this.mongoRepo.setCollection("translators");
        assert this.mongoRepo != null;
        this.mongoRepo.init(Env.TEST);
    }

    @Test
    public void testConnection() {
       assertNotNull(this.mongoRepo);
    }
}
