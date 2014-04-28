package com.javabean;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
 
public class FPTree {
 
    private int minSuport = 3;
    private ConnectDB connectDB = null;
 
    public FPTree() {
    	connectDB = new ConnectDB();
	}
    
    public int getMinSuport() {
        return minSuport;
    }
 
    public void setMinSuport(int minSuport) {
        this.minSuport = minSuport;
    }
 
    // �����ɸ��ļ��ж���Transaction Record
    public List<List<String>> readTransRocords(String... filenames) {
        List<List<String>> transaction = null;
        if (filenames.length > 0) {
            transaction = new LinkedList<List<String>>();
            for (String filename : filenames) {
                try {
//                    FileReader fr = new FileReader(filename);
//                    BufferedReader br = new BufferedReader(fr);
                	BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8")); 
                    try {
                        String line;
                        List<String> record;
                        while ((line = br.readLine()) != null) {
                            if(line.trim().length()>0){
                                String str[] = line.split("��");
                                record = new LinkedList<String>();
                                for (String w : str)
                                    record.add(w);
                                transaction.add(record);
                            }
                        }
                    } finally {
                        br.close();
                    }
                } catch (IOException ex) {
                    System.out.println("Read transaction records failed."
                            + ex.getMessage());
                    System.exit(1);
                }
            }
        }
        return transaction;
    }
 
    // FP-Growth�㷨
    public void FPGrowth(List<List<String>> transRecords,
            List<String> postPattern) {
        // ������ͷ��ͬʱҲ��Ƶ��1�
        ArrayList<TreeNode> HeaderTable = buildHeaderTable(transRecords);
        // ����FP-Tree
        TreeNode treeRoot = buildFPTree(transRecords, HeaderTable);
        // ���FP-TreeΪ���򷵻�
        if (treeRoot.getChildren()==null || treeRoot.getChildren().size() == 0)
            return;
        //�����ͷ���ÿһ��+postPattern
        if(postPattern!=null){
        	String countString = "0";
        	String oneString = "";
        	String twoString = "";
        	String threeString = "";
        	String foursString = "";
        	String fivesString = "";
        	String sixsString = "";
        	String sevenString = "";
            for (TreeNode header : HeaderTable) {
                System.out.print(header.getCount() + "\t" + header.getName());
                countString = String.valueOf(header.getCount());
                oneString = header.getName();
                int m = 2;
                for (String ele : postPattern) {
                	if (m == 2) {
                		twoString = ele;
                	} else if (m == 3) {
						threeString = ele;
					} else if (m == 4) {
						foursString = ele;
					} else if (m == 5) {
						fivesString = ele;
					} else if (m == 6) {
						sixsString = ele;
					} else if (m == 7) {
						sevenString = ele;
					}
                    System.out.print("\t" + ele);
                    m++;
                }
                System.out.println();
                String sqlString = "insert into libMini(numbers,onebook,twobook,threebook,fourbook,fivebook,sixbook,sevenbook) "
                		+" values(?,?,?,?,?,?,?,?)";
                String[] param = {countString,oneString,twoString,threeString,foursString,fivesString,sixsString,sevenString};
                int[] which = {0,0,0,0,0,0,0,0};
                
                boolean isSuccess = connectDB.updatesql(sqlString, param, which);
             }
        }
        // �ҵ���ͷ���ÿһ�������ģʽ��������ݹ����
        for (TreeNode header : HeaderTable) {
            // ��׺ģʽ����һ��
            List<String> newPostPattern = new LinkedList<String>();
            newPostPattern.add(header.getName());
            if (postPattern != null)
                newPostPattern.addAll(postPattern);
            // Ѱ��header������ģʽ��CPB������newTransRecords��
            List<List<String>> newTransRecords = new LinkedList<List<String>>();
            TreeNode backnode = header.getNextHomonym();
            while (backnode != null) {
                int counter = backnode.getCount();
                List<String> prenodes = new ArrayList<String>();
                TreeNode parent = backnode;
                // ����backnode�����Ƚڵ㣬�ŵ�prenodes��
                while ((parent = parent.getParent()).getName() != null) {
                    prenodes.add(parent.getName());
                }
                while (counter-- > 0) {
                    newTransRecords.add(prenodes);
                }
                backnode = backnode.getNextHomonym();
            }
            // �ݹ����
            FPGrowth(newTransRecords, newPostPattern);
        }
    }
 
