import java.io.File
import javax.swing.JFileChooser

/**
 * 选中文件的绝对路径
 */
 fun filePathResult(): String? {
    val fileChooser = JFileChooser()

    //设置文件选择器的初始目录为当前目录
    val currentDirectory = System.getProperty("user.dir")
    fileChooser.currentDirectory = File(currentDirectory)

    val result = fileChooser.showOpenDialog(null)
    if (result == JFileChooser.APPROVE_OPTION) {
        val selectFile = fileChooser.selectedFile
        return selectFile.absolutePath
    }
    return ""
}