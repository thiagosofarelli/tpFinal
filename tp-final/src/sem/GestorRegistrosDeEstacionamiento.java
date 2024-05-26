package sem;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import app.AppEstacionamiento;
import registroDeCompra.RegistroDeCompra;
import registroDeCompra.RegistroPorCompraPuntual;
import registroDeEstacionamiento.RegistroDeEstacionamiento;
import registroDeEstacionamiento.RegistroDeEstacionamientoPorApp;
import registroDeEstacionamiento.RegistroEstacionamientoPuntual;

public class GestorRegistrosDeEstacionamiento {
	private Map<String, RegistroDeEstacionamiento> registrosDeEstacionamiento= new HashMap<>();
	private Map<Integer, String> registroDePatentePorCelular = new HashMap<>();
	private SistemaEstacionamientoMedido sem;
	
	public GestorRegistrosDeEstacionamiento(SistemaEstacionamientoMedido sem) {
		this.sem = sem;
	}
	
	public void registrarEstacionamientoPuntual(String patente, LocalTime horaActual, int cantidadHoras, RegistroDeCompra registroCompra) {
		LocalTime horaFin = horaActual.plusHours(cantidadHoras);
		RegistroEstacionamientoPuntual registro = new RegistroEstacionamientoPuntual(patente, horaActual, horaFin, registroCompra);
		this.registrosDeEstacionamiento.put(patente, registro);
	}

	public void registrarEstacionamientoPorApp(int numero, String patente, AppEstacionamiento app ) {
		LocalTime horaActual = LocalTime.now();
		Float credito = this.sem.getCredito(numero);
		if (credito != null) {
			int cantHorasMax = (int) (credito / this.sem.getPrecioPorHora());
			LocalTime horaMaxPorCredito = horaActual.plusHours(cantHorasMax);
			LocalTime horaMax = horaMaxPorCredito.isBefore(this.sem.getHoraFin()) ? horaMaxPorCredito : this.sem.getHoraFin();
			RegistroDeEstacionamientoPorApp registro = new RegistroDeEstacionamientoPorApp(patente, horaActual, null, numero, horaMax);
			this.registrosDeEstacionamiento.put(patente, registro);
			this.registroDePatentePorCelular.put(numero, patente);
			app.recibirNotificacion("Se ha registrado un inicio de estacionamiento a las " + horaActual.toString() + ". La hora máxima  de fin de su estacionamiento es "
			+ horaMax.toString());
		} else {
			app.recibirNotificacion("No tiene saldo suficiente para la compra");
		}
	}
	
	public void registrarFinDeEstacionamientoPorApp(int numero, AppEstacionamiento app) {
		String patente = this.registroDePatentePorCelular.get(numero);
		
		if (patente != null) {
			LocalTime horaActual = LocalTime.now();
			RegistroDeEstacionamiento registro = this.registrosDeEstacionamiento.get(patente);
			registro.setHoraDeFin(horaActual);
			long duracion = Duration.between(registro.getHoraDeInicio(), horaActual).toHours();
			float costo = duracion * this.sem.getPrecioPorHora();
			this.sem.debitarCredito(costo, numero);
			this.registroDePatentePorCelular.remove(numero);
			app.recibirNotificacion("Se ha registrado un fin de estacionamiento a las " + horaActual.toString() + " horas. El mismo fue iniciado a las " +
			registro.getHoraDeInicio() + " y tuvo una duración de " + duracion + ". El costo fue de " + costo);
		}
	}
	
	public boolean poseeEstacionamientoVigente(String patente) {
		return this.registrosDeEstacionamiento.containsKey(patente) && this.registrosDeEstacionamiento.get(patente).estaVigente();
	}
}