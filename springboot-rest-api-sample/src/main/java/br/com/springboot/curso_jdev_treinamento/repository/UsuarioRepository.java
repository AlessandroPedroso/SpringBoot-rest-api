package br.com.springboot.curso_jdev_treinamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	/*remove os espaços com trim e define também a busca por minusculo e maiusculo upper*/
	@Query(value = "select u from Usuario u where upper(trim(u.nome)) like %?1%") /*realize a busca %?1% por causa de um parametro somente*/
	List<Usuario> buscarPorNome(String name);

}
