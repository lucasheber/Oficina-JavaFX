package br.com.lpv.trabalho.modelo;

public class Usuario {
	private int id;
	private String usuario, senha, papel;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getPapel() {
		return papel;
	}
	
	public void setPapel(String papel) {
		this.papel = papel;
	}

	@Override
	public String toString() {
		return String.format("%s - %s", usuario, papel);
	}
	
	
}
