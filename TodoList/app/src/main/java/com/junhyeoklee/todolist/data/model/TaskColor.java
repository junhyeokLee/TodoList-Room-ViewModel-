package com.junhyeoklee.todolist.data.model;

public class TaskColor {

    String ColorName;

    int ColorBackGround  = 0;

    String TextColorName;

    int TextColorBackGround;
    public TaskColor(){}


    public TaskColor(String ColorName, int ColorBackGround){
        this.ColorName = ColorName;
        this.ColorBackGround = ColorBackGround;
        this.TextColorName = ColorName;
        this.TextColorBackGround = ColorBackGround;
    }

    public String getColorName() {
        return ColorName;
    }

    public void setColorName(String colorName) {
        ColorName = colorName;
    }

    public int getColorBackGround() {
        return ColorBackGround;
    }

    public void setColorBackGround(int colorBackGround) {
        ColorBackGround = colorBackGround;
    }

    public String getTextColorName() {
        return TextColorName;
    }

    public void setTextColorName(String textColorName) {
        TextColorName = textColorName;
    }

    public int getTextColorBackGround() {
        return TextColorBackGround;
    }

    public void setTextColorBackGround(int textColorBackGround) {
        TextColorBackGround = textColorBackGround;
    }
}
