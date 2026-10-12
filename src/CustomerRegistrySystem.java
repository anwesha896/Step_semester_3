import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

class RegistryCustomer {
    private int id;
    private String name;

    public RegistryCustomer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        RegistryCustomer other = (RegistryCustomer) obj;

        return id == other.id && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

public class CustomerRegistrySystem {

    private Set<RegistryCustomer> customers = new HashSet<>();

    public boolean addCustomer(int id, String name) {
        RegistryCustomer customer = new RegistryCustomer(id, name);

        if (customers.add(customer)) {
            return true;
        }

        return false;
    }

    public boolean containsCustomer(int id, String name) {
        RegistryCustomer customer = new RegistryCustomer(id, name);
        return customers.contains(customer);
    }

    public int uniqueCustomerCount() {
        return customers.size();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CustomerRegistrySystem registry = new CustomerRegistrySystem();

        System.out.println("Enter number of customers to add:");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("Enter customer ID and name:");
            int id = Integer.parseInt(sc.nextLine());
            String name = sc.nextLine();

            if (registry.addCustomer(id, name)) {
                System.out.println("true");
            } else {
                System.out.println("false (duplicate rejected)");
            }
        }

        System.out.println("Enter customer ID and name to search:");
        int searchId = Integer.parseInt(sc.nextLine());
        String searchName = sc.nextLine();

        System.out.println("unique count " + registry.uniqueCustomerCount());
        System.out.println("contains: "
                + registry.containsCustomer(searchId, searchName));

        sc.close();
    }
}