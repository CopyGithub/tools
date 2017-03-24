package com.cc.android.tools;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame implements ItemListener, ActionListener {
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HIGHT = 720;
    private static final long serialVersionUID = 1L;

    private JPanel mContentPane;
    private JComboBox<String> mDevice;
    private JPanel mFeaturePane;

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
        setBounds((int) ((screensize.getWidth() - FRAME_WIDTH) / 2), (int) ((screensize.getHeight() - FRAME_HIGHT) / 2),
                FRAME_WIDTH, FRAME_HIGHT);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu featureList = new JMenu(Const.FEATURE_LIST);
        menuBar.add(featureList);

        JMenuItem deviceManage = new JMenuItem(Const.DEVICE_MANAGE);
        deviceManage.addActionListener(this);
        featureList.add(deviceManage);

        JMenuItem appManage = new JMenuItem(Const.APP_MANAGE);
        appManage.addActionListener(this);
        featureList.add(appManage);

        JMenuItem fileManage = new JMenuItem(Const.FILE_MANAGE);
        fileManage.addActionListener(this);
        featureList.add(fileManage);

        JMenuItem apkManage = new JMenuItem(Const.APK_MANAGE);
        apkManage.addActionListener(this);
        featureList.add(apkManage);

        JMenuItem logManage = new JMenuItem(Const.LOG_MANAGE);
        logManage.addActionListener(this);
        featureList.add(logManage);

        JMenu settings = new JMenu(Const.SETTINGS);
        menuBar.add(settings);

        JMenuItem root = new JMenuItem(Const.ROOT_PERMISSION);
        root.addActionListener(this);
        settings.add(root);

        JMenuItem about = new JMenuItem(Const.ABOUT);
        about.addActionListener(this);
        settings.add(about);

        mContentPane = new JPanel();
        mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mContentPane.setLayout(null);
        setContentPane(mContentPane);

        mDevice = new JComboBox<String>();
        mDevice.addItemListener(this);
        mDevice.setBounds(50, 16, 150, 23);
        mDevice.addItem(Const.NO_DEVICE);
        mContentPane.add(mDevice);

        JButton flashDevice = new JButton(Const.FALSH_DEVICE);
        flashDevice.addActionListener(this);
        flashDevice.setBounds(250, 16, 100, 23);
        mContentPane.add(flashDevice);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (Const.FALSH_DEVICE.equals(command)) {
            flashDevice();
        } else if (Const.DEVICE_MANAGE.equals(command)) {
            deviceManage();
        } else if (Const.APP_MANAGE.equals(command)) {
            appManage();
        } else if (Const.FILE_MANAGE.equals(command)) {
            fileManage();
        } else if (Const.APK_MANAGE.equals(command)) {
            apkManage();
        } else if (Const.LOG_MANAGE.equals(command)) {
            logManage();
        } else if (Const.ROOT_PERMISSION.equals(command)) {
            rootPermisson();
        } else if (Const.ABOUT.equals(command)) {
            about();
        }
    }

    private void flashDevice() {
        // TODO Auto-generated method stub
    }

    private void deviceManage() {
        // TODO Auto-generated method stub

    }

    private void appManage() {
        // TODO Auto-generated method stub

    }

    private void fileManage() {
        // TODO Auto-generated method stub

    }

    private void apkManage() {
        // TODO Auto-generated method stub

    }

    private void logManage() {
        // TODO Auto-generated method stub

    }

    private void rootPermisson() {
        // TODO Auto-generated method stub

    }

    private void about() {
        // TODO Auto-generated method stub
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO
    }
}