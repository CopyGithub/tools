using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace ComboBoxTree
{
    public partial class ComboBoxTree : ComboBox
    {
        //通过ToolStripControlHost来承载控件
        ToolStripControlHost treeViewHost;
        //下拉框
        ToolStripDropDown dropDown;

        public ComboBoxTree()
        {           
            TreeView treeView = new TreeView();
            
            treeView.BorderStyle = BorderStyle.None;
            treeView.Width = this.Width;
            treeViewHost = new ToolStripControlHost(treeView);

            dropDown = new ToolStripDropDown();

            dropDown.Items.Add(treeViewHost);

            //设置下拉框宽度为ComboBox宽度
            dropDown.Width = this.Width;
            
            this.TreeView.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(TreeViewNodeSelect);
        }        


        /// <summary>
        /// 获取Tree控件
        /// </summary>
        public TreeView TreeView
        {
            get 
            { 
                return treeViewHost.Control as TreeView; 
            }
            set
            {
                treeViewHost = new ToolStripControlHost(TreeView);
            }
        }


        /// <summary>
        /// 显示下拉框
        /// </summary>
        private void ShowDropDown()
        {
            if (dropDown != null)
            {
                treeViewHost.Width = DropDownWidth;
                treeViewHost.Height = DropDownHeight;
                //设置下拉框初始位置
                dropDown.Show(this, 0, this.Height);
                //显示下拉框之后，直接将焦点设置到TreeView上
                dropDown.Focus();
            }

        }

        /// <summary>
        /// 常量
        /// </summary>
        private const int WM_USER = 0x0400,
                          WM_REFLECT = WM_USER + 0x1C00,
                          WM_COMMAND = 0x0111,
                          CBN_DROPDOWN = 7;

        public static int HIWORD(int n)
        {
            return (n >> 16) & 0xffff;
        }

        protected override void WndProc(ref Message m)
        {
            if (m.Msg == (WM_REFLECT + WM_COMMAND))
            {
                if (HIWORD((int)m.WParam) == CBN_DROPDOWN)
                {
                    ShowDropDown();
                    return;
                }
            }
            base.WndProc(ref m);
        }


        /// <summary>
        /// 释放资源
        /// </summary>
        /// <param name="disposing"></param>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (dropDown != null)
                {
                    dropDown.Dispose();
                    dropDown = null;
                }
            }
            base.Dispose(disposing);
        }

        /// <summary>
        /// 重写了控件的大小改变事件，当Combox控件大小发生改变时，调整树形控件的大小
        /// </summary>
        /// <param name="e"></param>
        protected override void OnSizeChanged(EventArgs e)
        {           
            this.TreeView.Width = this.Width;
        }

        /// <summary>
        /// TreeView选择事件，当选择某一个TreeNode时，下拉框收起来
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void TreeViewNodeSelect(object sender, EventArgs e)
        {
            if (this.TreeView.SelectedNode != null)
            {
                this.Text = this.TreeView.SelectedNode.Text;                        
                dropDown.Close();                
            }
        }
    }
}
