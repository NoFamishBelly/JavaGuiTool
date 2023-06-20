import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

private val mFrame by lazy {
    JFrame("签名文件信息")
}


private val mSelectSignFileInfoField by lazy {
    JTextField()
}

private val mSignFilePasswordField by lazy {
    JTextField()
}


fun main() {

    //绝对布局
    mFrame.layout = null
    setLayout()
    initFrame()


}

private fun setLayout() {
    selectSignFileInfoLayout()
    signFilePasswordLayout()
    checkSignFileInfo()
}


/**
 * 选择签名文件的信息
 */
private val selectSignFileInfoLayout = {
    mSelectSignFileInfoField.setBounds(10, 10, TEXT_FIELD_WIDTH_300, VIEW_HEIGHT_100)
    mFrame.add(mSelectSignFileInfoField)

    val selectSignFileButton = JButton("选择签名文件")
    selectSignFileButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    selectSignFileButton.setBounds(320, 10, BUTTON_WIDTH_150, VIEW_HEIGHT_100)
    selectSignFileButton.addActionListener(SelectSignFileInfoButtonActionListener())
    mFrame.add(selectSignFileButton)
}


/**
 * 签名文件密码
 */
private val signFilePasswordLayout = {

    val signFilePasswordLabel = JLabel("输入签名文件密钥")
    signFilePasswordLabel.setBounds(10, 120, 460, 30)
    mFrame.add(signFilePasswordLabel)

    mSignFilePasswordField.setBounds(10, 160, 460, VIEW_HEIGHT_100)
    mFrame.add(mSignFilePasswordField)
}


/**
 * 查看签名文件信息
 */
private val checkSignFileInfo = {
    val checkSignFileInfoButton = JButton("查看签名文件信息")
    checkSignFileInfoButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    checkSignFileInfoButton.setBounds(10, 270, 460, VIEW_HEIGHT_100)
    checkSignFileInfoButton.addActionListener(CheckSignFileInfoButtonActionListener())
    mFrame.add(checkSignFileInfoButton)
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
            //签名文件为空
            JOptionPane.showMessageDialog(null, "签名文件不可为空")
            return
        }
        if (mSignFilePasswordField.text.isEmpty()) {
            //签名文件密码为空
            JOptionPane.showMessageDialog(null, "签名文件密码不可为空")
            return
        }
        checkSignFileInfoProcess()
    }
}


/**
 *  查看签名文件信息
 */
private fun checkSignFileInfoProcess() {
    val cmd = "keytool -list -v -keystore ${mSelectSignFileInfoField.text} -storepass ${mSignFilePasswordField.text}"
    cmdProcessDialog(cmd)
}


private fun initFrame() {
    mFrame.run {
        //关闭窗口时退出程序
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        //禁止最大化
        isResizable = false
        //设置高/宽/位置
        setBounds(500, 150, 500, 430)
        //可见
        isVisible = true
    }
}
