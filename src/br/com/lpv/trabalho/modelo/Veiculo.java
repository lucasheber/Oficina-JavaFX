package br.com.lpv.trabalho.modelo;

import java.time.LocalDate;

public class Veiculo {
	private String marca, modelo, placa;
	private LocalDate anoFabricacao, anoModelo;
	private Cliente cliente;
	private int id;
	
	public Veiculo() {
		
	}

	public Veiculo(int id, String marca, String modelo, String placa, LocalDate anoFabricacao, LocalDate anoModelo, Cliente cliente) {
		this.marca = marca;
		this.modelo = modelo;
		this.placa = placa;
		this.anoFabricacao = anoFabricacao;
		this.anoModelo = anoModelo;
		this.cliente = cliente;
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public LocalDate getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(LocalDate anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public LocalDate getAnoModelo() {
		return anoModelo;
	}

	public void setAnoModelo(LocalDate anoModelo) {
		this.anoModelo = anoModelo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("%s", placa);
	}
	
	
}
