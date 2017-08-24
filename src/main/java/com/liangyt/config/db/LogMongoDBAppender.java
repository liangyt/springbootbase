package com.liangyt.config.db;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.status.ErrorStatus;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * 描述：日志保存到mongodb
 *
 * @author tony
 * @创建时间 2017-08-24 11:03
 */
@SuppressWarnings("all")
public class LogMongoDBAppender extends AppenderBase<LoggingEvent> {

    private MongoClient mongo;
    private MongoCollection<Document> collection;
    private String dbHost;
    private int dbPort;
    private String dbName;
    private String dbCollectionName;

    @Override
    public void start() {

        try {
            mongo = new MongoClient(dbHost, dbPort);
            MongoDatabase db = mongo.getDatabase(dbName);
            collection = db.getCollection(dbCollectionName);
        } catch (Exception e) {
            addStatus(new ErrorStatus("Failed to initialize MondoDB", this, e));
            return;
        }
        super.start();
    }

    @Override
    public void stop() {
        mongo.close();
        super.stop();
    }

    @Override
    protected void append(LoggingEvent eventObject) {
        try{
            Document document = getDocument(eventObject.getFormattedMessage());
            if(document != null){
                collection.insertOne(document);
            }
        }catch (Exception e) {
            addStatus(new ErrorStatus("日志写入到MongDB出错", this, e));
        }
    }

    /**
     * 这个需要确保进来的字符串是json 格式的，否则解析异常
     * @param json
     * @return
     */
    private Document getDocument(String json) {
        try{
            return Document.parse("{\"log\":\"" + json + "\"}");
        }catch (Exception e) {
            return null;
        }
    }

    public MongoClient getMongo() {
        return mongo;
    }

    public void setMongo(MongoClient mongo) {
        this.mongo = mongo;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void setCollection(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public int getDbPort() {
        return dbPort;
    }

    public void setDbPort(int dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbCollectionName() {
        return dbCollectionName;
    }

    public void setDbCollectionName(String dbCollectionName) {
        this.dbCollectionName = dbCollectionName;
    }
}
