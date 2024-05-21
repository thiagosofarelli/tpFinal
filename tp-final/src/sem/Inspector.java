package sem;

import app.App;
import app.AppInspector;

public class Inspector {

	private SistemaEstacionamientoMedido sem;
	private Zona zona;
	private AppInspector app;
	
	public Inspector(SistemaEstacionamientoMedido sem, Zona zona, AppInspector app) {
		this.sem = sem;
		this.zona = zona;
		this.app = app;
	}
	
	public boolean poseeEstacionamientoVigente(String patente) {
		return this.app.poseeEstacionamientoVigente(patente);
	}
	
	public void generarInfraccion(String patente) {
	    if (!poseeEstacionamientoVigente(patente)) {
	        app.generarInfraccion(patente, this);
	    }
	}
	
	public Zona getZona() {
		return this.zona;
	}

	
	
}