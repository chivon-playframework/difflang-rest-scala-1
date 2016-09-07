package repos;

import org.junit.Before;
import org.junit.Test;
import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;


public class MongoRepoTest {
    @Inject
    private IMongoRepo mongoRepo;

    @Before
    public void setup() {
        if(mongoRepo != null)
            mongoRepo.setCollection("translators");
    }

    @Test
    public void testFind() {
        assertNotNull(mongoRepo);
    }
}
