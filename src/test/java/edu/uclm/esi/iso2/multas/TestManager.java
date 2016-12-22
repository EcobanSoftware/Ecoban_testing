package edu.uclm.esi.iso2.multas;

import static org.junit.Assert.*;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import edu.uclm.esi.iso2.multas.dao.DriverDao;
import edu.uclm.esi.iso2.multas.domain.Driver;
import edu.uclm.esi.iso2.multas.domain.Inquiry;
import edu.uclm.esi.iso2.multas.domain.Manager;
import edu.uclm.esi.iso2.multas.domain.Sanction;

public class TestManager {
	
	//@Before
    public void setUp() throws IOException {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();

        Transaction t=session.beginTransaction();
        Query<Inquiry> query=session.createQuery("delete from Inquiry");
        query.executeUpdate();
        query=session.createQuery("delete from Sanction");
        query.executeUpdate();
        Query query2=session.createQuery("update Driver set points=12");
        query2.executeUpdate();
        t.commit();
    }
	
	double [] limits = {30,40,50,60,70,80,90,100,110,120};
    int [] multas = {100,300,400,500,600};
    int [] puntos = {0,2,4,6,6};
    double [][] speeds = {{20,31,40,50,51,55,60,61,65,70,71,75,80,81,90}, // Speeds for limit 30
                        {30,41,50,60,61,65,70,71,75,80,81,85,90,91,100}, // Speeds for limit 40
                        {40,51,60,70,71,75,80,81,85,90,91,95,100,101,110}, // Speeds for limit 50
                        {50,61,70,90,91,100,110,111,115,120,121,125,130,131,140},  // Speeds for limit 60
                        {60,71,80,100,101,110,120,121,125,130,131,135,140,141,150},  // Speeds for limit 70
                        {70,81,90,110,111,120,130,131,135,140,141,145,150,151,160},  // Speeds for limit 80
                        {80,91,100,120,121,130,140,141,145,150,151,155,160,161,170},  // Speeds for limit 90
                        {90,101,120,130,131,140,150,151,155,160,161,165,170,171,180},  // Speeds for limit 100
                        {100,111,120,140,141,150,160,161,165,170,171,175,180,181,190},  // Speeds for limit 110
                        {110,121,130,150,151,160,170,171,175,180,181,185,190,191,200},  // Speeds for limit 120
                        };
    
    
    // Test Case for legal speeds
    @Test
	public void test_legal_speeds(){
    	for(int limit_index = 0; limit_index < 10; limit_index++){
    		// Pass at a legal speed
        	try{
    	        DriverDao dDao = new DriverDao();
    	        Driver driver = dDao.findByDni("5000009");
    	        int pointsPre = driver.getPoints();
    	        Manager m = Manager.get();
    	        int idInquiry = m.openInquiry("0034", speeds[limit_index][0], "Plaza del Pilar", limits[limit_index]);
    	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
    	        assertTrue(sanction.getAmount() == 0);
    	        assertTrue(sanction.getPoints() == 0);
    	        assertNull(sanction.getDateOfPayment());
    	        m.pay(sanction.getId());
    	        driver = dDao.findByDni("5000009");
    	        assertTrue(driver.getPoints() == pointsPre);
    	    }catch(Exception e){
    	        fail("UnexpectedException" + e);
    	    }
    	}
    }
    
    
 // Test Case for "grave" ilegal speeds
    @Test
	public void test_ilegal_speeds_grave(){
    	for(int limit_index = 0; limit_index < 10; limit_index++){
    		for(int multas_index = 0; multas_index < 4; multas_index++){
    			for(int i = 0; i < 3; i++){
    				double speed =  speeds[limit_index][multas_index * 3 + i + 1];
    				double limit = limits[limit_index];
    				double amount = multas[multas_index];
    				int point = puntos[multas_index];
    				
    				// Pass at a "grave" ilegal speed
    	        	try{
    	    	        DriverDao dDao = new DriverDao();
    	    	        Driver driver = dDao.findByDni("5000009");
    	    	        int pointsPre = driver.getPoints();
    	    	        Manager m = Manager.get();
    	    	        int idInquiry = m.openInquiry("0034", speed, "Plaza del Pilar", limit);
    	    	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
    	    	        assertTrue(sanction.getAmount() == amount);
    	    	        assertTrue(sanction.getPoints() == point);
    	    	        assertNull(sanction.getDateOfPayment());
    	    	        m.pay(sanction.getId());
    	    	        driver = dDao.findByDni("5000009");
    	    	        assertTrue(driver.getPoints() == pointsPre - point);
    	    	    }catch(Exception e){
    	    	        fail("UnexpectedException" + e);
    	    	    }
    			}
    		}
    	}
    }
    
    
 // Test Case for "muy grave" ilegal speeds
    @Test
	public void test_ilegal_speeds_muy_grave(){
    	for(int limit_index = 0; limit_index < 10; limit_index++){
    		for(int i = 0; i < 2; i++){
    			double speed =  speeds[limit_index][i + 13];
				double limit = limits[limit_index];
				double amount = multas[4];
				int point = puntos[4];
    			
				// Pass at a "muy grave" ilegal speed
	        	try{
	        		DriverDao dDao = new DriverDao();
	    	        Driver driver = dDao.findByDni("5000009");
	    	        int pointsPre = driver.getPoints();
	    	        Manager m = Manager.get();
	    	        int idInquiry = m.openInquiry("0034", speed, "Plaza del Pilar", limit);
	    	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	    	        assertTrue(sanction.getAmount() == amount);
	    	        assertTrue(sanction.getPoints() == point);
	    	        assertNull(sanction.getDateOfPayment());
	    	        m.pay(sanction.getId());
	    	        driver = dDao.findByDni("5000009");
	    	        assertTrue(driver.getPoints() == pointsPre - point);
	    	    }catch(Exception e){
	    	        fail("UnexpectedException" + e);
	    	    }
    		}    		
    	}
    }
    
    
}