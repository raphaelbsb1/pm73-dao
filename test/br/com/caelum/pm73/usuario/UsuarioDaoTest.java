package br.com.caelum.pm73.usuario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.com.caelum.pm73.dominio.Usuario;

public class UsuarioDaoTest extends ConfigTest {

	@Test
	public void metodosDeTestados() {
		porNomeEEmail();
		verificaNomeEEmail();
	}

	public void porNomeEEmail() {
		// criando um usuario e salvando antes
		// de invocar o porNomeEEmail
		Usuario novoUsuario = new Usuario("João da Silva", "joao@dasilva.com.br");
		usuarioDao.salvar(novoUsuario);

		// agora buscamos no banco
		Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("João da Silva", "joao@dasilva.com.br");

		assertEquals("João da Silva", usuarioDoBanco.getNome());
		assertEquals("joao@dasilva.com.br", usuarioDoBanco.getEmail());

	}

	public void verificaNomeEEmail() {

		// criando um usuario e salvando antes
		// de invocar o porNomeEEmail
		Usuario novoUsuario = new Usuario("João da Silva", "joao@dasilva.com.br");
		usuarioDao.salvar(novoUsuario);

		// agora buscamos no banco
		Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("Jose", "jose@com.br");

		assertNull(usuarioDoBanco);

	}

	@Test
	public void deveDeletarUmUsuario() {
		Usuario usuario = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

		usuarioDao.salvar(usuario);
		usuarioDao.deletar(usuario);

		// envia tudo para o banco de dados
		session.flush();
		session.clear();

		Usuario usuarioNoBanco = usuarioDao.porNomeEEmail("Mauricio Aniche", "mauricio@aniche.com.br");

		assertNull(usuarioNoBanco);

	}
	
	
	@Test
	public void alteracaoUsuarioTest(){
		Usuario usuario = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");
		usuarioDao.salvar(usuario);
		
		session.flush();
		session.clear();
		
		
		Usuario usuarioNoBanco = usuarioDao.porNomeEEmail("Mauricio Aniche", "mauricio@aniche.com.br");
		
		assertNotNull(usuarioNoBanco);
		
		usuarioNoBanco.setNome("lea arruda");
		usuarioNoBanco.setEmail("leinha@mail.com");
		
		usuarioDao.salvar(usuarioNoBanco);
		
		session.flush();
		session.clear();
		
		Usuario usuarioAlterado = usuarioDao.porId(usuarioNoBanco.getId());
		
		assertEquals(usuarioAlterado.getNome(), "lea arruda");
		assertEquals(usuarioAlterado.getEmail(), "leinha@mail.com");
		
		
	}
}
