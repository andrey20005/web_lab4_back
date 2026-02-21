package me.andrey20005.web4.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.andrey20005.web4.Entity.Point;
import java.util.List;

@Stateless
public class PointRepository {

    @PersistenceContext(unitName = "web4-pu")
    private EntityManager em;

    public List<Point> findAll() {
        return em.createQuery("SELECT p FROM Point p ORDER BY p.createdAt DESC", Point.class)
                .getResultList();
    }

    public Point findById(Long id) {
        return em.find(Point.class, id);
    }

    public Point create(Point point) {
        em.persist(point);
        return point;
    }

    public Point update(Long id, Point point) {
        Point existing = em.find(Point.class, id);
        if (existing == null) {
            return null;
        }
        existing.setX(point.getX());
        existing.setY(point.getY());
        existing.setR(point.getR());
        existing.setHit(point.isHit());
        return em.merge(existing);
    }

    public void delete(Long id) {
        Point point = em.find(Point.class, id);
        if (point != null) {
            em.remove(point);
        }
    }
}
