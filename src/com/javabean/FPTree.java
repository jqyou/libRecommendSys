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
 
    // 从若干个文件中读入Transaction Record
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
                                String str[] = line.split("，");
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
 
    // FP-Growth算法
    public void FPGrowth(List<List<String>> transRecords,
            List<String> postPattern) {
        // 构建项头表，同时也是频繁1项集
        ArrayList<TreeNode> HeaderTable = buildHeaderTable(transRecords);
        // 构建FP-Tree
        TreeNode treeRoot = buildFPTree(transRecords, HeaderTable);
        // 如果FP-Tree为空则返回
        if (treeRoot.getChildren()==null || treeRoot.getChildren().size() == 0)
            return;
        //输出项头表的每一项+postPattern
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
        // 找到项头表的每一项的条件模式基，进入递归迭代
        for (TreeNode header : HeaderTable) {
            // 后缀模式增加一项
            List<String> newPostPattern = new LinkedList<String>();
            newPostPattern.add(header.getName());
            if (postPattern != null)
                newPostPattern.addAll(postPattern);
            // 寻找header的条件模式基CPB，放入newTransRecords中
            List<List<String>> newTransRecords = new LinkedList<List<String>>();
            TreeNode backnode = header.getNextHomonym();
            while (backnode != null) {
                int counter = backnode.getCount();
                List<String> prenodes = new ArrayList<String>();
                TreeNode parent = backnode;
                // 遍历backnode的祖先节点，放到prenodes中
                while ((parent = parent.getParent()).getName() != null) {
                    prenodes.add(parent.getName());
                }
                while (counter-- > 0) {
                    newTransRecords.add(prenodes);
                }
                backnode = backnode.getNextHomonym();
            }
            // 递归迭代
            FPGrowth(newTransRecords, newPostPattern);
        }
    }
 
    // 构建项头表，同时也是频繁1项集
    public ArrayList<TreeNode> buildHeaderTable(List<List<String>> transRecords) {
        ArrayList<TreeNode> F1 = null;
        if (transRecords.size() > 0) {
            F1 = new ArrayList<TreeNode>();
            Map<String, TreeNode> map = new HashMap<String, TreeNode>();
            // 计算事务数据库中各项的支持度
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
            // 把支持度大于（或等于）minSup的项加入到F1中
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
 
    // 构建FP-Tree
    public TreeNode buildFPTree(List<List<String>> transRecords,
            ArrayList<TreeNode> F1) {
        TreeNode root = new TreeNode(); // 创建树的根节点
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
 
    // 把交易记录按项的频繁程序降序排列
    public LinkedList<String> sortByF1(List<String> transRecord,
            ArrayList<TreeNode> F1) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String item : transRecord) {
            // 由于F1已经是按降序排列的，
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
                // 降序排列
                return arg0.getValue() - arg1.getValue();
            }
        });
        LinkedList<String> rest = new LinkedList<String>();
        for (Entry<String, Integer> entry : al) {
            rest.add(entry.getKey());
        }
        return rest;
    }
 
    // 把record作为ancestor的后代插入树中
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
