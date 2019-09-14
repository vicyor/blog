package com.vicyor.blog.apps.blog.repository;


import com.vicyor.blog.apps.blog.domain.EsBlog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EsBlogRepositoryTest {
    @Autowired
    private EsBlogRepository repository;

    public void before() {

        for (int i = 0; i < 0; i++) {
            repository.save(new EsBlog("静夜思", "李白", "床前明月光，疑是地上霜" +
                    "举头望明月，低头思故乡", new Date(), new Date(), 2, "images/text02.jpg", "千古名诗"));

            repository.save(new EsBlog("行路难", "李白", "金樽清酒斗十千，玉盘珍羞直万钱。(羞 通：馐；直 通：值)" +
                    "停杯投箸不能食，拔剑四顾心茫然。" +
                    "欲渡黄河冰塞川，将登太行雪满山。(雪满山 一作：雪暗天)" +
                    "闲来垂钓碧溪上，忽复乘舟梦日边。(碧 一作：坐)" +
                    "行路难！行路难！多歧路，今安在？" +
                    "长风破浪会有时，直挂云帆济沧海。", new Date(), new Date(), 4, "images/text02.jpg", "千古名诗"));
        }
    }

    @Before
    public void before1() {
        repository.deleteAll();
        repository.save(new EsBlog("Elastic Search", "elastic search", "###### 1.全文索引\n" +
                "```\n" +
                "建立文本库(所有的数据) 例如所有汉字\n" +
                "建立索引               例如拼音\n" +
                "开始搜索\n" +
                "结果过滤\n" +
                "```\n" +
                "###### 2.正排索引和倒排索引\n" +
                "```\n" +
                "1.正排索引\n" +
                "  文档ID -->document\n" +
                "2.倒排索引\n" +
                "  文档的内容的单词--->文档ID\n" +
                "  单词对应的是一个id列表\n" +
                "```\n" +
                "###### 2. ElasticSearch\n" +
                "```\n" +
                "分布式多用户,全文搜索引擎.基于restfulweb接口.\n" +
                "基本概念\n" +
                "数据以json格式的文档进行存储\n" +
                "elasticsearch     mysql\n" +
                "index             schema\n" +
                "type              table\n" +
                "document          field\n" +
                "mapping           域,domain\n" +
                "这个比喻是不恰当的\n" +
                "type对应table的比喻,因为在一个index下的同名document类型是相同的\n" +
                "所以无法实现不同type的同名document意义不同.\n" +
                "所以推荐一个index,一个type\n" +
                "Query DSL(json格式的查询语句)         sql\n" +
                "```\n" +
                "###### 3.倒排索引\n" +
                "```\n" +
                "1.Elasticsearch 使用一种称为倒排索引的结构,适用于快速的全文搜索.\n" +
                "2.倒排序索引由文档不重复的列表构成,对于每个词,有包含它的document列表\n" +
                "3.使用倒排索引可以快速的找到要搜索内容的document.\n" +
                "4.标准化规则\n" +
                "  使用标准化规则,会对每个单词进行相对应的处理,即某些相关联的词,例如student和Stduent 大小写不同,可以合并.\n" +
                "```\n" +
                "###### 4.倒排索引\n" +
                "```\n" +
                "1.分词器: 从一串文本中切分出来一个一个的词条,对每个词条进行标准化\n" +
                "2.分词器分成三部分\n" +
                "      character filter  分词预处理 处理特殊字符\n" +
                "      tokenizer         分词\n" +
                "      token filter      分词后标准化\n" +
                "3.常见分词器\n" +
                "  standard 分词器:\n" +
                "  simple 分词器\n" +
                "  Whitespace 分词器\n" +
                "  language 分词器  \n" +
                "4.es提供分词测试的api\n" +
                "  POST _analyze\n" +
                "  {\n" +
                "      \"analyzer\":\"ik_max_word\",\n" +
                "      \"text\":\"我真的好帅\"\n" +
                "  }\n" +
                "```\n" +
                "###### 5.通过rest api 操作elasticsearch\n" +
                "```\n" +
                "1.可以通过curl\n" +
                "2.这里通过kibana 工具来简化操作\n" +
                "  1.创建index\n" +
                "    PUT /lib/\n" +
                "    {\n" +
                "        \"settings\":{\n" +
                "            \"index\":{\n" +
                "                \"number_of_shards\":3,\n" +
                "                \"number_of_replicas\":0 //副本数 \n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "   2.获取索引信息\n" +
                "    GET /lib/\n" +
                "    GET /lib/_settings\n" +
                "   3.添加document\n" +
                "     指定id\n" +
                "     PUT /lib/user/1\n" +
                "     {\n" +
                "         \"first_name\":\"Jane\",\n" +
                "         \"last_name\":\"smith\",\n" +
                "         \"age\":32,\n" +
                "         \"about\":\"I like to collect rock albums\",\n" +
                "         \"interests\":[\"music\"]\n" +
                "     }\n" +
                "     随机生成id\n" +
                "     POST /lib/user\n" +
                "     {\n" +
                "         \"first_name\":\"Jane\",\n" +
                "         \"last_name\":\"smith\",\n" +
                "         \"age\":32,\n" +
                "         \"about\":\"I like to collect rock albums\",\n" +
                "         \"interests\":[\"music\"]\n" +
                "     }\n" +
                "     覆盖\n" +
                "     PUT /lib/user/1\n" +
                "     {\n" +
                "         \n" +
                "     }\n" +
                "     更新\n" +
                "     POST /lib/user/1/_update\n" +
                "     {\n" +
                "         \"first_name\":\"vicyor\"\n" +
                "     }\n" +
                "    4.通过id获取字段\n" +
                "      GET /lib/user/1\n" +
                "      GET /lib/user/1?_source=age,about\n" +
                "    5.删除document\n" +
                "      DELETE /lib/user/1\n" +
                "      DELETE /lib\n" +
                "    6.mget get的批处理\n" +
                "      GET /_mget\n" +
                "      {\n" +
                "          \"docs\":[\n" +
                "          {\n" +
                "              \"_index\":\"lib\",\n" +
                "              \"_type\":\"user\",\n" +
                "              \"_id\":1,\n" +
                "              \"_source\":\"interests\"\n" +
                "          },\n" +
                "          {\n" +
                "              \"_index\":\"lib\",\n" +
                "              \"_type\":\"user\",\n" +
                "              \"_id\":2,\n" +
                "              \"_source\":[\"age\",\"interests\"]\n" +
                "          }\n" +
                "          ]\n" +
                "      }\n" +
                "      GET /lib/user/_mget\n" +
                "      {\n" +
                "          \"docs\":[{\n" +
                "                \"_id\":1\n" +
                "          }]\n" +
                "      }\n" +
                "      GET /lib/user/_mget\n" +
                "      {\n" +
                "          \"ids\":[\"1\",\"2\"]\n" +
                "      }\n" +
                "    7.使用Bulk API\n" +
                "      bulk的格式\n" +
                "      {action:{metadata}}\n" +
                "      {requestbody}\n" +
                "        action---->行为  create 创建 update 更新 index 替换 delete删除\n" +
                "        metada---->指定index,type,id\n" +
                "      例子:\n" +
                "            {\"delete\":{\"_index\":\"lib\",\"_type\":\"user\",\"_id\":1}}\n" +
                "        批量添加\n" +
                "            POST /lib/book/_bulk\n" +
                "            {\"index\":{\"_id\":1}}\n" +
                "            {\"title\":\"java\",\"price\":55}\n" +
                "            {\"index\":{\"_id\":2}}\n" +
                "            {\"title\":\"python\",\"price\":55}        \n" +
                "            {\"index\":{\"_id\":3}}\n" +
                "            {\"title\":\"linux\",\"price\":55}\n" +
                "    bulk会将所要处理的数据载入内存中,所以数据量是有限制的,最佳的数据量不是一个确定的数值.取决于硬件,文档大小复杂性,索引以及搜索的负载.\n" +
                "    一般建议是1000-5000个文档,大小建议是5-15mb,默认不能超过100M,\n" +
                "    在配置文件elasticsearch.yml中配置\n" +
                "```\n" +
                "###### 6.elastic search 乐观锁 版本号.\n" +
                "```\n" +
                "对document的操作是基于版本号的.\n" +
                "PUT /lib/user/4?version=2 ----->version++\n" +
                "{\n" +
                "    这是body\n" +
                "}\n" +
                "PUT /lib/user/4?version=2\n" +
                "{\n" +
                "    这是body\n" +
                "}\n" +
                "elasticsearch采用了乐观锁机制,当使用数据库与elasticsearch进行整合时候,需要将数据库的乐观锁机制(版本号)与el的乐观锁机制保持一致\n" +
                "例如oracle是基于时间戳的版本号.\n" +
                "所以使用version_type=external,这种方式是version>esversion就成功\n" +
                "PUT /lib/user/4?version=时间戳&version_type=external\n" +
                "\n" +
                "```\n" +
                "###### 7.elastic search mapping \n" +
                "```\n" +
                "mapping是指数据类型\n" +
                "GET /myindex/artical/_mapping\n" +
                "el的数据类型\n" +
                "    1.text 文本\n" +
                "    2.keyword  索引结构化的数据字段，通常是作为过滤关键字的.比如email地址,标签,主机名\n" +
                "      text会进行分词,keyword不会进行分词\n" +
                "    3.数字类型\n" +
                "     integer,float\n" +
                "    4.array\n" +
                "      json的array.\n" +
                "    5.boolean\n" +
                "    6.date 日期\n" +
                "     在mappings中配置\n" +
                "     date_detection:false可以关闭日期识别\n" +
                "    7.binary 二进制.\n" +
                "    8.object\n" +
                "    9.geo\n" +
                "对mapping为text的document可以使用模糊查询\n" +
                "  GET /index/type/_search?q=content:html\n" +
                "对mapping为data的只能使用精确查询\n" +
                "  GET /index/type/_search?q=birthday:1998-04-01\n" +
                "不能分词的mapping类型只能使用精确匹配  \n" +
                "mapping的属性\n" +
                "    store ---->数据是否存储\n" +
                "    index----->数据是否分词\n" +
                "    analyzer---->建立倒排索引分词器,默认为standard analyzer\n" +
                "    ignore-above----> 当文本字符超过设定值时,该text就会被忽略.\n" +
                "    search_analyzer---->搜索时分词器\n" +
                "对于对象会通过.的方式进行转换\n" +
                "    {\n" +
                "        \"person\":{\n" +
                "            \"name\":\"vc\",\n" +
                "            \"age\":20\n" +
                "        }\n" +
                "    }\n" +
                "会变成\n" +
                "   {\n" +
                "       \"person.name\":\"vc\",\n" +
                "       \"person.age\":20\n" +
                "   }\n" +
                "对象数组\n" +
                "   {\n" +
                "       \"persons\":[{\n" +
                "            \"name\":\"vc\",\n" +
                "            \"age\":20\n" +
                "       },{\n" +
                "           \"name\":\"abc\",\n" +
                "           \"age\":\"18\"\n" +
                "       }]\n" +
                "   }\n" +
                "会变成\n" +
                "   {\n" +
                "       \"persons.name\":[\"vc\",\"abc\"],\n" +
                "       \"persions.age\":[18,20]\n" +
                "   }\n" +
                "自定义mapping\n" +
                "PUT /lib\n" +
                "{\n" +
                "    \"settings\":{\n" +
                "        \"number_of_shards\":3,  //分片数,最好与集群节点数量相同\n" +
                "        \"number_of_replicas\":0  //副本数\n" +
                "    },\n" +
                "    \"mappings\":{\n" +
                "        \"books\":{\n" +
                "            \"properties\":{\n" +
                "                \"title\":{\"type\":\"text\"},\n" +
                "                \"name\":{\"type\":\"text\",\"analyzer\":\"standard\"},\n" +
                "                \"publish_date\":{\"type\":\"date\",\"index\":false}, 不创建倒排索引\n" +
                "                \"price\":{\"type\":\"double\"},\n" +
                "                \"number\":{\"type\":\"integer\"}\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "在mapping中如果type设置dynamic可以约束是否可以新添加字段\n" +
                "dynamic -->true 新增的字段动态映射\n" +
                "dynamic -->false\n" +
                "新增的字段忽略\n" +
                "dynamic -->strict\n" +
                "新增字段会报错\n" +
                "```\n" +
                "###### 7.term和match区别\n" +
                "```\n" +
                "就是会对查询条件分词\n" +
                "term--->name:\"I am\"   会查 倒排索引 \"I am\"\n" +
                "match --->name:\"I am\" 会查 倒排索引 \"I\"  \"am\"\n" +
                "```\n" +
                "###### 8.查询 我觉得查索引是通过正则表达式去找到倒排索引的索引\n" +
                "```\n" +
                "1.分词查询,会将给的条件进行分词\n" +
                "  GET /lib/type/_search?q=name:word&sort=age\n" +
                "  或\n" +
                "  GET /lib/type/_search\n" +
                "  {\n" +
                "      \"query\":{\n" +
                "          \"match\":{\n" +
                "             //or分词查询 \"name\":\"word1,word2,word3\"\n" +
                "          }\n" +
                "      }\n" +
                "  }\n" +
                "  match--->or\n" +
                "  match_all--->查詢所有\n" +
                "  match_phrase--->倒排索引必须连在一起\n" +
                "  match_phrase_prefix--->前缀查询\n" +
                "2.term,terms查询 不分词查询\n" +
                "  GET /lib/type/_search/\n" +
                "  {\n" +
                "    \"query\":{\n" +
                "        \"terms\":{\n" +
                "            \"title\":[\"java\",\"elasticsearch\"]\n" +
                "        }\n" +
                "    }   \n" +
                "  }\n" +
                "3.多字段查询,例如查询title,content中都有某个分词的document\n" +
                "  GET /lib/type/_search/\n" +
                "  {\n" +
                "      \"query\":{\n" +
                "          \"multi_match\":{\n" +
                "              \"query\":\"word\",\n" +
                "              \"fields\":[\"title\",\"content\"]\n" +
                "          }\n" +
                "      }\n" +
                "  }\n" +
                "4.排序 \n" +
                "  GET /lib/type/_search\n" +
                "  {\n" +
                "      \"query\":{\n" +
                "          \"match_all\":{}\n" +
                "      },\n" +
                "      \"sort\":[\n" +
                "          {\n" +
                "            \"age\":{\n" +
                "                \"order\":\"desc\"   #asc时升序\n" +
                "            }\n" +
                "          }\n" +
                "      ]\n" +
                "  }\n" +
                "5.范围查询 range\n" +
                "  GET /lib/user/_search\n" +
                "  {\n" +
                "      \"query\":{\n" +
                "          \"range\":{\n" +
                "              \"age\":{\n" +
                "                  \"from\":20,\n" +
                "                  \"to\":25,\n" +
                "                  \"include_lower\":true,\n" +
                "                  \"include_upper\":false\n" +
                "              }\n" +
                "          }\n" +
                "      }\n" +
                "  }\n" +
                "6.通配符查询 wildcard(对索引使用通配符匹配)\n" +
                "  GET /lib/type/_search\n" +
                "  {\n" +
                "      \"query\":{\n" +
                "          \"wildcard\":{\n" +
                "              \"name\":\"li?i\"\n" +
                "          }\n" +
                "      }\n" +
                "  }\n" +
                "   对倒排索引的通配符进行模糊匹配.\n" +
                "7.模糊查询  fuzzy(对索引使用模糊查询)\n" +
                "  value: 查询的关键字\n" +
                "  boost: 查询的权值 默认是1.0\n" +
                "  GET /lib/type/_search\n" +
                "  {\n" +
                "      \"query\":{\n" +
                "          \"fuzzy\":{\n" +
                "              \"content\":\"yaowei\"  //会匹配到yaokewei的索引\n" +
                "          }\n" +
                "      }\n" +
                "  }\n" +
                "8.高亮显示\n" +
                "    GET /lib2/book/_search\n" +
                "    {\n" +
                "      \"query\":{\n" +
                "        \"match\":{\n" +
                "          \"title\":\"java,elasticsearch\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"highlight\": {\n" +
                "        \"fields\": {\n" +
                "          \"title\": {}\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "9.定义中文分词器查询\n" +
                "  PUT /lib3\n" +
                "    {\n" +
                "      \"settings\": {\n" +
                "        \"number_of_shards\": 3,\n" +
                "        \"number_of_replicas\": 1\n" +
                "      },\n" +
                "      \"mappings\": {\n" +
                "        \"user\":{\n" +
                "          \"properties\":{\n" +
                "            \"name\":{\n" +
                "              \"type\":\"text\",\n" +
                "              \"analyzer\":\"ik_max_word\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "    ik_max_word ---->粒度大\n" +
                "    ik_min_word ---->粒度小\n" +
                "```\n" +
                "###### 9.对于大写英文字母字段,使用term查询\n" +
                "```\n" +
                "例如 IDdocument是模板是  ID0000001 ....\n" +
                "进行term查询是查不到的,因为没有index为Id0000001的index\n" +
                "解决方式 term查 0000001或id或id0000001\n" +
                "或document的mapping设置插入时index:false,不分词\n" +
                "\n" +
                "```\n" +
                "###### 9.Filter查询\n" +
                "```\n" +
                "filter不计算相关性,同时拥有缓存,因此速度比query快\n" +
                "```\n" +
                "###### 10.bool过滤查询\n" +
                "```\n" +
                "bool过滤查询可以实现组合过滤查询\n" +
                "格式:\n" +
                "{\"bool\":{\"must\":[],\"should\":[],\"must_not\":[]}}\n" +
                "must:必须满足的条件\n" +
                "should:可以满足也可以不满足的条件\n" +
                "must_not:不需要满足的条件\n" +
                "GET /lib4/user/_search\n" +
                "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"should\": [{\n" +
                "        \"match\": {\n" +
                "          \"name\": \"姚克威\"\n" +
                "          \n" +
                "        }\n" +
                "        \n" +
                "      },\n" +
                "      {\n" +
                "        \"term\": {\n" +
                "          \"introduction\": \"帅\"\n" +
                "        }\n" +
                "      }]\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "```\n" +
                "###### 11.范围过滤\n" +
                "```\n" +
                "gt,lt,gte,lte\n" +
                "GET /lib4/user/_search\n" +
                "{\n" +
                "  \"query\": {\n" +
                "    \"range\": {\n" +
                "      \"name\": {\n" +
                "        \"gte\": \"姚\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "```\n" +
                "###### 12.聚合查询\n" +
                "```\n" +
                "sum,max,min,avg,cardinality(指count(distinct)),terms(分组)\n" +
                "聚合查询一般不能用在text上,可以用keyword,long等其它数据类型\n" +
                "GET /lib4/items/_search\n" +
                "{\n" +
                "    \"aggs\":{  //aggs 指聚合\n" +
                "        \"size\":0,\n" +
                "        \"price_of_sum\":{  //\"price_of_sum\"为自定义返回结果\n" +
                "            \"sum\":{\n" +
                "                \"field\":\"price\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "对查询后的结果聚合\n" +
                "GET /lib4/items/_search\n" +
                "{\n" +
                "    \"size\": 0,\n" +
                "    \"query\":{\n" +
                "        \"match\":{\n" +
                "            \"name\":\"鸡蛋,萝卜,青菜\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"aggs\":{\n" +
                "        \"age_of_group\":{\n" +
                "            \"terms\":{\n" +
                "                \"field\":\"age\",\n" +
                "                \"order\":{\n" +
                "                    \"age_of_avg\":\"desc\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        //分组和聚合\n" +
                "        \"aggs\":{\n" +
                "            \"age_of_avg\":{\n" +
                "                \"avg\":{\n" +
                "                    \"field\":\"age\"\n" +
                "                }\n" +
                "            },\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "###### 13.ElasticSearch原理\n" +
                "```\n" +
                "1.扩容机制\n" +
                "  垂直扩容  加配置\n" +
                "  水平扩容  加机器  增加或减少节点时会自动均衡\n" +
                "2.master节点 \n" +
                "  主节点的主要职责是和集群操作相关的内容,如创建或删除索引,将分片分配给节点.\n" +
                "  es的数据会存在内存中,当达到某些阈值时候或者重启后会序列化\n" +
                "3.分片和副本\n" +
                "  1.index包含多个shard\n" +
                "  2.每个shard都是luncene实例,有完整的建立索引和处理请求的能力\n" +
                "  3.增减节点时,shard会自动在nodes中负载均衡\n" +
                "  4.primary shard唯一,不可能存在多个primary shard\n" +
                "  5.replica shard是primary shard的副本,负责容错,以及承担读请求负载\n" +
                "  6.primary shard数量在创建索引时候就固定了,replica shard数量可以随时修改.\n" +
                "  shard的总数量最好不要超过node的数量\n" +
                "4.容错性\n" +
                "  对于n个node的服务器,最多能挂 n/2台机器\n" +
                "  为啥,因为假设每个shade最少存在一个副本,那么副本节点都挂了,保留原来的节点.\n" +
                "5.集群状态\n" +
                "  red ---->有primary shard所在node宕机了\n" +
                "  yellow ----> 有 rep shard 所在node宕机了\n" +
                "  green  ----> health\n" +
                "6.容错机制 master宕机\n" +
                "  1.重新选举另外一个节点作为master \n" +
                "  2.master会把原来的primary shard的副本作为primary shard\n" +
                "    集群状态转为yellow\n" +
                "  3.宕机的服务器重启,master会将那些缺少副本的shard继续同步到该服务器上(注意脑裂)\n" +
                "```\n" +
                "###### 14.文档元数据\n" +
                "```\n" +
                "_index     index 一般index下只有一个type类型,索引不能以_开头,不能有','号\n" +
                "_type      type  type下可以添加和删除新的document类型\n" +
                "_id        id    文档唯一标识,区分不同的文档\n" +
                "_version   version\n" +
                "_source    http request body的内容,get时候可以指定获取的字段\n" +
                "```\n" +
                "###### 15.文档更新,删除解析\n" +
                "```\n" +
                "http method --->put是创建,post时更新\n" +
                "全部替换\n" +
                "PUT /lib/user/4\n" +
                "{\n" +
                "    \"first_name\":\"Jane\",\n" +
                "    \"age\":18\n" +
                "}\n" +
                "PUT 是新建一个段放document,删除原来的document(在.del文件中存储删除id)\n" +
                "部分替换\n" +
                "POST /lib/user/4\n" +
                "{\n" +
                "    \"age\":20\n" +
                "}\n" +
                "POST 方式同PUT方式更新\n" +
                "DELETE /lib/user/4\n" +
                "在.del文件中存储删除id  \n" +
                "```\n" +
                "###### 16.重试机制\n" +
                "```\n" +
                "POST /index/type/id/_update?retry_on_conflict=3&version=5\n" +
                "```\n" +
                "###### 17.数据路由原理\n" +
                "```\n" +
                "1.index由多个shard组成,在index下添加或删除document,es要决定将文档存储在哪个分片上,这个过程就是数据路由\n" +
                "2.路由算法: routing默认是文档的id \n" +
                "    shard=hash(routing)% number_of_primary_shards\n" +
                "```\n" +
                "###### 18.写一致性原理和quorum机制\n" +
                "```\n" +
                "PUT /index/type/id?consistency=one  \n" +
                "consistency --->primary  primary shard是活跃的就可以\n" +
                "            --->all    所有shard是活跃\n" +
                "            --->quorum   (n+1)/2活跃是可用\n" +
                "```\n" +
                "###### 19.协调节点\n" +
                "```\n" +
                "每一个node处理写操作或读操作,发现shard不在自己的机器上时,转发请求到shard所在的机器,并获取响应,返回即可.\n" +
                "此时shade称为协调节点.\n" +
                "```\n" +
                "###### 20.查询结果超时设置\n" +
                "```\n" +
                "1.可以设置timeout,设置timeout后只返回timeout内查询的结果\n" +
                "```\n" +
                "###### 21.分页查询的deep paging\n" +
                "```\n" +
                "GET /index/type/_search?from=page&size=pagesize\n" +
                "协调节点是是获取所有该index的shade数据,然后根据相关度排序,取page和pagesize的数据\n" +
                "```\n" +
                "###### 22.copyto使用\n" +
                "```\n" +
                "例如文章有标题,内容,作者等字段.copy to是将标题,内容,作者全接起来到copyto的字段\n" +
                "    PUT /myindex/article/_mapping\n" +
                "    {\n" +
                "        \"properties\":{\n" +
                "            \"post_date\":{\n" +
                "                \"type\": \"date\"\n" +
                "            },\n" +
                "            \"title\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"copy_to\":\"allin\"\n" +
                "            },\n" +
                "            \"content\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"copy_to\":\"allin\"\n" +
                "            },\n" +
                "            \"author_id\":{\n" +
                "                \"type\":\"integer\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "```\n" +
                "###### 23.字符串排序问题(倒排索引不能排序)\n" +
                "```\n" +
                "1.由于字符串被分词了,所以不能被排序\n" +
                "解决方式:对字段索引二次,一次用于分词,一次不分词\n" +
                "PUT /lib3{\n" +
                "    \"mappings\":{\n" +
                "        \"user\":{\n" +
                "            \"properties\":{\n" +
                "                \"name\":{\"type\":\"text\"},\n" +
                "                \"address\":{\"type\":text},\n" +
                "                \"interests\":{\n" +
                "                    \"type\":\"text\",\n" +
                "                    \"fields\":{\n" +
                "                        \"raw\":{\n" +
                "                            \"type\":\"keyword\"\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"fielddata\":true\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "//查询 排序\n" +
                "GET /lib3/user/_search\n" +
                "{\n" +
                "    \"sort\":[{\n" +
                "        \"interests.raw\":{\"order\":\"asc\"}        \n" +
                "    }]\n" +
                "}\n" +
                "```\n" +
                "###### 24.计算相关度分数\n" +
                "```\n" +
                "算法 TF/IDF算法 (Term Frequency/Inverse Document Frequency)\n" +
                "TF---->document中出现的频数\n" +
                "IDF ---->倒排索引中出现的频数\n" +
                "TF/IDF 表示相关度\n" +
                "TF/IDF 如果相同,看field长度，长的，相关度低\n" +
                "```\n" +
                "###### 25.doc values 解析\n" +
                "```\n" +
                "DocValues Lucene在构建倒排所以时候,会同时建立正排索引\n" +
                "对排序,分组,和一些聚合操作能够大大提升性能.\n" +
                "注意:默认对不分词的字段是开启的,对分词字段无效(需要将fielddata设置为true)\n" +
                "```\n" +
                "###### 26.scroll 滚动查询\n" +
                "```\n" +
                "像redis的scan\n" +
                "//执行一分钟\n" +
                "GET /index/type/_search?scroll=1m\n" +
                "{\n" +
                "    \"query\":{\n" +
                "        \"match_all\":{}\n" +
                "    },\n" +
                "    \"sort\":[\"_doc\"],\n" +
                "    \"size\":3\n" +
                "}\n" +
                "//scroll会建立快照,记录下已经读的位置\n" +
                "//第二次读\n" +
                "GET /_search/scroll\n" +
                "{\n" +
                "    \"scroll\":\"1m\",\n" +
                "    \"scroll_id\":\"第一次查询返回的_scroll_id\"\n" +
                "}\n" +
                "```\n" +
                "###### 27.修改字段类型重建索引\n" +
                "```\n" +
                "PUT /index/type/1\n" +
                "{\n" +
                "    \"content\":\"1880-03-11\"  --->日期类型\n" +
                "}\n" +
                "//修改为text类型--->会报错\n" +
                "错误操作\n" +
                "PUT /index/_mapping/type\n" +
                "{\n" +
                "    \"properties\":{\n" +
                "        \"content\":{\n" +
                "            \"tyep\":\"text\"\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "字段类型属于索引 \n" +
                "当字段的类型被修改后,只是新建一个新的索引,会将旧的索引中的数据导入到新的索引中\n" +
                "但是新建一个索引,应用程序使用的还是原来的索引,那么需要重新启动应用程序.为了不重新启动应用,我们需要使用别名的方式\n" +
                "正确操作\n" +
                "1.修改原索引\n" +
                "PUT /index/_alias/index_tmp\n" +
                "2.创建新索引\n" +
                "PUT /index\n" +
                "{\n" +
                "    \"mappings\":{\n" +
                "        \"type\":{\n" +
                "            \"properties\":{\n" +
                "                \"content\":{\"type\":\"text\"}\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "3.转移数据\n" +
                "//滚动获取数据\n" +
                "GET /index_tmp/type/_search?scroll=1m\n" +
                "{\n" +
                "    \"sort\":[\"_doc\"],\n" +
                "}\n" +
                "//bulk插入\n" +
                "POST /_bulk\n" +
                "{\n" +
                "    \"index\":{\"_index\":\"index\",\"_type\":\"type\"}\n" +
                "    {scroll获取的数据}\n" +
                "}\n" +
                "```\n" +
                "###### 28.索引不可变的原因\n" +
                "```\n" +
                "倒排索引包括\n" +
                "    文档列表,文档数量,词条在每个文档张出现的次数,出现的位置,每个文档的长度,所有文档的平均疮毒\n" +
                "索引 index(对应schema)不可变优点:\n" +
                "      1.不需要锁,提供并发性能\n" +
                "      2.可以一直保存在缓存中\n" +
                "      3.节省cpu和io开销\n" +
                "```", new Date(), new Date(), 2, "images/text02.jpg", "分布式搜索引擎"));

    }

    @Test
    public void testFindDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining() {
        String keyword = "静夜思";

        Page<EsBlog> page = repository
                .findDistinctEsBlogByContentMatchesOrTitleMatchesOrTagMatches(keyword, keyword, keyword, PageRequest.of(0, 10));
        System.out.println(page.getTotalElements());
    }

    @Test
    public void testFindDistinctEsBlog() {

        List<String> fields = new ArrayList<>();
        fields.add("udate");
        Iterable<EsBlog> esBlogs = repository.findAll(new Sort(Sort.Direction.DESC, fields));
        esBlogs.forEach(esBlog -> System.out.println(esBlog));
    }
}
