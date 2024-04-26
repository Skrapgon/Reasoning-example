package com.owltest;

import java.io.File;

import openllet.owlapi.OpenlletReasoner;
import openllet.owlapi.OpenlletReasonerFactory;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;

public class App {

    public static void main(String[] args) {
        File owlPath = new File("C:/Users/User/Desktop/projects/OWLTestApp/owltestapp/src/main/java/com/owltest/test_ontology.owl");
        
        try {
            OWLOntologyManager om = OWLManager.createOWLOntologyManager();
            OWLOntology o;
            o = om.loadOntologyFromOntologyDocument(owlPath);

            OpenlletReasoner reasoner = OpenlletReasonerFactory.getInstance().createReasoner(o);
            reasoner.prepareReasoner();
            reasoner.getKB().realize();
            InferredOntologyGenerator generator = new InferredOntologyGenerator(reasoner);
            OWLDataFactory dataFactory = om.getOWLDataFactory();
            generator.fillOntology(dataFactory, o);
            String currentPath = "C:/Users/User/Desktop/projects/OWLTestApp/owltestapp/src/main/java/com/owltest";
            // String currentPath = owlPath.getParent();
            String newOntologyName = "test.owl";
            // System.out.println(owlPath.getParent());
            File saveFile = new File(currentPath, newOntologyName);
            om.saveOntology(o, IRI.create(saveFile.toURI()));
        } catch (OWLOntologyCreationException | OWLOntologyStorageException e) {
            System.out.println("The ontology could not be opened or saved");
        }

    }
}
