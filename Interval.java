package HW2;

public class Interval implements Comparable{
	private int start;
	private int end;
	public Interval(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public int compareTo(Object o) {
		Interval inter = (Interval)o;
		if(this.start < inter.start) return -1;
		else if(this.start == inter.start) return 0;
		else return 1;
	}
}

