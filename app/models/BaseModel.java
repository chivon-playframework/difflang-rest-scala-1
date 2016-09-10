package models;

import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Optional;
import java.util.UUID;

public class BaseModel {
    protected static final String ID = "_id";
    protected static final String DESCRIPTION = "description";
    protected static final String LOCALE = "locale";
    protected static final String CREATED_AT = "createdAt";
    protected static final String UPDATED_AT = "updatedAt";
    protected static final String IS_ACTIVE = "isActive";
    protected static final String APP_ID = "appId";

    public String id;
    public String description;
    public String locale;
    public Long createdAt;
    public Long updatedAt;
    public Boolean isActive;
    public String appId;

    public BaseModel() {}

    public BaseModel(final Optional<String> appId,
                     final Optional<String> description,
                     final Optional<String> locale,
                     final Optional<Long> updatedAt,
                     final Boolean isActive) {

        this.id = UUID.randomUUID().toString();
        this.createdAt = new DateTime(DateTimeZone.UTC).getMillis();
        this.updatedAt = updatedAt.isPresent() ? updatedAt.get() : new DateTime(DateTimeZone.UTC).getMillis();
        this.isActive = isActive;
        this.locale = locale.isPresent() ? locale.get() : "en";
        this.description = description.isPresent() ? description.get() : "";
        this.appId = appId.isPresent() ? appId.get() : "";
    }

    protected Document getBaseDocument() {
        return new Document(ID, this.id)
                .append(LOCALE, this.locale)
                .append(CREATED_AT, this.createdAt)
                .append(UPDATED_AT, this.updatedAt)
                .append(IS_ACTIVE, this.isActive)
                .append(DESCRIPTION, this.description)
                .append(APP_ID, this.appId);
    }
}
