import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JTextField


private val mFrame by lazy {
    JFrame("验签工具 apksigner")
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
    signLayout()
}


/**
 * 选择安装包布局
 */
private val selectInstallationPackageLayout = {
    mInstallationPackageField.setBounds(10, 10, TEXT_FIELD_WIDTH, VIEW_HEIGHT)
    mFrame.add(mInstallationPackageField)

    val selectInstallationPackageButton = JButton("选择apk")
    selectInstallationPackageButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE)
    selectInstallationPackageButton.setBounds(320, 10, BUTTON_WIDTH, VIEW_HEIGHT)
    selectInstallationPackageButton.addActionListener(ApkVerifySelectInstallationPackageButtonActionListener())
    mFrame.add(selectInstallationPackageButton)
}


/**
 * 点击签名布局
 */
private val signLayout = {
    val signButton = JButton("验证签名")
    signButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE)
    signButton.setBounds(10, 120, 460, 100)
    signButton.addActionListener(ApkVerifySignButtonActionListener())
    mFrame.add(signButton)
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
 * 点击验签
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

        verifySignProcess()

    }
}


/**
 *  验签进程
 */
private fun verifySignProcess() {
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


private fun initFrame() {
    mFrame.run {
        //关闭窗口时退出程序
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        //禁止最大化
        isResizable = false
        //设置高/宽/位置
        setBounds(500, 200, 500, 270)
        //可见
        isVisible = true
    }
}



