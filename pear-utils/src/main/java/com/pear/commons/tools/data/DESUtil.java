package com.pear.commons.tools.data;


import com.pear.commons.tools.base.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by littlehui on 2015/10/15.
 */
public class DESUtil {
    //算法名称
    public static final String KEY_ALGORITHM = "DES";
    //算法名称/加密模式/填充方式
    //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    //PKCS5Padding 不是8字节的缺几个补什么,补齐为止。恰好8个字节时还要补8个字节的0×08
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    /**
     *
     * 生成密钥key对象
     * @param keyStr 密钥字符串
     * @return 密钥对象
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws Exception
     */
    private static SecretKey keyGenerator(String keyStr) throws Exception {
        byte input[] = Base64.decodeBase64(keyStr);
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        DESKeySpec desKey = new DESKeySpec(input);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        return securekey;
    }

    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    // 从十六进制字符串到字节数组转换
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    /**
     * 加密数据
     * @param data 待加密数据
     * @param key 密钥
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecureRandom random = new SecureRandom();
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
        byte[] results = cipher.doFinal(data.getBytes("UTF-8"));
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return Base64.encodeBase64String(results);
    }

    /**
     * 解密数据
     * @param data 待解密数据
     * @param key 密钥
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        // 执行解密操作
        return new String(cipher.doFinal(Base64.decodeBase64(data)), "UTF-8");
    }

    public static void main1(String[] args) {
        String s = "{\\\"businessCommodityCode\\\":\\\"647\\\",\\\"mainTitle\\\":\\\"魅影大衣\\\",\\\"secondTitle\\\":\\\"魅影大衣副标题\\\",\\\"gameCode\\\":\\\"1001\\\",\\\"gameName\\\":\\\"地下城与勇士\\\",\\\"imageUrls\\\":[\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/detail/A2.jpg；http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/detail/B2.jpg；http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/detail/C2.jpg\\\"],\\\"mainImageUrl\\\":\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/page/264.jpg\\\",\\\"detail\\\":\\\"<img alt=\\\\\\\"\\\\\\\" src=\\\\\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/1.jpg\\\\\\\" /><img alt=\\\\\\\"\\\\\\\" src=\\\\\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/2.jpg\\\\\\\" /><img alt=\\\\\\\"\\\\\\\" src=\\\\\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/3.jpg\\\\\\\" /><img alt=\\\\\\\"\\\\\\\" src=\\\\\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/4.jpg\\\\\\\" /><img alt=\\\\\\\"\\\\\\\" src=\\\\\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/5.jpg\\\\\\\" /><img alt=\\\\\\\"\\\\\\\" src=\\\\\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/6.jpg\\\\\\\" /><img alt=\\\\\\\"\\\\\\\" src=\\\\\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/7.jpg\\\\\\\" /><img alt=\\\\\\\"\\\\\\\" src=\\\\\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/8.jpg\\\\\\\" /><img alt=\\\\\\\"\\\\\\\" src=\\\\\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/9.jpg\\\\\\\" /><img alt=\\\\\\\"\\\\\\\" src=\\\\\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/10.jpg\\\\\\\" />\\\",\\\"style\\\":{\\\"styleName\\\":\\\"灰\\\",\\\"styleUrl\\\":\\\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/detail/A1.jpg\\\"},\\\"costPrice\\\":398,\\\"sellingPrice\\\":418,\\\"size\\\":[{\\\"sizeName\\\":\\\"M\\\",\\\"stock\\\":\\\"16\\\"},{\\\"sizeName\\\":\\\"L\\\",\\\"stock\\\":\\\"21\\\"},{\\\"sizeName\\\":\\\"XL\\\",\\\"stock\\\":\\\"29\\\"},{\\\"sizeName\\\":\\\"XXL\\\",\\\"stock\\\":\\\"62\\\"}]}"
                .replaceAll("\\\\","");
        String s1 = "<img alt=\"\" src=\"http: //img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/1.jpg\" /><img alt=\"\" src=\"http: //img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/2.jpg\" /><img alt=\"\" src=\"http: //img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/3.jpg\" /><img alt=\"\" src=\"http: //img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/4.jpg\" /><img alt=\"\" src=\"http: //img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/5.jpg\" /><img alt=\"\" src=\"http: //img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/6.jpg\" /><img alt=\"\" src=\"http: //img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/7.jpg\" /><img alt=\"\" src=\"http: //img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/8.jpg\" /><img alt=\"\" src=\"http: //img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/9.jpg\" /><img alt=\"\" src=\"http: //img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/10.jpg\" />"
                .replaceAll("\"","\\\\\"");

        System.out.println(s);
        System.out.println(s1);
    }

    public static void main(String[] args) throws Exception {
        String source = "/provice/commodity/visual/add?sourceCode=11&businessCommodityCode=12313131&name=龙霸天的刀&unitPrice=212.24";
        //String source1 = "{\"startTime\":null,\"endTime\":null,\"pageNo\":1,\"pageSize\":15,\"rowStart\":0,\"paged\":true,\"totalCount\":null,\"sourceCode\":null,\"authCode\":null,\"id\":null,\"businessCommodityCode\":\"11025487451\",\"name\":null,\"gameCode\":\"DHXY\",\"gameName\":null,\"commodityClassifys\":null,\"imageUrls\":null,\"commodityType\":\"ZB\",\"commodityTypeName\":null,\"tradeType\":null,\"tradeTypeName\":null,\"tradeMode\":null,\"tradeModeName\":null,\"seller\":{\"startTime\":null,\"endTime\":null,\"pageNo\":1,\"pageSize\":15,\"rowStart\":0,\"paged\":true,\"totalCount\":null,\"sourceCode\":null,\"authCode\":null,\"account\":\"stest@163.com\",\"playerRoleLevel\":null,\"gameRegionName\":null,\"gameRegionCode\":\"DHXY_FDS\",\"playerRoleId\":null,\"phoneNumber\":null,\"qq\":\"125784515\"},\"count\":null,\"unitPrice\":null,\"detail\":\"一只浪淘沙\",\"imageUrl\":null}";
        //
        // String source = "today is monday  tomorrow is tuesday,the day after tomorrow is ?";
        //String source1 = "{\"businessCommodityCode\":\"11025487451\",\"decreaseCount\":121}";
        //String source1 = "\"this is a test authdgasgeahsahehhhhhhhhgageaeh6a4h6e54+a684e+9a84h+6e84a68h46s4a6h51d6a5h1as61h6ae1aw65h1e6ah1e6h1a65h1a61hd6s5h1a6sdh1das6h1as6h4a6w84er+98a4weg96a16g5a6d5hahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh6546631dg61sd35g1as3dg1as3d1g6ads1h35a1h351d3a1hd61ha6sh1as3\"";
        //String source1 = "{\"businessCommodityCode\":\"11025487451\",\"increaseCount\":\"1231\"}";
        //String source1 = "{\"operateTime\":1446173661613,\"operateName\":\"7881\",\"operate\":\"7881\",\"operateMessage\":\"已支付，正在处理中\",\"businessOrderCode\":\"125061914346896901131570\"}\n";
        //String source1 = "\"this is a test auth\"";
        //String source1 = "{\"businessCommodityCode\":\"647\",\"mainTitle\":\"魅影大衣\",\"secondTitle\":\"魅影大衣副标题\",\"gameCode\":\"1001\",\"gameName\":\"地下城与勇士\",\"imageUrls\":[\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/detail/A2.jpg；http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/detail/B2.jpg；http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/detail/C2.jpg\"],\"mainImageUrl\":\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/page/264.jpg\",\"detail\":\"<img alt=\"\" src=\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/1.jpg\" /><img alt=\"\" src=\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/2.jpg\" /><img alt=\"\" src=\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/3.jpg\" /><img alt=\"\" src=\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/4.jpg\" /><img alt=\"\" src=\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/5.jpg\" /><img alt=\"\" src=\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/6.jpg\" /><img alt=\"\" src=\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/7.jpg\" /><img alt=\"\" src=\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/8.jpg\" /><img alt=\"\" src=\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/9.jpg\" /><img alt=\"\" src=\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/content/10.jpg\" />\",\"style\":{\"styleName\":\"灰\",\"styleUrl\":\"http://img.dnfcity.qq.com/goodsImages/toys/items/life/waitao/945/detail/A1.jpg\"},\"costPrice\":398,\"sellingPrice\":418,\"size\":[{\"sizeName\":\"M\",\"stock\":\"16\"},{\"sizeName\":\"L\",\"stock\":\"21\"},{\"sizeName\":\"XL\",\"stock\":\"29\"},{\"sizeName\":\"XXL\",\"stock\":\"62\"}]}";
        //String source1 = "{\"businessCommodityCode\":\"752\",\"mainTitle\":\"穿越火线沙漠之鹰长袖反光T恤\",\"secondTitle\":\"新反光T恤，给你不一样的温暖！送战无不胜礼包A或B随机一份（A：红龙7天、狙击枪弹夹7天、20%经验卡7天；B：加特林机关枪7天、机枪弹夹7天）【CDK实体卡】\",\"gameCode\":\"1002\",\"gameName\":\"穿越火线\",\"imageUrls\":[\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/detail/A2.jpg\",\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/detail/B2.jpg\",\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/detail/C2.jpg\"],\"mainImageUrl\":\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/page/245.jpg\",\"detail\":\"<table width=\\\"96%\\\" border=\\\"0\\\" cellspacing=\\\"0\\\" cellpadding=\\\"0\\\">\\n\\t<tbody>\\n\\t\\t<tr>\\n\\t\\t\\t<td colspan=\\\"3\\\">\\n\\t\\t\\t\\t商品尺码\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td colspan=\\\"2\\\">\\n\\t\\t\\t\\t商品属性\\n\\t\\t\\t<\\/td>\\n\\t\\t<\\/tr>\\n\\t\\t<tr>\\n\\t\\t\\t<td width=\\\"15%\\\">\\n\\t\\t\\t\\t尺码\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td width=\\\"25%\\\">\\n\\t\\t\\t\\t型号(身高/胸围)\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td width=\\\"15%\\\">\\n\\t\\t\\t\\t胸围\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td width=\\\"15%\\\">\\n\\t\\t\\t\\t成分：\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td width=\\\"30%\\\">\\n\\t\\t\\t\\t100%棉\\n\\t\\t\\t<\\/td>\\n\\t\\t<\\/tr>\\n\\t\\t<tr>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\tM\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t170/92A\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t94\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t袖型：\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t长袖\\n\\t\\t\\t<\\/td>\\n\\t\\t<\\/tr>\\n\\t\\t<tr>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\tL\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t175/96A\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t98\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t领型：\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t圆领\\n\\t\\t\\t<\\/td>\\n\\t\\t<\\/tr>\\n\\t\\t<tr>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\tXL\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t180/100A\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t102\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t剪裁：\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t修身剪裁\\n\\t\\t\\t<\\/td>\\n\\t\\t<\\/tr>\\n\\t\\t<tr>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\tXXL\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t185/104A\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t106\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t&nbsp;\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td>\\n\\t\\t\\t\\t&nbsp;\\n\\t\\t\\t<\\/td>\\n\\t\\t<\\/tr>\\n\\t<\\/tbody>\\n<\\/table>\\n<img alt=\\\"\\\" src=\\\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/content/1.jpg\\\" /><img alt=\\\"\\\" src=\\\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/content/2.jpg\\\" /><img alt=\\\"\\\" src=\\\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/content/3.jpg\\\" /><img alt=\\\"\\\" src=\\\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/content/4.jpg\\\" /><img alt=\\\"\\\" src=\\\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/content/5.jpg\\\" /><img alt=\\\"\\\" src=\\\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/content/6.jpg\\\" /><img alt=\\\"\\\" src=\\\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/content/7.jpg\\\" /><img alt=\\\"\\\" src=\\\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/content/8.jpg\\\" /><img alt=\\\"\\\" src=\\\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/content/9.jpg\\\" /><img alt=\\\"\\\" src=\\\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/content/10.jpg\\\" /><img alt=\\\"\\\" src=\\\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/content/11.jpg\\\" />\",\"style\":{\"styleName\":\"深灰\",\"styleUrl\":\"http://img.cfmall.qq.com/goodsImages/cloth/long_tee/82/detail/A1.jpg\"},\"costPrice\":138,\"sellingPrice\":198,\"size\":[{\"sizeName\":\"M\",\"stock\":\"18\"},{\"sizeName\":\"L\",\"stock\":\"17\"}]}";
        //String source1 = "{\"tradeType\":\"js\",\"commodityTypeName\":\"游戏币\",\"count\":5,\"imageUrl\":\"http://pic.7881.com/7881/market/images/promotion/game/240-240/G33.jpg\",\"imageUrls\":[\"http://pic.7881.com/7881/market/images/promotion/game/240-240/G33.jpg\"],\"unitPrice\":100.0,\"commodityType\":\"100001\",\"commodityClassifys\":[{\"commodityClassifyCode\":\"YXQ\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"游戏区\"},{\"commodityClassifyCode\":\"G33P073\",\"parentClassifyCode\":\"YXQ\",\"commodityClassifyName\":\"双线十四区\"},{\"commodityClassifyCode\":\"G33P073003\",\"parentClassifyCode\":\"G33P073\",\"commodityClassifyName\":\"长空守护\"},{\"commodityClassifyCode\":\"WPLX\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"物品类型\"},{\"commodityClassifyCode\":\"100001\",\"parentClassifyCode\":\"WPLX\",\"commodityClassifyName\":\"游戏币\"}],\"gameName\":\"蜀门\",\"gameCode\":\"G33\",\"unitPriceAddition\":\"1金=0.1506元_1元=6.6400金\",\"tradeModeName\":\"物品会以邮寄的方式发到您的账户\",\"businessCommodityCode\":\"364409469\",\"name\":\"664金=100元\",\"tradeMode\":\"物品会以邮寄的方式发到您的账户\",\"goldCount\":\"664\",\"tradeTypeName\":\"寄售交易\",\"saleTimeSectionStart\":36000000,\"saleTimeSectionEnd\":43200000}";
        String source1 = "{\"businessOrderCode\":14214124}";

        System.out.println("原文: " + source1);
        //String key = "A1B2C3D4E5F60701";
        //一个16位的字符串
        String key = "HYGYTHGYHBNHGTYJ";
        //String key = "OcPlupB6Jq53s4A3";

        String encryptData = encrypt(source1, key);
        System.out.println("加密后: " + encryptData);
        String decryptData = decrypt(encryptData, key);
        System.out.println("解密后: " + decryptData);

        //String authCode = "vWhzPEXeQWaM/ojZANdN//TJUvKpjqvuPx8Je7RcV82D0DsnwDxxqh/nbsnykEgy5R4Ef67KPqppyEpHCPDBDq7qB3xOsQFbmno+mgWwcZGdZ1dKBm3B4jfIZW4eSNp1ytlotrfi6DdX+wa5bvzkeenVHc59Vtpf6f5X7ur+lAkLnBmvIIotMcfVQX4C4dRjWKbxgLQwLY+6uDfwFsjLvyHhcsMkq1ET\n";
       // String decryptDat1a = decrypt(authCode, "HYGYTHGYHBNHGTYJ");
       // System.out.println("解密后: " + decryptDat1a);

        //String kongge = "      ";
      //  System.out.print(kongge.replaceAll(" ", "+"));
    }

    public static void main3(String[] args) throws Exception {
        //String key = "HYGYTHGYHBNHGTYJ";
        String key = "HYGYTHGYHBNHGTYJ";//HBNHGTYJ";

        //String se="{\"tradeType\":\"js\",\"detail\":\"测试新商品\",\"commodityTypeName\":\"装备\",\"count\":1,\"imageUrl\":\"http://userimg.7881.com/goods/113267150/2015-11-02/32b931cde62d404a83b3644ce2978793.jpg\",\"imageUrls\":[\"http://userimg.7881.com/goods/113267150/2015-11-02/32b931cde62d404a83b3644ce2978793.jpg\",\"http://userimg.7881.com/goods/113267150/2015-11-02/cb076d537776444a8c940c5932d3ee53.jpg\",\"http://userimg.7881.com/goods/113267150/2015-11-02/2c071b9f3d3645bbbbdd1ddb20c2962d.jpg\"],\"unitPrice\":6.0,\"commodityType\":\"100002\",\"commodityClassifys\":[{\"commodityClassifyCode\":\"YXQ\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"游戏区\"},{\"commodityClassifyCode\":\"YXF\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"游戏服\"},{\"commodityClassifyCode\":\"G10P001\",\"parentClassifyCode\":\"YXQ\",\"commodityClassifyName\":\"广东区\"},{\"commodityClassifyCode\":\"广东1区\",\"parentClassifyCode\":\"YXF\",\"commodityClassifyName\":\"G10P001001\"},{\"commodityClassifyCode\":\"WPLX\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"物品类型\"},{\"commodityClassifyCode\":\"装备\",\"parentClassifyCode\":\"WPLX\",\"commodityClassifyName\":\"100002\"}],\"gameName\":\"地下城与勇士\",\"seller\":{\"gameRegionCode\":\"G10_G10P001_G10P001001\",\"playerRoleName\":\"大头儿子\",\"gameRegionName\":\"地下城与勇士_广东区_广东1区\",\"account\":\"testaccount\",\"qq\":\"1067714815\"},\"gameCode\":\"G10\",\"businessCommodityCode\":290001541,\"tradeModeName\":\"物品会以邮寄的方式发到您的账户\",\"name\":\"【 首饰 项链 】TestAAtest\",\"tradeMode\":\"物品会以邮寄的方式发到您的账户\",\"tradeTypeName\":\"寄售交易\"}";
        // String se= "{\"tradeType\":\"js\",\"detail\":\"测试新商品\",\"commodityTypeName\":\"装备\",\"count\":1,\"imageUrl\":\"http://userimg.7881.com/goods/113267150/2015-11-02/32b931cde62d404a83b3644ce2978793.jpg\",\"imageUrls\":[\"http://userimg.7881.com/goods/113267150/2015-11-02/32b931cde62d404a83b3644ce2978793.jpg\",\"http://userimg.7881.com/goods/113267150/2015-11-02/cb076d537776444a8c940c5932d3ee53.jpg\",\"http://userimg.7881.com/goods/113267150/2015-11-02/2c071b9f3d3645bbbbdd1ddb20c2962d.jpg\"],\"unitPrice\":6.0,\"commodityType\":\"100002\",\"commodityClassifys\":[{\"commodityClassifyCode\":\"YXQ\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"游戏区\"},{\"commodityClassifyCode\":\"YXF\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"游戏服\"},{\"commodityClassifyCode\":\"G10P001\",\"parentClassifyCode\":\"YXQ\",\"commodityClassifyName\":\"广东区\"},{\"commodityClassifyCode\":\"广东1区\",\"parentClassifyCode\":\"YXF\",\"commodityClassifyName\":\"G10P001001\"},{\"commodityClassifyCode\":\"WPLX\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"物品类型\"},{\"commodityClassifyCode\":\"装备\",\"parentClassifyCode\":\"WPLX\",\"commodityClassifyName\":\"100002\"}],\"gameName\":\"地下城与勇士\",\"seller\":{\"gameRegionCode\":\"G10_G10P001_G10P001001\",\"playerRoleName\":\"大头儿子\",\"gameRegionName\":\"地下城与勇士_广东区_广东1区\",\"account\":\"testaccount\",\"qq\":\"1067714815\"},\"gameCode\":\"G10\",\"businessCommodityCode\":290001545,\"tradeModeName\":\"物品会以邮寄的方式发到您的账户\",\"name\":\"【 首饰 项链 】TestAAtest\",\"tradeMode\":\"物品会以邮寄的方式发到您的账户\",\"tradeTypeName\":\"寄售交易\"}";
        //String se= "{\"tradeType\":\"js\",\"commodityTypeName\":\"游戏币\",\"count\":100,\"unitPrice\":100.0,\"commodityType\":\"100001\",\"commodityClassifys\":[{\"commodityClassifyCode\":\\\"YXQ\\\",\\\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"游戏区\"},{\"commodityClassifyCode\":\"YXF\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"游戏服\"},{\"commodityClassifyCode\":\"G10P006\",\"parentClassifyCode\":\"YXQ\",\"commodityClassifyName\":\"广西区\"},{\"commodityClassifyCode\":\"广西3区\",\"parentClassifyCode\":\"YXF\",\"commodityClassifyName\":\"G10P006003\"},{\"commodityClassifyCode\":\"WPLX\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"物品类型\"},{\"commodityClassifyCode\":\"游戏币\",\"parentClassifyCode\":\"WPLX\",\"commodityClassifyName\":\"100001\"}],\"gameName\":\"地下城与勇士\",\"seller\":{\"gameRegionCode\":\"G10_G10P006_G10P006003\",\"playerRoleName\":\"1\",\"gameRegionName\":\"地下城与勇士_广西区_广西3区\",\"account\":\"1\",\"playerRoleLevel\":1,\"qq\":\"1067714815\"},\"gameCode\":\"G10\",\"businessCommodityCode\":\"290001549\",\"tradeModeName\":\"物品会以邮寄的方式发到您的账户\",\"name\":\"10000万金=100.00元\",\"tradeMode\":\"物品会以邮寄的方式发到您的账户\",\"tradeTypeName\":\"寄售交易\"}";
        //String se = "{\"tradeType\":\"js\",\"commodityTypeName\":\"游戏币\",\"count\":100,\"unitPrice\":100.0,\"commodityType\":\"100001\",\"commodityClassifys\":[{\"commodityClassifyCode\":\"YXQ\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"游戏区\"},{\"commodityClassifyCode\":\"YXF\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"游戏服\"},{\"commodityClassifyCode\":\"G10P006\",\"parentClassifyCode\":\"YXQ\",\"commodityClassifyName\":\"广西区\"},{\"commodityClassifyCode\":\"广西3区\",\"parentClassifyCode\":\"YXF\",\"commodityClassifyName\":\"G10P006003\"},{\"commodityClassifyCode\":\"WPLX\",\"parentClassifyCode\":\"\",\"commodityClassifyName\":\"物品类型\"},{\"commodityClassifyCode\":\"游戏币\",\"parentClassifyCode\":\"WPLX\",\"commodityClassifyName\":\"100001\"}],\"gameName\":\"地下城与勇士\",\"seller\":{\"gameRegionCode\":\"G10_G10P006_G10P006003\",\"playerRoleName\":\"1\",\"gameRegionName\":\"地下城与勇士_广西区_广西3区\",\"account\":\"1\",\"playerRoleLevel\":1,\"qq\":\"1067714815\"},\"gameCode\":\"G10\",\"businessCommodityCode\":\"290001599\",\"tradeModeName\":\"物品会以邮寄的方式发到您的账户\",\"name\":\"10000万金=100.00元\",\"tradeMode\":\"物品会以邮寄的方式发到您的账户\",\"tradeTypeName\":\"寄售交易\", \"goldCount\":10000,\"unitPriceAddition\":\"1金=0.00001元\"}";
        String se = "{\"businessOrderCode\":14214124}";
        String encryptData = encrypt(se, key);

        System.out.println(encryptData);

        //String deCrypData = decrypt("AyYNh2gYzb4lxsN+9RwoQZ8eTeNyWv3ygo+nKedr3goccykhmUqWN1pNpFRFyqjqIiFRN4+nyO4U3ChpM8gzNrSHygGG1SXUNdC6UTGiNd/QBf4bFmvkKpvgkvJQJaXJrwAcWhOJLg9pg+hbQfzxZ4nMyU0Wj/79Q64RAkghlXrQBf4bFmvkKo9zZ3EK9e3vofCPrdW3bNA+Z26vwvVqo+def5Go2+o+LI2XJqscgCWM/kQSHJio/NjyAUESjiE+42nCKd8Tr6AccykhmUqWN1Kw+uWDskyRPoIstNxK4fl+u3rRj0AzSvUaZjOVkFm1TSfzXdzY4Jhgd2CRwxEIIkmvSXrIEVzrfjgOtemS4KifHk3jclr98tk7LXA2VTMP2VsAMRSqy3vQMEO3h0BMXPMN9DR6EW3Y0AX+GxZr5CqPc2dxCvXt76Hwj63Vt2zQf9eBq0TW+Itgd2CRwxEIIkmvSXrIEVzrfjgOtemS4Kh2NITFfuN6mU1n9qpGgjYk4np8fwaFQq8ZsQ9tnbFCFMvizyxUQyJ/313LsWPhDiP+LOY11Q9MoiyNlyarHIAlDhkU/GeUE59UE9Or4wovMJPmNulGweb9OanGYT1otiMXg94dKJ5qkfEx9cKE1VWT/izmNdUPTKKarorRhgGq7SgLy29b5+jdAX0WWZoHxsSlRQRaCv+ckdk7LXA2VTMPR2x2YfEEBXgoAX8bersTopMOV8sxobaa42nCKd8Tr6DrKNCZgvSnd2mD6FtB/PFniczJTRaP/v2ueb+I2pnnF8HqrRwqrl/YMGZji2fUOSOlRQRaCv+ckdk7LXA2VTMPR2x2YfEEBXjQMEO3h0BMXMGJpiFEcehz515/kajb6j4sjZcmqxyAJTUCcN5+yMkinx5N43Ja/fLZOy1wNlUzD9lbADEUqst7O6RDuTOMcGHYflLC8/FPEY7SoRF8qswBDwaLJu7e5ctDqwHstRj109PPBnhpclvheQVdFciWLDeXnRClLSTyNNnFbFJ2m6xdsJR/yIvdGRSr64Bea0w5TyZcbF0eruWurtpyfnopmqrz0vWp7B3v92CDt0nydeQs1j1PEXaoHS5NP6MaE05aJ0sPh0z4ZljOmNOcOXWUvq53YBubBq6iRySDvvOKP/KmMtxyoaIHIG6ERrMRkM4SuytjwW96f4RotdsPjOHzIKdsy82ULG5Siia/tgyDqwNPBFtH8Ep7esZ1eSQaA5YkkVevWXoTxp2yUUbCj2wFX4R0BGgmXzuyv/4A5A97f8SNfjgOtemS4KiFY+beIG796e24tIOpoLcAjp7vH9N5Sv6bM7Vt1hKQ5RUSSqpIsmj4nTnwPfOwOq8IP8JnFODQVhvGF09udm17bz6U8NQOAW7sVdSezjoG7TukQ7kzjHBhaNMGZkGp1jNTpqpZ29hturugy/VpgyFLVs9MF+NQvIuJ0Gw4qALvGmBK8SjCpv090cKxqC5+EvV6fiQ+a+qmQa4OEVNQjLkXpJ1y3c0kiV2oIOWTjZKMNSqiPJYZel81y38h+Cxb7aXTK7naRA4DcA==", key);
        //System.out.println(deCrypData);
    }


/*
    public static void main1String[] args) throws Exception {
        System.out.println(encrypt("[{\"businessCommodityCode\":\"abcd\"},{\"businessCommodityCode\":\"abcd\"}]", "HYGYTHGYHBNHGTYH"));
    }
*/

}
