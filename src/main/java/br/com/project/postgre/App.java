package br.com.project.postgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App 
{
    private static final String URL = "jdbc:postgresql://localhost:5432/gerenciador_projetos";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgre";

    public Connection conectar(){
        Connection conec = null;

        try {
            conec = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado com sucesso!");
        } catch (SQLException e){
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
        return conec;
    }
    
    public void adicionarProduto(String nome, double preco){
        String sql = "INSERT INTO produto (nome, preco) VALUES (?, ?)";
        try(Connection conec = conectar();
            PreparedStatement stmt = conec.prepareStatement(sql)){
                stmt.setString(1, nome);
                stmt.setDouble(2, preco);
                stmt.executeUpdate();
                System.out.println("Produto adicionado com sucesso!");
            } catch (SQLException e){
                System.out.println("Erro ao adicionar produto: " + e.getMessage());
            }
    }

    public void listarProduto(){
        String sql = "SELECT * FROM produto";
        try (Connection conec = conectar();
            PreparedStatement stmt = conec.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
                while (rs.next()){
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    double preco = rs.getDouble("preco");
                    System.out.printf("ID: %d | Nome: %s | Preço: %.2f%n", id, nome, preco);
                }
            } catch (SQLException e){
                System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
    }

    public void atualizarProduto(int id, String nome, double preco){
        String sql = "UPDATE produto SET nome = ?, preco = ? WHERE id = ?";
        try (Connection conec = conectar();
            PreparedStatement stmt = conec.prepareStatement(sql)) {
                stmt.setString(1, nome);
                stmt.setDouble(2, preco);
                stmt.setInt(3, id);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0){
                    System.out.println("Produto atualizado com sucesso!");
                } else {
                    System.out.println("Produto não localizado.");
                }
            } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
    }

    public void deletarProduto(int id){
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conec = conectar();
            PreparedStatement stmt = conec.prepareStatement(sql)){
                stmt.setInt(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Produto deletado com sucesso!");
                } else {
                    System.out.println("Produto não encontrado.");
                }
            } catch (SQLException e){
                System.out.println("Erro ao deletar produto: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        App dao = new App();

        dao.adicionarProduto("Macarrão", 12.75);

        System.out.println("Lista de Produtos:");
        dao.listarProduto();
        
        dao.atualizarProduto(4, "Macarrão Integral", 9.00);

        //dao.deletarProduto(1);
        dao.listarProduto();

    }
}
