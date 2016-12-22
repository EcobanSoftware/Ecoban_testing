package edu.uclm.esi.iso2.multas.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import edu.uclm.esi.iso2.multas.dao.DriverDao;
import edu.uclm.esi.iso2.multas.dao.GeneralDao;
import edu.uclm.esi.iso2.multas.dao.OwnerDao;

@Entity
@Table
public class Inquiry {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable=false, updatable=false)
	private Date dateOfIssue;
	@Column(nullable=false, updatable=false)
	private String location;
	@ManyToOne(targetEntity=Owner.class)
	private Owner owner;
	@Column(nullable=false, updatable=false)
	private double speed;
	@Column(nullable=false, updatable=false)
	private double maxSpeed;
	@OneToOne(fetch = FetchType.LAZY, targetEntity=Sanction.class, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Sanction sanction;
	static int [][][] points_speeds_matrix = {
			{{0,30,0,0},{31,50,0,100},{51,60,2,300},{61,70,4,400},{71,80,6,500},{81,1000,6,600}},
			{{0,40,0,0},{41,60,0,100},{61,70,2,300},{71,80,4,400},{81,90,6,500},{91,1000,6,600}},
			{{0,50,0,0},{51,70,0,100},{71,80,2,300},{81,90,4,400},{91,100,6,500},{101,1000,6,600}},
			{{0,60,0,0},{61,90,0,100},{91,110,2,300},{111,120,4,400},{121,130,6,500},{131,1000,6,600}},
			{{0,70,0,0},{71,100,0,100},{101,120,2,300},{121,130,4,400},{131,140,6,500},{141,1000,6,600}},
			{{0,80,0,0},{81,110,0,100},{111,130,2,300},{131,140,4,400},{141,150,6,500},{151,1000,6,600}},
			{{0,90,0,0},{91,120,0,100},{121,140,2,300},{141,150,4,400},{151,160,6,500},{161,1000,6,600}},
			{{0,100,0,0},{101,130,0,100},{131,150,2,300},{151,160,4,400},{161,170,6,500},{171,1000,6,600}},
			{{0,110,0,0},{111,140,0,100},{141,160,2,300},{161,170,4,400},{171,180,6,500},{181,1000,6,600}},
			{{0,120,0,0},{121,150,0,100},{151,170,2,300},{171,180,4,400},{181,190,6,500},{191,1000,6,600}},
	};
	
	public Inquiry() {
		
	}
	
	public Inquiry(String license, double speed, String location, double maxSpeed) {
		this();
		this.dateOfIssue=new Date(System.currentTimeMillis());
		this.speed=speed;
		this.maxSpeed=maxSpeed;
		this.location=location;
		this.owner=findOwner(license);
	}

	private Owner findOwner(String license) {
		OwnerDao dao=new OwnerDao();
		return dao.findByLicense(license);
	}

	public Sanction createSanctionFor(String dni) {
		int points=getAmountAndPoints(maxSpeed,speed)[0];
		int amount=getAmountAndPoints(maxSpeed,speed)[1];
		Sanction sanction=new Sanction();
		DriverDao dao=new DriverDao();
		Driver driver=dao.findByDni(dni);
		sanction.setSanctionHolder(driver);
		sanction.setPoints(points);
		sanction.setAmount(amount);
		GeneralDao<Sanction> daoSanction=new GeneralDao<>();
		daoSanction.insert(sanction);
		return sanction;
	}

	public int getId() {
		return id;
	}

	private int [] getAmountAndPoints(double maxSpeed, double speed){
		int [] result = new int [2];
		int [][] matrix = getMatrix(maxSpeed);
		for(int i=0;i<matrix.length; i++){
			int [] row = matrix[i];
			if(row[0]<=speed && row[1]>=speed){
				result[0]=row[2];
				result[1]=row[3];
				break;
			}
		}
		return result;
	}
	
	public int [][] getMatrix(double maxSpeed){
		return points_speeds_matrix[(int) ((maxSpeed/10)-3)];
		
	}
}
