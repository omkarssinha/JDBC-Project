import java.sql.*;
public class MainClass {

	public static void main(String[] args) {
		
        
		
        try {
        	HTTPServer gtp = new HTTPServer();
            gtp.start();



		
//			Connection conn = ConnectionClass.connect();
//			String createTableQuery = "create table library(ISBN int PRIMARY KEY, Title varchar(100), Year_of_publication varchar(4), Author varchar(50))";
//			Statement stmt = conn.createStatement();
//			try {			
//				stmt.executeUpdate(createTableQuery);
//				System.out.println("Library table created");
//			} catch(Exception e) {
//				System.out.println("Library table already exists");
//			}
//			
//			
//			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
//			System.out.println("Choose CRUD operation:- 1 to insert, 2 to retrieve, 3 to update, 4 to delete and 5 to exit ");
//			String choice=br.readLine();
//			while(!choice.equals("5"))
//			{
//				switch(choice)
//				{
//					case "1":
//						create(br, conn);
//						break;
//						
//					case "2":
//						retrieve(br, conn);
//						break;
//						
//					case "3":
//						update(br, conn);
//						break;
//					
//					case "4":
//						delete(br, conn);
//						break;
//						
//					default:
//						System.out.println("Invalid choice. Please select 1-5");
//						break;
//						
//				}
//				System.out.println("Choose CRUD operation:- 1 to insert, 2 to retrieve, 3 to update, 4 to delete and 5 to exit ");
//				choice = br.readLine();
//			}
//			System.out.println("Goodbye!");
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void create(int ISBN, String title, String year, String author)
	{
		try {
			Connection conn = ConnectionClass.connect();
			String addBookQuery = "insert into library(ISBN, Title, Year_of_publication, Author) values(?,?,?,?)";						
			PreparedStatement pstmt = conn.prepareStatement(addBookQuery);
//			System.out.println("Enter book ISBN?");
//			int ISBN = Integer.parseInt(br.readLine());
//			System.out.println("Enter book Title?");
//			String title = br.readLine();
//			System.out.println("Enter year of publication?");
//			String year = br.readLine();
//			System.out.println("Enter author name?");
//			String author = br.readLine();
			
			pstmt.setInt(1, ISBN);
			pstmt.setString(2,  title);
			pstmt.setString(3, year);
			pstmt.setString(4, author);
			
			pstmt.executeUpdate();
			
//			System.out.println("Done.. \nEnter more books? (Y/N)");
//			if(br.readLine().equals("Y"))
//				create(br, conn);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String[] retrieve(int isbn)
	{
		String[] vals = new String[3];
		try {
			Connection conn = ConnectionClass.connect();
			String selectBookQuery = "select * from library where ISBN=?";
			
			PreparedStatement pstmt3 = conn.prepareStatement(selectBookQuery);
//			System.out.println("Enter book ISBN to select book  ");
//			int isbn = Integer.parseInt(br.readLine());
			
			pstmt3.setInt(1, isbn);
			
			ResultSet set = pstmt3.executeQuery();
			while(set.next()) {
				vals[0] = set.getString(2);
				vals[1] = set.getString(3);
				vals[2] = set.getString(4);
//				System.out.println("The required details are: ");
//				System.out.println(isbn+" "+title+" "+year+" "+author);
			}
			
//			System.out.println("Do you want to retrieve and view more books?(Y/N)");
//			if(br.readLine().equals("Y"))
//				retrieve(br, conn);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return vals;
	}
	
	public static void update(int isbn, int newIsbn, String newTitle, String newYear, String newAuthor)
	{
		try {
			Connection conn = ConnectionClass.connect();
			String updateBookQuery = "update library set ISBN=?, Title=?, Year_of_publication=?, Author=? where ISBN=?";		
			
			PreparedStatement pstmt1 = conn.prepareStatement(updateBookQuery);
//			System.out.println("Identify book to change with its current ISBN ");
//			int isbn = Integer.parseInt(br.readLine());
//			System.out.println("Enter book ISBN to change  ");
//			int newIsbn = Integer.parseInt(br.readLine());
//			System.out.println("Enter book title to change  ");
//			String newTitle = br.readLine();
//			System.out.println("Enter year of publication to change  ");
//			String newYear = br.readLine();
//			System.out.println("Enter author name to change  ");
//			String newAuthor = br.readLine();
			
			pstmt1.setInt(1, newIsbn);
			pstmt1.setString(2, newTitle);
			pstmt1.setString(3, newYear);
			pstmt1.setString(4, newAuthor);
			pstmt1.setInt(5, isbn);
			
			pstmt1.executeUpdate();
//			System.out.println("Done... \nDo want to update more?(Y/N)");
//			if(br.readLine().equals("Y"))
//				update(br, conn);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(int isbn)
	{
		try {
			Connection conn = ConnectionClass.connect();
			String deleteBookQuery = "delete from library where ISBN=?";		
			
			PreparedStatement pstmt2 = conn.prepareStatement(deleteBookQuery);
//			System.out.println("Enter book ISBN to delete  ");
//			int isbn = Integer.parseInt(br.readLine());
			
			pstmt2.setInt(1, isbn);
			
			pstmt2.executeUpdate();
//			System.out.println("Done... \nDo you delete more?(Y/N)");
//			if(br.readLine().equals("Y"))
//				delete(br, conn);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
