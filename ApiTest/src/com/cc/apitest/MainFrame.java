package com.cc.apitest;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField path;
    private JTextArea requestContent;
    private JTree tree;
    private File scriptDir = null;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode treeNode;
    private MyTreeCell treeCell;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 250, 900, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        path = new JTextField();
        path.setBounds(25, 10, 401, 21);
        contentPane.add(path);

        final JButton exploreButton = new JButton("......");
        exploreButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = chooser.showOpenDialog(exploreButton);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    scriptDir = chooser.getSelectedFile();
                    path.setText(scriptDir.getAbsolutePath());
                }
            }
        });
        exploreButton.setBounds(424, 9, 93, 23);
        contentPane.add(exploreButton);

        JButton showPathButton = new JButton("展示脚本");
        showPathButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String pathName = path.getText().trim();
                if ((!pathName.isEmpty()) && pathName != null) {
                    scriptDir = new File(path.getText().trim());
                    updateTree(scriptDir);
                }
            }

        });
        showPathButton.setBounds(553, 9, 93, 23);
        contentPane.add(showPathButton);

        treeCell = new MyTreeCell();
        treeCell.setText("请选择目录");
        treeNode = new DefaultMutableTreeNode(treeCell);
        treeModel = new DefaultTreeModel(treeNode);
        tree = new JTree(treeModel);
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();
                if (treeNode != null) {
                    MyTreeCell treeCell = (MyTreeCell) treeNode.getUserObject();
                    File file = treeCell.getFile();
                    if (file != null && file.isFile()) {
                        requestContent.setText(readText(treeCell.getFile()));
                    }
                }
            }

        });
        createJScrollPane(tree, "脚本路径", new int[] { 25, 59, 241, 445 });

        requestContent = new JTextArea();
        requestContent.setTabSize(4);
        createJScrollPane(requestContent, "脚本内容", new int[] { 304, 76, 255, 428 });

        JTextArea responseContent = new JTextArea();
        responseContent.setTabSize(4);
        createJScrollPane(responseContent, "响应内容", new int[] { 618, 76, 255, 425 });

        JButton requestSave = new JButton("保存");
        requestSave.setBounds(304, 528, 151, 23);
        contentPane.add(requestSave);

        JButton requestSend = new JButton("发送请求");
        requestSend.setBounds(513, 528, 189, 23);
        contentPane.add(requestSend);
    }

    private void updateTree(File dir) {
        tree.removeAll();
        treeNode.removeAllChildren();
        if (dir.isDirectory()) {
            treeCell.setText(dir.getName());
            treeCell.setFile(dir);
            loadTree(treeNode, dir);
        } else {
            treeCell.setText("请选择正确的脚本目录");
        }
        treeModel = new DefaultTreeModel(treeNode);
        tree.setModel(treeModel);
    }

    private class MyTreeCell extends DefaultTreeCellRenderer {
        private static final long serialVersionUID = 1L;
        private File file = null;

        protected File getFile() {
            return file;
        }

        protected void setFile(File file) {
            this.file = file;
        }

        @Override
        public String toString() {
            return getText();
        }
    }

    private void loadTree(DefaultMutableTreeNode parent, File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                MyTreeCell forTreeCell = new MyTreeCell();
                forTreeCell.setText(file.getName());
                forTreeCell.setFile(file);
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(forTreeCell);
                parent.add(child);
                if (file.isDirectory()) {
                    loadTree(child, file);
                }
            }
        }
    }

    public String readText(File file) {
        FileInputStream fis = null;
        byte[] buffer = null;
        try {
            fis = new FileInputStream(file);
            buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
        } catch (IOException e) {
        }
        return new String(buffer);
    }

    private void createJScrollPane(Component component, String title, int[] bounds) {
        JScrollPane jScrollPane = new JScrollPane(component);
        jScrollPane.setBorder(new TitledBorder(null, title, TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP, null, null));
        jScrollPane.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        component.setSize(bounds[2], bounds[3]);
        contentPane.add(jScrollPane);
    }
}
