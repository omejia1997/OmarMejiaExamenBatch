package ec.edu.espe.arquitectura.batch.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ec.edu.espe.arquitectura.batch.dto.EstudianteDTO;

public class FileUtils {

	public static ArrayList<EstudianteDTO> leerArchivo() {
		File file = new File("estudiante.txt");
		ArrayList<EstudianteDTO> estudiantes= new ArrayList<EstudianteDTO>();	
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String linea = scanner.nextLine();
				Scanner delimitar = new Scanner(linea);
				delimitar.useDelimiter("\\s*,\\s*");
				EstudianteDTO e= new EstudianteDTO();
				e.setCedula(delimitar.next());
				e.setApellidos(delimitar.next());
				e.setNombres(delimitar.next());
				e.setNivel(Integer.parseInt(delimitar.next()));
				estudiantes.add(e);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return estudiantes;
	}
	
	public static void aniadirArchivo(List<EstudianteDTO> lista,String nameFile) {
		FileWriter flwriter = null;
		try { 
			flwriter = new FileWriter(nameFile, true);
			BufferedWriter bfwriter = new BufferedWriter(flwriter);
			for (EstudianteDTO e : lista) {
				bfwriter.write(e.getCedula() + "," + e.getApellidos() + "," +e.getNombres()+"\n");
			}
			bfwriter.close();
			System.out.println("Archivo modificado satisfactoriamente..");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (flwriter != null) {
				try {
					flwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
