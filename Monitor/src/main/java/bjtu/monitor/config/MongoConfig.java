package bjtu.monitor.config;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;


@SuppressWarnings("deprecation")
@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.database}")
    String db;

    @Value("${spring.data.mongodb.username}")
    String user;
    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(db);
        GridFSBucket bucket = GridFSBuckets.create(database,user);
        return bucket;
    }
//    @Bean(name = "gridFsTemplate")
//    public GridFsTemplate gridFsTemplate(MongoConverter converter) {
//        return new GridFsTemplate();
//    }

//    @Bean(name = "gridFsPictureBucket")
//    public GridFSBucket getGridFsPictureBucket(MongoClient mongoClient){
//
//        MongoDatabase mongoDatabase = mongoClient.getDatabase(db);
//        // 注意，这里的bucketName与需要下载的存储桶要保持一致，否则会到默认的fs存储桶中进行下载
//        GridFSBucket bucket = GridFSBuckets.create(mongoDatabase,user);
//        return bucket;
//    }
//
//
//    @Bean(name = "gridFsPictureTemplate")
//    public GridFsTemplate gridFsPictureTemplate(MongoDatabaseFactory dbFactory, MongoConverter converter) {
//        return new GridFsTemplate(dbFactory, converter, user);
//    }
//    @Bean(name = "gridFsPdfTemplate")
//    public GridFsTemplate gridFsTestTemplate(MongoDatabaseFactory dbFactory, MongoConverter converter) {
//        return new GridFsTemplate(dbFactory, converter, user);
//    }

}

