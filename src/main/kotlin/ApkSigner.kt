import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.lang.StringBuilder
import javax.swing.*

private val mFrame by lazy {
    JFrame("签名工具 apksigner")
}

private val mInstallationPackageField by lazy {
    JTextField()
}

private val mSignFileField by lazy {
    JTextField()
}

private val mSignFilePasswordField by lazy {
    JTextField()
}

private val mV1CheckBox by lazy {
    JCheckBox("V1")
}

private val mV2CheckBox by lazy {
    JCheckBox("V2")
}

private val mV3CheckBox by lazy {
    JCheckBox("V3")
}


fun main() {
    //绝对布局
    mFrame.layout = null
    setLayout()
    initFrame()
}


private fun setLayout() {
    selectInstallationPackageLayout()
    selectSignFileLayout()
    selectSignFilePasswordLayout()
    chooseV1V2V3()
    signLayout()
}


/**
 * 选择安装包布局
 */
private val selectInstallationPackageLayout = {
    mInstallationPackageField.setBounds(10, 10, TEXT_FIELD_WIDTH_300, VIEW_HEIGHT_50)
    mFrame.add(mInstallationPackageField)

    val selectInstallationPackageButton = JButton("选择apk")
    selectInstallationPackageButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    selectInstallationPackageButton.setBounds(320, 10, BUTTON_WIDTH_150, VIEW_HEIGHT_50)
    selectInstallationPackageButton.addActionListener(ApkSelectInstallationPackageButtonActionListener())
    mFrame.add(selectInstallationPackageButton)
}


/**
 * 选择签名文件布局
 */
private val selectSignFileLayout = {
    mSignFileField.setBounds(10, 70, TEXT_FIELD_WIDTH_300, VIEW_HEIGHT_50)
    mFrame.add(mSignFileField)

    val selectSignFileButton = JButton("选择签名文件")
    selectSignFileButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    selectSignFileButton.setBounds(320, 70, BUTTON_WIDTH_150, VIEW_HEIGHT_50)
    selectSignFileButton.addActionListener(ApkSelectSignFileButtonActionListener())
    mFrame.add(selectSignFileButton)
}


/**
 * 签名文件密钥
 */
private val selectSignFilePasswordLayout = {
    val signFilePasswordLabel = JLabel("输入签名文件密钥")
    signFilePasswordLabel.setBounds(10, 130, 460, VIEW_LABEL_HEIGHT)
    mFrame.add(signFilePasswordLabel)

    mSignFilePasswordField.setBounds(10, 170, 460, VIEW_HEIGHT_50)
    mFrame.add(mSignFilePasswordField)
}


/**
 * 选择V1V2V3签名
 */
private val chooseV1V2V3 = {
    mV1CheckBox.setBounds(10, 230, 100, 50)
    mV2CheckBox.setBounds(120, 230, 100, 50)
    mV3CheckBox.setBounds(230, 230, 100, 50)
    mV1CheckBox.isSelected = true
    mV2CheckBox.isSelected = true
    mFrame.add(mV1CheckBox)
    mFrame.add(mV2CheckBox)
    mFrame.add(mV3CheckBox)
}


/**
 * 点击签名布局
 */
private val signLayout = {
    val signButton = JButton("签名")
    signButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    signButton.setBounds(10, 290, 460, VIEW_HEIGHT_50)
    signButton.addActionListener(ApkSignButtonActionListener())
    mFrame.add(signButton)
}


/**
 * 点击选择密钥
 */
private class ApkSelectSignFileButtonActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        mSignFileField.text = filePathResult()
    }
}


/**
 * 点击选择签名文件
 */
private class ApkSelectInstallationPackageButtonActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        mInstallationPackageField.text = filePathResult()
    }
}

/**
 * 点击签名
 */
private class ApkSignButtonActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        if (mSignFileField.text.isEmpty()) {
            //签名文件为空
            JOptionPane.showMessageDialog(null, "签名文件不可为空")
            return
        }

        if (mInstallationPackageField.text.isEmpty()) {
            //安装包为空
            JOptionPane.showMessageDialog(null, "安装包不可为空")
            return
        }

        if (mInstallationPackageField.text.endsWith(".aab")) {
            //不支持aab
            JOptionPane.showMessageDialog(null, "不支持aab")
            return
        }

        if (mSignFilePasswordField.text.isEmpty()) {
            //签名文件密码为空
            JOptionPane.showMessageDialog(null, "签名文件密码不可为空")
            return
        }

        signProcess()
    }
}


/**
 *  签名进程
 */
private fun signProcess() {
    val signFile = mSignFileField.text
    val installationPackage = mInstallationPackageField.text
    val signFilePassword = mSignFilePasswordField.text

    val supportV1 = if (mV1CheckBox.isSelected) "--v1-signing-enabled true" else "--v1-signing-enabled false"
    val supportV2 = if (mV2CheckBox.isSelected) "--v2-signing-enabled true" else "--v2-signing-enabled false"
    val supportV3 = if (mV3CheckBox.isSelected) "--v3-signing-enabled true" else "--v3-signing-enabled false"


    val signedApk = "signed_${getLocalTime()}.apk"

    val cmd =
        "apksigner sign --ks=$signFile --ks-pass=pass:$signFilePassword $supportV1 $supportV2 $supportV3  --out $signedApk $installationPackage"

    val tips = StringBuilder()
        .append("      ").append("签名完成").append("\n")
        .append("      ").append("生成文件:  $signedApk").append("\n\n")
        .append("      ").append("注：若未生成$signedApk, 则签名失败")

    cmdProcessDialog(
        cmd,
        tips.toString()
    )
}


private fun initFrame() {
    mFrame.run {
        //关闭窗口时退出程序
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        //禁止最大化
        isResizable = false
        //设置高/宽/位置
        setBounds(500, 200, 500, 400)
        //可见
        isVisible = true
    }
}
