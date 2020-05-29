package classes;

public enum Fire_type {
	A,B,C,D,E,F;
	
    private static Fire_type[] vals = values();

	
	public Fire_type next()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }
}
