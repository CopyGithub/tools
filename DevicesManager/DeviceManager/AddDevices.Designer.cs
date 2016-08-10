namespace DevicesManager
{
    partial class AddDevices
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.Label Model;
            System.Windows.Forms.Label System;
            System.Windows.Forms.Label ID;
            System.Windows.Forms.Label Size;
            System.Windows.Forms.Label Level;
            System.Windows.Forms.Label RAM;
            System.Windows.Forms.Label Person;
            System.Windows.Forms.Label State;
            System.Windows.Forms.Label Depart;
            System.Windows.Forms.Label Remark;
            this.Product = new System.Windows.Forms.Label();
            this.IMEI = new System.Windows.Forms.Label();
            this.Resolution = new System.Windows.Forms.Label();
            this.Type = new System.Windows.Forms.Label();
            this.CPUType = new System.Windows.Forms.Label();
            this.textProduct = new System.Windows.Forms.TextBox();
            this.textIMEI = new System.Windows.Forms.TextBox();
            this.textResolution = new System.Windows.Forms.TextBox();
            this.textCPU = new System.Windows.Forms.TextBox();
            this.textSystem = new System.Windows.Forms.TextBox();
            this.textSize = new System.Windows.Forms.TextBox();
            this.textRAM = new System.Windows.Forms.TextBox();
            this.textAccount = new System.Windows.Forms.TextBox();
            this.Cancel = new System.Windows.Forms.Button();
            this.Ok = new System.Windows.Forms.Button();
            this.textRemark = new System.Windows.Forms.TextBox();
            this.textState = new System.Windows.Forms.ComboBox();
            this.textType = new System.Windows.Forms.ComboBox();
            this.textModel = new System.Windows.Forms.TextBox();
            this.textID = new System.Windows.Forms.TextBox();
            this.departTree = new ComboBoxTree.ComboBoxTree();
            this.textPerson = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            Model = new System.Windows.Forms.Label();
            System = new System.Windows.Forms.Label();
            ID = new System.Windows.Forms.Label();
            Size = new System.Windows.Forms.Label();
            Level = new System.Windows.Forms.Label();
            RAM = new System.Windows.Forms.Label();
            Person = new System.Windows.Forms.Label();
            State = new System.Windows.Forms.Label();
            Depart = new System.Windows.Forms.Label();
            Remark = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // Model
            // 
            Model.AutoSize = true;
            Model.Location = new System.Drawing.Point(25, 31);
            Model.Name = "Model";
            Model.Size = new System.Drawing.Size(53, 12);
            Model.TabIndex = 0;
            Model.Text = "设备型号";
            // 
            // System
            // 
            System.AutoSize = true;
            System.Location = new System.Drawing.Point(341, 31);
            System.Name = "System";
            System.Size = new System.Drawing.Size(53, 12);
            System.TabIndex = 18;
            System.Text = "系统版本";
            // 
            // ID
            // 
            ID.AutoSize = true;
            ID.Location = new System.Drawing.Point(353, 72);
            ID.Name = "ID";
            ID.Size = new System.Drawing.Size(41, 12);
            ID.TabIndex = 19;
            ID.Text = "设备ID";
            // 
            // Size
            // 
            Size.AutoSize = true;
            Size.Location = new System.Drawing.Point(341, 112);
            Size.Name = "Size";
            Size.Size = new System.Drawing.Size(53, 12);
            Size.TabIndex = 20;
            Size.Text = "设备尺寸";
            // 
            // Level
            // 
            Level.AutoSize = true;
            Level.Location = new System.Drawing.Point(353, 226);
            Level.Name = "Level";
            Level.Size = new System.Drawing.Size(41, 12);
            Level.TabIndex = 21;
            Level.Text = "负责人";
            // 
            // RAM
            // 
            RAM.AutoSize = true;
            RAM.Location = new System.Drawing.Point(341, 189);
            RAM.Name = "RAM";
            RAM.Size = new System.Drawing.Size(53, 12);
            RAM.TabIndex = 22;
            RAM.Text = "运行内存";
            // 
            // Person
            // 
            Person.AutoSize = true;
            Person.Location = new System.Drawing.Point(17, 229);
            Person.Name = "Person";
            Person.Size = new System.Drawing.Size(65, 12);
            Person.TabIndex = 23;
            Person.Text = "登记域账号";
            // 
            // State
            // 
            State.AutoSize = true;
            State.Location = new System.Drawing.Point(341, 264);
            State.Name = "State";
            State.Size = new System.Drawing.Size(53, 12);
            State.TabIndex = 29;
            State.Text = "设备状态";
            // 
            // Depart
            // 
            Depart.AutoSize = true;
            Depart.Location = new System.Drawing.Point(29, 264);
            Depart.Name = "Depart";
            Depart.Size = new System.Drawing.Size(53, 12);
            Depart.TabIndex = 30;
            Depart.Text = "部门名称";
            // 
            // Remark
            // 
            Remark.AutoSize = true;
            Remark.Location = new System.Drawing.Point(51, 313);
            Remark.Name = "Remark";
            Remark.Size = new System.Drawing.Size(29, 12);
            Remark.TabIndex = 31;
            Remark.Text = "备注";
            // 
            // Product
            // 
            this.Product.AutoSize = true;
            this.Product.Location = new System.Drawing.Point(29, 72);
            this.Product.Name = "Product";
            this.Product.Size = new System.Drawing.Size(53, 12);
            this.Product.TabIndex = 2;
            this.Product.Text = "设备厂商";
            // 
            // IMEI
            // 
            this.IMEI.AutoSize = true;
            this.IMEI.Location = new System.Drawing.Point(29, 112);
            this.IMEI.Name = "IMEI";
            this.IMEI.Size = new System.Drawing.Size(53, 12);
            this.IMEI.TabIndex = 3;
            this.IMEI.Text = "设备IMEI";
            // 
            // Resolution
            // 
            this.Resolution.AutoSize = true;
            this.Resolution.Location = new System.Drawing.Point(17, 151);
            this.Resolution.Name = "Resolution";
            this.Resolution.Size = new System.Drawing.Size(65, 12);
            this.Resolution.TabIndex = 4;
            this.Resolution.Text = "设备分辨率";
            // 
            // Type
            // 
            this.Type.AutoSize = true;
            this.Type.Location = new System.Drawing.Point(341, 148);
            this.Type.Name = "Type";
            this.Type.Size = new System.Drawing.Size(53, 12);
            this.Type.TabIndex = 5;
            this.Type.Text = "设备类型";
            // 
            // CPUType
            // 
            this.CPUType.AutoSize = true;
            this.CPUType.Location = new System.Drawing.Point(35, 189);
            this.CPUType.Name = "CPUType";
            this.CPUType.Size = new System.Drawing.Size(47, 12);
            this.CPUType.TabIndex = 6;
            this.CPUType.Text = "CPU型号";
            // 
            // textProduct
            // 
            this.textProduct.Location = new System.Drawing.Point(106, 63);
            this.textProduct.Name = "textProduct";
            this.textProduct.Size = new System.Drawing.Size(191, 21);
            this.textProduct.TabIndex = 7;
            // 
            // textIMEI
            // 
            this.textIMEI.Location = new System.Drawing.Point(106, 109);
            this.textIMEI.Name = "textIMEI";
            this.textIMEI.Size = new System.Drawing.Size(191, 21);
            this.textIMEI.TabIndex = 8;
            // 
            // textResolution
            // 
            this.textResolution.Location = new System.Drawing.Point(106, 148);
            this.textResolution.Name = "textResolution";
            this.textResolution.Size = new System.Drawing.Size(191, 21);
            this.textResolution.TabIndex = 9;
            // 
            // textCPU
            // 
            this.textCPU.Location = new System.Drawing.Point(106, 186);
            this.textCPU.Name = "textCPU";
            this.textCPU.Size = new System.Drawing.Size(191, 21);
            this.textCPU.TabIndex = 11;
            // 
            // textSystem
            // 
            this.textSystem.Location = new System.Drawing.Point(416, 22);
            this.textSystem.Name = "textSystem";
            this.textSystem.Size = new System.Drawing.Size(191, 21);
            this.textSystem.TabIndex = 12;
            // 
            // textSize
            // 
            this.textSize.Location = new System.Drawing.Point(416, 109);
            this.textSize.Name = "textSize";
            this.textSize.Size = new System.Drawing.Size(191, 21);
            this.textSize.TabIndex = 14;
            // 
            // textRAM
            // 
            this.textRAM.Location = new System.Drawing.Point(416, 180);
            this.textRAM.Name = "textRAM";
            this.textRAM.Size = new System.Drawing.Size(191, 21);
            this.textRAM.TabIndex = 16;
            // 
            // textAccount
            // 
            this.textAccount.Location = new System.Drawing.Point(106, 223);
            this.textAccount.Name = "textAccount";
            this.textAccount.Size = new System.Drawing.Size(191, 21);
            this.textAccount.TabIndex = 17;
            // 
            // Cancel
            // 
            this.Cancel.Location = new System.Drawing.Point(416, 375);
            this.Cancel.Name = "Cancel";
            this.Cancel.Size = new System.Drawing.Size(75, 23);
            this.Cancel.TabIndex = 24;
            this.Cancel.Text = "取消";
            this.Cancel.UseVisualStyleBackColor = true;
            this.Cancel.Click += new System.EventHandler(this.Cancel_Click);
            // 
            // Ok
            // 
            this.Ok.Location = new System.Drawing.Point(532, 375);
            this.Ok.Name = "Ok";
            this.Ok.Size = new System.Drawing.Size(75, 23);
            this.Ok.TabIndex = 25;
            this.Ok.Text = "确定";
            this.Ok.UseVisualStyleBackColor = true;
            this.Ok.Click += new System.EventHandler(this.Ok_Click);
            // 
            // textRemark
            // 
            this.textRemark.Location = new System.Drawing.Point(106, 310);
            this.textRemark.Name = "textRemark";
            this.textRemark.Size = new System.Drawing.Size(501, 21);
            this.textRemark.TabIndex = 28;
            // 
            // textState
            // 
            this.textState.CausesValidation = false;
            this.textState.FormattingEnabled = true;
            this.textState.Items.AddRange(new object[] {
            "正常",
            "不正常"});
            this.textState.Location = new System.Drawing.Point(416, 261);
            this.textState.Name = "textState";
            this.textState.Size = new System.Drawing.Size(191, 20);
            this.textState.TabIndex = 32;
            // 
            // textType
            // 
            this.textType.FormattingEnabled = true;
            this.textType.Items.AddRange(new object[] {
            "Android",
            "IOS"});
            this.textType.Location = new System.Drawing.Point(416, 148);
            this.textType.Name = "textType";
            this.textType.Size = new System.Drawing.Size(191, 20);
            this.textType.TabIndex = 33;
            // 
            // textModel
            // 
            this.textModel.Location = new System.Drawing.Point(106, 22);
            this.textModel.Name = "textModel";
            this.textModel.Size = new System.Drawing.Size(191, 21);
            this.textModel.TabIndex = 1;
            // 
            // textID
            // 
            this.textID.Location = new System.Drawing.Point(416, 63);
            this.textID.Name = "textID";
            this.textID.Size = new System.Drawing.Size(191, 21);
            this.textID.TabIndex = 36;
            // 
            // departTree
            // 
            this.departTree.FormattingEnabled = true;
            this.departTree.Location = new System.Drawing.Point(106, 264);
            this.departTree.Name = "departTree";
            this.departTree.Size = new System.Drawing.Size(191, 20);
            this.departTree.TabIndex = 35;
            // 
            // textPerson
            // 
            this.textPerson.Location = new System.Drawing.Point(416, 226);
            this.textPerson.Name = "textPerson";
            this.textPerson.Size = new System.Drawing.Size(191, 21);
            this.textPerson.TabIndex = 37;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(512, 344);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(95, 12);
            this.label1.TabIndex = 38;
            this.label1.Text = "提示：*为必填项";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(613, 109);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(11, 12);
            this.label4.TabIndex = 41;
            this.label4.Text = "*";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            
            this.label7.Location = new System.Drawing.Point(613, 226);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(11, 12);
            this.label7.TabIndex = 44;
            this.label7.Text = "*";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(303, 267);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(11, 12);
            this.label2.TabIndex = 45;
            this.label2.Text = "*";
            // 
            // AddDevices
            // 
            this.ClientSize = new System.Drawing.Size(652, 410);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.textPerson);
            this.Controls.Add(this.textID);
            this.Controls.Add(this.departTree);
            this.Controls.Add(this.textType);
            this.Controls.Add(this.textState);
            this.Controls.Add(Remark);
            this.Controls.Add(Depart);
            this.Controls.Add(State);
            this.Controls.Add(this.textRemark);
            this.Controls.Add(this.Ok);
            this.Controls.Add(this.Cancel);
            this.Controls.Add(Person);
            this.Controls.Add(RAM);
            this.Controls.Add(Level);
            this.Controls.Add(Size);
            this.Controls.Add(ID);
            this.Controls.Add(System);
            this.Controls.Add(this.textAccount);
            this.Controls.Add(this.textRAM);
            this.Controls.Add(this.textSize);
            this.Controls.Add(this.textSystem);
            this.Controls.Add(this.textCPU);
            this.Controls.Add(this.textResolution);
            this.Controls.Add(this.textIMEI);
            this.Controls.Add(this.textProduct);
            this.Controls.Add(this.CPUType);
            this.Controls.Add(this.Type);
            this.Controls.Add(this.Resolution);
            this.Controls.Add(this.IMEI);
            this.Controls.Add(this.Product);
            this.Controls.Add(this.textModel);
            this.Controls.Add(Model);
            this.Name = "AddDevices";
            this.Text = "登记设备信息";
            this.Load += new System.EventHandler(this.AddDevices_Load);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.AddDevice_FormClosing);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label Product;
        private System.Windows.Forms.Label IMEI;
        private System.Windows.Forms.Label Resolution;
        private System.Windows.Forms.Label Type;
        private System.Windows.Forms.Label CPUType;
        private System.Windows.Forms.TextBox textProduct;
        private System.Windows.Forms.TextBox textIMEI;
        private System.Windows.Forms.TextBox textResolution;
        private System.Windows.Forms.TextBox textCPU;
        private System.Windows.Forms.TextBox textSystem;
        private System.Windows.Forms.TextBox textSize;
        private System.Windows.Forms.TextBox textRAM;
        private System.Windows.Forms.TextBox textAccount;
        private System.Windows.Forms.Button Cancel;
        private System.Windows.Forms.Button Ok;
        private System.Windows.Forms.TextBox textRemark;
        private System.Windows.Forms.ComboBox textState;
        private System.Windows.Forms.ComboBox textType;
        private ComboBoxTree.ComboBoxTree departTree;
        private System.Windows.Forms.TextBox textModel;
        private System.Windows.Forms.TextBox textID;
        private System.Windows.Forms.TextBox textPerson;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label2;
    }
}