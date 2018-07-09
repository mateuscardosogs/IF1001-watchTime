package if1001.watchtime.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {

    private static DatabaseReference referenceFirebase;
    private static FirebaseAuth authentication;


    public static DatabaseReference getFirebase() {
        if (referenceFirebase == null) {
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenceFirebase;
    }

    public static FirebaseAuth getFirebaseAuthentication() {
        if (authentication == null) {
            authentication = FirebaseAuth.getInstance();
        }
        return authentication;
    }

    public static FirebaseAuth getCurrentUser() {
        if (authentication == null) {
            authentication = FirebaseAuth.getInstance();
        }
        return authentication;
    }
}
