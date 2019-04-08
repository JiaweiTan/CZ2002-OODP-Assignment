import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reservation {
	private int ReservationId;
	private int TableId;
	private String Date;
	private String ArrivalTime;
	private int Pax;
	private String Name;
	private String ContactNumber;

	public Reservation() {

	}

	public Reservation(int ReservationId, int TableId, String Date, String ArrivalTime, int Pax, String Name, String ContactNumber) {
		this.ReservationId = ReservationId;
		this.TableId = TableId;
		this.Date = Date;
		this.ArrivalTime = ArrivalTime;
		this.Pax = Pax;
		this.Name = Name;
		this.ContactNumber = ContactNumber;
	}

	public int getReservationId() {
		return ReservationId;
	}

	public void setReservationId(int reservationId) {
		ReservationId = reservationId;
	}

	public int getTableId() {
		return TableId;
	}

	public void setTableId(int tableId) {
		TableId = tableId;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getArrivalTime() {
		return ArrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		ArrivalTime = arrivalTime;
	}

	public int getPax() {
		return Pax;
	}

	public void setPax(int pax) {
		Pax = pax;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getContactNumber() {
		return ContactNumber;
	}

	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
	}
	
	public String checkSession(String arrivaltime) {
		// 24 hour timing
		// Check session range to see if input is valid
		String result;
		LocalTime session = LocalTime.parse(arrivaltime);

		LocalTime amStart = LocalTime.parse("09:59");

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
				if ((item.ContactNumber).equals(contactNumber)) {
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
	
	public ArrayList<Reservation> getTodayReservation(String todaydate, String session) {
		ArrayList<Reservation> resTb = new ArrayList<Reservation>();
		ArrayList<Reservation> todayResTb = new ArrayList<Reservation>();
		try {
			resTb = DBManager.readReservationInfo("Reservation.txt");
			for(Reservation item: resTb) {
				if(((item.Date).equals(todaydate)) && ((item.checkSession(item.ArrivalTime)).equals(session))) {
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
				oldresDate = format.parse(oldres.Date);
				Date newresDate = format.parse(newres.Date);
				String oldSession = oldres.checkSession(oldres.getArrivalTime());
				String newSession = newres.checkSession(newres.getArrivalTime());
				int oldTableSize = tb.checkTableSize(oldres.Pax);
				int newTableSize = tb.checkTableSize(newres.Pax);
				// #To handle
				// User change dates
				// User change session
				// User change pax
				// Best case scenario, no change to dates, session and pax!
				// Just update the database immediately!

				if (oldresDate.compareTo(newresDate) == 0 && oldSession == newSession && oldTableSize == newTableSize) {
					DBManager.saveExistingReservation(newres, oldres.ContactNumber);
				} else {
					// If Session & Date is different, the data will be in a different row
					int newTableCount = 0;
					if (oldSession != newSession || oldresDate.compareTo(newresDate) != 0) {
						// Release table - Increment count & tableseater by 1
						// Get the updated date & session
						// Assign table - Decrement count & tableseater by 1
						// Update the reservation table id
						TableInfo tbItem = new TableInfo();
						tbItem = tb.getTableSession(oldres.Date, oldSession);
						tb.releaseTable(tbItem, oldTableSize);
						TableInfo tbNewItem = new TableInfo();
						tbNewItem = tb.getTableSession(newres.Date, newSession);
						newTableCount = tb.assignTable(tbNewItem, newTableSize);
					}
					else if(oldTableSize != newTableSize) {
						//Update the pax count only
						TableInfo tbItem = new TableInfo();
						tbItem = tb.getTableSession(oldres.Date, oldSession);
						tb.releaseTable(tbItem, oldTableSize);
						newTableCount = tb.assignTable(tbItem, newTableSize);				
					}
					newres.TableId = newTableCount;				
					DBManager.saveExistingReservation(newres, oldres.ContactNumber);
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
			tbItem = tb.getTableSession(res.Date, checkSession(res.ArrivalTime));
			tb.releaseTable(tbItem, tb.checkTableSize(res.Pax));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
