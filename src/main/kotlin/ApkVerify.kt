import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JTextField


private val mFrame by lazy {
    JFrame("验签工具")
}


private val mInstallationPackageField by lazy {
    JTextField()
}

private val mSelectSignFileInfoField by lazy {
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
    selectSignFileInfoLayout()
    checkSignFileInfo()
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
 * 选择签名文件的信息
 */
private val selectSignFileInfoLayout = {
    mSelectSignFileInfoField.setBounds(10, 270, TEXT_FIELD_WIDTH_300, VIEW_HEIGHT_50)
    mFrame.add(mSelectSignFileInfoField)

    val selectSignFileButton = JButton("选择签名文件")
    selectSignFileButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    selectSignFileButton.setBounds(320, 270, BUTTON_WIDTH_150, VIEW_HEIGHT_50)
    selectSignFileButton.addActionListener(SelectSignFileInfoButtonActionListener())
    mFrame.add(selectSignFileButton)
}


/**
 * 查看签名文件信息
 */
private val checkSignFileInfo = {
    val checkSignFileInfoButton = JButton("查看签名文件信息")
    checkSignFileInfoButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    checkSignFileInfoButton.setBounds(10, 330, 460, VIEW_HEIGHT_50)
    checkSignFileInfoButton.addActionListener(CheckSignFileInfoButtonActionListener())
    mFrame.add(checkSignFileInfoButton)
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
 * 选择 签名文件
 */
private class SelectSignFileInfoButtonActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        mSelectSignFileInfoField.text = filePathResult()
    }
}


/**
 * 查看签名文件信息
 */
private class CheckSignFileInfoButtonActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        if (mSelectSignFileInfoField.text.isEmpty()) {
            //安装包为空
            JOptionPane.showMessageDialog(null, "签名文件不可为空")
            return
        }

        checkSignFileInfoProcess()
    }
}


/**
 *  验签进程 - apksigner
 */
private fun verifyApkSignProcess() {
    val installationPackage = mInstallationPackageField.text

    try {
        val cmd = "apksigner verify -v $installationPackage"

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


/**
 *  验签进程 - jarsigner
 */
private fun verifyJarSignProcess() {
    val installationPackage = mInstallationPackageField.text

    try {
        val cmd = "jarsigner -verify -verbose -certs $installationPackage"

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

//        val reader = BufferedReader(InputStreamReader(Runtime.getRuntime().exec(cmd).inputStream, "GB2312"))
//        val message = StringBuilder("")
//        var line: String? = reader.readLine()
//        while (line != null && line != "") {
//            message.append(line).append("\n")
//            line = reader.readLine()
//        }
//        if (message.toString() != "") {
//            JOptionPane.showMessageDialog(null, message)
//        }
    } catch (e: Exception) {
        e.printStackTrace()
        println(e.message)
        JOptionPane.showMessageDialog(null, e.message)
    }
}

/**
 *  查看安装包签名信息
 */
private fun checkInstallationPackageSignInfoProcess() {
    val installationPackage = mInstallationPackageField.text

    try {
        val cmd = "keytool -printcert -jarfile $installationPackage"

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

//        val reader = BufferedReader(InputStreamReader(Runtime.getRuntime().exec(cmd).inputStream, "GB2312"))
//        val message = StringBuilder("")
//        var line: String? = reader.readLine()
//        while (line != null && line != "") {
//            message.append(line).append("\n")
//            line = reader.readLine()
//        }
//        if (message.toString() != "") {
//            JOptionPane.showMessageDialog(null, message)
//        }
    } catch (e: Exception) {
        e.printStackTrace()
        println(e.message)
        JOptionPane.showMessageDialog(null, e.message)
    }
}


/**
 *  查看安装包签名信息
 */
private fun checkSignFileInfoProcess() {
    val signInfo = mSelectSignFileInfoField.text

    try {
        val cmd = "keytool -list -v -keystore $signInfo"

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

//        val reader = BufferedReader(InputStreamReader(Runtime.getRuntime().exec(cmd).inputStream, "GB2312"))
//        val message = StringBuilder("")
//        var line: String? = reader.readLine()
//        while (line != null && line != "") {
//            message.append(line).append("\n")
//            line = reader.readLine()
//        }
//        if (message.toString() != "") {
//            JOptionPane.showMessageDialog(null, message)
//        }
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
        setBounds(500, 200, 500, 450)
        //可见
        isVisible = true
    }
}



