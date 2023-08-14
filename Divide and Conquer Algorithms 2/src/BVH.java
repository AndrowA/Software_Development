import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class BVH implements Iterable<Circle>{
    Box boundingBox;

    BVH child1;
    BVH child2;

    Circle containedCircle;

    // todo for students
    public BVH(ArrayList<Circle> circles) {

        this.boundingBox = buildTightBoundingBox(circles);

        if (circles.size() > 1){
            ArrayList<Circle>[] children = split(circles, boundingBox);
            this.child1 = new BVH(children[0]);
            this.child2 = new BVH(children[1]);
        }
        if (circles.size() == 1){
            this.containedCircle = circles.get(0);
        }

    }

    public void draw(Graphics2D g2) {
        this.boundingBox.draw(g2);
        if (this.child1 != null) {
            this.child1.draw(g2);
        }
        if (this.child2 != null) {
            this.child2.draw(g2);
        }
    }

    // todo for students
    public static ArrayList[] split(ArrayList<Circle> circles, Box boundingBox) {
        double longestAxis = Math.max(boundingBox.getWidth(), boundingBox.getHeight());

        ArrayList<Circle> beforeMid = new ArrayList<>();
        ArrayList<Circle> afterMid = new ArrayList<>();

        if (boundingBox.getWidth() == boundingBox.getHeight()){
            longestAxis = boundingBox.getWidth();
        }

        if (longestAxis == boundingBox.getWidth()){
            for (Circle circle:circles){
                if (circle.getPosition().x <= boundingBox.getMidX()){
                    beforeMid.add(circle);
                }
                else if (circle.getPosition().x > boundingBox.getMidX()){
                    afterMid.add(circle);
                }
            }
        }

        else if (longestAxis == boundingBox.getHeight()){
            for (Circle circle: circles){
                if (circle.getPosition().y <= boundingBox.getMidY()){
                    beforeMid.add(circle);
                }
                else if (circle.getPosition().y > boundingBox.getMidY()){
                    afterMid.add(circle);
                }
            }
        }

        return new ArrayList[]{beforeMid,afterMid};
    }

    // returns the smallest possible box which fully encloses every circle in circles
    public static Box buildTightBoundingBox(ArrayList<Circle> circles) {
        Vector2 bottomLeft = new Vector2(Float.POSITIVE_INFINITY);
        Vector2 topRight = new Vector2(Float.NEGATIVE_INFINITY);

        for (Circle c : circles) {
            bottomLeft = Vector2.min(bottomLeft, c.getBoundingBox().bottomLeft);
            topRight = Vector2.max(topRight, c.getBoundingBox().topRight);
        }

        return new Box(bottomLeft, topRight);
    }

    // METHODS BELOW RELATED TO ITERATOR

    // todo for students
    @Override
    public Iterator<Circle> iterator() {
        return new BVHIterator(this);
    }

    public class BVHIterator implements Iterator<Circle> {

        LinkedList<Circle> circles = new LinkedList<Circle>();
        int index = 0;

        // todo for students
        public BVHIterator(BVH bvh) {
            traverse(bvh);
        }

        public void traverse(BVH bvh){
            // populate circles reccursively
            if((bvh.child1 == null) && (bvh.child2 == null)){
                this.circles.add(bvh.containedCircle);
            }
            // if there is a child on the left recursive on the left
            if (bvh.child1 != null){
                traverse(bvh.child1);
            }
            // if there is a child on the left recursive on the right
            if (bvh.child2 != null){
                traverse(bvh.child2);
            }

        }

        // todo for students
        @Override
        public boolean hasNext() {
            return index < circles.size();
        }

        // todo for students
        @Override
        public Circle next() {
            return circles.get(index++);
        }
    }
}