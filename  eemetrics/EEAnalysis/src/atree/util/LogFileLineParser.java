package atree.util;

import atree.treeData.Node;
import atree.treeData.NodeRealValues;
import atree.treeData.Nodes;

public class LogFileLineParser {
	public static final int BOF=0;
	public static final int EOF=-1;
	public static final int PID=1;
	public static final int ID=3;
	public static final int IN=4;
	public static final int OPERATOR=9;
	public static final int CROSS=5;
	public static final int MUTAT=6;
	public static final int REPAIR=7;
	public static final int FITNESS=8;
	
	private String line;
	private String value;
	int state; 
	int pos;
	int len;
	boolean start, end;

	public Node convertFromString(String line, Nodes all) 
	{
		parse(line);
		NodeRealValues r = new NodeRealValues();
		Node p1;
		p1=null;
		String id[];
		String key;
		while (getState()!=LineParserECJ.EOF) {
			switch (getState()) {
			case ID:
				id = getValues(",");
				r.setIdInPop(Long.parseLong(id[0]));
				r.setIdGen(Long.parseLong(id[1]));
				break;
			case PID:
				id = getValues(",");
				key= "("+id[1]+","+id[0]+")";
				if (key.equals("(-1,-1)")) break; //random or no
				if (all.containsKey(key)) {
					p1=all.get(key);
					r.addParent(p1);
				} else 
				{
					System.err.println("Log file error ("+key+")");
				}
				break;
			case IN:
				r.setChromo(getValue().trim());
				break;
			case MUTAT:
				r.setM(getIntValue()>0);
				break;
			case CROSS:
				r.setC(getIntValue()>0);
				break;
			case REPAIR:
				r.setR(getIntValue()>0);
				break;
			case FITNESS:
				r.setFitness(getValue().trim());
				break;
			}
			nextState();
		}
		return r;
	}
	
	public int getState() {
		return state;
	}
	public String getValue() {
		return value;
	}
	public int getIntValue() {
		return Integer.parseInt(value);
	}
	public String[] getValues(String seperator) {
		return value.split(seperator);
	}
	public LogFileLineParser() {
		line ="";
		len = line.length();
		state=EOF;
		pos=0;
	}
	public void parse(String l) {
		line =l;
		len = line.length();
		state=BOF;
		pos=0;
		nextState();	
	}
	
	//Example: p1(-1,-1) p2(-1,-1) id(1,0) in( 1 0 0 0 1 0 1 1 1 1) c1 m0 r0
	public void nextState() {
		
		if ((state==FITNESS)||(len==pos)) {
			state = EOF;
			value="";
			return;
		}
		switch (state) {
		 case  BOF: 
			for (; pos<len; pos++) {
				if (line.charAt(pos)=='p') {
					state= PID;
					pos++;
					value="";
					while (line.charAt(pos)!='(') {
						pos++;
					}
					pos++;
					while (line.charAt(pos)!=')') {
						value=value+line.charAt(pos);
						pos++;
					}
					break;
				}
			};
			break;
		
		 case  PID: 
			for (; pos<len; pos++) {
				if (line.charAt(pos)=='p') { //next parent
					state= BOF;
					nextState();
					break;
				}
				if (line.charAt(pos)=='(') {
					state= ID;
					pos++;
					value="";
					while (line.charAt(pos)!=')') {
						value=value+line.charAt(pos);
						pos++;
					}
					break;
				}
			};
			break;
		 case  ID: 
			for (; pos<len; pos++) {
				if (line.charAt(pos)=='(') {
					state= IN;
					pos++;
					value="";
					while (line.charAt(pos)!=')') {
						value=value+line.charAt(pos);
						pos++;
					}
					value =value.trim();
					break;
				}
			};
			break;	
		 case IN: 			
		 case OPERATOR:
		 case CROSS:
		 case MUTAT:
		 case REPAIR:
				for (; pos<len; pos++) {
					if (line.charAt(pos)=='c') {
						state= CROSS;
						pos++;
						value="";
						while ((pos<len) && (line.charAt(pos)!=' ')) {
							value=value+line.charAt(pos);
							pos++;
						}
						break;
					}
					if (line.charAt(pos)=='m') {
						state= MUTAT;
						pos++;
						value="";
						while ((pos<len) && (line.charAt(pos)!=' ')) {
							value=value+line.charAt(pos);
							pos++;
						}
						break;
					}
					if (line.charAt(pos)=='r') {
						state= REPAIR;
						pos++;
						value="";
						while ((pos<len) && (line.charAt(pos)!=' ')) {
							value=value+line.charAt(pos);
							pos++;
						}
						break;
					}
					if (line.charAt(pos)=='f') {
						state= FITNESS;
						pos++; //f
						pos++; //i
						pos++; //t
						pos++; //(						
						value="";
						while (line.charAt(pos)!=')') {
							value=value+line.charAt(pos);
							pos++;
						}
						break;
					}
				};
				break;	
		}
		
	}
}
