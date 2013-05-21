package simplex;

public class Entry {
	public final double v;
	public Entry(double v){
		this.v=v;
	}
	
	@Override
	public String toString(){
		return String.valueOf(v);
	}
}