    // ������ͷ��ͬʱҲ��Ƶ��1�
    public ArrayList<TreeNode> buildHeaderTable(List<List<String>> transRecords) {
        ArrayList<TreeNode> F1 = null;
        if (transRecords.size() > 0) {
            F1 = new ArrayList<TreeNode>();
            Map<String, TreeNode> map = new HashMap<String, TreeNode>();
            // �����������ݿ��и����֧�ֶ�
            for (List<String> record : transRecords) {
                for (String item : record) {
                    if (!map.keySet().contains(item)) {
                        TreeNode node = new TreeNode(item);
                        node.setCount(1);
                        map.put(item, node);
                    } else {
                        map.get(item).countIncrement(1);
                    }
                }
            }
            // ��֧�ֶȴ��ڣ�����ڣ�minSup������뵽F1��
            Set<String> names = map.keySet();
            for (String name : names) {
                TreeNode tnode = map.get(name);
                if (tnode.getCount() >= minSuport) {
                    F1.add(tnode);
                }
            }
            Collections.sort(F1);
            return F1;
        } else {
            return null;
        }
    }
 
    // ����FP-Tree
    public TreeNode buildFPTree(List<List<String>> transRecords,
            ArrayList<TreeNode> F1) {
        TreeNode root = new TreeNode(); // �������ĸ��ڵ�
        for (List<String> transRecord : transRecords) {
            LinkedList<String> record = sortByF1(transRecord, F1);
            TreeNode subTreeRoot = root;
            TreeNode tmpRoot = null;
            if (root.getChildren() != null) {
                while (!record.isEmpty()
                        && (tmpRoot = subTreeRoot.findChild(record.peek())) != null) {
                    tmpRoot.countIncrement(1);
                    subTreeRoot = tmpRoot;
                    record.poll();
                }
            }
            addNodes(subTreeRoot, record, F1);
        }
        return root;
    }
 
    // �ѽ��׼�¼�����Ƶ������������
    public LinkedList<String> sortByF1(List<String> transRecord,
            ArrayList<TreeNode> F1) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String item : transRecord) {
            // ����F1�Ѿ��ǰ��������еģ�
            for (int i = 0; i < F1.size(); i++) {
                TreeNode tnode = F1.get(i);
                if (tnode.getName().equals(item)) {
                    map.put(item, i);
                }
            }
        }
        ArrayList<Entry<String, Integer>> al = new ArrayList<Entry<String, Integer>>(
                map.entrySet());
        Collections.sort(al, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> arg0,
                    Entry<String, Integer> arg1) {
                // ��������
                return arg0.getValue() - arg1.getValue();
            }
        });
        LinkedList<String> rest = new LinkedList<String>();
        for (Entry<String, Integer> entry : al) {
            rest.add(entry.getKey());
        }
        return rest;
    }
 
    // ��record��Ϊancestor�ĺ����������
    public void addNodes(TreeNode ancestor, LinkedList<String> record,
            ArrayList<TreeNode> F1) {
        if (record.size() > 0) {
            while (record.size() > 0) {
                String item = record.poll();
                TreeNode leafnode = new TreeNode(item);
                leafnode.setCount(1);
                leafnode.setParent(ancestor);
                ancestor.addChild(leafnode);
 
                for (TreeNode f1 : F1) {
                    if (f1.getName().equals(item)) {
                        while (f1.getNextHomonym() != null) {
                            f1 = f1.getNextHomonym();
                        }
                        f1.setNextHomonym(leafnode);
                        break;
                    }
                }
 
                addNodes(leafnode, record, F1);
            }
        }
    }
    
    private List<List<String>> readDB() {
    	List<List<String>> transaction = new LinkedList<List<String>>();
    	
    	String[] param = {};
    	int[] which = {};
    	String sqlString = "select * from libHistory";
    	
    	try {
    		ResultSet record = connectDB.executeQuery(sqlString,param,which);
			while (record.next()) {
				List<String> borrows = new LinkedList<String>();
				
				String oneString = record.getString("one");
				String twoString = record.getString("two");
				String threeString = record.getString("three");
				String foursString = record.getString("four");
				String fiveString = record.getString("five");
				String sixsString = record.getString("six");
				String sevensString = record.getString("seven");
				if (oneString != null) {
					borrows.add(oneString);
				}
				if (twoString != null) {
					borrows.add(twoString);
				}
				if (threeString != null) {
					borrows.add(threeString);
				}
				if (foursString != null) {
					borrows.add(foursString);
				}
				if (fiveString != null) {
					borrows.add(fiveString);
				}
				if (sixsString != null) {
					borrows.add(sixsString);
				}
				if (sevensString != null) {
					borrows.add(sevensString);
				}
				
				if(borrows.size() > 0) {
					transaction.add(borrows);
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return transaction;
	}
    
    public void mini() {
    	String delString = "delete from libMini";
    	boolean delSuccess = connectDB.updatesql(delString, new String[0], new int[0]);
    	List<List<String>> transRecords = readDB();
    	FPGrowth(transRecords, null);
	}
// 
//    public static void main(String[] args) {
//    	System.out.println("start");
//        FPTree fptree = new FPTree();
//        fptree.setMinSuport(3);
//        List<List<String>> transRecords = fptree
//                .readTransRocords("11.txt");
//        fptree.FPGrowth(transRecords, null);
//    }
}
