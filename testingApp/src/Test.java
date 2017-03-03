import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class Test {

	public final int i;
	
	public Test() {
		this(20);
		//i=10;
	}
	
	public Test(int j) {
		i=10;
	}
	
	/*private DataGrid getMerchantKeys() {
		Hashtable keys=null;
		Connection con=null;		
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		DataGrid dg = new DataGrid();

		try
		{
			con = CCAVUtil.getNewConnection();
			String sqlString = "SELECT A.user_id, A.WorkingKey FROM merchant_reqs A, merchant_registration B WHERE A.user_id=B.user_id AND B.approval='ACTI' AND A.workingKey IS NOT NULL AND A.workingKey<>'NA'";
			pstmt=con.prepareStatement(sqlString);
			rs=pstmt.executeQuery();
			dg.setDataGrid(rs);
			if(rs!=null)
				keys=new Hashtable();
				while (rs.next())
					keys.put(rs.getString("user_id"), rs.getString("WorkingKey"));
		}
		catch (Exception e) {
			CCAVUtil.printLog("test_trans.log", "-----Exception - "+e);
		}
		finally{

			try {
				if (rs!= null)
					rs.close();
			}
			catch(Exception e) {
				rs=null;
			}

			try {
				if (pstmt!= null)
					pstmt.close();
			}
			catch(Exception e) {
				pstmt=null;
			}

			try {
				if (con != null)
					con.close();
			}
			catch(Exception e){
				con=null;
			}
		}
		return dg;
	}
	
	private Hashtable<String, String> getHashtable(DataGrid dg) throws Exception {
		
		Hashtable<String, String> ht=new Hashtable<String, String>();
		for (int i = 0; i < dg.getRowCount(); i++) {
			ht.put(dg.getString(i,"user_id"), dg.getString(i, "WorkingKey"));
		}
		return ht;
	}
	
	private ConcurrentHashMap<String, String> getConcurrentHashMap(DataGrid dg) throws Exception {
		
		ConcurrentHashMap<String, String> chm=new ConcurrentHashMap<String, String>();
		for (int i = 0; i < dg.getRowCount(); i++) {
			chm.put(dg.getString(i,"user_id"), dg.getString(i, "WorkingKey"));
		}
		return chm;
	}*/
	
	public static void main(String[] args) throws Exception {
		Test t = new Test();
		String str = "asgdfhasghdfk";
		char[] arr = str.toCharArray();
		int i=0;
		while(i<str.length()) {
			if(str.substring(0, i).indexOf(String.valueOf(arr[i])) < 0 && str.substring(i+1).indexOf(String.valueOf(arr[i])) < 0) {
				//if(str.lastIndexOf(String.valueOf(arr[i])) == i) {
					System.out.println("First non-repeated character: "+arr[i]);
					break;
				}
			i++;
		}
		int[] binaryArray = {0,0,0,1,0,1,0,1,0,1,1,1,1,1,0,1,0,0,1,0,0,1,1,1,1,0,0,0,1,0,1,0,0};
		int[] sortedArray = sortBinaryArray(binaryArray);
		for(int j=0;j<sortedArray.length;j++)
			System.out.print(sortedArray[j]+",");
	}
	
	public static int[] sortBinaryArray(int[] binaryArray) {
		int begin=0;
		int end=binaryArray.length-1;
		while(begin < end) {
			if(binaryArray[begin] == 0)
				begin++;
			
			if(binaryArray[end]==1)
				end--;
			
			binaryArray[begin]=binaryArray[end];
			binaryArray[end]=1;
		}
		return binaryArray;
	}

}
