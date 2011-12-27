package com.netnumeri.server;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.netnumeri.server.auth.MD5EncryptionServiceImpl;
import com.netnumeri.server.auth.OneWayEncryptionService;
import com.netnumeri.server.command.CommandProcessor;
import com.netnumeri.server.command.ICommandProcessor;
import org.apache.lucene.analysis.Analyzer;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import java.util.HashMap;
import java.util.Map;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ICommandProcessor.class).to(CommandProcessor.class);
        bind(OneWayEncryptionService.class).to(MD5EncryptionServiceImpl.class);
    }

    @Provides
    @Singleton
    PersistenceManagerFactory getPersistenceManagerFactory() {
        Map<String, String> props = new HashMap<String, String>();
        props.put("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.store.appengine.jdo.DatastoreJDOPersistenceManagerFactory");
        props.put("javax.jdo.option.ConnectionURL", "appengine");
        props.put("javax.jdo.option.NontransactionalRead", "true");
        props.put("javax.jdo.option.NontransactionalWrite", "true");
        props.put("javax.jdo.option.RetainValues", "true");
        props.put("javax.jdo.option.TransactionType", "RESOURCE_LOCAL");
        props.put("datanucleus.appengine.autoCreateDatastoreTxns", "true");
        props.put("datanucleus.appengine.attachSameDatastore", "true");

        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(props);
        return pmf;
    }

    @Provides
    @Singleton
    Analyzer getAnalyzer() {
        // This is just English for now
        return new PorterStemAnalyzer();
    }
}
