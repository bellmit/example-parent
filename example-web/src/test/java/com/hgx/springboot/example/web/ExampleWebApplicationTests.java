package com.hgx.springboot.example.web;

//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;


import com.alibaba.fastjson.JSONObject;
import com.hgx.springboot.example.core.util.HttpClientUtil;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.time.DayOfWeek.MONDAY;

//@SpringBootTest
public class ExampleWebApplicationTests {

    static List<Group> groups = new ArrayList<>();

    @Test
    public void contextLoads() {
        List<String> list = new LinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        System.out.println("list size" + list.size());
//        for (String str : list) {
//            System.out.println(str);
//            if (str.equals("4")) {
//                list.remove(str);
//            }
//        }
        System.out.println("=================");
        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
            if (i == 0) {
                list.remove(i);
                System.out.println("list size new " + list.size());
                System.out.println(list.get(i));
            }else {
                System.out.println(list.get(i));
            }
        }
    }

    static {
        List<Customer> customers1 = new ArrayList<>();
        customers1.add(new Customer("李建鹏", "18612830669"));
        customers1.add(new Customer("王泊涵", "18513044261"));
        List<Customer> customers2 = new ArrayList<>();
        customers2.add(new Customer("郝庚鑫", "13231936831"));
        customers2.add(new Customer("谢延君", "18204370100"));
        List<Customer> customers3 = new ArrayList<>();
        customers3.add(new Customer("宋迁越","13718045310"));
        customers3.add(new Customer("王泊涵", "18145662119"));
        Group group1 = new Group("第一组", customers1);
        Group group2 = new Group("第二组", customers2);
        Group group3 = new Group("第三组", customers3);

        groups.add(group1);
        groups.add(group2);
        groups.add(group3);
    }

    static class Group {

        private String groupName;

        private List<Customer> customers;

        public String getGroupName() {
            return groupName;
        }

        public List<Customer> getCustomers() {
            return customers;
        }

        public void setCustomers(List<Customer> customers) {
            this.customers = customers;
        }

        public Group(String groupName, List<Customer> customers) {
            this.groupName = groupName;
            this.customers = customers;
        }

        @Override
        public String toString() {
            return "Group{" +
                    "groupName='" + groupName + '\'' +
                    ", customers=" + customers +
                    '}';
        }
    }

    static class Customer {

        private String name;

        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Customer(String name) {
            this.name = name;
        }

        public Customer(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "name='" + name + '\'' +
                    "phone='" + phone + '\'' +
                    '}';
        }
    }


    public static void main(String[] args) {
        System.out.println("================");

        groups.stream().forEach(group -> {
            System.out.println(group);
        });

        System.out.println("请输入从第几组开始排班：");
        Scanner input = new Scanner(System.in);
        int groupIndex = input.nextInt();
        System.out.println("==============");
        if (groupIndex > groups.size()) {
            System.out.println("请输入合法的组号");
        }else {
            int totalDay = 5;
            List<Group> shift = new ArrayList<>();
            int count = 0 ;
            while (count != totalDay) {
                for (int i = groupIndex - 1; i < groups.size(); i++) {
                    shift.add(groups.get(i));
                    count ++;
                    if (count == totalDay) {
                        break;
                    }
                }
                groupIndex = 1;
            }
            System.out.println("================");

            shift.stream().forEach(name -> {
                System.out.println("name:" + name);
            });

            DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
            int index = 0;
            switch (dayOfWeek) {
                case MONDAY:
                    break;
                case TUESDAY:
                    index = 1;
                    break;
                case WEDNESDAY:
                    index = 2;
                    break;
                case THURSDAY:
                    index = 3;
                    break;
                case FRIDAY:
                case SATURDAY:
                case SUNDAY:
                    index = -1;
                    break;
            }
            if (index != -1) {
                sendWorkTable(shift.get(index));
            }
        }
    }

    public static void sendWorkTable(Group group) {
        try {
            String groupName = group.getGroupName();
            System.out.println("今天是：" + groupName + "加班！");
            Long timestamp = System.currentTimeMillis();
            String secret = "SEC9285d5f3d728146696d1477188c624ccc76b70cac604d38b67d8afb254441851";//交易通道组
//            String secret = "SEC4da03500eaffb0cf52c9d66928887e520add817f99402746fbf92026fc2f439b";//测试组
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
            String url = "https://oapi.dingtalk.com/robot/send?access_token=ff4eeba5f764a413edbf9a268e2e246613a5164ff5f57cece32f5ff2cef20232" +
                    "&timestamp=" + timestamp +
                    "&sign=" + sign;
            JSONObject requestData = new JSONObject();
            List<String> atMobiles = new ArrayList();//被@人的手机号。
            requestData.put("msgtype", "text");
            JSONObject text = new JSONObject();
            StringBuilder builder = new StringBuilder("今天加班是");

            JSONObject at = new JSONObject();
            group.getCustomers().stream().forEach(customer -> {
                atMobiles.add(customer.getPhone());
                builder.append(customer.getName() + " ");
            });
            builder.append("晚上记得吃饭哦！");
//            atMobiles.add("13231936831");
            at.put("atMobiles", atMobiles);
            text.put("content", builder.toString());
            requestData.put("text", text);
            List<String> atUserIds = new ArrayList();//被@人的用户userid。
//            at.put("atUserIds", atUserIds);
//            at.put("isAtAll", true);
            requestData.put("at", at);
            String result = HttpClientUtil.doPostSSL(url, JSONObject.parse(requestData.toJSONString()));
            System.out.println(result);
        }catch (Exception e) {
            System.out.println("exception:");
        }
    }

    public String sign() throws Exception {
        Long timestamp = System.currentTimeMillis();
        String secret = "SEC9285d5f3d728146696d1477188c624ccc76b70cac604d38b67d8afb254441851";
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        System.out.println(sign);
        return sign;
    }



}
