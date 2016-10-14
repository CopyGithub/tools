package com.cc.apitest;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField pathTextField;
    private JTextField requestTitle;
    private JTextField responseTitle;
    private JTree tree;
    private DefaultMutableTreeNode top = new DefaultMutableTreeNode("脚本目录");
    private File scriptDir = null;

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

        pathTextField = new JTextField();
        pathTextField.setBounds(25, 10, 401, 21);
        contentPane.add(pathTextField);

        final JButton exploreButton = new JButton("...");
        exploreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = chooser.showOpenDialog(exploreButton);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    scriptDir = chooser.getSelectedFile();
                    pathTextField.setText(scriptDir.getAbsolutePath());
                }
            }
        });
        exploreButton.setBounds(424, 9, 93, 23);
        contentPane.add(exploreButton);

        JButton showPathButton = new JButton("展示脚本");
        showPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tree.removeAll();
                tree.setModel(new DefaultTreeModel(getFileTree(scriptDir)));
            }
        });
        showPathButton.setBounds(553, 9, 93, 23);
        contentPane.add(showPathButton);

        tree = new JTree(top);
        tree.setBorder(new TitledBorder(null, "脚本路径", TitledBorder.LEFT, TitledBorder.ABOVE_TOP,
                null, null));
        tree.setBounds(25, 59, 241, 445);
        contentPane.add(tree);

        requestTitle = new JTextField();
        requestTitle.setText("脚本内容");
        requestTitle.setBounds(304, 57, 255, 21);
        contentPane.add(requestTitle);

        JTextArea requestContent = new JTextArea();
        requestContent.setTabSize(4);
        requestContent.setBounds(304, 76, 255, 428);
        contentPane.add(requestContent);

        responseTitle = new JTextField();
        responseTitle.setText("响应内容");
        responseTitle.setBounds(618, 57, 255, 21);
        contentPane.add(responseTitle);

        JTextArea responseContent = new JTextArea();
        requestContent.setTabSize(4);
        responseContent.setBounds(618, 76, 255, 425);
        contentPane.add(responseContent);

        JButton requestSave = new JButton("保存");
        requestSave.setBounds(304, 528, 151, 23);
        contentPane.add(requestSave);

        JButton requestSend = new JButton("发送请求");
        requestSend.setBounds(513, 528, 189, 23);
        contentPane.add(requestSend);
    }

    private DefaultMutableTreeNode getFileTree(File dir) {
        tree.removeAll();
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("请选择正确的脚本目录");
        if (dir != null) {
            String[] node = new String[2];
            top = new DefaultMutableTreeNode(dir.getName());
        }
        return top;
    }
}
