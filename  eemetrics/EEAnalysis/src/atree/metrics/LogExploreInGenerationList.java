package atree.metrics;

import java.util.ArrayList;

public class LogExploreInGenerationList {
	private ArrayList<LogExploreInGeneration> list;

	public LogExploreInGenerationList() {
		list = new ArrayList<LogExploreInGeneration>();
	}
	
	public void add(LogExploreInGeneration log) {
		list.add(log);
	}
	
	public void add(int genID, double exploreRatio) {
		//CM debug
		System.out.println("genID:"+genID+"; exploreRatio:"+exploreRatio);
		list.add(new LogExploreInGeneration(genID, exploreRatio));
	}
	
	public  ArrayList<LogExploreInGeneration> getAllData() {
		return list;
	}
	
	public String toTabDelimeted() {
		StringBuffer sb = new StringBuffer();
		sb.append("Generation\tExploreRatio\n");
		for (int i=0; i<list.size();i++) {
			sb.append(list.get(i).getGenerationID()).append("\t").append(list.get(i).getExplorRatio()).append("\n");
		}
		return sb.toString();
	}
}
