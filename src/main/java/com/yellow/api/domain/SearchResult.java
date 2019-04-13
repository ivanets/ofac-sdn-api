package com.yellow.api.domain;

public class SearchResult {
    private String sdnRevisionDate;
    private int score;
    private Sanction sanction;



    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Sanction getSanction() {
        return sanction;
    }

    public void setSanction(Sanction sanction) {
        this.sanction = sanction;
    }


    public String getSdnRevisionDate() {
        return sdnRevisionDate;
    }

    public void setSdnRevisionDate(String sdnRevisionDate) {
        this.sdnRevisionDate = sdnRevisionDate;
    }
}
