/**
 * 
 */
package br.com.caelum.pm73.usuario;

import java.io.IOException;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.fail;

import br.com.caelum.pm73.bdTest.CriadorSessaoTest;
import br.com.caelum.pm73.dao.LeilaoDao;
import br.com.caelum.pm73.dao.UsuarioDao;

/**
 * @author raphael.santos
 *
 */
public class ConfigTest {
	
	protected Session session;
	protected UsuarioDao usuarioDao;
	protected LeilaoDao leilaoDao;

	@Before
	public void antes() {
		try {
			session = new CriadorSessaoTest().getSession();
		} catch (IOException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		usuarioDao = new UsuarioDao(session);
		leilaoDao = new LeilaoDao(session);

		//Inicia transacao com o BD
		session.beginTransaction();
	}
	
	@After
	public void depois() {
		//Fecha transacao com o BD retornando os dados
		session.getTransaction().rollback();
		session.close();
		System.out.print("Fim do Teste");
	}
	

}
