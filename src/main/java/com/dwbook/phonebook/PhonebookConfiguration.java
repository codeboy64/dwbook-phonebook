package com.dwbook.phonebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Max;

public class PhonebookConfiguration extends Configuration {
    @JsonProperty
    @NotEmpty
    private String message;


    @JsonProperty
    @Max(10)
    private int messageRepetitions;

    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    public String getMessage() {
        return message;
    }


    public int getMessageRepetitions() {
        return messageRepetitions;
    }

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
}
