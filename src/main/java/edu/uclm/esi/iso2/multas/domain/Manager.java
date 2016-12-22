package edu.uclm.esi.iso2.multas.domain;

import edu.uclm.esi.iso2.multas.dao.DriverDao;
import edu.uclm.esi.iso2.multas.dao.GeneralDao;
import edu.uclm.esi.iso2.multas.dao.OwnerDao;

/**
 * This class is the access point to the business logic.
 * @author macario.polo
 *  
 */
public class Manager {
	
	private Manager() {
		
	}
	
	public static class ManagerHolder {
		public static Manager manager=new Manager();
	}
	
	/**
	 * 
	 * @return The only existing instance of the Manager
	 */
	public static Manager get() {
		return ManagerHolder.manager;
	}
	
	/**
	 * Executed by {@link Manager} when a vehicle passes through the radar exceeding the speed limit
	 * @param license: license plate of the vehicle
	 * @param speed: speed of the vehicle
	 * @param location: location of the radar
	 * @param maxSpeed: max speed in the controlled point
	 */
	public int openInquiry(String license, double speed, String location, double maxSpeed) {
		Inquiry inquiry=new Inquiry(license, speed, location, maxSpeed);
		GeneralDao<Inquiry> dao=new GeneralDao<>();
		dao.insert(inquiry);
		return inquiry.getId();
	}
	
	/**
	 * Executed from the user interface when the vehicle's owner identifies the driver
	 * @param idInquiry id of the {@link Inquiry}
	 * @param dni dni of the {@link Driver}
	 * @return 
	 */
	public Sanction identifyDriver(int idInquiry, String dni) {
		GeneralDao<Inquiry> dao=new GeneralDao<>();
		Inquiry inquiry=dao.findById(Inquiry.class, idInquiry);
		Sanction sanction=inquiry.createSanctionFor(dni);
		subtractPoints(dni, sanction.getPoints());
		return sanction;
	}
	
	/**
	 * Subtract the points to the driver
	 * @param dni
	 * @param points
	 * @return 
	 */
	public void subtractPoints(String dni, int points){
		DriverDao Ddao = new DriverDao();
		Driver driver = Ddao.findByDni(dni);
		driver.setPoints(driver.getPoints() - points);
		Ddao.update(driver);
	}
	
	/***
	 * Executed from the user interface when a sanction is paid
	 * @param idSanction The id of the sanction to be paid
	 */
	public void pay(int idSanction) {
		GeneralDao<Sanction> dao=new GeneralDao<>();
		Sanction sanction=dao.findById(Sanction.class, idSanction);
		sanction.pay();
		dao.update(sanction);
	}
	
}
