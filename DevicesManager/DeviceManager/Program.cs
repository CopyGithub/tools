using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DevicesManager
{
    static class Program
    {
        public static EventWaitHandle ProgramStarted;
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main(string[] args)
        {
            string s = (args.Length > 0 ? args[0] : null);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            if (!checkRegistry())
            {
                System.Security.Principal.WindowsIdentity identity = System.Security.Principal.WindowsIdentity.GetCurrent();
                System.Security.Principal.WindowsPrincipal principal = new System.Security.Principal.WindowsPrincipal(identity);
                //判断当前登录用户是否为管理员
                if (principal.IsInRole(System.Security.Principal.WindowsBuiltInRole.Administrator))
                {
                    addRegistry();
                    runapp(s);
                }
                else
                {
                    //创建启动对象
                    System.Diagnostics.ProcessStartInfo startInfo = new System.Diagnostics.ProcessStartInfo();
                    startInfo.UseShellExecute = true;
                    startInfo.WorkingDirectory = Environment.CurrentDirectory;
                    startInfo.FileName = Application.ExecutablePath;
                    //设置启动动作,确保以管理员身份运行
                    startInfo.Verb = "runas";
                    try
                    {
                        System.Diagnostics.Process.Start(startInfo);
                    }
                    catch
                    {
                        return;
                    }
                    //退出
                    Application.Exit();
                }
            }
            else
            {
                runapp(s);
            }
        }
        private static Boolean checkRegistry()
        {
            string[] subkeyNames;
            RegistryKey hkml = Registry.LocalMachine;
            subkeyNames = hkml.OpenSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run").GetValueNames();
            //取得该项下所有子项的名称的序列，并传递给预定的数组中  
            foreach (string keyName in subkeyNames)
            {
                if (keyName.Equals("DevicesManager"))
                {
                    hkml.Close();
                    return true;
                }
            }
            hkml.Close();
            return false;
        }
        private static void addRegistry()
        {
            string appName = "DevicesManager";
            string appPath = "\"" + Application.StartupPath + @"\" + appName + ".exe\""+" -a";
            RegistryKey run = Registry.LocalMachine.OpenSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run" + appName, true);
            if (run == null)
            {
                run = Registry.LocalMachine.CreateSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run");
            }
            try
            {
                run.SetValue(appName, appPath);
                run.Close();
            }
            catch (Exception my)
            {
                MessageBox.Show(my.Message.ToString());
            }
        }
        static void runapp(string s)
        {
            // 尝试创建一个命名事件  
            bool createNew;
            ProgramStarted = new EventWaitHandle(false, EventResetMode.AutoReset, "MyStartEvent", out createNew);
            // 如果该命名事件已经存在(存在有前一个运行实例)，则发事件通知并退出  
            if (!createNew)
            {
                ProgramStarted.Set();
                return;
            }
            else
            {
                Application.Run(new DeviceManager(s));
            }
        }
    }


}
