package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.LibroDto;

public class ADto {
	
	// Método para convertir resultados de consulta a objetos LibroDto
	public ArrayList<LibroDto> resultsALibrosDto(ResultSet resultadoConsulta){
		ArrayList<LibroDto> listaLibros = new ArrayList<>();
		
		try {
			while (resultadoConsulta.next()) {
				// Crear un nuevo objeto LibroDto y añadirlo a la lista
				listaLibros.add(new LibroDto(resultadoConsulta.getLong("id_libro"),
						resultadoConsulta.getString("titulo"),
						resultadoConsulta.getString("autor"),
						resultadoConsulta.getString("isbn"),
						resultadoConsulta.getInt("edicion"))
						);
			}
			
			// Mostrar el número de libros en la lista
			int i = listaLibros.size();
			System.out.println("[INFORMACIÓN-ADto-resultsALibrosDto] Número libros: "+i);
			
		} catch (SQLException e) {
			// Capturar y mostrar errores de SQL
			System.out.println("[ERROR-ADto-resultsALibrosDto] Error al pasar el result set a lista de LibroDto" + e);
		}
		
		return listaLibros;
	}
}
