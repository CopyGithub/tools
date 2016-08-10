using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Diagnostics;
using System.Threading;
using System.Timers;
using System.Runtime.Serialization;
using Microsoft.Win32;

namespace DevicesManager
{
    public partial class DeviceManager : Form
    {
        private static int CheckUpDateLock = 0;
        private static object LockObject = new Object();
        private GetDevicesInfo getDevices = new GetDevicesInfo();
        private string[] result = null;
        public static AddDevices addDevice;
        public DeviceManager(string s)
        {
            InitializeComponent();
            this.Text = "设备管理V" + System.Reflection.Assembly.GetExecutingAssembly().GetName().Version.ToString(); 
            Tips.Text = "提示：";
            this.notifyIcon1.Text = "设备管理";
            this.notifyIcon1.Icon = this.Icon;
            this.notifyIcon1.Visible = true;
            this.notifyIcon1.DoubleClick += new System.EventHandler(this.notifyIcon1_MouseDoubleClick);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form1_Closing);
            this.WindowState = (s == null ? FormWindowState.Normal : FormWindowState.Minimized);
            this.ShowInTaskbar = false;
            ThreadPool.RegisterWaitForSingleObject(Program.ProgramStarted, OnProgramStarted, null, -1, false);
            System.Timers.Timer procTimer = new System.Timers.Timer();
            procTimer.Elapsed += new System.Timers.ElapsedEventHandler(CheckUpdatetimer_Elapsed);
            procTimer.Interval = 2000;
            procTimer.Enabled = true;
            procTimer.Start();
        }
        delegate void UpdateListboxDelegate(string[] result);
        // 当收到第二个进程的通知时，显示窗体  
        void OnProgramStarted(object state, bool timeout)
        {
            if (this.InvokeRequired)
            {
                this.Invoke(new WaitOrTimerCallback(OnProgramStarted), state, timeout);
            }
            else
            {
                notifyIcon1_MouseDoubleClick(this, EventArgs.Empty);
            }
        }
        private void showData(string[] result)
        {
            if (!listBox1.InvokeRequired)
            {
                listBox1.Items.Clear();
                foreach (string i in result)
                {
                    this.listBox1.Items.Add(i);
                }
                this.Tips.Text = "提示：设备信息加载可能需要几分钟，请稍后……";
            }
            else
            {
                UpdateListboxDelegate ul = delegate(string[] mm)
                {
                    listBox1.Items.Clear();
                    foreach (string i in mm)
                    {
                        this.listBox1.Items.Add(i);
                    }
                };
                this.listBox1.Invoke(ul, new object[] { result });
            }
        }
        private void CheckUpdatetimer_Elapsed(object sender, ElapsedEventArgs e)
        {
            // 加锁检查更新锁
            lock (LockObject)
            {
                if (CheckUpDateLock == 0) CheckUpDateLock = 1;
                else return;
            }
            result = getDevices.adbDevicesGet();
            //是否刷新列表
            if (getDevices.devicesUninstallList.Length > 0 || getDevices.devicesInstallList.Length > 0)
            {
                this.showData(result);
            }
            //post 设备log
            if (getDevices.devicesInstallList.Length > 0)
            {
                string[] deviceID = getDevices.devicesInstallList.Split(',');
                PostDeviceInfo postDeviceInfo = new PostDeviceInfo();
                foreach (string id in deviceID)
                {
                    postDeviceInfo.PostDeviceLog(id);
                }
            }
            // 解锁更新检查锁
            lock (LockObject)
            {
                CheckUpDateLock = 0;
            }
        }
        private void Form1_Closing(object sender, FormClosingEventArgs e)
        {
            this.ShowInTaskbar = false;
            this.Hide();
            e.Cancel = true;
        }
        private void notifyIcon1_MouseDoubleClick(object sender, System.EventArgs e)
        {
            this.Show();
            this.ShowInTaskbar = true;
            this.WindowState = FormWindowState.Normal;
            this.Activate();
        }
        private void button1_Click(object sender, EventArgs e)
        {
            this.Tips.Text = "提示：设备信息加载需要几分钟，请稍后……";
            if (this.listBox1.SelectedItem == null)
            {
                MessageBox.Show("请选择设备！");
                return;
            }
            string deviceId = this.listBox1.SelectedItem.ToString().Split('\t')[0];
            Debug.WriteLine("安装设备：" + deviceId);
            if (deviceId == null || deviceId.Length == 0 || deviceId.Contains("List"))
            {
                MessageBox.Show("请选择设备！");
                return;
            }
            if (addDevice == null)
            {
                addDevice = new AddDevices(deviceId);
                addDevice.Show();
                this.Tips.Text = "提示：设备信息加载完成";
            }
            else
            {
                addDevice.Activate();
            }
        }
    }
}
