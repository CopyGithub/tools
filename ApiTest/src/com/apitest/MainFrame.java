package com.apitest;

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
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.json.JSONObject;

import com.cashreward.ParamEncodeAndDecode;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame
        implements ActionListener, TreeSelectionListener, ItemListener {
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HIGHT = 720;
    private static final long serialVersionUID = 1L;

    private MainFrameListener mainFrameListener = new MainFrameListener();
    private static JPanel mContentPane;
    private JButton mExploreButton;
    private JTextField mPath;
    private JTextArea mRequestContent;
    private JTextArea mResponseContent;
    private JTree mTree;
    private File mScriptDir = null;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootTreeNode;
    private DefaultMutableTreeNode selectedTreeNode;
    private DefaultMutableTreeNode parentTreeNode;
    private MyTreeCell treeCell;
    private MyTreeCell selectedTreeCell;
    private JPopupMenu popupMenu;
    private JComboBox<String> configList;
    private JTextArea mSendRequest;
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

        mContentPane = new JPanel();
        mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mContentPane.setLayout(null);
        setContentPane(mContentPane);

        JLabel pathlabe = new JLabel(Const.PATH);
        pathlabe.setHorizontalAlignment(SwingConstants.CENTER);
        pathlabe.setBounds(70, 20, 40, 15);
        mContentPane.add(pathlabe);

        mPath = new JTextField();
        mPath.setBounds(110, 17, 400, 21);
        mContentPane.add(mPath);

        mExploreButton = new JButton(Const.EXPLORE);
        mExploreButton.addActionListener(this);
        mExploreButton.setBounds(510, 17, 30, 21);
        mContentPane.add(mExploreButton);

        JButton showPathButton = new JButton(Const.FLASH_SCRIPT);
        showPathButton.addActionListener(this);
        showPathButton.setBounds(600, 16, 100, 23);
        mContentPane.add(showPathButton);

        configList = new JComboBox<>();
        configList.addItem(Const.SELECT_INI);
        configList.addItemListener(this);
        configList.setBounds(740, 16, 150, 23);
        mContentPane.add(configList);

        JButton decodeButton = new JButton(Const.DCRIPT_DATA);
        decodeButton.addActionListener(this);
        decodeButton.setBounds(930, 16, 150, 23);
        mContentPane.add(decodeButton);

        treeCell = new MyTreeCell();
        treeCell.setText(Const.SELECT_DIR);
        rootTreeNode = new DefaultMutableTreeNode(treeCell);
        treeModel = new DefaultTreeModel(rootTreeNode);
        mTree = new JTree(treeModel);
        mTree.addTreeSelectionListener(this);
        mTree.addMouseListener(new MyMouseListener());
        createJScrollPane(mTree, Const.SCRIPT_DIR, new int[] { 20, 60, 160, 500 });

        popupMenu = new JPopupMenu();
        for (String menuItemName : Const.POPUP_MENU) {
            JMenuItem menuItem = new JMenuItem(menuItemName);
            menuItem.addActionListener(this);
            popupMenu.add(menuItem);
        }

        mRequestContent = new JTextArea();
        mRequestContent.setLineWrap(true);
        mRequestContent.addKeyListener(mainFrameListener);
        createJScrollPane(mRequestContent, Const.SCRIPT_CONTENT, new int[] { 195, 60, 400, 500 });

        mResponseContent = new JTextArea();
        mResponseContent.setLineWrap(true);
        mResponseContent.addKeyListener(mainFrameListener);
        createJScrollPane(mResponseContent, Const.RESPONSE_CONTENT,
                new int[] { 610, 60, 640, 500 });

        mSendRequest = new JTextArea();
        mSendRequest.setEditable(false);
        mSendRequest.setLineWrap(true);
        createJScrollPane(mSendRequest, Const.REQUEST_DETAIL, new int[] { 20, 560, 575, 100 });

        JButton requestSave = new JButton(Const.SAVE_SCRIPT);
        requestSave.addActionListener(this);
        requestSave.setBounds(700, 600, 150, 23);
        mContentPane.add(requestSave);

        JButton requestSend = new JButton(Const.SEND_REQUEST);
        requestSend.addActionListener(this);
        requestSend.setBounds(930, 600, 150, 23);
        mContentPane.add(requestSend);
    }

    private void initTree(File dir) {
        configFiles.clear();
        mTree.removeAll();
        rootTreeNode.removeAllChildren();
        if (dir.isDirectory()) {
            treeCell.setText(dir.getName());
            treeCell.setFile(dir);
            loadTree(rootTreeNode, dir, Const.SCRIPT_SUFFIX);
        } else {
            treeCell.setText(Const.ERROR_SCRIPT_DIR);
        }
        treeModel = new DefaultTreeModel(rootTreeNode);
        mTree.updateUI();
        refreshConfigList();
    }

    private void loadTree(DefaultMutableTreeNode parent, File dir, String suffix) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if (file.isDirectory() || fileName.endsWith(suffix)) {
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode();
                    MyTreeCell forTreeCell = new MyTreeCell();
                    forTreeCell.setText(file.getName());
                    forTreeCell.setFile(file);
                    child.setUserObject(forTreeCell);
                    parent.add(child);
                    if (Const.INI_DIR.equals(file.getParentFile().getName())) {
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
        mContentPane.add(jScrollPane);
    }

    private void refreshConfigList() {
        configList.removeAllItems();
        for (File file : configFiles) {
            configList.addItem(file.getName());
        }
        if (configList.getItemCount() > 0) {
            configList.setSelectedIndex(0);
        } else {
            configList.addItem(Const.NO_INI);
        }
    }

    private void initConfig(String fileName) {
        for (File file : configFiles) {
            if (fileName.equals(file.getName())) {
                configJs = new JSONObject(FileOperation.readText(file));
                // TODO
                ParamEncodeAndDecode.key = configJs.getString("key");
                break;
            }
        }
    }

    private String showInputDialog(String title, String description, String defaultValue) {
        Object object = JOptionPane.showInputDialog(mContentPane, description, title,
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
        return JOptionPane.showOptionDialog(mContentPane, description, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
    }

    public class MyMouseListener extends MainFrameListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                TreePath treePath = mTree.getClosestPathForLocation(e.getX(), e.getY());
                if (treePath != null) {
                    mTree.setSelectionPath(treePath);
                    popupMenu.show(mTree, e.getX(), e.getY());
                }
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            initConfig(e.getItem().toString());
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        selectedTreeNode = (DefaultMutableTreeNode) mTree.getLastSelectedPathComponent();
        mResponseContent.setText("");
        if (selectedTreeNode != null) {
            selectedTreeCell = (MyTreeCell) selectedTreeNode.getUserObject();
            File file = selectedTreeCell.getFile();
            if (file != null && file.isFile()) {
                String read = FileOperation.readText(selectedTreeCell.getFile());
                mRequestContent.setText(JsonOperation.sortJs(read));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (Const.EXPLORE.equals(command)) {
            explore();
        } else if (Const.FLASH_SCRIPT.equals(command)) {
            flashScript();
        } else if (Const.DCRIPT_DATA.equals(command)) {
            decriptData();
        } else if (Const.POPUP_MENU[0].equals(command)) {
            createFile(false);
        } else if (Const.POPUP_MENU[1].equals(command)) {
            createFile(true);
        } else if (Const.POPUP_MENU[2].equals(command)) {
            deleteFile();
        } else if (Const.POPUP_MENU[3].equals(command)) {
            renameFile();
        } else if (Const.SAVE_SCRIPT.equals(command)) {
            saveScript();
        } else if (Const.SEND_REQUEST.equals(command)) {
            sendRequest();
        }
    }

    private void explore() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(mExploreButton);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            mScriptDir = chooser.getSelectedFile();
            mPath.setText(mScriptDir.getAbsolutePath());
        }
    }

    private void flashScript() {
        String pathName = mPath.getText().trim();
        if (pathName != null && pathName.length() > 0) {
            mScriptDir = new File(mPath.getText().trim());
            initTree(mScriptDir);
        }
    }

    private void decriptData() {
        if (ParamEncodeAndDecode.key.length() > 0) {
            String content = mRequestContent.getText();
            int type = Integer.valueOf(content.substring(0, 1));
            String result = "";
            try {
                result = ParamEncodeAndDecode.decode(content.substring(1, content.length()), type);
            } catch (Exception e) {
            }
            mResponseContent.setText(JsonOperation.sortJs(result));
        }
    }

    private void createFile(boolean dir) {
        File parentDir = selectedTreeCell.getFile();
        if (parentDir != null) {
            if (!parentDir.isDirectory()) {
                parentDir = parentDir.getParentFile();
                parentTreeNode = (DefaultMutableTreeNode) selectedTreeNode.getParent();
            } else {
                parentTreeNode = selectedTreeNode;
            }
            String fileName = null;
            if (dir) {
                fileName = showInputDialog(Const.INPUT_DIR_NAME, Const.POPUP_MENU[1],
                        Const.POPUP_MENU[1]);
            } else {
                fileName = showInputDialog(Const.INPUT_FILE_NAME, Const.POPUP_MENU[0],
                        Const.POPUP_MENU[0]);
            }
            if (fileName != null) {
                MyTreeCell treeCell = new MyTreeCell();
                treeCell.setText(fileName);
                fileName = dir ? fileName : fileName + Const.SCRIPT_SUFFIX;
                File path = new File(parentDir.getAbsolutePath() + File.separator + fileName);
                treeCell.setFile(path);
                FileOperation.createFileOrDir(path, dir, false);
                DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(treeCell);
                parentTreeNode.add(treeNode);
                mTree.updateUI();
            }
        }
    }

    private void deleteFile() {
        File parentDir = selectedTreeCell.getFile();
        if (parentDir != null) {
            if (showOptionDialog(Const.POPUP_MENU[2],
                    Const.DELETE_CONFIRM) == JOptionPane.OK_OPTION) {
                FileOperation.deleteFileOrDir(parentDir);
                parentTreeNode = (DefaultMutableTreeNode) selectedTreeNode.getParent();
                parentTreeNode.remove(selectedTreeNode);
                mTree.updateUI();
            }
        }
    }

    private void renameFile() {
        File file = selectedTreeCell.getFile();
        if (file != null) {
            String oldName = file.getName();
            String newName = showInputDialog(Const.INPUT_NEW_FILE_NAME, Const.POPUP_MENU[3],
                    oldName);
            if (FileOperation.rename(file, newName)) {
                if (newName.indexOf(".") < 0) {
                    newName += Const.SCRIPT_SUFFIX;
                }
                selectedTreeCell.setText(newName);
                selectedTreeCell
                        .setFile(new File(file.getAbsolutePath().replace(oldName, newName)));
                mTree.updateUI();
            }
        }
    }

    private void saveScript() {
        if (selectedTreeCell != null) {
            File file = selectedTreeCell.getFile();
            if (file != null && file.isFile()) {
                FileOperation.writeText(JsonOperation.sortJs(mRequestContent.getText()),
                        selectedTreeCell.getFile(), false);
            }
        }
    }

    private void sendRequest() {
        mResponseContent.setText(Const.WAIT_FOR_RESULT);
        try {
            HttpRequester requester = new HttpRequester();
            HttpResponser responser = requester.exec(new JSONObject(mRequestContent.getText()),
                    configJs);
            mResponseContent.setText(responser.getFormatResponse());
            String body = requester.getBody();
            mSendRequest.setText(requester.getUrl() + (body == null ? "" : "\n" + body));
        } catch (Exception e) {
            mResponseContent.setText(e.getMessage());
        }
    }
}