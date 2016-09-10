package repos;

import models.App;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class AppRepoTest {

    private IAppRepo appRepo;

    @Before
    public void setup() {
        this.appRepo = new AppRepo();
    }

    @Test
    public void insertOne() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        Long updatedAt = new DateTime(DateTimeZone.UTC).getMillis();
        App app2 = new App(
                "Web app",
                Optional.empty(),
                Optional.ofNullable("en"),
                Optional.ofNullable(updatedAt),
                true
        );

        this.appRepo.insertOne(app2).subscribe( result -> System.out.println(result),
                t -> { System.err.println(t.toString()); latch.countDown(); },
                () -> { latch.countDown(); assertTrue(1 == 1); }
        );

        latch.await();
    }
}
