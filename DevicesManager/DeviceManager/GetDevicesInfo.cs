using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Collections;
using System.Windows.Forms;
using System.Diagnostics;
using System.IO;
namespace DevicesManager
{
    class GetDevicesInfo
    {
        //设备列表
        public string devicesList = "";
        //插入的设备序列号，以‘，’分隔
        public string devicesInstallList = "";
        public string devicesUninstallList = "";
        public string apk = "com.baina.device";
        public static string path = System.AppDomain.CurrentDomain.SetupInformation.ApplicationBase;
        public string resDir = path + "Resources\\";
        public string[] adbDevicesGet()
        {
            //字符串处理
            string command = "adb devices";
            string list = "List of devices attached";
            string output = RunCmd(command);
            if (!output.Contains(list))
                return null;
            output = output.Substring(output.LastIndexOf(list));
            string[] listS = output.Split('\n');
            List<string> ktls = listS.ToList();
            ktls.Remove("\r");
            ktls.Remove("");
            listS = ktls.ToArray();
            //插入设备列表
            devicesInstallList = "";
            //拔出设备列表
            devicesUninstallList = "";
            foreach (string i in listS)
            {
                if (!devicesList.Contains(i) && !i.Contains(list))
                {
                    devicesInstallList += (devicesInstallList.Length > 0 ? "," : "");
                    devicesInstallList += i.Split('\t')[0];
                }
            }
            string[] listD = devicesList.Split('\n');
            foreach (string i in listD)
            {
                if (!output.Contains(i))
                {
                    devicesUninstallList += (devicesUninstallList.Length > 0 ? "," : "");
                    devicesUninstallList += i.Split('\t')[0];
                }
            }
            //刷新设备列表
            devicesList = output;
            return listS;
        }
        private string GetDeviceInfoFromApkFile(string device)
        {
            PostDeviceInfo.DeviceInfo deviceInfo = new PostDeviceInfo.DeviceInfo();
            string output = "";
            string adb = "adb -s " + device;
            string filename = "deviceInfo.txt";
            string cmdStart = adb + " shell am start -n com.baina.device/com.baina.device.MainActivity -d true -W";
            string cmdPull = adb + " pull /mnt/sdcard/" + filename + " \"" + resDir + filename + "\"";
            string filedir = resDir + filename;
            //删除文件：
            if (File.Exists(@filedir))
            {
                File.Delete(@filedir);
                Debug.WriteLine("删除：" + filedir);
            }
            output = RunCmd(cmdStart);
            Debug.WriteLine(output);
            output = RunCmd(cmdPull);
            Debug.WriteLine(output);
            if (!File.Exists(filedir))
            {
                MessageBox.Show("设备信息文件获取失败，该文件不存在");
                return null;
            }
            StreamReader sr = new StreamReader(filedir);
            output = sr.ReadToEnd();
            Debug.WriteLine("deviceInfo:" + output);
            return output;
        }
        private Boolean installApk(string device)
        {
            string adb = "adb -s " + device;
            string cmdUninstall = adb + " uninstall " + apk;
            string cmdInsert = adb + " install \"" + resDir + "DeviceInfo.apk" + "\"";
            string output = "";
            RunCmd(cmdUninstall);
            output = RunCmd(cmdInsert);
            Debug.WriteLine(output);
            if (!output.Contains("Success"))
            {
                RunCmd(cmdUninstall);
                MessageBox.Show("插入apk失败");
                return false;
            }
            return true;
        }

        private string GetDeviceInfoFromApkLog(string device)
        {
            string adb = "adb -s " + device;
            string cmdUninstall = adb + " uninstall " + apk;
            string cmdStart = adb + " shell am start -n com.baina.device/com.baina.device.MainActivity -d true";
            string cmdPrint = adb + " shell am broadcast -a com.baina.android.GET_DEVICE_INFO";
            string cmdGetlog = adb + " logcat -v time -b main -t 100 -d -s GetDeviceInfo:I *:S";
            string cmdLogClear = adb + " logcat -c";
            string tag = "deviceModel";
            string output = "";
            output = RunCmd(cmdLogClear + "&" + cmdStart + "&" + cmdPrint + "&" + cmdGetlog);
            Debug.WriteLine(output);
            if (!output.Contains(tag)) return null;
            output = output.Substring(output.LastIndexOf(tag));
            Debug.WriteLine(output);
            return output;
        }
        public PostDeviceInfo.DeviceInfo GetDeviceInfo(string device)
        {
            PostDeviceInfo.DeviceInfo deviceInfo = new PostDeviceInfo.DeviceInfo();
            //file
            string path = System.AppDomain.CurrentDomain.SetupInformation.ApplicationBase;
            if (!installApk(device)) return null;
            string output = GetDeviceInfoFromApkFile(device);

            //uninstall
            string adb = "adb -s " + device;
            string cmdUninstall = adb + " uninstall " + apk;
            RunCmd(cmdUninstall);
            if (output == null) return deviceInfo;
            string[] devicestr = { "deviceModel", "systemVersion", "deviceFirm", "deviceId", "deviceImei", "deviceResolution", "cpuModel", "deviceRam" };
            foreach (string str in devicestr)
                if (!output.Contains(str))
                {
                    MessageBox.Show("设备信息获取字段出错");
                    return deviceInfo;
                }
            string[] result = output.Split(',');

            int i = 0;
            deviceInfo.deviceModel = result[i++].Split(':')[1];
            deviceInfo.systemVersion = result[i++].Split(':')[1];
            deviceInfo.deviceFirm = result[i++].Split(':')[1];
            deviceInfo.deviceId = result[i++].Split(':')[1];
            deviceInfo.deviceImei = result[i++].Split(':')[1];
            deviceInfo.deviceResolution = result[i++].Split(':')[1];
            deviceInfo.cpuModel = result[i++].Split(':')[1];
            deviceInfo.deviceRam = result[i++].Split(':')[1];
            deviceInfo.deviceSize = result[i++].Split(':')[1];
            return deviceInfo;
        }
        //output为空，cmd执行失败
        public string RunCmd(string command)
        {
            string str = command;
            System.Diagnostics.Process p = new System.Diagnostics.Process();
            p.StartInfo.FileName = "cmd.exe";
            p.StartInfo.UseShellExecute = false;    //是否使用操作系统shell启动
            p.StartInfo.RedirectStandardInput = true;//接受来自调用程序的输入信息
            p.StartInfo.RedirectStandardOutput = true;//由调用程序获取输出信息
            p.StartInfo.RedirectStandardError = true;//重定向标准错误输出
            p.StartInfo.CreateNoWindow = true;//不显示程序窗口
            p.Start();//启动程序
            //向cmd窗口发送输入信息
            Debug.WriteLine("开始");
            p.StandardInput.WriteLine(str + "&exit");
            p.StandardInput.AutoFlush = true;
            //向标准输入写入要执行的命令。这里使用&是批处理命令的符号，表示前面一个命令不管是否执行成功都执行后面(exit)命令，如果不执行exit命令，后面调用ReadToEnd()方法会假死
            //同类的符号还有&&和||前者表示必须前一个命令执行成功才会执行后面的命令，后者表示必须前一个命令执行失败才会执行后面的命令
            //获取cmd窗口的输出信息
            string output = "";
            if (p.WaitForExit(30000))
            {
                output = p.StandardOutput.ReadToEnd();
                Debug.WriteLine(output);
                Debug.WriteLine("未超时");
                p.Close();
                return output;
            }
            p.Close();
            return null;
        }
    }
}
