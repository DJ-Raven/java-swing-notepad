/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import say.swing.JFontChooser;

/**
 *
 * @author RAVEN
 */
public class Main extends javax.swing.JFrame {

    private File currentFile;

    public Main(File file) {
        initComponents();
        setUpExtension();
        setIconImage(new ImageIcon(getClass().getResource("/icon/icon.png")).getImage());
        setTitle("Untitled-Notepad");
        new DropTarget(txt, new DropTargetAdapter() {
            @Override
            public void dragOver(DropTargetDragEvent dtde) {
                dtde.acceptDrag(DnDConstants.ACTION_COPY);
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                Transferable transfer = dtde.getTransferable();
                DataFlavor data[] = transfer.getTransferDataFlavors();
                for (DataFlavor d : data) {
                    try {
                        List<File> list = (List<File>) transfer.getTransferData(d);
                        currentFile = list.get(0);
                        open(currentFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if (file != null) {
            open(file);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txt = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuOpen = new javax.swing.JMenuItem();
        menuSave = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuFont = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jScrollPane1.setBorder(null);
        jScrollPane1.setViewportView(txt);

        jMenu1.setText("File");

        menuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuOpen.setText("Open");
        menuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpenActionPerformed(evt);
            }
        });
        jMenu1.add(menuOpen);

        menuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuSave.setText("Save");
        menuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSaveActionPerformed(evt);
            }
        });
        jMenu1.add(menuSave);
        jMenu1.add(jSeparator1);

        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        menuFont.setText("Font              ");
        menuFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFontActionPerformed(evt);
            }
        });
        jMenu2.add(menuFont);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuFontActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFontActionPerformed
        JFontChooser ch = new JFontChooser();
        ch.setSelectedFont(txt.getFont());
        int opt = ch.showDialog(this);
        if (opt == JFontChooser.OK_OPTION) {
            txt.setFont(ch.getSelectedFont());
        }
    }//GEN-LAST:event_menuFontActionPerformed

    private void menuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpenActionPerformed
        JFileChooser ch = new JFileChooser();
        ch.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                String name = f.getName();
                return f.isDirectory() || name.endsWith(".my_note") || name.endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return ".my_note | .txt";
            }
        });
        int opt = ch.showOpenDialog(this);
        if (opt == JFileChooser.APPROVE_OPTION) {
            open(ch.getSelectedFile());
        }
    }//GEN-LAST:event_menuOpenActionPerformed

    private void menuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveActionPerformed
        if (currentFile == null) {
            JFileChooser ch = new JFileChooser();
            ch.setSelectedFile(new File("*.my_note"));
            int opt = ch.showSaveDialog(this);
            if (opt == JFileChooser.APPROVE_OPTION) {
                File file = ch.getSelectedFile();
                if (!file.getName().endsWith(".my_note")) {
                    file = new File(file.getAbsolutePath() + ".my_note");
                }
                if (file.exists()) {
                    int o = JOptionPane.showConfirmDialog(this, "File " + file.getName() + "Already Exit\nDo you want to replace it ?", "Save", JOptionPane.YES_NO_OPTION);
                    if (o == JOptionPane.YES_OPTION) {
                        currentFile = file;
                        save(currentFile);
                    }
                } else {
                    currentFile = file;
                    save(currentFile);
                }
            }
        } else {
            save(currentFile);
        }
    }//GEN-LAST:event_menuSaveActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Are you sure to exit ?", "Exit", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_menuExitActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        menuExitActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing

    private void save(File file) {
        try {
            FileWriter write = new FileWriter(file);
            write.write(txt.getText());
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void open(File file) {
        try {
            FileReader read = new FileReader(file);
            txt.setText("");
            int i;
            while ((i = read.read()) != -1) {
                txt.setText(txt.getText() + (char) i);
            }
            setTitle(file.getName() + "-Notepad");
            currentFile = file;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        FlatDarkPurpleIJTheme.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                File file = null;
                if (args.length == 1) {
                    file = new File(args[0]);
                }
                new Main(file).setVisible(true);
            }
        });
    }

    private void setUpExtension() {
        String extensions = ".my_note";
        String path = "D:\\Raven\\my_notepad_new\\";
        String text = "Open with my Notepad New";
        String appName = "my_notepad.exe";
        if (!Advapi32Util.registryKeyExists(WinReg.HKEY_CLASSES_ROOT, extensions)) {
            //  For all file
            Advapi32Util.registryCreateKey(WinReg.HKEY_CLASSES_ROOT, "*\\shell", text);
            Advapi32Util.registrySetStringValue(WinReg.HKEY_CLASSES_ROOT, "*\\shell\\" + text, "", text);
            Advapi32Util.registryCreateKey(WinReg.HKEY_CLASSES_ROOT, "*\\shell\\" + text, "command");
            Advapi32Util.registrySetStringValue(WinReg.HKEY_CLASSES_ROOT, "*\\shell\\" + text, "icon", path + "icon\\icon.ico");
            Advapi32Util.registrySetStringValue(WinReg.HKEY_CLASSES_ROOT, "*\\shell\\" + text + "\\command", "", "\"" + path + appName + "\" \"%1\"");
            //  For Your file
            Advapi32Util.registryCreateKey(WinReg.HKEY_CLASSES_ROOT, extensions);
            Advapi32Util.registryCreateKey(WinReg.HKEY_CLASSES_ROOT, extensions, "DefaultIcon");
            Advapi32Util.registryCreateKey(WinReg.HKEY_CLASSES_ROOT, extensions, "shell");
            Advapi32Util.registryCreateKey(WinReg.HKEY_CLASSES_ROOT, extensions + "\\shell", text);
            Advapi32Util.registryCreateKey(WinReg.HKEY_CLASSES_ROOT, extensions + "\\shell\\" + text, "command");
            Advapi32Util.registrySetStringValue(WinReg.HKEY_CLASSES_ROOT, extensions + "\\shell\\" + text, "icon", path + "icon\\icon.ico");
            Advapi32Util.registrySetStringValue(WinReg.HKEY_CLASSES_ROOT, extensions + "\\DefaultIcon", "", path + "icon\\icon.ico");
            Advapi32Util.registrySetStringValue(WinReg.HKEY_CLASSES_ROOT, extensions + "\\shell\\" + text + "\\command", "", "\"" + path + appName + "\" \"%1\"");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenuItem menuFont;
    private javax.swing.JMenuItem menuOpen;
    private javax.swing.JMenuItem menuSave;
    private javax.swing.JTextPane txt;
    // End of variables declaration//GEN-END:variables
}
