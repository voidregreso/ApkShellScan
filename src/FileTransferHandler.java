import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

class FileTransferHandler
    extends TransferHandler {
    private ApkScanWin apkScanWin;

    public FileTransferHandler(ApkScanWin apkScanWin) {
        this.apkScanWin = apkScanWin;
    }

    public boolean importData(JComponent comp, Transferable t) {
        try {
            List<File> list = (List<File>)t.getTransferData(DataFlavor.javaFileListFlavor);
            Iterator<File> it = list.iterator();
            if(it.hasNext()) {
                File f = it.next();
                this.apkScanWin.getTextField1().setText(f.getAbsolutePath());
                List<String> result = MyUtil.readZipFile(f.getAbsolutePath());
                if(result.size() != 1) {
                    boolean flag = true;
                    for(String fileName : result) {
                        if(this.apkScanWin.getMarkNameMap().containsKey(fileName)) {
                            this.apkScanWin.getTextField2().setText("«" + this.apkScanWin.getMarkNameMap().get(fileName) + "»");
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        this.apkScanWin.getTextField2().setText("Apk is not encrypted!");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean canImport(TransferHandler.TransferSupport support) {
        return true;
    }
}
