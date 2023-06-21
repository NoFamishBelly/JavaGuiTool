import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

private val mFrame by lazy {
    JFrame("签名工具 jarsigner")
}

private val mSignFileField by lazy {
    JTextField()
}

private val mInstallationPackageField by lazy {
    JTextField()
}

private val mSelectedSignFileAliasField by lazy {
    JTextField()
}


private val mSelectedSignFilePasswordField by lazy {
    JTextField()
}

fun main() {
    //绝对布局
    mFrame.layout = null
    setLayout()
    initFrame()
}


/**
 * 设置布局
 */
private fun setLayout() {
    selectSignFileLayout()
    selectInstallationPackageLayout()
    selectSignFileAliasLayout()
    selectSignFilePasswordLayout()
    signLayout()
}


/**
 * 选择签名文件布局
 */
private val selectSignFileLayout = {
    mSignFileField.setBounds(10, 10, TEXT_FIELD_WIDTH_300, VIEW_HEIGHT_50)
    mFrame.add(mSignFileField)

    val selectSignFileButton = JButton("选择签名文件")
    selectSignFileButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    selectSignFileButton.setBounds(320, 10, BUTTON_WIDTH_150, VIEW_HEIGHT_50)
    selectSignFileButton.addActionListener(SelectSignFileButtonActionListener())
    mFrame.add(selectSignFileButton)
}


/**
 * 选择安装包布局
 */
private val selectInstallationPackageLayout = {
    mInstallationPackageField.setBounds(10, 70, TEXT_FIELD_WIDTH_300, VIEW_HEIGHT_50)
    mFrame.add(mInstallationPackageField)

    val selectInstallationPackageButton = JButton("选择apk/aab")
    selectInstallationPackageButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    selectInstallationPackageButton.setBounds(320, 70, BUTTON_WIDTH_150, VIEW_HEIGHT_50)
    selectInstallationPackageButton.addActionListener(SelectInstallationPackageButtonActionListener())
    mFrame.add(selectInstallationPackageButton)
}


/**
 * 签名文件的别名布局
 */
private val selectSignFileAliasLayout = {
    val aliasLabel = JLabel("输入签名文件的别名Alias")
    aliasLabel.setBounds(10, 130, 400, VIEW_LABEL_HEIGHT)
    mFrame.add(aliasLabel)

    mSelectedSignFileAliasField.setBounds(10, 170, 460, VIEW_HEIGHT_50)
    mFrame.add(mSelectedSignFileAliasField)
}


/**
 * 输入签名文件密码
 */
private val selectSignFilePasswordLayout = {
    val aliasLabel = JLabel("输入签名文件的密钥")
    aliasLabel.setBounds(10, 230, 400, VIEW_LABEL_HEIGHT)
    mFrame.add(aliasLabel)

    mSelectedSignFilePasswordField.setBounds(10, 270, 460, VIEW_HEIGHT_50)
    mFrame.add(mSelectedSignFilePasswordField)
}


/**
 * 点击签名布局
 */
private val signLayout = {
    val signButton = JButton("签名")
    signButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    signButton.setBounds(10, 330, 460, VIEW_HEIGHT_50)
    signButton.addActionListener(SignButtonActionListener())
    mFrame.add(signButton)
}


/**
 * 点击选择密钥
 */
private class SelectSignFileButtonActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        mSignFileField.text = filePathResult()
    }
}


/**
 * 点击选择签名文件
 */
private class SelectInstallationPackageButtonActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        mInstallationPackageField.text = filePathResult()
    }
}


/**
 * 点击签名
 */
private class SignButtonActionListener : ActionListener {
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

        if (mSelectedSignFileAliasField.text.isEmpty()) {
            //签名文件别名为空
            JOptionPane.showMessageDialog(null, "签名文件别名不可为空")
            return
        }

        if (mSelectedSignFilePasswordField.text.isEmpty()) {
            //签名文件密钥为空
            JOptionPane.showMessageDialog(null, "签名文件密钥不可为空")
            return
        }
        signProcess()
    }
}


/**
 * 签名进程
 */
private fun signProcess() {
    val signFile = mSignFileField.text
    val installationPackage = mInstallationPackageField.text
    val signFileAlias = mSelectedSignFileAliasField.text
    val signFilePassword = mSelectedSignFilePasswordField.text

    val currentTime = getLocalTime()

    val signPackage = if (installationPackage.endsWith(".apk")) "signed_$currentTime.apk" else "signed_$currentTime.aab "
    val cmd =
        "jarsigner -verbose -keystore $signFile  -storepass $signFilePassword -signedjar $signPackage $installationPackage $signFileAlias"
    cmdProcessDialog(cmd)
}


private fun initFrame() {
    mFrame.run {
        //关闭窗口时退出程序
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        //禁止最大化
        isResizable = false
        //设置高/宽/位置
        setBounds(500, 200, 500, 430)
        //可见
        isVisible = true
    }
}