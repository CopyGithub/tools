package com.cc.apitest;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.json.JSONObject;

import com.cc.apitest.HttpRequester.ResponseResult;
import com.params.convert.ParamEncodeAndDecode;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HIGHT = 720;

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField path;
    private JTextArea requestContent;
    private JTextArea responseContent;
    private JTree tree;
    private File scriptDir = null;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode treeNode;
    private DefaultMutableTreeNode selectedTreeNode;
    private MyTreeCell treeCell;
    private MyTreeCell selectedTreeCell;
    private JPopupMenu popupMenu;
    private JMenuItem addFileItem;
    private JMenuItem addDirItem;
    private JMenuItem delItem;
    private JMenuItem editItem;
    private JComboBox<String> configList;
    private ArrayList<File> configFiles = new ArrayList<>();
    protected static JSONObject configJs;

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
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds((int) ((screensize.getWidth() - FRAME_WIDTH) / 2),
                (int) ((screensize.getHeight() - FRAME_HIGHT) / 2), FRAME_WIDTH, FRAME_HIGHT);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel pathlabe = new JLabel("路径：");
        pathlabe.setHorizontalAlignment(SwingConstants.CENTER);
        pathlabe.setBounds(70, 20, 40, 15);
        contentPane.add(pathlabe);

        path = new JTextField();
        path.setBounds(110, 17, 400, 21);
        contentPane.add(path);

        final JButton exploreButton = new JButton("...");
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
        exploreButton.setBounds(510, 17, 30, 21);
        contentPane.add(exploreButton);

        JButton showPathButton = new JButton("刷新脚本");
        showPathButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String pathName = path.getText().trim();
                if (pathName != null && pathName.length() > 0) {
                    scriptDir = new File(path.getText().trim());
                    refreshTree(scriptDir);
                }
            }

        });
        showPathButton.setBounds(600, 16, 100, 23);
        contentPane.add(showPathButton);

        configList = new JComboBox<>();
        configList.addItem("请选择配置文件");
        configList.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    initConfig(e.getItem().toString());
                }
            }
        });
        configList.setBounds(740, 16, 150, 23);
        contentPane.add(configList);

        JButton decodeButton = new JButton("解密数据");
        decodeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String content = requestContent.getText();
                int type = Integer.valueOf(content.substring(0, 1));
                String result = ParamEncodeAndDecode.decode(content.substring(1, content.length()),
                        type);
                responseContent.setText(JsonOperation.sortJs(result));
            }
        });
        decodeButton.setBounds(930, 16, 150, 23);
        contentPane.add(decodeButton);

        treeCell = new MyTreeCell();
        treeCell.setText("请选择目录");
        treeNode = new DefaultMutableTreeNode(treeCell);
        treeModel = new DefaultTreeModel(treeNode);
        tree = new JTree(treeModel);
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                selectedTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedTreeNode != null) {
                    selectedTreeCell = (MyTreeCell) selectedTreeNode.getUserObject();
                    File file = selectedTreeCell.getFile();
                    if (file != null && file.isFile()) {
                        String read = FileOperation.readText(selectedTreeCell.getFile());
                        requestContent.setText(JsonOperation.sortJs(read));
                    }
                }
            }
        });
        tree.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    TreePath treePath = tree.getClosestPathForLocation(e.getX(), e.getY());
                    if (treePath != null) {
                        tree.setSelectionPath(treePath);
                        popupMenu.show(tree, e.getX(), e.getY());
                    }
                }
            }
        });
        createJScrollPane(tree, "脚本树", new int[] { 20, 60, 160, 500 });

        popupMenu = new JPopupMenu();
        addFileItem = new JMenuItem("新建文本文件");
        addFileItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File parentDir = selectedTreeCell.getFile();
                if (parentDir != null) {
                    parentDir = parentDir.isDirectory() ? parentDir : parentDir.getParentFile();
                    String fileName = showInputDialog("请输入文件名", "新建文本文件", "新建文本文件");
                    if (fileName != null) {
                        FileOperation.createFileOrDir(new File(
                                parentDir.getAbsolutePath() + File.separator + fileName + ".txt"),
                                false, false);
                        refreshTree(scriptDir);
                    }
                }
            }
        });
        addDirItem = new JMenuItem("新建目录");
        addDirItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File parentDir = selectedTreeCell.getFile();
                if (parentDir != null) {
                    parentDir = parentDir.isDirectory() ? parentDir : parentDir.getParentFile();
                    String fileName = showInputDialog("请输入目录名", "新建目录", "新建目录");
                    if (fileName != null) {
                        FileOperation.createFileOrDir(
                                new File(parentDir.getAbsolutePath() + File.separator + fileName),
                                true, false);
                        refreshTree(scriptDir);
                    }
                }
            }
        });
        delItem = new JMenuItem("删除");
        delItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File parentDir = selectedTreeCell.getFile();
                if (parentDir != null) {
                    if (showOptionDialog("删除", "你确定要删除该文件或文件夹么？") == JOptionPane.OK_OPTION) {
                        FileOperation.deleteFileOrDir(parentDir);
                        refreshTree(scriptDir);
                    }
                }
            }
        });
        editItem = new JMenuItem("重命名");
        editItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File file = selectedTreeCell.getFile();
                if (file != null) {
                    String oldName = file.getName();
                    String fileName = showInputDialog("请输入新文件名", "重命名", oldName);
                    if (FileOperation.rename(file, fileName)) {
                        refreshTree(scriptDir);
                    }
                }
            }
        });
        popupMenu.add(addFileItem);
        popupMenu.add(addDirItem);
        popupMenu.add(delItem);
        popupMenu.add(editItem);

        requestContent = new JTextArea();
        requestContent.setLineWrap(true);
        createJScrollPane(requestContent, "脚本内容", new int[] { 195, 60, 400, 500 });

        responseContent = new JTextArea();
        responseContent.setLineWrap(true);
        createJScrollPane(responseContent, "响应内容", new int[] { 610, 60, 640, 500 });

        JButton requestSave = new JButton("保存");
        requestSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileOperation.writeText(JsonOperation.sortJs(requestContent.getText()),
                        selectedTreeCell.getFile(), false);
            }
        });
        requestSave.setBounds(330, 600, 150, 23);
        contentPane.add(requestSave);

        JButton requestSend = new JButton("发送请求");
        requestSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HttpRequester requester = new HttpRequester(requestContent.getText());
                ResponseResult responseResult = requester.exec();
                String response = String.valueOf(responseResult.responseBody);
                responseContent.setText(JsonOperation.sortJs(response));
            }
        });
        requestSend.setBounds(800, 600, 150, 23);
        contentPane.add(requestSend);
    }

    private void refreshTree(File dir) {
        configFiles.clear();
        tree.removeAll();
        treeNode.removeAllChildren();
        if (dir.isDirectory()) {
            treeCell.setText(dir.getName());
            treeCell.setFile(dir);
            loadTree(treeNode, dir, ".txt");
        } else {
            treeCell.setText("请选择正确的脚本目录");
        }
        treeModel = new DefaultTreeModel(treeNode);
        tree.updateUI();
        refreshConfigList();
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

    private void loadTree(DefaultMutableTreeNode parent, File dir, String suffix) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if (file.isDirectory() || fileName.endsWith(suffix)) {
                    MyTreeCell forTreeCell = new MyTreeCell();
                    forTreeCell.setText(file.getName());
                    forTreeCell.setFile(file);
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode(forTreeCell);
                    parent.add(child);
                    if ("apiconfig".equals(file.getParentFile().getName())) {
                        configFiles.add(file);
                    }
                    if (file.isDirectory()) {
                        loadTree(child, file, suffix);
                    }
                }
            }
        }
    }

    private void createJScrollPane(Component component, String title, int[] bounds) {
        JScrollPane jScrollPane = new JScrollPane(component);
        jScrollPane.setBorder(new TitledBorder(null, title, TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP, null, null));
        jScrollPane.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        component.setSize(bounds[2], bounds[3]);
        contentPane.add(jScrollPane);
    }

    private void refreshConfigList() {
        configList.removeAllItems();
        for (File file : configFiles) {
            configList.addItem(file.getName());
        }
        if (configList.getItemCount() > 0) {
            configList.setSelectedIndex(0);
        } else {
            configList.addItem("没有内容");
        }
    }

    private void initConfig(String fileName) {
        for (File file : configFiles) {
            if (fileName.equals(file.getName())) {
                configJs = new JSONObject(FileOperation.readText(file));
                ParamEncodeAndDecode.key = configJs.getString("key");
                break;
            }
        }
    }

    private String showInputDialog(String title, String description, String defaultValue) {
        Object object = JOptionPane.showInputDialog(contentPane, description, title,
                JOptionPane.INFORMATION_MESSAGE, null, null, defaultValue);
        if (object != null) {
            String value = ((String) object).trim();
            if (!value.isEmpty()) {
                return value;
            }
        }
        return null;
    }

    private int showOptionDialog(String title, String description) {
        return JOptionPane.showOptionDialog(contentPane, description, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
    }
}
