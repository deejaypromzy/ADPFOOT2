package com.projectwork.adp.adpfoot;

class Laws_Database  {
    String title;
    String sub_title;
    String img;
    String audio;
    String video;
    String detail;
    int law_no;

    public Laws_Database() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getLaw_no() {
        return law_no;
    }

    public void setLaw_no(Integer law_no) {
        this.law_no = law_no;
    }


    public Laws_Database(String title, String sub_title, String img, String audio, String video, String detail, Integer law_no) {
        this.title = title;
        this.sub_title = sub_title;
        this.img = img;
        this.audio = audio;
        this.video = video;
        this.detail = detail;
        this.law_no = law_no;
    }
}
