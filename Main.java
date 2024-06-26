/*
    Nama        : Erdafa Andikri
    NPM         : 2306244993
    Kode Asdos  : RAC
    Tanggal     : 5 April 2024

    Program ini dibuat untuk memenuhi tugas Lab 05 mata kuliah DDP 2.

    Program ini merupakan sebuah aplikasi sederhana untuk mengelola karyawan di sebuah perusahaan bernama PacilValley.
    Program ini memiliki 5 menu:
        1. Daftar Karyawan
        2. Hire Karyawan
        3. Menaikkan Gaji Karyawan (Permanent and Contract)
        4. Memperpanjang Kontrak Karyawan (Contract and Intern)
        5. Keluar

    Menu 1 merupakan menu untuk menampilkan daftar karyawan yang terdaftar di perusahaan
    Menu 2 merupakan menu untuk menambahkan karyawan baru ke dalam perusahaan
    Menu 3 dan Menu 4 merupakan menu untuk mengelola karyawan yang telah dihire sebelumnya
    Menu 5 merupakan menu untuk keluar dari program

    Program ini memiliki error handling untuk memastikan input yang diberikan valid.
*/


import java.util.ArrayList;
import java.util.Scanner;

// Main class
public class Main {
    static ArrayList<Employee> employeeList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // Main method
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

  // Menu 1
    public static void printEmployeeList() {
        if (employeeList.size() == 0)
            System.out.println("Tidak ada karyawan yang terdaftar");
        else {
            if (getPermanentEmployee().size() != 0)
                displayPermanentEmployee();
            if (getContractEmployee().size() != 0)
                displayContractEmployee();
            if (getInternEmployee().size() != 0)
                displayInternEmployee();
        }
    }

  // Menu 2
    public static void hireEmployee() {
        System.out.print("Nama: ");
        String name = sc.next();
        if (getEmployeeByNameOrId(name) != null) {
            System.out.println("Nama sudah terdaftar!!!\n");
            return;
        }
        System.out.print("Base Salary: ");
        double salary = sc.nextDouble();
        System.out.print("Status Employee (Permanent/Contract/Intern): ");
        String status = sc.next();
        if (status.equals("Permanent")) {
            PermanentEmployee employee = new PermanentEmployee(name, salary);
            employeeList.add(employee);
            System.out.print("PermanentEmployee dengan ID " + employee.employeeId + " bernama " + name + " berhasil ditambahkan!\n\n");
        } else if (status.equals("Contract")) {
            System.out.print("Lama Kontrak (Bulan): ");
            int contractDuration = sc.nextInt();
            ContractEmployee employee = new ContractEmployee(name, salary, contractDuration);
            employeeList.add(employee);
            System.out.print("ContractEmployee dengan ID " + employee.employeeId + " bernama " + name + " berhasil ditambahkan!\n\n");
        } else if (status.equals("Intern")) {
            System.out.print("Lama Kontrak (Bulan): ");
            int contractDuration = sc.nextInt();
            InternEmployee employee = new InternEmployee(name, salary, contractDuration);
            employeeList.add(employee);
            System.out.print("InternEmployee dengan ID " + employee.employeeId + " bernama " + name + " berhasil ditambahkan!\n\n");
        } else {
            System.out.println("Status Employee tidak valid!\n\n");
        }
    }

    // Menu 3
    public static void askForRaise() {
        if (getContractEmployee().size() == 0 && getPermanentEmployee().size() == 0){
            System.out.println("Tidak Ada Permanent atau Contract Employee yang Terdaftar!!!\n");
            return;
        }
        if (getPermanentEmployee().size() != 0)
            displayPermanentEmployee();
        if (getContractEmployee().size() != 0)
            displayContractEmployee();

        System.out.print("Masukan Nama/ID Employee: ");
        String nameOrId = sc.next();
        Employee employee = getEmployeeByNameOrId(nameOrId);
        if (employee == null) {
            System.out.println("Employee dengan Nama/ID " + nameOrId + " Tidak Ditemukan!!!\n");
            return;
        } else if (employee instanceof InternEmployee) {
            System.out.println("Intern Employee Tidak Bisa Mendapatkan Raise!!!\n");
            return;
        }
        System.out.print("Masukan Jumlah Kenaikan Gaji: ");
        double raise = sc.nextDouble();
        if (raise < 0) {
            System.out.println("Kenaikan Gaji Tidak Boleh Negatif!!!\n");
            return;
        }
        if (employee instanceof PermanentEmployee) {
            PermanentEmployee permanentEmployee = (PermanentEmployee) employee;
            permanentEmployee.askRaise(raise);
        } else if (employee instanceof ContractEmployee) {
            ContractEmployee contractEmployee = (ContractEmployee) employee;
            contractEmployee.askRaise(raise);
        } 
        System.out.println("Employee dengan Nama/ID " + nameOrId + " Berhasil Dinaikkan Gajinya Sebesar " + String.format("%.0f", raise) + "\n\n");
    }

    // Menu 4
    public static void extendContract() {
        if (getContractEmployee().size() == 0 && getInternEmployee().size() == 0){
            System.out.println("Tidak Ada Contract atau Intern Employee yang Terdaftar!!!\n");
            return;
        }
        if (getContractEmployee().size() != 0)
            displayContractEmployee();
        if (getInternEmployee().size() != 0)
            displayInternEmployee();

        System.out.print("Masukan Nama/ID Employee: ");
        String nameOrId = sc.next();
        Employee employee = getEmployeeByNameOrId(nameOrId);
        if (employee == null) {
            System.out.println("Employee dengan Nama/ID " + nameOrId + " Tidak Ditemukan!!!\n");
            return;
        } else if (employee instanceof PermanentEmployee) {
            System.out.println("PermanentEmployee Tidak Bisa Extend Kontrak!!!\n");
            return;
        }
        System.out.print("Masukan Jumlah Perpanjangan Kontrak: ");
        int duration = sc.nextInt();
        if (duration < 0) {
            System.out.println("Perpanjangan Kontrak Tidak Boleh Negatif!!!\n");
            return;
        }
        if (employee instanceof InternEmployee) {
            InternEmployee internEmployee = (InternEmployee) employee;
            internEmployee.extendContract(duration);
        } else if (employee instanceof ContractEmployee) {
            ContractEmployee contractEmployee = (ContractEmployee) employee;
            contractEmployee.extendContract(duration);
        }
        System.out.println("Employee dengan Nama/ID " + nameOrId + " Berhasil Diperpanjang Kontraknya Selama " + duration + " Bulan\n");
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

//      It's The End of The Program
//            Thank You RAC!
//
//
//
//            Here's a null
//                  :(

// FYI: This lab was done on a tablet using replit because at the moment i am on the way of reaching my hometown.
//      So, i can't use my laptop for this lab. Sorry if it's not as good as the previous labs i've done.

// Have a Nice Day!