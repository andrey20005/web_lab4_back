package me.andrey20005.web4.Area;


import me.andrey20005.web4.Entity.Point;

public record Circle(double radius, double x, double y) implements Area {

    @Override
    public boolean hit(Point point) {
        return Math.pow(point.getX()/point.getR() - x, 2) + Math.pow(point.getX()/point.getR() - y, 2) <= Math.pow(radius, 2);
    }
}
