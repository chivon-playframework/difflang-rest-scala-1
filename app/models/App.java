package models;

import org.bson.Document;
import org.joda.time.DateTime;
import java.util.Optional;

public class App extends BaseModel {
    private static final String NAME = "name";
    public final String name;

    public App(final String name,
               final Optional<String> description,
               final Optional<String> locale,
               final Optional<DateTime> updatedAt,
               final Optional<Boolean> isActive
               ) {
        super(null, description, locale, updatedAt, isActive);
        this.name = name;
    }

    public App(Document doc) {
        this(doc.getString(NAME),
                Optional.ofNullable(doc.getString(DESCRIPTION)),
                Optional.ofNullable(doc.getString(LOCALE)),
                Optional.ofNullable(DateTime.parse(doc.getString(UPDATED_AT))),
                Optional.ofNullable(doc.getBoolean(IS_ACTIVE))
        );
    }

    public Document toDocument() {
        return getBaseDocument()
                .append(NAME, this.name);
    }
}
