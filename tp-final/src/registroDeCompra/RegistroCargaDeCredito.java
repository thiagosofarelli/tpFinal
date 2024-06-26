package registroDeCompra;

import java.time.LocalDate;
import java.time.LocalTime;

import sem.PuntoDeVenta;

public class RegistroCargaDeCredito extends RegistroDeCompra {

	private int numero;
	private float monto;
	
	public RegistroCargaDeCredito(int nroControl, PuntoDeVenta puntoDeVenta, LocalDate fecha, LocalTime hora, int numero, float monto){
		super(nroControl, puntoDeVenta, fecha, hora);
		this.numero = numero;
		this.monto 	= monto;
	}

	public int getNumero() {
		return this.numero;
	}

	public float getMonto() {
		return this.monto;
	}
}

