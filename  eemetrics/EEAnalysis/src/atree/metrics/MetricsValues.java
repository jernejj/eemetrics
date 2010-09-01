package atree.metrics;

import java.util.ArrayList;

import atree.util.MeanStDev;


public class MetricsValues {
	private double explorRatio;
	private double explorType_c;
	private double explorType_m;
	private double explorType_r;
	private double explorType_rnd;
	private MeanStDev explorDynamic_1;
	private MeanStDev explorDynamic_2;
	private double exploitRatio;
	private double exploitType_c;
	private double exploitType_m;
	private double exploitType_r;
	private double exploitType_cln;
	private double exploitStructure;
	private double countAllNodes;
	private double revisitedRatio;

	public double getExplorRatio() {
		return explorRatio;
	}
	
	public double getRevisitedRatio() {
		return revisitedRatio;
	}

	public double getExplorType_c() {
		return explorType_c;
	}

	public double getExplorType_m() {
		return explorType_m;
	}

	public double getExplorType_r() {
		return explorType_r;
	}

	public double getExplorType_rnd() {
		return explorType_rnd;
	}

	public MeanStDev getExplorDynamic_1() {
		return explorDynamic_1;
	}

	public MeanStDev getExplorDynamic_2() {
		return explorDynamic_2;
	}

	public double getExploitRatio() {
		return exploitRatio;
	}

	public double getExploitType_c() {
		return exploitType_c;
	}

	public double getExploitType_m() {
		return exploitType_m;
	}

	public double getExploitType_r() {
		return exploitType_r;
	}

	public double getExploitType_cln() {
		return exploitType_cln;
	}

	public double getExploitStructure() {
		return exploitStructure;
	}

	public double getCountAllNodes() {
		return countAllNodes;
	}

	public MetricsValues(ATMetrics m) {
		super();
		this.explorRatio = m.explorRatio();
		explorType_c = m.explorType(0);
		explorType_m = m.explorType(1);
		explorType_r = m.explorType(2);
		explorType_rnd = m.explorType(3);
		this.explorDynamic_1 = m.explorDynamic1();
		this.explorDynamic_2 = m.explorDynamic2();
		this.exploitRatio = m.exploitRatio();
		exploitType_c = m.exploitType(0);
		exploitType_m = m.exploitType(1);
		exploitType_r = m.exploitType(2);
		exploitType_cln = m.exploitType(4);
		this.exploitStructure = m.exploitStructure();
		this.countAllNodes = m.getCount();
		this.revisitedRatio = m.revisitedRatio();
	}
}
