package com.eq3.multiply;

public class QuesInfo {
    private int quesId; // 编号
    private String text; // 内容
    private int countTime; // 计时
    private boolean result; // 结果

    public QuesInfo(int quesId, String text, int countTime, boolean result) {
        this.quesId = quesId;
        this.text = text;
        this.countTime = countTime;
        this.result = result;
    }

    public QuesInfo(String text, int countTime, boolean result) {
        this.text = text;
        this.countTime = countTime;
        this.result = result;
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
