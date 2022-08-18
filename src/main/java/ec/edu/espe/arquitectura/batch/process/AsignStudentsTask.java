package ec.edu.espe.arquitectura.batch.process;

import java.util.Arrays;
import java.util.List;



import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.web.client.RestTemplate;

import ec.edu.espe.arquitectura.batch.dto.EstudianteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public class AsignStudentsTask implements Tasklet, StepExecutionListener {

    private List<EstudianteDTO> estudiantes;

    private String BASEURL = "http://localhost:8081/estudiante";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void beforeStep(StepExecution arg0) {
        ResponseEntity<EstudianteDTO[]> response= restTemplate.getForEntity(BASEURL+"/", EstudianteDTO[].class);
        EstudianteDTO[] objectArray = response.getBody();
        estudiantes = Arrays.asList(objectArray);
    }

    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
        for (EstudianteDTO e : estudiantes) {
            HttpEntity<EstudianteDTO> estudianteDto = new HttpEntity<>(e);
            this.restTemplate.postForObject(BASEURL+"", estudianteDto, EstudianteDTO.class);
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution arg0) {
        return ExitStatus.COMPLETED;
    }

}
