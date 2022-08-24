package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

@RestController
public class GreetingsController {
	
	@Autowired /*IC/CD ou CDI - Injeção de dependencia*/
	private UsuarioRepository usuarioRepository;

    @RequestMapping(value = "mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot API: " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);/*grava no banco de dados*/
    	
    	return "Ola mundo " + nome;
    }
    
    
    /************** JSON *******************/
    
    @GetMapping(value = "listatodos") /*nosso primeiro metodo de API*/
    @ResponseBody/*Retorna os dados para o corpo da resposta*/
   public ResponseEntity<List<Usuario>> listaUsuario(){
	   
    	List<Usuario> listaUsuario = usuarioRepository.findAll(); /*Executa a consulta no banco de dados*/
    	
    	return new ResponseEntity<List<Usuario>>(listaUsuario, HttpStatus.OK); /*Retorna a lista em JSON*/
   }
    
    @PostMapping(value = "salvar") /*mapeia a URL*/
    @ResponseBody /*descrição da reposta*/
    public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario){ /*recebe os dados para salvar*/
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    	
    }
    
    @PutMapping(value = "atualizar") /*mapeia a URL*/
    @ResponseBody /*descrição da reposta*/
    public ResponseEntity<?> atualizar (@RequestBody Usuario usuario){ /*recebe os dados para salvar*/
    	
    		
    	if (usuario.getId() == null) {
    		
    		return new ResponseEntity<String>("Id não foi informado para atualização", HttpStatus.OK);
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    	
    }
    
    @DeleteMapping(value = "delete") /*mapeia a URL*/
    @ResponseBody /*descrição da reposta*/
    public ResponseEntity<String> delete (@RequestParam Long iduser){ /*recebe os dados para salvar*/
    	
    	 usuarioRepository.deleteById(iduser);
    	 	 
    	return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
    	
    }
    
    @GetMapping(value = "buscaruserid") /*mapeia a URL*/
    @ResponseBody /*descrição da reposta*/
    public ResponseEntity<Usuario> buscaruserid (@RequestParam(name = "iduser") Long iduser){ /*recebe os dados para salvar*/
    	
    	Usuario usuario = usuarioRepository.findById(iduser).get();
    	 	 
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    	
    }
    
    @GetMapping(value = "buscarPorNome") /*mapeia a URL*/
    @ResponseBody /*descrição da reposta*/
    public ResponseEntity<?> buscarPorNome (@RequestParam(name = "name") String name){ /*recebe os dados para salvar*/
    	
    	List<Usuario> listUsuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());/*remove os espaços com trim e define também a busca por minusculo e maiusculo*/
    	
    	Iterator<Usuario> iteratorListUsuario = listUsuario.iterator();
    	
    	if(iteratorListUsuario.hasNext() == false) { /*Se não encontrar o resultado informa que não existe a busca*/
    		
    		return new ResponseEntity<String>("Objeto não encontrado!", HttpStatus.FOUND);
    		
    	}else {
    		return new ResponseEntity<List<Usuario>>(listUsuario, HttpStatus.OK);
    	}
    	 	 
    	
    }
    
    
} 
