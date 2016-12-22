package edu.uclm.esi.iso2.multas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Driver extends SanctionHolder {
	@Column
	private int points;
	
	public Driver() {
		this.points=12;
	}


	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
}
