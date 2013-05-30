package GUI;




import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JButton;

import SQL_CON.MySQL;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.JScrollPane;
import javax.swing.JList;

import model.LoadInfo;

public class GUI_MainFrame {

	private JFrame frmSqlGui;
	private Controller controller;
	private JButton btnClose;
	private JButton btnCreateLoad;
	private JTextField txfSuborder;
	private JTextField txtUssername;
	private JTextField txtPassword;
	private JTextField txtUrl;
	private JTextField txtDockid;
	private JTextField txDate;
	private JScrollPane scrollPane;
	private JList<LoadInfo> list;
	DefaultListModel<LoadInfo> model;
	private JButton btnSet;
	private JButton btnPrintfordate;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_MainFrame window = new GUI_MainFrame();
					window.frmSqlGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		controller= new Controller();
		
		frmSqlGui = new JFrame();
		frmSqlGui.setTitle("SQL GUI");
		frmSqlGui.setBounds(100, 100, 721, 394);
		frmSqlGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSqlGui.getContentPane().setLayout(null);
		
		btnClose = new JButton("Close connection");
		btnClose.addActionListener(controller);
		btnClose.setBounds(130, 115, 144, 23);
		frmSqlGui.getContentPane().add(btnClose);
		
		btnCreateLoad = new JButton("CreateLoad(2B)");
		btnCreateLoad.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreateLoad.addActionListener(controller);
		btnCreateLoad.setBounds(359, 81, 135, 23);
		frmSqlGui.getContentPane().add(btnCreateLoad);
		
		txfSuborder = new JTextField();
		txfSuborder.setText("Suborder_ID");
		txfSuborder.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txfSuborder.setHorizontalAlignment(SwingConstants.CENTER);
		txfSuborder.setToolTipText("Dock");
		txfSuborder.setBounds(362, 20, 86, 20);
		frmSqlGui.getContentPane().add(txfSuborder);
		txfSuborder.setColumns(10);
		
		txtUssername = new JTextField();
		txtUssername.setText("ussername");
		txtUssername.setBounds(21, 22, 86, 20);
		frmSqlGui.getContentPane().add(txtUssername);
		txtUssername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("password");
		txtPassword.setBounds(21, 53, 86, 20);
		frmSqlGui.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		txtUrl = new JTextField();
		txtUrl.setText("jdbc:mysql://localhost:3306/caos_dc");
		txtUrl.setBounds(21, 82, 271, 20);
		frmSqlGui.getContentPane().add(txtUrl);
		txtUrl.setColumns(10);
		
		btnSet = new JButton("Set ");
		btnSet.setBounds(21, 115, 86, 23);
		btnSet.addActionListener(controller);
		frmSqlGui.getContentPane().add(btnSet);
		
		txtDockid = new JTextField();
		txtDockid.setHorizontalAlignment(SwingConstants.CENTER);
		txtDockid.setText("Dock_ID");
		txtDockid.setBounds(362, 51, 86, 20);
		frmSqlGui.getContentPane().add(txtDockid);
		txtDockid.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 180, 674, 168);
		frmSqlGui.getContentPane().add(scrollPane);
		
		list = new JList<LoadInfo>();
		scrollPane.setViewportView(list);
		model=new DefaultListModel<LoadInfo>();
	    list.setModel(model);
		
		btnPrintfordate = new JButton("PrintForDate(2C):");
		btnPrintfordate.setBounds(21, 146, 144, 23);
		btnPrintfordate.addActionListener(controller);
		frmSqlGui.getContentPane().add(btnPrintfordate);
		
		txDate = new JTextField();
		txDate.setText("'2013-06-02'");
		txDate.setBounds(175, 149, 162, 20);
		frmSqlGui.getContentPane().add(txDate);
		txDate.setColumns(10);
	}
	
	
	private void fillModel()
	{
		model.clear();
		for(LoadInfo li:MySQL.getDatesForDock(txDate.getText()))
		{
			model.addElement(li);
		}
	}
	
	 private class Controller implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnPrintfordate)
				{
				
					fillModel();
				}
				if(e.getSource()== btnSet)
				{
					MySQL.setUsername(txtUssername.getText());
					MySQL.setPassword(txtPassword.getText());
					MySQL.setUrl(txtUrl.getText());
				}
				
				if(e.getSource()==btnCreateLoad)
				{
					MySQL.calculateAndCreateLoad( Integer.parseInt(txfSuborder.getText())
							,Integer.parseInt( txtDockid.getText()));
				}
				
				if(e.getSource()==btnClose)
				{
					MySQL.closeConnection();
				}
			}	
		}
}
