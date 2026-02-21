package me.andrey20005.web4.service;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import me.andrey20005.web4.Area.*;
import me.andrey20005.web4.Entity.Point;
import me.andrey20005.web4.repository.PointRepository;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class PointService {

    private static final Area area;
    static {
        AndArea quarterCircle = new AndArea(new ArrayList<>());
        quarterCircle.addArea(new AboveLine(0, 0, 1, 0));
        quarterCircle.addArea(new AboveLine(0, 0, 0, 1));
        quarterCircle.addArea(new Circle(1, 0, 0));
        AndArea lowerTriangle = new AndArea(new ArrayList<>());
        lowerTriangle.addArea(new AboveLine(0, 0, 1, 0));
        lowerTriangle.addArea(new AboveLine(0, 0, 0, -1));
        lowerTriangle.addArea(new AboveLine(0, -0.5, -1, 1));
        AndArea rectangle = new AndArea(new ArrayList<>());
        rectangle.addArea(new AboveLine(0, 0, -1, 0));
        rectangle.addArea(new AboveLine(0, 0, 0, -1));
        rectangle.addArea(new AboveLine(-1, -1, 1, 0));
        rectangle.addArea(new AboveLine(-1, -1, 0, 1));
        OrArea orArea = new OrArea(new ArrayList<>());
        orArea.addArea(quarterCircle);
        orArea.addArea(lowerTriangle);
        orArea.addArea(rectangle);
        area = orArea;
    }

    @Inject
    private PointRepository pointRepository;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Point> findAll() {
        return pointRepository.findAll();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Point findById(Long id) {
        Point point = pointRepository.findById(id);
        if (point == null) {
            throw new WebApplicationException(404);
        }
        return point;
    }

    private void applyArea(Point point) {
        // Валидация
        if (point.getX() < -4 || point.getX() > 4) {
            throw new WebApplicationException("X должен быть от -4 до 4", 400);
        }
        if (point.getY() < -3 || point.getY() > 3) {
            throw new WebApplicationException("Y должен быть от -3 до 3", 400);
        }
        if (point.getR() < -4 || point.getR() > 4) {
            throw new WebApplicationException("R должен быть от -4 до 4", 400);
        }

        point.setHit(area.hit(point));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Point create(Point point) {
        applyArea(point);
        return pointRepository.create(point);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Point update(Long id, Point point) {
        applyArea(point);
        Point updated = pointRepository.update(id, point);
        if (updated == null) {
            throw new WebApplicationException(404);
        }
        return updated;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Long id) {
        pointRepository.delete(id);
    }
}