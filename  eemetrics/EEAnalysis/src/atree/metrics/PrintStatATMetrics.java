package atree.metrics;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class PrintStatATMetrics {
	StatATMetrics m;
	NumberFormat formatter, stdFormat;

	public PrintStatATMetrics(StatATMetrics m) {
		super();
		this.m = m;
		formatter = new DecimalFormat("#0.000");
		stdFormat = new DecimalFormat("#.00");
	}
	
	public static String toLatex(ArrayList<String> heads, ArrayList<ArrayList<String>> cols, String tableCaption) {
		StringBuffer sb = new StringBuffer();
		sb.append("\\begin{table}[H]\n");
		sb.append("\\begin{center}\n");
		sb.append("\\begin{tabular}{@{}l@{\\hspace{1pt}}||@{\\hspace{1pt}}");
		for (int i=0;i<heads.size();i++) {
			sb.append("@{\\hspace{1pt}}r");
			if (i<(heads.size()-1))sb.append("@{\\hspace{1pt}} | ");
			else sb.append("} \n ");
		}
		sb.append("Metrics &");
		for (int i=0;i<heads.size();i++) {
			sb.append(heads.get(i));
			if (i<(heads.size()-1))sb.append(" & ");
			else sb.append("\\\\ \\hline\\hline\n");
		}
		for (int j=0;j<cols.get(0).size();j++) {
		  for (int i=0;i<cols.size();i++) {
				sb.append(cols.get(i).get(j));
				if (i<(cols.size()-1))sb.append(" & ");
				else sb.append("\\\\ \\hline\n");				
			}
		}
		
		sb.append("\\end{tabular}\n");
		sb.append("\\caption{"+tableCaption+"}\n");
		sb.append("\\label{smalldatatable}\n");
		sb.append("\\end{center}\n");
		sb.append("\\end{table}\n");
		return sb.toString();
	}
	
	public ArrayList<String> getColumn() {
		ArrayList<String> data = new ArrayList<String>();
		data.add(m.getExplorRatio().toString());
		data.add(m.getExplorType_c().toString());
		data.add(m.getExplorType_m().toString());
		data.add(m.getExplorType_r().toString());
		data.add(m.getExplorType_rnd().toString());
		data.add(m.getExplorDynamic_1().toString());
		data.add(m.getExplorDynamic_1_std().toString());
		data.add(m.getExplorDynamic_2().toString());
		data.add(m.getExplorDynamic_2_std().toString());
		data.add(m.getExploitRatio().toString());
		data.add(m.getExploitType_c().toString());
		data.add(m.getExploitType_m().toString());
		data.add(m.getExploitType_r().toString());
		data.add(m.getExploitType_cln().toString());
		data.add(m.getExploitStructure().toString());
		data.add(m.getCountAllNodes().toString());
		data.add(m.getRevisitedRatio().toString());		
		return data;
		
	}
	public ArrayList<String> getDoubleColumn() {
		ArrayList<String> data = new ArrayList<String>();
		data.add(m.getExplorRatio().toStringMean());
		data.add(m.getExplorRatio().toStringStDev());
		data.add(m.getExplorType_c().toStringMean());
		data.add(m.getExplorType_c().toStringStDev());
		data.add(m.getExplorType_m().toStringMean());
		data.add(m.getExplorType_m().toStringStDev());
		data.add(m.getExplorType_r().toStringMean());
		data.add(m.getExplorType_r().toStringStDev());
		data.add(m.getExplorType_rnd().toStringMean());
		data.add(m.getExplorType_rnd().toStringStDev());
		data.add(m.getExplorDynamic_1().toStringMean());
		data.add(m.getExplorDynamic_1().toStringStDev());
		//data.add(m.getExplorDynamic_1_std().toString());
		data.add(m.getExplorDynamic_2().toStringMean());
		data.add(m.getExplorDynamic_2().toStringStDev());
		//data.add(m.getExplorDynamic_2_std().toString());
		data.add(m.getExploitRatio().toStringMean());
		data.add(m.getExploitRatio().toStringStDev());
		data.add(m.getExploitType_c().toStringMean());
		data.add(m.getExploitType_c().toStringStDev());
		data.add(m.getExploitType_m().toStringMean());
		data.add(m.getExploitType_m().toStringStDev());
		data.add(m.getExploitType_r().toStringMean());
		data.add(m.getExploitType_r().toStringStDev());
		data.add(m.getExploitType_cln().toStringMean());
		data.add(m.getExploitType_cln().toStringStDev());
		data.add(m.getExploitStructure().toStringMean());
		data.add(m.getExploitStructure().toStringStDev());
		data.add(m.getRevisitedRatio().toStringMean());
		data.add(m.getRevisitedRatio().toStringStDev());
		//data.add(""+(int)m.getCountAllNodes().getMean());
		return data;
		
	}
	public static ArrayList<String> getInfoColumn() {
		ArrayList<String> data = new ArrayList<String>();
		data.add("{\\footnotesize$explorRatio$"+"}");
		data.add("{\\footnotesize$explorType(c)$"+"}");
		data.add("{\\footnotesize$explorType(m)$"+"}");
		data.add("{\\footnotesize$explorType(r)$"+"}");
		data.add("{\\footnotesize$explorType(rnd)$"+"}");
		data.add("{\\footnotesize$explorDynamic_1$"+"}");
		data.add("{\\footnotesize$\\hspace{15mm}stdev$"+"}");
		data.add("{\\footnotesize$explorDynamic_2$"+"}");
		data.add("{\\footnotesize$\\hspace{15mm}stdev$"+"}");
		data.add("{\\footnotesize$exploitRatio$"+"}");
		data.add("{\\footnotesize$exploitType(c)$"+"}");
		data.add("{\\footnotesize$exploitType(m)$"+"}");
		data.add("{\\footnotesize$exploitType(r)$"+"}");
		data.add("{\\footnotesize$exploitType(cln)$"+"}");
		data.add("{\\footnotesize$exploitStructure$"+"}");
		data.add("{\\footnotesize$countAllNodes$"+"}");
		data.add("{\\footnotesize$revisitedRatio$"+"}");
		return data;		
	}
	public static ArrayList<String> getDoubleInfoColumn() {
		ArrayList<String> data = new ArrayList<String>();
		data.add("{\\footnotesize$explorRatio$"+"}");
		data.add("");
		data.add("{\\footnotesize$explorType(c)$"+"}");
		data.add("");
		data.add("{\\footnotesize$explorType(m)$"+"}");
		data.add("");
		data.add("{\\footnotesize$explorType(r)$"+"}");
		data.add("");
		data.add("{\\footnotesize$explorType(rnd)$"+"}");
		data.add("");
		data.add("{\\footnotesize$explorDynamic_1$"+"}");
		data.add("");
		//data.add("{\\footnotesize$\\hspace{15mm}stdev$"+"}");
		data.add("{\\footnotesize$explorDynamic_2$"+"}");
		data.add("");
		//data.add("{\\footnotesize$\\hspace{15mm}stdev$"+"}");
		data.add("{\\footnotesize$exploitRatio$"+"}");
		data.add("");
		data.add("{\\footnotesize$exploitType(c)$"+"}");
		data.add("");
		data.add("{\\footnotesize$exploitType(m)$"+"}");
		data.add("");
		data.add("{\\footnotesize$exploitType(r)$"+"}");
		data.add("");
		data.add("{\\footnotesize$exploitType(cln)$"+"}");
		data.add("");
		data.add("{\\footnotesize$exploitStructure$"+"}");
		data.add("");
		data.add("{\\footnotesize$revisitedRatio$"+"}");
		data.add("");
		//data.add("{\\footnotesize$countAllNodes$"+"}");
		return data;		
	}

	public void printToScreen() {
		System.out.println("Not implemented yet!");
	}

	

}
