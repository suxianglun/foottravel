package com.footprint.travel.entity;

import java.util.List;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/20 @版本：
 */
public class ScenerySubject {

    /**
     * error_code : 0
     * reason : 成功
     * result : [{"title":"世纪欢乐园","grade":"AAAA","price_min":"35","comm_cnt":null,"cityId":"163","address":"河南省郑州市中国郑州石化路1号 （中州大道与石化路交叉口）","sid":"20454","url":"http://www.ly.com/scenery/BookSceneryTicket_20454.html","imgurl":"http://pic4.40017.cn/scenery/destination/2016/08/29/15/nxXXdI_240x135_00.jpg"},{"title":"郑州绿博园","grade":"AAAA","price_min":"26","comm_cnt":null,"cityId":"163","address":"河南省郑州市郑开大道与人文路交汇向南2000米","sid":"29296","url":"http://www.ly.com/scenery/BookSceneryTicket_29296.html","imgurl":"http://pic3.40017.cn/scenery/destination/2015/04/19/05/1AAvv3_240x135_00.jpg"},{"title":"中原福塔","grade":"","price_min":"108","comm_cnt":null,"cityId":"163","address":"河南省郑州市管城回族区航海东路与机场高速路交汇处","sid":"29870","url":"http://www.ly.com/scenery/BookSceneryTicket_29870.html","imgurl":"http://pic3.40017.cn/scenery/destination/2016/02/05/09/vImA3I_240x135_00.jpg"},{"title":"郑州黄河风景名胜区","grade":"AAAA","price_min":"54","comm_cnt":null,"cityId":"163","address":"郑州市黄河南岸郑州黄河风景名胜区","sid":"28411","url":"http://www.ly.com/scenery/BookSceneryTicket_28411.html","imgurl":"http://pic4.40017.cn/scenery/destination/2016/11/09/15/WkaQ8y_240x135_00.jpg"},{"title":"郑州方特欢乐世界","grade":"AAAA","price_min":"119","comm_cnt":null,"cityId":"163","address":"郑州市郑州新区郑开大道与华强路交汇处","sid":"29894","url":"http://www.ly.com/scenery/BookSceneryTicket_29894.html","imgurl":"http://pic4.40017.cn/scenery/destination/2016/09/26/21/xh0TNV_240x135_00.jpg"},{"title":"少林景区（少林寺）","grade":"AAAAA","price_min":"70","comm_cnt":null,"cityId":"163","address":"河南省郑州市登封市","sid":"5434","url":"http://www.ly.com/scenery/BookSceneryTicket_5434.html","imgurl":"http://pic3.40017.cn/scenery/destination/2015/04/18/15/NJNjeq_240x135_00.jpg"},{"title":"点点梦想城","grade":"","price_min":"20","comm_cnt":null,"cityId":"163","address":"河南郑州金水路玉凤路名门城市广场5楼","sid":"29798","url":"http://www.ly.com/scenery/BookSceneryTicket_29798.html","imgurl":"http://pic3.40017.cn/scenery/destination/2015/10/09/16/KsrXZs_240x135_00.jpg"},{"title":"浮戏山雪花洞景区","grade":"AAA","price_min":"46","comm_cnt":null,"cityId":"163","address":"河南郑州巩义市新中镇浮戏山雪花洞景区","sid":"8730","url":"http://www.ly.com/scenery/BookSceneryTicket_8730.html","imgurl":"http://pic3.40017.cn/scenery/destination/2015/04/18/22/owIFB3_240x135_00.jpg"},{"title":"江南春温泉","grade":"","price_min":"138","comm_cnt":null,"cityId":"163","address":"河南省郑州市荥阳市近郊江山路与北四环交叉口向西七公里左右","sid":"30039","url":"http://www.ly.com/scenery/BookSceneryTicket_30039.html","imgurl":"http://pic4.40017.cn/scenery/destination/2016/11/11/14/RHaWOG_240x135_00.jpg"},{"title":"普兰斯薰衣草庄园","grade":"","price_min":"50","comm_cnt":null,"cityId":"163","address":"河南省郑州市惠济区赵兰庄向北两公里","sid":"30781","url":"http://www.ly.com/scenery/BookSceneryTicket_30781.html","imgurl":"http://pic4.40017.cn/scenery/destination/2016/10/28/17/ZsPc0j_240x135_00.jpg"}]
     */

    private int error_code;
    private String reason;
    private List<Scenery> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Scenery> getResult() {
        return result;
    }

    public void setResult(List<Scenery> result) {
        this.result = result;
    }


}
