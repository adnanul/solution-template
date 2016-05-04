package com.tigerit.exam;


import static com.tigerit.exam.IO.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {
	
    @Override
    public void run() {
    	int nTest,nTable;
		ArrayList<String> tableName = new ArrayList<String>();
		ArrayList<String>nColNData = new ArrayList<String>();// coloumn & record
		HashMap<String, String[]>tableInfo = new HashMap<String,String[]>();
		HashMap<String,Integer[][] >tableWithColoumns = new HashMap<String,Integer[][] >();
		int nQuery[] = null ;
		String colSize[];


		HashMap<Integer, ArrayList<String> >queriesList = new HashMap<Integer, ArrayList<String> >();
		HashMap<String, String[] >colSizeList = new HashMap<String,String[] >();

		// test case input
		nTest = IO.readLineAsInteger();
		if(nTest >=1 && nTest <=10){
			// start test case
			for(int i = 0;i<nTest;i++){
				nTable =  IO.readLineAsInteger();
				if(nTable<=2 || nTable>=10)
					return;
				// start table input 
				for(int j= 0;j<nTable;j++){
					tableName.add(IO.readLine());//table name
					nColNData.add(IO.readLine());//nC & nD(row)
					colSize=nColNData.get(j).split(" ",2);
					colSizeList.put(tableName.get(j), colSize);
					if(Integer.parseInt(colSize[0])<2 || Integer.parseInt(colSize[1])>100)
						return;

					// table coloumn names
					String tempCol[] = IO.readLine().split(" ",Integer.parseInt(colSize[0]));					
					tableInfo = new HashMap<String, String[]>();
					tableInfo.put(tableName.get(j), tempCol);
					// coloumn values
					//tableInfo.put(tableName.get(j), tableData.get(j));
					Integer [][]mTemp = new Integer[Integer.parseInt(colSize[0])][Integer.parseInt(colSize[1])];

					for(int c=0;c<Integer.parseInt(colSize[1]);c++)
					{
						String []tempVal = (IO.readLine().split(" ",Integer.parseInt(colSize[0])));
						for(int d=0;d<Integer.parseInt(colSize[0]);d++){

							mTemp[c][d] = Integer.parseInt(tempVal[d]);

							/*	table[c][d] = new ArrayList(); // 
							table[c][d].add(mTemp[c][d]);*/
						}
					}
					tableWithColoumns = new HashMap<String,Integer[][] >();
					tableWithColoumns.put(tableName.get(j), mTemp);



				}//  finish table insert

				nQuery[i] = IO.readLineAsInteger();// query no
				ArrayList<String> s = new ArrayList<String>();
				for(int q=0;q<nQuery[i];q++){
					while(IO.readLine()!="\n\n"){
						s.add(IO.readLine());
					}
				}
				queriesList.put(nQuery[i], s);

				// input is finished
				IO.printLine("Test: "+(i+1));

				for(int z=0;z<nQuery[i];z++ ){
					// all query list in 1st test case
					ArrayList<String> query=queriesList.get(nQuery[i]);
					String alias1,alias2;
					String table1,table2;
					String comp1,comp2;
					int pos1,pos2;
					Boolean isCol = true;
					// query looping 
					for(int y=0;y<query.size();y++){
						//
						String s1 = query.get(y);
						//output
						String s2[] = s1.split("\n",4 );// 4 line separate in array
						//if it is * s2[0]---1st line
						String t0[] = s2[0].split(" ");//1st line query

						//	if(s2[0].contains("*")){
						// if table name is different
						int t ;
						String t1[] = s2[1].split(" ");//2nd line query
						if(t1.length == 3)// with alias
							alias1 = t1[2];
						table1 = t1[1];

						String t2[] = s2[2].split(" ");//3rd line query
						if(t2.length == 3)// with alias
							alias2 = t2[2];
						table2 = t2[1];

						String t3[] = s2[3].split(" ");//4th line query
						comp1 = t3[1];
						comp2 = t3[3];


						// comparing coloumns
						comp1 = comp1.substring(comp1.indexOf('.')+1,comp1.length());
						comp2 = comp2.substring(comp2.indexOf('.')+1,comp2.length());
						String []tempColArray1 = tableInfo.get(table1);
						String []tempColArray2 = tableInfo.get(table2);

						IO.print("\n");
						if(s2[0].contains("*")){

							pos1 = -1;
							for (int p=0;p<tempColArray1.length;p++) {
								if (tempColArray1[p].equals(comp1)) {
									pos1 = p;
									break;
								}
							}
							pos2 = -1;
							for (int p=0;p<tempColArray2.length;p++) {
								if (tempColArray2[p].equals(comp2)) {
									pos2 = p;
									break;
								}
							}
							// output coloumn name
							for(String ss: tempColArray1){
								IO.print(ss+" ");
							}
							for(String ss: tempColArray2){
								IO.print(ss+" ");
							}
							IO.print("\n");
							Integer[][] tableData1 = tableWithColoumns.get(table1);
							String colSize1[] = colSizeList.get(table1);
							Integer[][] tableData2 = tableWithColoumns.get(table2);
							String colSize2[] = colSizeList.get(table2);
							// if the compare is 1st coloumn
							if(comp1.equals(tempColArray1[1]) && comp2.equals(tempColArray2[1])){



								for(int c=0;c<Integer.parseInt(colSize1[1]);c++)
								{
									for(int d=0;d<Integer.parseInt(colSize1[0]);d++){
										if(isCol && d == pos1 && tableData1[c][pos1] == tableData2[c][pos1])
											IO.print(tableData1[c][d] +" ");
										if(d == Integer.parseInt(colSize1[0])-1){
											isCol = false;
											d=0;
										} 
										if(!isCol && d == pos2 && tableData1[c][pos2] == tableData2[c][pos2])
											IO.print(tableData2[c][d] +" ");

									}
									IO.print("\n");
								}


							}

							//////////////////
							else{

								for(int c=0;c<Integer.parseInt(colSize1[1]);c++)
								{
									for(int d=0;d<Integer.parseInt(colSize1[0]);d++){
										if(isCol && d == pos1 && tableData1[c][pos1] == tableData2[c][pos1])
											IO.print(tableData1[c][d] +" ");
										if(d == Integer.parseInt(colSize1[0])-1){
											isCol = false;
											d=0;
										} 
										if(!isCol && d == pos2 && tableData1[c][pos2] == tableData2[c][pos2])
											IO.print(tableData2[c][d] +" ");

									}
									IO.print("\n");
								}

							}
						}else{
							// not * out[] 
							//output[] ;
							isCol = true;
							String output[] = t0[1].split(",");
							ArrayList<Integer> poss1=new ArrayList<Integer>();
							ArrayList<Integer> poss2=new ArrayList<Integer>();
							ArrayList<String>oo = new ArrayList<String>();
							for(String st:output){
								String sd = st.substring(st.indexOf('.')+1,st.length());
								IO.print(sd);
								oo.add(sd);

							}

							for (int p=0;p<tempColArray1.length;p++) {
								if (tempColArray1[p].equals(oo.get(p))) {
									poss1.add(p);
									break;
								}
							}
							for (int p=0;p<tempColArray2.length;p++) {
								if (tempColArray2[p].equals(oo.get(p))) {
									poss2.add(p);
									break;
								}
							}
							IO.print("\n");
							Integer[][] tableData1 = tableWithColoumns.get(table1);
							String colSize1[] = colSizeList.get(table1);
							Integer[][] tableData2 = tableWithColoumns.get(table2);
							String colSize2[] = colSizeList.get(table2);

							for(int c=0;c<Integer.parseInt(colSize1[1]);c++)
							{int p = 0;
							for(int d=0;d<Integer.parseInt(colSize1[0]);d++){

								if(isCol && d == poss1.get(p) && tableData1[c][poss1.get(p)] == tableData2[c][poss1.get(p)]){
									if(tableData1[c][d] != tableData1[c][poss1.get(p)])
										IO.print(tableData1[c][d] +" ");
								}
								if(d == Integer.parseInt(colSize1[0])-1){
									isCol = false;
									d=0;
								} 
								if(!isCol && d == poss1.get(p) && tableData1[c][poss2.get(p)] == tableData2[c][poss1.get(p)]){
									if(tableData2[c][d] != tableData2[c][poss2.get(p)])
										IO.print(tableData2[c][d] +" ");
								}
								p++;

							}
							IO.print("\n");
							}
						}


					}

				}
			}

		}
       
    }
}
