import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class HTTPServer extends Thread {
    private final int PORT = 2222; 
    private enum Choice {Select, insert, retrieve, update, delete, exit};
    Choice choice = Choice.Select;
    boolean start=true;
    
//    public static void main(String[] args) {
//        MiniPbxManServer gtp = new MiniPbxManServer();
//        gtp.start();
//    }    
    @SuppressWarnings("removal")
	public void run() {
        try {
        	//Connection conn = ConnectionClass.connect();
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("MiniServer active "+PORT);
            boolean shudown = true;
            while (shudown && choice!=Choice.exit) {  
            	            	
                Socket socket = server.accept();                
                InputStream is = socket.getInputStream();
                PrintWriter out = new PrintWriter(socket.getOutputStream());            
                BufferedReader in = new BufferedReader(new InputStreamReader(is));              
                String line;
                line = in.readLine();
                //String auxLine = line;
                line = "";
                // looks for post data
                int postDataI = -1;
                while ((line = in.readLine()) != null && (line.length() != 0)) {
                    System.out.println(line);
                    if (line.indexOf("Content-Length:") > -1) {
                        postDataI = new Integer(line
                                .substring(
                                        line.indexOf("Content-Length:") + 16,
                                        line.length())).intValue();
                    }
                }
                String postData = "";
                for (int i = 0; i < postDataI; i++) {
                    int intParser = in.read();
                    postData += (char) intParser;
                }
                out.println("HTTP/1.0 200 OK");
                out.println("Content-Type: text/html; charset=utf-8");
                out.println("Server: MINISERVER");
                // this blank line signals the end of the headers
                out.println("");
                // Send the HTML page 
                //out.println("<H2>Post->"+postData+ "</H2>");
                
                if(postData.contains("continue")) {
                	String toContinue=postData.substring(9, postData.length());
                	if(toContinue.equals("Y")) {
                		switch(choice) {
                			case insert:
                				out.println("<H2>Enter new book details</H2>");
                				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
                                out.println("ISBN: <input type=\"text\" name=\"isbn\"> Title: <input type=\"text\" name=\"title\"> Year Of Publication: <input type=\"text\" name=\"year\"> Author: <input type=\"text\" name=\"author\"> <input type=\"submit\" value=\"Submit\"></form>");
                				break;
                			case update:
                				out.println("<H2>Enter book details to update</H2>");
                				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
                                out.println("ISBN: <input type=\"text\" name=\"isbn\"> Title: <input type=\"text\" name=\"title\"> Year Of Publication: <input type=\"text\" name=\"year\"> Author: <input type=\"text\" name=\"author\"> <input type=\"submit\" value=\"Submit\"></form>");
                				break;
                			case retrieve:
                				out.println("<H2>Specify book to get details</H2>");
                				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
                                out.println("ISBN: <input type=\"text\" name=\"isbn\"> <input type=\"submit\" value=\"Submit\"></form>");
                                out.println("<H2>Or..</H2>");
                				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
                                out.println("Get All books?(Y/N): <input type=\"text\" name=\"getall\"> <input type=\"submit\" value=\"Submit\"></form>");
                				break;
                			case delete:
                				out.println("<H2>Specify book to delete</H2>");
                				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
                                out.println("ISBN: <input type=\"text\" name=\"isbn\"> <input type=\"submit\" value=\"Submit\"></form>");
                                break;
                			default:
                                break;
                		}
                	}
                	else {
                		choice = Choice.Select;
                		out.println("<H2>Choose CRUD operation:- 1 to insert, 2 to retrieve, 3 to update, 4 to delete and 5 to exit </H2>");
        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
                        out.println("Enter choice: <input type=\"text\" name=\"choice\"> <input type=\"submit\" value=\"Submit\"></form>");
                		
                	}
                }
                else if(postData.contains("choice")) {
                	int toChoose=Integer.parseInt(postData.substring(7, postData.length()));
            		switch(toChoose) {
	        			case 1:
	        				choice = Choice.insert;
	        				out.println("<H2>Enter new book details</H2>");
	        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
	                        out.println("ISBN: <input type=\"text\" name=\"isbn\"> Title: <input type=\"text\" name=\"title\"> Year Of Publication: <input type=\"text\" name=\"year\"> Author: <input type=\"text\" name=\"author\"> <input type=\"submit\" value=\"Submit\"></form>");
	        				break;
	        			case 3:
	        				choice = Choice.update;
	        				out.println("<H2>Enter book details to update</H2>");
	        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
	                        out.println("ISBN: <input type=\"text\" name=\"isbn\"> Title: <input type=\"text\" name=\"title\"> Year Of Publication: <input type=\"text\" name=\"year\"> Author: <input type=\"text\" name=\"author\"> <input type=\"submit\" value=\"Submit\"></form>");
	        				break;
	        			case 2:
	        				choice = Choice.retrieve;
	        				out.println("<H2>Specify book ISBN to retrieve particular book details</H2>");
	        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
	                        out.println("ISBN: <input type=\"text\" name=\"isbn\"> <input type=\"submit\" value=\"Submit\"></form>");
	                        out.println("<H2>Or..</H2>");
            				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
                            out.println("Get All books?(Y/N): <input type=\"text\" name=\"getall\"> <input type=\"submit\" value=\"Submit\"></form>");
	        				break;
	        			case 4:
	        				choice = Choice.delete;
	        				out.println("<H2>Specify book to delete</H2>");
	        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
	                        out.println("ISBN: <input type=\"text\" name=\"isbn\"> <input type=\"submit\" value=\"Submit\"></form>");
	                        break;
	        			case 5:
	        				choice = Choice.exit;
	        				ConnectionClass.closeOpenConnection();
	        				out.println("<H2>GoodBye</H2>");
	                        break;
                        default:
                        	choice = Choice.exit;
	        				ConnectionClass.closeOpenConnection();
	        				out.println("<H2>Invalid input</H2>");
	        				break;
                        	
            		}                
                }
                else if(postData.length()>0 || start) {
                	System.out.println("PostData is "+postData+"////////////////////////////////////////////////////////");
                	String[] vals = postData.split("&");
                	int rowsUpdated;
            		switch(choice) {
	        			case Select:
	        				start=false;
	        				out.println("<H2>Choose CRUD operation:- 1 to insert, 2 to retrieve, 3 to update, 4 to delete and 5 to exit </H2>");
	        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
	                        out.println("Enter choice: <input type=\"text\" name=\"choice\"> <input type=\"submit\" value=\"Submit\"></form>");
	                        break;
	        			case insert:        				
	        				//MainClass.create(conn, Integer.parseInt(vals[0]), vals[1], vals[2], vals[3]);
	        				rowsUpdated = MainClass.create(Integer.parseInt(vals[0].split("=")[1]), vals[1].split("=")[1].replace('+', ' '), vals[2].split("=")[1], vals[3].split("=")[1].replace('+', ' '));
	        				if(rowsUpdated>0) {
		        				out.println("<H2><I>Done...</I></H2>");
		        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
		                        out.println("Enter more books(Y/N): <input type=\"text\" name=\"continue\"> <input type=\"submit\" value=\"Submit\"></form>");
	        				}
	        				else {
	        					out.println("<H2><I>Sorry, operation failed. Book probably exists or is invalid...</I></H2>");
		        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
		                        out.println("Try again(Y/N): <input type=\"text\" name=\"continue\"> <input type=\"submit\" value=\"Submit\"></form>");
	        				}
	        				break;	        				
	        			case update:
	        				//MainClass.update(conn, Integer.parseInt(vals[0]), Integer.parseInt(vals[1]), vals[2], vals[3], vals[4]);
	        				rowsUpdated = MainClass.update(Integer.parseInt(vals[0].split("=")[1]), vals[1].split("=")[1].replace('+', ' '), vals[2].split("=")[1], vals[3].split("=")[1].replace('+', ' '));
	        				if(rowsUpdated>0) {
		        				out.println("<H2><I>Done...</I></H2>");
		        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
		                        out.println("Update more books(Y/N): <input type=\"text\" name=\"continue\"> <input type=\"submit\" value=\"Submit\"></form>");
	        				}
	        				else {
	        					out.println("<H2><I>Sorry, this book doesn't exist...</I></H2>");
		        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
		                        out.println("Try again(Y/N): <input type=\"text\" name=\"continue\"> <input type=\"submit\" value=\"Submit\"></form>");	        					
	        				}
	        				break;
	        			case retrieve:
	        				//String toDisplay[] = MainClass.retrieve(conn, Integer.parseInt(vals[0]));
	        				if(postData.contains("getall=Y"))
	        				{
	        					StringBuffer tableHTML = new StringBuffer("<TABLE Border=\"1\" cellpadding=\"8\" cellspacing=\"3\"><TR style=\"font-weight:bold\"><TD>ISBN</TD> <TD>Title</TD> <TD>Year of Publication</TD> <TD>Author</TD></TR>");
	        					List<String[]> toDisplay = MainClass.retrieve();
	        					for(String[] rowDisplay : toDisplay) {
	        						tableHTML.append("<TR><TD>"+rowDisplay[0]+"</TD><TD>"+rowDisplay[1]+"</TD> <TD>"+rowDisplay[2]+"</TD> <TD>"+rowDisplay[3]+"</TD></TR>");
	        					}
	        					tableHTML.append("</TR></TABLE>");
	        					out.println(tableHTML);
	        					out.println("<H2 />");
	        					out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
		                        out.println(" Retrieve more books(Y/N): <input type=\"text\" name=\"continue\"> <input type=\"submit\" value=\"Submit\"></form>");
	        				}
	        				else {
		        				String toDisplay[] = MainClass.retrieve(Integer.parseInt(vals[0].split("=")[1]));
		        				if(toDisplay[0]!=null) {
			        				out.println("<H2>The book with ISBN "+vals[0].split("=")[1]+" are :</H2>");
			        				out.println("<TABLE Border=\"1\" cellpadding=\"10\" cellspacing=\"5\"><TR style=\"font-weight:bold\"><TD>ISBN</TD> <TD>Title</TD> <TD>Year of Publication</TD> <TD>Author</TD></TR><TR><TD>"+toDisplay[0]+"</TD><TD>"+toDisplay[1]+"</TD> <TD>"+toDisplay[2]+"</TD> <TD>"+toDisplay[3]+"</TD></TR></TABLE>");
			        				out.println("<H2 />");
			        				//out.println("<H2>Title: "+toDisplay[0]+"   Year of Publication: "+toDisplay[1]+"   Author: "+toDisplay[2]+"</H2>");
			        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
			                        out.println(" Retrieve more books(Y/N): <input type=\"text\" name=\"continue\"> <input type=\"submit\" value=\"Submit\"></form>");
		        				}
		        				else {
		        					out.println("<H2><I>Sorry, this book doesn't exist...</I></H2>");
			        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
			                        out.println(" Try again(Y/N): <input type=\"text\" name=\"continue\"> <input type=\"submit\" value=\"Submit\"></form>");
		        				}
	        				}
	        				break;
	        			case delete:
	        				//MainClass.delete(conn, Integer.parseInt(vals[0]));
	        				//String[] vals2 = vals[0].split("=");
	        				rowsUpdated = MainClass.delete(Integer.parseInt(vals[0].split("=")[1]));
	        				if(rowsUpdated>0) {
		        				out.println("<H2><I>Done...</I></H2>");
		        				out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
		                        out.println("Delete more books(Y/N): <input type=\"text\" name=\"continue\"> <input type=\"submit\" value=\"Submit\"></form>");
	        				}
	        				else {
	        					out.println("<H2><I>Sorry, this book doesn't exist...</I></H2>");
	        					out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
	        					out.println("Try again(Y/N): <input type=\"text\" name=\"continue\"> <input type=\"submit\" value=\"Submit\"></form>");
	        				}
	        					
	                        break;
	        			default:
	                        break;
	        		}
                	
                }
                
                              
                /*out.println("<H1>Welcome to the Mini PbxMan server</H1>");
                out.println("<H2>GET->"+auxLine+ "</H2>");        
                out.println("<H2>Post->"+postData+ "</H2>");
                out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
                out.println("Username: <input type=\"text\" name=\"user\"> Password: <input type=\"text\" name=\"pass\"> <input type=\"submit\" value=\"Submit\"></form>");  */
                //if your get parameter contains shutdown it will shutdown
                /*if(auxLine.indexOf("?shutdown")>-1){
                    shudown = false;
                }*/
                out.close();
                socket.close();
            }
            server.close();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}