package models;

import org.bson.Document;

import java.util.Date;
import java.util.Optional;

public class Translator extends BaseModel {

    private static final String FIRST_NAME = "firstName";
    private static final String MIDDLE_NAME = "middleName";
    private static final String LAST_NAME = "lastName";
    private static final String DATE_OF_BIRTH = "dateOfBirth";
    private static final String SSN = "ssn";
    private static final String LANGUAGES = "languages";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";

    public final String firstName;
    public final String middleName;
    public final String lastName;
    public final Date dateOfBirth;
    public final String ssn;
    public final String languages; // Comma-separated languages
    public final String email;
    public final String phone;


    public Translator(final String firstName,
                      final Optional<String> middleName,
                      final String lastName,
                      final Date dateOfBirth,
                      final String languages,
                      final String ssn,
                      final String email,
                      final String phone,
                      final Optional<String> appId,
                      final Optional<String> description,
                      final Optional<String> locale,
                      final Optional<Long> updatedAt,
                      final Boolean isActive
    ) {
        super(appId, description, locale, updatedAt, isActive);
        this.firstName = firstName;
        this.middleName = middleName.isPresent() ? middleName.get() : "";
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.languages = languages;
        this.ssn = ssn;
        this.email = email;
        this.phone = phone;
    }

    public Translator(Document doc) {
        this(doc.getString(FIRST_NAME),
                Optional.ofNullable(doc.getString(MIDDLE_NAME)),
                doc.getString(LAST_NAME),
                doc.getDate(DATE_OF_BIRTH),
                doc.getString(LANGUAGES),
                doc.getString(SSN),
                doc.getString(EMAIL),
                doc.getString(PHONE),
                Optional.ofNullable(doc.getString(APP_ID)),
                Optional.ofNullable(doc.getString(DESCRIPTION)),
                Optional.ofNullable(doc.getString(LOCALE)),
                Optional.ofNullable(doc.getLong(UPDATED_AT)),
                doc.getBoolean(IS_ACTIVE)
        );
    }

    public Document toDocument() {
        return getBaseDocument()
                .append(FIRST_NAME, this.firstName)
                .append(MIDDLE_NAME, this.middleName)
                .append(LAST_NAME, this.lastName)
                .append(DATE_OF_BIRTH, this.dateOfBirth)
                .append(LANGUAGES, this.languages)
                .append(SSN, this.ssn)
                .append(EMAIL, this.email)
                .append(PHONE, this.phone);
    }
}