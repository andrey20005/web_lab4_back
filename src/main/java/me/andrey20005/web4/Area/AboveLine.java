package me.andrey20005.web4.Area;


import me.andrey20005.web4.Entity.Point;

public record AboveLine(double centerX, double centerY, double normX, double normY) implements Area {
    @Override
    public boolean hit(Point point) {
        return (point.getX()/point.getR() - centerX)*normX + (point.getY()/point.getR() - centerY)*normY >= 0;
    }
}
