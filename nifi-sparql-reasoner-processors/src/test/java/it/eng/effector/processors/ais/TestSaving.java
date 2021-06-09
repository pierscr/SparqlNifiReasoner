package it.eng.effector.processors.ais;

import it.eng.rslab.rdf4j.inference.MultipleRulesInference;
import it.eng.rslab.rdf4j.inference.MultipleRulesInferenceFactory;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.config.SailRegistry;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestSaving {
    public static void main(String[] args) throws IOException {
        SailRegistry.getInstance().add(new MultipleRulesInferenceFactory());
        Repository repo = new SailRepository(new MultipleRulesInference(new MemoryStore(), QueryLanguage.SPARQL, new File("/home/piero/Development/sparql/rulefolder")));
        RepositoryConnection con=repo.getConnection();
        File file = new File("/home/piero/Development/sparql/test-ais/ais5.ttl");
        FileOutputStream fos = new FileOutputStream("/home/piero/Development/sparql/results/output.ttl");
        try {
            con.add(file);
            RDFHandler writer = Rio.createWriter(RDFFormat.TURTLE, fos);
            con.export(writer);
            con.clear();
        }finally {
            con.close();
        }
    }
}
