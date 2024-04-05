import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Employee> employeeList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Selamat Datang di PacilRekrutment");
        while (true) {
            printWelcomingMsg();
            System.out.print("Input: ");
            int actionCode = sc.nextInt();
            switch (actionCode) {
                case 1:
                    printEmployeeList();
                    break;
                case 2:
                    hireEmployee();
                    break;
                case 3:
                    askForRaise();
                    break;
                case 4:
                    extendContract();
                    break;
                case 5:
                    System.out.println("Terima kasih telah menggunakan layanan PacilRekrutment ~ !");
                    sc.close();
                    return;
                default:
                    unknownActionMsg();
                    break;
            }
        }
    }

    public static void printEmployeeList() {
        displayPermanentEmployee();
        displayContractEmployee();
        displayInternEmployee();
    }

    public static void hireEmployee() {
        System.out.print("Nama: ");
        String name = sc.nextLine();
        System.out.print("\nBase Salary: ");
        double salary = sc.nextDouble();
        System.out.print("\nStatus Employee (Permanent/Contract/Intern): ");
        String status = sc.nextLine();
        if (status.equals("Permanent")) {
            PermanentEmployee employee = new PermanentEmployee(name, salary);
            employeeList.add(employee);
            System.out.print("\nPermanentEmployee dengan ID " + employee.employeeId + " bernama " + name + " berhasil ditambahkan!");
        } else if (status.equals("Contract")) {
            System.out.print("\nLama Kontrak (Bulan): ");
            int contractDuration = sc.nextInt();
            ContractEmployee employee = new ContractEmployee(name, salary, contractDuration);
            employeeList.add(employee);
            System.out.print("\nContractEmployee dengan ID " + employee.employeeId + " bernama " + name + " berhasil ditambahkan!");
        } else if (status.equals("Intern")) {
            System.out.print("\nLama Kontrak (Bulan): ");
            int contractDuration = sc.nextInt();
            InternEmployee employee = new InternEmployee(name, salary, contractDuration);
            employeeList.add(employee);
            System.out.print("\nInternEmployee dengan ID " + employee.employeeId + " bernama " + name + " berhasil ditambahkan!");
        } else {
            System.out.println("Status Employee tidak valid!");
        }
    }

    public static void askForRaise() {
        if (getContractEmployee().size() == 0 && getPermanentEmployee().size() == 0){
            System.out.println("Tidak Ada Permanent atau Contract Employee yang Terdaftar!!!");
            return;
        }

        displayPermanentEmployee();
        displayContractEmployee();

        System.out.println("Masukan Nama/ID Employee: ");
        String nameOrId = sc.nextLine();
        Employee employee = getEmployeeByNameOrId(nameOrId);
        if (employee == null) {
            System.out.println("Employee dengan Nama/ID " + nameOrId + " Tidak Ditemukan!!!");
            return;
        } else if (employee instanceof InternEmployee) {
            System.out.println("Intern Employee Tidak Bisa Mendapatkan Raise!!!");
            return;
        }
        System.out.println("Masukan Jumlah Kenaikan Gaji: ");
        double raise = sc.nextDouble();
        if (raise < 0) {
            System.out.println("Kenaikan Gaji Tidak Boleh Negatif!!!");
            return;
        }
        employee.askRaise(raise);
        System.out.println("Employee dengan Nama/ID " + nameOrId + " Berhasil Dinaikkan Gajinya Sebesar " + raise);
    }

    public static void extendContract() {
        if (getContractEmployee().size() == 0 && getInternEmployee().size() == 0){
            System.out.println("Tidak Ada Contract atau Intern Employee yang Terdaftar!!!");
            return;
        }

        displayContractEmployee();
        displayInternEmployee();

        System.out.println("Masukan Nama/ID Employee: ");
        String nameOrId = sc.nextLine();
        Employee employee = getEmployeeByNameOrId(nameOrId);
        if (employee == null) {
            System.out.println("Employee dengan Nama/ID " + nameOrId + " Tidak Ditemukan!!!");
            return;
        } else if (employee instanceof PermanentEmployee) {
            System.out.println("PermanentEmployee Tidak Bisa Extend Kontrak!!!");
            return;
        }
        System.out.println("Masukan Jumlah Perpanjangan Kontrak: ");
        int duration = sc.nextInt();
        if (duration < 0) {
            System.out.println("Perpanjangan Kontrak Tidak Boleh Negatif!!!");
            return;
        }
        employee.extendContract(duration);
        System.out.println("Employee dengan Nama/ID " + nameOrId + " Berhasil Diperpanjang Kontraknya Sebesar " + duration + " Bulan");
    }

    // Kumpulan Helper Method

    public static Employee getEmployeeByNameOrId(String nameOrId) {
        // Return employee if exists, otherwise null
        for (Employee employee : employeeList) {
            if (employee.name.equals(nameOrId) || Integer.toString(employee.employeeId).equals(nameOrId)) {
                return employee;
            }
        }
        return null;
    }

    public static void displayPermanentEmployee() {
        if (PermanentEmployee.employeeCnt == 0) {
            return;
        }
        System.out.println("===== Pegawai Tetap =====");
        ArrayList<PermanentEmployee> permanentEmployees = getPermanentEmployee();
        for (PermanentEmployee employee : permanentEmployees) {
            System.out.println(employee);
        }
        System.out.println();
    }

    public static void displayContractEmployee() {
        if (ContractEmployee.employeeCnt == 0) {
            return;
        }
        System.out.println("===== Pegawai Kontrak =====");
        ArrayList<ContractEmployee> contractEmployees = getContractEmployee();
        for (ContractEmployee employee : contractEmployees) {
            System.out.println(employee);
        }
        System.out.println();
    }

    public static void displayInternEmployee() {
        if (InternEmployee.employeeCnt == 0) {
            return;
        }
        System.out.println("===== Pegawai Intern =====");
        ArrayList<InternEmployee> internEmployees = getInternEmployee();
        for (InternEmployee employee : internEmployees) {
            System.out.println(employee);
        }
        System.out.println();
    }

    // Penggunaan Generics dapat digunakan (akan dipelajari di week mendatang)
    // untuk mengurangi pengulangan 3 method ini
    public static ArrayList<InternEmployee> getInternEmployee() {
        ArrayList<InternEmployee> internEmployees = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee instanceof InternEmployee) {
                internEmployees.add((InternEmployee) employee);
            }
        }
        return internEmployees;
    }

    public static ArrayList<ContractEmployee> getContractEmployee() {
        ArrayList<ContractEmployee> contractEmployees = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee instanceof ContractEmployee) {
                contractEmployees.add((ContractEmployee) employee);
            }
        }
        return contractEmployees;
    }

    public static ArrayList<PermanentEmployee> getPermanentEmployee() {
        ArrayList<PermanentEmployee> permanentEmployees = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee instanceof PermanentEmployee) {
                permanentEmployees.add((PermanentEmployee) employee);
            }
        }
        return permanentEmployees;
    }

    // Printing Function
    public static void printWelcomingMsg() {
        System.out.println("Silakan pilih salah satu opsi berikut:");
        System.out.println("[1] Employee List");
        System.out.println("[2] Hire Employee");
        System.out.println("[3] Raise Salary");
        System.out.println("[4] Extend Contract");
        System.out.println("[5] Exit");
        System.out.println("=".repeat(64));
    }

    public static void unknownActionMsg() {
        System.out.println("Mohon masukkan opsi yang valid!\n");
    }
}