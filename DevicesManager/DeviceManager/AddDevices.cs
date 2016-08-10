using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Runtime.Serialization.Json;
using Newtonsoft.Json.Linq;
namespace DevicesManager
{
    public partial class AddDevices : Form
    {
        private PostDeviceInfo.DeviceInfo deviceInfo = new PostDeviceInfo.DeviceInfo();
        private string deviceId = "";
        public AddDevices(string devices)
        {
            InitializeComponent();
            this.label7.ForeColor = System.Drawing.Color.Maroon;
            this.label1.ForeColor = System.Drawing.Color.Maroon;
            this.label2.ForeColor = System.Drawing.Color.Maroon;
            this.label4.ForeColor = System.Drawing.Color.Maroon;
            deviceId = devices;

        }
        private Boolean getDeviceInfo(string devices)
        {
            GetDevicesInfo getdevices = new GetDevicesInfo();
            deviceInfo = getdevices.GetDeviceInfo(devices);
            if (deviceInfo == null)
            {
                MessageBox.Show("设备信息获取失败");
                return false;
            }
            this.textCPU.Text = deviceInfo.cpuModel;
            this.textID.Text = devices;
            this.textIMEI.Text = deviceInfo.deviceImei;
            this.textRAM.Text = deviceInfo.deviceRam;
            this.textModel.Text = deviceInfo.deviceModel;
            this.textResolution.Text = deviceInfo.deviceResolution;
            this.textSystem.Text = deviceInfo.systemVersion;
            this.textProduct.Text = deviceInfo.deviceFirm;
            this.textState.Text = deviceInfo.status;
            this.textSize.SelectedText = deviceInfo.deviceSize;
            this.textType.Text = deviceInfo.deviceType;
            this.textAccount.Text = Environment.UserName;
            this.textSize.Text = deviceInfo.deviceSize;
            SetEnable();
            initDepartInfo();
            return true;
        }
        private void SetEnable()
        {
            this.textCPU.Enabled = false;
            this.textID.Enabled = false;
            this.textIMEI.Enabled = false;
            this.textProduct.Enabled = false;
            this.textRAM.Enabled = false;
            this.textSystem.Enabled = false;
            this.textModel.Enabled = false;
            this.textAccount.Enabled = false;
            this.textResolution.Enabled = false;
            this.textSize.Enabled = false;
        }
        private void Ok_Click(object sender, EventArgs e)
        {
            PostDeviceInfo postdeviceInfo = new PostDeviceInfo();
            deviceInfo.remark = this.textRemark.Text;
            deviceInfo.status = (this.textState.SelectedIndex == 0 ? "Y" : "N");
            deviceInfo.deviceType = (this.textType.SelectedIndex == 0 ? "A" : "I");
            deviceInfo.principal = this.textAccount.Text;
            deviceInfo.deviceSize = this.textSize.Text;
            deviceInfo.departmentId = departTree.TreeView.SelectedNode != null ? departTree.TreeView.SelectedNode.Name.ToString() : "";
            deviceInfo.deviceId = this.textID.Text;
            deviceInfo.deviceGrade = this.textPerson.Text;
            string requestMap = "requestMap";
            string ParaUrl = postdeviceInfo.getParaUrlCoded(requestMap, deviceInfo);
            string result = postdeviceInfo.PostDeviceinfo(postdeviceInfo.strAddDeviceURL, ParaUrl);
            if (result == null)
                return;
            MessageBox.Show(result);
            if (result.Contains("成功"))
            {
                postdeviceInfo.PostDeviceLog(deviceId);
                this.Close();
            }
            if (result.Contains("存在"))
            {
                this.Close();
            }
        }

        private void Cancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }
        public string[] departStr = { "checked", "children", "id", "state", "text" };
        private void initDepartInfo()
        {
            PostDeviceInfo postDeviceInfo = new PostDeviceInfo();
            string json = postDeviceInfo.GetDepartInfo(postDeviceInfo.strGetDepartURL);
            if (json == null)
            {
                MessageBox.Show("连接服务器失败");
                this.Close();
            }
            tarverse(null, json);
        }
        public void tarverse(string parentName, string child)
        {
            JArray jsonRsp;
            try
            {
                jsonRsp = JArray.Parse(child);
            }
            catch (Newtonsoft.Json.JsonReaderException)
            {
                MessageBox.Show("连接服务器失败,部门信息获取失败");
                this.Close();
            }
            jsonRsp = JArray.Parse(child);
            if (jsonRsp != null)
            {
                for (int i = 0; i < jsonRsp.Count; i++)
                {
                    JObject obj = JObject.Parse(jsonRsp[i].ToString());
                    TreeNode tn = new TreeNode();
                    tn.Name = obj[departStr[2]].ToString();
                    tn.Text = obj[departStr[4]].ToString();
                    if (parentName == null)
                    {
                        departTree.TreeView.Nodes.Add(tn);
                    }
                    else
                    {
                        foreach (TreeNode node in departTree.TreeView.Nodes)
                        {
                            TreeNode parentTree = FindNode(node, parentName);
                            if (parentTree != null)
                            {
                                parentTree.Nodes.Add(tn);
                            }
                            else
                            {
                                MessageBox.Show("所在部门信息获取失败");
                                this.Close();
                            }
                        }
                    }
                    tarverse(tn.Name, obj[departStr[1]].ToString());
                }
            }
        }
        private TreeNode FindNode(TreeNode tnParent, string strValue)
        {
            if (tnParent == null) return null;
            if (tnParent.Name.Equals(strValue)) return tnParent;
            TreeNode tnRet = null;
            foreach (TreeNode tn in tnParent.Nodes)
            {
                tnRet = FindNode(tn, strValue);
                if (tnRet != null) break;
            }
            return tnRet;
        }

        private void AddDevices_Load(object sender, EventArgs e)
        {
            if (!getDeviceInfo(deviceId))
            {
                this.Close();
            }
        }
        private void AddDevice_FormClosing(object sender, FormClosingEventArgs e)
        {
            DeviceManager.addDevice = null;
        }
    }
}
