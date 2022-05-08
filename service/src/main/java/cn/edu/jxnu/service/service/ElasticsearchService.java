package cn.edu.jxnu.service.service;

import cn.edu.jxnu.service.dao.elasticsearch.DiaryRepository;
import cn.edu.jxnu.service.entity.Diary;
import cn.edu.jxnu.service.util.RedisKeyUtil;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ElasticsearchService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    public void saveDiary(Diary diary) {
        diaryRepository.save(diary);
    }

    public void deleteDiaryById(int id) {
        diaryRepository.deleteById(id);
    }

    public Page<Diary> searchDiscussPost(int userId, String keyword, int current, int limit) {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

        if (userId != 0) {
            searchQueryBuilder
                    .withQuery(QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("userId", userId))
                            .must(QueryBuilders.multiMatchQuery(keyword, "content")))
                    .withSort(SortBuilders.fieldSort("hot").order(SortOrder.DESC))
                    .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                    .withPageable(PageRequest.of(current, limit))
                    .withHighlightFields(new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>"));
        } else {
            searchQueryBuilder
                    .withQuery(QueryBuilders.multiMatchQuery(keyword, "content"))
                    .withSort(SortBuilders.fieldSort("hot").order(SortOrder.DESC))
                    .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                    .withPageable(PageRequest.of(current, limit))
                    .withHighlightFields(new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>"));
        }
        NativeSearchQuery searchQuery = searchQueryBuilder.build();

        return elasticsearchTemplate.queryForPage(searchQuery, Diary.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {

                SearchHits hits = response.getHits();
                if (hits.totalHits <= 0) {
                    return null;
                }

                List<Diary> list = new ArrayList<>();
                for (SearchHit hit : hits) {
                    Diary diary = new Diary();

                    String id = hit.getSourceAsMap().get("id").toString();
                    diary.setId(Integer.parseInt(id));

                    String userId = hit.getSourceAsMap().get("userId").toString();
                    diary.setUserId(Integer.parseInt(userId));

                    String content = hit.getSourceAsMap().get("content").toString();
                    diary.setContent(content);

                    String createTime = hit.getSourceAsMap().get("createTime").toString();
                    diary.setCreateTime(new Date(Long.parseLong(createTime)));

                    String commentNum = hit.getSourceAsMap().get("commentNum").toString();
                    diary.setCommentNum(Integer.parseInt(commentNum));

                    String hot = hit.getSourceAsMap().get("hot").toString();
                    diary.setHot(Double.parseDouble(hot));

                    // 处理高亮显示效果
//                    HighlightField contentField = hit.getHighlightFields().get("content");
//                    if (contentField != null) {
//                        diary.setContent(contentField.getFragments()[0].toString());
//                    }

                    list.add(diary);
                }

                return new AggregatedPageImpl(list, pageable, hits.getTotalHits(), response.getAggregations(), response.getScrollId(), hits.getMaxScore());
            }
        });
    }

    public Map<String, Object> getHotSearchKeyword() {
        String redisKey = RedisKeyUtil.getSearchKeywordKey();
        Set<String> set = redisTemplate.opsForZSet().reverseRange(redisKey, 0, -1);
        Map<String, Object> map = new HashMap<>();
        for (String s : set) {
            map.put(s, redisTemplate.opsForZSet().score(redisKey, s));
            if (map.size() == 15) break;
        }
        return map;
    }

    public void countSearchKeyword(String keyword) {
        List<String> list = getIkAnalyzeSearchTerms(keyword, "ik_smart");
        String redisKey = RedisKeyUtil.getSearchKeywordKey();

        if (list != null) {
            for (String s : list) {
                if (s.length() < 2) continue;
                redisTemplate.opsForZSet().incrementScore(redisKey, s, 1);
            }
        }
    }

    private List<String> getIkAnalyzeSearchTerms(String searchContent, String ikAnalyzer) {
        String index = "diary"; // elasticsearch里面的index

        // 调用 IK 分词分词
        AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(elasticsearchTemplate.getClient(), AnalyzeAction.INSTANCE, index, searchContent);

        ikRequest.setTokenizer(ikAnalyzer);
        List<AnalyzeResponse.AnalyzeToken> tokenList = ikRequest.execute().actionGet().getTokens();

        // 循环赋值
        List<String> searchTermList = new ArrayList<>();
        tokenList.forEach(ikToken -> {
            searchTermList.add(ikToken.getTerm());
        });

        return searchTermList;

    }

}
