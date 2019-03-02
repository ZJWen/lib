package library;

import java.util.Calendar;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Function {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rr =null;
	int j = 0;
	
	@SuppressWarnings("unused")
	public void add(){
		boolean flag=true;
		System.out.print("请输入图书名：");
		Scanner input=new Scanner(System.in);
		String books=input.next();
		//System.out.print("请输入数量：");
		int number=1;
		int count=0;
		try {
			//获得数据连接
			conn=JDBC.getConnection();
			//获得Statement对象
			stmt =conn.createStatement();
			//发送SQL语句
			String sql="insert into book values(?,?,?,?,?)";
			String sql1="select * from book";
			rr=stmt.executeQuery(sql1);
			
			PreparedStatement pst=conn.prepareStatement(sql);
			while(rr.next()){
				count=rr.getInt("id");
				if(books.equalsIgnoreCase(rr.getString("bookname"))){
					System.out.println("该书已存在！，添加失败");
					flag=false;
				}
			}
			if(flag==true){
				pst.setInt(1, count+1);
				pst.setString(2, books);
				pst.setInt(3, 1);
				pst.setInt(4, number);
				pst.setString(5, null);
				pst.executeUpdate();
				System.out.println("添加《"+books+"》书籍成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void search(){
		System.out.println("--->查看图书\n");
		System.out.println("id\tbookname\tstate\tnumber\tlendtime");
		
		try {
		// 获得数据的连接
		conn = JDBC.getConnection();
		// 获得Statement对象
		stmt = conn.createStatement();
		// 发送SQL语句
	    String sql = "select * from book";
	    rr = stmt.executeQuery(sql);
		
		while (rr.next()) {
			// 通过列名获取指定字段的值
			int i=rr.getInt("id");
			String bookname = rr.getString("bookname");
			int state = rr.getInt("state");
			String date = rr.getString("lendtime");
			int number = rr.getInt("number");
			System.out.println(i+ "\t" +bookname+ " \t\t " + state + " \t " + number+ "\t" +  date
						);
		}
	    
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	}	
	
	public void delete(){
		System.out.print("请输入删除的图书名：");
		Scanner input=new Scanner(System.in);
		String delbooks=input.next();
		boolean flag=false;
		try {
			conn=JDBC.getConnection();
			stmt=conn.createStatement();
			String sql2="select * from book";
			String sql="delete from book where bookname=?";
			rr=stmt.executeQuery(sql2);
			while(rr.next()){
				if(rr.getString("bookname").equalsIgnoreCase(delbooks)){
					PreparedStatement pst=conn.prepareStatement(sql);
					pst.setString(1, delbooks);
					pst.executeUpdate();
					System.out.println("删除成功！");
					flag=true;
					break;
				}
			}
			if(flag==false){
				System.out.println("数据库中没有该书，不需要删除");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void lend(){
		System.out.print("请输入借的图书名：");
		Scanner input=new Scanner(System.in);
		String lendbooks=input.next();
		boolean flag=false;
		try {
			conn=JDBC.getConnection();	
			stmt=conn.createStatement();
			String sql3="select * from book";
			String sql ="update book set number=0 where bookname=?";
			String sql1 ="update book set state=0 where bookname=?";
			rr=stmt.executeQuery(sql3);
			while(rr.next()){
				if(rr.getString("bookname").equalsIgnoreCase(lendbooks)&&rr.getInt("state")==1){
						PreparedStatement pst=conn.prepareStatement(sql);
						PreparedStatement pst1=conn.prepareStatement(sql1);
						pst.setString(1, lendbooks);
						pst1.setString(1, lendbooks);
						pst1.executeUpdate();
						pst.executeUpdate();
						Date day=new Date();
						SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String lenddate=df.format(day);
						String sql2 ="update book set lendtime=? where bookname=?";
						PreparedStatement pst2=conn.prepareStatement(sql2);
						pst2.setString(1, lenddate);
						pst2.setString(2, lendbooks);
						pst2.executeUpdate();
						System.out.println("《"+lendbooks+"》借出成功");
						flag=true;
				}
			}
			if(flag==false){
				System.out.println("没有该书，或该书已被借出!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void returnbook(){
		System.out.print("请输入归还图书名：");
		Scanner input=new Scanner(System.in);
		String lendbooks=input.next();
		try {
			conn=JDBC.getConnection();	
			String sql ="update book set number=1 where bookname=?";
			String sql1 ="update book set state=1 where bookname=?";
			PreparedStatement pst=conn.prepareStatement(sql);
			PreparedStatement pst1=conn.prepareStatement(sql1);
			pst.setString(1, lendbooks);
			pst1.setString(1, lendbooks);
			pst1.executeUpdate();
			pst.executeUpdate();
			String sql2 ="update book set lendtime=? where bookname=?";
			PreparedStatement pst2=conn.prepareStatement(sql2);
			pst2.setString(1, null);
			pst2.setString(2, lendbooks);
			pst2.executeUpdate();
			System.out.println("归还书籍《"+lendbooks+"》成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
