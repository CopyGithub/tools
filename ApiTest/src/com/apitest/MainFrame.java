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
import javax.swing.DefaultComboBoxModel;
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

import org.json.JSONException;
import org.json.JSONObject;

import com.cashreward.ParamEncodeAndDecode;
import com.cc.http.HttpResponser;
import com.cc.io.FileOperation;
import com.cc.json.Json;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;
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
    private JPanel mContentPane;
    private JTextField mPath;
    private JComboBox<MyTreeCell> mConfigList;
    private JScrollPane mJScrollTree;
    private JTextArea mRequest;
    private JTextArea mResponse;
    private JTextArea mActualRequest;
    private JTree mTree;
    private JPopupMenu mPopupMenu;

    private DefaultMutableTreeNode mTreeNode;
    private DefaultComboBoxModel<MyTreeCell> mConfigs = new DefaultComboBoxModel<>();
    private JSONObject mConfigJs;
    private DefaultMutableTreeNode mSelectedTreeNode;
    private MyTreeCell mSelectedTreeCell;

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

        JButton exploreButton = new JButton(Const.EXPLORE);
        exploreButton.addActionListener(this);
        exploreButton.setBounds(510, 17, 30, 21);
        mContentPane.add(exploreButton);

        JButton flashScriptButton = new JButton(Const.FLASH_SCRIPT);
        flashScriptButton.addActionListener(this);
        flashScriptButton.setBounds(600, 16, 100, 23);
        mContentPane.add(flashScriptButton);

        mConfigList = new JComboBox<>(mConfigs);
        mConfigList.addItemListener(this);
        mConfigList.setBounds(740, 16, 150, 23);
        mContentPane.add(mConfigList);
        MyTreeCell treeCell = new MyTreeCell();
        treeCell.setText(Const.NO_INI);
        mConfigList.addItem(treeCell);

        JButton decodeButton = new JButton(Const.DECODE_DATA);
        decodeButton.addActionListener(this);
        decodeButton.setBounds(930, 16, 150, 23);
        mContentPane.add(decodeButton);

        mJScrollTree = createJScrollPane(null, Const.SCRIPT_DIR, new int[] { 20, 60, 160, 500 });

        mRequest = new JTextArea();
        mRequest.setLineWrap(true);
        mRequest.setTabSize(4);
        mRequest.addKeyListener(mainFrameListener);
        createJScrollPane(mRequest, Const.SCRIPT_CONTENT, new int[] { 195, 60, 400, 500 });

        mResponse = new JTextArea();
        mResponse.setLineWrap(true);
        mRequest.setTabSize(4);
        mResponse.addKeyListener(mainFrameListener);
        createJScrollPane(mResponse, Const.RESPONSE_CONTENT, new int[] { 610, 60, 640, 500 });

        mActualRequest = new JTextArea();
        mActualRequest.setEditable(false);
        mActualRequest.setLineWrap(true);
        createJScrollPane(mActualRequest, Const.REQUEST_DETAIL, new int[] { 20, 560, 575, 100 });

        JButton requestSave = new JButton(Const.SAVE);
        requestSave.addActionListener(this);
        requestSave.setBounds(700, 600, 150, 23);
        mContentPane.add(requestSave);

        JButton requestSend = new JButton(Const.SEND);
        requestSend.addActionListener(this);
        requestSend.setBounds(930, 600, 150, 23);
        mContentPane.add(requestSend);

        mPopupMenu = new JPopupMenu();
        for (String menuItemName : Const.POPUP_MENU) {
            JMenuItem menuItem = new JMenuItem(menuItemName);
            menuItem.addActionListener(this);
            mPopupMenu.add(menuItem);
        }
    }

    /**
     * 监控点击事件
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (Const.EXPLORE.equals(command)) {
            explore();
        } else if (Const.FLASH_SCRIPT.equals(command)) {
            flashScript();
        } else if (Const.DECODE_DATA.equals(command)) {
            decodeData();
        } else if (Const.POPUP_MENU[0].equals(command)) {
            createFile(false);
        } else if (Const.POPUP_MENU[1].equals(command)) {
            createFile(true);
        } else if (Const.POPUP_MENU[2].equals(command)) {
            deleteFile();
        } else if (Const.POPUP_MENU[3].equals(command)) {
            renameFile();
        } else if (Const.SAVE.equals(command)) {
            saveContent();
        } else if (Const.SEND.equals(command)) {
            sendOrExec();
        }
    }

    /**
     * 浏览文件夹
     */
    private void explore() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(mContentPane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            mPath.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * 刷新脚本文件和配置文件
     */
    private void flashScript() {
        boolean flag = true;
        if (mTree != null) {
            flag = false;
            mTreeNode.removeAllChildren();
            mTree.removeAll();
        }
        String pathName = mPath.getText().trim();
        if (pathName == null || pathName.length() == 0) {
            showOptionDialog(Const.ERROR, Const.ERROR_SCRIPT_DIR, JOptionPane.CLOSED_OPTION);
            return;
        }
        File scriptDir = new File(pathName);
        if (!scriptDir.isDirectory()) {
            showOptionDialog(Const.ERROR, Const.ERROR_SCRIPT_DIR, JOptionPane.CLOSED_OPTION);
            return;
        }
        mConfigs.removeAllElements();
        MyTreeCell treeCell = new MyTreeCell();
        treeCell.setText(scriptDir.getName());
        treeCell.setFile(scriptDir);
        mTreeNode = new DefaultMutableTreeNode(treeCell);
        loadTree(mTreeNode, scriptDir, Const.SCRIPT_SUFFIX);
        DefaultTreeModel treeModel = new DefaultTreeModel(mTreeNode);
        if (flag) {
            mTree = new JTree(treeModel);
            mJScrollTree.setViewportView(mTree);
            mTree.setSize(mJScrollTree.getSize());
            mTree.addTreeSelectionListener(this);
            mTree.addMouseListener(new MyMouseListener());
        } else {
            mTree.setModel(treeModel);
            mTree.updateUI();
        }
        if (mConfigs.getSize() == 0) {
            MyTreeCell myTreeCell = new MyTreeCell();
            myTreeCell.setText(Const.NO_INI);
            mConfigList.addItem(myTreeCell);
        }
    }

    /**
     * 循环加载所有以{@code suffix}结尾的文件到{@link DefaultMutableTreeNode}}父节点上 </br>
     * 并将配置文件存储到{@code mConfigs}中
     * 
     * @param parent
     * @param dir
     * @param suffix
     */
    private void loadTree(DefaultMutableTreeNode parent, File dir, String suffix) {
        File[] files = dir.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            if (file.isDirectory() || fileName.endsWith(suffix)) {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode();
                MyTreeCell treeCell = new MyTreeCell();
                treeCell.setText(fileName);
                treeCell.setFile(file);
                child.setUserObject(treeCell);
                parent.add(child);
                if ("apiconfig".equals(file.getParentFile().getName())
                        && !"default.txt".equals(file.getName())) {
                    mConfigs.addElement(treeCell);
                }
                if (file.isDirectory()) {
                    loadTree(child, file, suffix);
                }
            }
        }
    }

    /**
     * 显示确认弹窗信息
     * 
     * @param title
     *            标题
     * @param description
     *            描述
     * @param option
     *            可选的按钮类型{@link JOptionPane},如:{@code JOptionPane.OK_OPTION}
     * @return
     */
    private int showOptionDialog(String title, String description, int option) {
        return JOptionPane.showOptionDialog(mContentPane, description, title, option,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);
    }

    /**
     * 创建一个被{@link JScrollPane}包裹的控件
     * 
     * @param component
     *            {@code null}则创建一个空的{@link JScrollPane}
     * @param title
     * @param bounds
     * @return
     */
    private JScrollPane createJScrollPane(Component component, String title, int[] bounds) {
        JScrollPane jScrollPane;
        if (component == null) {
            jScrollPane = new JScrollPane();
        } else {
            jScrollPane = new JScrollPane(component);
            component.setSize(bounds[2], bounds[3]);
        }
        jScrollPane.setBorder(new TitledBorder(null, title, TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP, null, null));
        jScrollPane.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        mContentPane.add(jScrollPane);
        return jScrollPane;
    }

    /**
     * {@link JTree}被选择值变更的监听
     */
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        mSelectedTreeNode = (DefaultMutableTreeNode) mTree.getLastSelectedPathComponent();
        if (mSelectedTreeNode != null) {
            mSelectedTreeCell = (MyTreeCell) mSelectedTreeNode.getUserObject();
            File file = mSelectedTreeCell.getFile();
            if (file != null && file.isFile()) {
                String read = null;
                try {
                    read = FileOperation.readText(mSelectedTreeCell.getFile(), "utf-8");
                } catch (UnsupportedEncodingException e1) {
                }
                mRequest.setText(Json.sortJs(read));
            }
        }
    }

    /**
     * 配置文件变更监听
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            MyTreeCell treeCell = (MyTreeCell) mConfigs.getSelectedItem();
            File file = treeCell.getFile();
            if (file != null) {
                try {
                    mConfigJs = new JSONObject(FileOperation.readText(file, "utf-8"));
                    File defaultConfig = new File(
                            file.getParent() + File.separator + "default.txt");
                    if (defaultConfig.exists()) {
                        JSONObject defaultJs = new JSONObject(
                                FileOperation.readText(defaultConfig, "utf-8"));
                        Set<String> keys = mConfigJs.keySet();
                        for (String key : keys) {
                            if (defaultJs.isNull(key)) {
                                defaultJs.remove(key);
                            }
                            defaultJs.putOpt(key, mConfigJs.get(key));
                        }
                        mConfigJs = defaultJs;
                    }
                } catch (JSONException | UnsupportedEncodingException e1) {
                    showOptionDialog(Const.ERROR, Const.ERROR_INI, JOptionPane.CLOSED_OPTION);
                    mConfigJs = null;
                }
            } else {
                mConfigJs = null;
            }
        }
    }

    /**
     * 解密数据
     */
    private void decodeData() {
        if (mConfigJs == null) {
            showOptionDialog(Const.ERROR, Const.ERROR_INI, JOptionPane.CLOSED_OPTION);
            return;
        }
        ParamEncodeAndDecode.key = mConfigJs.getString("key");
        String content = mRequest.getText();
        int type = Integer.valueOf(content.substring(0, 1));
        String result = "";
        try {
            result = ParamEncodeAndDecode.decode(content.substring(1, content.length()), type);
        } catch (Exception e) {
        }
        mResponse.setText(Json.sortJs(result));
    }

    /**
     * 保存请求框的内容
     */
    private void saveContent() {
        if (mSelectedTreeCell != null) {
            File file = mSelectedTreeCell.getFile();
            if (file != null && file.isFile()) {
                try {
                    FileOperation.writeText(Json.sortJs(mRequest.getText()), file, false, "utf-8",
                            1);
                    return;
                } catch (IOException e) {
                }
            }
        }
        showOptionDialog(Const.ERROR, Const.ERROR_SAVE_FILE, JOptionPane.CLOSED_OPTION);
    }

    /**
     * 发送或执行请求
     */
    private void sendOrExec() {
        mResponse.setText(Const.WAIT_FOR_RESULT);
        try {
            ApiRequester requester = new ApiRequester();
            HttpResponser responser = requester.exec();
            mResponse.setText(responser.getFormatResponse());
            String body = requester.getBody();
            String headers = requester.getHeaders();
            mActualRequest.setText(
                    requester.getUrl() + (headers == null ? "" : "\n" + Json.sortJs(headers) + "\n")
                            + (body == null ? "" : "\n" + body));
        } catch (Exception e) {
            e.printStackTrace();
            showOptionDialog(Const.ERROR, e.getMessage(), JOptionPane.CLOSED_OPTION);
        }
    }

    /**
     * 监听鼠标事件
     * 
     * @author chchen
     *
     */
    public class MyMouseListener extends MainFrameListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                TreePath treePath = mTree.getClosestPathForLocation(mouseX, mouseY);
                if (treePath != null) {
                    mTree.setSelectionPath(treePath);
                    mPopupMenu.show(mTree, mouseX, mouseY);
                }
            }
        }
    }

    /**
     * 创建文件或文件夹，并同步更新{@link JTree}
     * 
     * @param dir
     */
    private void createFile(boolean dir) {
        File parentDir = mSelectedTreeCell.getFile();
        DefaultMutableTreeNode parentTreeNode;
        if (parentDir.isFile()) {
            parentDir = parentDir.getParentFile();
            parentTreeNode = (DefaultMutableTreeNode) mSelectedTreeNode.getParent();
        } else {
            parentTreeNode = mSelectedTreeNode;
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
            if (!dir && !fileName.endsWith(Const.SCRIPT_SUFFIX)) {
                fileName = fileName + Const.SCRIPT_SUFFIX;
            }
            MyTreeCell treeCell = new MyTreeCell();
            treeCell.setText(fileName);
            File path = new File(parentDir.getAbsolutePath() + File.separator + fileName);
            treeCell.setFile(path);
            FileOperation.fileCreate(path, dir, false);
            DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(treeCell);
            parentTreeNode.add(treeNode);
            mTree.updateUI();
        }
    }

    /**
     * 显示可输入弹窗信息
     * 
     * @param title
     * @param description
     * @param defaultValue
     * @return
     */
    private String showInputDialog(String title, String description, String defaultValue) {
        Object object = JOptionPane.showInputDialog(mContentPane, description, title,
                JOptionPane.INFORMATION_MESSAGE, null, null, defaultValue);
        if (object != null) {
            String value = ((String) object).trim();
            if (value.length() != 0) {
                return value;
            }
        }
        return null;
    }

    /**
     * 删除文件，并同步更新{@link JTree}
     */
    private void deleteFile() {
        File file = mSelectedTreeCell.getFile();
        int option = showOptionDialog(Const.POPUP_MENU[2],
                String.format(Const.DELETE_CONFIRM, file.getAbsolutePath()),
                JOptionPane.OK_CANCEL_OPTION);
        if (JOptionPane.OK_OPTION == option && FileOperation.fileDel(file)) {
            DefaultMutableTreeNode parentTreeNode = (DefaultMutableTreeNode) mSelectedTreeNode
                    .getParent();
            parentTreeNode.remove(mSelectedTreeNode);
            mTree.updateUI();
        }
    }

    /**
     * 重命名文件，并同步更新{@link JTree}
     */
    private void renameFile() {
        File file = mSelectedTreeCell.getFile();
        String oldName = file.getName();
        String newName = showInputDialog(Const.POPUP_MENU[3], Const.INPUT_NEW_FILE_NAME, oldName);
        if (newName == null) {
            return;
        }
        if (file.isFile() && !newName.endsWith(Const.SCRIPT_SUFFIX)) {
            newName += Const.SCRIPT_SUFFIX;
        }
        if (FileOperation.fileRename(file, newName)) {
            mSelectedTreeCell.setText(newName);
            mSelectedTreeCell.setFile(new File(file.getParent() + File.separator + newName));
            mTree.updateUI();
        }
    }

}