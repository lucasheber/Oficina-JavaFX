package br.com.lpv.trabalho.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface para acesso ao banco de dados.
 * @author lucas
 *
 */
public interface Database {
    
	/**
	 * Conecta com o banco de dados.
	 * @return retorna a conexao
	 */
    public Connection conectar();
    
    /**
     * Desliga a conexao com o banco de dados.
     * @param conn - conexao a ser desligada
     * @throws SQLException - dispara a exceção se houver falha.
     */
    public void desconectar(Connection conn)throws SQLException;
}
