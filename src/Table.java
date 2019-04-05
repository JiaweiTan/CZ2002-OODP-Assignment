import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Table {
	public int TableId;
	private int Capacity;
	private int Status;
	
	public Table() {
		
	}
	
	public Table(int TableId, int Capacity, int Status) {
		this.TableId = TableId;
		this.Capacity = Capacity;
		this.Status = Status;
	}
	
	public int getTableId() {
		return TableId;
	}

	public void setTableId(int tableId) {
		TableId = tableId;
	}
	
	public int getCapacity() {
		return Capacity;
	}

	public void setCapacity(int capacity) {
		Capacity = capacity;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
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
				twoseater.TableId = tableid;
				twoseater.Capacity = 2;
				twoseater.Status = 0;
				tbList.add(twoseater);
			}
			else if(tableid > 10 && tableid <= 20) {
				Table fourseater = new Table();
				fourseater.TableId = tableid;
				fourseater.Capacity = 4;
				fourseater.Status = 0;
				tbList.add(fourseater);
			}
			else if(tableid > 20 && tableid <= 25) {
				Table eightseater = new Table();
				eightseater.TableId = tableid;
				eightseater.Capacity = 8;
				eightseater.Status = 0;
				tbList.add(eightseater);
			}
			else if(tableid > 25 && tableid <= 30) {
				Table tenseater = new Table();
				tenseater.TableId = tableid;
				tenseater.Capacity = 10;
				tenseater.Status = 0;
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
					if(updatedResList.get(i).getTableId() == tbList.get(j).TableId) {
						tbList.get(j).Status = 1;
					}
				}
			}
		}
		return tbList;
	}
}
