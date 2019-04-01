import java.io.IOException;
import java.util.*;
import java.text.*;

public class TableInfo {

	private String Date;
	private String Session;
	private int TenSeater = 5;
	private int EightSeater = 5;
	private int FourSeater = 10;
	private int TwoSeater = 10;
	private int TotalCount;

	public TableInfo() {

	}

	public TableInfo(String Date, String Session, int TenSeater, int EightSeater, int FourSeater, int TwoSeater,
			int TotalCount) {
		this.Date = Date;
		this.Session = Session;
		this.TenSeater = TenSeater;
		this.EightSeater = EightSeater;
		this.FourSeater = FourSeater;
		this.TwoSeater = TwoSeater;
		this.TotalCount = TotalCount;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getSession() {
		return Session;
	}

	public void setSession(String session) {
		Session = session;
	}

	public int getTenSeater() {
		return TenSeater;
	}

	public void setTenSeater(int tenSeater) {
		TenSeater = tenSeater;
	}

	public int getEightSeater() {
		return EightSeater;
	}

	public void setEightSeater(int eightSeater) {
		EightSeater = eightSeater;
	}

	public int getFourSeater() {
		return FourSeater;
	}

	public void setFourSeater(int fourSeater) {
		FourSeater = fourSeater;
	}

	public int getTwoSeater() {
		return TwoSeater;
	}

	public void setTwoSeater(int twoSeater) {
		TwoSeater = twoSeater;
	}

	public int getTotalCount() {
		return TotalCount;
	}

	public void setTotalCount(int totalCount) {
		TotalCount = totalCount;
	}

	public void createTableSession(TableInfo item) {
		try {
			DBManager.saveNewTableSession(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TableInfo getTableSession(String resDate, String resSession) {
		TableInfo tblItem = new TableInfo();
		boolean recordExist = false;
		TableInfo item = new TableInfo(resDate, resSession, 5, 5, 10, 10, 30);
		ArrayList<TableInfo> tbInfo = new ArrayList<TableInfo>();

		try {
			tbInfo = DBManager.readTableInfo("TableInfo.txt");
			if (tbInfo.size() != 0) {
				for (TableInfo tbl : tbInfo) {

					try {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date resvDate = format.parse(resDate);
						Date sesDate = format.parse(tbl.Date);
						if (sesDate.compareTo(resvDate) == 0 && (tbl.Session).equals(resSession)) {
							tblItem = tbl;
							recordExist = true;
							return tblItem;
						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (recordExist == false) {
					// Default Session
					createTableSession(item);
				}
			} else {
				// Default Session
				createTableSession(item);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return item;
	}

	public int checkTableSize(int resPax) {
		int result = 0;
		if (resPax > 8 && resPax <= 10) {
			result = 10;
		} else if (resPax > 4 && resPax <= 8) {
			result = 8;
		} else if (resPax > 2 && resPax <= 4) {
			result = 4;
		} else if (resPax > 0 && resPax <= 2) {
			result = 2;
		} else {
			// Handle for other ranges of number
		}
		return result;
	}

	public boolean checkAvailableTable(String resDate, String resSession, int resPax) {
		boolean result = false;
		TableInfo tbl = new TableInfo();
		tbl = getTableSession(resDate, resSession);
		int tablesize = checkTableSize(resPax);

		switch (tablesize) {
		case 2:
			result = tbl.TenSeater > 0 ? true : false;
		case 4:
			result = tbl.EightSeater > 0 ? true : false;
		case 8:
			result = tbl.FourSeater > 0 ? true : false;
		case 10:
			result = tbl.TwoSeater > 0 ? true : false;
		}
		return result;
	}

	public int assignTable(TableInfo tblItem, int tablesize) {
		// Find out which seater table to use
		// Update TableID

		// Table ID Sequence
		// 2 Seater - 1 to 10
		// 4 Seater - 11 to 20
		// 8 Seater - 21 to 25
		// 10 Seater - 26 to 30

		int tableID = 0;

		if (tablesize == 2) {
			if (tblItem.TwoSeater > 0) {
				tableID = 11 - tblItem.TwoSeater;
				tblItem.TwoSeater = tblItem.TwoSeater - 1;
			}
		} else if (tablesize == 4) {
			if (tblItem.FourSeater > 0) {
				tableID = 21 - tblItem.FourSeater;
				tblItem.FourSeater = tblItem.FourSeater - 1;
			}
		}

		else if (tablesize == 8) {
			if (tblItem.EightSeater > 0) {
				tableID = 26 - tblItem.EightSeater;
				tblItem.EightSeater = tblItem.EightSeater - 1;
			}
		} else if (tablesize == 10) {
			if (tblItem.TenSeater > 0) {
				tableID = 31 - tblItem.TenSeater;
				tblItem.TenSeater = tblItem.TenSeater - 1;
			}
		}

		if(tblItem.getTotalCount() > 0) {
			tblItem.setTotalCount(tblItem.TotalCount - 1);			
		}
		
		try {
			DBManager.UpdateTableSessionCount(tblItem);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return tableID;
	}

	public void releaseTable(TableInfo tblItem, int tablesize) {
		// Find out which seater table to release

		if (tablesize == 2) {
			if (tblItem.TwoSeater < 10) {
				tblItem.TwoSeater = tblItem.TwoSeater + 1;
			}
		} else if (tablesize == 4) {
			if (tblItem.FourSeater < 10) {
				tblItem.FourSeater = tblItem.FourSeater + 1;
			}
		}

		else if (tablesize == 8) {
			if (tblItem.EightSeater < 5) {
				tblItem.EightSeater = tblItem.EightSeater + 1;
			}
		} else if (tablesize == 10) {
			if (tblItem.TenSeater < 5) {
				tblItem.TenSeater = tblItem.TenSeater + 1;
			}
		}

		if(tblItem.getTotalCount() > 0) {
			tblItem.setTotalCount(tblItem.TotalCount + 1);		
		}

		try {
			DBManager.UpdateTableSessionCount(tblItem);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
