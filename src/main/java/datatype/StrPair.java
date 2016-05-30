package datatype;

public class StrPair {

	public String s1;
	public String s2;
	public StrPair(String ts1, String ts2){
		s1 = ts1;
		s2 = ts2;
	}

	public String toString(){
		return "(" + s1 + ":" + s2 + ")";
	}
}
