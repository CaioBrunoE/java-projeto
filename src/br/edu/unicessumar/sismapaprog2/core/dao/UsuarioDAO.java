package br.edu.unicessumar.sismapaprog2.core.dao;

import br.edu.unicessumar.sismapaprog2.core.dao.entity.Usuario;
import java.sql.PreparedStatement;
import br.edu.unicessumar.sismapaprog2.core.dao.conexao.ConexaoJDBC;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    public void inserir(Usuario usuario) {

        String sql = "insert into usuario (nome, login, senha, email) values (?,?,?,?)";

        PreparedStatement ps = null ;

        try {
            ps = ConexaoJDBC.getConnection().prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getLogin());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getEmail());
            ps.execute();

            JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuario");
        }finally{
            try {
                ps.close();
            } catch (SQLException e) {
              System.err.print("Erro ao fechar conexão");
           }
        }

    }

    public Usuario buscarUsuario(String login, String senha) {
        String sql = "SELECT id, nome, login, senha, email FROM usuario WHERE login = ? AND senha = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = ConexaoJDBC.getConnection().prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, senha);

            rs = ps.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setEmail(rs.getString("email"));
                return usuario;

            }

        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.print("Erro ao fechar conexão");
            }
        }
        return null;
    }
}
