package com.test.spring.batch.tp1.backend.config;

import com.test.spring.batch.tp1.backend.listner.JobCompletionListener;
import com.test.spring.batch.tp1.backend.model.Personne;
import com.test.spring.batch.tp1.backend.step.Processor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;


@Configuration
class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

//    @Autowired
//    DataSource dataSource;
//
//    @Autowired
//    PlatformTransactionManager transactionManager;

    @Bean
    public FlatFileItemReader<Personne> reader() {
        return new FlatFileItemReaderBuilder<Personne>()
                .name("personItemReader")
                .resource(new ClassPathResource("personne.csv"))
                .delimited()
                .names(new String[]{"id", "nom", "prenom", "civilite"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Personne>() {{
                    setTargetType(Personne.class);
                }})
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Personne> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Personne>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO personne (id, nom, prenom, civilite ) VALUES (:id, :nom, :prenom, :civillite)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob( JobCompletionListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Personne> writer) {
        return stepBuilderFactory.get("step1")
                .<Personne, Personne> chunk(10)
                .reader(reader())
                .processor(new Processor())
                .writer(writer)
                .build();
    }

//    @Bean
//    public Job processJob() {
//
//        //
//        return jobBuilderFactory.get("processJob")
//                .start(orderStep1()).listener(listener()).build();
////        return jobBuilderFactory.get("processJob")
////                .incrementer(new RunIdIncrementer()).listener(listener())
////                .flow(orderStep1()).end().build();
//    }

//    @Bean
//    public Step orderStep1() {
//        return stepBuilderFactory.get("orderStep1").<Personne, Personne> chunk(10)
//                .reader(reader()).processor(new Processor())
//                .writer(writer(da))).build();
//    }

//    @Bean
//    public JobExecutionListener listener() {
//        return new JobCompletionListener();
//    }


//    protected JobRepository createJobRepository() throws Exception{
//        JobRepositoryFactoryBean factory= new JobRepositoryFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setTransactionManager(transactionManager);
//        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
//        factory.setTablePrefix("BATCH_");
//        factory.setMaxVarCharLength(1000);
//        return factory.getObject();
//    }

}