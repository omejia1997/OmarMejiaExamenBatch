package ec.edu.espe.arquitectura.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ec.edu.espe.arquitectura.batch.process.AsignStudentsTask;
import ec.edu.espe.arquitectura.batch.process.GenerateListTask;
import ec.edu.espe.arquitectura.batch.process.ReadAndInsertTask;


@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class JobConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    protected Step readAndInsertTask() {
        return steps
                .get("readAndInsertTask")
                .tasklet(new ReadAndInsertTask())
                .build();
    }

    @Bean
    protected Step asignStudentsTask() {
        return steps
                .get("asignStudentsTask")
                .tasklet(new AsignStudentsTask())
                .build();
    }

    @Bean
    protected Step generateListTask() {
        return steps
                .get("generateListTask")
                .tasklet(new GenerateListTask())
                .build();
    }

    @Bean
    public Job processTextFileJob() {
        return jobs
                .get("processTextFileJob")
                .start(readAndInsertTask())
                .next(generateListTask())
                .build();
    }
}
