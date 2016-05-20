package org.ailab.tem.wim;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import flexjson.JSONSerializer;
import org.ailab.wimfra.core.BLMessage;
import org.ailab.wimfra.core.BaseAction;
import org.ailab.wimfra.core.idFactory.IdFactory;
import org.ailab.wimfra.util.Config;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.opensymphony.xwork2.ActionSupport;


public class UploadUtilAction extends BaseAction implements ServletResponseAware {
    private Logger logger = Logger.getLogger(this.getClass());

    //备份图片的路径
    public static String IMAGE_BACKUP_PATH;
    public static String IMAGE_SERVER_PATH="upload/images/";
    private File fileupload;
    private BLMessage blMessage;
    private String imageUrl;
    private String attachmentUrl;
    private String fileRealName;
    private HttpServletResponse response;
    private String fileuploadFileName;

    public UploadUtilAction() {
        IMAGE_BACKUP_PATH = Config.getImageSavePath();
    }


    public String uploadFile() throws SQLException {
        String extName = "";
        String newFileName = "";
        try {
            logger.debug("fileuploadFileName="+fileuploadFileName);
            if (fileuploadFileName!=null && fileuploadFileName.lastIndexOf(".") >= 0) {
                extName = fileuploadFileName.substring(fileuploadFileName.lastIndexOf("."));
            }
            int picNum = IdFactory.getNextId("picture");
            newFileName = picNum + extName;
            //上传文件
            String serverSavePath = ServletActionContext.getServletContext().getRealPath("");
            String filePath = IMAGE_SERVER_PATH + newFileName;
            serverSavePath = serverSavePath.replace("\\", "/");
            final String desPath = serverSavePath + "/" + filePath;
            logger.debug("file copied to "+desPath);
            FileUtils.copyFile(fileupload, new File(desPath));
            //备份文件
            String backUpPath = IMAGE_BACKUP_PATH + newFileName;
            backUpPath = backUpPath.replace("\\", "/");
            logger.debug("file backup to "+backUpPath);
            FileUtils.copyFile(fileupload, new File(backUpPath));

            blMessage = new BLMessage(true, "上传成功", newFileName + "#" + filePath + "#" + fileuploadFileName);
        } catch (Exception e) {
            e.printStackTrace();
            blMessage = new BLMessage(false, "上传失败"+e.toString());
        }
        return "message";
    }

    /**
     * 显示图片
     *
     * @return
     * @throws Exception
     */
    public String showImage() throws Exception {
        if (StringUtil.isEmpty(imageUrl)) {
            return null;
        }
        File file = new File(IMAGE_BACKUP_PATH + imageUrl);
        InputStream is = new FileInputStream(file);
        Image image = ImageIO.read(is);
        String imageType = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
        RenderedImage img = (RenderedImage) image;
        OutputStream out = response.getOutputStream();
        ImageIO.write(img, imageType, out);
        out.flush();
        out.close();
        return null;
    }


    public File getFileupload() {
        return fileupload;
    }

    public void setFileupload(File fileupload) {
        this.fileupload = fileupload;
    }

    public File getFile() {
        return fileupload;
    }

    public void setFile(File file) {
        this.fileupload = file;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getFileRealName() {
        return fileRealName;
    }

    public void setFileRealName(String fileRealName) {
        this.fileRealName = fileRealName;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getFileuploadFileName() {
        return fileuploadFileName;
    }

    public void setFileuploadFileName(String fileuploadFileName) {
        this.fileuploadFileName = fileuploadFileName;
    }

    public String getFileName() {
        return fileuploadFileName;
    }

    public void setFileName(String fileName) {
        this.fileuploadFileName = fileName;
    }

    @Override
    public String myExecute() throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BLMessage getBlMessage() {
        return blMessage;
    }

    public void setBlMessage(BLMessage blMessage) {
        this.blMessage = blMessage;
    }

    public String getMessage() {
        return new JSONSerializer().exclude("class").serialize(blMessage);
    }

    public static String getPhotoRelUrl(String photoUrl) {
        if(StringUtil.isEmpty(photoUrl)){
            return photoUrl;
        } else if(photoUrl.contains("://")){
            return photoUrl;
        } else {
            return UploadUtilAction.IMAGE_SERVER_PATH + photoUrl;
        }
    }


    public static String getPhotoRelUrl(String ctx, String photoUrl) {
        if(StringUtil.isEmpty(photoUrl)){
            return photoUrl;
        } else if(photoUrl.contains("://")){
            return photoUrl;
        } else {
            return ctx+UploadUtilAction.IMAGE_SERVER_PATH + photoUrl;
        }
    }

}
