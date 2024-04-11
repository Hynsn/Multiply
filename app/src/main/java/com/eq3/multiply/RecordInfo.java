package com.eq3.multiply;

public class RecordInfo {
    private int recordId; // 记录编码
    private int sum; // 总数
    private int grade;
    private int rightSum; // 正确数
    private int level; // 等级
    private int totalTime;// 耗时

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public RecordInfo(int recordId, int sum, int grade, int level, int totalTime) {
        this.recordId = recordId;
        this.sum = sum;
        this.grade = grade;
        this.level = level;
        this.totalTime = totalTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getRightSum() {
        return rightSum;
    }

    public void setRightSum(int rightSum) {
        this.rightSum = rightSum;
    }
}
