import java.io.IOException;
import java.util.*;
import java.text.*;

public class TableInfo {

	private String date;
	private String session;
	private int tenSeater = 5;
	private int eightSeater = 5;
	private int fourSeater = 10;
	private int twoSeater = 10;
	private int totalCount;

	public TableInfo() {

	}

	public TableInfo(String date, String session, int tenSeater, int eightSeater, int fourSeater, int twoSeater,
			int totalCount) {
		this.date = date;
		this.session = session;
		this.tenSeater = tenSeater;
		this.eightSeater = eightSeater;
		this.fourSeater = fourSeater;
		this.twoSeater = twoSeater;
		this.totalCount = totalCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public int getTenSeater() {
		return tenSeater;
	}

	public void setTenSeater(int tenSeater) {
		this.tenSeater = tenSeater;
	}

	public int getEightSeater() {
		return eightSeater;
	}

	public void setEightSeater(int eightSeater) {
		this.eightSeater = eightSeater;
	}

	public int getFourSeater() {
		return fourSeater;
	}

	public void setFourSeater(int fourSeater) {
		this.fourSeater = fourSeater;
	}

	public int getTwoSeater() {
		return twoSeater;
	}

	public void setTwoSeater(int twoSeater) {
		this.twoSeater = twoSeater;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
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
		boolean recordExist = false, createSession = false;
		TableInfo item = new TableInfo(resDate, resSession, 5, 5, 10, 10, 30);
		ArrayList<TableInfo> tbInfo = new ArrayList<TableInfo>();

		try {
			tbInfo = DBManager.readTableInfo("TableInfo.txt");
			if (tbInfo.size() != 0) {
				for (TableInfo tbl : tbInfo) {

					try {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date resvDate = format.parse(resDate);
						Date sesDate = format.parse(tbl.date);
						if (sesDate.compareTo(resvDate) == 0 && (tbl.session).equals(resSession)) {
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
					//createTableSession(item);
					createSession = true;
				}
			} 
			else {
				// Default Session
				//createTableSession(item);
				createSession = true;
			}
			
			if(createSession) {
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
			result = tbl.twoSeater > 0 ? true : false;
		case 4:
			result = tbl.fourSeater > 0 ? true : false;
		case 8:
			result = tbl.eightSeater > 0 ? true : false;
		case 10:
			result = tbl.tenSeater > 0 ? true : false;
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
			if (tblItem.twoSeater > 0) {
				tableID = 11 - tblItem.twoSeater;
				tblItem.twoSeater = tblItem.twoSeater - 1;
			}
		} else if (tablesize == 4) {
			if (tblItem.fourSeater > 0) {
				tableID = 21 - tblItem.fourSeater;
				tblItem.fourSeater = tblItem.fourSeater - 1;
			}
		}

		else if (tablesize == 8) {
			if (tblItem.eightSeater > 0) {
				tableID = 26 - tblItem.eightSeater;
				tblItem.eightSeater = tblItem.eightSeater - 1;
			}
		} else if (tablesize == 10) {
			if (tblItem.tenSeater > 0) {
				tableID = 31 - tblItem.tenSeater;
				tblItem.tenSeater = tblItem.tenSeater - 1;
			}
		}

		if(tblItem.getTotalCount() > 0) {
			tblItem.setTotalCount(tblItem.totalCount - 1);			
		}
		
		try {
			DBManager.UpdateTableSessionCount(tblItem);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return tableID;
	}

	public void releaseTable(TableInfo tblItem, int tableSize) {
		// Find out which seater table to release

		if (tableSize == 2) {
			if (tblItem.twoSeater < 10) {
				tblItem.twoSeater = tblItem.twoSeater + 1;
			}
		} else if (tableSize == 4) {
			if (tblItem.fourSeater < 10) {
				tblItem.fourSeater = tblItem.fourSeater + 1;
			}
		}

		else if (tableSize == 8) {
			if (tblItem.eightSeater < 5) {
				tblItem.eightSeater = tblItem.eightSeater + 1;
			}
		} else if (tableSize == 10) {
			if (tblItem.tenSeater < 5) {
				tblItem.tenSeater = tblItem.tenSeater + 1;
			}
		}

		if(tblItem.getTotalCount() < 30) {
			tblItem.setTotalCount(tblItem.totalCount + 1);		
		}

		try {
			DBManager.UpdateTableSessionCount(tblItem);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
