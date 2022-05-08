package cn.edu.jxnu.service.dao.elasticsearch;

import cn.edu.jxnu.service.entity.Diary;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends ElasticsearchRepository<Diary, Integer> {
}
