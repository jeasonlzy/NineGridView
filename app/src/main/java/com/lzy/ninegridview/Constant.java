package com.lzy.ninegridview;

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
            ArrayList<String> urls = new ArrayList<>();
            for (int j = 0; j <= imageCount; j++) {
                urls.add(URLS[RANDOM.nextInt(TOTAL)]);
            }
            detailNews.setImageUrls(urls);
            list.add(detailNews);
        }
        return list;
    }

    private static final String[] URLS = new String[]{//
            "http://img1.3lian.com/2015/w6/60/d/89.jpg",//
            "http://img1.3lian.com/2015/w6/60/d/3.jpg",//
            "http://img1.3lian.com/2015/w6/60/d/84.jpg",//
            "http://img1.3lian.com/2015/w7/87/d/27.jpg",//
            "http://img1.3lian.com/2015/w6/60/d/21.jpg",//
            "http://img1.3lian.com/2015/w6/60/d/9.jpg",//
            "http://img2.3lian.com/2014/f5/36/d/9.jpg",//
            "http://img2.3lian.com/2014/c7/12/d/62.jpg",//
            "http://img2.3lian.com/2014/f4/171/d/69.jpg",//
            "http://img2.3lian.com/2014/f5/36/d/11.jpg",//
            "http://img2.3lian.com/2014/f4/143/d/105.jpg",//
            "http://img2.3lian.com/2014/f4/143/d/105.jpg",//
            "http://img15.3lian.com/2015/f2/147/d/43.jpg",//
            "http://img15.3lian.com/2015/f2/147/d/31.jpg",//
            "http://img15.3lian.com/2015/f2/147/d/39.jpg",//
            "http://img15.3lian.com/2015/f2/147/d/33.jpg",//
            "http://img15.3lian.com/2015/f2/170/d/22.jpg",//
            "http://img15.3lian.com/2015/f2/147/d/37.jpg",//
            "http://img15.3lian.com/2015/f2/147/d/37.jpg",//
            "http://img15.3lian.com/2015/f2/147/d/23.jpg"};

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
