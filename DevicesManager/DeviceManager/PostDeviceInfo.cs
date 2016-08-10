using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System.Runtime.Serialization.Json;
using System.Diagnostics;
namespace DevicesManager
{
    class PostDeviceInfo
    {
        public static string m = System.Configuration.ConfigurationManager.AppSettings["server"];
        public string strAddDeviceURL = m + "AutoIntegrateSystem/device/addDevice.jspx";
        public string strAddDeviceLogURL = m + "AutoIntegrateSystem/log/addDeviceLog.jspx";
        public string strGetDepartURL = m + "AutoIntegrateSystem/department/getAllDepartments.jspx";
        public static string path = System.AppDomain.CurrentDomain.SetupInformation.ApplicationBase;
        public string dirImeiLog = path + "Resources\\ImeiLog.txt";
        public class DeviceInfo
        {
            public string deviceModel = "";
            public string systemVersion = "";
            public string deviceFirm = "";
            public string deviceId = "";
            public string deviceImei = "";
            public string deviceSize = "";
            public string deviceResolution = "";
            public string deviceGrade = "高端机";
            public string deviceType = "Android";
            public string deviceRam = "";
            public string cpuModel = "";
            public string principal = "";
            public string departmentId = "";
            public string status = "正常";
            public string remark = "";
        }
        public class DeviceLogInfo
        {
            public string operateUser = Environment.UserName;
            public string operateMac;
            public string operateIp;
            public string operateDeviceId;
            public string remark = "";
            public string operateDeviceImei;
        }
        public string getLogParaUrlCoded(DeviceLogInfo deviceLogInfo)
        {
            string[] member = { "requestMap.operateUser",  "requestMap.operateMac", "requestMap.operateIp", "requestMap.operateDeviceId", "requestMap.remark","requestMap.operateDeviceImei"
 };
            string paraUrlCoded = "";
            int i = 0;
            paraUrlCoded = member[i++] + "=" + deviceLogInfo.operateUser;
            paraUrlCoded = paraUrlCoded + "&" + member[i++] + "=" + deviceLogInfo.operateMac;
            paraUrlCoded = paraUrlCoded + "&" + member[i++] + "=" + deviceLogInfo.operateIp;
            paraUrlCoded = paraUrlCoded + "&" + member[i++] + "=" + deviceLogInfo.operateDeviceId;
            paraUrlCoded = paraUrlCoded + "&" + member[i++] + "=" + deviceLogInfo.remark;
            paraUrlCoded = paraUrlCoded + "&" + member[i++] + "=" + deviceLogInfo.operateDeviceImei;
            return paraUrlCoded;
        }
        public string getParaUrlCoded(string requestMap, DeviceInfo deviceInfo)
        {
            string paraUrlCoded = "";
            paraUrlCoded = requestMap + ".deviceModel=" + deviceInfo.deviceModel;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".systemVersion=" + deviceInfo.systemVersion;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".deviceFirm=" + deviceInfo.deviceFirm;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".deviceId=" + deviceInfo.deviceId;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".deviceImei=" + deviceInfo.deviceImei;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".deviceSize=" + deviceInfo.deviceSize;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".deviceResolution=" + deviceInfo.deviceResolution;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".deviceGrade=" + deviceInfo.deviceGrade;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".deviceType=" + deviceInfo.deviceType;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".deviceRam=" + deviceInfo.deviceRam;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".cpuModel=" + deviceInfo.cpuModel;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".principal=" + deviceInfo.principal;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".departmentId=" + deviceInfo.departmentId;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".status=" + deviceInfo.status;
            paraUrlCoded = paraUrlCoded + "&" + requestMap + ".remark=" + deviceInfo.remark;
            return paraUrlCoded;
        }

