package app;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.asistencia.Activada;
import app.asistencia.Desactivada;
import app.estado.Estacionado;
import app.modo.Automatico;
import app.modo.Manual;
import sem.SistemaEstacionamientoMedido;

public class AppEstacionamientoTest {
	private SistemaEstacionamientoMedido sem;
	private AppEstacionamiento app;
	
	@BeforeEach
	void setup() {
		this.sem = mock(SistemaEstacionamientoMedido.class);
		this.app = new AppEstacionamiento(12345678, "ABC123", sem);
	}
	
	@Test
	void laAppCambiaLaPatente() {
		app.setPatente("BBB222");
		assertEquals("BBB222", app.getPatente());
	}
	
	void seActivaLaAsistencia() {
		app.activarAsistencia();
		assertTrue(app.getAsistencia() instanceof Activada);
	}
	
	void seActivaYDesactivaLaAsitencia() {
		app.activarAsistencia();
		app.desactivarAsistencia();
		assertTrue(app.getAsistencia() instanceof Desactivada);
	}
	
	void seActivaModoAutomatico() {
		app.activarModoAutomatico();
		assertTrue(app.getModo() instanceof Automatico);
	}
	
	void seActivaModoManual() {
		app.activarModoAutomatico();
		app.activarModoManual();
		assertTrue(app.getModo() instanceof Manual);
	}
	
	void seRegistraEstacionamientoConAutoManejandoYCambiaDeEstadoAEstacionado() {
		app.registrarInicioEstacionamiento();
		
		verify(sem).registrarEstacionamientoPorApp(app);
		assertTrue(app.getEstado() instanceof Estacionado);
	}
	
	void seIntentaRegistrarEstacionamientoDosVecesYLaSegundaNoHaceNada() {
		app.registrarInicioEstacionamiento();
		app.registrarInicioEstacionamiento();
		
		verify(sem, times(1)).registrarEstacionamientoPorApp(app);
	}
	
	void seIntentaRegistrarUnFinDeEstacionamientoConElAutoManejandoYElSemNoRecibeElMensaje() {
		app.registrarFinEstacionamiento();
		verify(sem, never()).registrarFinEstacionamientoPorApp(app);
	}
	
	void seRegistraUnFinDeEstacionamientoConElAutoEstacionado() {
		app.registrarInicioEstacionamiento();
		app.registrarFinEstacionamiento();
		
		verify(sem, times(1)).registrarFinEstacionamientoPorApp(app);
	}

}