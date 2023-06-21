import java.awt.Dimension
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.swing.*


const val TEXT_FIELD_WIDTH_300 = 300
const val BUTTON_WIDTH_150 = 150
const val BUTTON_TEXT_SIZE_16 = 16
const val VIEW_HEIGHT_100 = 100
const val VIEW_HEIGHT_50 = 50
const val VIEW_LABEL_HEIGHT = 30

const val CURRENT_DIR = "user.dir"


/**
 * 选中文件的绝对路径
 */
fun filePathResult(): String? {
    val fileChooser = JFileChooser()

    //设置文件选择器的初始目录为当前目录
    val currentDirectory = System.getProperty(CURRENT_DIR)
    fileChooser.currentDirectory = File(currentDirectory)

    val result = fileChooser.showOpenDialog(null)
    if (result == JFileChooser.APPROVE_OPTION) {
        val selectFile = fileChooser.selectedFile
        return selectFile.absolutePath
    }
    return ""
}


/**
 * 开启cmd进程
 */
fun cmdProcess(cmd: String?) {
    cmd?.let {
        try {
            val pb = ProcessBuilder("cmd", "/c", "start $it")
            val process = pb.start()
            // 等待命令执行完成
            val exitCode: Int = process.waitFor()
            println("Exit Code: $exitCode")
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message)
            JOptionPane.showMessageDialog(null, "异常报错:\n${e.message}")
        }
    }
}


/**
 * 开启cmd进程
 */
fun cmdProcessDialog(
    cmd: String?,
    tips: String = ""
) {
    val message = StringBuilder("\n")
    if (tips.isNotEmpty()) {
        message.append(tips).append("\n")
    }
    cmd?.let {
        try {
            val reader =
                BufferedReader(InputStreamReader(Runtime.getRuntime().exec("cmd /c  $it").inputStream, "GB2312"))
            var line: String? = reader.readLine()
            while (line != null) {
                message.append("      ").append(line).append("\n")
                line = reader.readLine()
                println(line)
            }
            if (message.toString() != "") {
//                JOptionPane.showMessageDialog(null, message.toString(), "提示信息", JOptionPane.INFORMATION_MESSAGE)
                showScrollDialog(message.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message)
            JOptionPane.showMessageDialog(null, "异常报错:\n${e.message}")
        }
    }
}


fun getLocalTime(): String {
    val currentTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    return currentTime.format(formatter)
}


fun showScrollDialog(message: String) {
    val textArea = JTextArea(message)
    textArea.isEditable = false
    val scrollPane = JScrollPane(textArea)
    scrollPane.preferredSize = Dimension(600, 300)

    val dialog = JDialog()
    dialog.title = "提示信息"
    dialog.contentPane = scrollPane
    dialog.isModal = true
    dialog.pack()
    dialog.setLocationRelativeTo(null)
    dialog.isVisible = true
}

