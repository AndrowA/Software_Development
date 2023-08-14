import java.util.ArrayList;
import java.util.HashSet;

public class ContactFinder {
    // todo for students
    // Returns a HashSet of ContactResult objects representing all the contacts between circles in the scene.
    // The runtime of this method should be O(n^2) where n is the number of circles.
    public static HashSet<ContactResult> getContactsNaive(ArrayList<Circle> circles) {
        HashSet<ContactResult> output = new HashSet<>();
        for (Circle a: circles){
            for (Circle b:circles){
                if((a.id != b.id) && (a.isContacting(b))!= null){
                    output.add(a.isContacting(b));
                }
            }
        }
        return output;
    }

    // todo for students
    // Returns a HashSet of ContactResult objects representing all the contacts between circles in the scene.
    // The runtime of this method should be O(n*log(n)) where n is the number of circles.
    public static HashSet<ContactResult> getContactsBVH(ArrayList<Circle> circles, BVH bvh) {

        // Output
        HashSet<ContactResult> output = new HashSet<>();

        // Go through the list of circles
        for (Circle circle: circles){

            // Create a map of contacts for each circle using the accelerated method
            HashSet<ContactResult> contacts = getContactBVH(circle,bvh);

            // add all the contacts to the output hashmap
            output.addAll(contacts);
        }

        return output;
    }

    // todo for students
    // Takes a single circle c and a BVH bvh.
    // Returns a HashSet of ContactResult objects representing contacts between c
    // and the circles contained in the leaves of the bvh.
    public static HashSet<ContactResult> getContactBVH(Circle c, BVH bvh) {
        HashSet<ContactResult> output = new HashSet<>();

        if (!(c.getBoundingBox().intersectBox(bvh.boundingBox))){
            return output;
        }
        else{

            if ((bvh.child1 == null) && (bvh.child2 == null)){
                if (bvh.containedCircle.id != c.id){
                    if (c.isContacting(bvh.containedCircle)!= null){
                        output.add(c.isContacting(bvh.containedCircle));
                    }
                }
            }
            else{
                if (bvh.child1 != null){
                    output.addAll(getContactBVH(c,bvh.child1));

                }
                if (bvh.child2 != null){
                    output.addAll(getContactBVH(c,bvh.child2));
                }
            }
        }

        return output;
    }
}
