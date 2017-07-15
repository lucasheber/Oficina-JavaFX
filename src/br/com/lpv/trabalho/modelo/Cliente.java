package br.com.lpv.trabalho.modelo;

public class Cliente {
	private String nome, cpf, endereco,
					email, telefone;
	private int id;
	
	public Cliente() {
		
	}

	public Cliente(String nome, String cpf, String endereco, String email, String telefone) {
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.email = email;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("%s, Tel: %s", nome, telefone);
	}
	
	
	
}
