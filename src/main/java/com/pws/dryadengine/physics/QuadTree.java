package main.java.com.pws.dryadengine.physics;

import java.util.ArrayList;
import java.util.List;

import main.java.com.pws.dryadengine.types.Rect2D;

public class QuadTree {
    private static final int MAX_OBJECTS = 4;
    private static final int MAX_LEVELS = 5;

    private int level;
    private List<Rect2DCollider> objects;
    private float x, y, width, height;
    private QuadTree[] nodes;

    public QuadTree(int level, float x, float y, float width, float height) {
        this.level = level;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.objects = new ArrayList<>();
        this.nodes = new QuadTree[4];
    }

    public void clear() {
        objects.clear();
        for (int i = 0; i < 4; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    private void split() {
        float subWidth = width / 2;
        float subHeight = height / 2;
        float xMid = x + subWidth;
        float yMid = y + subHeight;

        nodes[0] = new QuadTree(level + 1, xMid, y, subWidth, subHeight);
        nodes[1] = new QuadTree(level + 1, x, y, subWidth, subHeight);
        nodes[2] = new QuadTree(level + 1, x, yMid, subWidth, subHeight);
        nodes[3] = new QuadTree(level + 1, xMid, yMid, subWidth, subHeight);
    }

    private int getIndex(Rect2DCollider collider) {
        Rect2D rect = collider.getRect();
        boolean top = rect.position.y < y + height / 2;
        boolean bottom = rect.position.y + rect.scale.y > y + height / 2;
        boolean left = rect.position.x < x + width / 2;
        boolean right = rect.position.x + rect.scale.x > x + width / 2;

        if (top && right) return 0;
        if (top && left) return 1;
        if (bottom && left) return 2;
        if (bottom && right) return 3;
        return -1;
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
            if (nodes[0] == null) {
                split();
            }
            int i = 0;
            while (i < objects.size()) {
                int index = getIndex(objects.get(i));
                if (index != -1) {
                    nodes[index].insert(objects.remove(i));
                } else {
                    i++;
                }
            }
        }
    }

    public void remove(Rect2DCollider collider) {
        objects.remove(collider);
        if (nodes[0] != null) {
            int index = getIndex(collider);
            if (index != -1) {
                nodes[index].remove(collider);
            }
        }
    }

    public List<Rect2DCollider> retrieve(Rect2DCollider collider) {
        List<Rect2DCollider> returnObjects = new ArrayList<>(objects);
        if (nodes[0] != null) {
            int index = getIndex(collider);
            if (index != -1) {
                returnObjects.addAll(nodes[index].retrieve(collider));
            }
        }
        return returnObjects;
    }
}