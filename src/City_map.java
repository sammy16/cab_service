import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author SAURABHHH
 */
class City_map extends javax.swing.JFrame implements MouseListener,Runnable {
	/**
	 * 
	 */
	private int x, y, flag = 0, len = 0;
	private int[][] adj = new int[1000][1000];
	public int[] v1 = new int[1000];
	public int[] v2 = new int[1000];
	/**
	 * Creates new form City_map
	 */
	private javax.swing.JPanel jPaneL2;

	public City_map() throws Exception {
		initComponents();
		start();
	}

	public void actionPerformed(ActionEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	public void mouseReleased(MouseEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	public class imagepanel extends JPanel {
		public imagepanel() {
			setBackground(Color.white);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// draw circle outline
			try {
				FileReader fr1 = new FileReader("points.txt");
				BufferedReader br1 = new BufferedReader(fr1);
				FileReader fr2 = new FileReader("lines.txt");
				BufferedReader br2 = new BufferedReader(fr2);
				String s = new String();
				len = 0;
				while ((s = br1.readLine()) != null) {
					String[] words = s.split(" ");
					g.setColor(Color.black);
					g.drawOval(Integer.parseInt(words[0]),
							Integer.parseInt(words[1]), 20, 20);

					// set color to RED
					// So after this, if you draw anything, all of it's result
					// will be RED
					g.setColor(Color.RED);

					// fill circle with RED
					g.fillOval(Integer.parseInt(words[0]),
							Integer.parseInt(words[1]), 20, 20);
					g.setColor(Color.black);
					g.drawString(words[2], Integer.parseInt(words[0]),
							Integer.parseInt(words[1]));
					len++;
				}
				br1.close();
				fr1.close();

				for (int i = 0; i < len; i++) {
					for (int j = 0; j < len; j++)
						adj[i][j] = 0;
				}
				while ((s = br2.readLine()) != null) {
					String[] words = s.split(" ");
					int c = -1, d = -1;
					g.setColor(Color.blue);
					g.drawLine(Integer.parseInt(words[0]),
							Integer.parseInt(words[1]),
							Integer.parseInt(words[2]),
							Integer.parseInt(words[3]));
					int x1 = Integer.parseInt(words[0]);
					int y1 = Integer.parseInt(words[1]);
					int x2 = Integer.parseInt(words[2]);
					int y2 = Integer.parseInt(words[3]);
					// System.out.println(x1+" "+y1+" "+x2+" "+y2);
					g.setColor(Color.black);
					g.drawString(words[4]+" Kms", (x1+x2)/2, (y1+y2)/2);
					int a, b, len1 = 0;
					String s1 = new String();
					fr1 = new FileReader("points.txt");
					br1 = new BufferedReader(fr1);
					while ((s1 = br1.readLine()) != null) {
						String[] words1 = s1.split(" ");
						a = Integer.parseInt(words1[0]);
						b = Integer.parseInt(words1[1]);
						if (x1 < a + 20 && x1 > a - 20 && y1 < b + 20
								&& y1 > b - 20)
							c = len1;
						if (x2 < a + 20 && x2 > a - 20 && y2 < b + 20
								&& y2 > b - 20)
							d = len1;
						len1++;
						
					}
					br1.close();
					fr1.close();
					adj[c][d] = Integer.parseInt(words[4]);
					adj[d][c] = Integer.parseInt(words[4]);
					// set color to RED
					// So after this, if you draw anything, all of it's result
					// will be RED

				}
				FileWriter fw = new FileWriter("graph.txt");
				fw.write(len+"\n");
				for (int i = 0; i < len; i++) {
					fw.write(adj[i][0] + "");
					for (int j = 1; j < len; j++)
						fw.write(" "+adj[i][j]);
					fw.write("\n");
				}
				fw.close();
				br1.close();
				fr1.close();
				br2.close();
				fr2.close();
			} catch (FileNotFoundException ex) {
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println("success1");
				Connection con = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/cab_service", "root",
						"dynamic");
				if (con != null) {
					System.out
							.println("A database connection has been established!");
				}
				System.out.println("success2");
				Statement st = con.createStatement();
				String s1 = "select * from vehicles";
				ResultSet rs = st.executeQuery(s1);
				int i = 0;
				while (rs.next()) {
					
					g.setColor(Color.black);
					g.drawRect(v1[i], v2[i], 10, 10);
					
					if(rs.getInt("status")==1)
						g.setColor(Color.green);
					else
						g.setColor(Color.gray);
					g.fillRect(v1[i], v2[i], 10, 10);
					g.setColor(Color.black);
					g.drawString(rs.getString("regno"), v1[i]+10, v2[i]+15);
					i++;
				}
				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jPanel2 = new imagepanel();
		jButton1 = new javax.swing.JButton();
		jRadioButton1 = new javax.swing.JRadioButton();
		jTextField1 = new javax.swing.JTextField();
		jRadioButton2 = new javax.swing.JRadioButton();
		jTextField2 = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel1.setFont(new java.awt.Font("Monotype Corsiva", 0, 24)); // NOI18N
		jLabel1.setText("CITY   MAP");

		jPanel2.setBackground(new java.awt.Color(255, 255, 255));
		jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(
				0, 0, 0), 1, true));

		org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(0, 555,
				Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(0, 389,
				Short.MAX_VALUE));

		jButton1.setBackground(new java.awt.Color(255, 204, 0));
		this.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  FileWriter fw;
				try {
					fw = new FileWriter("city.txt");
					 fw.write("new");
			    	  fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	 
		    	  
		      }
		    });
		jButton1.setText("BACK");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				  FileWriter fw;
					try {
						fw = new FileWriter("city.txt");
						 fw.write("old");
				    	  fw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	 
				try {

					new Admin_Panel().setVisible(true);
				} catch (Exception ex) {
					Logger.getLogger(City_map.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});
		jRadioButton1.setText("Add Location");
		jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				// jRadioButton1MouseClicked(evt);
			}
		});
		jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton1ActionPerformed(evt);
			}
		});

		jTextField1.setText("");
		jTextField1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField1ActionPerformed(evt);
			}
		});
		jTextField2.setText("");
		jRadioButton2.setText("Add Road");
		jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jRadioButton2MouseClicked(evt);
			}
		});

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(241, 241, 241)
                        .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(39, 39, 39)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(layout.createSequentialGroup()
                                .add(jRadioButton1)
                                .add(18, 18, 18)
                                .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(81, 81, 81)
                                .add(jRadioButton2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(51, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(234, 234, 234))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(0, 2, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jRadioButton1)
                            .add(jRadioButton2)
                            .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jTextField1))
                .add(15, 15, 15)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
		pack();
		jPanel2.repaint();
		jPanel2.addMouseListener(this);
	}// </editor-fold>

	private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here

	}

	private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {
		// TODO add your handling code here:
		jPanel2.addMouseListener(this);
	}

	private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		System.out.println("You clicked the button");
		jPanel2.addMouseListener(this);

	}
	int c=0,d=0;
	public void mouseClicked(MouseEvent e) {

		if (jRadioButton2.isSelected() == true) {
			Point p = e.getPoint();
			if (p.x != c && p.y != d){
				flag++;
			System.out.println(e.getPoint());
			System.out.println("value of flag:" + flag);
			if (flag==1) {
				c = p.x;
				d = p.y;
			}
			if (flag==2) {
				jRadioButton2.setSelected(false);
				flag = 0;
				
				FileWriter fw;
				try {
					FileReader fr1 = new FileReader("points.txt");
					BufferedReader br1 = new BufferedReader(fr1);
					fw = new FileWriter("lines.txt", true);
					fw.write(c + " " + d + " " + p.x + " " + p.y + " "+jTextField2.getText()+"\n");
					fw.close();
					jTextField2.setText("");
					br1.close();
					fr1.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				jPanel2.repaint();
			}
			}
		}
		if (jRadioButton1.isSelected() == true) {
			Point p = e.getPoint();
			x = p.x;
			y = p.y;
			try {
				FileWriter fw = new FileWriter("points.txt", true);
				fw.write((x-10) + " " + (y-10) + " " + jTextField1.getText() + "\n");
				fw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			jPanel2.repaint();
			System.out.println(e.getPoint());
			String s1 = jTextField1.getText();

			jRadioButton1.setSelected(false);
			jTextField1.setText("");
		}
	}

	public void mousePressed(MouseEvent e) {

		// throw new UnsupportedOperationException("Not supported yet.");

	}

	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	/**
	 * @param args
	 *            the command line arguments
	 */

	// Variables declaration - do not modify
	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JRadioButton jRadioButton1;
	private javax.swing.JRadioButton jRadioButton2;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	// End of variables declaration

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(City_map.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(City_map.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(City_map.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(City_map.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	while(true){
		this.repaint();
			System.out.println("Again in upper loop");
		String[] src=new String[1000];
		String[] dest=new String[1000];
		int[] book_id=new int[1000];
		try {
			System.out.println("success1");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cab_service", "root",
					"dynamic");
			if (con != null) {
				System.out
						.println("A database connection has been established!");
			}
			System.out.println("success2");
			Statement st = con.createStatement();
			String s1 = "select * from vehicles";
			ResultSet rs = st.executeQuery(s1);
			int i = 0;
			while (rs.next()) {
				FileReader fr1 = new FileReader("points.txt");
				BufferedReader br1 = new BufferedReader(fr1);
				String s=new String();
				while ((s = br1.readLine()) != null) {
						String[] words = s.split(" ");
						if(rs.getString("start_place").compareTo(words[2])==0){		
						v1[i] =Integer.parseInt(words[0]);
						v2[i] =Integer.parseInt(words[1]);
						}
				}
				System.out.println("v1 for "+i+v1[i]+"v2 for "+i+v2[i]);
				br1.close();
				fr1.close();
				i++;
			}
			repaint();
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("fucking");
		}
		int[][] path = new int[1000][1000];
		try {
			System.out.println("success1");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cab_service", "root",
					"dynamic");
			if (con != null) {
				System.out
						.println("A database connection has been established!");
			}
			System.out.println("success2");
			
			
			Statement st= con.createStatement();   
			String s1 = "select * from bookings WHERE status = 'pending'";
			String s2 = "select * from vehicles WHERE status = 0";
			String s3 = "select * from vehicles";
			
            ResultSet rs=st.executeQuery(s1);  
            int books=0;
            String s4=new String();
            String s5=new String();
            int[][] booking=new int[1000][1000];
           
			while (rs.next()) {
				
				System.out.println(rs.getString("name"));
				s4=rs.getString("source");
				s5=rs.getString("destination");
				System.out.println(s4);
				System.out.println(s5);
				String s = new String();
				FileReader fr1 = new FileReader("points.txt");
				BufferedReader br1 = new BufferedReader(fr1);
				int j = 0;
				book_id[books]=rs.getInt("booking_id");
				System.out.println("booking id for "+books+" "+book_id[books]);
				while ((s = br1.readLine()) != null) {
						String[] words = s.split(" ");
						
						if(words[2].compareTo(s4)==0){
							src[books]=words[2];
							booking[books][0]=j;
							System.out.println("source: "+booking[books][0]);
						}
						if(words[2].compareTo(s5)==0){
							dest[books]=words[2];
							booking[books][1]=j;
							System.out.println("destination: "+booking[books][1]);
						}
						j++;
						
						

				}
				br1.close();
				fr1.close();
				books++;
			}
			
			rs.close();
			con.close();
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cab_service", "root",
					"dynamic");
			st= con.createStatement(); 
			rs=st.executeQuery(s2);
			int j=0;
			String[] cabs = new String[1000];
			int[] cab=new int[1000];
			while (rs.next()) {
				System.out.println(cabs[j]=rs.getString("regno"));
				
				//cabs[j]=rs.getString("regno");
				
				j++;
			}
			
			rs.close();
			st.close();
		    con.close();
			con = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/cab_service", "root",
						"dynamic");
			st= con.createStatement(); 
			rs=st.executeQuery(s3);
			System.out.println("j :"+j);
			for(int i=0;i<j;i++){
				int k=0;
				while (rs.next()) {
					if(cabs[i].compareTo(rs.getString("regno"))==0)
					{	cab[i]=k;
					}
					k++;
				}
			}
			rs.close();
			st.close();
			con.close();
			String s=new String();
			String ss=new String();
			String reg=new String();
			if(books<=j)
			{	System.out.println("bookings: "+books);
				for(int i=0;i<books;i++)
				{	 		new dijkstra(booking[i][0],i);
							FileReader fr1 = new FileReader("path"+i+".txt");
							BufferedReader br1 = new BufferedReader(fr1);
							int k=0,v=0;
							int order[]= new int[1000];
							int order1[]=new int[1000];
							while ((s = br1.readLine()) != null) {
								String[] words = s.split(" ");
								order[v]=Integer.parseInt(words[0]);
								v++;	
							}
							int u=0;
							for(j=0;j<v;j++){
								int max=order[j];
								order1[j]=j;
								for(int l=0;l<v;l++){
									if(max>order[l]){
										max=order[l];
										order1[j]=l;
									}
									
								}
								int temp=order[order1[j]];
								order[order1[j]]=order[j];
								order[j]=temp;
							}
							br1.close();
							fr1.close();
							
							System.out.println("order1[0] :"+order1[0]);
							for(j=0;j<v;j++){
								fr1 = new FileReader("points.txt");
								br1 = new BufferedReader(fr1);
								k = 0; int flag=0;
								while ((s1 = br1.readLine()) != null) {
								if (k == order1[j]) {
									String[] words = s1.split(" ");
									s=words[2];
									}
								k++;
								}
								
								System.out.println("s "+s);
								con.close();
								con = DriverManager.getConnection(
										"jdbc:mysql://localhost:3306/cab_service", "root",
										"dynamic");
								st= con.createStatement(); 
								System.out.println();
								s1="select * from vehicles WHERE start_place LIKE '"+s+"'";
								rs=st.executeQuery(s1);
								while (rs.next()) {
									if(rs.getInt("status")==0){
										u=order1[j];
										System.out.println("v :"+j);
										con = DriverManager.getConnection(
												"jdbc:mysql://localhost:3306/cab_service", "root",
												"dynamic");
										st= con.createStatement(); 
										reg=rs.getString("regno");
										s1="UPDATE vehicles SET status= REPLACE( status , '0', '1' ) WHERE regno LIKE '"+rs.getString("regno")+"'";
										st.executeUpdate(s1);
										st.close();
										con.close();
										flag=1;
									}
								}
								st.close();
								con.close();
								
							if(flag==1)
							{
								break;
							}
							
							br1.close();
							fr1.close();
							
							}
							int[] temp_path=new int[1000];
							fr1 = new FileReader("path"+i+".txt");
							br1 = new BufferedReader(fr1);
							k=0;
							int pathlen=0;
							while ((s = br1.readLine()) !=null) {
									if(k==u){
									String[] words = s.split(" ");
									for(j=words.length;j>1;j--){
										temp_path[words.length-j]=Integer.parseInt(words[j-1]);
										pathlen=words.length-1;
										System.out.println("path for "+booking[i][0]+"to"+booking[i][1]+" "+path[i][j-1]);
									}
									}
									k++;
									
							}
							br1.close();
							fr1.close();
						System.out.println("fuckinggg");
						con = DriverManager.getConnection(
								"jdbc:mysql://localhost:3306/cab_service", "root",
								"dynamic");
						st= con.createStatement(); 
						System.out.println("cabs for "+i+" :"+cabs[i]);
						s1="UPDATE bookings SET status = REPLACE( status , 'pending', 'processing' ) WHERE booking_id LIKE '"+book_id[i]+"'";
						st.executeUpdate(s1);
						st.close();
					con.close();
					System.out.println("source: "+booking[i][0]);
					System.out.println("destination: "+booking[i][1]);
				
					
						
						
						double p1=0,q1=0,p2=0,q2=0;
						s1 = new String();
						fr1 = new FileReader("points.txt");
						br1 = new BufferedReader(fr1);
						j = 0;
						p1=0; p2=0; q1 = 0; q2=0;
						while ((s1 = br1.readLine()) != null) {
							if (j == temp_path[0]) {
								String[] words= s1.split(" ");
								p1 = Integer.parseInt(words[0])+10;
								q1 = Integer.parseInt(words[1])+10;
							}
							j++;

						}
						br1.close();
						fr1.close();
							for (j = 1; j < pathlen; j++) {
							fr1 = new FileReader("points.txt");
							br1 = new BufferedReader(fr1);
							k = 0;
							while ((s1 = br1.readLine()) != null) {
								if (k == temp_path[j]) {
									String[] words = s1.split(" ");
									p2 = Integer.parseInt(words[0])+10;
									q2= Integer.parseInt(words[1])+10;
								}
								k++;
							}
							System.out.println(p1 + " " + q1 + " " + p2 + " " + q2);
							double m=(q2-q1)/(p2-p1);
							br1.close();
							fr1.close();
							con = DriverManager.getConnection(
									"jdbc:mysql://localhost:3306/cab_service", "root",
									"dynamic");
							st= con.createStatement(); 
							System.out.println();
							s1="select * from vehicles";
							rs=st.executeQuery(s1);
							k=0; int pt=0;
							
							while (rs.next()) {
								if(reg.compareTo(rs.getString("regno"))==0){
									pt=k;
									ss=rs.getString("start_place");
									break;
								}
								k++;
							}
							st.close();
							con.close();
							System.out.println("m :"+m);
							v1[pt] =(int) p1;
							v2[pt] =(int) q1;
							repaint();
							for(k=0;k>-1;k++){
								if(p2<p1)
									v1[pt] =v1[pt]-10;
								else
									v1[pt] =v1[pt]+10;
								if(q2<q1)
									v2[pt] =(int) (v2[pt]-10*Math.abs(m));
								else
									v2[pt] =(int) (v2[pt]+10*Math.abs(m));
								repaint();
								System.out.println("v1 v2:"+v1[pt]+v2[pt]);
							System.out.println("Main Thread: " + k);
							Thread.sleep(1000);
							if ( v1[pt]< p2 + 20 && v1[pt] > p2 - 20 && v2[pt] < q2 + 20
									&& v2[pt] > q2 - 20){
								System.out.println("broked");
									p1=p2;
									q1=q2;
									break;
									
							}
								
							}
						}
							System.out.println("Second journey");
							s = new String();
							fr1 = new FileReader("path"+i+".txt");
							br1 = new BufferedReader(fr1);
							k=0;
							int pathlength=0;
							while ((s = br1.readLine()) != null) {
								System.out.println("");
									if(k==booking[i][1]){
									String[] words = s.split(" ");
									for(j=1;j<words.length;j++){
										path[i][j-1]=Integer.parseInt(words[j]);
										pathlength=words.length-1;
										System.out.println("path for "+booking[i][0]+"to"+booking[i][1]+" "+path[i][j-1]);
									}
									}
									k++;
									
							}
							br1.close();
							fr1.close();
							
							
						s1 = new String();
						fr1 = new FileReader("points.txt");
						br1 = new BufferedReader(fr1);
						j = 0;
						p1=0; p2=0; q1 = 0; q2=0;
						while ((s1 = br1.readLine()) != null) {
							if (j == path[i][0]) {
								String[] words= s1.split(" ");
								p1 = Integer.parseInt(words[0])+10;
								q1 = Integer.parseInt(words[1])+10;
							}
							j++;

						}
						br1.close();
						fr1.close();
						int flag=0;
						for (j = 1; j < pathlength; j++) {
							fr1 = new FileReader("points.txt");
							br1 = new BufferedReader(fr1);
							k = 0;
							while ((s1 = br1.readLine()) != null) {
								if (k == path[i][j]) {
									String[] words = s1.split(" ");
									p2 = Integer.parseInt(words[0])+10;
									q2= Integer.parseInt(words[1])+10;
								}
								k++;
							}
							System.out.println(p1 + " " + q1 + " " + p2 + " " + q2);
							double m=(q2-q1)/(p2-p1);
							br1.close();
							fr1.close();
							con = DriverManager.getConnection(
									"jdbc:mysql://localhost:3306/cab_service", "root",
									"dynamic");
							st= con.createStatement(); 
							System.out.println();
							s1="select * from vehicles";
							rs=st.executeQuery(s1);
							k=0; int pt=0;
							while (rs.next()) {
								if(reg.compareTo(rs.getString("regno"))==0){
									pt=k;
									break;
								}
								k++;
							}
							st.close();
							con.close();
							System.out.println("m :"+m);
							v1[pt] =(int) p1;
							v2[pt] =(int) q1;
							repaint();
							for(k=0;k>-1;k++){
								if(p2<p1)
									v1[pt] =v1[pt]-10;
								else
									v1[pt] =v1[pt]+10;
								if(q2<q1)
									v2[pt] =(int) (v2[pt]-10*Math.abs(m));
								else
									v2[pt] =(int) (v2[pt]+10*Math.abs(m));
								repaint();
								System.out.println("v1 v2:"+v1[pt]+v2[pt]);
							System.out.println("Main Thread: " + k);
							Thread.sleep(1000);
							if ( v1[pt]< p2 + 20 && v1[pt] > p2 - 20 && v2[pt] < q2 + 20
									&& v2[pt] > q2 - 20){
								if(path[i][j]==booking[i][1]){
									con = DriverManager.getConnection(
											"jdbc:mysql://localhost:3306/cab_service", "root",
											"dynamic");
									st= con.createStatement(); 
									System.out.println("cabs for "+i+" :"+cabs[i]);
									System.out.println("ss :"+ss);
									System.out.println("ss :"+ss);
									s1="UPDATE vehicles SET start_place = REPLACE( start_place , '"+ss+"', '"+dest[i]+"' ) WHERE regno LIKE '"+reg+"'";
									st.executeUpdate(s1);
									st.close();
								con.close();
								con = DriverManager.getConnection(
										"jdbc:mysql://localhost:3306/cab_service", "root",
										"dynamic");
								st= con.createStatement(); 
								System.out.println("cabs for "+i+" :"+cabs[i]);
								System.out.println("ss :"+ss);
								System.out.println("ss :"+ss);
								s1="UPDATE vehicles SET status = REPLACE( status , '1', '0' ) WHERE regno LIKE '"+reg+"'";
								st.executeUpdate(s1);
								st.close();
							con.close();
								con = DriverManager.getConnection(
										"jdbc:mysql://localhost:3306/cab_service", "root",
										"dynamic");
								st= con.createStatement(); 
								System.out.println("cabs for "+i+" :"+cabs[i]);
								s1="UPDATE bookings SET status = REPLACE( status , 'processing', 'completed' ) WHERE booking_id LIKE '"+book_id[i]+"'";
								st.executeUpdate(s1);
								st.close();
							con.close();
							flag=1;
								}
							
								System.out.println("broked");
									p1=p2;
									q1=q2;
									break;
									
							}
							}
						}
				}
			
				
		}
		} catch (Exception e1) {
		   System.out.println("Exception Occured!");
	}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
	 public void start() throws IOException {
		 Thread thread = new Thread(this);
         thread.start();
	    }
}

