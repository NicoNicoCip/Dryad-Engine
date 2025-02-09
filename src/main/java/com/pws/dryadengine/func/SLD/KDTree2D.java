package main.java.com.pws.dryadengine.func.SLD;

import java.util.ArrayList;
import java.util.List;

import main.java.com.pws.dryadengine.types.Rect2DCollider;

public class KDTree2D {
    private KDNode root;

    private static class KDNode {
        Rect2DCollider collider;
        KDNode left, right;
        boolean vertical; // True = split by x, False = split by y

        KDNode(Rect2DCollider collider, boolean vertical) {
            this.collider = collider;
            this.vertical = vertical;
        }
    }

    public void buildTree(List<Rect2DCollider> objects) {
        root = build(objects, 0);
    }

    private KDNode build(List<Rect2DCollider> objects, int depth) {
        if (objects.isEmpty()) return null;
    
        boolean vertical = (depth % 2 == 0);
        objects.sort((a, b) -> vertical ? Float.compare(a.position.x, b.position.x) : Float.compare(a.position.y, b.position.y));
    
        int median = objects.size() / 2;
        Rect2DCollider medianObject = objects.get(median);
        //Debug.println("Splitting at: " + (vertical ? "X=" + medianObject.position.x : "Y=" + medianObject.position.y));
    
        KDNode node = new KDNode(medianObject, vertical);
    
        node.left = build(objects.subList(0, median), depth + 1);
        node.right = build(objects.subList(median + 1, objects.size()), depth + 1);
    
        return node;
    }
    

    public List<Rect2DCollider> query(Rect2DCollider target) {
        List<Rect2DCollider> result = new ArrayList<>();
        query(root, target, result);
        /*
        Debug.println("Query for: " + target + " found " + result.size() + " objects.");
        
        for (Rect2DCollider collider : result) {
            Debug.println("  Nearby: " + collider);
        }
         */

        return result;
    }
    

    private void query(KDNode node, Rect2DCollider target, List<Rect2DCollider> result) {
        if (node == null) return;

        // Add if within bounds
        if (node.collider != target && node.collider.checkCollision(target)) {
            result.add(node.collider);
        }

        // Check left or right subtree
        if (node.vertical) {
            if (target.position.x < node.collider.position.x) {
                query(node.left, target, result);
            } else {
                query(node.right, target, result);
            }
        } else {
            if (target.position.y < node.collider.position.y) {
                query(node.left, target, result);
            } else {
                query(node.right, target, result);
            }
        }
    }
}
