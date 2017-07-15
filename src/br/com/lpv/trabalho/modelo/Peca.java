package br.com.lpv.trabalho.modelo;

public class Peca {
	private String nome, fornecedor, tipo, marca;
	private int id, quantidade;
	
	public Peca() {
		
	}

	public Peca(String nome, String fornecedor, String tipo, String marca, int quantidade) {
		this.nome = nome;
		this.fornecedor = fornecedor;
		this.tipo = tipo;
		this.marca = marca;
		this.quantidade = quantidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return String.format("%d- %s", id, nome);
		
	}

}
