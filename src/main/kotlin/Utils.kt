import java.io.File
import javax.swing.JFileChooser

const val TEXT_FIELD_WIDTH_300 = 300
const val BUTTON_WIDTH_150 = 150
const val BUTTON_TEXT_SIZE_16 = 16
const val VIEW_HEIGHT_100 = 100
const val VIEW_HEIGHT_50 = 50


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