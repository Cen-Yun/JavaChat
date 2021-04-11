package JFrame;

import java.awt.*;

public class GBC extends GridBagConstraints {

    //本类只用于创建窗体的工具
    public  GBC(int gridx,int gridy){
        this.gridx=gridx;
        this.gridy=gridy;
    }
    public  GBC(int gridx,int gridy,int gridwidth,int gridheight){
        this.gridx=gridx;
        this.gridy=gridy;
        this.gridwidth=gridwidth;
        this.gridheight=gridheight;
    }
    public GBC setAnchor(int anchor){
        this.anchor=anchor;
        return this;
    }
    public GBC setFill(int fill){
        this.fill=fill;
        return this;
    }
    public GBC setWeight(double weightx,double weighty){
        this.weightx=weightx;
        this.weighty=weighty;
        return this;
    }
    public GBC setInsets(int distance){
        this.insets=new Insets(distance,distance,distance,distance);
        return this;
    }
    public GBC setInsets(int top,int left,int bottom,int right){
        this.insets=new Insets(top,left,bottom,right);
        return this;
    }
    public GBC setIpad(int x,int y){
        this.ipadx=x;
        this.ipady=y;
        return this;
    }

    public static void main(String[] args) {
        String str="@张三*你好，我是张三";
        int i = str.indexOf("*");
        String substring = str.substring(i+1);
        System.out.println("indexOf(*)的位置"+i);
        System.out.println("substring找出的字符串"+substring);
    }
}
