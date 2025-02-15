package com.java2nb.novel.core.listener;


import com.java2nb.novel.core.crawl.RuleBean;
import com.java2nb.novel.utils.CrawlHttpClient;
import org.apache.el.util.ReflectionUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class StarterListenerTest {
    @InjectMocks
    private StarterListener listener;

    @Mock
    private CrawlHttpClient crawlHttpClient;

    @Before
    public void setup()  {
      Mockito.when(crawlHttpClient.get(Mockito.anyString()))
              .thenReturn("<!DOCTYPE html>\n" +
                      "<html>\n" +
                      "<head>\n" +
                      "    <meta charset=\"utf-8\">\n" +
                      "    <title>搜索小说_</title>\n" +
                      "    <meta name=\"keywords\" content=\"长乐歌,三戒大师,梦书中文\" />\n" +
                      "    <meta name=\"description\" content=\"梦书中文提供起点大神作家三戒大师最新作品长乐歌最新章节免费阅读.\" />\n" +
                      "    <meta name=\"MobileOptimized\" content=\"240\" />\n" +
                      "    <meta name=\"applicable-device\" content=\"mobile\" />\n" +
                      "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no\" />\n" +
                      "    <meta name=\"format-detection\" content=\"telephone=no\" />\n" +
                      "    <meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />\n" +
                      "    <meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black-translucent\" />\n" +
                      "    \n" +
                      "    <script language=\"javascript\" type=\"text/javascript\" src=\"/js/zepto.min.js\"></script>\n" +
                      "    <script language=\"javascript\" type=\"text/javascript\" src=\"/js/common.js\"></script>\n" +
                      "    <script language=\"javascript\" type=\"text/javascript\" src=\"/js/lazyload.js\"></script>\n" +
                      "    <script src=\"/js/mcmssc.js\"></script>\n" +
                      "    \n" +
                      "    <link rel=\"stylesheet\" href=\"/layui/css/layui.css\" />\n" +
                      "    <link rel=\"stylesheet\" href=\"/css/reset.css\" />\n" +
                      "    \n" +
                      "    <link rel=\"stylesheet\" href=\"/css/rank.css\" />\n" +
                      "\n" +
                      "</head>\n" +
                      "<body>\n" +
                      "\n" +
                      "<div class=\"main transition\">\n" +
                      "\n" +
                      "<header class=\"channelHeader\">\n" +
                      "    <a class=\"iconback\" href=\"/\"><img src=\"images/header-back.gif\" alt=\"返回\" /></a>\n" +
                      "    搜索小说\n" +
                      "    <a class=\"iconhome\" href=\"/\"><img src=\"images/header-backhome.gif\" alt=\"首页\" /></a>\n" +
                      "</header>\n" +
                      "\n" +
                      "<form class=\"searchForm\" method=\"get\" action=\"/SearchBook.php\">\n" +
                      "    <input type=\"text\" name=\"keyword\" class=\"searchForm_input searchForm_input2\" placeholder=\"输入书名或作者\">\n" +
                      "    <input type=\"submit\" class=\"searchForm_btn\" value=\"搜索\">\n" +
                      "</form>\n" +
                      "\n" +
                      "<div class=\"tabArea  list rankingList rankingList_3\">\n" +
                      "    <div class=\"slide-con slide-con2\">\n" +
                      "        <div class=\"slide-item list1\">\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num1\">\n" +
                      "                        1                   </span>\n" +
                      "                    <a href=\"/168_168421/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            Z世代艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/168421/\">起酥面包</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/168_168421/66062878.html\" target=\"_blank\" title=\"第83章 一片混乱\">\n" +
                      "                            第83章 一片混乱                     </a>\n" +
                      "                        02-15                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num2\">\n" +
                      "                        2                   </span>\n" +
                      "                    <a href=\"/110_110938/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            全职艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/110938/\">我最白</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/110_110938/63110272.html\" target=\"_blank\" title=\"新书《重生了谁还当明星》\">\n" +
                      "                            新书《重生了谁还当明星》                     </a>\n" +
                      "                        07-08                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num3\">\n" +
                      "                        3                   </span>\n" +
                      "                    <a href=\"/32_32935/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            唱片艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/32935/\">夏日过客</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/32_32935/35643649.html\" target=\"_blank\" title=\"第四十八章 寻求帮助\">\n" +
                      "                            第四十八章 寻求帮助                     </a>\n" +
                      "                        05-11                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num4\">\n" +
                      "                        4                   </span>\n" +
                      "                    <a href=\"/133_133184/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            全能艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/133184/\">夏天不热</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/133_133184/53015657.html\" target=\"_blank\" title=\"第八十一章 一个冷笑话\">\n" +
                      "                            第八十一章 一个冷笑话                     </a>\n" +
                      "                        03-28                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num5\">\n" +
                      "                        5                   </span>\n" +
                      "                    <a href=\"/145_145339/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            灾厄艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/145339/\">收手吧阿咸</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/145_145339/58340673.html\" target=\"_blank\" title=\"110 麦克斯\">\n" +
                      "                            110 麦克斯                     </a>\n" +
                      "                        10-03                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num6\">\n" +
                      "                        6                   </span>\n" +
                      "                    <a href=\"/18_18788/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            天才艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/18788/\">黄金芽</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/18_18788/12354353.html\" target=\"_blank\" title=\"第108章 黑粉\">\n" +
                      "                            第108章 黑粉                     </a>\n" +
                      "                        12-14                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num7\">\n" +
                      "                        7                   </span>\n" +
                      "                    <a href=\"/89_89204/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            从艺术家开始                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/89204/\">烛</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/89_89204/42948348.html\" target=\"_blank\" title=\"第427章 封神之始！\">\n" +
                      "                            第427章 封神之始！                     </a>\n" +
                      "                        04-30                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num8\">\n" +
                      "                        8                   </span>\n" +
                      "                    <a href=\"/1_1037/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            我真是大艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/1037/\">司马青雨</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/1_1037/33380753.html\" target=\"_blank\" title=\"第446章 跟风神格突然解封\">\n" +
                      "                            第446章 跟风神格突然解封                     </a>\n" +
                      "                        02-21                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num9\">\n" +
                      "                        9                   </span>\n" +
                      "                    <a href=\"/120_120602/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            我真是个艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/120602/\">我要秃头</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/120_120602/47942711.html\" target=\"_blank\" title=\"什么，太监。\">\n" +
                      "                            什么，太监。                     </a>\n" +
                      "                        01-15                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num10\">\n" +
                      "                        10                   </span>\n" +
                      "                    <a href=\"/105_105878/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            我真的只想当艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/105878/\">葱油花卷呀</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/105_105878/43429145.html\" target=\"_blank\" title=\"第四十三章 惊梦\">\n" +
                      "                            第四十三章 惊梦                     </a>\n" +
                      "                        05-28                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num11\">\n" +
                      "                        11                   </span>\n" +
                      "                    <a href=\"/121_121005/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            开局离婚被迫成为全职艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/121005/\">金海浅</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/121_121005/53777665.html\" target=\"_blank\" title=\"终章！结局！\">\n" +
                      "                            终章！结局！                     </a>\n" +
                      "                        05-31                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num12\">\n" +
                      "                        12                   </span>\n" +
                      "                    <a href=\"/167_167749/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            叫谁偶像，我是艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/167749/\">油炸大金</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/167_167749/66061738.html\" target=\"_blank\" title=\"第14章 方法总比困难多 （4/4）\">\n" +
                      "                            第14章 方法总比困难多 （4/4）                     </a>\n" +
                      "                        02-15                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num13\">\n" +
                      "                        13                   </span>\n" +
                      "                    <a href=\"/141_141593/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            娱乐圈的边缘艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/141593/\">骑猪的小猫</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/141_141593/58704037.html\" target=\"_blank\" title=\"新书《请呼叫1988》已发，各位义父请支持！\">\n" +
                      "                            新书《请呼叫1988》已发，各位义父请支持！                     </a>\n" +
                      "                        11-13                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num14\">\n" +
                      "                        14                   </span>\n" +
                      "                    <a href=\"/156_156397/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            全能艺术家：从女团选秀开始                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/156397/\">薄暮映雪</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/156_156397/65929301.html\" target=\"_blank\" title=\"第356章 番外四：百鸟朝凤\">\n" +
                      "                            第356章 番外四：百鸟朝凤                     </a>\n" +
                      "                        02-01                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num15\">\n" +
                      "                        15                   </span>\n" +
                      "                    <a href=\"/141_141273/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            听劝后，我成了顶流艺术家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/141273/\">悉年</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/141_141273/57303937.html\" target=\"_blank\" title=\"第176章 我敲，林白都这么叼了吗？\">\n" +
                      "                            第176章 我敲，林白都这么叼了吗？                     </a>\n" +
                      "                        06-19                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num16\">\n" +
                      "                        16                   </span>\n" +
                      "                    <a href=\"/162_162486/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            诡异世代                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/162486/\">贰伍崽</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/162_162486/64032591.html\" target=\"_blank\" title=\"翊\">\n" +
                      "                            翊                     </a>\n" +
                      "                        09-08                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num17\">\n" +
                      "                        17                   </span>\n" +
                      "                    <a href=\"/90_90834/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            极恶世代                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/90834/\">心如若冰</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/90_90834/39538079.html\" target=\"_blank\" title=\"第二十八章 佛\">\n" +
                      "                            第二十八章 佛                     </a>\n" +
                      "                        11-11                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num18\">\n" +
                      "                        18                   </span>\n" +
                      "                    <a href=\"/89_89889/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            爆笑Z班                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/89889/\">排骨大叔</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/89_89889/53821434.html\" target=\"_blank\" title=\"第八百七十五章：白漠号（上）\">\n" +
                      "                            第八百七十五章：白漠号（上）                     </a>\n" +
                      "                        06-04                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num19\">\n" +
                      "                        19                   </span>\n" +
                      "                    <a href=\"/46_46728/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            星碎Z                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/46728/\">星碎Z</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/46_46728/22291216.html\" target=\"_blank\" title=\"正文 第32章：感情升温（一）\">\n" +
                      "                            正文 第32章：感情升温（一）                     </a>\n" +
                      "                        08-11                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num20\">\n" +
                      "                        20                   </span>\n" +
                      "                    <a href=\"/15_15420/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            艺术风云                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/15420/\">言万戈</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/15_15420/12291748.html\" target=\"_blank\" title=\"第0096章 商业战\">\n" +
                      "                            第0096章 商业战                     </a>\n" +
                      "                        12-12                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num21\">\n" +
                      "                        21                   </span>\n" +
                      "                    <a href=\"/76_76145/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            才女：玲珑劫                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/76145/\">起点艺术家</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/76_76145/33076913.html\" target=\"_blank\" title=\"第99章 还没开的玛莎拉蒂\">\n" +
                      "                            第99章 还没开的玛莎拉蒂                     </a>\n" +
                      "                        09-15                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num22\">\n" +
                      "                        22                   </span>\n" +
                      "                    <a href=\"/83_83915/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            最强长生赘婿                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/83915/\">文字艺术家</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/83_83915/38285831.html\" target=\"_blank\" title=\"第二百三十六章 大结局\">\n" +
                      "                            第二百三十六章 大结局                     </a>\n" +
                      "                        09-05                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num23\">\n" +
                      "                        23                   </span>\n" +
                      "                    <a href=\"/61_61154/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            第十艺术                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/61154/\">夜行生物</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/61_61154/30898720.html\" target=\"_blank\" title=\"第16章 独家消息\">\n" +
                      "                            第16章 独家消息                     </a>\n" +
                      "                        10-17                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num24\">\n" +
                      "                        24                   </span>\n" +
                      "                    <a href=\"/0_496/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            艺术人生                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/496/\">后来者</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/0_496/39098478.html\" target=\"_blank\" title=\"推荐新书《今天你立Flag了吗》\">\n" +
                      "                            推荐新书《今天你立Flag了吗》                     </a>\n" +
                      "                        09-24                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num25\">\n" +
                      "                        25                   </span>\n" +
                      "                    <a href=\"/53_53949/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            海贼之极恶的世代                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/53949/\">绒克</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/53_53949/37027081.html\" target=\"_blank\" title=\"新的群\">\n" +
                      "                            新的群                     </a>\n" +
                      "                        07-06                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num26\">\n" +
                      "                        26                   </span>\n" +
                      "                    <a href=\"/129_129147/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            归零世代                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/129147/\">再燃天地</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/129_129147/53197445.html\" target=\"_blank\" title=\"番外 我们的青春\">\n" +
                      "                            番外 我们的青春                     </a>\n" +
                      "                        04-08                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num27\">\n" +
                      "                        27                   </span>\n" +
                      "                    <a href=\"/68_68907/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            海贼之爆炸艺术                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/68907/\">农夫一拳</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/68_68907/36084534.html\" target=\"_blank\" title=\"新书：海贼之银狐大将\">\n" +
                      "                            新书：海贼之银狐大将                     </a>\n" +
                      "                        05-30                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num28\">\n" +
                      "                        28                   </span>\n" +
                      "                    <a href=\"/19_19109/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            电影艺术大师                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/19109/\">扑出个未来</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/19_19109/14334834.html\" target=\"_blank\" title=\"此书太监\">\n" +
                      "                            此书太监                     </a>\n" +
                      "                        01-25                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num29\">\n" +
                      "                        29                   </span>\n" +
                      "                    <a href=\"/29_29793/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            海贼王之极恶世代                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/29793/\">绒克</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/29_29793/20086651.html\" target=\"_blank\" title=\"新书《极恶的世代》已发布！\">\n" +
                      "                            新书《极恶的世代》已发布！                     </a>\n" +
                      "                        07-03                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num30\">\n" +
                      "                        30                   </span>\n" +
                      "                    <a href=\"/117_117488/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            第九艺术之书重启                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/117488/\">打火匣子</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/117_117488/48695982.html\" target=\"_blank\" title=\"第262章:戛然而止的终章\">\n" +
                      "                            第262章:戛然而止的终章                     </a>\n" +
                      "                        03-02                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num31\">\n" +
                      "                        31                   </span>\n" +
                      "                    <a href=\"/104_104777/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            俘获人心的沟通艺术.                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/104777/\">泉林..</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/104_104777/42752120.html\" target=\"_blank\" title=\"6.委婉说辞，征服人心\">\n" +
                      "                            6.委婉说辞，征服人心                     </a>\n" +
                      "                        01-14                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num32\">\n" +
                      "                        32                   </span>\n" +
                      "                    <a href=\"/134_134128/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            这才叫第九艺术                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/134128/\">我系萝莉控</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/134_134128/55983134.html\" target=\"_blank\" title=\"抱歉……\">\n" +
                      "                            抱歉……                     </a>\n" +
                      "                        02-26                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num33\">\n" +
                      "                        33                   </span>\n" +
                      "                    <a href=\"/91_91921/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/91921/\">巴金</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        全本 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/91_91921/38481591.html\" target=\"_blank\" title=\"后记\">\n" +
                      "                            后记                     </a>\n" +
                      "                        09-24                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num34\">\n" +
                      "                        34                   </span>\n" +
                      "                    <a href=\"/15_15982/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            明星老爸的艺术人生                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/15982/\">褚慕白</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/15_15982/11711976.html\" target=\"_blank\" title=\"《明星老爸的文娱人生》上传，求收藏，推荐！\">\n" +
                      "                            《明星老爸的文娱人生》上传，求收藏，推荐！                     </a>\n" +
                      "                        11-27                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num35\">\n" +
                      "                        35                   </span>\n" +
                      "                    <a href=\"/166_166137/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            我的艺术太超前了                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/166137/\">十五合</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/166_166137/66052401.html\" target=\"_blank\" title=\"第246章 还有天理吗？（求月票！！！）\">\n" +
                      "                            第246章 还有天理吗？（求月票！！！）                     </a>\n" +
                      "                        02-14                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num36\">\n" +
                      "                        36                   </span>\n" +
                      "                    <a href=\"/120_120611/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            家命                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/120611/\">莜里</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/120_120611/47778065.html\" target=\"_blank\" title=\"第六章 （终篇）\">\n" +
                      "                            第六章 （终篇）                     </a>\n" +
                      "                        01-04                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num37\">\n" +
                      "                        37                   </span>\n" +
                      "                    <a href=\"/148_148254/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            丸家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/148254/\">小裴爱玩原神</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/148_148254/65681500.html\" target=\"_blank\" title=\"有事外出，休息两天\">\n" +
                      "                            有事外出，休息两天                     </a>\n" +
                      "                        01-08                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num38\">\n" +
                      "                        38                   </span>\n" +
                      "                    <a href=\"/143_143035/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            支配家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/143035/\">毛笔扎蚂蚁</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/143_143035/57033309.html\" target=\"_blank\" title=\"调研\">\n" +
                      "                            调研                     </a>\n" +
                      "                        05-27                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num39\">\n" +
                      "                        39                   </span>\n" +
                      "                    <a href=\"/61_61120/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            家之初                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/61120/\">孑与2</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/61_61120/26763216.html\" target=\"_blank\" title=\"家之初\">\n" +
                      "                            家之初                     </a>\n" +
                      "                        12-27                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num40\">\n" +
                      "                        40                   </span>\n" +
                      "                    <a href=\"/23_23719/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            谋家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/23719/\">林木儿</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/23_23719/37526957.html\" target=\"_blank\" title=\"246 新元十年（大结局）\">\n" +
                      "                            246 新元十年（大结局）                     </a>\n" +
                      "                        07-30                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num41\">\n" +
                      "                        41                   </span>\n" +
                      "                    <a href=\"/1_1615/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            家谋                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/1615/\">三叹</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/1_1615/25361449.html\" target=\"_blank\" title=\"第328章 发展\">\n" +
                      "                            第328章 发展                     </a>\n" +
                      "                        10-07                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num42\">\n" +
                      "                        42                   </span>\n" +
                      "                    <a href=\"/113_113632/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            家主                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/113632/\">北隐</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        全本 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/113_113632/45295493.html\" target=\"_blank\" title=\"完结感言\">\n" +
                      "                            完结感言                     </a>\n" +
                      "                        08-27                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num43\">\n" +
                      "                        43                   </span>\n" +
                      "                    <a href=\"/155_155339/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            棠家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/155339/\">性感美丽母蟑螂</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/155_155339/61486277.html\" target=\"_blank\" title=\"第20章\">\n" +
                      "                            第20章                     </a>\n" +
                      "                        03-28                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num44\">\n" +
                      "                        44                   </span>\n" +
                      "                    <a href=\"/164_164213/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            星轨之梦：艺术与未来的交                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/164213/\">我有你有</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/164_164213/64351238.html\" target=\"_blank\" title=\"第 23 章 友谊与团结加深\">\n" +
                      "                            第 23 章 友谊与团结加深                     </a>\n" +
                      "                        10-01                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num45\">\n" +
                      "                        45                   </span>\n" +
                      "                    <a href=\"/74_74476/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            白家军                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/74476/\">菀守紫心</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/74_74476/32705561.html\" target=\"_blank\" title=\"第九十二章\">\n" +
                      "                            第九十二章                     </a>\n" +
                      "                        01-26                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num46\">\n" +
                      "                        46                   </span>\n" +
                      "                    <a href=\"/76_76506/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            家国兴起                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/76506/\">胡诌家言</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/76_76506/34469650.html\" target=\"_blank\" title=\"第84章 \">\n" +
                      "                            第84章                      </a>\n" +
                      "                        03-27                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num47\">\n" +
                      "                        47                   </span>\n" +
                      "                    <a href=\"/9_9069/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            天生娱乐家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/9069/\">夜苍</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/9_9069/50231823.html\" target=\"_blank\" title=\"新书《我未来老婆竟是天后》，求支持！\">\n" +
                      "                            新书《我未来老婆竟是天后》，求支持！                     </a>\n" +
                      "                        06-28                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num48\">\n" +
                      "                        48                   </span>\n" +
                      "                    <a href=\"/16_16051/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            极品娱乐家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/16051/\">孜然腰花</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/16_16051/29962758.html\" target=\"_blank\" title=\"第三百零一章 开机\">\n" +
                      "                            第三百零一章 开机                     </a>\n" +
                      "                        09-28                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale\">\n" +
                      "                    <span class=\"num num49\">\n" +
                      "                        49                   </span>\n" +
                      "                    <a href=\"/59_59193/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            野心家                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/59193/\">石头与水</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/59_59193/35339378.html\" target=\"_blank\" title=\"311 韶华之四\">\n" +
                      "                            311 韶华之四                     </a>\n" +
                      "                        04-27                   </p>\n" +
                      "                </div>\n" +
                      "                                <div class=\"hot_sale hot_saleEm\">\n" +
                      "                    <span class=\"num num50\">\n" +
                      "                        50                   </span>\n" +
                      "                    <a href=\"/32_32605/\">\n" +
                      "                        <p class=\"title\">\n" +
                      "                            宁家女儿                        </p>\n" +
                      "                   \n" +
                      "                        <p class=\"author\">\n" +
                      "                            作者：<a href=\"/author/32605/\">繁朵</a>\n" +
                      "                        </p>\n" +
                      "                    </a>\n" +
                      "                    <p class=\"author\">\n" +
                      "                        连载 | 最近更新：\n" +
                      "                        <a style=\"color: Red;\" href=\"/32_32605/17839447.html\" target=\"_blank\" title=\"正文 第一百十六章 惊变\">\n" +
                      "                            正文 第一百十六章 惊变                     </a>\n" +
                      "                        05-25                   </p>\n" +
                      "                </div>\n" +
                      "                                \n" +
                      "                \n" +
                      "        </div>\n" +
                      "    </div>\n" +
                      "</div>\n" +
                      "\n" +
                      "</div>\n" +
                      "\n" +
                      "\n" +
                      "<form class=\"searchForm\" method=\"get\" action=\"/SearchBook.php\">\n" +
                      "    <input type=\"text\" name=\"keyword\" class=\"searchForm_input searchForm_input2\" placeholder=\"输入书名或作者\">\n" +
                      "    <input type=\"submit\" class=\"searchForm_btn\" value=\"搜索\">\n" +
                      "</form>\n" +
                      "<footer>\n" +
                      "    <a href=\"#top\"><img src=\"/images/icon-backtop.gif\" title=\"↑\" alt=\"↑\"></a>\n" +
                      "    <p class=\"version channel\">\n" +
                      "        <a href=\"/\">首页</a>\n" +
                      "        <a href=\"/bookcase.html\">我的书架</a>\n" +
                      "        <a href=\"/gj.html\">阅读记录</a>\n" +
                      "        <a href=\"/api/sitemap.xml\">sitemap</a>\n" +
                      "    </p>\n" +
                      "</footer>\n" +
                      "<script>tj();</script>\n" +
                      "\n" +
                      "</body>\n" +
                      "</html>");
    }
    @Test
    public void trySearchBookId() {
        RuleBean ruleBean= new RuleBean();
        ruleBean.setSearchUrl("http://m.mcmssc.la/SearchBook.php?keyword={bookName}");
        ruleBean.setSearchBookId("<a href=\"/(\\d+_\\d+)/\">");
        String bookId = listener.trySearchBookId("Z世代艺术家", ruleBean);
        Assert.assertNotNull(bookId);
    }
}