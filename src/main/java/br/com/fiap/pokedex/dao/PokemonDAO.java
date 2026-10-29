package br.com.fiap.pokedex.dao;

import br.com.fiap.pokedex.to.PokemonTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PokemonDAO {

    public ArrayList<PokemonTO> findAll(){ //Arraylist que vai retornar objeto de PokemonTO
        ArrayList<PokemonTO> pokemons = new ArrayList<>();
        String sql = "select * from ddd_pokemon order by codigo";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                while ( rs.next()){
                    PokemonTO pokemon = new PokemonTO();
                    pokemon.setCodigo(rs.getLong("codigo"));
                    pokemon.setNome(rs.getString("nome"));
                    pokemon.setAltura(rs.getDouble("altura"));
                    pokemon.setPeso(rs.getDouble("peso"));
                    pokemon.setCategoria(rs.getString("categoria"));
                    pokemon.setDataDaCaptura(rs.getDate("data_captura").toLocalDate());
                    pokemons.add(pokemon);
                }
            }else {
                return null;
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar: "+e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
        return pokemons;
    }

    public PokemonTO findByCodigo(Long codigo){
        PokemonTO pokemon = new PokemonTO();
        String sql = "select * from ddd_pokemon where codigo = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql))
        {
            ps.setLong(1,codigo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                pokemon.setCodigo(rs.getLong("codigo"));
                pokemon.setNome(rs.getString("nome"));
                pokemon.setAltura(rs.getDouble("altura"));
                pokemon.setPeso(rs.getDouble("peso"));
                pokemon.setCategoria(rs.getString("categoria"));
                pokemon.setDataDaCaptura(rs.getDate("data_captura").toLocalDate());
            }else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar por id: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return pokemon;
    }

    public PokemonTO save (PokemonTO pokemon) {
        String sql = "insert into ddd_pokemon (nome, altura, peso, categoria, data_captura) values(?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1,pokemon.getNome());
            ps.setDouble(2,pokemon.getAltura());
            ps.setDouble(3,pokemon.getPeso());
            ps.setString(4,pokemon.getCategoria());
            ps.setDate(5, Date.valueOf(pokemon.getDataDaCaptura()));

            if (ps.executeUpdate() > 0) {
                return pokemon;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete (Long codigo){
        String sql = "delete from ddd_pokemon where codigo =?";
        try(PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql))
        {
            ps.setLong(1 ,codigo);
            return ps.executeUpdate() > 0;

        }catch (SQLException e){
            System.out.println("Erro ao excluir: "+e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    public PokemonTO update(PokemonTO pokemon){
        String sql = "update ddd_pokemon set nome=?, altura=?, peso=?, categoria=?, data_captura=? where codigo =?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql))
        {
            ps.setString(1,pokemon.getNome());
            ps.setDouble(2,pokemon.getAltura());
            ps.setDouble(3,pokemon.getPeso());
            ps.setString(4,pokemon.getCategoria());
            ps.setDate(5, Date.valueOf(pokemon.getDataDaCaptura()));
            ps.setLong(6, pokemon.getCodigo());


            if(ps.executeUpdate()> 0){
                return pokemon;
            }else{
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }


}
