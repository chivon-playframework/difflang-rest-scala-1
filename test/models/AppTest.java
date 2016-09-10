package models;

import com.google.gson.Gson;
import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testCreateAppModel() {
        Long updatedAt = new DateTime(DateTimeZone.UTC).getMillis();
        App app = new App();
        System.out.println(app);
        System.out.format("name: %s", app.name);
        assertNotNull(app);
        assertEquals(app.name, "");

        App app2 = new App(
                "Web app",
                Optional.empty(),
                Optional.ofNullable("en"),
                Optional.ofNullable(updatedAt),
                true
        );
        System.out.println(app2);
        System.out.format("app2.name: %s", app2.name);
        assertNotNull(app2);
        assertEquals(app2.name, "Web app");
        assertEquals(app2.updatedAt, updatedAt);

        Document doc = app2.toDocument();
        System.out.println(doc);
        assertNotNull(doc);
        assertEquals(doc.get("name"), "Web app");
        System.out.format("doc: %s", doc.toJson());

        DateTime dateTime = new DateTime(updatedAt);
        System.out.println("");
        System.out.format("dateTime: %s", dateTime.toString());

        Gson gson = new Gson();
        String jsonString = gson.toJson(app2);
        System.out.format("\njsonString of app2: %s\n", jsonString);

        Document doc2 = Document.parse(jsonString);
        assertNotNull(doc2);
        System.out.println(doc2);

        App app3 = gson.fromJson(jsonString, App.class);
        System.out.println(app3);
        System.out.println(app3.name);
        assertNotNull(app3);
    }

    @Test
    public void testCreateDocumentFromAppModel() {
        Document doc = new Document("_id", UUID.randomUUID().toString())
                .append("locale", "en")
                .append("createdAt", new DateTime(DateTimeZone.UTC).getMillis())
                .append("updatedAt", new DateTime(DateTimeZone.UTC).getMillis())
                .append("isActive", true)
                .append("description", "Web application")
                .append("name", "Difflang web application");
        App app = new App(doc);
        assertNotNull(app);
        assertEquals(app.name, "Difflang web application");
        assertEquals(app.description, "Web application");
        assertTrue(app.isActive);
        System.out.println(app);
    }
}
