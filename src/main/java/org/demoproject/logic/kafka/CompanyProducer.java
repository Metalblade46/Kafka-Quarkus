package org.demoproject.logic.kafka;

import io.smallrye.reactive.messaging.kafka.Record;
import org.demoproject.dataaccess.Entities.Company;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CompanyProducer {
    @Inject
    @Channel("company-out")
    Emitter<Record<Long,Company>> emitter;
    private static final Logger logger= LoggerFactory.getLogger(CompanyProducer.class);

    public void sendKafkaCompany(Company company){
        logger.info("Producing Company Data...");
        emitter.send(Record.of(company.getId(),company));
        logger.info("Produced Successfully!!");
    }
}
