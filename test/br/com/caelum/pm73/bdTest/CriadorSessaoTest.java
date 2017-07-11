package br.com.caelum.pm73.bdTest;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.caelum.pm73.dominio.Lance;
import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.Usuario;

public class CriadorSessaoTest {
	
	private static SessionFactory sf;
	
	public Session getSession() throws IOException {
		if(sf == null) {
			sf = getConfig();
		}
		
		return sf.openSession();
	}
	
	public static SessionFactory getConfig() throws IOException {
		// Create configuration instance
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Usuario.class);
		configuration.addAnnotatedClass(Leilao.class);
		configuration.addAnnotatedClass(Lance.class);
		SchemaExport se = new SchemaExport(configuration);
		
		se.create(true, true);

		// Create properties file
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
		// Pass hibernate properties file
		configuration.setProperties(properties);
		// Since version 4.x, service registry is being used
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).buildServiceRegistry();

		// Create session factory instance
		return configuration.buildSessionFactory(serviceRegistry);

	}	

}
