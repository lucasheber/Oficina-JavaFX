package br.com.lpv.trabalho.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.lpv.trabalho.modelo.Cliente;
import br.com.lpv.trabalho.modelo.Usuario;
import br.com.lpv.trabalho.modelo.Veiculo;

public class VeiculoDAO {

    private Connection connection;
    
    
    public VeiculoDAO() {
		
	}
    
    /**
     * Construtor
     * @param connection conexao a ser estabelecida
     */
	public VeiculoDAO(Connection connection) {
		this.connection = connection;
	}


	public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Insere um veiculo no banco de dados
     * @param veiculo usuario a ser inserido
     * @return true se inserido, false se houve falha
     */
    public boolean inserir(Veiculo veiculo) {
        String sql = "INSERT INTO veiculo(marca, modelo, placa, ano_fabricacao, ano_modelo, id_cliente) VALUES(?,?,?,?,?,?)";
  
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getPlaca());
            stmt.setDate(4, Date.valueOf(veiculo.getAnoFabricacao()));
            stmt.setDate(5, Date.valueOf(veiculo.getAnoModelo()));
            stmt.setInt(6, veiculo.getCliente().getId());
            
            return stmt.execute();
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Altera um usuario no banco de dados
     * @param usuario usuario a ser alterado
     * @return true se alterou, false caso contrário.
     */
    public boolean alterar(Veiculo veiculo) {
        String sql = "UPDATE veiculo SET marca=?, modelo=?, placa=?, ano_fabricacao=?, ano_modelo=?, id_cliente=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getPlaca());
            stmt.setDate(4, Date.valueOf(veiculo.getAnoFabricacao()));
            stmt.setDate(5, Date.valueOf(veiculo.getAnoModelo()));
            stmt.setInt(6, veiculo.getCliente().getId());
            stmt.setInt(7, veiculo.getId());
            
            return stmt.execute();
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean remover(Usuario usuario) {
        String sql = "DELETE FROM usuario WHERE id=?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, usuario.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public List<Veiculo> listar() {
        String sql = "SELECT * FROM veiculo ORDER BY placa";
        List<Veiculo> retorno = new ArrayList<>();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
            	Veiculo veiculo = new Veiculo();
            	
                veiculo.setId(resultado.getInt("id"));
                veiculo.setMarca(resultado.getString("marca"));
                veiculo.setAnoFabricacao(resultado.getDate("ano_fabricacao").toLocalDate());
                veiculo.setAnoModelo(resultado.getDate("ano_modelo").toLocalDate());
                veiculo.setModelo(resultado.getString("modelo"));
                veiculo.setPlaca(resultado.getString("placa"));
                
                ClienteDAO c = new ClienteDAO(connection);
                Cliente cli;
                cli = c.buscar(resultado.getInt("id_cliente"));
                
                veiculo.setCliente(cli);
                retorno.add(veiculo);
            }
            
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
    public Veiculo buscar(int id) {
        String sql = "SELECT * FROM veiculo WHERE id=?";
        Veiculo retorno = new Veiculo();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
            	retorno.setId(resultado.getInt("id"));
                retorno.setMarca(resultado.getString("marca"));
                retorno.setAnoFabricacao(resultado.getDate("ano_fabricacao").toLocalDate());
                retorno.setAnoModelo(resultado.getDate("ano_modelo").toLocalDate());
                retorno.setModelo(resultado.getString("modelo"));
                retorno.setPlaca(resultado.getString("placa"));
                
                ClienteDAO c = new ClienteDAO(connection);
                Cliente cli;
                cli = c.buscar(resultado.getInt("id_cliente"));
                
                if(cli == null){
                	cli = new Cliente();
                	cli.setNome("REMOVIDO");
                }
                	
                retorno.setCliente(cli);
               
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
    public Veiculo buscar(String placa) {
        String sql = "SELECT * FROM veiculo WHERE placa=?";
        Veiculo retorno = new Veiculo();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, placa);
            
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
            	retorno.setId(resultado.getInt("id"));
                retorno.setMarca(resultado.getString("marca"));
                retorno.setAnoFabricacao(resultado.getDate("ano_fabricacao").toLocalDate());
                retorno.setAnoModelo(resultado.getDate("ano_modelo").toLocalDate());
                retorno.setModelo(resultado.getString("modelo"));
                retorno.setPlaca(resultado.getString("placa"));
                
                ClienteDAO c = new ClienteDAO(connection);
                Cliente cli;
                cli = c.buscar(resultado.getInt("id_cliente"));
                
                retorno.setCliente(cli);
               
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
    public Veiculo buscarCliente(int id) {
        String sql = "SELECT * FROM veiculo WHERE id_cliente=?";
        Veiculo retorno = new Veiculo();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
            	retorno.setId(resultado.getInt("id"));
                retorno.setMarca(resultado.getString("marca"));
                retorno.setAnoFabricacao(resultado.getDate("ano_fabricacao").toLocalDate());
                retorno.setAnoModelo(resultado.getDate("ano_modelo").toLocalDate());
                retorno.setModelo(resultado.getString("modelo"));
                retorno.setPlaca(resultado.getString("placa"));
                
                ClienteDAO c = new ClienteDAO(connection);
                Cliente cli;
                cli = c.buscar(resultado.getInt("id_cliente"));
                
                retorno.setCliente(cli);
               
               return retorno;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
  
}
