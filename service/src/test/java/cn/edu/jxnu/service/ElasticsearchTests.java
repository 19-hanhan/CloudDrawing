package cn.edu.jxnu.service;

import cn.edu.jxnu.service.entity.Diary;
import cn.edu.jxnu.service.service.DiaryService;
import cn.edu.jxnu.service.service.ElasticsearchService;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ServiceApplication.class)
public class ElasticsearchTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private DiaryService diaryService;

    @Test
    public void testIkAnalyzeSearchTerms() {
        String s = "行程安排\n" +
                "本篇游记记录我们在喀纳斯和可可托海的6天行程：\n" +
                "9月23日（第一天）：从北哈巴村乘坐景区区间车抵达喀纳斯村，夜宿喀纳斯村\n" +
                "9月24日（第二天）：继续在喀纳斯游玩，仍然住在喀纳斯村\n" +
                "9月25日（第三天）：从喀纳斯村乘坐出租车到禾木村，夜宿禾木村\n" +
                "9月26日（第四天）：离开禾木村前往可可托海，一天都在路上，夜宿可可托海\n" +
                "9月27日（第五天）：游玩可可托海景区和3号矿区，晚上赶到富蕴县住宿\n" +
                "9月28日（第六天）：乘坐商务车回到乌鲁木齐，全天在路上，夜宿乌鲁木齐，完成北疆大环线的全部行程\n" +
                "游记正文\n" +
                "第一天\n" +
                "今天我们从北哈巴村乘坐景区区间车抵达喀纳斯村，住下后就先后去“观鱼台”和喀纳斯湖两个景区，晚上住在喀纳斯村。\n" +
                "喀纳斯村\n" +
                "由于昨天就了解到从北哈巴村到喀纳斯村的景区班车的运行情况，今天早上自己拖着行李但非常顺利地乘坐班车来到了喀纳斯村。我们事先预订的房间，房东说他会到车站接我们，等我们下车后看到房东骑了一辆自行车来接我们，搞得我们真正哭笑不得。没办法，只好又拖着行李跟着房东来到我们的住处。好在车站到我们住的地方也不是很远，走路十几分钟吧。\n" +
                "喀纳斯村隶属于新疆阿勒泰地区布尔津县。喀纳斯村分老村和新村，我们住在老村。老村沿一条公路两边分布，其房屋的建筑风格与北哈巴村一样，而且也是一个以图瓦人为主的村落。\n" +
                "俯瞰喀纳斯老村。\n" +
                "我们下榻的“蝴蝶山庄”是联排木屋。房间不大（堆放行李有些困难），但性价比很高（全靠我们同行的伙伴攻略做得好）。\n" +
                "安顿好住宿之后，我们来到景区游客中心。喀纳斯村所有景区观光车都在这里始发。\n" +
                "观鱼台\n" +
                "观鱼台是我们这次去喀纳斯村游玩的第一个景点。之所以把观鱼台作为第一个景点，是想趁体力还好用来登山。我们从北哈巴村坐车过来时在车上就远远看到了山顶上的观鱼台，这次乘坐景区观光车来到观鱼台登山道附近。接下来要靠双脚登山，登山道有一千多级台阶，我们用了40多分钟登上观鱼台。\n" +
                "观鱼台始建于1987年，2009年9月改建竣工，改建后才命名为观鱼台。观鱼台建于海拔2030米的哈拉开特(蒙古语意为\"骆驼峰\"）山顶上，与湖面的垂直落差达600多米，是观看喀纳斯湖全貌的最佳位置。当地人有“不登观鱼台，不足以领略喀纳斯湖美景”的说法。\n" +
                "观鱼台登山起点处。\n" +
                "登山一半时看到的喀纳斯湖。喀纳斯湖水的颜色是那种很奇特的牛奶绿色，这也是喀纳斯湖水的标志色。之后我们在喀纳斯看到的所有湖水、河水的颜色几乎都是这个颜色。\n" +
                "在登山途中，不时可以看到山体斜坡上有不少这样裸露在地表的岩石。据考察，这些斜片状岩石形成于6亿年前，最早是在水下，所以这些岩石是地质变迁的见证。景区把这些岩石称作“6亿年前的地质史书”。\n" +
                "攀登了一千多级台阶后，我们登上了山顶。\n" +
                "立于山顶最高处的观鱼台造型非常独特。其结构为两台一亭，底台略小，中台大于底台和顶亭，可容纳百余人同时观景，顶部为半圆球状，有四个对称的类似于翅膀的奇异造型，为湖怪的尾巴和雄鹰的翅膀的寓意。\n" +
                "观鱼台底层中央有一汉白玉石碑，上书《兴建观鱼台记》。\n" +
                "登上观鱼台，峡谷中的喀纳斯湖尽收眼底。\n" +
                "环顾四周，群山逶迤，草木葳蕤，江山如此多娇。\n" +
                "当我们快要下山时，满天的乌云渐渐散开，蓝天显露出来。远处的雪上也终于向我们展现出雄伟的身姿。\n" +
                "为了避免人多的时候拥挤，上山与下山的道路是分开的。云顶阁位于下山的路上，是一个综合性的服务建筑。\n" +
                "漂亮的小鸟等着我们喂它吃的。\n" +
                "下到喀纳斯湖边，回头再看一眼山顶上的观鱼台。\n" +
                "喀纳斯湖\n" +
                "下山之后，我们回到喀纳斯村的景区车总站，再换乘另一辆到喀纳斯湖码头的景区车来到喀纳斯湖游船码头。\n" +
                "\"喀纳斯\"是蒙古语，意为\"美丽而神秘\"。喀纳斯湖水来自周边雪山的冰川融水和当地降水，湖面海拔1374米，面积46平方公里，湖泊最深处188.5米，是中国最深的冰碛堰塞湖，属于高山内陆淡水湖。\n" +
                "喀纳斯湖与蓝天、白云、冰峰、雪岭、森林、草甸、河流交相辉映，湖光山色融为一体，既具有北国风光之雄浑，又具有江南山水之隽秀。喀纳斯湖是中国最美的湖泊之一，被誉为\"人间仙境、神的花园\"。加之喀纳斯“湖怪”的传说，更是为喀纳斯湖蒙上了一块神秘的面纱。\n" +
                "位于喀纳斯湖东头的游船码头。游船寻幽探密线路：游船码头——三道湾(上岸观景)——返程，往返时间约1小时。船票120元/人。原先我们以为乘坐游船可能会看到喀纳斯“湖怪”，当了解到要想看“湖怪”至少要到六道湾才有可能，而游船只到三道湾，所以立刻放弃了乘坐游船的念头。\n" +
                "在游船码头上，有一个围起来的喀纳斯湖大红鱼模型（供游人有偿拍照用）。据说以前在喀纳斯湖看到的“湖怪”就是这种大红鱼跃出水面的幻觉。\n" +
                "在游船码头边上，有一条湖边栈道可以观赏湖景。\n" +
                "这里是喀纳斯湖的出口，流出的湖水汇集成美丽的喀纳斯河。\n" +
                "深秋季节的喀纳斯湖是最美的，乳绿色的湖水倒映着岸边的彩林，美轮美奂。\n" +
                "喀纳斯的生态环境极其良好，无论在水里还是在山林里，各种野生动物随时可见。\n" +
                "我们游玩观鱼台和喀纳斯湖之后，已经暮色暗沉。看看时间不早，我们回到喀纳斯老村住处吃晚饭。喀纳斯作为一个著名旅游景区，这里的饭菜质量不敢恭维，但价格却有些离谱。\n" +
                "第二天\n" +
                "今天继续在喀纳斯游玩，游玩的方式是沿着喀纳斯河，依次游玩喀纳斯河的神仙湾、月亮湾、卧龙湾。\n" +
                "神仙湾\n" +
                "从喀纳斯老村出发，步行到景区游客中心，在景区车总站乘坐到卧龙湾方向的景区车在神仙湾站下车，然后沿着喀纳斯河徒步游玩。\n" +
                "神仙湾是喀纳斯河在山涧低缓处形成的一片河滩，这里的河水将森林和草地切分成一块块似连似断的小岛，秋天的彩林加上喀纳斯河自带的仙韵，使这里犹如人间仙境，人称神仙湾。神仙湾还有一大奇观，就是这里的河水会随着季节的变换而改变颜色，被称之为“变色湖”。\n" +
                "由于受下游崩塌堆积的堵塞，神仙湾的河水流速变慢，河面变宽，河岸地带形成了大片的沼泽和草甸。";
        List<String> list = getIkAnalyzeSearchTerms(s, "ik_smart");
        Map<String, Integer> map = new HashMap<>();
        for (String p : list) {
            if (p.length() < 2) continue;
            if (map.containsKey(p) == false) {
                map.put(p, 1);
            } else {
                map.replace(p, map.get(p) + 1);
            }
        }
        map = map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue(), (x1, x2) -> x2, LinkedHashMap::new));
        map.forEach((key, value) -> System.out.println(key + ":" + value));
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

    @Test
    public void testSearch() {
        Page<Diary> list = elasticsearchService.searchDiscussPost(0, "添加", 0, 10);

        for (Diary diary : list) {
            System.out.println(diary);
        }

    }

    @Test
    public void testSave() {
        List<Diary> list = diaryService.findDiarys(0, 0, Integer.MAX_VALUE, 0);
        for (Diary diary : list)
            elasticsearchService.saveDiary(diary);
    }

}