        public string PostDeviceinfo(string strURL, string paraUrlCoded)
        {

            System.Net.HttpWebRequest request = (System.Net.HttpWebRequest)HttpWebRequest.Create(strURL);
            //Post请求方式
            request.Method = "POST";
            // 内容类型
            request.ContentType = "application/x-www-form-urlencoded";
            //将URL编码后的字符串转化为字节
            byte[] payload = System.Text.Encoding.UTF8.GetBytes(paraUrlCoded);
            //设置请求的 ContentLength 
            request.ContentLength = payload.Length;
            //获得请 求流
            Stream writer;
            try
            {
                writer = request.GetRequestStream();
                //将请求参数写入流
                writer.Write(payload, 0, payload.Length);
                // 关闭请求流
                writer.Close();
                System.Net.HttpWebResponse response;
                // 获得响应流并显示
                response = (System.Net.HttpWebResponse)request.GetResponse();
                string encoding = response.ContentEncoding;
                if (encoding == null || encoding.Length < 1)
                {
                    encoding = "UTF-8"; //默认编码  
                }
                StreamReader reader = new StreamReader(response.GetResponseStream(), Encoding.GetEncoding(encoding));
                string retString = reader.ReadToEnd();
                retString = getMessage("message", retString);
                return retString;
            }
            catch (Exception)
            {
                writer = null;
                MessageBox.Show("连接服务器失败！");
                return null;
            }
        }
        private string getMessage(string key, string json)
        {
            string massage = "";
            JObject obj = JObject.Parse(json);
            massage = obj[key].ToString();
            return massage;
        }
        public string GetDepartInfo(string strURL)
        {
            try
            {
                System.Net.HttpWebRequest request;
                request = (System.Net.HttpWebRequest)HttpWebRequest.Create(strURL);
                //Post请求方式
                request.Method = "POST";
                // 获得响应流并显示
                System.Net.HttpWebResponse response = (System.Net.HttpWebResponse)request.GetResponse(); ;
                string encoding = response.ContentEncoding;
                if (encoding == null || encoding.Length < 1)
                {
                    encoding = "UTF-8"; //默认编码  
                }
                StreamReader reader;
                reader = new StreamReader(response.GetResponseStream(), Encoding.GetEncoding(encoding));
                string retString = reader.ReadToEnd();
                return retString;
            }
            catch (Exception)
            {
                MessageBox.Show("连接服务器失败！");
                return null;
            }

        }
        //IP地址
        private string getIP()
        {
            string ip = "";
            string hostInfo = Dns.GetHostName();
            System.Net.IPAddress[] addressList = Dns.GetHostEntry(Dns.GetHostName()).AddressList;
            for (int i = 0; i < addressList.Length; i++)
            {
                ip = addressList[i].ToString();
            }
            return ip;
        }
        //mac地址
        private string getMac()
        {
            string mac = "";
            System.Management.ManagementClass mc = new System.Management.ManagementClass("Win32_NetworkAdapterConfiguration");
            System.Management.ManagementObjectCollection moc = mc.GetInstances();
            foreach (System.Management.ManagementObject mo in moc)
            {
                if (mo["IPEnabled"].ToString() == "True")
                {
                    mac = mo["MacAddress"].ToString();
                }
            }
            return mac;
        }
        private string findImei(string deviceID)
        {
            string Imei = null;
            int count = 0;
            if (!File.Exists(dirImeiLog))
                return Imei;
            try
            {
                Dictionary<string, string> imeiAdeviceID = new Dictionary<string, string>();
                StreamReader sr = new StreamReader(dirImeiLog);
                string strLine = sr.ReadLine();
                while (strLine != null)
                {
                    if (strLine.Length > 0)
                    {
                        string[] sArray = strLine.Split(':');
                        imeiAdeviceID.Add(sArray[0], sArray[1]);
                    }
                    strLine = sr.ReadLine();
                }
                sr.Close();
                foreach (var item in imeiAdeviceID)
                {
                    if (deviceID.Equals(item.Value))
                    {
                        count++;
                        Imei = item.Key;
                    }
                }
                if (count != 1) return null;
            }
            catch (Exception e)
            {
                MessageBox.Show(e.ToString());
            }
            return Imei;
        }
        private void addImei(string deviceID, string Imei)
        {
            try
            {
                FileStream file = new FileStream(dirImeiLog, FileMode.OpenOrCreate);
                file.Close();
                StreamWriter sw = new StreamWriter(dirImeiLog, true);
                sw.WriteLine(Imei + ":" + deviceID);
                sw.Close();
            }
            catch (Exception e)
            {
                MessageBox.Show(e.ToString());
            }
        }
        public string getImei(string deviceID)
        {
            string imei = findImei(deviceID);
            if (imei == null || imei.Length == 0)
            {

                GetDevicesInfo getdevices = new GetDevicesInfo();
                imei = getdevices.GetDeviceInfo(deviceID).deviceImei;
                if (imei != null) addImei(deviceID, imei);
            }
            if (imei.Length == 0 || imei == null)
                MessageBox.Show("获取设备IMEI失败");
            return imei;
        }
        public Boolean PostDeviceLog(string deviceID)
        {
            //初始化 deviceLogInfo
            DeviceLogInfo deviceLogInfo = new DeviceLogInfo();
            deviceLogInfo.operateDeviceId = deviceID;
            deviceLogInfo.operateIp = getIP();
            deviceLogInfo.operateMac = getMac();
            deviceLogInfo.operateDeviceImei = getImei(deviceID);
            //post请求
            string LogParaUrl = getLogParaUrlCoded(deviceLogInfo);
            string m = PostDeviceinfo(strAddDeviceLogURL, LogParaUrl);
            if (m != null && !m.Contains("成功"))
            {
                MessageBox.Show(deviceID + m);
                return false;
            }
            return true;
        }
    }

}
