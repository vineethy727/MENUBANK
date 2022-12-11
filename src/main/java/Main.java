import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("welcome to Menu Bank");
		while(true)
		{
			System.out.println("please enter your choice");
			System.out.println("1.create account");
			System.out.println("2.login");
			int ch=sc.nextInt();
			
			Bank b=new Bank();
			if(ch==1)
			{
				b.createAccount();
			}
			if(ch==2)
			{
				b.login();
			}
		}
	}
}
