package br.com.caelum.pm73.bdTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class ConexaoTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		configureUsingHibernatePropertiesFile();
	}


	public static void configureUsingHibernatePropertiesFile() throws IOException {
		// Create configuration instance
		Configuration configuration = new Configuration();

		// Create properties file
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
		// Pass hibernate properties file
		configuration.setProperties(properties);
		// Since version 4.x, service registry is being used
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).buildServiceRegistry();

		// Create session factory instance
		SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);

		// Get current session
		Session session = factory.getCurrentSession();

		// Begin transaction
		session.getTransaction().begin();

		// Print out all required information
		System.out.println("Session Is Opened :: " + session.isOpen());
		System.out.println("Session Is Connected :: " + session.isConnected());

		// Commit transaction
		session.getTransaction().commit();

		System.exit(0);
	}

}
