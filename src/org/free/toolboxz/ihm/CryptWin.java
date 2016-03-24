package org.free.toolboxz.ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.free.toolboxz.exceptions.SimpleException;
import org.free.toolboxz.security.EnumAlgo;

import com.spinn3r.log5j.Logger;

public class CryptWin extends JFrame implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Logger logger = null;

    private JFileChooser fc = new JFileChooser();

    private JButton jbnFile, jbnRead;
    private JComboBox<?> jcmbTypes;

    private JTextField jtfInput, jtfFile;
    private JTextField jtfHash;

    private JDialog dialogWait;

    /**
     * @return Returns the jtfHash.
     */
    public JTextField getJtfHash() {
        return jtfHash;
    }

    /**
     * @return Returns the jtfInput.
     */
    public JTextField getJtfInput() {
        return jtfInput;
    }

    /**
     * @return Returns the dialogWait.
     */
    public JDialog getDialogWait() {
        return dialogWait;
    }

    public CryptWin(Logger logger) throws SimpleException {
        super("ToolBox");

        this.logger = logger;

        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(700, 200));
        setMinimumSize(new Dimension(700, 200));
        setLocationRelativeTo(null); // On centre la fenêtre sur l'écran
        setResizable(false);

        // Fenetre modale d'attente
        dialogWait = new JDialog(this, true);
        dialogWait.setTitle("Pending...");
        dialogWait.setLocationRelativeTo(this); // On centre la fenêtre sur la Frame parent

        // Construction de la fenêtre
        JPanel body = buildContentPane();
        setContentPane(body);

        // Ajout d'un menu
        JMenuBar menuBar = buildMenu();
        setJMenuBar(menuBar);

        // Initialisation et affichage de la fenêtre
        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JMenuBar buildMenu() throws SimpleException {

        JMenuBar menubar = new JMenuBar();
        ImageIcon icon = UIHelper.readImageIcon("exit_app32x32.png");

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Quit", icon);
        eMenuItem.setMnemonic(KeyEvent.VK_Q);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }

        });

        file.add(eMenuItem);
        menubar.add(file);

        return menubar;
    }

    private JPanel buildContentPane() {

        JLabel jlbl;

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.white);

        // Declaration des contraintes d'affichage par défaut
        GBC gbc = new GBC(0, 0, 1, 1, GBC.NONE, GBC.BASELINE_LEADING, 0.0, 0.0, new Insets(5, 5, 5, 5), 0, 0);

        int gridx = 0;
        int gridy = 0;

        // Installation des composants
        jlbl = new JLabel("Method :");
        panel.add(jlbl, gbc);

        jcmbTypes = new JComboBox<Object>(EnumAlgo.values());
        jcmbTypes.addActionListener(this);
        gbc.update(++gridx, gridy);
        panel.add(jcmbTypes, gbc);

        jlbl = new JLabel("text :");
        gbc.update(gridx = 0, ++gridy);
        panel.add(jlbl, gbc);

        jtfInput = new JTextField();
        jtfInput.setEditable(true);
        gbc.update(++gridx, gridy, GBC.REMAINDER, 1);
        gbc.setFill(GBC.HORIZONTAL).setWeight(1, 0);
        panel.add(jtfInput, gbc);

        jlbl = new JLabel("File :");
        gbc.update(gridx = 0, ++gridy);
        panel.add(jlbl, gbc);

        jtfFile = new JTextField();
        jtfFile.setEditable(true);
        gbc.update(++gridx, gridy, GBC.RELATIVE, 1);
        gbc.setFill(GBC.HORIZONTAL).setWeight(1, 0);
        panel.add(jtfFile, gbc);

        jbnFile = new JButton("file");
        jbnFile.addActionListener(this);
        jbnFile.setPreferredSize(new Dimension(100, jbnFile.getPreferredSize().height));
        jbnFile.setMinimumSize(jbnFile.getPreferredSize());
        gbc.update(++gridx, gridy, GBC.REMAINDER, 1);
        panel.add(jbnFile, gbc);

        jbnRead = new JButton(jcmbTypes.getSelectedItem() + " hash > ");
        jbnRead.addActionListener(this);
        jbnRead.setPreferredSize(jbnFile.getPreferredSize());
        jbnRead.setMinimumSize(jbnFile.getMinimumSize());
        gbc.update(gridx = 0, ++gridy, 1, GBC.REMAINDER);
        panel.add(jbnRead, gbc);

        jtfHash = new JTextField();
        jtfHash.setEditable(false);
        gbc.update(++gridx, gridy, GBC.REMAINDER, GBC.REMAINDER);
        gbc.setFill(GBC.HORIZONTAL);
        panel.add(jtfHash, gbc);

        return panel;
    }

    public void actionPerformed(ActionEvent ae) {

        try {
            if (ae.getSource() == jcmbTypes) {
                jbnRead.setText(jcmbTypes.getSelectedItem() + " hash > ");
                validate();
            }
            if (ae.getSource() == jbnFile) {
                int returnVal = fc.showOpenDialog(CryptWin.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String pathFile = fc.getSelectedFile().getPath();
                    jtfInput.setText(pathFile);
                }
            }
            if (ae.getSource() == jbnRead) {
                CryptSwingWorker swingWorker = new CryptSwingWorker(this);
                dialogWait.setContentPane(new JWaitPanel(swingWorker));
                dialogWait.pack();
                swingWorker.execute();

                // La fenetre modale sera visible jusqu'à la fin du traitement
                dialogWait.setVisible(true);
            }
        }
        catch (Exception e) {
            if (logger.isDebugEnabled()) {
                e.printStackTrace();
            }
            else new SimpleException(e).printMessages();
        }
    }
}
