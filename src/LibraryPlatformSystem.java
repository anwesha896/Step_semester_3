
import java.util.Scanner;

abstract class LibraryResourceBase {
    String id;

    LibraryResourceBase(String id) {
        this.id = id;
    }
}

interface LibraryReservable {
    void reserve(String memberId);
}

interface LibraryDownloadable {
    void download();
}

class LibraryBookResource extends LibraryResourceBase
        implements LibraryReservable {

    LibraryBookResource(String id) {
        super(id);
    }

    public void reserve(String memberId) {
        System.out.println(id + " reserved for " + memberId);
    }
}

class LibraryEBookResource extends LibraryResourceBase
        implements LibraryReservable, LibraryDownloadable {

    LibraryEBookResource(String id) {
        super(id);
    }

    public void reserve(String memberId) {
        System.out.println(id + " reserved for " + memberId);
    }

    public void download() {
        System.out.println(id + " downloaded");
    }
}

public class LibraryPlatformSystem {

    static LibraryResourceBase[] resources =
            new LibraryResourceBase[100];

    static int count = 0;

    static int findResource(String id) {
        int left = 0;
        int right = count - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int result = resources[mid].id.compareTo(id);

            if (result == 0)
                return mid;
            else if (result < 0)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return -1;
    }

    static void addResource(String type, String id) {
        if (findResource(id) != -1) {
            System.out.println("duplicate rejected");
            return;
        }

        LibraryResourceBase resource;

        if (type.equals("Book")) {
            resource = new LibraryBookResource(id);
        } else if (type.equals("EBook")) {
            resource = new LibraryEBookResource(id);
        } else {
            System.out.println("unsupported resource type");
            return;
        }

        int i = count - 1;

        while (i >= 0 && resources[i].id.compareTo(id) > 0) {
            resources[i + 1] = resources[i];
            i--;
        }

        resources[i + 1] = resource;
        count++;

        System.out.println(id + " added");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            if (line.isEmpty())
                continue;

            String[] parts = line.split("\\s+");

            if (parts[0].equals("ADD")) {
                addResource(parts[1], parts[2]);
            }

            else if (parts[0].equals("FIND")) {
                int index = findResource(parts[1]);

                if (index == -1)
                    System.out.println(parts[1] + " not found");
                else
                    System.out.println(parts[1]
                            + " found at index " + index);
            }

            else if (parts[0].equals("RESERVE")) {
                int index = findResource(parts[1]);

                if (index == -1) {
                    System.out.println(parts[1] + " not found");
                } else if (resources[index]
                        instanceof LibraryReservable) {
                    LibraryReservable r =
                            (LibraryReservable) resources[index];
                    r.reserve(parts[3]);
                } else {
                    System.out.println(parts[1]
                            + " rejected: reserve unsupported");
                }
            }

            else if (parts[0].equals("DOWNLOAD")) {
                int index = findResource(parts[1]);

                if (index == -1) {
                    System.out.println(parts[1] + " not found");
                } else if (resources[index]
                        instanceof LibraryDownloadable) {
                    LibraryDownloadable d =
                            (LibraryDownloadable) resources[index];
                    d.download();
                } else {
                    System.out.println(parts[1]
                            + " rejected: download unsupported");
                }
            }
        }

        sc.close();
    }
}
