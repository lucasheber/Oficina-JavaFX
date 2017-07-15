
package br.com.lpv.trabalho.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.lpv.trabalho.modelo.OrdemServico;
import br.com.lpv.trabalho.modelo.Peca;
import br.com.lpv.trabalho.modelo.Veiculo;

public class OrdemServicoDAO {

    private Connection connection;
    
    
    public OrdemServicoDAO() {}
    
    /**
     * Construtor
     * @param connection conexao a ser estabelecida
     */
	public OrdemServicoDAO(Connection connection) {
		this.connection = connection;
	}


	public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Insere uma ordem no banco de dados
     * @param ordem ordem de serviço a ser inserido
     * @return true se inserido, false se houve falha
     */
    public boolean inserir(OrdemServico ordem) {
        String sql = "INSERT INTO ordem_servico (id_veiculo, data_orcamento, data_servico, servico, valor, status, quantidade, id_peca) VALUES(?,?,?,?,?,?,?,?)";
  
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ordem.getVeiculo().getId());
            stmt.setDate(2, Date.valueOf(ordem.getDataOrcamento()));
            stmt.setDate(3, Date.valueOf(ordem.getDataServico()));
            stmt.setString(4, ordem.getServico());
            stmt.setFloat(5, ordem.getValor());
            stmt.setString(6, ordem.getStatus());
            stmt.setInt(7, ordem.getQuantidade());
            stmt.setInt(8, ordem.getPeca().getId());
            
            return !stmt.execute();
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return false;
        }
    }

    /**
     * Altera uma ordem de serviço no banco de dados
     * @param ordem ordem a ser alterado
     * @return true se alterou, false caso contrário.
     */
    public boolean alterar(OrdemServico ordem) {
        String sql = "UPDATE ordem_servico SET id_veiculo=?, data_orcamento=?, data_servico=?, servico=?, valor=?, status=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ordem.getVeiculo().getId());
            stmt.setDate(2, Date.valueOf(ordem.getDataOrcamento()));
            stmt.setDate(3, Date.valueOf(ordem.getDataServico()));
            stmt.setString(4, ordem.getServico());
            stmt.setFloat(5, ordem.getValor());
            stmt.setString(6, ordem.getStatus());
            stmt.setInt(7, ordem.getId());
            
            return !stmt.execute();
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return false;
        }
    }

    public boolean remover(OrdemServico ordem) {
        String sql = "DELETE FROM ordem_servico WHERE id=?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ordem.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public List<OrdemServico> listar() {
        String sql = "SELECT * FROM ordem_servico ORDER BY id";
        List<OrdemServico> retorno = new ArrayList<>();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
            	OrdemServico ordem = new OrdemServico();
            	
                ordem.setId(resultado.getInt("id"));
                ordem.setServico(resultado.getString("servico"));
                ordem.setDataOrcamento(resultado.getDate("data_orcamento").toLocalDate());
                ordem.setDataServico(resultado.getDate("data_servico").toLocalDate());
                ordem.setValor(resultado.getFloat("valor"));
                ordem.setStatus(resultado.getString("status"));
                ordem.setQuantidade(resultado.getInt("quantidade"));
                
                VeiculoDAO v = new VeiculoDAO(connection);
                PecaDAO p = new PecaDAO(connection);
               
                Peca peca;
                Veiculo veiculo;
                
                peca = p.buscar(resultado.getInt("id_peca"));
                veiculo = v.buscar(resultado.getInt("id_veiculo"));
                
                if(peca == null){
                	peca = new Peca();
                	peca.setNome("REMOVIDA");
                }
                
                if(veiculo == null){
                	veiculo = new Veiculo();
                	veiculo.setPlaca("REMOVIDA");
                }
                
                ordem.setVeiculo(veiculo);
                ordem.setPeca(peca);
                retorno.add(ordem);
            }
            
            return retorno;
        } catch (SQLException ex) {
        	return null;
        }
        
    }
    
    public List<OrdemServico> listar(int id_veiculo) {
        String sql = "SELECT * FROM ordem_servico WHERE id_veiculo=? ORDER BY id";
        List<OrdemServico> retorno = new ArrayList<>();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_veiculo);
            
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
            	OrdemServico ordem = new OrdemServico();
            	
                ordem.setId(resultado.getInt("id"));
                ordem.setServico(resultado.getString("servico"));
                ordem.setDataOrcamento(resultado.getDate("data_orcamento").toLocalDate());
                ordem.setDataServico(resultado.getDate("data_servico").toLocalDate());
                ordem.setValor(resultado.getFloat("valor"));
                ordem.setStatus(resultado.getString("status"));
                ordem.setQuantidade(resultado.getInt("quantidade"));
                

                VeiculoDAO v = new VeiculoDAO(connection);
                PecaDAO p = new PecaDAO(connection);
                
                Peca peca;
                Veiculo veiculo;
                
                peca = p.buscar(resultado.getInt("id_peca"));
                veiculo = v.buscar(resultado.getInt("id_veiculo"));
                
                if(peca == null){
                	peca = new Peca();
                	peca.setNome("REMOVIDA");
                }
                
                if(veiculo == null){
                	veiculo = new Veiculo();
                	veiculo.setPlaca("REMOVIDA");
                }
                
                ordem.setVeiculo(veiculo);
                ordem.setPeca(peca);
                retorno.add(ordem);
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
    public OrdemServico buscar(int id) {
        String sql = "SELECT * FROM ordem_servico WHERE id=?";
        OrdemServico ordem = new OrdemServico();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
            	ordem.setId(resultado.getInt("id"));
                ordem.setServico(resultado.getString("servico"));
                ordem.setDataOrcamento(resultado.getDate("data_orcamento").toLocalDate());
                ordem.setDataServico(resultado.getDate("data_servico").toLocalDate());
                ordem.setValor(resultado.getFloat("valor"));
                ordem.setStatus(resultado.getString("status"));
                ordem.setQuantidade(resultado.getInt("quantidade"));
                

                VeiculoDAO v = new VeiculoDAO(connection);
                PecaDAO p = new PecaDAO(connection);
                
                Peca peca;
                Veiculo veiculo;
                
                peca = p.buscar(resultado.getInt("id_peca"));
                veiculo = v.buscar(resultado.getInt("id_veiculo"));
                
                if(peca == null){
                	peca = new Peca();
                	peca.setNome("REMOVIDA");
                }
                
                if(veiculo == null){
                	veiculo = new Veiculo();
                	veiculo.setPlaca("REMOVIDA");
                }
                
                ordem.setVeiculo(veiculo);
                ordem.setPeca(peca);
              
               return ordem;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
  
}
