import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Bank {
	Scanner sc=new Scanner(System.in);
	void createAccount()
	{
		try
		{
			System.out.println("Enter your account number");
			int actno=sc.nextInt();
			System.out.println("Enter your name");
			String name=sc.next();
			System.out.println("create your 4 digit password");
			int password=sc.nextInt();
			System.out.println("Enter your Balance");
			int bal=sc.nextInt();
			
			Details d=new Details();
			d.setAccno(actno);
			d.setName(name);
			d.setPass(password);
			d.setBal(bal);
			
			int an=d.getAccno();
			String n=d.getName();
			int p=d.getPass();
			int b=d.getBal();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521","nbatch","nbatch");
			PreparedStatement ps=con.prepareStatement("insert into menubank values(?,?,?,?)");
			ps.setInt(1, an);
			ps.setString(2, n);
			ps.setInt(3, p);
			ps.setInt(4, b);
			int e=ps.executeUpdate();
			System.out.println(e+"registered successfull");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}		
	}
	
	void login()
	{
		try
		{
			System.out.println("Enter your name");
			String name=sc.next();
			System.out.println("Enter your password");
			int password=sc.nextInt();
			
			Details d=new Details();
			
			d.setName(name);
			d.setPass(password);
			
			String n=d.getName();
			int p=d.getPass();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","nbatch","nbatch");
			
			PreparedStatement ps=con.prepareStatement("select * from menubank where name=? and password=?");
			ps.setString(1, n);
			ps.setInt(2, p);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				System.out.println("login succesfull");
				while(true)
				{
					System.out.println("please enter your choice");
					System.out.println("1.balance");
					System.out.println("2.Transfer");
					System.out.println("3.Logout");
					int ch=sc.nextInt();
					if(ch==1)
					{
						System.out.println("your account balance is"+ rs.getInt(4));
					}
					if(ch==2)
					{
						System.out.println("please enter reciever account number");
						int ract=sc.nextInt();
						System.out.println("please enter amount to transfer");
						int tamt=sc.nextInt();
						
						PreparedStatement ps1=con.prepareStatement("select * from menubank where accno=?");
						ps1.setInt(1, ract);
						ResultSet rs1=ps1.executeQuery();
						
						if(rs1.next())
						{
							
							if(tamt<rs.getInt(4))
							{
								
								
								int ammt=rs.getInt(4)-tamt;								
								PreparedStatement ps2=con.prepareStatement("update menubank set balance=? where name=?");
								ps2.setInt(1, ammt);
								ps2.setString(2, n);
								int l=ps2.executeUpdate();
							
								if(l==1)
								{
									System.out.println("balance updated");
								}
								int tammt=rs1.getInt(4)+tamt;
								PreparedStatement ps3=con.prepareStatement("update menubank set balance=? where accno=?");
								ps3.setInt(1, tammt);
								ps3.setInt(2, ract);
								int m=ps3.executeUpdate();
								
								System.out.println("your account balace is "+ ammt	);
								System.out.println("transfered amount is" + tamt);
							}
							else
							{
								System.out.println("insuffiecient amount");
							}
						}
						else
						{
							System.out.println("invalid details");
						}
					}
					if(ch==3)
					{
						break;
					}
				}
				
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}

