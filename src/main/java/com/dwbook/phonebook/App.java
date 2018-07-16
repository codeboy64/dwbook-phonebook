package com.dwbook.phonebook;

import com.dwbook.phonebook.resources.ContactResource;
import com.dwbook.phonebook.resources.ClientResource;

import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.jdbi.DBIFactory;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.sun.jersey.api.client.Client;
import io.dropwizard.client.JerseyClientBuilder;

public class App extends Application<PhonebookConfiguration>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> b) {

    }

    @Override
    public void run(PhonebookConfiguration c, Environment e)throws Exception {
        LOGGER.info("Method App#run() called");
        for (int i=0; i < c.getMessageRepetitions(); i++) {
            System.out.println(c.getMessage());
        }

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(e, c.getDataSourceFactory(), "mysql");
        final Client client = new JerseyClientBuilder(e).build("REST Client");
        client.addFilter(new HTTPBasicAuthFilter("wsuser", "wspassword"));

        e.jersey().register(new ContactResource(jdbi, e.getValidator()));
        e.jersey().register(new ClientResource(client));
        e.jersey().register(new BasicAuthProvider<Boolean>(new PhonebookAuthenticator(jdbi), "Web Service Realm"));
    }

    public static void main( String[] args ) throws Exception {
        new App().run(args);
    }
}
