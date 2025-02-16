package main.java.com.pws.dryadengine.physics;

import java.util.ArrayList;
import java.util.List;

import main.java.com.pws.dryadengine.types.Vector2;

public class QuadTree {
    private static final int MAX_OBJECTS = 10;
    private static final int MAX_LEVELS = 5;
    
    private int level;
    private List<Rect2DCollider> objects;
    private Vector2 min;  // Top-left corner
    private Vector2 max;  // Bottom-right corner
    private QuadTree[] nodes;
    
    public QuadTree(int level, Vector2 min, Vector2 max) {
        this.level = level;
        this.objects = new ArrayList<>();
        this.min = min;
        this.max = max;
        this.nodes = new QuadTree[4];
    }
    
    private void split() {
        float midX = (min.x + max.x) / 2;
        float midY = (min.y + max.y) / 2;
        
        nodes[0] = new QuadTree(level + 1, 
            new Vector2(min.x, min.y),
            new Vector2(midX, midY));
            
        nodes[1] = new QuadTree(level + 1,
            new Vector2(midX, min.y),
            new Vector2(max.x, midY));
            
        nodes[2] = new QuadTree(level + 1,
            new Vector2(min.x, midY),
            new Vector2(midX, max.y));
            
        nodes[3] = new QuadTree(level + 1,
            new Vector2(midX, midY),
            new Vector2(max.x, max.y));
    }
    
    public void clear() {
        objects.clear();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }
    
    public void insert(Rect2DCollider collider) {
        if (nodes[0] != null) {
            int index = getIndex(collider);
            if (index != -1) {
                nodes[index].insert(collider);
                return;
            }
        }
        
        objects.add(collider);
        
        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            split();
        }
    }
    
    public List<Rect2DCollider> retrieve(List<Rect2DCollider> returnObjects, Rect2DCollider collider) {
        int index = getIndex(collider);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, collider);
        }
        
        returnObjects.addAll(objects);
        return returnObjects;
    }

    private int getIndex(Rect2DCollider collider) {
        float midX = (min.x + max.x) / 2;
        float midY = (min.y + max.y) / 2;
        
        Vector2 pos = collider.getGlobalPosition().toVector2();
        Vector2 size = collider.getGlobalScale().toVector2();
        
        // Determine which quadrant the object belongs to
        boolean topQuadrant = (pos.y <= midY && pos.y + size.y <= midY);
        boolean bottomQuadrant = (pos.y >= midY);
        boolean leftQuadrant = (pos.x <= midX && pos.x + size.x <= midX);
        boolean rightQuadrant = (pos.x >= midX);
        
        if (leftQuadrant) {
            if (topQuadrant) return 0;
            if (bottomQuadrant) return 2;
        }
        else if (rightQuadrant) {
            if (topQuadrant) return 1;
            if (bottomQuadrant) return 3;
        }
        
        return -1; // Object spans multiple quadrants
    }
    
}
