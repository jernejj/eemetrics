package atree.util;

public class UtilTestMain {

	/**
	 * Just example test!
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LineParserECJ lp = new LineParserECJ("p1(-1,-1) p2(-2,-2) id(1,0) in( 1 0 0 0 1 0 1 1 1 1) c1 m2 r3");
		while (lp.getState()!=LineParserECJ.EOF) {
			System.out.println(lp.getState() +" Value:"+lp.getValue());
			lp.nextState();
			
		}
		System.out.println("Log file multi parent!");
		LogFileLineParser lp1 = new LogFileLineParser();
		lp1.parse("p1(-1,-1) p2(-2,-2) p3(-3,-3) p4(-4,-4) id(0,0) in( 6.332904577937874 4.603028992406429 0.01467603976412959) c1 m2 r3 fit(9.781609825142667)");
		while (lp1.getState()!=LineParserECJ.EOF) {
			System.out.println(lp1.getState() +" Value:"+lp1.getValue());
			lp1.nextState();
			
		}

	}

}
