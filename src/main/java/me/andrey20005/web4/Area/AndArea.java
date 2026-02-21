package me.andrey20005.web4.Area;


import me.andrey20005.web4.Entity.Point;

import java.util.Collection;

public record AndArea(Collection<Area> areas) implements Area {

    public void addArea(Area area) {
        areas.add(area);
    }

    @Override
    public boolean hit(Point point) {
        return areas.stream().allMatch(area -> area.hit(point));
    }
}
