import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JTextField


private val mFrame by lazy {
    JFrame("验签工具")
}


private val mInstallationPackageField by lazy {
    JTextField()
}



fun main() {
    //绝对布局
    mFrame.layout = null
    setLayout()
    initFrame()
}

private fun setLayout() {
    selectInstallationPackageLayout()
    verifyApkSignLayout()
    verifyJarSignLayout()
    checkInstallationPackageSignInfoLayout()
}


/**
 * 选择安装包布局
 */
private val selectInstallationPackageLayout = {
    mInstallationPackageField.setBounds(10, 10, TEXT_FIELD_WIDTH_300, VIEW_HEIGHT_50)
    mFrame.add(mInstallationPackageField)

    val selectInstallationPackageButton = JButton("选择apk/aab")
    selectInstallationPackageButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    selectInstallationPackageButton.setBounds(320, 10, BUTTON_WIDTH_150, VIEW_HEIGHT_50)
    selectInstallationPackageButton.addActionListener(ApkVerifySelectInstallationPackageButtonActionListener())
    mFrame.add(selectInstallationPackageButton)
}


/**
 * 点击验签布局 - apksigner
 */
private val verifyApkSignLayout = {
    val signButton = JButton("验证签名 - apksigner (仅限apk)")
    signButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    signButton.setBounds(10, 70, 460, VIEW_HEIGHT_50)
    signButton.addActionListener(ApkVerifySignButtonActionListener())
    mFrame.add(signButton)
}

/**
 * 点击验签布局 - jarsigner
 */
private val verifyJarSignLayout = {
    val signButton = JButton("验证签名 - jarsigner")
    signButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    signButton.setBounds(10, 130, 460, VIEW_HEIGHT_50)
    signButton.addActionListener(JarVerifySignButtonActionListener())
    mFrame.add(signButton)
}


/**
 * 查看安装包的签名信息
 */
private val checkInstallationPackageSignInfoLayout = {
    val checkInstallationPackageSignInfoButton = JButton("查看安装包签名信息")
    checkInstallationPackageSignInfoButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    checkInstallationPackageSignInfoButton.setBounds(10, 190, 460, VIEW_HEIGHT_50)
    checkInstallationPackageSignInfoButton.addActionListener(CheckInstallationPackageSignInfoButtonActionListener())
    mFrame.add(checkInstallationPackageSignInfoButton)
}





/**
 * 点击选择安装包apk
 */
private class ApkVerifySelectInstallationPackageButtonActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        mInstallationPackageField.text = filePathResult()
    }
}

/**
 * 点击验签 - apksigner
 */
private class ApkVerifySignButtonActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
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

        verifyApkSignProcess()

    }
}


/**
 * 点击验签 - jarsigner
 */
private class JarVerifySignButtonActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        if (mInstallationPackageField.text.isEmpty()) {
            //安装包为空
            JOptionPane.showMessageDialog(null, "安装包不可为空")
            return
        }

        verifyJarSignProcess()
    }
}


/**
 * 查看安装包签名信息
 */
private class CheckInstallationPackageSignInfoButtonActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        if (mInstallationPackageField.text.isEmpty()) {
            //安装包为空
            JOptionPane.showMessageDialog(null, "安装包不可为空")
            return
        }


        checkInstallationPackageSignInfoProcess()
    }
}





/**
 *  验签进程 - apksigner
 */
private fun verifyApkSignProcess() {
    val cmd = "apksigner verify -v ${mInstallationPackageField.text}"
    cmdProcessDialog(cmd)
}


/**
 *  验签进程 - jarsigner
 */
private fun verifyJarSignProcess() {
    val cmd = "jarsigner -verify -verbose -certs ${mInstallationPackageField.text}"
    cmdProcessDialog(cmd)
}

/**
 *  查看安装包签名信息
 */
private fun checkInstallationPackageSignInfoProcess() {
    val cmd = "keytool -printcert -jarfile ${mInstallationPackageField.text}"
    cmdProcessDialog(cmd)
}


private fun initFrame() {
    mFrame.run {
        //关闭窗口时退出程序
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        //禁止最大化
        isResizable = false
        //设置高/宽/位置
        setBounds(500, 220, 500, 300)
        //可见
        isVisible = true
    }
}



