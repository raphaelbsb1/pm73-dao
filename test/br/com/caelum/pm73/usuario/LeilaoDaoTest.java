package br.com.caelum.pm73.usuario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import br.com.caelum.pm73.dominio.Lance;
import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.Usuario;

public class LeilaoDaoTest extends LeilaoBuilder {
	
	@Test
	public void deveContarleiloesNaoEncerrados(){
		
		Leilao encerrado1 = new LeilaoBuilder().encerrado().constroi();
		
		usuarioDao.salvar(encerrado1.getDono());
		leilaoDao.salvar(encerrado1);
		
		long total = leilaoDao.total();
		
		assertEquals(0L, total);
	}
	
	
	@Test
	public void leilaoNovosTest(){
		
		Usuario roberto = new Usuario("roberto", "roberto@mail.com");
		
		Leilao usado = new Leilao("Geladeira", 1500.00, roberto, true);
		Leilao naoUsado = new Leilao("XBOX", 1300.00, roberto, false);	
		
		usuarioDao.salvar(roberto);
		leilaoDao.salvar(usado);
		leilaoDao.salvar(naoUsado);
		
		List<Leilao> usados = leilaoDao.novos();
		
		assertEquals(1, usados.size());
		
	}
	
	
	@Test
	public void leilaoAntigosTest(){
		
		Leilao antigo = new LeilaoBuilder().diasAtras(9).constroi();
		
		
		Leilao recente = new LeilaoBuilder().diasAtras(0).constroi();
		
		usuarioDao.salvar(antigo.getDono());
		usuarioDao.salvar(recente.getDono());
		leilaoDao.salvar(antigo);
		leilaoDao.salvar(recente);
		
		
		List<Leilao> antigos = leilaoDao.antigos();
			
		assertTrue(antigos.size() > 0);
		
	}
	
	
	@Test
	public void leilaoCom7DiasTest(){
		Usuario roberto = new Usuario("roberto", "roberto@mail.com");
		Calendar seteDias = Calendar.getInstance();
		seteDias.add(Calendar.DAY_OF_MONTH, -7);
		
		Leilao leilao = new Leilao("XBOX", 1300.00, roberto, false);
		leilao.setDataAbertura(seteDias);
		
		usuarioDao.salvar(roberto);
		leilaoDao.salvar(leilao);
		
		
		List<Leilao> antigos = leilaoDao.antigos();
			
		assertTrue(antigos.size() > 0);
		
	}	
	
	@Test
	public void leilaoPorPeriodoTest(){
		Usuario roberto = new Usuario("roberto", "roberto@mail.com");
		
		Calendar periodoInicial = Calendar.getInstance();
		periodoInicial.add(Calendar.DAY_OF_MONTH, -20);
		
		Calendar periodoAtual = Calendar.getInstance();
		
		Calendar seteDias = Calendar.getInstance();
		seteDias.add(Calendar.DAY_OF_MONTH, -7);

		Calendar vinteCincoDias = Calendar.getInstance();
		vinteCincoDias.add(Calendar.DAY_OF_MONTH, -25);
		
		Leilao leilao1 = new Leilao("XBOX", 1300.00, roberto, false);
		leilao1.setDataAbertura(seteDias);
		Leilao leilao2 = new Leilao("Geladeira", 1500.00, roberto, true);
		leilao2.setDataAbertura(vinteCincoDias);
		
		usuarioDao.salvar(roberto);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);
		
		List<Leilao> lista = leilaoDao.porPeriodo(periodoInicial, periodoAtual);
		
