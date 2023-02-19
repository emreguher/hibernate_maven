package hibernate_maven_2.model;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

public class Program {

	public static void main(String[] args) {
		EntityManagerFactory factory = 
		Persistence.createEntityManagerFactory("persistenceUnitName");
		
		EntityManager manager = factory.createEntityManager();
		
		EntityTransaction transaction = manager.getTransaction();
		
		Personel personel = 
				new Personel("Ali", "Veli", new BigDecimal("7500.50"));
		
		transaction.begin();
			manager.persist(personel); // persist : insert
		transaction.commit();
		
		manager.close();
		
		
		
		
		double x = 0.3;
		double y = 0.2;
		
		System.out.println(x-y);
		
		BigDecimal x1 = new BigDecimal("0.345");
		BigDecimal y1 = new BigDecimal("0.267");
		
		System.out.println(x1.subtract(y1));

	}

}
