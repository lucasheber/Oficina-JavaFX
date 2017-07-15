package br.com.lpv.trabalho.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.lpv.trabalho.modelo.Path;

/**
 * Classe responsável por conectar com o banco de dados.
 * @author lucas
 *
 */
public class DatabasePostgreSQL implements Database {
    private Connection connection;

    @Override
    public Connection conectar() {
        try {
           
        	Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(Path.PATH_DATABASE, Path.USER_DATABASE, Path.PASSSWORD_DATABASE);
            return this.connection;
        } catch (SQLException | ClassNotFoundException e) {
        	
            return null;
        }
    }

    @Override
    public void desconectar(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
        	
        }
    }
}
