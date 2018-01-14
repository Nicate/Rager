package nl.tsfs.rager.ui;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.*;

import nl.tsfs.rager.*;


public class About extends JDialog {
	private static final long serialVersionUID = 7934842710634366954L;
	
	
	public About() {
		setTitle(Rager.getInstance().getName() + " " + Rager.getInstance().getVersion());
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setResizable(false);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});
		
		JTextArea nameTextArea = new JTextArea();
		nameTextArea.setFont(okButton.getFont());
		nameTextArea.setWrapStyleWord(true);
		nameTextArea.setLineWrap(true);
		nameTextArea.setEditable(false);
		nameTextArea.setColumns(40);
		nameTextArea.setRows(1);
		nameTextArea.setText(Rager.getInstance().getName());
		nameTextArea.setCaretPosition(0);
		
		JScrollPane nameScrollPane = new JScrollPane(nameTextArea);
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setLabelFor(nameScrollPane);
		
		JTextArea versionTextArea = new JTextArea();
		versionTextArea.setFont(okButton.getFont());
		versionTextArea.setWrapStyleWord(true);
		versionTextArea.setLineWrap(true);
		versionTextArea.setEditable(false);
		versionTextArea.setColumns(40);
		versionTextArea.setRows(1);
		versionTextArea.setText(Rager.getInstance().getVersion());
		versionTextArea.setCaretPosition(0);
		
		JScrollPane versionScrollPane = new JScrollPane(versionTextArea);
		
		JLabel versionLabel = new JLabel("Version:");
		versionLabel.setLabelFor(versionScrollPane);
		
		JTextArea descriptionTextArea = new JTextArea();
		descriptionTextArea.setFont(okButton.getFont());
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setEditable(false);
		descriptionTextArea.setColumns(40);
		descriptionTextArea.setRows(4);
		descriptionTextArea.setText(Rager.getInstance().getDescription());
		descriptionTextArea.setCaretPosition(0);
		
		JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
		
		JLabel descriptionLabel = new JLabel("Description:");
		descriptionLabel.setLabelFor(descriptionScrollPane);
		
		JTextArea authorTextArea = new JTextArea();
		authorTextArea.setFont(okButton.getFont());
		authorTextArea.setWrapStyleWord(true);
		authorTextArea.setLineWrap(true);
		authorTextArea.setEditable(false);
		authorTextArea.setColumns(40);
		authorTextArea.setRows(1);
		authorTextArea.setText(Rager.getInstance().getAuthor());
		authorTextArea.setCaretPosition(0);
		
		JScrollPane authorScrollPane = new JScrollPane(authorTextArea);
		
		JLabel authorLabel = new JLabel("Author:");
		authorLabel.setLabelFor(authorScrollPane);
		
		JTextArea licenseTextArea = new JTextArea();
		licenseTextArea.setFont(okButton.getFont());
		licenseTextArea.setWrapStyleWord(true);
		licenseTextArea.setLineWrap(true);
		licenseTextArea.setEditable(false);
		licenseTextArea.setColumns(40);
		licenseTextArea.setRows(10);
		licenseTextArea.setText(Rager.getInstance().getLicense());
		licenseTextArea.setCaretPosition(0);
		
		JScrollPane licenseScrollPane = new JScrollPane(licenseTextArea);
		
		JLabel licenseLabel = new JLabel("License:");
		licenseLabel.setLabelFor(licenseScrollPane);
		
		// @formatter:off
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(nameLabel)
						.addComponent(versionLabel)
						.addComponent(descriptionLabel)
						.addComponent(licenseLabel)
						.addComponent(authorLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(nameScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(versionScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(descriptionScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(licenseScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(authorScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(okButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameLabel)
						.addComponent(nameScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(versionLabel)
						.addComponent(versionScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(descriptionLabel)
						.addComponent(descriptionScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(authorLabel)
						.addComponent(authorScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(licenseLabel)
						.addComponent(licenseScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(okButton)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		// @formatter:on
		
		pack();
		
		Rectangle screenBounds = getGraphicsConfiguration().getBounds();
		Rectangle windowBounds = getBounds();
		
		setLocation((screenBounds.width - windowBounds.width) / 2, (screenBounds.height - windowBounds.height) / 2);
	}
}
