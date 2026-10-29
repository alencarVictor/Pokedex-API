package br.com.fiap.pokedex.resource;

import br.com.fiap.pokedex.bo.PokemonBO;
import br.com.fiap.pokedex.to.PokemonTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokemons")
public class PokemonResource {
    private PokemonBO pokemonBO = new PokemonBO();

    @GetMapping //requisicao get
    public ResponseEntity<List<PokemonTO>> findAll(){
        List<PokemonTO> pokemons = pokemonBO.findAll();
        if(pokemons != null){
            return ResponseEntity.status(HttpStatus.OK).body(pokemons);
            //configurando o retorno com status ok 200
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pokemons);
            //configurando o retorno com status not found 404
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?>findByCodigo(@PathVariable Long codigo){
        PokemonTO pokemon = pokemonBO.findByCodigo(codigo);
        if(pokemon != null){
            return ResponseEntity.status(HttpStatus.OK).body(pokemon);
            //configurando o retorno com status ok 200
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pokemon);
            //configurando o retorno com status not found 404
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PokemonTO pokemon){
        PokemonTO resultado = pokemonBO.save(pokemon);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar pokemon");
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> delete(@PathVariable Long codigo){
        if(pokemonBO.delete(codigo)){
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pokemon deletado com sucesso!");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon não encontrado!");
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update (@PathVariable  Long codigo,
                                     @RequestBody @Valid PokemonTO pokemon){
        // 404: o código da URI não existe no banco
        if (pokemonBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon não encontrado!");
        }
        pokemon.setCodigo(codigo);
        PokemonTO resultado = pokemonBO.update(pokemon);
        // 400: o pokémon existe, mas o BO recusou (data no futuro)
        if (resultado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o pokemon");
        }
        // 200: atualização não é criação, então não é 201
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }
}
