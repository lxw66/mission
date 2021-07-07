package com.lxw.website;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author LXW
 * @date 2021年05月10日 16:24
 */
public class Images {

    public static void main(String[] args) throws IOException {
        /*String backImgUrl="C:\\Users\\86188\\Desktop\\1.jpg";
        String seImgUrl="C:\\Users\\86188\\Desktop\\2.jpg";
        String outUrl="C:\\Users\\86188\\Desktop\\3.jpg";
        String code1="测试1";
        String code2="测试2";
        overLoadImage(backImgUrl,seImgUrl,code1,code2,outUrl);*/
        //reSizeImage( getBufferedImage(backImgUrl),outUrl);
        /*String pic="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAZwAAAGfCAYAAABr1WSXAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAP+lSURBVHhe7J0FmBzHubW1qwWxLMvMFDPKELq5ublhTpw4TE5ijBlkENOymJnRkiyyLMliWQy2ZUtm2RbDanl4Zs9/TvX0bM9s72oFpv/2Ps95qnmot94+VV991Qjen/fn/Xl/3p/39xn8NWpcWI6EiqjeFXUqnfvrlY45piqpKspnlNanEo36VCSU1reK2/wsJe13LFON+lShEa+h0qx78uTJk6cvh5KA86lDx4ZNslyhwzeXChwDm1TZH8STJ0+ePH2xVQs4cQkeBjDUqYOOO3C0Pc0BHEs1LsYpDziePHny9CWVG2yMCI66gGO223DhsemFZQ0Ejq2GOR1BJxU4rtDxwOPJkydPX3y5wkYiONxAY6te4Eg6pl7Vhk5t4KQ0rznkAceTJ0+evmRyhY1ThIcbcKRakEmVjqlTycBJIzTSzHLt5rUGQUfrnjx58uTpiytXyDhFcLjBxilX2NjS/jplQacGNhZwtO+EoOOBx5MnT56+uHKFTIoEDjfQOFULNE5pvxGX";
        BASE64Decoder decoder=new BASE64Decoder();
        byte[] bytes;
        try {
            bytes = decoder.decodeBuffer(pic);
            File file=new File("E:/MYTESTPIC.png");
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        String str="hello";
        String ecode=Images.getEncode(str);
    }

    public static final String[] ENCODES = new String[]{"UTF-8", "GBK", "GB2312", "ISO-8859-1", "ISO-8859-2"};

    /**
     * 获取字符串是什么编码，例如返回的值有：UTF-8，GBK，ISO-8859-1等
     *
     * @param str
     * @return
     */
    public static String getEncode(String str) {
        byte[] data = str.getBytes();
        byte[] b = null;
        a:
        for (int i = 0; i < ENCODES.length; i++) {
            try {
                b = str.getBytes(ENCODES[i]);
                if (b.length != data.length) {
                    continue;
                }
                for (int j = 0; j < b.length; j++) {
                    if (b[j] != data[j]) {
                        continue a;
                    }
                }
                return ENCODES[i];
            } catch (UnsupportedEncodingException e) {
                continue;
            }
        }
        return null;
    }


    public static String overLoadImage(String backImgUrl,String seImgUrl,String code1,String code2,String outUrl) throws IOException {
        BufferedImage backBufferImage=reSizeImage(1000,618, ImageIO.read(new File(backImgUrl)));
        BufferedImage secBufferImage=reSizeImage(500,320, ImageIO.read(new File(seImgUrl)));

        Graphics2D g=backBufferImage.createGraphics();
        //g.setColor(Color.white);
        g.setFont(new Font("微软雅黑",Font.BOLD,20));
        g.setColor(Color.black);
        g.drawString(code1,200,100);
        g.drawString(code2,200,120);
        g.drawImage(secBufferImage,500,240,secBufferImage.getWidth(),secBufferImage.getHeight(),null);
        g.dispose();
        ImageIO.write(backBufferImage,"jpg",new File(outUrl));

        return backImgUrl;

    }


    /**
     * 
     * @author LXW    重新给图片设置大小
     * @date 2021/5/10 16:30
     * @param x
     * @param y
     * @param image 
     * @return java.awt.image.BufferedImage
     */
    public static BufferedImage reSizeImage(int x,int y,BufferedImage image){
        BufferedImage bufferedImage=new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image.getScaledInstance(x,y,BufferedImage.SCALE_SMOOTH),0,0,null);
        return  bufferedImage;
    }


    /**
     *
     * @author LXW    重新给图片设置大小
     * @date 2021/5/10 16:30
     * @param
     * @param
     * @param image
     * @return java.awt.image.BufferedImage
     */
    public static void reSizeImage(BufferedImage image,String backUrl) throws IOException {
        BufferedImage bufferedImage=new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image.getScaledInstance(image.getWidth(), image.getHeight(),BufferedImage.SCALE_SMOOTH),0,0,null);
        ImageIO.write(bufferedImage,"jpg",new File(backUrl));
    }


    public static  BufferedImage getBufferedImage(String url) throws IOException {
        File file=new File(url);
        return  ImageIO.read(file);

    }
}
