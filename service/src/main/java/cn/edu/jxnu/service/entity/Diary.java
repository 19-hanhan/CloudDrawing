package cn.edu.jxnu.service.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "diary", type = "_doc", shards = 6, replicas = 3)
public class Diary {
    @Id
    private int id;

    @Field(type = FieldType.Integer)
    private int userId;

    @Field(type = FieldType.Date)
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    @Field(type = FieldType.Integer)
    private int likeNum;

    @Field(type = FieldType.Integer)
    private int commentNum;

    @Field(type = FieldType.Double)
    private double hot;

    @Field(type = FieldType.Integer)
    private int exist;

}
