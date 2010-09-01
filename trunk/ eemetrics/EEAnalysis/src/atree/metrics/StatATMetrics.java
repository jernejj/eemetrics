package atree.metrics;

import java.util.ArrayList;

import atree.util.MeanStDev;


public class StatATMetrics {
	ArrayList<MetricsValues> all;
	ArrayList<Double> tmpLista;
	public StatATMetrics() {
		tmpLista = new ArrayList<Double>();
		all = new ArrayList<MetricsValues>();
	}
	public void add(ATMetrics m) {
		all.add(new MetricsValues(m));
	}
	
	public MeanStDev getExplorRatio() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExplorRatio());
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExplorType_c() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExplorType_c());
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExplorType_m() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExplorType_m());
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExplorType_r() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExplorType_r());
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExplorType_rnd() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExplorType_rnd());
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExplorDynamic_1() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExplorDynamic_1().mean);
		return new MeanStDev(tmpLista);
	}
	
	public MeanStDev getExplorDynamic_1_std() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExplorDynamic_1().stdev);
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExplorDynamic_2() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExplorDynamic_2().mean);
		return new MeanStDev(tmpLista);
	}
	
	public MeanStDev getExplorDynamic_2_std() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExplorDynamic_2().stdev);
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExploitRatio() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExploitRatio());
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExploitType_c() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExploitType_c());
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExploitType_m() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExploitType_m());
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExploitType_r() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExploitType_r());
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExploitType_cln() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExploitType_cln());
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getExploitStructure() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getExploitStructure());
		return new MeanStDev(tmpLista);
	}

	public MeanStDev getCountAllNodes() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getCountAllNodes());
		return new MeanStDev(tmpLista);
	}
	
	public MeanStDev getRevisitedRatio() {
		tmpLista.clear();
		for (MetricsValues m:all) tmpLista.add(m.getRevisitedRatio());
		return new MeanStDev(tmpLista);
	}
	
	

}
