/*
 * Copyright 2012 Frantisek Galcik
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sk.upjs.visualscanner;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Frame that visualizes the current content of an underlying VisualScanner.
 */
class ScannerFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * A class storing a position of the token
	 */
	private static class TokenPosition {
		int start;
		int length;
	}

	/**
	 * A scanner that is visualized by this frame
	 */
	private VisualScanner scanner;

	/**
	 * Next token to be scanned by the underlying scanner.
	 */
	private String nextToken;

	/**
	 * Content to be scanned by the underlying scanner.
	 */
	private String contentToScan;

	/**
	 * List of token positions in the contentToScan
	 */
	private ArrayList<TokenPosition> tokens;

	// Checkboxes displaying results of hasNext methods.
	private JCheckBox hasNextBox;
	private JCheckBox hasNextByteBox;
	private JCheckBox hasNextIntBox;
	private JCheckBox hasNextFloatBox;
	private JCheckBox hasNextBigIntegerBox;
	private JCheckBox hasNextBooleanBox;
	private JCheckBox hasNextBigDecimalBox;
	private JCheckBox hasNextDoubleBox;
	private JCheckBox hasNextLongBox;
	private JCheckBox hasNextShortBox;
	private JCheckBox hasNextLineBox;

	private JPanel contentPane;
	private JLabel nextTokenLabel;
	private JTextPane bufferPane;
	private JScrollPane bufferPaneScroller;
	private JPanel sourcePanel;
	private JPanel panel_5;
	private JPanel panel_7;
	private JPanel panel;
	private JLabel delimiterLabel;
	private JLabel sourceLabel;
	private JLabel currentOperationLabel;
	private JButton btnExecute;
	private JLabel radixLabel;
	private JLabel localeLabel;
	private JToggleButton btnSpecialChars;
	private JPanel hasNextsPanel;
	private JToggleButton highlightButton;

	/**
	 * Create the frame.
	 */
	public ScannerFrame() {
		this(null);
	}

	public ScannerFrame(VisualScanner scanner) {
		this.scanner = scanner;
		initializeComponents();
	}

	/**
	 * Initializes all components of the frame
	 */
	private void initializeComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ScannerFrame.class.getResource("images/visualScanner.png")));

		setTitle("Visual Scanner");
		setBounds(100, 100, 386, 547);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(new Color(255, 250, 240));
		infoPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		hasNextsPanel = new JPanel();
		hasNextsPanel.setToolTipText("Results of hasNextXYZ methods in the current state of the scanner");
		hasNextsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Results of HasNext___",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(
				new TitledBorder(null, "Content to scan", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(hasNextsPanel, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
				.addComponent(infoPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		gl_contentPane
				.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(5)
								.addComponent(hasNextsPanel, GroupLayout.PREFERRED_SIZE, 169,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)));
		GridBagLayout gbl_infoPanel = new GridBagLayout();
		gbl_infoPanel.columnWidths = new int[] { 150, 100, 0 };
		gbl_infoPanel.rowHeights = new int[] { 22, 22, 22, 0 };
		gbl_infoPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_infoPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		infoPanel.setLayout(gbl_infoPanel);

		sourcePanel = new JPanel();
		sourcePanel.setBackground(new Color(255, 255, 204));

		sourceLabel = new JLabel("File(\"Subor.txt\")");
		FlowLayout fl_sourcePanel = new FlowLayout(FlowLayout.LEFT, 5, 3);
		fl_sourcePanel.setAlignOnBaseline(true);
		sourcePanel.setLayout(fl_sourcePanel);

		JLabel label = new JLabel("Source:");
		sourcePanel.add(label);
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		sourcePanel.add(sourceLabel);
		GridBagConstraints gbc_sourcePanel = new GridBagConstraints();
		gbc_sourcePanel.gridwidth = 2;
		gbc_sourcePanel.fill = GridBagConstraints.BOTH;
		gbc_sourcePanel.gridx = 0;
		gbc_sourcePanel.gridy = 0;
		infoPanel.add(sourcePanel, gbc_sourcePanel);

		panel_5 = new JPanel();
		panel_5.setBackground(new Color(255, 255, 204));
		FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
		flowLayout.setVgap(3);
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setAlignOnBaseline(true);

		JLabel label_2 = new JLabel("Token delimiter:");
		panel_5.add(label_2);
		label_2.setFont(new Font("Trebuchet MS", Font.BOLD, 12));

		delimiterLabel = new JLabel("\\t");
		panel_5.add(delimiterLabel);
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.gridwidth = 2;
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 1;
		infoPanel.add(panel_5, gbc_panel_5);

		JLabel lblRadix = new JLabel("Radix:");
		lblRadix.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_5.add(lblRadix);

		radixLabel = new JLabel("10");
		panel_5.add(radixLabel);

		JLabel lblLocale = new JLabel("Locale:");
		lblLocale.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_5.add(lblLocale);

		localeLabel = new JLabel("SK");
		panel_5.add(localeLabel);

		panel_7 = new JPanel();
		panel_7.setBackground(new Color(255, 255, 204));
		FlowLayout flowLayout_1 = (FlowLayout) panel_7.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		flowLayout_1.setAlignOnBaseline(true);
		flowLayout_1.setVgap(3);
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 2;
		infoPanel.add(panel_7, gbc_panel_7);

		JLabel lblMethodToExecuted = new JLabel("Method:");
		panel_7.add(lblMethodToExecuted);
		lblMethodToExecuted.setFont(new Font("Trebuchet MS", Font.BOLD, 12));

		currentOperationLabel = new JLabel("hasNextInt");
		panel_7.add(currentOperationLabel);

		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 204));
		FlowLayout flowLayout_2 = (FlowLayout) panel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.RIGHT);
		flowLayout_2.setAlignOnBaseline(true);
		flowLayout_2.setVgap(3);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		infoPanel.add(panel, gbc_panel);

		btnExecute = new JButton("Execute");
		btnExecute.setToolTipText("Executed the pending method");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				synchronized (scanner.waitLock) {
					scanner.waitLock.notifyAll();
				}
			}
		});
		btnExecute.setIcon(new ImageIcon(ScannerFrame.class.getResource("images/executeIcon.png")));
		panel.add(btnExecute);

		JLabel lblNewLabel_4 = new JLabel("Next token:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));

		bufferPaneScroller = new JScrollPane();

		nextTokenLabel = new JLabel("???");
		nextTokenLabel.setToolTipText("The first unscanned token");

		btnSpecialChars = new JToggleButton("¶");
		btnSpecialChars.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateContentToScanArea();
			}
		});
		btnSpecialChars.setToolTipText("Show/hide hidden symbols");
		btnSpecialChars.setFont(new Font("Tahoma", Font.BOLD, 13));

		highlightButton = new JToggleButton("");
		highlightButton.setToolTipText("Enable/disable token highlighting");
		highlightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateContentToScanArea();
			}
		});
		highlightButton.setIcon(new ImageIcon(ScannerFrame.class.getResource("images/highlight.png")));
		highlightButton.setSelectedIcon(null);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(
						gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_2.createSequentialGroup().addContainerGap()
										.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
												.addComponent(
														bufferPaneScroller, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														328, Short.MAX_VALUE)
												.addGroup(gl_panel_2.createSequentialGroup().addComponent(lblNewLabel_4)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(nextTokenLabel)
														.addPreferredGap(ComponentPlacement.RELATED, 82,
																Short.MAX_VALUE)
														.addComponent(highlightButton)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(btnSpecialChars)))
										.addContainerGap()));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
				.createSequentialGroup().addGap(6)
				.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_4)
						.addComponent(nextTokenLabel).addComponent(btnSpecialChars).addComponent(highlightButton))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(bufferPaneScroller, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE).addContainerGap()));

		bufferPane = new JTextPane();
		bufferPane.setEditable(false);
		bufferPaneScroller.setViewportView(bufferPane);
		panel_2.setLayout(gl_panel_2);
		hasNextsPanel.setLayout(new GridLayout(0, 2, 0, 0));

		hasNextBox = new ReadOnlyCheckBox("hasNext");
		hasNextsPanel.add(hasNextBox);

		hasNextLineBox = new ReadOnlyCheckBox("hasNextLine");
		hasNextLineBox.setSelected(true);
		hasNextsPanel.add(hasNextLineBox);

		hasNextByteBox = new ReadOnlyCheckBox("hasNextByte");
		hasNextsPanel.add(hasNextByteBox);

		hasNextShortBox = new ReadOnlyCheckBox("hasNextShort");
		hasNextsPanel.add(hasNextShortBox);

		hasNextIntBox = new ReadOnlyCheckBox("hasNextInt");
		hasNextsPanel.add(hasNextIntBox);

		hasNextLongBox = new ReadOnlyCheckBox("hasNextLong");
		hasNextsPanel.add(hasNextLongBox);

		hasNextFloatBox = new ReadOnlyCheckBox("hasNextFloat");
		hasNextsPanel.add(hasNextFloatBox);

		hasNextDoubleBox = new ReadOnlyCheckBox("hasNextDouble");
		hasNextsPanel.add(hasNextDoubleBox);

		hasNextBigIntegerBox = new ReadOnlyCheckBox("hasNextBigInteger");
		hasNextsPanel.add(hasNextBigIntegerBox);

		hasNextBigDecimalBox = new ReadOnlyCheckBox("hasNextBigDecimal");
		hasNextsPanel.add(hasNextBigDecimalBox);

		hasNextBooleanBox = new ReadOnlyCheckBox("hasNextBoolean");
		hasNextsPanel.add(hasNextBooleanBox);
		contentPane.setLayout(gl_contentPane);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(ScannerFrame.this, "Do you really want to exit this program?",
						"Confirm Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		DefaultCaret caret = (DefaultCaret) bufferPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
	}

	/**
	 * Update the content of the frame according to current state of the
	 * underlying Scanner
	 */
	void updateView() {
		// Set values of the hasNexts
		hasNextBox.setSelected(scanner.hasNext());
		hasNextLineBox.setSelected(scanner.hasNextLine());
		hasNextByteBox.setSelected(scanner.hasNextByte());
		hasNextShortBox.setSelected(scanner.hasNextShort());
		hasNextIntBox.setSelected(scanner.hasNextInt());
		hasNextLongBox.setSelected(scanner.hasNextLong());
		hasNextFloatBox.setSelected(scanner.hasNextFloat());
		hasNextDoubleBox.setSelected(scanner.hasNextDouble());
		hasNextBigIntegerBox.setSelected(scanner.hasNextBigInteger());
		hasNextBigDecimalBox.setSelected(scanner.hasNextBigDecimal());
		hasNextBooleanBox.setSelected(scanner.hasNextBoolean());
		colorHasNexts();

		// Set properties of the scanner
		if (scanner.delimiter() != null) {
			delimiterLabel.setText(toJavaString(scanner.delimiter().toString()));
		} else {
			delimiterLabel.setText("null");
		}

		radixLabel.setText(Integer.toString(scanner.radix()));

		if (scanner.locale() != null) {
			localeLabel.setText(scanner.locale().toString());
		} else {
			localeLabel.setText("null");
		}

		sourceLabel.setText(toJavaString(scanner.sourceDescription));

		if (scanner.currentOperation == null) {
			currentOperationLabel.setText("");
			btnExecute.setEnabled(false);
		} else {
			currentOperationLabel.setText(scanner.currentOperation);
			btnExecute.setEnabled(true);
		}

		// Update content to scan
		nextToken = scanner.getNextToken();
		contentToScan = scanner.getBuffer();
		computeToScanTokens();
		updateContentToScanArea();
	}

	/**
	 * Updates the content of the "Content to scan" panel.
	 */
	private void updateContentToScanArea() {
		if (highlightButton.isSelected()) {
			highlightContentToScan();
			if (btnSpecialChars.isSelected()) {
				nextTokenLabel.setText(toWordString(nextToken));
			} else {
				nextTokenLabel.setText(nextToken);
			}
		} else {
			if (btnSpecialChars.isSelected()) {
				nextTokenLabel.setText(toWordString(nextToken));
				bufferPane.setText("");
				bufferPane.setText(toWordString(contentToScan));
			} else {
				nextTokenLabel.setText(nextToken);
				bufferPane.setText("");
				bufferPane.setText(contentToScan);
			}
		}
	}

	/**
	 * Computes positions of tokens in the content to scan text
	 */
	private void computeToScanTokens() {
		Scanner s = new Scanner(contentToScan);
		s.useDelimiter(scanner.delimiter());
		s.useRadix(scanner.radix());
		s.useLocale(scanner.locale());

		tokens = new ArrayList<TokenPosition>();
		int scanPos = 0;
		while (s.hasNext()) {
			String token = s.next();
			int tokenPos = contentToScan.indexOf(token, scanPos);

			TokenPosition tp = new TokenPosition();
			tp.start = tokenPos;
			tp.length = token.length();
			tokens.add(tp);
			scanPos = tokenPos + token.length() + 1;
		}
		s.close();
	}

	/**
	 * Highlights positions of tokens in the content to scan area
	 */
	private void highlightContentToScan() {
		// Style for tokens
		SimpleAttributeSet tokenStyle = new SimpleAttributeSet();
		StyleConstants.setBackground(tokenStyle, Color.yellow);

		// Style for delimiters
		SimpleAttributeSet delimiterStyle = new SimpleAttributeSet();

		// Style for text after the last token
		SimpleAttributeSet textTrailStyle = new SimpleAttributeSet();

		bufferPane.setText("");
		Document doc = bufferPane.getDocument();

		try {
			int delimStart = 0;
			for (TokenPosition tp : tokens) {
				String delimiter = contentToScan.substring(delimStart, tp.start);
				String token = contentToScan.substring(tp.start, tp.start + tp.length);
				if (btnSpecialChars.isSelected()) {
					delimiter = toWordString(delimiter);
					token = toWordString(token);
				}
				doc.insertString(doc.getLength(), delimiter, delimiterStyle);
				doc.insertString(doc.getLength(), token, tokenStyle);
				delimStart = tp.start + tp.length;
			}
			doc.insertString(doc.getLength(), contentToScan.substring(delimStart), textTrailStyle);
		} catch (Exception ignore) {

		}
	}

	/**
	 * Color hasNexts
	 */
	private void colorHasNexts() {
		for (Component component : hasNextsPanel.getComponents()) {
			if (component instanceof ReadOnlyCheckBox) {
				ReadOnlyCheckBox checkbox = (ReadOnlyCheckBox) component;
				if (checkbox.isSelected()) {
					checkbox.setForeground(new Color(0, 100, 0));
				} else {
					checkbox.setForeground(Color.black);
				}
			}
		}
	}

	/**
	 * Converts all special characters to its Java escape sequences.
	 */
	private String toJavaString(String s) {
		if (s == null) {
			return null;
		}

		return s.replace("\t", "\\t").replace("\n", "\\n").replace("\r", "\\r");
	}

	/**
	 * Converts all special characters to Word symbol representation
	 */
	private String toWordString(String s) {
		if (!btnSpecialChars.isSelected()) {
			return s;
		}

		if (s == null) {
			return null;
		}

		return s.replace("\n", "¶\n").replace("\t", "→\t");
	}
}
