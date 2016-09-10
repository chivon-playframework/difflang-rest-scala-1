package models;

import org.bson.Document;

import java.util.Optional;

public class Country extends BaseModel {

    private static final String NAME = "name";
    private static final String CODE = "code";

    public final String name;
    public final String code;

    public Country(final String name,
                   final String code,
                   final Optional<String> appId,
                   final Optional<String> description,
                   final Optional<String> locale,
                   final Optional<Long> updatedAt,
                   final Boolean isActive) {
        super(appId, description, locale, updatedAt, isActive);
        this.name = name;
        this.code = code;
    }

    public Country(Document doc) {
        this(doc.getString(NAME),
                doc.getString(CODE),
                Optional.ofNullable(doc.getString(APP_ID)),
                Optional.ofNullable(doc.getString(DESCRIPTION)),
                Optional.ofNullable(doc.getString(LOCALE)),
                Optional.ofNullable(doc.getLong(UPDATED_AT)),
                doc.getBoolean(IS_ACTIVE)
        );
    }

    public Document toDocument() {
        return getBaseDocument()
                .append(NAME, this.name)
                .append(CODE, this.code);
    }
}
