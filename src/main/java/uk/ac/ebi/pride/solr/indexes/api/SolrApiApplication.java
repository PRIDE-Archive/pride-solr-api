package uk.ac.ebi.pride.solr.indexes.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "uk.ac.ebi.pride.solr.indexes")
public class SolrApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SolrApiApplication.class, args);
    }
}
