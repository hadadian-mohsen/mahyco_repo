package ir.mahyco.p2p.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing(tablePrefix = "KCS_")
public class BatchConfig4P2P  extends DefaultBatchConfiguration {
    public static final String ISSUER_READING_JOB = "read-iss-file";

    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Bean
    public Job myJob() {
        return jobBuilderFactory.get("myJob")
                .start(readFileStep())
                .next(compareWithDatabaseStep())
                .next(writeToDatabaseStep())
                .build();
    }



}