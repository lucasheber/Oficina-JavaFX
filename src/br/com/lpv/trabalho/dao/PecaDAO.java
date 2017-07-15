package br.com.lpv.trabalho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.lpv.trabalho.modelo.Peca;

public class PecaDAO {

    private Connection connection;
        
    public PecaDAO() {}
    
    /**
     * Construtor
     * @param connection conexao a ser estabelecida
     */
	public PecaDAO(Connection connection) {
		this.connection = connection;
	}


	public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Insere um usuario no banco de dados
     * @param peca usuario a ser inserido
     * @return true se inserido, false se houve falha
     */
    public boolean inserir(Peca peca) {
        String sql = "INSERT INTO peca(nome, fornecedor, tipo, marca, quantidade) VALUES(?,?,?,?,?)";
  
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, peca.getNome());
            stmt.setString(2, peca.getFornecedor());
            stmt.setString(3, peca.getTipo());
            stmt.setString(4, peca.getMarca());
            stmt.setInt(5, peca.getQuantidade());
            
           return !stmt.execute();

      
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return false;
        }
    }

    /**
     * Altera uma peca no banco de dados
     * @param peca peca a ser alterada
     * @return true se alterou, false caso contrário.
     */
    public boolean alterar(Peca peca) {
        String sql = "UPDATE peca SET nome=?, fornecedor=?, tipo=?, marca=?, quantidade=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, peca.getNome());
            stmt.setString(2, peca.getFornecedor());
            stmt.setString(3, peca.getTipo());
            stmt.setString(4, peca.getMarca());
            stmt.setInt(5, peca.getQuantidade());
            stmt.setInt(6, peca.getId());
            
            
            return !stmt.execute();
            
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return false;
        }
    }

    public boolean remover(Peca peca) {
        String sql = "DELETE FROM peca WHERE id=?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, peca.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public List<Peca> listar() {
        String sql = "SELECT * FROM peca ORDER BY id";
        List<Peca> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
            	Peca peca = new Peca();
            	
                peca.setId(resultado.getInt("id"));
                peca.setFornecedor(resultado.getString("fornecedor"));
                peca.setMarca(resultado.getString("marca"));
                peca.setNome(resultado.getString("nome"));
                peca.setQuantidade(resultado.getInt("quantidade"));
                peca.setTipo(resultado.getString("tipo"));
                
                
                retorno.add(peca);
            }
        } catch (SQLException ex) {
           
        }
        return retorno;
    }

    /**
     * Busca um cliente no banco de dados. Recebe um ojeto cliente 
     * @param idcliente  id do cliente a ser buscado
     * @return Retorna os dados do cliente contido no banco..
     */
    public Peca buscar(int id) {
        String sql = "SELECT * FROM peca WHERE id=?";
        Peca peca = new Peca();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
            	peca.setId(resultado.getInt("id"));
                peca.setFornecedor(resultado.getString("fornecedor"));
                peca.setMarca(resultado.getString("marca"));
                peca.setNome(resultado.getString("nome"));
                peca.setQuantidade(resultado.getInt("quantidade"));
                peca.setTipo(resultado.getString("tipo"));
               
               return peca;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
  
}
