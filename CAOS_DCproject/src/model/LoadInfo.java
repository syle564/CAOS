package model;

import java.sql.Timestamp;

public class LoadInfo {

	private Timestamp estStartTime;
	private Timestamp estEndTime;
	private int dockID;
	private String status;
	private int tr_ID;
	
	
	
	public LoadInfo() {
		super();
	}



	public LoadInfo(Timestamp estStartTime, Timestamp estEndTime, int dockID,
			String status, int tr_ID) {
		super();
		this.estStartTime = estStartTime;
		this.estEndTime = estEndTime;
		this.dockID = dockID;
		this.status = status;
		this.tr_ID = tr_ID;
	}



	public Timestamp getEstStartTime() {
		return estStartTime;
	}



	public void setEstStartTime(Timestamp estStartTime) {
		this.estStartTime = estStartTime;
	}



	public Timestamp getEstEndTime() {
		return estEndTime;
	}



	public void setEstEndTime(Timestamp estEndTime) {
		this.estEndTime = estEndTime;
	}



	public int getDockID() {
		return dockID;
	}



	public void setDockID(int dockID) {
		this.dockID = dockID;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public int getTr_ID() {
		return tr_ID;
	}



	public void setTr_ID(int tr_ID) {
		this.tr_ID = tr_ID;
	}



	@Override
	public String toString() {
		return "LoadInfo: estStartTime=" + estStartTime + ", estEndTime=" 
				+ estEndTime + ", dockID=" + dockID + ", status=" + status
				+ ", tr_ID=" + tr_ID;
	}
	
	
	
	
	
}
