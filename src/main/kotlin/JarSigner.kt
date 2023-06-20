import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.*


const val TEXT_FIELD_WIDTH = 300
const val BUTTON_WIDTH = 150
const val BUTTON_TEXT_SIZE = 16
const val VIEW_HEIGHT = 100


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
    signLayout()
}


/**
 * 选择签名文件布局
 */
private val selectSignFileLayout = {
    mSignFileField.setBounds(10, 10, TEXT_FIELD_WIDTH, VIEW_HEIGHT)
    mFrame.add(mSignFileField)

    val selectSignFileButton = JButton("选择签名文件")
    selectSignFileButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE)
    selectSignFileButton.setBounds(320, 10, BUTTON_WIDTH, VIEW_HEIGHT)
    selectSignFileButton.addActionListener(SelectSignFileButtonActionListener())
    mFrame.add(selectSignFileButton)
}


/**
 * 选择安装包布局
 */
private val selectInstallationPackageLayout = {
    mInstallationPackageField.setBounds(10, 120, TEXT_FIELD_WIDTH, VIEW_HEIGHT)
    mFrame.add(mInstallationPackageField)

    val selectInstallationPackageButton = JButton("选择apk/aab")
    selectInstallationPackageButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE)
    selectInstallationPackageButton.setBounds(320, 120, BUTTON_WIDTH, VIEW_HEIGHT)
    selectInstallationPackageButton.addActionListener(SelectInstallationPackageButtonActionListener())
    mFrame.add(selectInstallationPackageButton)
}


/**
 * 签名文件的别名布局
 */
private val selectSignFileAliasLayout = {
    val aliasLabel = JLabel("输入签名文件的别名Alias")
    aliasLabel.setBounds(10, 230, 400, 20)
    mFrame.add(aliasLabel)

    mSelectedSignFileAliasField.setBounds(10, 260, 460, 40)
    mFrame.add(mSelectedSignFileAliasField)
}


/**
 * 点击签名布局
 */
private val signLayout = {
    val signButton = JButton("签名")
    signButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE)
    signButton.setBounds(10, 310, 460, 100)
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

    try {
        val signPackage = if (installationPackage.endsWith(".apk")) "signed.apk" else "signed.aab "
        val cmd =
            "jarsigner -verbose -keystore $signFile -signedjar $signPackage $installationPackage $signFileAlias"

        val pb = ProcessBuilder("cmd", "/c", "start $cmd")
        val process = pb.start()

//        // 获取命令行窗口输出流
//        val inputStream = process.inputStream
//        val reader = BufferedReader(InputStreamReader(inputStream))
//
//        // 读取输出信息
//        var line: String?
//        while (reader.readLine().also { line = it } != null) {
//            println(line)
//        }

        // 等待命令执行完成
        val exitCode: Int = process.waitFor()
        println("Exit Code: $exitCode")

//        // 关闭资源
//        reader.close()
//        inputStream.close()

    } catch (e: Exception) {
        e.printStackTrace()
        println(e.message)
        JOptionPane.showMessageDialog(null, e.message)
    }
}


private fun initFrame() {
    mFrame.run {
        //关闭窗口时退出程序
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        //禁止最大化
        isResizable = false
        //设置高/宽/位置
        setBounds(500, 200, 500, 500)
        //可见
        isVisible = true
    }
}