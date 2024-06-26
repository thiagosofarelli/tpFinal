package registroDeInfraccion;

import java.time.LocalDate;
import java.time.LocalTime;

import sem.Inspector;
import sem.Zona;

public class RegistroDeInfraccion {

	private LocalDate fecha;
	private LocalTime hora;
	private Zona 	  zona;
	private Inspector inspector;
	private String 	  patente;
	
	public RegistroDeInfraccion(LocalDate fecha, LocalTime hora, Zona zona, Inspector inspector, String patente) {
		this.fecha     = fecha;
		this.hora 	   = hora;
		this.zona      = zona;
		this.inspector = inspector;
		this.patente   = patente;
	}

	public LocalDate getFecha() {
		return this.fecha;
	}

	public LocalTime getHora() {
		return this.hora;
	}

	public Zona getZona() {
		return this.zona;
	}

	public Inspector getInspector() {
		return this.inspector;
	}

	public String getPatente() {
		return this.patente;
	}
}
