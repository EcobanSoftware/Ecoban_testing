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
	
	@Before
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
        Query query2=session.createQuery("update Driver set points = :points where id = :id");
        query2.setParameter("points", 12);
        query2.setParameter("id", 10);
        query2.executeUpdate();
        t.commit();
    }

	// Test Case 1
	//Passing at 40 km/h through a radar limited at 30 km/h
	@Test
	public void test31_30(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 40, "Plaza del Pilar", 30);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount() == 100);
	        assertTrue(sanction.getPoints() == 0);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints() == 12);
	    }catch(Exception e){
	        fail("UnexpectedException" + e);
	    }
	}

	//Test Case 2
	//Passing at 50 km/h through a radar limited at 40 km/h
	@Test
	public void test41_40(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 50, "Calle Postas", 40);
	        Sanction sanction = m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount() == 100);
	        assertTrue(sanction.getPoints() == 0);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints() == 12);
	    }catch(Exception e){
	        fail("UnexpectedException" + e);
	    }
	}

	//Test Case 3
	//Passing at 64 km/h through a radar limited at 50 km/h
	@Test
	public void test51_50(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 64, "Avenida Tablas de Daimiel", 50);
	        Sanction sanction = m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount() == 100);
	        assertTrue(sanction.getPoints() == 0);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints() == 12);
	    }catch(Exception e){
	        fail("UnexpectedException" + e);
	    }
	}

	//Test Case 4
	//Passing at 85 km/h through a radar limited at 60 km/h
	@Test
	public void test61_60(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 85, "Ronda de Toledo", 60);
	        Sanction sanction = m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount() == 100);
	        assertTrue(sanction.getPoints() == 0);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints() == 12);
	    }catch(Exception e){
	        fail("UnexpectedException" + e);
	    }
	}

	//Test Case 5
	//Passing at 80 km/h through a radar limited at 70 km/h
	@Test
	public void test71_70(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 80, "Carretera de Porzuna", 70);
	        Sanction sanction = m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount() == 100);
	        assertTrue(sanction.getPoints() == 0);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints() == 12);
	    }catch(Exception e){
	        fail("UnexpectedException" + e);
	    }
	}

	//Test Case 6
	//Passing at 100 km/h through a radar limited at 80 km/h
	@Test
	public void test81_80(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 100, "Carretera de Porzuna", 80);
	        Sanction sanction = m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount() == 100);
	        assertTrue(sanction.getPoints() == 0);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints() == 12);
	    }catch(Exception e){
	        fail("UnexpectedException" + e);
	    }
	}

	//Test Case 7
	//Passing at 110 km/h through a radar limited at 900 km/h
	@Test
	public void test91_90(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 110, "Carretera de Daimiel", 90);
	        Sanction sanction = m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount() == 100);
	        assertTrue(sanction.getPoints() == 0);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints() == 12);
	    }catch(Exception e){
	        fail("UnexpectedException" + e);
	    }
	}

	//Test Case 8
	//Passing at 120 km/h through a radar limited at 100 km/h
	@Test
	public void test101_100(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 120, "Carretera Toledo", 100);
	        Sanction sanction = m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount() == 100);
	        assertTrue(sanction.getPoints() == 0);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints() == 12);
	    }catch(Exception e){
	        fail("UnexpectedException" + e);
	    }
	}

	//Test Case 9
	//Passing at 125 km/h through a radar limited at 110 km/h
	@Test
	public void test111_110(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 125, "Autovia A-43", 110);
	        Sanction sanction = m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount() == 100);
	        assertTrue(sanction.getPoints() == 0);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints() == 12);
	    }catch(Exception e){
	        fail("UnexpectedException" + e);
	    }
	}

	//Test Case 10
	//Passing at 140 km/h through a radar limited at 120 km/h
	@Test
	public void test121_120(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 140, "Autovia A-4", 120);
	        Sanction sanction = m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount() == 100);
	        assertTrue(sanction.getPoints() == 0);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints() == 12);
	    }catch(Exception e){
	        fail("UnexpectedException" + e);
	    }
	}

	// Test Case 11
	//Passing at 55 km/h through a radar limited at 30 km/h
	@Test
	public void test51_30(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 55, "Plaza del Pilar", 30);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==300);
	        assertTrue(sanction.getPoints()==2);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==10);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 12
	//Passing at 65 km/h through a radar limited at 40 km/h
	@Test
	public void test61_40(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 65, "Calle Calatrava", 40);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==300);
	        assertTrue(sanction.getPoints()==2);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==10);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 13
	//Passing at 75 km/h through a radar limited at 50 km/h
	@Test
	public void test71_50(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 75, "Ronda de Calatrava", 50);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==300);
	        assertTrue(sanction.getPoints()==2);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==10);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 14
	//Passing at 95 km/h through a radar limited at 60 km/h
	@Test
	public void test91_60(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 95, "Avenida de Europa", 60);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==300);
	        assertTrue(sanction.getPoints()==2);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==10);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	//Test Case 15
	//Passing at 115 km/h through a radar limited at 70 km/h
	@Test
	public void test101_70(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 115, "Carretera de Porzuna", 70);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==300);
	        assertTrue(sanction.getPoints()==2);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==10);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 16
	//Passing at 120 km/h through a radar limited at 80 km/h
	@Test
	public void test111_80(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 120, "Carretera de Piedrabuena", 80);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==300);
	        assertTrue(sanction.getPoints()==2);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId()); 
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==10);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 17
	//Passing at 135 km/h through a radar limited at 90 km/h
	@Test
	public void test121_90(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 135, "Carretera de Herencia", 90);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==300);
	        assertTrue(sanction.getPoints()==2);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==10);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 18
	//Passing at 140 km/h through a radar limited at 100 km/h
	@Test
	public void test131_100(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 140, "Carretera de Manzanares", 100);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==300);
	        assertTrue(sanction.getPoints()==2);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==10);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 19
	//Passing at 150 km/h through a radar limited at 110 km/h
	@Test
	public void test141_110(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 150, "Autovia A-4", 110);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==300);
	        assertTrue(sanction.getPoints()==2);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==10);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 20
	//Passing at 160 km/h through a radar limited at 120 km/h
	@Test
	public void test151_120(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 160, "Autovia A-4", 120);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==300);
	        assertTrue(sanction.getPoints()==2);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId()); 
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==10);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 21
	//Passing at 63 km/h through a radar limited at 30 km/h
	@Test
	public void test61_30(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 63, "Calle Toledo", 30);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==400);
	        assertTrue(sanction.getPoints()==4);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==8);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 22
	//Passing at 77 km/h through a radar limited at 40 km/h
	@Test
	public void test71_40(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 77, "Plaza del Pilar", 40);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==400);
	        assertTrue(sanction.getPoints()==4);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==8);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 23
	//Passing at 82 km/h through a radar limited at 50 km/h
	@Test
	public void test81_50(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 82, "Ronda Calatrava", 50);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==400);
	        assertTrue(sanction.getPoints()==4);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==8);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 24
	//Passing at 119 km/h through a radar limited at 60 km/h
	@Test
	public void test111_60(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 119, "Carretera de Carrión", 60);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==400);
	        assertTrue(sanction.getPoints()==4);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==8);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 25
	//Passing at 125 km/h through a radar limited at 70 km/h
	@Test
	public void test121_70(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 125, "Carretera de Miguelturra", 70);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==400);
	        assertTrue(sanction.getPoints()==4);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==8);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 26
	//Passing at 134 km/h through a radar limited at 80 km/h
	@Test
	public void test131_80(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 134, "Carretera de Piedrabuena", 80);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==400);
	        assertTrue(sanction.getPoints()==4);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==8);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 27
	//Passing at 149 km/h through a radar limited at 90 km/h
	@Test
	public void test141_90(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 149, "Carretera de Malaga", 90);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==400);
	        assertTrue(sanction.getPoints()==4);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==8);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 28
	//Passing at 158 km/h through a radar limited at 100 km/h
	@Test
	public void test151_100(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 158, "Carretera de Malagon", 100);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==400);
	        assertTrue(sanction.getPoints()==4);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==8);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 29
	//Passing at 168 km/h through a radar limited at 110 km/h
	@Test
	public void test161_110(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 168, "M-30 ", 110);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==400);
	        assertTrue(sanction.getPoints()==4);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==8);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 30
	//Passing at 178 km/h through a radar limited at 120 km/h
	@Test
	public void test171_120(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 178, "Autovia A-5 ", 120);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==400);
	        assertTrue(sanction.getPoints()==4);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==8);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 31
	//Passing at 75 km/h through a radar limited at 30 km/h
	@Test
	public void test71_30(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 75, "Plaza del Pilar", 30);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==500);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 32
	//Passing at 88 km/h through a radar limited at 40 km/h
	@Test
	public void test81_40(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 88, "Calle Calatrava", 40);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==500);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 33
	//Passing at 97 km/h through a radar limited at 50 km/h
	@Test
	public void test91_50(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 97, "Avenida de Europa", 50);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==500);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 34
	//Passing at 125 km/h through a radar limited at 60 km/h
	@Test
	public void test121_60(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 125, "Carretera de Porzuna", 60);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==500);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 35
	//Passing at 140 km/h through a radar limited at 70 km/h
	@Test
	public void test131_70(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 140, "Carretera de Piedrabuena", 70);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==500);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 36
	//Passing at 145 km/h through a radar limited at 80 km/h
	@Test
	public void test141_80(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 145, "Carretera de Miguelturra", 80);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==500);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 37
	//Passing at 155 km/h through a radar limited at 90 km/h
	@Test
	public void test151_90(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 155, "Carretera de Herencia", 90);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==500);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());      
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 38
	//Passing at 165 km/h through a radar limited at 100 km/h
	@Test
	public void test161_100(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 165, "Carretera de Manzanares", 100);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==500);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 39
	//Passing at 175 km/h through a radar limited at 110 km/h
	@Test
	public void test171_110(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 175, "Autovia A-43", 110);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==500);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 40
	//Passing at 185 km/h through a radar limited at 120 km/h
	@Test
	public void test181_120(){
	    try{
	        Manager m= Manager.get();
	        int idInquiry = m.openInquiry("0034", 185, "Autovia A-4", 120);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==500);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());	        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 41
	//Passing at 90 km/h through a radar limited at 30 km/h
	@Test
	public void test81_30(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 90, "Plaza del Pilar", 30);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==600);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 42
	//Passing at 100 km/h through radar limited at 40 km/h
	@Test 
	public void test91_40(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 100, "Plaza del Pilar", 40);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==600);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 43
	//Passing at 105 km/h through radar limited by 50 km/h
	@Test
	public void test101_50(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 105, "Ronda de Ciruela", 50);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==600);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());       
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 44
	//Passing at 139 km/h through radar limited by 60 km/h
	@Test 
	public void test131_60(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 139, "Avenida de Europa", 60);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==600);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());      
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 45
	//Passing at 150 km/h through radar limited by 70 km/h
	@Test 
	public void test141_70(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 150, "Carretera de Porzuna", 70);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==600);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());       
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 46
	//Passing at 167 km/h through radar limited by 80 km/h
	@Test 
	public void test151_80(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 167, "Carretera de Piedrabuena", 80);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==600);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());   
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 47
	//Passing at 171 km/h through radar limited by 90 km/h
	@Test 
	public void test161_90(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 171, "Carretera de Porzuna", 90);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==600);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());       
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 48
	//Passing at 179 km/h through radar limited by 100 km/h
	@Test 
	public void test171_100(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 179, "Carretera de Manzanares", 100);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==600);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 49
	//Passing at 200 km/h through radar limited by 110 km/h
	@Test 
	public void test181_110(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 181, "Carretera de Porzuna", 110);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==600);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());        
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}

	// Test Case 50
	//Passing at 210 km/h through radar limited by 120 km/h
	@Test 
	public void test191_120(){
	    try{
	        Manager m = Manager.get();
	        int idInquiry = m.openInquiry("0034", 210, "Autopista R-4", 120);
	        Sanction sanction=m.identifyDriver(idInquiry, "5000009");
	        assertTrue(sanction.getAmount()==600);
	        assertTrue(sanction.getPoints()==6);
	        assertNull(sanction.getDateOfPayment());
	        m.pay(sanction.getId());       
	        DriverDao dDao = new DriverDao();
	        Driver driver = dDao.findByDni("5000009");
	        assertTrue(driver.getPoints()==6);
	    }catch(Exception e){
	        fail("UnexpectedException"+e);
	    }
	}
	
}