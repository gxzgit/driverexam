package com.exam.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.exam.entity.Examer;
import com.exam.service.IExamerService;
import com.exam.service.impl.ExamerService;
import com.exam.utils.FontInit;

public class EntryFrame extends JFrame {
	IExamerService examerService = new ExamerService();
	private static final long serialVersionUID = 3484010874797612372L;
	private JPanel pane = null; // ��JPanel
	JPanel entry = null;

	/**
	 * ���췽����ʼ������
	 */
	public EntryFrame() {
		FontInit.InitGlobalFont(new Font("΢���ź�", Font.PLAIN, 16));
		init();
	}

	private void init() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		pane = new JPanel(new BorderLayout()); // JPanel�Ĳ��ֹ����������ó�CardLayout
		entry = new JPanel(new GridLayout(3, 1));
		entry.setLayout(null);
		this.setSize(600, 400);
		this.setLocation((screenSize.width - 600) / 2, (screenSize.height - 400) / 2);
		this.setResizable(false);
		final JTextField text = new JTextField(18);
		text.setBackground(UIManager.getColor("scrollbar"));
		text.setBounds(200, 100, 180, 45);
		text.setBorder(BorderFactory.createTitledBorder("����������֤"));
		// ��������ֻ֤��Ϊ18λ�Ҳ��������������������ַ�
		text.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				int keyChar = e.getKeyChar();
				if (text.getText().length() < 18) {
					if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {
					} else {
						e.consume();
					}
				} else {
					e.consume();
				}
			}
		});
		JButton jb = new JButton("��¼");
		jb.setBounds(200, 150, 60, 30);
		final JLabel jl = new JLabel();
		jl.setBounds(160, 200, 350, 80);
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				final Examer examer = examerService.checkIdCard(text.getText());
				if (examer == null) {
					jl.setForeground(Color.red);
					jl.setText("���������֤���벻�ԣ�����������");
				}else if(examer.getCount()>1){
					jl.setText("�Ѿ��μӹ������������ٿ���");
				}else {
					JButton jlogin = new JButton("��ʼ");
					jlogin.setBounds(300, 150, 60, 30);
					jl.setForeground(Color.BLACK);
					jl.setText("<html><body>�������Ϊ��"+examer.getName()+"<br>��ȷ��������㿪ʼ��ť���п���<html><body>");
					EntryFrame.this.repaint();
					jlogin.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							new ExamFrame(examer);
							examerService.updateCount(examer.getId(), examer.getCount()+1);
							EntryFrame.this.dispose();
						}
					});
					entry.add(jlogin);
				}
			}
		});
		
		entry.add(jl);
		entry.add(text);
		entry.add(jb);
		pane.add(entry);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setContentPane(pane);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			// com.jtattoo.plaf.mint.MintLookAndFeel
			//com.jtattoo.plaf.aluminium.AluminiumLookAndFeel
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
			// start application
			new EntryFrame();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}