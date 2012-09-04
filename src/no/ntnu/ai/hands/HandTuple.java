package no.ntnu.ai.hands;

public class HandTuple {
	
	private final int c1Val;
	private final int c2Val;
	private final boolean suited;
	
	public HandTuple(int val1, int val2, boolean suited){
		this.c1Val = val1;
		this.c2Val = val2;
		this.suited = suited;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + c1Val;
		result = prime * result + c2Val;
		result = prime * result + (suited ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HandTuple other = (HandTuple) obj;
		if (c1Val != other.c1Val)
			return false;
		if (c2Val != other.c2Val)
			return false;
		if (suited != other.suited)
			return false;
		return true;
	}

}
