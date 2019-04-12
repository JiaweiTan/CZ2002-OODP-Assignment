import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Table {
	public int tableId;
	private int capacity;
	private int status;
	
	public Table() {
		
	}
	
	public Table(int tableId, int capacity, int status) {
		this.tableId = tableId;
		this.capacity = capacity;
		this.status = status;
	}
	
	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String statusText(int status) {
		String result = "Available";
		if(status == 1) {
			result = "Reserved";
		}
		return result;
	}
	
	public ArrayList<Table> initTables(){
		ArrayList<Table> tbList = new ArrayList<Table>();
		for(int i = 0; i < 30; i++) {
			int tableid = i + 1;
			if(tableid <= 10) {
				Table twoseater = new Table();
				twoseater.tableId = tableid;
				twoseater.capacity = 2;
				twoseater.status = 0;
				tbList.add(twoseater);
			}
			else if(tableid > 10 && tableid <= 20) {
				Table fourseater = new Table();
				fourseater.tableId = tableid;
				fourseater.capacity = 4;
				fourseater.status = 0;
				tbList.add(fourseater);
			}
			else if(tableid > 20 && tableid <= 25) {
				Table eightseater = new Table();
				eightseater.tableId = tableid;
				eightseater.capacity = 8;
				eightseater.status = 0;
				tbList.add(eightseater);
			}
			else if(tableid > 25 && tableid <= 30) {
				Table tenseater = new Table();
				tenseater.tableId = tableid;
				tenseater.capacity = 10;
				tenseater.status = 0;
				tbList.add(tenseater);				
			}
		}
		return tbList;
	}
	
	public ArrayList<Table> getTableStatus(ArrayList<Reservation> resList){
		ArrayList<Table> tbList = new ArrayList<Table>();
		ArrayList<Reservation> updatedResList = new ArrayList<Reservation>();
		Reservation res = new Reservation();
		tbList = initTables();
		//Check if Reservation have expired
		//If it is, delete reservation
		for(int i = 0; i < resList.size(); i++) {
			LocalTime arrivalTime = LocalTime.parse(resList.get(i).getArrivalTime());
			LocalTime futureTime = arrivalTime.plusMinutes(30);
			LocalTime currentTime = LocalTime.now();
			if(currentTime.isBefore(arrivalTime) && arrivalTime.isBefore(futureTime)) {
				updatedResList.add(resList.get(i));
			}
			else {
				res.deleteReservation(resList.get(i));
			}
		}
		//Fetch a list of Reservation table ID
		//Set the Table Status to booked
		//Return information to TableApp
		if(updatedResList.size() > 0) {
			for(int i = 0; i < updatedResList.size(); i++) {
				for(int j = 0; j < tbList.size(); j++) {
					if(updatedResList.get(i).getTableId() == tbList.get(j).tableId) {
						tbList.get(j).status = 1;
					}
				}
			}
		}
		return tbList;
	}
}
