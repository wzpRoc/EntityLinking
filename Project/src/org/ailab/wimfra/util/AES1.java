package org.ailab.wimfra.util;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Security;

/**
 * Created with IntelliJ IDEA.
 * User: samsung
 * Date: 14-6-16
 * Time: 下午2:10
 */
public class AES1 {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        byte[] input = "“长亭外，古道边，芳草碧连天……”中国废除科举后兴办新式学堂，就有了以国外现成乐曲填上中文词的“学堂歌”，在华夏大地传唱甚广，可算是中国流行音乐的“前传”。\\n真正的中国流行音乐，于20世纪20年代发源于上海，是在中国传统民间小调和地方戏曲的基础上，吸收了美国爵士乐、百老汇歌舞剧等西方流行音乐元素而发展起来的。其实，从诞生初期到70年代，尽管经历了政权更迭，但在两岸三地一直延续着由上海发源的流行音乐风格——以“大上海时期”风格为主，一般都是柔情委婉、轻盈曼妙，即便是轻快的歌曲，其根源亦是“大上海”。\\n按地域划分，中国流行音乐大致可分为三大板块，分别为内地、香港、台湾；按时间划分，大致可分为五个时期：第一个时期为“大上海时期”，主要是指1920年代到1940年代的“大上海流行音乐”；第二个时期为“香港时期”，主要是指新中国成立后，上海流行音乐南迁香港开始，直到1960年代中期台湾流行歌曲的崛起，香港国语歌曲衰退；第三个时期为“台湾时期”，主要是指1960年代中期开始以姚苏蓉等人为代表的台湾流行歌曲的崛起到台湾“民歌运动”的爆发；第四个时期为“港台歌曲迅速发展时期”，主要是指1970年代中期香港“粤语歌曲”的兴起和台湾“民歌运动”的爆发，港台流行音乐得到迅猛发展，并对内地流行音乐产生重要影响；第五个时期为“内地和港台流行音乐同步发展时期”，主要是指1970年代末开始，内地流行音乐的复苏及发展，以及港台歌曲对内地流行音乐的重要影响。 \\n1927年中国流行音乐创始人黎锦晖先生在上海创作了第一首流行歌曲《毛毛雨》，标志着中国流行音乐的开端。总体来说，中国流行音乐在不同时期，皆受国外的影响，多数是对国外流行音乐，经过借鉴、改良后为己所用；中国历史悠久的文化传统，既是骄傲，也是流行音乐发展的“枷锁”，使得中国的音乐家的涌现数量和对音乐的创新精神，远不如西方世界；最重要的是，在中国流行音乐发展的九十余年里，中国大地的社会比较动荡，经济发展落后，更不利于流行音乐的发展。\\n近年中国两岸三地政通人和，社会和经济发展平稳，尤其内地的经济迅猛发展，让文化产业百花齐放，包括流行音乐领域，逐步与世界流行音乐接轨；另外，技术的日新月异，流行音乐有了以广播、电视、互联网为主流的多种传播渠道，让更多的人可以参与、享受和传播流行音乐。\\n"
                .getBytes();
        byte[] keyBytes = new byte[] { 0x2b, 0x7e, 0x15, 0x16, 0x28, (byte) 0xae, (byte) 0xd2, (byte) 0xa6,
                (byte) 0xab, (byte) 0xf7, 0x15, (byte) 0x88, 0x09, (byte) 0xcf, 0x4f, 0x3c };
        byte[] ivBytes = new byte[] { (byte) 0xf0 , (byte) 0xf1 , (byte) 0xf2 , (byte) 0xf3 , (byte) 0xf4 , (byte) 0xf5 , (byte) 0xf6 , (byte) 0xf7,
                (byte) 0xf8 , (byte) 0xf9 , (byte) 0xfa , (byte) 0xfb , (byte) 0xfc , (byte) 0xfd , (byte) 0xfe , (byte) 0xff };

        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
        System.out.println("input : " + new String(input));

        // encryption pass
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        ByteArrayInputStream bIn = new ByteArrayInputStream(input);
        CipherInputStream cIn = new CipherInputStream(bIn, cipher);
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();

        int ch;
        while ((ch = cIn.read()) >= 0) {
            bOut.write(ch);
        }

        byte[] cipherText = bOut.toByteArray();

        System.out.println("cipher: " + new String(cipherText));
        FileUtil.write("d:\\cipher.txt", cipherText);

        // decryption pass
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        bOut = new ByteArrayOutputStream();
        CipherOutputStream cOut = new CipherOutputStream(bOut, cipher);
        cOut.write(cipherText);
        cOut.close();
        byte[] plainBytes = bOut.toByteArray();
        System.out.println("plain : " + new String(plainBytes));
        FileUtil.write("d:\\plain.txt", plainBytes);
    }
}
