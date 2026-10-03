import java.util.Scanner;

class LibraryCatalogBook {
    private String isbn;
    private String title;

    public LibraryCatalogBook(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }
}

public class LibraryCatalogLookup {

    public static String findBook(
            LibraryCatalogBook[] catalog, String targetIsbn) {

        int left = 0;
        int right = catalog.length - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            int comparison =
                    catalog[mid].getIsbn().compareTo(targetIsbn);

            if (comparison == 0) {
                return catalog[mid].getTitle();
            }
            else if (comparison < 0) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }

        return "Not Found";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LibraryCatalogBook[] catalog = {
                new LibraryCatalogBook(
                        "0001112223", "Introduction to Algebra"),

                new LibraryCatalogBook(
                        "0002223334", "Beginning Python"),

                new LibraryCatalogBook(
                        "0003334445", "Classic Mythology"),

                new LibraryCatalogBook(
                        "0004445556", "Data and Society"),

                new LibraryCatalogBook(
                        "0005556667", "European History")
        };

        System.out.print("Enter ISBN to search: ");
        String targetIsbn = sc.nextLine();

        System.out.println(
                findBook(catalog, targetIsbn)
        );

        sc.close();
    }
}