		assertTrue(lista.size() > 0);
	}
	
	@Test
	public void leiloesEncerradosdentroIntervaloDataTest() {
		Usuario roberto = new Usuario("roberto", "roberto@mail.com");
		
		Calendar doisDias = Calendar.getInstance();
		doisDias.add(Calendar.DAY_OF_MONTH, -2);	
		
		Calendar tresDias = Calendar.getInstance();
		tresDias.add(Calendar.DAY_OF_MONTH, -3);	
		
		Leilao leilao1 = new Leilao("XBOX", 1300.00, roberto, false);
		leilao1.setDataAbertura(doisDias);
		Leilao leilao2 = new Leilao("Geladeira", 1500.00, roberto, true);
		leilao2.setDataAbertura(tresDias);		
		
		leilao1.encerra();
		leilao2.encerra();
		
		usuarioDao.salvar(roberto);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);		
		
		Calendar periodoInicial = Calendar.getInstance();
		periodoInicial.add(Calendar.DAY_OF_MONTH, -20);	
		
		Calendar periodoFinal = Calendar.getInstance();
		
		List<Leilao> lista = leilaoDao.porPeriodo(periodoInicial, periodoFinal);
		
		assertTrue(lista.size() == 0);		
		
	}
	
	
	@Test
	public void leiloesDisputadoEntreDatas(){
		Calendar periodo= Calendar.getInstance();
		periodo.add(Calendar.DAY_OF_MONTH, -2);	
		
		Leilao leilao1 = new LeilaoBuilder().comValor(100.00).constroi();
		Lance lance1 = new Lance(periodo, leilao1.getDono(), 120.00, leilao1);
		Lance lance2 = new Lance(periodo, leilao1.getDono(), 124.00, leilao1);
		Lance lance3 = new Lance(periodo, leilao1.getDono(), 150.00, leilao1);
		Lance lance4 = new Lance(periodo, leilao1.getDono(), 180.00, leilao1);
		leilao1.adicionaLance(lance1);
		leilao1.adicionaLance(lance2);
		leilao1.adicionaLance(lance3);
		leilao1.adicionaLance(lance4);
		
		usuarioDao.salvar(leilao1.getDono());
		leilaoDao.salvar(leilao1);
		

		
		List<Leilao> lista = leilaoDao.disputadosEntre(50.00, 300.00);
		
		assertEquals(1, lista.size());
		
	}
	
	
	@Test
	public void leiloesComLanceUsuario(){
		
		Calendar periodo= Calendar.getInstance();
		periodo.add(Calendar.DAY_OF_MONTH, -2);			
		Leilao leilao1 = new LeilaoBuilder().comValor(100.00).constroi();
		Lance lance1 = new Lance(periodo, leilao1.getDono(), 120.00, leilao1);
		Lance lance2 = new Lance(periodo, leilao1.getDono(), 124.00, leilao1);
		Lance lance3 = new Lance(periodo, leilao1.getDono(), 150.00, leilao1);
		Lance lance4 = new Lance(periodo, leilao1.getDono(), 180.00, leilao1);		
		leilao1.adicionaLance(lance1);
		leilao1.adicionaLance(lance2);
		leilao1.adicionaLance(lance3);
		leilao1.adicionaLance(lance4);	
		
		usuarioDao.salvar(leilao1.getDono());
		leilaoDao.salvar(leilao1);		
		
		List<Leilao> lista = leilaoDao.listaLeiloesDoUsuario(leilao1.getDono());
		
		assertTrue(lista.size() > 1);
		
	}
	
	
	@Test
	public void mediaValorLeiloesTest(){
		
		Calendar periodo= Calendar.getInstance();
		periodo.add(Calendar.DAY_OF_MONTH, -2);			
		Leilao leilao1 = new LeilaoBuilder().comValor(100.00).constroi();
		Lance lance1 = new Lance(periodo, leilao1.getDono(), 120.00, leilao1);
		Lance lance2 = new Lance(periodo, leilao1.getDono(), 124.00, leilao1);
		Lance lance3 = new Lance(periodo, leilao1.getDono(), 150.00, leilao1);
		Lance lance4 = new Lance(periodo, leilao1.getDono(), 180.00, leilao1);		
		leilao1.adicionaLance(lance1);
		leilao1.adicionaLance(lance2);
		leilao1.adicionaLance(lance3);
		leilao1.adicionaLance(lance4);		
		
		usuarioDao.salvar(leilao1.getDono());
		leilaoDao.salvar(leilao1);
		
		Double valor = leilaoDao.getValorInicialMedioDoUsuario(leilao1.getDono());
		
		assertTrue(valor >= 100.00);
		
	}
	
	
	@Test
	public void deleteLeilaoTest(){
		
		Leilao leilao = new LeilaoBuilder().constroi();
		
		usuarioDao.salvar(leilao.getDono());
		leilaoDao.salvar(leilao);
		
		leilaoDao.deleta(leilao);
		session.flush();
		session.clear();
		
		assertNull(leilaoDao.porId(leilao.getId()));
		
	}

}
