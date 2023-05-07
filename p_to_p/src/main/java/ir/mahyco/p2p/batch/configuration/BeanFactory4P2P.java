/*
package ir.mahyco.p2p.batch.configuration;

import ir.mahyco.p2p.batch.object.IssuerTransaction;
import ir.mahyco.p2p.batch.process.CustomItemProcessor;
import ir.mahyco.p2p.batch.process.CustomSkipPolicy;
import ir.mahyco.p2p.batch.process.SkippingItemProcessor;
import jakarta.xml.bind.Marshaller;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@EnableBatchProcessing(tablePrefix = "KCS_")
public class BeanFactory4P2P  extends DefaultBatchConfiguration {
public static final String ISSUER_READING_JOB = "read-iss-file";
    @Value("input/record.csv")
    private Resource inputCsv;

    @Value("input/recordWithInvalidData.csv")
    private Resource invalidInputCsv;

    @Value("file:xml/output.xml")
    private WritableResource outputXml;
    */
/*@Bean
    public Job readIssuerFileJob(JobRepository jobRepository) {

        return new JobBuilder(ISSUER_READING_JOB, jobRepository).start(new Step() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public void execute(StepExecution stepExecution) throws JobInterruptedException {

            }
        }).build();
    }*//*


    public ItemReader<IssuerTransaction> itemReader(Resource inputData) throws UnexpectedInputException {
        FlatFileItemReader<IssuerTransaction> reader = new FlatFileItemReader<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"username", "userid", "transactiondate", "amount"};
        tokenizer.setNames(tokens);
        reader.setResource(inputData);
        DefaultLineMapper<IssuerTransaction> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new ir.mahyco.p2p.domain.RecordFieldSetMapper());
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public ItemProcessor<IssuerTransaction, IssuerTransaction> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemProcessor<IssuerTransaction, IssuerTransaction> skippingItemProcessor() {
        return new SkippingItemProcessor();
    }

*/
/*    @Bean
    public ItemWriter<IssuerTransaction> itemWriter(Jaxb2Marshaller marshaller) {
        StaxEventItemWriter<IssuerTransaction> itemWriter = new StaxEventItemWriter<>();
        itemWriter.setMarshaller(marshaller);
        itemWriter.setRootTagName("transactionRecord");
        itemWriter.setResource(outputXml);
        return itemWriter;
    }*//*


*/
/*    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(IssuerTransaction.class);
        return  marshaller;
    }*//*


    @Bean
    protected Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("itemProcessor") ItemProcessor<IssuerTransaction,
                IssuerTransaction> processor) {
        return new StepBuilder("step1", jobRepository)
                .<IssuerTransaction, IssuerTransaction> chunk(10, transactionManager)
                .reader(itemReader(inputCsv))
                .processor(processor)

                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(JobRepository jobRepository, @Qualifier("step1") Step step1) {
        return new JobBuilder("firstBatchJob", jobRepository).preventRestart().start(step1).build();
    }

    @Bean
    public Step skippingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("skippingItemProcessor") ItemProcessor<IssuerTransaction,
            IssuerTransaction> processor, ItemWriter<IssuerTransaction> writer) {
        return new StepBuilder("skippingStep", jobRepository)
                .<IssuerTransaction, IssuerTransaction>chunk(10, transactionManager)
                .reader(itemReader(invalidInputCsv))
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipLimit(2)
                .skip(RuntimeException.class)
                .skip(NullPointerException.class)
                .build();
    }

    @Bean(name = "skippingBatchJob")
    public Job skippingJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("skippingStep") Step skippingStep) {
        return new JobBuilder("skippingBatchJob", jobRepository)
                .start(skippingStep)
                .preventRestart()
                .build();
    }

    @Bean
    public Step skipPolicyStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("skippingItemProcessor") ItemProcessor<IssuerTransaction, IssuerTransaction> processor,
                               ItemWriter<IssuerTransaction> writer) {
        Resource invalidInputCsv = null;
        return new StepBuilder("skipPolicyStep", jobRepository)
                .<IssuerTransaction, IssuerTransaction>chunk(10, transactionManager)
                .reader(itemReader(invalidInputCsv))
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(new CustomSkipPolicy())
                .build();
    }

    @Bean(name = "skipPolicyBatchJob")
    public Job skipPolicyBatchJob(JobRepository jobRepository, @Qualifier("skipPolicyStep") Step skipPolicyStep) {
        return new JobBuilder("skipPolicyBatchJob", jobRepository)
                .start(skipPolicyStep)
                .preventRestart()
                .build();
    }

    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean(name = "jobRepository")
    public JobRepository getJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource());
        factory.setTransactionManager(getTransactionManager());
        // JobRepositoryFactoryBean's methods Throws Generic Exception,
        // it would have been better to have a specific one
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "jobLauncher")
    public JobLauncher getJobLauncher() throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        // TaskExecutorJobLauncher's methods Throws Generic Exception,
        // it would have been better to have a specific one
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
}
*/
