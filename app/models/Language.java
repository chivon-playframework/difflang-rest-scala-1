package models;

import org.bson.Document;
import org.joda.time.DateTime;
import java.util.Optional;

public class Language extends BaseModel {

    private static final String NAME = "name";
    private static final String FLAG = "flag";

    public final String name;
    public final String flag;

    public Language(final String name,
                    final String flag,
                    final Optional<String> appId,
                    final Optional<String> description,
                    final Optional<String> locale,
                    final Optional<DateTime> updatedAt,
                    final Optional<Boolean> isActive) {
        super(appId, description, locale, updatedAt, isActive);
        this.name = name;
        this.flag = flag;
    }

    public Language(Document doc) {
        this(doc.getString(NAME),
                doc.getString(FLAG),
                Optional.ofNullable(doc.getString(APP_ID)),
                Optional.ofNullable(doc.getString(DESCRIPTION)),
                Optional.ofNullable(doc.getString(LOCALE)),
                Optional.ofNullable(DateTime.parse(doc.getString(UPDATED_AT))),
                Optional.ofNullable(doc.getBoolean(IS_ACTIVE))
                );
    }

    public Document toDocument() {
        return getBaseDocument()
                .append(NAME, name)
                .append(FLAG, this.flag);
    }
}
