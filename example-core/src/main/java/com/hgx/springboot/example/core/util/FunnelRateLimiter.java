/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: FunnelRateLimiter
 * Author:   gengxin.hao
 * Date:     2021/5/27 9:35
 * Description: 漏斗限流算法
 * History:
 */
package com.hgx.springboot.example.core.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈漏斗限流算法〉
 *
 * @author gengxin.hao
 * @create 2021/5/27
 * @since 1.0.0
 */
public class FunnelRateLimiter {

    private Map<String, Funnel> funnelMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        FunnelRateLimiter limiter = new FunnelRateLimiter();
        int testAccessCount = 10;
        //漏桶容量
        int capacity = 5;
        //每秒多少次
        int allowQuota = 5;
        //时间秒
        int perSecond = 30;
        //允许访问次数
        int allowCount = 0;
        //拒绝访问次数
        int denyCount = 0;
        for (int i = 0; i < testAccessCount; i++) {
            boolean isAllow = limiter.isActionAllowed("jeep", "doSomething", 5, 5, 30);
            if (isAllow) {
                allowCount++;
            } else {
                denyCount++;
            }
            System.out.println("访问权限：" + isAllow);
            Thread.sleep(1000);
        }
        System.out.println("报告：");
        System.out.println("漏斗容量：" + capacity);
        System.out.println("漏斗流动速率：" + allowQuota + "次/" + perSecond + "秒");

        System.out.println("测试次数=" + testAccessCount);
        System.out.println("容许次数=" + allowCount);
        System.out.println("拒绝次数=" + denyCount);
    }

    /**
     * 根据给定的漏斗参数检查是否容许访问
     *
     * @param username   用户名
     * @param action     操做
     * @param capacity   漏斗容量
     * @param allowQuota 每单个单位时间容许的流量
     * @param perSecond  单位时间（秒）
     * @return 是否容许访问
     */
    public boolean isActionAllowed(String username, String action, int capacity, int allowQuota, int perSecond) {
        String key = "funnel:" + action + ":" + username;
        if (!funnelMap.containsKey(key)) {
            funnelMap.put(key, new Funnel(capacity, allowQuota, perSecond));
        }
        Funnel funnel = funnelMap.get(key);
        return funnel.watering(1);
    }

    private static class Funnel {
        private int capacity;//漏斗容量
        private float leakingRate;//平均速率
        private int leftQuota;//剩余容量
        private long leakingTs;//记录上次流出的时间

        public Funnel(int capacity, int count, int perSecond) {
            this.capacity = capacity;
            // 由于计算使用毫秒为单位的
            perSecond *= 1000;
            this.leakingRate = (float) count / perSecond;//容许的流量/单位时间（毫秒）
        }

        /**
         * 根据上次水流动的时间，腾出已流出的空间
         */
        private void makeSpace() {
            long now = System.currentTimeMillis();
            long time = now - leakingTs;
            int leaked = (int) (time * leakingRate);
            System.out.println("leaked=="+leaked+",time="+time+",leakingRate"+Float.toString(leakingRate));
            if (leaked < 1) {
                return;
            }
            leftQuota += leaked;
            // 若是剩余大于容量，则剩余等于容量
            if (leftQuota > capacity) {
                leftQuota = capacity;
            }
            leakingTs = now;
        }

        /**
         * 漏斗漏水
         *
         * @param quota 流量
         * @return 是否有足够的水能够流出（是否容许访问）
         */
        public boolean watering(int quota) {
            makeSpace();
            int left = leftQuota - quota;
            if (left >= 0) {
                leftQuota = left;
                return true;
            }
            return false;
        }
    }
}
