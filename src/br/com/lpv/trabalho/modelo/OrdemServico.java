package br.com.lpv.trabalho.modelo;

import java.time.LocalDate;

public class OrdemServico {
	private String servico, status;
	private float valor;
	private int id, quantidade;
	private Veiculo veiculo;
	private LocalDate dataOrcamento, dataServico;
	private Peca peca;
	
	public OrdemServico() {
		
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumeroOS(){
		return String.format("%05d", id);
	}
	
	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public LocalDate getDataOrcamento() {
		return dataOrcamento;
	}

	public String getDO(){
		return Path.Dataformatada(dataOrcamento);
	}
	
	public void setDataOrcamento(LocalDate data_orcamento) {
		this.dataOrcamento = data_orcamento;
	}

	public LocalDate getDataServico() {
		return dataServico;
	}

	public void setDataServico(LocalDate data_servico) {
		this.dataServico = data_servico;
	}

	@Override
	public String toString() {
		return String.format("%s - %s", servico, status);
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}

}// class
