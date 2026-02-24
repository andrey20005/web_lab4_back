package me.andrey20005.web4.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import me.andrey20005.web4.Area.Area;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "points")
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private double x;

    @Column(nullable = false)
    private double y;

    @Column(nullable = false)
    private double r;

    @Column(nullable = false)
    private boolean hit = false;

    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt = LocalDateTime.now();

    @JoinColumn(name = "userId", nullable = false)
    private Long userId;

    public Point() {}

    public Point(double x, double y, double r, Area area) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = area.hit(this);
    }

    public Point(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Point(double x, double y, double r, boolean hit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getR() { return r; }
    public void setR(double r) { this.r = r; }

    public boolean isHit() { return hit; }
    public void setHit(boolean hit) { this.hit = hit; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime time) { this.createdAt = time; }

    public Long getUserId() { return userId; }
    public void setUserId(Long user) { this.userId = user; }

    @Override
    public String toString() {
        return "p: x = " + this.getX() + ", y = " + this.getY() + ", r = " + this.getR() + ", hit = " + this.isHit() + ", time = " + this.getCreatedAt() + ", userName = " + this.getUserId();
    }
}
