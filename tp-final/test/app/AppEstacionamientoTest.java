package app;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.asistencia.Activada;
import app.asistencia.Desactivada;
import app.estado.Estacionado;
import app.estado.Estado;
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
	
	@Test
	void seActivaLaAsistencia() {
		app.activarAsistencia();
		assertTrue(app.getAsistencia() instanceof Activada);
	}
	
	@Test
	void seActivaYDesactivaLaAsitencia() {
		app.activarAsistencia();
		app.desactivarAsistencia();
		assertTrue(app.getAsistencia() instanceof Desactivada);
	}
	
	@Test
	void seActivaModoAutomatico() {
		app.activarModoAutomatico();
		assertTrue(app.getModo() instanceof Automatico);
	}
	
	@Test
	void seActivaModoManual() {
		app.activarModoAutomatico();
		app.activarModoManual();
		assertTrue(app.getModo() instanceof Manual);
	}
	
	@Test
	void seRegistraEstacionamientoConAutoManejandoYCambiaDeEstadoAEstacionado() {
		app.registrarInicioEstacionamiento();
		
		verify(sem).registrarEstacionamientoPorApp(app);
		assertTrue(app.getEstado() instanceof Estacionado);
	}
	
	@Test
	void seIntentaRegistrarEstacionamientoDosVecesYLaSegundaNoHaceNada() {
		app.registrarInicioEstacionamiento();
		app.registrarInicioEstacionamiento();
		
		verify(sem, times(1)).registrarEstacionamientoPorApp(app);
	}
	
	@Test
	void seIntentaRegistrarUnFinDeEstacionamientoConElAutoManejandoYElSemNoRecibeElMensaje() {
		app.registrarFinEstacionamiento();
		verify(sem, never()).registrarFinEstacionamientoPorApp(app);
	}
	
	@Test
	void seRegistraUnFinDeEstacionamientoConElAutoEstacionado() {
		app.registrarInicioEstacionamiento();
		app.registrarFinEstacionamiento();
		verify(sem, times(1)).registrarFinEstacionamientoPorApp(app);
	}

	@Test
	void testGetterNumero() {
		assertEquals(12345678, this.app.getNumero());
	}
	
	@Test
	void testGetterCredito() {
		when(this.sem.getCredito(12345678)).thenReturn(19.89f);
		assertEquals(19.89f, this.app.getCredito());
	}
	
	@Test 
    void laAppRecibeElMensajeDriving() {
        Estado manejando = mock(Estado.class);
        app.setEstado(manejando);
        app.driving();
        verify(manejando).driving(app);
    }
	
	@Test 
    void laAppRecibeElMensajeWalking() {
        Estado estacionado = mock(Estado.class);
        app.setEstado(estacionado);
        app.walking();
        verify(estacionado).walking(app);
    }
}
