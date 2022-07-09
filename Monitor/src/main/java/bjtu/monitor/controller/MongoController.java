package bjtu.monitor.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @author song
 * @Title: MongoService
 * @ProjectName qyfu
 * @Description: TODO
 * @date 2020/7/22 21:08
 */
@RestController
@RequestMapping("/Mongo")
//@Api(tags = "Mongo工具类")
public class MongoController {

    @Autowired
    private GridFsTemplate gridfsTemplate;
    /*    @Autowired
        private MongoDbFactory mongoDbFactory;*/
    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * 根据id查询文件
     *
     * @return
     */
    @GetMapping("/findFileById")
//    @ApiOperation("查询文件")
    public void findFileById(String id, HttpServletResponse response) throws Exception {
        GridFSFile result = gridfsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        GridFSFile gridFSFile = gridfsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
        //打开下载流对象
        if (null != gridFSFile) {
            GridFSDownloadStream gridFSDownloadStream =
                    gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建gridFsResource，用于获取流对象
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            //获取流中的数据
            InputStream inputStream = gridFsResource.getInputStream();

            byte[] bytes = new byte[1024];
            // 创建基于文件的输出流
            // FileOutputStream fos = new FileOutputStream(f1);
            OutputStream out = response.getOutputStream();
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                // fos.write(bytes, 0, len);
                out.write(bytes);
            }
            inputStream.close();
            out.close();
        }
    }


    /**
     * 下载文件
     *
     * @param fileId
     * @param request
     * @param response
     * @throws IOException
     */
//    @ApiOperation(value = "下载文件测试", notes = "下载文件测试demo")
    @RequestMapping(value = "/download", method = {RequestMethod.GET, RequestMethod.POST})
    public void downloadFile(@RequestParam(name = "file_id") String fileId,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        Query query = Query.query(Criteria.where("_id").is(fileId));
        // 查询单个文件
        GridFSFile gridFSFile = gridfsTemplate.findOne(query);
        if (gridFSFile == null) {
            return;
        }

        String fileName = gridFSFile.getFilename().replace(",", "");
        String contentType = gridFSFile.getMetadata().get("_contentType").toString();

        // 通知浏览器进行文件下载
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource resource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = resource.getInputStream();
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }


    /**
     * 保存文件
     *
     * @param file
     */
    @PostMapping("/uploadFile")
//    @ApiOperation("单文件上传")
    public String saveFile(@RequestParam MultipartFile file, @RequestParam String filename) throws IOException {
        String type = filename.substring(filename.lastIndexOf("."), filename.length());
        ObjectId id = gridfsTemplate.store(file.getInputStream(), UUID.randomUUID().toString().replaceAll("-", "") + type, file.getContentType());

        return id.toString();
    }

    /**
     * 根据id删除文件
     */
    @GetMapping("/deleteFileById")
//    @ApiOperation("删除文件")
    @ResponseBody
    public void deleteFileById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        if (query != null) {
            gridfsTemplate.delete(query);
        } else {
            System.out.println("没有此项数据");
        }
    }

    /**
     * 上传图片
     *
     * @param blobFile
     */
    @PostMapping("/uploadImg")
//    @ApiOperation("图片上传")
    public String uploadFile(@RequestParam("file") MultipartFile blobFile) throws IOException {
        ObjectId id = gridfsTemplate.store(blobFile.getInputStream(), blobFile.getContentType());
        return id.toString();
    }
}
