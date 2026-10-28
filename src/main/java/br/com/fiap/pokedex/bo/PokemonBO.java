package br.com.fiap.pokedex.bo;

import br.com.fiap.pokedex.dao.PokemonDAO;
import br.com.fiap.pokedex.to.PokemonTO;

import java.time.LocalDate;
import java.util.ArrayList;

public class PokemonBO {
    private PokemonDAO pokemonDAO;


    public ArrayList<PokemonTO> findAll(){
        pokemonDAO = new PokemonDAO();
        //regra de negocin
        return pokemonDAO.findAll();
    }

    public PokemonTO findByCodigo(Long codigo){
        pokemonDAO = new PokemonDAO();
        //regra de negocin
        return pokemonDAO.findByCodigo(codigo);
    }

    public PokemonTO save(PokemonTO pokemon){
        pokemonDAO = new PokemonDAO();
        //regra de negocio: data no passado
        if (pokemon.getDataDaCaptura().isAfter(LocalDate.now())){
            return null;
        }
        return pokemonDAO.save(pokemon);
    }

    public boolean delete (Long codigo){
        pokemonDAO = new PokemonDAO();
        //regra de negocin
        return pokemonDAO.delete(codigo);
    }

    public PokemonTO update(PokemonTO pokemon){
        pokemonDAO = new PokemonDAO();
        //regra de negocio: data no passado
        if (pokemon.getDataDaCaptura().isAfter(LocalDate.now())){
            return null;
        }
        return pokemonDAO.update(pokemon);
    }
}
