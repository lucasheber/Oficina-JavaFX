package br.com.lpv.trabalho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.lpv.trabalho.modelo.Cliente;

public class ClienteDAO {

    private Connection connection;
    
    public ClienteDAO() {
		
	}
    
    /**
     * Construtor
     * @param connection conexao a ser estabelecida
     */
	public ClienteDAO(Connection connection) {
		this.connection = connection;
	}


	public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Insere um cliente no banco de dados
     * @param cliente usuario a ser inserido
     * @return true se inserido, false se houve falha
     */
    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente(nome, cpf, telefone, email, endereco) VALUES(?,?,?,?,?)";
  
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getEndereco());
            
            return stmt.execute();
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Altera um cliente no banco de dados
     * @param cliente cliente a ser alterado
     * @return true se alterou, false caso contrário.
     */
    public boolean alterar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome=?, cpf=?, email=?, endereco=?, telefone=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getTelefone());
            stmt.setInt(6, cliente.getId());
            
            return stmt.execute();
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean remover(Cliente cliente) {
        String sql = "DELETE FROM cliente WHERE id=?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public List<Cliente> listar() {
        String sql = "SELECT * FROM cliente ORDER BY nome";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
            	Cliente cliente = new Cliente();
            	
                cliente.setId(resultado.getInt("id"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setEndereco(resultado.getString("endereco"));
                cliente.setTelefone(resultado.getString("telefone"));

                retorno.add(cliente);
                
       
            } // while
            
            return retorno;
        } catch (SQLException ex) {
           return null;
        }
        
    }

    /**
     * Busca um cliente no banco de dados. Recebe um ojeto cliente 
     * @param idcliente  id do cliente a ser buscado
     * @return Retorna os dados do cliente contido no banco..
     */
    public Cliente buscar(String cpf) {
        String sql = "SELECT * FROM cliente WHERE cpf=?";
        Cliente retorno = new Cliente();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
            	
                retorno.setId(resultado.getInt("id"));
                retorno.setNome(resultado.getString("nome"));
                retorno.setCpf(resultado.getString("cpf"));
                retorno.setEmail(resultado.getString("email"));
                retorno.setEndereco(resultado.getString("endereco"));
                retorno.setTelefone(resultado.getString("telefone"));
               
               return retorno;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Busca um cliente no banco de dados. Recebe um ojeto cliente 
     * @param idcliente  id do cliente a ser buscado
     * @return Retorna os dados do cliente contido no banco..
     */
    public Cliente buscar(int idcliente) {
        String sql = "SELECT * FROM cliente WHERE id=?";
        Cliente retorno = new Cliente();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idcliente);
            
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
            	retorno.setId(resultado.getInt("id"));
            	retorno.setNome(resultado.getString("nome"));
            	retorno.setCpf(resultado.getString("cpf"));
            	retorno.setEmail(resultado.getString("email"));
            	retorno.setEndereco(resultado.getString("endereco"));
            	retorno.setTelefone(resultado.getString("telefone"));
               
               return retorno;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
  
}
