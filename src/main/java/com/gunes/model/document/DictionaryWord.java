package com.gunes.model.document;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.HashMap;


@Entity("DICTIONARY_WORD")
public class DictionaryWord {

    @Id
    @Property("id")
    private ObjectId id;

    @Property("id")
    private String key;

    @Embedded
    private HashMap<String, String> translations = new HashMap<>();

    public ObjectId getId() {
        return id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public HashMap<String, String> getTranslations() {
        return translations;
    }

    public void setTranslations(final HashMap<String, String> translations) {
        this.translations = translations;
    }
}