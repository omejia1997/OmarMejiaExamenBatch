package ec.edu.espe.arquitectura.batch.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
//@Builder
public class EstudianteDTO {
    
    private String cedula;

    private String nombres;

    private String apellidos;

    private Integer nivel;
}
