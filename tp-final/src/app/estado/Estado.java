package app.estado;

import app.AppEstacionamiento;

public abstract class Estado {
		
	public void driving(AppEstacionamiento app) {};
	
	public void walking(AppEstacionamiento app) {};
	
	public void registrarInicioEstacionamiento(AppEstacionamiento app) {};
	
	public void registrarFinEstacionamiento(AppEstacionamiento app) {};

}
