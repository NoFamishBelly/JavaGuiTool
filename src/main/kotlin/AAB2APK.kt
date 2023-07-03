import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.lang.StringBuilder
import javax.swing.*


private val mFrame by lazy {
    JFrame("aab转apk")
}


private val mSelectAABField by lazy {
    JTextField()
}

private val mSelectSignFileField by lazy {
    JTextField()
}


private val mSelectSignFileAliasField by lazy {
    JTextField()
}

private val mSelectSignFilePasswordField by lazy {
    JTextField()
}


fun main() {
    //绝对布局
    mFrame.layout = null
    setLayout()
    initFrame()
}


private fun setLayout() {
    selectAABLayout()
    selectSignFileLayout()
    inputSignFileAliasLayout()
    inputSignFilePasswordLayout()
    aab2ApkLayout()
}


/**
 * 选择aab布局
 */
private val selectAABLayout = {
    mSelectAABField.setBounds(10, 10, TEXT_FIELD_WIDTH_300, VIEW_HEIGHT_50)
    mFrame.add(mSelectAABField)

    val selectAABButton = JButton("选择aab")
    selectAABButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    selectAABButton.setBounds(320, 10, BUTTON_WIDTH_150, VIEW_HEIGHT_50)
    selectAABButton.addActionListener(SelectAABActionListener())
    mFrame.add(selectAABButton)
}


/**
 * 选择签名文件
 */
private val selectSignFileLayout = {
    mSelectSignFileField.setBounds(10, 70, TEXT_FIELD_WIDTH_300, VIEW_HEIGHT_50)
    mFrame.add(mSelectSignFileField)

    val selectSignFileButton = JButton("选择签名文件")
    selectSignFileButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    selectSignFileButton.setBounds(320, 70, BUTTON_WIDTH_150, VIEW_HEIGHT_50)
    selectSignFileButton.addActionListener(SelectSignFileActionListener())
    mFrame.add(selectSignFileButton)
}

/**
 * 输入签名文件别名
 */
private val inputSignFileAliasLayout = {
    val signFileAliasLabel = JLabel("输入签名文件别名")
    signFileAliasLabel.setBounds(10, 130, 460, VIEW_LABEL_HEIGHT)
    mFrame.add(signFileAliasLabel)

    mSelectSignFileAliasField.setBounds(10, 170, 460, VIEW_HEIGHT_50)
    mFrame.add(mSelectSignFileAliasField)
}

/**
 * 输入签名文件密钥
 */
private val inputSignFilePasswordLayout = {
    val signFilePasswordLabel = JLabel("输入签名文件密码")
    signFilePasswordLabel.setBounds(10, 230, 460, VIEW_LABEL_HEIGHT)
    mFrame.add(signFilePasswordLabel)

    mSelectSignFilePasswordField.setBounds(10, 270, 460, VIEW_HEIGHT_50)
    mFrame.add(mSelectSignFilePasswordField)
}


/**
 * aab转apk布局
 */
private val aab2ApkLayout = {
    val aab2ApkButton = JButton("aab转apk")
    aab2ApkButton.font = Font("", Font.BOLD, BUTTON_TEXT_SIZE_16)
    aab2ApkButton.setBounds(10, 330, 460, VIEW_HEIGHT_50)
    aab2ApkButton.addActionListener(AAB2APKActionListener())
    mFrame.add(aab2ApkButton)
}


/**
 * 点击选择aab
 */
private class SelectAABActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        mSelectAABField.text = filePathResult()
    }
}


/**
 * 点击选择签名文件
 */
private class SelectSignFileActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        mSelectSignFileField.text = filePathResult()
    }
}


/**
 * 点击aab 2 apk
 */
private class AAB2APKActionListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {

        if (mSelectAABField.text.isEmpty()) {
            //aab文件不可为空
            JOptionPane.showMessageDialog(null, "aab文件不可为空")
            return
        }

        if (!mSelectAABField.text.endsWith(".aab")) {
            //仅支持aab文件
            JOptionPane.showMessageDialog(null, "仅支持aab文件")
            return
        }

        if (mSelectSignFileField.text.isEmpty()) {
            //签名文件不可为空
            JOptionPane.showMessageDialog(null, "签名文件不可为空")
            return
        }

        if (mSelectSignFileAliasField.text.isEmpty()) {
            //签名文件别名不可为空
            JOptionPane.showMessageDialog(null, "签名文件别名不可为空")
            return
        }

        if (mSelectSignFilePasswordField.text.isEmpty()) {
            //签名文件密码不可为空
            JOptionPane.showMessageDialog(null, "签名文件密码不可为空")
            return
        }


        aab2apkProcess()
    }
}


/**
 * aab 转 apk
 */
private fun aab2apkProcess() {

    val aabFile = mSelectAABField.text
    val signFile = mSelectSignFileField.text
    val signFileAlias = mSelectSignFileAliasField.text
    val signFilePassword = mSelectSignFilePasswordField.text

    val apks = "apk_${getLocalTime()}.apks"

    val cmd =
        "java -jar bundletool.jar build-apks --bundle=$aabFile --output=$apks --ks=$signFile --ks-pass=pass:$signFilePassword --ks-key-alias=$signFileAlias --key-pass=pass:$signFilePassword"

    val tips = StringBuilder()
        .append("      ").append("转换完成").append("\n")
        .append("      ").append("生成文件:  $apks").append("\n")
        .append("      ").append("将.apks文件修改为.zip文件, 然后解压, 即可得到apk").append("\n\n")
        .append("      ").append("注：若未生成$apks, 则转换失败")


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
        setBounds(500, 220, 500, 430)
        //可见
        isVisible = true
    }
}

