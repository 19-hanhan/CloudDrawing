package cn.edu.jxnu.service.event;

import cn.edu.jxnu.service.entity.Diary;
import cn.edu.jxnu.service.entity.Event;
import cn.edu.jxnu.service.entity.Notice;
import cn.edu.jxnu.service.service.DiaryService;
import cn.edu.jxnu.service.service.ElasticsearchService;
import cn.edu.jxnu.service.service.NoticeService;
import cn.edu.jxnu.service.util.ServerConstant;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EventConsumer implements ServerConstant {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @KafkaListener(topics = {TOPIC_COMMENT, TOPIC_LIKE})
    public void handleCommentMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            logger.error("消息内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            logger.error("消息格式错误!");
            return;
        }

        // 发送站内通知
        Notice notice = new Notice();
        notice.setSenderId(event.getUserId());
        notice.setDiaryId(event.getEntityId());
        notice.setActionId(event.getEntityUserId());
        notice.setActionContent((String) event.getData().get("content"));
        notice.setCreateTime(new Date());
        notice.setExist(1);


        noticeService.addNotice(notice);
    }

    @KafkaListener(topics = {TOPIC_PUBLISH})
    public void handlePublishMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            logger.error("消息内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            logger.error("消息格式错误!");
            return;
        }

        Diary diary = diaryService.findDiaryById(event.getEntityId());
        elasticsearchService.saveDiary(diary);
    }

    @KafkaListener(topics = {TOPIC_DELETE})
    public void handleDeleteMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            logger.error("消息内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            logger.error("消息格式错误!");
            return;
        }

        elasticsearchService.deleteDiaryById(event.getEntityId());
    }

}
