package com.eq3.multiply;

public class QuesDetail {
    public static final String GAME_NUM = "game_num";
    public static final String QUES_ID = "ques_id";
    public static final String TEXT = "text";
    public static final String COUNT_TIME = "count_time";
    public static final String RESULT = "res";

    private int gameNum; // 游戏次数
    private int quesId; // 编号
    private String text; // 内容
    private int countTime; // 计时
    private boolean result; // 结果

    public QuesDetail(int gameNum, String text, int countTime, boolean result) {
        this.gameNum = gameNum;
        this.text = text;
        this.countTime = countTime;
        this.result = result;
    }

    public int getGameNum() {
        return gameNum;
    }

    public void setGameNum(int gameNum) {
        this.gameNum = gameNum;
    }

    public int getQuesId() {
        return quesId;
    }

    public void setQuesId(int quesId) {
        this.quesId = quesId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCountTime() {
        return countTime;
    }

    public void setCountTime(int countTime) {
        this.countTime = countTime;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
