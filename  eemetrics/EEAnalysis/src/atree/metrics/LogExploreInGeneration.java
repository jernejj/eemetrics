package atree.metrics;

public class LogExploreInGeneration {
	private int generationID;
	private double exploreRatio;
	public static int NOT_DEFINED = -1;
	public LogExploreInGeneration(){
		generationID = NOT_DEFINED;
		exploreRatio = 0;
		
	}
	public LogExploreInGeneration(int generationID, double explorRatio) {
		super();
		this.generationID = generationID;
		this.exploreRatio = explorRatio;
	}
	public int getGenerationID() {
		return generationID;
	}
	public void setGenerationID(int generationID) {
		this.generationID = generationID;
	}
	public double getExplorRatio() {
		return exploreRatio;
	}
	public void setExplorRatio(double explorRatio) {
		this.exploreRatio = explorRatio;
	}
	
}
