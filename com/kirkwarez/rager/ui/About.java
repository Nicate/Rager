package com.kirkwarez.rager.ui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.kirkwarez.rager.Rager;

public class About extends JDialog {
	private static final long serialVersionUID = 7934842710634366954L;
	
	
	public About() {
		setTitle(Rager.getApplicationName() + " " + Rager.getApplicationVersion());
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});
		
		JTextArea nameTextArea = new JTextArea();
		nameTextArea.setFont(okButton.getFont());
		nameTextArea.setEditable(false);
		nameTextArea.setColumns(40);
		nameTextArea.setRows(1);
		nameTextArea.setText(Rager.getApplicationName());
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setLabelFor(nameTextArea);
		
		JTextArea versionTextArea = new JTextArea();
		versionTextArea.setFont(okButton.getFont());
		versionTextArea.setEditable(false);
		versionTextArea.setColumns(40);
		versionTextArea.setRows(1);
		versionTextArea.setText(Rager.getApplicationVersion());
		
		JLabel versionLabel = new JLabel("Version:");
		versionLabel.setLabelFor(versionTextArea);
		
		JTextArea descriptionTextArea = new JTextArea();
		descriptionTextArea.setFont(okButton.getFont());
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setEditable(false);
		descriptionTextArea.setColumns(40);
		descriptionTextArea.setRows(4);
		descriptionTextArea.setText(Rager.getApplicationDescription());
		
		JLabel descriptionLabel = new JLabel("Description:");
		descriptionLabel.setLabelFor(descriptionTextArea);
		
		JTextArea authorTextArea = new JTextArea();
		authorTextArea.setFont(okButton.getFont());
		authorTextArea.setEditable(false);
		authorTextArea.setColumns(40);
		authorTextArea.setRows(1);
		authorTextArea.setText(Rager.getApplicationAuthor());
		
		JLabel authorLabel = new JLabel("Author:");
		authorLabel.setLabelFor(authorTextArea);
		
		JTextArea licenseTextArea = new JTextArea();
		licenseTextArea.setFont(okButton.getFont());
		licenseTextArea.setWrapStyleWord(true);
		licenseTextArea.setLineWrap(true);
		licenseTextArea.setEditable(false);
		licenseTextArea.setColumns(40);
		licenseTextArea.setRows(4);
		licenseTextArea.setText(Rager.getApplicationLicense());
		
		JLabel licenseLabel = new JLabel("License:");
		licenseLabel.setLabelFor(licenseTextArea);
		
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
						.addComponent(nameTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(versionTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(descriptionTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(licenseTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(authorTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(okButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameLabel)
						.addComponent(nameTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(versionLabel)
						.addComponent(versionTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(descriptionLabel)
						.addComponent(descriptionTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(authorLabel)
						.addComponent(authorTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(licenseLabel)
						.addComponent(licenseTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(okButton)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		
		pack();
		
		Rectangle screenBounds = getGraphicsConfiguration().getBounds();
		Rectangle windowBounds = getBounds();
		
		setLocation((screenBounds.width - windowBounds.width) / 2, (screenBounds.height - windowBounds.height) / 2);
	}
}
