package models;

import org.bson.Document;
import java.util.Date;
import java.util.Optional;

public class App extends BaseModel {
    private static final String NAME = "name";

    public final String name;

    public App() {
        super(Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                false);
        this.name = "";
    }

    public App(final String name,
               final Optional<String> description,
               final Optional<String> locale,
               final Optional<Long> updatedAt,
               final Boolean isActive
    ) {
        super(Optional.empty(), description, locale, updatedAt, isActive);
        this.name = name;
    }

    public App(Document doc) {
        this(doc.getString(NAME),
                Optional.ofNullable(doc.getString(DESCRIPTION)),
                Optional.ofNullable(doc.getString(LOCALE)),
                Optional.ofNullable(doc.getLong(UPDATED_AT)),
                doc.getBoolean(IS_ACTIVE)
        );
    }

    public Document toDocument() {
        return getBaseDocument()
                .append(NAME, this.name);
    }
}
