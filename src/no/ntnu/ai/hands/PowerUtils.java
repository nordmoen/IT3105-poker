package no.ntnu.ai.hands;

public class PowerUtils {
	public static PowerRating max(PowerRating a, PowerRating b){
		return (a.compareTo(b) > 0? a: b);
	}
	
	public static PowerRating max(PowerRating[] a){
		PowerRating res = a[0];
		for(int i=1; i<a.length; i++){
			res = max(res, a[i]);
		}
		return res;
	}

}
