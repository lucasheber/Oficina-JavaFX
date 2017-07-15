package br.com.lpv.trabalho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.lpv.trabalho.modelo.Usuario;

public class UsuarioDAO {

    private Connection connection;
    
    
    public UsuarioDAO() {
		
	}
    
    /**
     * Construtor
     * @param connection conexao a ser estabelecida
     */
	public UsuarioDAO(Connection connection) {
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
     * @param usuario usuario a ser inserido
     * @return true se inserido, false se houve falha
     */
    public boolean inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario(usuario, senha, papel) VALUES(?,?,?)";
  
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getUsuario().toLowerCase());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getPapel());
            
            return !stmt.execute();
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Altera um usuario no banco de dados
     * @param usuario usuario a ser alterado
     * @return true se alterou, false caso contrário.
     */
    public boolean alterar(Usuario usuario) {
        String sql = "UPDATE usuario SET usuario=?, senha=?, papel=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getPapel());
            stmt.setInt(4, usuario.getId());
            
            return !stmt.execute();
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

    public List<Usuario> listar() {
        String sql = "SELECT * FROM usuario ORDER BY usuario";
        List<Usuario> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
            	Usuario usuario = new Usuario();
            	
                usuario.setId(resultado.getInt("id"));
                usuario.setPapel(resultado.getString("papel"));
                usuario.setUsuario(resultado.getString("usuario").toUpperCase());
                usuario.setSenha(resultado.getString("senha"));
                retorno.add(usuario);
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
    public Usuario buscar(String usuario) {
        String sql = "SELECT * FROM usuario WHERE usuario=?";
        Usuario retorno = new Usuario();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.toLowerCase());
            
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
            	retorno.setPapel(resultado.getString("papel"));
            	retorno.setId(resultado.getInt("id"));
            	retorno.setSenha(resultado.getString("senha"));
            	retorno.setUsuario(resultado.getString("usuario"));
               
               return retorno;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
  
}
