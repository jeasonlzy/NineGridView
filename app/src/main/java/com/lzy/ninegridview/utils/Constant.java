package com.lzy.ninegridview.utils;

import com.lzy.ninegridview.bean.DetailNews;
import com.lzy.ninegridview.bean.ImageDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/15
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class Constant {

    private static final Random RANDOM = new Random();
    private static final int TOTAL = 20;

    /** 模拟假数据 */
    public static List<DetailNews> getData() {
        ArrayList<DetailNews> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DetailNews detailNews = new DetailNews();
            detailNews.setTitle(TITLES[RANDOM.nextInt(TOTAL)]);
            int imageCount = RANDOM.nextInt(9);
            ArrayList<ImageDetail> imageDetails = new ArrayList<>();
            int nextInt = RANDOM.nextInt(5);
            if (nextInt == 0) {

            } else if (nextInt == 1) {
                imageDetails.add(URLS[RANDOM.nextInt(TOTAL)]);
            } else {
                for (int j = 0; j <= imageCount; j++) {
                    imageDetails.add(URLS[RANDOM.nextInt(TOTAL)]);
                }
            }
            detailNews.setImageDetails(imageDetails);
            list.add(detailNews);
        }
        return list;
    }

    private static final ImageDetail[] URLS = new ImageDetail[]{//
            new ImageDetail(900, 573, "http://c.hiphotos.baidu.com/image/pic/item/18d8bc3eb13533fa48f84527aad3fd1f41345b69.jpg"),//
            new ImageDetail(639, 960, "http://d.hiphotos.baidu.com/image/pic/item/562c11dfa9ec8a13f075f10cf303918fa1ecc0eb.jpg"),//
            new ImageDetail(640, 960, "http://h.hiphotos.baidu.com/image/pic/item/dc54564e9258d1093cf78e5cd558ccbf6d814dc3.jpg"),//
            new ImageDetail(640, 960, "http://a.hiphotos.baidu.com/image/pic/item/279759ee3d6d55fb924d52c869224f4a21a4dd50.jpg"),//
            new ImageDetail(640, 960, "http://d.hiphotos.baidu.com/image/pic/item/f3d3572c11dfa9ec994471f966d0f703908fc1f1.jpg"),//
            new ImageDetail(500, 750, "http://c.hiphotos.baidu.com/image/pic/item/09fa513d269759ee673682cfb0fb43166d22df54.jpg"),//
            new ImageDetail(750, 500, "http://a.hiphotos.baidu.com/image/pic/item/aa18972bd40735faeef4a3bd9c510fb30f2408a9.jpg"),//
            new ImageDetail(640, 413, "http://g.hiphotos.baidu.com/image/pic/item/4d086e061d950a7bcca92acf08d162d9f2d3c919.jpg"),//
            new ImageDetail(900, 569, "http://f.hiphotos.baidu.com/image/pic/item/00e93901213fb80e0ee553d034d12f2eb9389484.jpg"),//
            new ImageDetail(610, 801, "http://d.hiphotos.baidu.com/image/pic/item/55e736d12f2eb938e0ae49f5d7628535e4dd6ff1.jpg"),//
            new ImageDetail(533, 799, "http://e.hiphotos.baidu.com/image/pic/item/83025aafa40f4bfb27bfbf2b014f78f0f7361865.jpg"),//
            new ImageDetail(522, 787, "http://e.hiphotos.baidu.com/image/pic/item/574e9258d109b3de08cdc16fcebf6c81800a4c42.jpg"),//
            new ImageDetail(880, 580, "http://e.hiphotos.baidu.com/image/pic/item/622762d0f703918f94eaf0ce533d269759eec4ff.jpg"),//
            new ImageDetail(914, 567, "http://f.hiphotos.baidu.com/image/pic/item/c2cec3fdfc039245831fa7498294a4c27c1e25c9.jpg"),//
            new ImageDetail(472, 700, "http://b.hiphotos.baidu.com/image/pic/item/b64543a98226cffc47102fb2bb014a90f603eafc.jpg"),//
            new ImageDetail(850, 533, "http://a.hiphotos.baidu.com/image/pic/item/4d086e061d950a7b6ad3cc9508d162d9f3d3c9f9.jpg"),//
            new ImageDetail(750, 499, "http://f.hiphotos.baidu.com/image/pic/item/cb8065380cd791234275326baf345982b2b7801c.jpg"),//
            new ImageDetail(499, 750, "http://e.hiphotos.baidu.com/image/pic/item/5882b2b7d0a20cf4ed0558c074094b36acaf991c.jpg"),//
            new ImageDetail(612, 911, "http://a.hiphotos.baidu.com/image/pic/item/8b13632762d0f703c9a773c30afa513d2697c59c.jpg"),//
            new ImageDetail(613, 842, "http://e.hiphotos.baidu.com/image/pic/item/9358d109b3de9c82cde6458f6e81800a19d8432a.jpg")};

    private static final String[] TITLES = new String[]{//
            "当我苏醒，已经事隔千年。万丈红尘，冉冉浮生，诸多人事几番断。如果你不能爱我，就不该打扰我的平静。",//
            "是的，我仍然相信他，他留给我的茶、留给我的时光、留给我的记忆，握在手心，都是有温度的。我只是无法相信我自己。",//
            "因为要等日出，必然会辜负安眠，但别错过山顶每一丝原本就属于你的风景。",//
            "在季节的列车上，如果你要提前下车，请别推醒装睡的我，这样我可以沉睡到终点，假装不知道你已离开。",//
            "一个人的记忆就是一座城市，时间腐蚀着一切建筑，把高楼和道路全部沙化。如果你不往前走就会被沙子掩埋，所以我们泪流满面，步步回头，可是只能往前走。",//
            "青春就是匆匆披挂上阵，末了战死沙场。你为谁冲锋陷阵，谁为你捡拾骸骨，剩下依旧在河流中漂泊的刀痕，沉寂在水面之下，只有自己看得到。",//
            "世事如书，我偏爱你这一句，愿做个逗号，待在你的脚边。但你有自己的朗读者，而我只是个摆渡人。",//
            "回忆不能抹去，只好慢慢堆积，岁月带你走上牌桌偏偏赌注是自己。",//
            "雨过天晴，终要好天气。时间予我千万种满心欢喜，沿途逐枝怒放，全部遗漏都不要紧，得你一枝配我胸襟就好。",//
            "用一段时光，换一次懂得。曾有一个人，你用尽所有痴狂，爱他如生命。愿有一个人，让你收起铅华，用心陪他走过光阴。",//
            "随风而逝的爱，沉默在心里，掩盖在岁月中，如同深夜孤独吟唱的歌谣，霜浓风重，究竟要唱给谁来听?",//
            "这世上，不是只有烈酒才能醉人，不是只有热恋才会刻骨，有时候，一份清淡，更能历久弥香；一种无意，更能魂牵梦萦；一段简约，更可以维系一生。",//
            "松声空壑动，月镌花影碧窗幽，静观淡漠了的人生中很多是是非非，那些功利性很强的东西都被大脑屏蔽，注重自然赋予的灵魂，使一切都重新回到一个起点。",//
            "彼岸繁花，开一千年，落一千年，花叶不相见，情不为因果，缘注定生死，浮华苍桑，终究太多的伤。喧嚣、沉寂，终究躲不过悲凉。蝶恋天涯，迁移一季，守望一季，对影两相弃，爱不为情生，璨璨泪雨下，流年，残惜，终究太多的痛，繁花，没落，终究逃不过惆怅。",//
            "闭上繁花，推开扰人，空握一滴透明无色的水。头上的明月，你可会为我洒一片诱人的好光阴。回及那些没有颜色的年轮，再翦一幅西窗含泪。",//
            "繁华殆尽，独留寂寥，谁许谁的天荒地老？时间消磨一切，流光容易把人抛。",//
            "抱琴不语，思量，难忘。奏一曲清苦桃花，谁知，谁想？",//
            "我们要去流浪，虔诚地定格住每一寸记忆；我们要去成长，潇洒地忘却掉每一条纹路。",//
            "枕函香，花径漏。依约相逢，絮语黄昏后。时节薄寒人病酒，铲地梨花，彻夜东风瘦。",//
            "掩银屏，垂翠袖。何处吹箫，脉脉情微逗。肠断月明红豆蔻，月似当时，人似当时否？"};
}