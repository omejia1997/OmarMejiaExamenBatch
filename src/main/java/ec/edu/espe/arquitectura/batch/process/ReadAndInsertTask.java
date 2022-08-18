package ec.edu.espe.arquitectura.batch.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import ec.edu.espe.arquitectura.batch.dto.EstudianteDTO;
import ec.edu.espe.arquitectura.batch.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;

@Slf4j
public class ReadAndInsertTask implements Tasklet, StepExecutionListener {

    private List<EstudianteDTO> estudiantes;

    private String BASEURL = "http://localhost:8080/api/estudiante";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void beforeStep(StepExecution arg0) {
        estudiantes = FileUtils.leerArchivo();
    }

    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
        for (EstudianteDTO e : estudiantes) {
            HttpEntity<EstudianteDTO> estudianteDto = new HttpEntity<>(e);
            this.restTemplate.postForObject(BASEURL, estudianteDto, EstudianteDTO.class);
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution arg0) {
        return ExitStatus.COMPLETED;
    }

}
