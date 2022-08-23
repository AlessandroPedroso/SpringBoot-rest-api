package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @DeleteMapping(value = "delete") /*mapeia a URL*/
    @ResponseBody /*descrição da reposta*/
    public ResponseEntity<String> delete (@RequestParam Long iduser){ /*recebe os dados para salvar*/
    	
    	 usuarioRepository.deleteById(iduser);
    	 	 
    	return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
    	
    }
    
} 
