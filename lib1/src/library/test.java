package library;

import java.util.Scanner;

public class test {
	public static void main(String []args){
		BookMgr test1=new BookMgr();
		Function fc=new Function();

		int i=1;
		while (i==test1.printMenu1()) {
			Scanner input =new Scanner(System.in);
			int in=input.nextInt();
			switch (in) {
			case 1:
				fc.add();
				break;
			case 2:
				fc.search();
				break;
			case 3:
				fc.delete();
				break;
			case 4:
				fc.lend();
				break;
			case 5:
				fc.returnbook();
				break;
			case 6:
				System.out.println("退出成功！");
				break;
			default:
				System.out.println("请正确选择1~6");
				break;
			}
		}
			
		}
		
}
