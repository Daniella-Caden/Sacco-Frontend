// import java.util.Comparator;
// import org.primefaces.model.SortOrder;
// import org.pahappa.systems.kimanyisacco.models.Members;


// public class MemberComparator implements Comparator<Members> {

//     private String sortField;
//     private SortOrder sortOrder;

//     public MemberComparator(String sortField, SortOrder sortOrder) {
//         this.sortField = sortField;
//         this.sortOrder = sortOrder;
//     }

//     @Override
//     public int compare(Members member1, Members member2) {
//         // Implement comparison logic based on sortField and sortOrder
//         // For example, if your Member class has a 'get' method for the sortField,
//         // you can use reflection to dynamically get the field value and compare.
//         // You may need to handle null values and different data types appropriately.

//         // Example comparison based on 'firstName'
//         if ("firstName".equals(sortField)) {
//             return sortOrder == SortOrder.ASCENDING
//                     ? member1.getFirstName().compareTo(member2.getFirstName())
//                     : member2.getFirstName().compareTo(member1.getFirstName());
//         }

//         // Handle other sortField values similarly for other columns
//         // ...

//         // If no sortField matched, return 0 (considered equal)
//         return 0;
//     }
// }
