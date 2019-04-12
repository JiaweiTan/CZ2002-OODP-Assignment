import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reservation {
	private int reservationId;
	private int tableId;
	private String date;
	private String arrivalTime;
	private int pax;
	private String name;
	private String contactNumber;

	public Reservation() {

	}

	public Reservation(int reservationId, int tableI, String date, String arrivalTime, int pax, String name, String contactNumber) {
		this.reservationId = reservationId;
		this.tableId = tableI;
		this.date = date;
		this.arrivalTime = arrivalTime;
		this.pax = pax;
		this.name = name;
		this.contactNumber = contactNumber;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getPax() {
		return pax;
	}

	public void setPax(int pax) {
		this.pax = pax;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public String checkSession(String arrivaltime) {
		// 24 hour timing
		// Check session range to see if input is valid
		String result;
		LocalTime session = LocalTime.parse(arrivaltime);

		LocalTime amStart = LocalTime.parse("10:59");

		LocalTime amEnd = LocalTime.parse("15:01");

		LocalTime pmStart = LocalTime.parse("17:59");

		LocalTime pmEnd = LocalTime.parse("22:01");

		// Check if the time given is AM/PM or invalid
		if (session.isBefore(amEnd) && session.isAfter(amStart)) {
			result = "AM";
		} else if (session.isBefore(pmEnd) && session.isAfter(pmStart)) {
			result = "PM";
		} else {
			result = "Invalid";
		}
		return result;
	}

	public void createReservation(Reservation item) {
		try {
			DBManager.saveNewReservation(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNewReservationId() throws IOException
	{	String filename= "Reservation.txt";
		List<Reservation> PromoSetList = DBManager.readReservationInfo(filename);
		if(PromoSetList.size()>0)
			return PromoSetList.get(PromoSetList.size()-1).getReservationId() + 1;
		else
			return 3001;
	}
	
	public Reservation getReservation(String contactNumber) {
		ArrayList<Reservation> res = new ArrayList<Reservation>();
		Reservation resItem = new Reservation();
		try {
			res = DBManager.readReservationInfo("Reservation.txt");
			for (Reservation item : res) {
				if ((item.contactNumber).equals(contactNumber)) {
					resItem = item;
					return resItem;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Reservation> getAllReservations(){
		ArrayList<Reservation> resTb = new ArrayList<Reservation>();
		try {
			resTb = DBManager.readReservationInfo("Reservation.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resTb;
	}
	
	public ArrayList<Reservation> getTodayReservation(String todayDate, String session) {
		ArrayList<Reservation> resTb = new ArrayList<Reservation>();
		ArrayList<Reservation> todayResTb = new ArrayList<Reservation>();
		try {
			resTb = DBManager.readReservationInfo("Reservation.txt");
			for(Reservation item: resTb) {
				if(((item.date).equals(todayDate)) && ((item.checkSession(item.arrivalTime)).equals(session))) {
					todayResTb.add(item);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return todayResTb;
	}

	public void updateReservation(Reservation oldres, Reservation newres) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			TableInfo tb = new TableInfo();
			Date oldresDate;
			try {
				oldresDate = format.parse(oldres.date);
				Date newresDate = format.parse(newres.date);
				String oldSession = oldres.checkSession(oldres.getArrivalTime());
				String newSession = newres.checkSession(newres.getArrivalTime());
				int oldTableSize = tb.checkTableSize(oldres.pax);
				int newTableSize = tb.checkTableSize(newres.pax);
				// #To handle
				// User change dates
				// User change session
				// User change pax
				// Best case scenario, no change to dates, session and pax!
				// Just update the database immediately!

				if (oldresDate.compareTo(newresDate) == 0 && oldSession == newSession && oldTableSize == newTableSize) {
					DBManager.saveExistingReservation(newres, oldres.contactNumber);
				} else {
					// If Session & Date is different, the data will be in a different row
					int newTableCount = 0;
					if (oldSession != newSession || oldresDate.compareTo(newresDate) != 0) {
						// Release table - Increment count & tableseater by 1
						// Get the updated date & session
						// Assign table - Decrement count & tableseater by 1
						// Update the reservation table id
						TableInfo tbItem = new TableInfo();
						tbItem = tb.getTableSession(oldres.date, oldSession);
						tb.releaseTable(tbItem, oldTableSize);
						TableInfo tbNewItem = new TableInfo();
						tbNewItem = tb.getTableSession(newres.date, newSession);
						newTableCount = tb.assignTable(tbNewItem, newTableSize);
					}
					else if(oldTableSize != newTableSize) {
						//Update the pax count only
						TableInfo tbItem = new TableInfo();
						tbItem = tb.getTableSession(oldres.date, oldSession);
						tb.releaseTable(tbItem, oldTableSize);
						newTableCount = tb.assignTable(tbItem, newTableSize);				
					}
					newres.tableId = newTableCount;				
					DBManager.saveExistingReservation(newres, oldres.contactNumber);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteReservation(Reservation res) {
		try {
			//Delete Reservation
			//Get Table Session
			//release table
			DBManager.deleteReservation(res);
			TableInfo tb = new TableInfo();
			TableInfo tbItem = new TableInfo();
			tbItem = tb.getTableSession(res.date, checkSession(res.arrivalTime));
			tb.releaseTable(tbItem, tb.checkTableSize(res.pax));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
