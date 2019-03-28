import java.io.IOException;
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

}
