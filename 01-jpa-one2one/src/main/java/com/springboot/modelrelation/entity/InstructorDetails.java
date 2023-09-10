package com.springboot.modelrelation.entity;

import jakarta.persistence.*;

@Entity
@Table(name="instructor_detail")
public class InstructorDetails {

    /* -- Fields -- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="instructor_detail_id")
    private int instructor_id;

    @Column(name = "youtube_channel")
    private String youtube_channel;

    @Column(name="hobby")
    private String hobby;

    /* -- Constructors -- */
    public InstructorDetails() {}
    public InstructorDetails(String youtube_channel, String hobby) {
        this.youtube_channel = youtube_channel;
        this.hobby = hobby;
    }

    /* -- Getters / Setters -- */
    public Integer getInstructor_id() {
        return instructor_id;
    }
    public void setInstructor_id(Integer instructor_id) {
        this.instructor_id = instructor_id;
    }

    public String getYoutube_channel() {
        return youtube_channel;
    }
    public void setYoutube_channel(String youtube_channel) {
        this.youtube_channel = youtube_channel;
    }

    public String getHobby() {
        return hobby;
    }
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /* -- toString -- */
    @Override
    public String toString() {
        return "InstructorDetails{" +
                "instructor_id=" + instructor_id +
                ", youtube_channel='" + youtube_channel + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}
