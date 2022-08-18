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

import ch.qos.logback.core.util.FileUtil;
import ec.edu.espe.arquitectura.batch.dto.EstudianteDTO;
import ec.edu.espe.arquitectura.batch.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

@Slf4j
public class GenerateListTask implements Tasklet, StepExecutionListener {

    private List<EstudianteDTO> estudiantes;

    private String BASEURL = "http://localhost:8081/estudiante";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void beforeStep(StepExecution arg0) {
    }

    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
        Integer[] niveles = new Integer[] {1, 2, 3, 4,5,6,7,8,9,10};
        String[] paralelos = new String[] {"A","B","C"};
        
        /*for (Integer nivel: niveles) {
            estudiantes.clear();
            for(String paralelo:paralelos){
                HttpEntity<EstudianteDTO> estudianteDto = new HttpEntity<>(e);
                ResponseEntity<EstudianteDTO[]> response= restTemplate.getForEntity(BASEURL+"/", EstudianteDTO[].class);
                EstudianteDTO[] objectArray = response.getBody();
                estudiantes = Arrays.asList(objectArray);
                FileUtils.aniadirArchivo(estudiantes, "estudiante_paralelo_"+paralelo);
            }
        }*/
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution arg0) {
        return ExitStatus.COMPLETED;
    }

}
