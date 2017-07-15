package br.com.lpv.trabalho.dao;

import br.com.lpv.trabalho.modelo.Path;

/**
 * Define a classe para a conexão com o tipo do banco de dados.
 */
public class DatabaseFactory {
	
	/**
	 * Verifica o tipo do banco de dados a ser conecatado.
	 * @param nomeBancoDeDados
	 * @return Retorna o tipo de banco de dados a ser conectado.
	 */
    public static Database getDatabase(String nomeBancoDeDados){
        if(nomeBancoDeDados.equals(Path.POSTGRES))
            return new DatabasePostgreSQL();
		return null;
      
    }
}
